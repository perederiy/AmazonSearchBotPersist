package org.itstep.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
	
	@Transient
	private String name;
	@Id
	@Column(name = "user_email", length = 80, nullable = false)
	private String emailUser;
	@Column(name = "user_password", length = 80)
	private String password;
	
	public Account() {
	}
	
	
}

