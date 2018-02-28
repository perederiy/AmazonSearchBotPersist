package org.itstep.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CreateEntityManager {
	
	private static EntityManagerFactory emf = null;
	private static EntityManager em = null;

	public static EntityManager getSession() {
		
		emf = Persistence.createEntityManagerFactory("amazon_hibernate_persist_db");
		em = emf.createEntityManager();
		
		return em;
	}
	
	public static void closeSession() {
		em.close();
		emf.close();
	}
}

