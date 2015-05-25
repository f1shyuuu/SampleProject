package rest;

import java.io.InputStream;
import java.net.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import bean.ShippingBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Path("/shipping")
public class Rest {

	//instance variable
	public Rest()
	{
	}
	
	@GET
	//@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ShippingBean calculateShipping(@QueryParam("city")String city, @QueryParam("quantity") String quantity, @QueryParam("titles") String titles, @QueryParam("quantities") String quantities, @QueryParam("prices") String prices)
	{
		System.out.println("Order Status: Starting order");
		System.out.println();
		/**
		 * The example URL is:
		 * http://localhost:9000/shipping/rest/shipping?city=kathmandu&quantity=3&titles=this is something||that is another&quantities=1||2&prices=15||19
		 * where city is kathmandu and quantity of photos is 3
		 */
		ShippingBean sb = new ShippingBean();
		
		System.out.println("Order Status: Receiving order");
		System.out.println();
		
		//get cartitems
		System.out.println("$$$$    "+ titles);

		String[] splitTitles = titles.split("\\|\\|");
		System.out.println("!!!!    "+quantities);
		String[] splitQuantities = quantities.split(",");
		String[] splitPrices = prices.split(",");
		
		System.out.println("Items in order:");
		System.out.println("");
		
		for(int i = 0; i<splitTitles.length; i++)
		{
			String formattedTitle = "";
			for(int j = 0; j<splitTitles[i].length(); j++)
			{
				formattedTitle = splitTitles[i].replace("___"," ");
			}
			System.out.println("Title: "+formattedTitle);
			System.out.println("Quantity: "+splitQuantities[i]);
			System.out.println("Price: "+splitPrices[i]);
			System.out.println("");
		}

		int cost = -1;
		int shippingPrice = 0;
		//this is the shipping price incurred on each photo
		final int unitShippingPrice = 1;
		//message if destination city is valid
		String positiveMessage = "AvailableDestinationAAAAAAAAA";
		//message if destination city is invalid
		String errorMessage = "InvalidDestination";
		
		try 
		{	
			Context iniCtx = new InitialContext();
			Context env = (Context) iniCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) env.lookup("jdbc/Assignment2");			
			
			//get the cost for shipping to a city
			String query = "select cost from destination where city = ?";
			
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setString(1, city);
			
			ResultSet rs = ps.executeQuery();
			//System.out.println("given city is "+city);
			//System.out.println("given quantity is "+quantity);
			while(rs.next())
			{
				cost = Integer.parseInt(rs.getString("cost"));
			}

			//if the destination city is not valid then rs.next is not true because the result is null
			//so that no cost has been computed and it is the initial cost of -1
			if(cost == -1)
			{
				//if no city then set shipping price to -1 and message to error message
				shippingPrice = -1;
				sb.setMessage(errorMessage);
				System.out.println("Invalid destination entered!!!!");
			}
			else
			{
				System.out.println("Order state: finishing cost computation");
				//if the city is valid then compute the shipping price
				//it is the shipping cost to the destination plus the per unit charge for each photo
				//set the message to positive indicating the destination is valid
				shippingPrice = ((Integer.parseInt(quantity)) * unitShippingPrice)  + cost;
				sb.setMessage(positiveMessage);
				System.out.println("Shipping cost to city "+city+" is "+cost);
			}
			
			sb.setShippingprice(shippingPrice);
			
			rs.close();
			ps.close();
			conn.close();			

		} 
		catch (NamingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			shippingPrice = -1;
			System.out.println("naming exception error: "+e.getMessage());
			System.out.println(shippingPrice);
			//if error encountered, set message to error and shipping price to -1
			sb.setMessage(errorMessage);
			sb.setShippingprice(shippingPrice);
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			shippingPrice = -1;
			
			System.out.println("sql exception error: "+e.getMessage());
			System.out.println(shippingPrice);

			//if error encountered, set message to error and shipping price to -1
			sb.setMessage(errorMessage);
			sb.setShippingprice(shippingPrice);
		}
		
		return sb;
	}
	
	/**
	 * @param args
	 *//*
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/

}
