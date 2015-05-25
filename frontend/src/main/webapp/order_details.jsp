<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h2>Details for order ${id}</h2>
<table class="order_details">
	<thead>
		<tr>
			<th>Title</th>
			<th>Quantity</th>
			<th>Price</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="cartItems">
			<tr>
				<td><s:property value="title" /></td>
				<td><s:property value="quantity" /></td>
				<td><s:property value="price" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
