package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Cart;
import models.CartItem;
import models.CartItemBean;
import models.CartItemJSON;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;


import com.opensymphony.xwork2.ActionSupport;

import dao.SingletonDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
//import shipping;

public class CartAction extends ActionSupport  implements SessionAware , ApplicationAware{

	private Map<String,Object> session;
	private Map<String,Object> application;
	private static final long serialVersionUID = 1L;

	private long photoId;
	private List<String> tags;
	private String title;
	private int price;
	private List<CartItemJSON> cartData;
	private String address;
	private String welcome;
	private String message;


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getWelcome() {
		return welcome;
	}

	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}



	public String add()
	{
		CartItemBean bean=new CartItemBean(price,title,photoId);
		Object obj=session.get("CART");
		Cart cart=null;
		if(obj==null)
		{
			cart=new Cart();
			cart.addItem(bean);
			session.put("CART", cart);
		}
		else
		{
			cart=(Cart) obj;
			cart.addItem(bean);
		}
		cartData=new ArrayList<CartItemJSON>();
		for(CartItem i :cart.getItems())
		{
			CartItemJSON c=new CartItemJSON(i.getBean().getTitle(),i.getQuantity(),i.getBean().getPrice());
			cartData.add(c);
		}
		System.out.println(cartData);
		return SUCCESS;
	}
	
	public String remove()
	{
		//System.out.print("the photoId is: "+photoId);
		Object obj=session.get("CART");
		Cart cart=null;
		if(obj==null)
		{

		}
		else
		{
			cart=(Cart)obj;
			cart.removeItem(photoId);
		}
		cartData=new ArrayList<CartItemJSON>();
		for(CartItem i :cart.getItems())
		{
			CartItemJSON c=new CartItemJSON(i.getBean().getTitle(),i.getQuantity(),i.getBean().getPrice());
			cartData.add(c);
		}
		//System.out.println(cartData);
		System.out.println("the cart is : "+session.get("CART"));
		return SUCCESS;
	}
	
	public String clear()
	{
		System.out.println("clear all");
		//Object obj=session.get("CART");
		session.put("CART", null);
		cartData=new ArrayList<CartItemJSON>();
		//System.out.println("the cart is : "+session.get("CART"));
		//cartData.clear();
		//System.out.println("the cart is : "+cartData);
		return SUCCESS;
	}

	
	public String checkout()
	{
		welcome=(String) session.get("welcome");
		System.out.println("the customer had checked out");
		System.out.println("using this project!!!!!");
		//prepare return type
		cartData=new ArrayList<CartItemJSON>();
		Cart cart=(Cart) session.get("CART");
				
		//check if cart is null
		//if not
		if(cart!=null)
		{
			//print order status
			System.out.println("Order state: starting order");
			//initialise quantity required to calculate shipping cost
			int quantity = 0;
			//initialise order ID required for saving order information in DB
			int orderID = 0;//
			//initialise total cost for photos
			int totalPhotosCost = 0;
			//initialise total cost;
			int grandTotal = 0;
			
			//get cart items from session and store it in cartData arraylist
			//also prepare strings out of cart items for sending calling shipping component
			String titles = "";
			String quantities = "";
			String prices = "";
			int j = 0;
			
			for(CartItem i :cart.getItems())
			{				
				CartItemJSON c=new CartItemJSON(i.getBean().getTitle(),i.getQuantity(),i.getBean().getPrice());
				cartData.add(c);
				//count quantity for shipping calculation
				quantity = quantity + c.getQuantity();
				//compute total cost of all photos
				totalPhotosCost = totalPhotosCost + i.getBean().getPrice();
				
				//preparing string information
				String titleToProcess = i.getBean().getTitle();
				String[] tempTitles = titleToProcess.split("\\s+");
				String formattedTitles = "";
				for(int k=0; k<tempTitles.length;k++)
				{
					if(k==0)
					{
						formattedTitles = tempTitles[k];
					}
					else
					{
						formattedTitles = formattedTitles + "___" +tempTitles[k];
					}						
				}

				if(j==0)
				{
					//prepare titles
					titles = formattedTitles;
					//prepare quantities
					quantities = Integer.toString(i.getQuantity());
					//prepare prices	
					prices = Integer.toString(i.getBean().getPrice());
				}				
				else
				{
					//prepare titles
					titles = titles+"||"+formattedTitles;
					//prepare quantities
					quantities = quantities+","+Integer.toString(i.getQuantity());
					//prepare prices	
					prices = prices+","+Integer.toString(i.getBean().getPrice());
				}
				j++;
			}
			System.out.println("titles"+titles);
			System.out.println("quantites"+quantities);
			System.out.println("prices"+prices);
			///Calling the shipping component
			String url = "http://localhost:9000/shipping/rest/shipping?city="+address+"&quantity="+quantity+"&titles="+titles+"&quantities="+quantities+"&prices="+prices;
			String shippingmessage ="";
			int shippingCost = -1;
			System.out.println("url "+url);
			URL callUrl;
			try 
			{
				callUrl = new URL(url);
				HttpURLConnection urlConnection;
				urlConnection = (HttpURLConnection)callUrl.openConnection();
				InputStream urlStream = urlConnection.getInputStream();
				System.out.println(urlConnection.getResponseCode());
				if (urlConnection.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ urlConnection.getResponseCode());
				}
				
				BufferedReader br = new BufferedReader(new InputStreamReader(urlStream));
				
				String output;
				String returnXML = "";
				while((output = br.readLine()) != null)
				{
					returnXML+=output;
				}
				
				try 
				{
					JSONObject json = XML.toJSONObject(returnXML);
					JSONObject root = (JSONObject) json.get("shippingBean");
					//get the message
					shippingmessage = root.getString("message");
					//get shipping cost
					shippingCost = root.getInt("shippingprice");
					System.out.println("message is: "+shippingmessage+" and shipping price is "+shippingCost);
				} 
				catch (JSONException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//grand total is the total cost of photos and the total shipping cost
			grandTotal = totalPhotosCost + shippingCost;
			
			//shipping only be processed if the destination is valid
			//message is "AvailableDestination" and shipping cost is not equal to -1 if the destination is valid
			if((shippingCost != -1) && (shippingmessage.equals("AvailableDestination")))
			{
				//print order status
				System.out.println("Order state: receiving order");
				//next order ID is last ID + 1
				orderID = SingletonDao.getInstance().getPreviousOrderID() + 1;
				//get user from session
				String user = session.get("user").toString();
				//add order
				SingletonDao.getInstance().addOrder(orderID, user, address, "processed", shippingCost, grandTotal);
				//add cart items
				for(int i = 0; i<cartData.size(); i++)
				{
					SingletonDao.getInstance().addItem(orderID, cartData.get(i).getTitle(), cartData.get(i).getQuantity(), cartData.get(i).getPrice());					
				}	
				//this order is complete so empty the cart
				clear();
				//print order status
				System.out.println("Order state: finishing cost computation");
				message = "yes";
			}
			else
			{
				//invalid destination entered
				System.out.println("********* you have entered invalid destination *********");
				message = "no";
				//invalidDestination();
			}			
			return SUCCESS;
		}
		else
		{
			return ERROR;
		}
	}
	
	public String discard()
	{
		session.put("CART",null);
		return SUCCESS;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////Getters and Setters////////////////////////////////////////////////////////////////////////////////
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<CartItemJSON> getCartData() {
		return cartData;
	}
	public void setCartData(List<CartItemJSON> cartData) {
		this.cartData = cartData;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public long getPhotoId() {
		return photoId;
	}
	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}

	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setApplication(Map<String, Object> arg0) {
		application=arg0;

	}
	@Override
	public void setSession(Map<String, Object> arg0) {
		session=arg0;
	}

}
