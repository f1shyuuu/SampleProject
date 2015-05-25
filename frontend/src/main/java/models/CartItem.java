package models;

public class CartItem {
	private int quantity=1;
	private CartItemBean bean;
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public CartItemBean getBean() {
		return bean;
	}
	public void setBean(CartItemBean bean) {
		this.bean = bean;
	}
	public void add()
	{
		quantity++;
	}
	public void remove()
	{
		quantity--;
	}
	

}
