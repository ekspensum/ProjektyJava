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
	@NamedQuery(name="productsByName", query="SELECT pr FROM Product pr WHERE pr.name LIKE :name"),
	@NamedQuery(name="productsByQuantity", query="SELECT pr FROM Product pr WHERE pr.unitsInStock <= :quantity"),
	@NamedQuery(name="productsByIdRange", query="SELECT pr FROM Product pr WHERE pr.id BETWEEN :idFrom AND :idTo"),
	@NamedQuery(name="getAllMainBoardXml", query="SELECT p FROM ProductCategory pc INNER JOIN pc.product p INNER JOIN pc.category c WHERE c.id = 24"),
	@NamedQuery(name="getAllRamMemoryXml", query="SELECT p FROM ProductCategory pc INNER JOIN pc.product p INNER JOIN pc.category c WHERE c.id = 25"),
	@NamedQuery(name="getAllMainBoardJson", query="SELECT p.id, p.name, p.description, p.price, p.unitsInStock, p.productImage FROM ProductCategory pc INNER JOIN pc.product p INNER JOIN pc.category c WHERE c.id = 24"),
	@NamedQuery(name="getAllRamMemoryJson", query="SELECT p.id, p.name, p.description, p.price, p.unitsInStock, p.productImage FROM ProductCategory pc INNER JOIN pc.product p INNER JOIN pc.category c WHERE c.id = 25"),
	@NamedQuery(name="getAllProcessorsJson", query="SELECT p.id, p.name, p.description, p.price, p.unitsInStock, p.productImage, c.name FROM ProductCategory pc INNER JOIN pc.product p INNER JOIN pc.category c WHERE c.id = 27"),
	@NamedQuery(name="getAllHardDisksJson", query="SELECT p.id, p.name, p.description, p.price, p.unitsInStock, p.productImage, c.name FROM ProductCategory pc INNER JOIN pc.product p INNER JOIN pc.category c WHERE c.id = 26"),
	@NamedQuery(name="getProductByIdJson", query="SELECT p.id, p.name, p.description, p.price, p.unitsInStock, p.productImage FROM Product p WHERE p.id = :id"),
	@NamedQuery(name="productsByIdRangeJson", query="SELECT pc FROM ProductCategory pc INNER JOIN pc.product pr INNER JOIN pc.category c WHERE c.id IN(24,25,26,27) AND pr.id BETWEEN :idFrom AND :idTo"),
	@NamedQuery(name="productsByNameJson", query="SELECT pr.id, pr.name, pr.description, pr.price, pr.unitsInStock, pr.productImage FROM Product pr WHERE pr.name LIKE :name")
})
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	protected String name;
	protected String description;
	protected double price;
	protected int unitsInStock;
	@Lob
	private byte[] productImage;
	@Transient
	protected String base64Image;
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
