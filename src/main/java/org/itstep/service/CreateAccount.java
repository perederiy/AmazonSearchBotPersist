package org.itstep.service;

import org.itstep.model.Account;

public class CreateAccount {

	public static Account  createAccountAmazon() {
		
		Account account = new Account();
		
		String name = getName();
		account.setName(name);
		
		String email = name + (10000 +(int)(Math.random() * (100000-10000))) + "@" + getDomain();
		account.setEmailUser(email);
		
		String password = name.toUpperCase() + (1000 + (int)(Math.random() * (10000-1000)));
		account.setPassword(password);
		
		return account;
	}

	private static String getDomain() {
		
		String domain = "";
		
		switch(1 + (int)(Math.random() * 5)) {
		
		case 1: domain = "ukr.net";
			break;
		case 2: domain = "mail.ru";
			break;
		case 3: domain = "i.ua";
			break;
		case 4: domain = "ua.fm";
			break;
		default: domain = "email.ua"; 
			break;
		}
		return domain;
	}

	private static String getName() {
		
		String name = "";
		
		switch(1 + (int)(Math.random() * 5)) {
		
		case 1: name = "Alex";
			break;
		case 2: name = "Natalia";
			break;
		case 3: name = "Olga";
			break;
		case 4: name = "Misha";
			break;
		default: name = "Vitaliy";
			break;
		}
		return name;
	}
}
