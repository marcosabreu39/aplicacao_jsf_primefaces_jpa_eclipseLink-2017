package com.appweb.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.appweb.dao.ClienteDao;
import com.appweb.model.Cliente;

@Stateless
@LocalBean
public class Util implements Serializable {

	/**
	 * 
	 */

	@EJB
	ClienteDao clienteDao;

	private static final long serialVersionUID = 1L;

	public void msgInfoGrowl(String detalhes, String mensagem, String componente) {
		try {

			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, detalhes, mensagem);
			FacesContext.getCurrentInstance().addMessage(componente, fm);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void msgErroGrowl(String detalhes, String mensagem, String componente) {
		try {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, detalhes, mensagem);
			FacesContext.getCurrentInstance().addMessage(componente, fm);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void msgErro(String mensagem, String idComponente) {
		try {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null);
			FacesContext.getCurrentInstance().addMessage(idComponente, fm);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void atualizarComponente(String idComponente) {
		try {
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(idComponente);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void encerrarSessao() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void colocarNaSessao(String login) {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session = session == null ? (HttpSession) fc.getExternalContext().getSession(true) : session;
			session.setAttribute("login", login);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public String pegarNaSessao() {
		String login = null;
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session = session == null ? (HttpSession) fc.getExternalContext().getSession(true) : session;
			login = (String) session.getAttribute("login");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return login;
	}

	public void colocarBeanNaSessao(Cliente cliente) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cliente", cliente);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public Cliente pegarBeanNaSessao() {
		Cliente cliente = new Cliente();
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session = session == null ? (HttpSession) fc.getExternalContext().getSession(true) : session;
			Object clienteObj = (Cliente) session.getAttribute("cliente");

			if (clienteObj instanceof Cliente) {
				cliente = (Cliente) clienteObj;
			}

		} catch (ClassCastException e) {

			e.printStackTrace();
		}

		return cliente;
	}

	public Date gerarDataEhoraAtual() {
		Date data = new Date(System.currentTimeMillis());
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		String dataEhoraCadastroSTR = fmt.format(data);
		Date dataEhoraAtual = null;
		try {
			dataEhoraAtual = (Date) fmt.parse(dataEhoraCadastroSTR);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return dataEhoraAtual;
	}

	public Date calcularDataMinimaPermitida(Date data) {

		Date dataMinima = null;

		try {
			Calendar instance = Calendar.getInstance();

			instance.setTime(data);

			instance.add(Calendar.YEAR, -16);

			SimpleDateFormat isoFormat = new SimpleDateFormat("dd-MMM-yyyy");

			String DataMinimaStr = isoFormat.format(instance.getTime());

			dataMinima = isoFormat.parse(DataMinimaStr);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		return dataMinima;

	}

	public boolean emailApto(String email, String idComponente) {
		boolean apto = false;

		try {
			if (email != null && !email.equals("")) {

				if (clienteDao.emailNaoCadastrado(email)) {

					apto = true;

				} else {

					msgErro("E-mail já cadastrado.", idComponente);

				}

			} else {

				msgErroGrowl("Erro", "Erro para checar o e-mail", idComponente);

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return apto;

	}

}
