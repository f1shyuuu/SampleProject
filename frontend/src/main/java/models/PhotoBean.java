package models;

import java.util.List;


public class PhotoBean {
	private int farm;
	private int server;
	private long  id;
	private String secret;
	private String title;
	private String url;
	private List<String> tags;
	private int price;
	
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getFarm() {
		return farm;
	}
	public void setFarm(int farm) {
		this.farm = farm;
	}
	public int getServer() {
		return server;
	}
	public void setServer(int server) {
		this.server = server;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
