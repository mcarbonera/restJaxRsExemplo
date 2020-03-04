package exemplo.core;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.jpa.QueryHints;

public abstract class BaseDao<T> implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @PersistenceContext(unitName="persistence-unit", type=PersistenceContextType.TRANSACTION)
	protected EntityManager em;
    
    private Class<T> classe;
    //private Session session;
    private transient CriteriaBuilder criteriaBuilder;
    private transient Root<T> root;
    private boolean cacheavel;
    
    /* No-arg constructor */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public BaseDao() {
    	Class obtainedClazz = getClass();
        Type superClazz;
        ParameterizedType genericSuperclass;
        while (true) {
            superClazz = obtainedClazz.getGenericSuperclass();
            if (superClazz instanceof ParameterizedType) {
                genericSuperclass = (ParameterizedType) superClazz;
                break;
            }
            obtainedClazz = obtainedClazz.getSuperclass();
        }
        this.classe = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }
    
    /**
     * Método inicializador deste DAO. Irá guardar uma referência da {@link Session}, {@link CriteriaBuilder}.
     * <p>Também irá guardar um valor booleano indicando se para esta {@link #classe}, de que trata este DAO, deve ser
     * armazenada em cache após as consultas. Para isso, a classe deve estar anotada com {@link Cacheable}</p>
     */
    
    @PostConstruct
    public void iniciar() {
    	//this.session = (Session) em.getDelegate();
    	this.criteriaBuilder = em.getCriteriaBuilder();
    	for (Annotation annotation : classe.getDeclaredAnnotations()) {
    		if (annotation.annotationType().equals(Cacheable.class)) {
    			cacheavel = ((Cacheable) annotation).value();
    		}
    	}
    }
    
    
	/* Operações CRUD */
    /* Adicionar */
    @Transactional
	public boolean adicionar(int id, T obj) {
    	if(em.find(classe, id) == null) {
    		em.persist(obj);
    		return true;
    	}
    	return false;
    }
   
    /* Listar Todos */
    @Transactional
    public List<T> listarTodos() {
    	TypedQuery<T> query = em.createQuery(getCriteria());
    	setHints(query);
    	return Optional.ofNullable(query.getResultList()).orElse(new ArrayList<>());
    }
    
    /* Buscar Por Id */
    @Transactional
    public T buscarPorId(int id) {
    	return em.find(classe, id);
    }
    
    /* Editar */
    @Transactional
    public T editar(int id, T obj) {
    	T objNoBanco = em.find(classe, id);
    	
    	if(objNoBanco != null) {
    		return em.merge(obj);
    	} else {
    		return null;
    	}
    }
    
    /* Excluir */
    @Transactional
    public boolean excluir(T obj) {
    	if(em.contains(obj)) {
    		em.remove(obj);
    		return true;
    	} else {
    		return false;
    	}    	
    }
    
    @Transactional
    public boolean excluir(int id) {
    	T obj = em.find(classe, id);
    	if(obj != null) {
    		em.remove(obj);
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /* Getters and Setters */
    /**
     * Método que irá criar a consulta, baseado na entidade persistente deste DAO, ao mesmo tempo em que irá aplicar o
     * 'from' da consulta e executar o select sobre a entidade {@link #root} retornado pelo {@link
     * CriteriaQuery#from(Class)}.
     *
     * @return CriteriaQuery correspondente a consulta desta entidade persistente.
     */ 
    protected final CriteriaQuery<T> getCriteria() {
        CriteriaQuery<T> criteria = criteriaBuilder.createQuery(classe);
        root = criteria.from(classe);
        criteria.select(root);
        return criteria;
    }
    
    /**
     * Seta os Hints na query. Atualmente apenas set {@link QueryHints#HINT_CACHEABLE} para true, se o valor de {@link
     * #cacheavel} também for true.
     *
     * @param query {@link Query} onde serão aplicados os hints.
     */
    protected final void setHints(Query query) {
        if (cacheavel) {
            query.setHint(QueryHints.HINT_CACHEABLE, true);
        }
    }
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
