package org.itstep.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "add_good_to_wishlist_and_cart")
public class AddGood {

	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	@Column(name = "email", length = 150)
	private String email;
	@Column(name = "asin", length =10)
	private String asin;
	@Column(name = "add_to_vishlist", length = 5)
	private String addVishList;
	@Column(name = "time_add_vishlist")
	private long timeVishList;
	@Column(name = "add_to_card", length = 5)
	private String addCard;
	@Column(name = "time_add_card")
	private long timeCard;
	
	public AddGood() {
	}
	
}
