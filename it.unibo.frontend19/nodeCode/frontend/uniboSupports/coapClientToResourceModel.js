/*
frontend/uniboSupports/coapClientToResourceModel
*/
const coap             = require("node-coap-client").CoapClient; 
var coapAddr           = "coap://192.168.1.8:5683"	//RESOURCE ON RASPBERRY PI
//const coapAddr       = "coap://localhost:5683"
var coapResourceAddr   = coapAddr + "/resourcemodel"

/*
coap
    .tryToConnect( coapAddr )
    .then((result ) => { //  true or error code or Error instance  
        console.log("coap connection done"); // do something with the result  
    })
    ;
*/

exports.setcoapAddr = function ( addr ){
	coapAddr = "coap://"+ addr + ":5683";
	coapResourceAddr   = coapAddr + "/resourcemodel"
	console.log("coap coapResourceAddr  " + coapResourceAddr);
}

exports.coapGet = function (  ){
	coap
	    .request(
	         coapResourceAddr,
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
	        coapResourceAddr,     
	        "put" ,			                          // "get" | "post" | "put" | "delete"   
	        new Buffer(cmd )                          // payload Buffer 
 	        //[options]]							//  RequestOptions 
	    )
	    .then(response => { 			// handle response  
	    	console.log("coap put done> " + cmd);}
	     )
	    .catch(err => { // handle error  
	    	console.log("coap put error> " + err + " for cmd=" + cmd);}
	    )
	    ;
	    
}//coapPut

const myself          = require('./coapClientToResourceModel');

function test(){
//	console.log("GET");
// 	myself.coapGet();
 	console.log("PUT");
 	myself.coapPut("w")
//	myself.coapGet();
}

test()

/*
 * ========= EXPORTS =======
 */

//module.exports = coap;
