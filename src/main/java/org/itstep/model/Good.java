package org.itstep.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQuery;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "good")
public class Good {

	@Column(name = "name", length = 150)
	private String name;
	@Column(name = "url", length = 150)
	private String url;
	@Column(name = "price", length = 30)
	private String price;
	@Id
	@Column(name = "asin", length = 10, nullable = false)
	private String asinGood;
	
	public Good() {
	}
}
