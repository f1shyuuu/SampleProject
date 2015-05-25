var asyncRequest;
function addToCartJS(url){	
	try
	{
		asyncRequest = new XMLHttpRequest(); 
		asyncRequest.onreadystatechange = processResponse;
		asyncRequest.open( 'GET', url ,true ); 
		asyncRequest.send( null ); 

	} 
	catch ( exception )
	{
		alert( 'Request Failed' );
	} 
} 
function processResponse(){

	if ( asyncRequest.readyState == 4 && asyncRequest.status == 200){
		var data = eval("("+asyncRequest.responseText+")"); // evaluate this to an array
		var totalPrice = 0.0;
		var cartContent = document.getElementById("cartContent");
		var status=document.getElementById("cartstatus");

		while (cartContent.lastChild)
			cartContent.removeChild(cartContent.lastChild);
		cartContent.innerHTML="";//clear everything
		
		if(data.length!=0)
			{
			status.innerHTML="the following item(s) is in cart";
			}
		else
			{status.innerHTML="the cart is empty now";}

		for(var i=0;i<data.length;i++){
			var newRow=document.createElement("tr");
			var newCell=document.createElement("td");
			newCell.appendChild(document.createTextNode("The title is: "));
			newRow.appendChild(newCell);

			var cell2=document.createElement("td");
			cell2.appendChild(document.createTextNode(data[i].title));
			newRow.appendChild(cell2);
			
			var cell22=document.createElement("td");
			cell22.appendChild(document.createTextNode(" The quantity is : "));
			newRow.appendChild(cell22);

			var cell3=document.createElement("td");
			cell3.appendChild(document.createTextNode(data[i].quantity));
			newRow.appendChild(cell3);
			cartContent.appendChild(newRow);
			totalPrice += data[i].price * data[i].quantity;
		}
		var priceCell = document.getElementById("totalPrice");
		priceCell.firstChild.nodeValue=totalPrice;

	}
}

function removeFromCartJS(url){	
	try
	{
		asyncRequest = new XMLHttpRequest(); 
		asyncRequest.onreadystatechange = processResponse;
		asyncRequest.open( 'GET', url ,true ); 
		asyncRequest.send( null ); 

	} 
	catch ( exception )
	{
		alert( 'Request Failed' );
	} 
} 

function clearCartJS(url){
	try
	{
		 confirm("Are you sure to clear the cart");
		asyncRequest = new XMLHttpRequest(); 
		asyncRequest.onreadystatechange = processResponse;
		asyncRequest.open( 'GET', url ,true ); 
		asyncRequest.send( null ); 

	} 
	catch ( exception )
	{
		alert( 'Request Failed' );
	} 
	
}

function checkout() {
	el = document.getElementById("overlay");
	el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
}