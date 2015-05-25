package models;

public class CartItemBean {
	private int price;
	private String title;
	private long id;
	private int quantity;
	
	public CartItemBean(int price, String title, long id, int quantity) {
		super();
		this.price = price;
		this.title = title;
		this.id = id;
		this.quantity = quantity;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "CartItemBean [price=" + price + ", title=" + title + ", id="
				+ id + "]";
	}
	public CartItemBean(int price, String title, long id) {
		super();
		this.price = price;
		this.title = title;
		this.id = id;
	}
	

}
