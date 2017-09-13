package com.appweb.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;

import com.appweb.model.Cliente;

@Stateless
@LocalBean
public class ClienteDao implements GenericDao<Cliente>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "appwebPU")
	private EntityManager em;

	@Override
	public void persist(Cliente cliente) {
		try {

			em.persist(cliente);

		} catch (PersistenceException | ConstraintViolationException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void merge(Cliente cliente) {
		try {

			em.merge(cliente);

		} catch (PersistenceException | ConstraintViolationException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void remove(Cliente cliente) {

		try {

			if (em.contains(cliente)) {

				em.remove(cliente);

			} else {

				Cliente c = find(cliente.getId());

				if (!em.contains(c)) {

					em.remove(em.merge(c));

				} else {

					em.remove(c);

				}

			}

		} catch (PersistenceException | ConstraintViolationException e) {

			e.printStackTrace();
		}

	}

	@Override
	public Cliente find(long id) {

		Cliente c = new Cliente();

		try {

			c = em.find(Cliente.class, id);

		} catch (PersistenceException e) {
			e.printStackTrace();

		}

		return c;
	}

	@Override
	public List<Cliente> findAll() {

		List<Cliente> retorno = null;

		try {

			String jpql = "SELECT c FROM Cliente c";

			TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

			retorno = query.getResultList();

		} catch (PersistenceException e) {

			e.printStackTrace();
		}

		return retorno;
	}

	public boolean emailNaoCadastrado(String email) {
		boolean valido = false;

		try {

			String jpql = "SELECT c FROM Cliente c WHERE c.email = :email";

			TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

			query.setParameter("email", email);

			List<Cliente> c = query.getResultList();

			valido = c.isEmpty() ? true : valido;

		} catch (PersistenceException e) {

			e.printStackTrace();
		}

		return valido;
	}

}
