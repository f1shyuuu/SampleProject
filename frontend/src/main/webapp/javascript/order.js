/**
 * 
 */
var asyncRequest;
function listMyOrder(url) {
	try {
		asyncRequest = new XMLHttpRequest();
		asyncRequest.onreadystatechange = processResponse;
		asyncRequest.open('GET', url, true);
		asyncRequest.send(null);
	} catch (exception) {
		alert('Request Failed');
	}
}

function closeOrders() {
	var orders = document.getElementById("orders");
	orders.innerHTML = null;
}

function processResponse() {

	if (asyncRequest.readyState == 4 && asyncRequest.status == 200) {
		var data = JSON.parse(asyncRequest.responseText); // evaluate this to
															// an array

		var orders = document.getElementById("orders");
		orders.innerHTML = "";// clear everything

		var headRow = document.createElement("tr");
		var userCell = document.createElement("th");
		userCell.appendChild(document.createTextNode("User"));
		headRow.appendChild(userCell);

		var orderIdCell = document.createElement("th");
		orderIdCell.appendChild(document.createTextNode("Order Id"));
		headRow.appendChild(orderIdCell);

		var titleCell = document.createElement("th");
		titleCell.appendChild(document.createTextNode("Title"));
		headRow.appendChild(titleCell);

		var quantityCell = document.createElement("th");
		quantityCell.appendChild(document.createTextNode("Quantity"));
		headRow.appendChild(quantityCell);

		var priceCell = document.createElement("th");
		priceCell.appendChild(document.createTextNode("Price"));
		headRow.appendChild(priceCell);

		var destCell = document.createElement("th");
		destCell.appendChild(document.createTextNode("Destination"));
		headRow.appendChild(destCell);

		var statusCell = document.createElement("th");
		statusCell.appendChild(document.createTextNode("Status"));
		headRow.appendChild(statusCell);

		var shipFeeCell = document.createElement("th");
		shipFeeCell.appendChild(document.createTextNode("Shipping Fee"));
		headRow.appendChild(shipFeeCell);

		var totalCell = document.createElement("th");
		totalCell.appendChild(document.createTextNode("Total"));
		headRow.appendChild(totalCell);

		orders.appendChild(headRow);

		for ( var i = 0; i < data.length; i++) {
			var newRow = document.createElement("tr");
			var userCell = document.createElement("td");
			userCell.appendChild(document
					.createTextNode(data[i].orderBean.user));
			newRow.appendChild(userCell);

			var orderIdCell = document.createElement("td");
			orderIdCell.appendChild(document
					.createTextNode(data[i].orderBean.id));
			newRow.appendChild(orderIdCell);

			var titleCell = document.createElement("td");
			titleCell.appendChild(document
					.createTextNode(data[i].cartItemBean.title));
			newRow.appendChild(titleCell);

			var quantityCell = document.createElement("td");
			quantityCell.appendChild(document
					.createTextNode(data[i].cartItemBean.quantity));
			newRow.appendChild(quantityCell);

			var priceCell = document.createElement("td");
			priceCell.appendChild(document
					.createTextNode(data[i].cartItemBean.price));
			newRow.appendChild(priceCell);

			var destCell = document.createElement("td");
			destCell.appendChild(document
					.createTextNode(data[i].orderBean.destination));
			newRow.appendChild(destCell);

			var statusCell = document.createElement("td");
			statusCell.appendChild(document
					.createTextNode(data[i].orderBean.status));
			newRow.appendChild(statusCell);

			var shipFeeCell = document.createElement("td");
			shipFeeCell.appendChild(document
					.createTextNode(data[i].orderBean.shipfee));
			newRow.appendChild(shipFeeCell);

			var totalCell = document.createElement("td");
			totalCell.appendChild(document
					.createTextNode(data[i].orderBean.total));
			newRow.appendChild(totalCell);

			orders.appendChild(newRow);

		}
		;

	}
}