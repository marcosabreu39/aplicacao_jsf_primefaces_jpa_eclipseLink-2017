package com.appweb.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import com.appweb.dao.ClienteDao;
import com.appweb.model.Cliente;
import com.appweb.model.Endereco;
import com.appweb.service.BuscaEnderecoService;
import com.appweb.util.UfsEnum;
import com.appweb.util.Util;
import com.sun.jersey.api.client.UniformInterfaceException;

@ViewScoped
@ManagedBean(name = "cadastrarClienteBean")
public class CadastrarClienteController implements Serializable {

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

	private Cliente cliente;

	private Endereco endereco;

	UfsEnum uf;

	@PostConstruct
	public void init() {

		cliente = new Cliente();

		endereco = new Endereco();

	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<UfsEnum> ufs() {

		List<UfsEnum> getUfs = Arrays.asList(UfsEnum.values());

		return getUfs;

	}

	public Endereco buscarCep() {

		try {

			if (endereco.getCep().matches("^\\d{5}-\\d{3}$")) {

				endereco = service.buscarEnderecoViaCep(endereco);

				if (endereco.getCidade() != null && endereco.getUf() != null && !endereco.getCidade().equals("")
						&& !endereco.getUf().equals("")) {

					util.atualizarComponente("formCadCli");

				}

			}

		} catch (UniformInterfaceException e) {

			e.printStackTrace();
		}

		return endereco;

	}

	public void cadastrarCliente() {

		try {

			if (cliente.getNome() != null && !cliente.getNome().equals("") && cliente.getEmail() != null
					&& !cliente.getEmail().equals("") && cliente.getDataNascimento() != null
					&& endereco.getCidade() != null && endereco.getUf() != null && !endereco.getCidade().equals("")
					&& !endereco.getUf().equals("")) {

				cliente.setEndereco(endereco);

				clienteDao.persist(cliente);

				cliente = new Cliente();

				endereco = new Endereco();

				util.msgInfoGrowl("Sucesso", "Cadastro Concluído", "cadUser");

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
