package com.ElectronicStore.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="categories")
public class Categories {

	@Id
	private String categoryId;
	private String title;
	private String description;
	private String coverImage;
	
	@OneToMany(mappedBy = "category",cascade = CascadeType.REMOVE ,fetch =FetchType.LAZY )
	private List<Product> products = new ArrayList<>();
}
