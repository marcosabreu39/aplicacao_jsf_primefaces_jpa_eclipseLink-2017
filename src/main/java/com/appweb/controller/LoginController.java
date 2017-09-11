package com.appweb.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.appweb.dao.UsuarioDao;
import com.appweb.model.Usuario;
import com.appweb.util.Util;

@SessionScoped
@ManagedBean(name="loginBean")
public class LoginController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private Util util;

	@EJB
	private UsuarioDao usuarioDao;
	
	private Usuario usuario;
	
	private String login;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@PostConstruct
	public void init() {
		
		usuario = new Usuario();
		
	}
	
	public String acessar() {
		String retorno = null;

		try {

			if (usuario.getLogin() != null && !usuario.getLogin().equals("") 
					&& usuario.getSenha() != null && !usuario.getSenha().equals("")) {

				if (usuarioDao.verificarDadosLogon(usuario)) {

					util.msgInfoGrowl("Logado com sucesso", "Bem-vindo " + usuario.getLogin(), "formGrowl");

					login = usuario.getLogin();

					util.colocarNaSessao(login);

					FacesContext context = FacesContext.getCurrentInstance();
					context.getExternalContext().getFlash().setKeepMessages(true);

					retorno = "/view/index.xhtml?faces-redirect=true";

				} else {
					
					util.msgInfoGrowl("Acesso negado", "Credenciais inválidas ", "acessa");

					
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	public String sair() {

		util.encerrarSessao();

		login = null;

		util.msgInfoGrowl("Logout concluído", "Sessão encerrada", "formGrowl");

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		return "/view/index.xhtml?faces-redirect=true";

	}

}
