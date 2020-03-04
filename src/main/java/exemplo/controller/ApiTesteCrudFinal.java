package exemplo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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

import exemplo.model.UsuarioEntity;
import exemplo.negocio.UsuarioRepositoryFinal;

@Path("/crudFinal")
@RequestScoped
public class ApiTesteCrudFinal {	
	@Inject
	private UsuarioRepositoryFinal usuarioRepositoryFinal;
	
	@GET
	@Path("/consultarTodos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UsuarioEntity> consultarTodos() throws URISyntaxException {		
		/* Operação consultar todos */
		return usuarioRepositoryFinal.consultarTodos();
	}
	
	@GET
	@Path("/consultarId/{id: [0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioEntity consultarId(@PathParam("id") Integer id) throws URISyntaxException {
		/* Operação consultar por Id */
		return usuarioRepositoryFinal.consultarPorId(id);
	}
	
	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response cadastrar(UsuarioEntity usuarioEntity) throws URISyntaxException {
		/* Operação cadastrar usuário */
		if(usuarioRepositoryFinal.cadastrar(usuarioEntity.getId(), usuarioEntity)) {
			return Response.status(201)
					.contentLocation(new URI("/teste/" + usuarioEntity.getId()))
					.build();
		} else {
			return Response.status(400)
					.contentLocation(new URI("/teste/" + usuarioEntity.getId()))
					.build();
		}
	}
	
	@PUT
	@Path("/editar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UsuarioEntity editar(UsuarioEntity usuarioEntity) throws URISyntaxException {
		return usuarioRepositoryFinal.editar(usuarioEntity.getId(), usuarioEntity);
	}
	
	@DELETE
	@Path("/excluir/{id: [0-9]*}")
	public Response excluir(@PathParam("id") Integer id) throws URISyntaxException {
		if(usuarioRepositoryFinal.excluir(id)) {
			return Response.status(204).build();
		} else {
			return Response.status(400).build();
		}
	}
}