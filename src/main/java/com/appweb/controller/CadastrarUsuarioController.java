package com.appweb.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.appweb.dao.UsuarioDao;
import com.appweb.model.Usuario;
import com.appweb.util.Util;

@ViewScoped
@ManagedBean(name="cadastrarUsuarioBean")
public class CadastrarUsuarioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private Util util;

	@EJB
	private UsuarioDao usuarioDao;
	
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@PostConstruct
	public void init() {
		usuario = new Usuario();
	}

	public String cadastrarUsuario() {

		String retorno = null;

		try {
			if (usuario != null && !usuario.equals("")) {

				usuarioDao.persist(usuario);

				util.msgInfoGrowl("Sucesso", "Cadastrado com sucesso", "formGrowl");

				FacesContext context = FacesContext.getCurrentInstance();
				context.getExternalContext().getFlash().setKeepMessages(true);

				retorno = "/view/index.xhtml?faces-redirect=true";

			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return retorno;

	}

}
