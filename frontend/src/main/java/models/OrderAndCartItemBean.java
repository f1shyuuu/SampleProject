package models;

public class OrderAndCartItemBean {
	private OrderBean orderBean;
	private CartItemBean cartItemBean;

	public OrderAndCartItemBean(OrderBean orderBean, CartItemBean cartItemBean) {
		super();
		this.orderBean = orderBean;
		this.cartItemBean = cartItemBean;
	}

	public OrderBean getOrderBean() {
		return orderBean;
	}

	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}

	public CartItemBean getCartItemBean() {
		return cartItemBean;
	}

	public void setCartItemBean(CartItemBean cartItemBean) {
		this.cartItemBean = cartItemBean;
	}

}
