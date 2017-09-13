package com.appweb.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.appweb.dao.ClienteDao;
import com.appweb.model.Cliente;
import com.appweb.model.Endereco;
import com.appweb.service.BuscaEnderecoService;
import com.appweb.util.Util;
import com.sun.jersey.api.client.UniformInterfaceException;

@ViewScoped
@ManagedBean(name = "consultarClienteBean")
public class ConsultarClienteController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	BuscaEnderecoService service;

	@EJB
	private Util util;

	@EJB
	private ClienteDao clienteDao;

	private Endereco endereco;

	private Cliente cliente;

	private Cliente detalheCli;

	private List<Cliente> clientes;

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getDetalheCli() {
		return detalheCli;
	}

	public void setDetalheCli(Cliente detalheCli) {
		this.detalheCli = detalheCli;
	}

	@PostConstruct
	public void init() {

		endereco = new Endereco();

		cliente = new Cliente();

	}

	public void buscarTodosClientes() {

		try {

			clientes = clienteDao.findAll();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void buscarCliente() {

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String detalhesDoCliente(Cliente cliente) {

		try {

			util.colocarBeanNaSessao(cliente);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return "/view/private/detalhes-cliente.xhtml?faces-redirect=true";

	}

	public String clienteParaEditar(Cliente cliente) {

		try {

			util.colocarBeanNaSessao(cliente);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return "/view/private/editar-cliente.xhtml?faces-redirect=true";

	}

	public void clienteSelecionado() {

		Cliente client = new Cliente();

		try {

			if (cliente.getNome() == null || cliente.getNome().equals("")) {

				Object clienteObj = util.pegarBeanNaSessao();

				client = (Cliente) clienteObj;

				setCliente(client);

				client = new Cliente();

				util.colocarBeanNaSessao(client);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public Endereco buscarCep(Endereco endereco) {

		try {

			if (endereco.getCep().matches("^\\d{5}-\\d{3}$")) {

				endereco = service.buscarEnderecoViaCep(endereco);

				if (endereco.getCidade() != null && endereco.getUf() != null && !endereco.getCidade().equals("")
						&& !endereco.getUf().equals("")) {

					cliente = util.pegarBeanNaSessao();

					cliente.setEndereco(endereco);

					util.colocarBeanNaSessao(cliente);

					util.atualizarComponente("formEditCli");

				}

			}

		} catch (UniformInterfaceException e) {

			e.printStackTrace();
		}

		return endereco;

	}

	public void atualizarCliente(Cliente cliente) {

		try {

			if (cliente.getNome() != null && !cliente.getNome().equals("")) {

				if (util.emailApto(cliente.getEmail(), "email")) {

					clienteDao.merge(cliente);

					util.colocarBeanNaSessao(cliente);

					util.msgInfoGrowl("Sucesso", "Atualização cadastral concluída", "msgGrowl");

				}

			} else {

				util.msgErroGrowl("Erro", "Não foi possível atualizar", "editCli");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void excluirCliente(Cliente cliente) {
		

		try {

			if (cliente.getNome() != null && !cliente.getNome().equals("")) {

				clienteDao.remove(cliente);
				
				clientes.clear();
				
				util.msgInfoGrowl("Sucesso", "Cliente removido", "consuCliGrowl");
				
			} else {

				util.msgErroGrowl("Erro", "Não foi possível remover o cliente", "consuCliGrowl");

			}
			

		} catch (Exception e) {

			e.printStackTrace();
		}
		

	}

	public Date dataMin() {

		Date min = null;

		try {

			min = util.calcularDataMinimaPermitida(util.gerarDataEhoraAtual());

		} catch (Exception e) {

			e.printStackTrace();
		}

		return min;

	}

	public Date dataMax() {

		Date max = null;

		try {

			max = util.gerarDataEhoraAtual();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return max;
	}

}
