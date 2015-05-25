package models;

public class UserBean {
	private String name;
	private String password;
	private String role;
	
	public UserBean(String n,String p,String r){
		name=n;
		password=p;
		role=r;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String toString(){
		return "My name is "+name;
	}

}
