package exemplo.model;

import javax.annotation.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "REST_HELLO_WORLD_USUARIO", schema = "APL_SBJ")
@ManagedBean
public class UsuarioEntity implements Serializable {
	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name = "ID_USUARIO", unique = true, nullable = false, precision=10, scale = 0)
	private Integer id;
	
	@Column(name = "URI", nullable = false)
	private String uri;
	
	@Column(name = "FIRST_NAME")
	private String nome;
	
	@Column(name = "LAST_NAME")
	private String sobrenome;
	
	/* No-Arg constructor
	 * "[...] mandatory that the Hibernate Entity Bean Classes require no-arg constructors, [...]" 
	 * Java cria um construtor implícito, mas somente se nenhum outro construtor for criado.
	 */
	public UsuarioEntity() {
		
	}
	
	public UsuarioEntity(Usuario usuario) {
		id = usuario.getId();
		uri = usuario.getUri();
		nome = usuario.getNome();
		sobrenome = usuario.getSobrenome();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	/*
	public UsuarioEntity getUsuarioEntity() {
		return usuarioEntity;
	}

	public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
		this.usuarioEntity = usuarioEntity;
	}
	*/

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "UsuarioEntity [id=" + id + ", uri=" + uri + ", nome=" + nome + ", sobrenome=" + sobrenome + "]";
	}
	
	
}
