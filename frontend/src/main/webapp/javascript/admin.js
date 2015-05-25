function changeStatusJS(a, base_url, status, view_details_url) {
	try {
		var asyncRequest = new XMLHttpRequest();
		asyncRequest.onreadystatechange = function() {
			if (asyncRequest.readyState == 4 && asyncRequest.status == 200) {
				var data = JSON.parse(asyncRequest.responseText); // evaluate
																	// this to
				// an array
				console.log(data);
				var tr = a.parentNode.parentNode;
				console.log(tr);

				tr.innerHTML = "";// clear everything

				var userCell = document.createElement("td");
				userCell.appendChild(document.createTextNode(data.user));
				tr.appendChild(userCell);

				var orderIdCell = document.createElement("td");
				orderIdCell.appendChild(document.createTextNode(data.id));
				tr.appendChild(orderIdCell);

				var destCell = document.createElement("td");
				destCell.appendChild(document.createTextNode(data.destination));
				tr.appendChild(destCell);

				var statusCell = document.createElement("td");
				statusCell.appendChild(document.createTextNode(data.status));
				tr.appendChild(statusCell);

				var shipFeeCell = document.createElement("td");
				shipFeeCell.appendChild(document.createTextNode(data.shipfee));
				tr.appendChild(shipFeeCell);

				var totalCell = document.createElement("td");
				totalCell.appendChild(document.createTextNode(data.total));
				tr.appendChild(totalCell);

				var changeStatCell = document.createElement("td");
				if (data.status != 'delivered') {
					var changeStatusLink = document.createElement('a');
					var changeStatusLinkText = document
							.createTextNode("Change status to "
									+ data.nextStatus);
					changeStatusLink.appendChild(changeStatusLinkText);
					changeStatusLink.href = "#";
					changeStatusLink.addEventListener("click", function() {
						changeStatusJS(this, base_url, data.nextStatus,
								view_details_url);
					});
					changeStatCell.appendChild(changeStatusLink);
					changeStatCell.appendChild(document.createElement("br"));
				}
				var viewDetailsLink = document.createElement('a');
				var viewDetailsLinkText = document
						.createTextNode("View order details");
				viewDetailsLink.appendChild(viewDetailsLinkText);
				viewDetailsLink.href = "#";
				viewDetailsLink.addEventListener("click", function() {
					viewDetails(view_details_url);
				});
				changeStatCell.appendChild(viewDetailsLink);
				tr.appendChild(changeStatCell);
			}

		};
		asyncRequest.open('GET', base_url + "&status=" + status, true);
		asyncRequest.send(null);
	} catch (exception) {
		alert('Request Failed');
	}
}
function viewDetails(url) {
	try {
		var asyncRequest = new XMLHttpRequest();
		asyncRequest.onreadystatechange = function() {
			if (asyncRequest.readyState == 4 && asyncRequest.status == 200) {
				document.getElementById('order_details').innerHTML = asyncRequest.responseText;
			}
		};
		asyncRequest.open('GET', url, true);
		asyncRequest.send(null);
	} catch (exception) {
		alert('Request Failed');
	}
}