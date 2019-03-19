package pl.shopapp.webservice.model;

public class ProductDataJson {
	
	private String name;
	private String description;
	private double price;
	private int unitsInStock;
	private byte [] image;
	private int category1Id;
	private int category2Id;
	private int previousCategory1Id;
	private int previousCategory2Id;
	private String login;
	private String password;
	private int idProduct;
	private int imageSize;
	
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
	public int getCategory1Id() {
		return category1Id;
	}
	public void setCategory1Id(int category1Name) {
		this.category1Id = category1Name;
	}
	public int getCategory2Id() {
		return category2Id;
	}
	public void setCategory2Id(int category2Name) {
		this.category2Id = category2Name;
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
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	public int getPreviousCategory1Id() {
		return previousCategory1Id;
	}
	public void setPreviousCategory1Id(int previousCategory1Id) {
		this.previousCategory1Id = previousCategory1Id;
	}
	public int getPreviousCategory2Id() {
		return previousCategory2Id;
	}
	public void setPreviousCategory2Id(int previousCategory2Id) {
		this.previousCategory2Id = previousCategory2Id;
	}
	public int getImageSize() {
		return imageSize;
	}
	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}
	
	
}
