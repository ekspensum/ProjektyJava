package pl.shopapp.entites;

import java.io.Serializable;
import java.lang.String;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Category
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="listCategory", query="SELECT c FROM Category c"),
	@NamedQuery(name="findIdCategory", query="SELECT c.id FROM Category c WHERE c.name = :name")
})
public class Category implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private LocalDateTime dateTime;
	@Lob
	private byte[] categoryImage;
	@OneToOne
	private Operator op;
	@Transient
	private String base64Image;

	private static final long serialVersionUID = 1L;

	public Category() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public byte[] getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(byte[] categoryImage) {
		this.categoryImage = categoryImage;
	}

	public Operator getOp() {
		return op;
	}

	public void setOp(Operator op) {
		this.op = op;
	}

	public String getBase64Image() {
		return Base64.getEncoder().encodeToString(this.categoryImage);
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

}
