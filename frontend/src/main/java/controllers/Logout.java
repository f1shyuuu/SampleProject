package controllers;

import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;

public class Logout extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = 1L;
	private Map<String,Object> session;
	public String execute(){
		System.out.println("before clearing : "+session);
		session.put("welcome", null);
		session.put("user", null);
		session.put("login",false);
		session.put("CART", null);
		System.out.println("after clearing : "+session);
		//session.clear();
	
		return SUCCESS;
	}
	@Override
	public void setSession(Map<String, Object> arg0) {
		session=arg0;	
	}

}
