package controllers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import models.PhotoBean;
import models.UserBean;

import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.opensymphony.xwork2.ActionSupport;

import commons.Constants;
import dao.SingletonDao;

public class CustomerAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	private String tag;
	private String welcome;
	private List<PhotoBean> photoStore;
	private Map<String, Object> session;

	public String get()
	{
		List<UserBean> us=new ArrayList<UserBean>();
		us=SingletonDao.getInstance().getUsers();
		System.out.println(us);
		return SUCCESS;
	}


	public String list()
	{
		if( session.get("welcome")!=null){
			welcome=(String) session.get("welcome");}

		//construct url
		String url;
		try {
			
			url = Constants.REST_ENDPOINT+"?method="+Constants.SEARCH+
					"&tags="+URLEncoder.encode(tag,Constants.ENC)+
					"&per_page="+Constants.DEFAULT_NUMBER+
					"extras=tags"+
					"&api_key="+Constants.API_KEY;
			System.out.print(" ........."+url);
			java.net.URL callUrl = new URL(null, url,new sun.net.www.protocol.https.Handler());
			HttpsURLConnection urlConnection = (HttpsURLConnection)callUrl.openConnection();
			if (urlConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ urlConnection.getResponseCode());
			}


			BufferedReader br = new BufferedReader(new InputStreamReader(
					(urlConnection.getInputStream())));
			String output;
			String sss = null;
			//System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				sss+=output;
			}	
			JSONObject json=XML.toJSONObject(sss);			
			JSONObject root= (JSONObject) json.get("rsp");
			JSONObject photos=root.getJSONObject( "photos");
			JSONArray photo=photos.getJSONArray("photo");
			photoStore=new ArrayList<PhotoBean>();
			for(int i=0;i<photo.length();i++)
			{
				JSONObject j=photo.getJSONObject(i);
				System.out.println("the photo is : "+j);
				PhotoBean p=new PhotoBean();
				p.setFarm(j.getInt("farm"));
				p.setServer(j.getInt("server"));
				p.setId((long) j.get("id"));

				if( j.get("secret").getClass().getName()=="java.lang.String"){
					p.setSecret(j.getString("secret"));}
				else
				{
					p.setSecret(Long.toString(j.getLong("secret")));
				}

				p.setTitle(j.getString("title"));
				String u=null;
				u="http://farm"+p.getFarm()+".staticflickr.com/"+p.getServer()+"/"+p.getId()+"_"+p.getSecret()+".jpg";
				p.setUrl(u);
				p.setTags(getTags(j.getLong("id")));
				List<String> ttt=getTags(j.getLong("id"));
				int price=5;
				for(int k=0;k<ttt.size();k++)
				{
					price+=2;
				}
				p.setPrice(price);

				photoStore.add(p);
			}


		} 

		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SUCCESS;
	}
	public static List<String> getTags(long  id){

		String url=Constants.REST_ENDPOINT+"?method="+Constants.tag+
				"&photo_id="+id+"&api_key="+Constants.API_KEY;
		java.net.URL callUrl;
		try {
			callUrl = new URL(null, url,new sun.net.www.protocol.https.Handler());
			HttpsURLConnection urlConnection = (HttpsURLConnection)callUrl.openConnection();
			if (urlConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ urlConnection.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(urlConnection.getInputStream())));
			String output;
			String sss = null;
			while ((output = br.readLine()) != null) {
				sss+=output;
			}	
			JSONObject json=XML.toJSONObject(sss);				
			JSONObject root= (JSONObject) json.get("rsp");
			JSONObject photo=root.getJSONObject("photo");
			//			System.out.println(" \r \r \r");
			//			System.out.println("the photo is : "+photo);
			//			System.out.println(photo.get("tags").getClass().getName());
			if( photo.get("tags").getClass().getName()!="java.lang.String"){

				//				System.out.println("null :"+photo.isNull("tags"));
				//System.out.println("has : "+photo.has("tags"));
				//				System.out.println("the tags are : "+photo.getJSONObject("tags"));
				JSONObject tags= photo.getJSONObject("tags");

				//System.out.println(tags.has("tag"));
				//if(tags.get("tag").getClass().getName())
				System.out.println("the tag is : "+tags.get("tag").getClass().getName());
				System.out.print("the content of tag is : "+tags.get("tag"));
				List<String> tagList=new ArrayList<String>();
				if(tags.get("tag").getClass().getName()=="org.json.JSONArray"){
					JSONArray tag=tags.getJSONArray("tag");

					for(int i=0;i<tag.length();i++)
					{
						String s=null;
						//System.out.println(tag.get(i));
						//System.out.println("tag? : "+tag.isNull(i));
						//System.out.println(tag.getJSONObject(i).get("raw").getClass().getName());
						if(tag.getJSONObject(i).get("raw").getClass().getName()!="java.lang.Integer"&&
								tag.getJSONObject(i).get("raw").getClass().getName()!="java.lang.Double"){
							s=(String)(tag.getJSONObject(i)).get("raw");}

						tagList.add(s);
					}
				}
				return tagList;
			}
			else return null;

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}




	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////Getters and Setters///////////////////////////////////////////////////////////////////////////

	public List<PhotoBean> getPhotoStore() {
		return photoStore;
	}

	public void setPhotoStore(List<PhotoBean> photoStore) {
		this.photoStore = photoStore;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getWelcome() {
		return welcome;
	}


	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}



	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		session=arg0;

	}

}
