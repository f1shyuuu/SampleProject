package controllers;

import java.util.List;

import models.CartItemJSON;
import models.OrderAndCartItemBean;
import models.OrderBean;

import com.opensymphony.xwork2.ActionSupport;

import dao.SingletonDao;

public class AdminAction extends ActionSupport  {
	
	private int id;
	private String status;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String change(){
		SingletonDao.getInstance().changeStatus(id, status);
		return SUCCESS;
	}
	
	public List<OrderBean> getAllOrders(){
		return SingletonDao.getInstance().listOrdersOnly();
	}
	
	public String listAll(){
		return SUCCESS;
	}
	
	public OrderBean getOrder() {
		return SingletonDao.getInstance().getOrderById(id);
	}
	
	public List<CartItemJSON> getCartItems(){
		return SingletonDao.getInstance().getItemByOrder(id);
	}
	
	public String orderDetails() {
		return SUCCESS;
	}


}
