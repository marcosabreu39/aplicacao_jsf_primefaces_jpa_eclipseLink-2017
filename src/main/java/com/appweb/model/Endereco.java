
package com.appweb.model;

import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Endereco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@SerializedName("cep")
	@Expose
	@NotNull(message = "Campo obrigatório")
	@Pattern(regexp = "^\\d{5}-\\d{3}$", message = "O CEP com a formatação incorreta")
	@Column(nullable = false)
	private String cep;

	@SerializedName("logradouro")
	@Expose
	@Column
	private String logradouro;

	@SerializedName("complemento")
	@Expose
	@Column
	private String complemento;

	@SerializedName("bairro")
	@Expose
	@Column
	private String bairro;

	@SerializedName("localidade")
	@Expose
	@Size(min = 4, max = 40, message = "A cidade entre 4 e 40 caracteres")
	@NotNull(message = "A cidade é obrigatória")
	@Column(nullable = false)
	private String cidade;

	@SerializedName("uf")
	@Expose
	@NotNull(message = "Campo obrigatório")
	@Column(nullable = false)
	private String uf;

	public Endereco() {

	}

	public Endereco(String cep, String logradouro, String complemento, String bairro, String localidade, String uf,
			String unidade, String ibge, String gia) {
		super();
		this.cep = cep;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = localidade;
		this.uf = uf;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return cidade;
	}

	public void setLocalidade(String localidade) {
		this.cidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	 @Override public String toString() { return
	  ToStringBuilder.reflectionToString(this); }
	  
	  @Override public int hashCode() { return new
	  HashCodeBuilder().append(cep).append(logradouro).append(complemento).append(
	  bairro) .append(cidade).append(uf).toHashCode(); }
	 

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if ((other instanceof Endereco) == false) {
			return false;
		}
		
		Endereco rhs = ((Endereco) other);
		return new EqualsBuilder().append(cep, rhs.cep).append(logradouro, rhs.logradouro)
				.append(complemento, rhs.complemento).append(bairro, rhs.bairro).append(cidade, rhs.cidade)
				.append(uf, rhs.uf).isEquals();
	}

}