package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import models.CartItemBean;
import models.CartItemJSON;
import models.DestinationBean;
import models.OrderAndCartItemBean;
import models.OrderBean;
import models.UserBean;

public class SingletonDao {
	private DataSource ds;
	private static SingletonDao sd = null;

	public static SingletonDao getInstance() {
		if (sd == null) {
			try {
				sd = new SingletonDao();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sd;
	}

	private SingletonDao() throws Exception {
		try {
			Context iniCtx = new InitialContext();
			Context env = (Context) iniCtx.lookup("java:comp/env");
			ds = (DataSource) env.lookup("jdbc/Assignment2");
		} catch (NamingException e) {
			throw new Exception("cannot find database");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List<UserBean> getUsers() {
		List<UserBean> u = new ArrayList<UserBean>();
		String sql = "select * from users";
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				u.add(new UserBean(rs.getString("user_name"), rs
						.getString("user_pass"), "customer"));
			}

			rs.close();
			ps.close();
			conn.close();
			return u;
		} catch (Exception e) {
			return null;
		}
	}

	public List<DestinationBean> getDestination() {
		List<DestinationBean> u = new ArrayList<DestinationBean>();
		String sql = "select * from destination";
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				u.add(new DestinationBean(rs.getString("city"), rs
						.getInt("cost")));
			}
			rs.close();
			ps.close();
			conn.close();
			return u;
		} catch (Exception e) {
			return null;
		}

	}

	public void addItem(int orderId, String title, int quantity, int price) {
		String sql = "insert into `cartitem` (`order_id`,`title`,`quantity`,`price`) values (?,?,?,?) ";
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, orderId);
			ps.setString(2, title);
			ps.setInt(3, quantity);
			ps.setInt(4, price);
			// System.out.println(ps);
			ps.executeUpdate();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// updating order status
	public void changeStatus(int orderId, String nextStatus) {
		String sql = "update `order` set status = ? WHERE id = ?";
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, nextStatus);
			ps.setInt(2, orderId);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<CartItemJSON> getItemByOrder(int id) {
		String sql = "select * from cartItem where order_id=?";
		List<CartItemJSON> result = new ArrayList<CartItemJSON>();
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				CartItemJSON c = new CartItemJSON(rs.getString("title"),
						rs.getInt("quantity"), rs.getInt("price"));
				result.add(c);
			}
			rs.close();
			ps.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}

	}

	public List<OrderAndCartItemBean> listAllOrders() {
		List<OrderAndCartItemBean> result = new ArrayList<OrderAndCartItemBean>();
		String sql = "SELECT order_id, user, title, quantity, price, destination, status, shipping_fee, final_cost  FROM `order` as o join cartitem as c on o.id=c.order_id Order by user;";
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.add(new OrderAndCartItemBean(new OrderBean(rs
						.getInt("order_id"), rs.getString("user"), rs
						.getString("destination"), rs.getString("status"), rs
						.getInt("shipping_fee"), rs.getInt("final_cost")),
						new CartItemBean(rs.getInt("price"), rs
								.getString("title"), 0, rs.getInt("quantity"))));
			}

			rs.close();
			ps.close();
			conn.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	// get order information only
	public List<OrderBean> listOrdersOnly() {
		List<OrderBean> result = new ArrayList<OrderBean>();
		String sql = "SELECT id, user, destination, status, shipping_fee, final_cost  FROM `order`;";
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.add(new OrderBean(rs.getInt("id"), rs.getString("user"),
						rs.getString("destination"), rs.getString("status"), rs
								.getInt("shipping_fee"), rs
								.getInt("final_cost")));
			}
			rs.close();
			ps.close();
			conn.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public List<OrderAndCartItemBean> listUserOrders(String user) {
		List<OrderAndCartItemBean> result = new ArrayList<OrderAndCartItemBean>();
		String sql = "SELECT order_id, user, title, quantity, price, destination, status, shipping_fee, final_cost  FROM `order` as o join cartitem as c on o.id=c.order_id  where user = ? Order by user";
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.add(new OrderAndCartItemBean(new OrderBean(rs
						.getInt("order_id"), rs.getString("user"), rs
						.getString("destination"), rs.getString("status"), rs
						.getInt("shipping_fee"), rs.getInt("final_cost")),
						new CartItemBean(rs.getInt("price"), rs
								.getString("title"), 0, rs.getInt("quantity"))));
			}

			rs.close();
			ps.close();
			conn.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public List<OrderBean> getOrderByUser(String u) {
		String sql = "select *  from `order` where `user`=?";
		List<OrderBean> result = new ArrayList<OrderBean>();
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u);
			ResultSet rs = ps.executeQuery();
			// System.out.println(rs);
			while (rs.next()) {
				OrderBean o = new OrderBean(rs.getInt("id"),
						rs.getString("user"), rs.getString("destination"),
						rs.getString("status"), rs.getInt("shipping_fee"),
						rs.getInt("final_cost"));
				result.add(o);
			}
			rs.close();
			ps.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}
	}

	// Retrieves order information by orderId
	public OrderBean getOrderById(int id) {
		OrderBean result = null;
		String sql = "SELECT id, user, destination, status, shipping_fee, final_cost  FROM `order` WHERE id = ? ;";
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result = (new OrderBean(rs.getInt("id"), rs.getString("user"),
						rs.getString("destination"), rs.getString("status"),
						rs.getInt("shipping_fee"), rs.getInt("final_cost")));
			}
			rs.close();
			ps.close();
			conn.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	
	public int getPreviousOrderID()
	{
		Context iniCtx;
		int maxorderid = 0;
		try {
			iniCtx = new InitialContext();
			
			Context env = (Context) iniCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) env.lookup("jdbc/Assignment2");
			
			//get the cost for shipping to a city
			String query = "select max(id) from `order`";
			
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			//String city = "something";
			
			//ps.setString(1, city);
			
			ResultSet rs = ps.executeQuery();
			//System.out.println("given city is "+city);
			//System.out.println("given quantity is "+quantity);
			while(rs.next())
			{
				//cost = Integer.parseInt(rs.getString("cost"));
				maxorderid = rs.getInt("max(id)");
				System.out.println("~~~~~~~~~~~~~~~~~~~ max order id is "+maxorderid+"~~~~~~~~~~~~~~~~~~~");
			}			
		} 
		catch (NamingException | SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		return maxorderid;
	}

	public void addOrder(int orderId,String user,String destination,String status, int shippingFee, int finalCost)
	{
		String sql="insert into `order` (`id`,`user`,`destination`,`status`,`shipping_fee`,`final_cost`) values (?,?,?,?,?,?) ";
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, orderId);
			ps.setString(2, user);
			ps.setString(3, destination);
			ps.setString(4, status);
			ps.setInt(5, shippingFee);
			ps.setInt(6, finalCost);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
