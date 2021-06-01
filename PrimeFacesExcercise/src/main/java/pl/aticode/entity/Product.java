package pl.aticode.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Base64;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter @Setter
@NamedQueries({
    @NamedQuery(name = "findProductByCategory", query = "SELECT product FROM Product product WHERE product.category.id = :categoryId"),
    @NamedQuery(name = "getTopProducts", query = "SELECT product FROM Product product WHERE product.id IN (:topProductId)")
})
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
    @Size(min = 0, max = 2048)
    @Type(type = "text")
	private String description;
    
	private BigDecimal price;
	private int quantity;
	
	@Lob
	private byte [] photo;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Category category;
		
	public Product(String name, String description, BigDecimal price, int quantity, Category category) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.category = category;
	}

	public Product() {

	}
	
	public String getBase64Image() {
		return Base64.getEncoder().encodeToString(this.photo);
	}
}
