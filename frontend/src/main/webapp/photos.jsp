<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All the products</title>
<script language="JavaScript" type="text/JavaScript"
	src="../javascript/cart.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="../javascript/checkout.js"></script>	
<link href="../css/stytle.css" rel="stylesheet">
</head>
<body>
	<h1>Here is all the products</h1>
	<s:url var="out" action="logout" namespace="/check"></s:url>
	<h2>
		<s:property value="welcome" />
		<a href="${out}"> Log Out</a>
	</h2>

	<%= (String)session.getAttribute("welcome") %>

	<s:iterator value="photoStore">
		<div style="float: left;">
			<h5>title</h5>
			<s:property value="title" />
			<h5>product id</h5>
			<s:property value="id" />
			<h5>the tags are</h5>
			<s:property value="tags" />
			<h5>the price is</h5>
			<s:property value="price" />
			<s:url var="addcart" action="add" namespace="/cart">
				<s:param name="photoId">
					<s:property value="id" />
				</s:param>
				<s:param name="title">
					<s:property value="title" />
				</s:param>
				<s:param name="price">
					<s:property value="price" />
				</s:param>
			</s:url>
			<s:url var="removecart" action="remove" namespace="/cart">
				<s:param name="photoId">
					<s:property value="id" />
				</s:param>
			</s:url>
			<br>
			<button onclick="addToCartJS('${addcart}')" style="color: green">
				add to cart</button>
			<button onclick="removeFromCartJS('${removecart}')"
				style="color: green">remove from cart</button>


		</div>
		<div>
			<img src="<s:property value="url" />" />
		</div>
	</s:iterator>

	<button onclick="checkout()" style="color: blue">Check out</button>



<h3 id="cartstatus"> the cart is empty now</h3>

	<table>
		<tbody id="cartContent">		
		</tbody>
		<tfoot>
			<tr>
				<th>Total:</th>
				<th id="totalPrice">0.00</th>
			</tr>
		</tfoot>
	</table>
	
	<s:url var="clearcart" action="clear" namespace="/cart"></s:url>
	<button style="color: red" onclick="clearCartJS('${clearcart}')">clear cart</button>
	
	
	<s:url var="checkout" action="checkout" namespace="/cart"></s:url>

	<form action="${checkout}" id="checkout"></form>

	<div id="overlay">
		<s:textfield id="good" name="hello"
			label="Please enter your deliver address here"></s:textfield>
		<s:url var="hello" action="checkout" namespace="/cart">
			<s:param name="address"></s:param>
		</s:url>
		<button style="color: red" onclick="hello('${hello}')">send
			address</button>
	</div>
	
	<h2 style="color: green" id="yes"></h2>
	<div style="color: red" id="no"></div>
	<s:url var="discard" action="discard" namespace="/cart"></s:url>
	<a href='${discard}' id="discard"> </a>

</body>
</html>