package controllers;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class Authentication implements Interceptor {
	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {}

	@Override
	public void init(){}
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		Map<String,Object> session=arg0.getInvocationContext().getSession();
		Boolean login=false;
		
		if(session.get("login")!=null)
		login=(Boolean) session.get("login");
	
		if(!login){
		System.out.println("please log in");
		return Action.LOGIN;}
		else{ return arg0.invoke();}
	}


}
