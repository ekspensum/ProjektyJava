package pl.aticode.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter @Setter
@NamedQueries({
    @NamedQuery(name = "findProductByCategory", query = "SELECT product FROM Product product WHERE product.category.id = :categoryId")
})
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	private BigDecimal price;
	private int quantity;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Category category;
		
	public Product(Long id, String name, BigDecimal price, int quantity, Category category) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.category = category;
	}

	public Product() {

	}
	
	
}
