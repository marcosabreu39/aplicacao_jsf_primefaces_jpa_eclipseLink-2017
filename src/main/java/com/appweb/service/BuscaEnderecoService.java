package com.appweb.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.appweb.model.Endereco;
import com.appweb.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Stateless
public class BuscaEnderecoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	Util util;

	Client client;

	String json;

	WebResource webResource;

	public Endereco buscarEnderecoViaCep(Endereco endereco2) {

		Endereco endereco = new Endereco();

		try {

			if (endereco2.getCep() != null && !endereco2.getCep().equals("")) {

				json = ("https://viacep.com.br/ws/").concat(endereco2.getCep()).concat("/json/");

				client = Client.create();

				webResource = client.resource(new String(json.getBytes("UTF-8")));

				final GsonBuilder gsonBuilder = new GsonBuilder();

				final Gson gson = gsonBuilder.create();

				endereco = gson.fromJson(webResource.get(String.class), Endereco.class);

			}

		} catch (JsonParseException | UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		return endereco;
	}

}
