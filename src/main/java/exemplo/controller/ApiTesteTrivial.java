package exemplo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import exemplo.model.Usuario;
import exemplo.model.UsuarioEntity;
import exemplo.negocio.UsuarioRepository;

@Stateless
@LocalBean
@Path("/teste")
public class ApiTesteTrivial {
	@PersistenceContext(unitName="persistence-unit", type=PersistenceContextType.TRANSACTION)
	EntityManager em;

	private UsuarioEntity usuarioEntity;
	
	@EJB
	private UsuarioRepository usuarioRepository;

	@GET
	@Path("/consultarTodos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> consultarTodos() throws URISyntaxException {		
		/* Operação consultar todos */
		try {
			Query query = em.createQuery("FROM UsuarioEntity");
			List<Usuario> usuarios = query.getResultList();
			return usuarios;
		} catch(Exception e) {
			return null;
		}
	}
	
	@GET
	@Path("/consultarId/{id: [0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario consultarId(@PathParam("id") Integer id) throws URISyntaxException {
		/* Operação consultar por Id */
		try {
			Usuario usuario = new Usuario(em.find(UsuarioEntity.class, id));
			return usuario;
		} catch(Exception e) {
			return null;
		}
	}
	
	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response cadastrar(Usuario usuario) throws URISyntaxException {
		/* Operação cadastrar usuário */
		try {
			usuarioEntity = em.find(UsuarioEntity.class, usuario.getId());
			
			if(null == usuarioEntity) {
				usuarioEntity = new UsuarioEntity(usuario);
				em.persist(usuarioEntity);
				return Response.status(201)
						.contentLocation(new URI("/teste/" + usuario.getId()))
						.build();
			}
			/* Resposta */
			return Response.status(400)
					.contentLocation(new URI("/teste/" + usuario.getId()))
					.build();
		} catch(Exception e) {
			return Response.status(400)
					.contentLocation(new URI("/teste/" + usuario.getId()))
					.build();
		}
	}
	
	@PUT
	@Path("/editar/{id: [0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editar(@PathParam("id") Integer id, Usuario usuario) throws URISyntaxException {
		try {
			/* Operação editar usuario */
			UsuarioEntity usuarioEntity = em.find(UsuarioEntity.class, usuario.getId());
			usuarioEntity.setUri(usuario.getUri());
			usuarioEntity.setNome(usuario.getNome());
			usuarioEntity.setSobrenome(usuario.getSobrenome());
			em.merge(usuarioEntity);
			
			/* Resposta */
			return Response.status(204)
					.build();
		} catch(Exception e) {
			return Response.status(400)
					.build();
		}
	}
	
	@DELETE
	@Path("/excluir/{id: [0-9]*}")
	public Response excluir(@PathParam("id") Integer id) throws URISyntaxException {
		try {
			/* Operação excluir usuario */
			UsuarioEntity usuarioEntity = em.find(UsuarioEntity.class, id);
			em.remove(usuarioEntity);
			
			/* Resposta */
			return Response.status(204).build();
		} catch(Exception e) {
			/* Resposta */
			return Response.status(400).build();
		}
	}
}
