package models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import models.CartItem;


public class Cart {
	public Map<Long,CartItem> carts;
	public Cart(){carts=new HashMap<Long,CartItem>();}
	public void addItem(CartItemBean p)
	{
		long photoId=p.getId();
		
		if(carts.get(photoId)==null)
		{
			CartItem c=new CartItem();
			c.setBean(p);
			carts.put(photoId, c);
		}
		else
		{
			carts.get(photoId).add();  
		}
	}
	public void removeItem(long id)
	{
		if(carts.get(id)==null)
		{
			// do nothing
		}
		else
		{
			carts.get(id).remove();
			if(carts.get(id).getQuantity()==0)
			{
				carts.remove(id);
			}
		}
	}
	public Collection<CartItem> getItems() {
		return carts.values();
	}

}
