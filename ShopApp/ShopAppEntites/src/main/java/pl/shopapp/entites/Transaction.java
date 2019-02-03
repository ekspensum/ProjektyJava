package pl.shopapp.entites;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;


/**
 * Entity implementation class for Entity: Order
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="findTransactionsQuery", query="SELECT tr FROM Transaction tr WHERE tr.customer = :customer AND tr.dateTime BETWEEN :dateFrom AND :dateTo")
})
@Table(name = "\"Transaction\"") //for integration test with Derby database
public class Transaction implements Serializable {
   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@OneToOne
	private Product product;
	@OneToOne
	private Customer customer;
	private String productName;
	private int quantity;
	private double price;
	private LocalDateTime dateTime;
	private static final long serialVersionUID = 1L;

	public Transaction() {
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
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}   
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
   
}
