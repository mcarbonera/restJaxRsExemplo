package exemplo.negocio;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import exemplo.dao.UsuarioDao;
import exemplo.model.UsuarioEntity;

@Stateless
public class UsuarioRepositoryFinal implements Serializable{
	private static final long serialVersionUID=1L;
	@Inject
	private UsuarioDao usuarioDao;
	
	/* Listar Todos */
	public List<UsuarioEntity> consultarTodos(){
		return usuarioDao.listarTodos();
	}
	
	/* Listar por id */
	public UsuarioEntity consultarPorId(Integer id) {
		return usuarioDao.buscarPorId(id);
	}
	
	/* Cadastrar */
	public Boolean cadastrar(Integer id, UsuarioEntity usuarioEntity) {
		return usuarioDao.adicionar(id, usuarioEntity);
	}
	
	/* Editar */
	public UsuarioEntity editar(Integer id, UsuarioEntity usuarioEntity) {
		return usuarioDao.editar(id, usuarioEntity);
	}
	
	/* Excluir */
	public Boolean excluir(Integer id) {
		return usuarioDao.excluir(id);
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
