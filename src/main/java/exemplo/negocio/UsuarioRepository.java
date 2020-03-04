package exemplo.negocio;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import exemplo.model.Usuario;
import exemplo.model.UsuarioEntity;

@Stateless
@LocalBean
public class UsuarioRepository implements Serializable{
	private static final long serialVersionUID=1L;
	private UsuarioEntity usuarioEntity;
	@PersistenceContext(unitName="persistence-unit", type=PersistenceContextType.TRANSACTION)
	EntityManager em;
	
	/* No-arg contructor */
	public UsuarioRepository() {
		
	}
	
	/* Listar Todos */
	public List<Usuario> consultarTodos(){
		try {
			Query query = em.createQuery("FROM UsuarioEntity");
			List<Usuario> usuarios = query.getResultList();
			return usuarios;
		} catch(Exception e) {
			return null;
		}
	}
	
	/* Listar Por Id */
	public Usuario consultarPorId(Integer id) {
		try {
			Usuario usuario = new Usuario(em.find(UsuarioEntity.class, id));
			return usuario;
		} catch(Exception e) {
			return null;
		}
	}
	
	/* Cadastrar */
	public Boolean cadastrar(Usuario usuario) {
		usuarioEntity = em.find(UsuarioEntity.class, usuario.getId());
		
		if(null == usuarioEntity) {
			try {
				usuarioEntity = new UsuarioEntity(usuario);
				em.persist(usuarioEntity);
				
				return true;
			} catch(Exception e) {
				return false;
			}
		}
		
		return false;
	}
	/* Editar */
	public Boolean editar(Integer id, Usuario usuario) {
		try {
			/* Operação editar usuario */
			UsuarioEntity usuarioEntity = em.find(UsuarioEntity.class, usuario.getId());
			usuarioEntity.setUri(usuario.getUri());
			usuarioEntity.setNome(usuario.getNome());
			usuarioEntity.setSobrenome(usuario.getSobrenome());
			em.merge(usuarioEntity);
			
			/* Resposta */
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	/* Excluir */
	public Boolean excluir(Integer id) {
		try {
			/* Operação excluir usuario */
			UsuarioEntity usuarioEntity = em.find(UsuarioEntity.class, id);
			em.remove(usuarioEntity);
			
			/* Resposta */
			return true;
		} catch(Exception e) {
			/* Resposta */
			return false;
		}
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
