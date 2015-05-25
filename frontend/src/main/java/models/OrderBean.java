package models;

public class OrderBean {
	private int id;
	private String user;
	private String destination;
	private String status;
	private int shipfee;
	private int total;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getShipfee() {
		return shipfee;
	}
	public void setShipfee(int shipfee) {
		this.shipfee = shipfee;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "OrderBean [id=" + id + ", user=" + user + ", destination="
				+ destination + ", status=" + status + ", shipfee=" + shipfee
				+ ", total=" + total + "]";
	}
	public OrderBean(int id,String user, String destination, String status,
			int shipfee, int total) {
		this.id=id;
		this.user = user;
		this.destination = destination;
		this.status = status;
		this.shipfee = shipfee;
		this.total = total;
	}
	//returns next status, for example returns "shipped" status if previous status was "processing"
	public String getNextStatus(){
		switch (status) {
		case "processing":
			return "shipped";
		case "shipped":
			return "delivered";
		default:
			return "delivered";
		}		
	}	

}
