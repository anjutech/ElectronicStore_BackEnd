package com.ElectronicStore.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="products")
public class Product {

	@Id
	private String productId;
	private String title;
	private String description;
	private int price;
	private int discountedPrice;
	private int quantity;
	private Date addeddate;
	private boolean live;
	private boolean stock;
	private String productImageName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="product_id_category")
	private Categories category;
}
