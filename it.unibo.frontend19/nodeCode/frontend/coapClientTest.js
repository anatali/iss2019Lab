const coap   = require("node-coap-client").CoapClient; 


function coapGet(  ){
 
	coap
	    .request(
	        "coap://localhost:5683/resourcemodel" /* string */,
	        "get" /* "get" | "post" | "put" | "delete" */
 	        //[payload /* Buffer */,
	        //[options /* RequestOptions */]]
	    )
	    .then(response => { 			/* handle response */
	    	console.log("coap get done> " + response.payload );}
	     )
	    .catch(err => { /* handle error */ 
	    	console.log("coap get error> " + err );}
	    )
	    ;
	    
}//coapPut

function coapPut(  cmd ){
 
	coap
	    .request(
	        "coap://localhost:5683/resourcemodel" /* string */,
	        "put" 			/* "get" | "post" | "put" | "delete" */,
	        new Buffer(cmd )
	        //[payload /* Buffer */,
	        //[options /* RequestOptions */]]
	    )
	    .then(response => { 			/* handle response */
	    	console.log("coap put done> " );}
	     )
	    .catch(err => { /* handle error */ 
	    	console.log("coap put error> " + err );}
	    )
	    ;
	    
}//coapPut

function test(){
	//coapPut("w")
	coapGet();
	console.log("PUT");
	coapPut("w")
	coapGet();
}

test()
