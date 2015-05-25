package controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import models.CartItemJSON;
import models.OrderAndCartItemBean;
import models.OrderBean;
import models.UserBean;

import com.opensymphony.xwork2.ActionSupport;

import dao.SingletonDao;

public class Login extends ActionSupport implements SessionAware {
	private String userName;
	private String password;
	private Map<String, Object> session;
	private static final long serialVersionUID = 1L;
	private String welcome;

	public String execute() {
		SingletonDao db = SingletonDao.getInstance();
		List<UserBean> users = db.getUsers();
		Iterator<UserBean> itr = users.iterator();
		Boolean u = false;
		Boolean p = false;

		// List<CartItemJSON> aa=SingletonDao.getInstance().getItemByOrder(1);
		// System.out.println("my items are: "+aa);
		// List<OrderBean>
		// aaa=SingletonDao.getInstance().getOrderByUser("guichi");
		// System.out.println("my order is : "+aaa);

		while (itr.hasNext()) {
			UserBean usr = itr.next();
			if ((usr.getName()).equals(userName)) {
				u = true;
				if ((usr.getPassword()).equals(password)) {
					p = true;
					String welcom = "welcome to visit us " + usr.getName();
					session.put("welcome", welcom);
					session.put("user", userName);
					welcome = welcom;
				}
			}
			// System.out.println("u is "+u+" and p is "+p);

		}
		if (!u || !p) {
			return INPUT;
		} else {
			if (userName.equals("admin")) {
				return "admin";
			}
			Boolean login = true;
			session.put("login", login);
			return SUCCESS;
		}
	}

	// ////////////////////////////////////////////////////////////////////
	// ///////Getters and Setters//////////////////////////////////////////
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWelcome() {
		return welcome;
	}

	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;

	}

}
