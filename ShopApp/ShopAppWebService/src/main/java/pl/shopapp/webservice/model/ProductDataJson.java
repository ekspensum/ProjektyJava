package pl.shopapp.webservice.model;

public class ProductDataJson {
	
	private String name;
	private String description;
	private double price;
	private int unitsInStock;
	private byte [] image;
	private int category1Name;
	private int category2Name;
	private String login;
	private String password;
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
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public int getCategory1Name() {
		return category1Name;
	}
	public void setCategory1Name(int category1Name) {
		this.category1Name = category1Name;
	}
	public int getCategory2Name() {
		return category2Name;
	}
	public void setCategory2Name(int category2Name) {
		this.category2Name = category2Name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
