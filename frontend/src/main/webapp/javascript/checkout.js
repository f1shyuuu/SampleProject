/**
 * 
 */
var asyncRequest;
function hello(url){	
	try
	{
		
		var address = document.getElementById('good').value;
		//alert( "the input is : "+address);
		url+=address;
		asyncRequest = new XMLHttpRequest(); 
		asyncRequest.onreadystatechange = process;
		asyncRequest.open( 'GET', url ,true ); 
		asyncRequest.send( null ); 

	} 
	catch ( exception )
	{
		alert( 'Request Failed' );
	} 
} 
function process(){
	if ( asyncRequest.readyState == 4 && asyncRequest.status == 200){
		var data = eval("("+asyncRequest.responseText+")"); // evaluate this to an array
		
		message=data.toString();
		//alert(message);
		if(message=="yes")
			{
			var yes = document.getElementById("yes");
			yes.innerHTML="Congratulations,you order had been placed successfully";
			var no = document.getElementById("no");
			while (no.lastChild)
				no.removeChild(no.lastChild);
			}
		else{
			var yes = document.getElementById("yes");
			yes.innerHTML="";
			var link = document.getElementById("discard");
			link.innerHTML = "Discard!";
			var no = document.getElementById("no");
			while (no.lastChild)
				no.removeChild(no.lastChild);
			var notification=document.createElement("h2");
			notification.appendChild(document.createTextNode("Invalid destination,enter the address again or dicard order"));
			no.appendChild(notification);

		
		}
	}


}

