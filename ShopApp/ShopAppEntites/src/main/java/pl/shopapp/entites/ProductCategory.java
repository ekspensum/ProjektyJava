package pl.shopapp.entites;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ProductCategory
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="productCategoriesQuery", query="SELECT c FROM ProductCategory pc INNER JOIN pc.category c INNER JOIN pc.product p WHERE p = :product"),
	@NamedQuery(name="getProductCategoryQuery", query="SELECT pc FROM ProductCategory pc WHERE pc.category = :category AND pc.product = :product")
})
public class ProductCategory implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	private Product product;
	
	@OneToOne
	private Category category;
	
	private static final long serialVersionUID = 1L;

	public ProductCategory() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}   
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
   
}
