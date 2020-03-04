package exemplo.dao;

import javax.ejb.Stateless;

import exemplo.core.BaseDao;
import exemplo.model.UsuarioEntity;

@Stateless
public class UsuarioDao extends BaseDao<UsuarioEntity> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
