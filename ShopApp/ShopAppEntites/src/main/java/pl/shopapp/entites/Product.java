package pl.shopapp.entites;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Product
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="productsByCategory", query="SELECT p FROM ProductCategory pc INNER JOIN pc.product p INNER JOIN pc.category c WHERE c.id = ?1"),
	@NamedQuery(name="productsByName", query="SELECT pr FROM Product pr WHERE pr.name LIKE :name")
})
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String description;
	private double price;
	private int unitsInStock;
	@Lob
	private byte[] productImage;
	private String base64Image;
	private LocalDateTime dateTime;
	
	@OneToOne
	private Operator op;

	private static final long serialVersionUID = 1L;

	public Product() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(int unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	public byte[] getProductImage() {
		return productImage;
	}

	public void setProductImage(byte[] productImage) {
		this.productImage = productImage;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Operator getOp() {
		return op;
	}

	public void setOp(Operator op) {
		this.op = op;
	}

	public String getBase64Image() {
		return Base64.getEncoder().encodeToString(this.productImage);
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

}
