package controllers;

import java.util.List;
import java.util.Map;

import models.CartItemJSON;
import models.OrderAndCartItemBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.SingletonDao;

public class OrderAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private List<OrderAndCartItemBean> orders;
	

	public List<OrderAndCartItemBean> getOrders() {
		return orders;
	}


	public void setOrders(List<OrderAndCartItemBean> orders) {
		this.orders = orders;
	}


	public String list()
	{
		Map<String, Object> session = ActionContext.getContext().getSession();
		orders = SingletonDao.getInstance().listUserOrders((String)session.get("user"));
		return SUCCESS;
	}

}
