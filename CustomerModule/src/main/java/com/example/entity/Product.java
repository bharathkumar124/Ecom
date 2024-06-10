package com.example.entity;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Entity
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String description;

	private BigDecimal price;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "seller_user_id")
	private Seller seller;

	private int quantity;

	private String status;
	@Lob
	@Column(name = "image1",columnDefinition = "LONGBLOB")
	private byte[] image1;
	@Lob
	@Column(name = "image2",columnDefinition = "LONGBLOB")
	private byte[] image2;
	@Lob
	@Column(name = "image3",columnDefinition = "LONGBLOB")
	private byte[] image3;
	

}
