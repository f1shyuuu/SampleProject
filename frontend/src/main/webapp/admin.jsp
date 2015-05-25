<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script language="JavaScript" type="text/JavaScript"
	src="../javascript/admin.js"></script>
<title>Admin page</title>
<link href="../css/stytle.css" rel="stylesheet">
</head>
<body>
	<s:url var="out" action="logout" namespace="/check"></s:url>
	<h2>
		<s:property value="welcome" />
		<a href="${out}"> Log Out</a>
	</h2>


	<h1>Here is all the orders</h1>
	<table class="orders">
		<thead>
			<tr>
				<th>User</th>
				<th>Id</th>
				<th>Destination</th>
				<th>Status</th>
				<th>Shipping Fee</th>
				<th>Total</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>

			<s:iterator value="allOrders">
				<tr>
					<td><s:property value="user" /></td>
					<td><s:property value="id" /></td>
					<td><s:property value="destination" /></td>
					<td><s:property value="status" /></td>
					<td><s:property value="shipfee" /></td>
					<td><s:property value="total" /></td>
					<s:url var="changestatus" action="change" namespace="/admin">
						<s:param name="id">
							<s:property value="id" />
						</s:param>
					</s:url>
					<s:url var="viewDetails" action="orderDetails" namespace="/admin">
						<s:param name="id">
							<s:property value="id" />
						</s:param>
					</s:url>
					<td><s:if test="status != 'delivered'">
							<a href="#"
								onclick="changeStatusJS(this, '${changestatus}', '${nextStatus}', '${viewDetails}')">Change
								status to ${nextStatus}</a>
							<br>
						</s:if> <a href="#" onclick="viewDetails('${viewDetails}')"> View
							order details</a></td>

				</tr>
			</s:iterator>
		</tbody>
	</table>
	<div id="order_details"></div>
</body>
</html>