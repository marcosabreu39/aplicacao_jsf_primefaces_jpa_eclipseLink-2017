package com.appweb.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.appweb.model.Usuario;

@Stateless
@LocalBean
public class UsuarioDao implements GenericDao<Usuario>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "appwebPU")	
	private EntityManager em;

	@Override
	public void persist(Usuario usuario) {
		try {

			em.persist(usuario);

		} catch (PersistenceException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void merge(Usuario usuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Usuario usuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public Usuario find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean verificarDadosLogon(Usuario usuario) {

		boolean autorizado = false;

		try {

			String jpql = "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha";

			Query query = em.createQuery(jpql);

			query.setParameter("login", usuario.getLogin());

			query.setParameter("senha", usuario.getSenha());

			@SuppressWarnings("unchecked")
			List<Usuario> result = query.getResultList();

			autorizado = !result.isEmpty() ? true : autorizado;

		} catch (PersistenceException e) {

			e.printStackTrace();
		}
		return autorizado;
	}

}
