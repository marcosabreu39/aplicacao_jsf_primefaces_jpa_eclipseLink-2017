package com.appweb.dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface GenericDao<T> {
	
	void persist(T t);

	void merge(T t);

	void remove(T t);
	
	T find(long id);
	
	List<T> findAll();
}

