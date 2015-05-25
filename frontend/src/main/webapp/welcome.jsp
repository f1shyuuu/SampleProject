<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script language="JavaScript" type="text/JavaScript"
	src="../javascript/order.js"></script>
<title>welcome</title>
<link href="../css/stytle.css" rel="stylesheet">
</head>
<body>
	<s:url var="out" action="logout" namespace="/check"></s:url>
	<h2>
		<s:property value="welcome" />
		<a href="${out}"> Log Out</a>
	</h2>


	<%
		if (!session.getAttribute("user").equals("admin")) {
	%>

	<s:url var="list" action="list" namespace="/order">
	</s:url>
	<button onclick="listMyOrder('${list}')" style="color: green">expand
		my orders</button>
	<button onclick="closeOrders()" style="color: green">close
		my orders</button>
	<table id="orders" class="orders"></table>

	<s:form action="list" namespace="/customer">
		<s:textfield name="tag" label="please enter the tag" />
		<s:submit />
	</s:form>
	<%
		}
	%>



</body>
</html>