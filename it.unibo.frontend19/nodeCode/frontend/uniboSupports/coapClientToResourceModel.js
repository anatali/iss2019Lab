/*
coapClientToResourceModel
*/
const coap  = require("node-coap-client").CoapClient; 

/*
coap
    .tryToConnect("localhost:5683" )
    .then((result ) => { //  true or error code or Error instance  
        cosnile.log("coap connection done"); // do something with the result  
    })
    ;
*/

exports.coapGet = function (  ){
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

exports.coapPut = function (  cmd ){ 
	coap
	    .request(
	        "coap://localhost:5683/resourcemodel",     
	        "put" ,			                          // "get" | "post" | "put" | "delete"   
	        new Buffer(cmd )                          // payload Buffer 
 	        //[options]]							//  RequestOptions 
	    )
	    .then(response => { 			// handle response  
	    	console.log("coap put done> " );}
	     )
	    .catch(err => { // handle error  
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

//test()

/*
 * ========= EXPORTS =======
 */

//module.exports = coap;
