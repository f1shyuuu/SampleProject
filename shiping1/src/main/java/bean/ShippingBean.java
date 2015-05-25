package bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class ShippingBean {
	private String message;
	private int shippingPrice;
	
	public ShippingBean()
	{
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getShippingprice() {
		return shippingPrice;
	}

	public void setShippingprice(int shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	

}
