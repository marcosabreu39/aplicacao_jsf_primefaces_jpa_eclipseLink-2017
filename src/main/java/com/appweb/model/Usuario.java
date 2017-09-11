package com.appweb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false)
	@Size(min=4, max=25, message="Nome entre 4 e 25 caracteres")
	@NotNull(message="O nome é obrigatório")
	private String nome;
	
	@Column(nullable=false, unique=true)
	@Size(min=4, max=12, message="Login entre 4 e 12 caracteres")
	@NotNull(message="O login é obrigatório")
	private String login;
	
	@Column(nullable=false)
	@Size(min=4, max=10, message="Senha entre 4 e 10 caracteres")
	@NotNull(message="O nome é obrigatório")
	private String senha;
	
	@OneToMany
	private List<Cliente>clientes;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
}
