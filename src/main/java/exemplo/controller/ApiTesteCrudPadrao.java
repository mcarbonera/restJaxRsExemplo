package exemplo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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
import exemplo.negocio.UsuarioRepository;

@Stateless
@LocalBean
@Path("/crud")
public class ApiTesteCrudPadrao {	
	@EJB
	private UsuarioRepository usuarioRepository;

	@GET
	@Path("/consultarTodos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> consultarTodos() throws URISyntaxException {		
		/* Operação consultar todos */
		return usuarioRepository.consultarTodos();
	}
	
	@GET
	@Path("/consultarId/{id: [0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario consultarId(@PathParam("id") Integer id) throws URISyntaxException {
		/* Operação consultar por Id */
		return usuarioRepository.consultarPorId(id);
	}
	
	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response cadastrar(Usuario usuario) throws URISyntaxException {
		/* Operação cadastrar usuário */
		if(usuarioRepository.cadastrar(usuario)) {
			return Response.status(201)
					.contentLocation(new URI("/teste/" + usuario.getId()))
					.build();
		} else {
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
		if(usuarioRepository.editar(id, usuario)) {
			return Response.status(204)
					.build();
		} else {
			return Response.status(400)
					.build();
		}
	}
	
	@DELETE
	@Path("/excluir/{id: [0-9]*}")
	public Response excluir(@PathParam("id") Integer id) throws URISyntaxException {
		if(usuarioRepository.excluir(id)) {
			return Response.status(204).build();
		} else {
			return Response.status(400).build();
		}
	}
}
