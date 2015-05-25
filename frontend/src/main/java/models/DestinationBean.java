package models;

public class DestinationBean {
	private String city;
	private int cost;
	public String getCity() {
		return city;
	}
	public DestinationBean(String city, int cost) {
		this.city = city;
		this.cost = cost;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
}
