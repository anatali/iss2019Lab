/*
* =====================================
* uniboSupports/mqttUtils.js
* =====================================
*/
const mqtt   = require ('mqtt');  //npm install --save mqtt
const topic  = "unibo/qasys";

//var client = mqtt.connect('mqtt://iot.eclipse.org');
//var client   = mqtt.connect('mqtt://192.168.1.100');
var client   = mqtt.connect('mqtt://localhost');
//
console.log("mqtt client= " + client );
//
client.on('connect', function () {
	  //client.subscribe( topic );
	  console.log('client has connected successfully ');
});

//
////The message usually arrives as buffer, so I had to convert it to string data type;
client.on('message', function (topic, message){
  console.log("mqtt RECEIVES:"+ message.toString()); //if toString is not given, the message comes as buffer
});
//
exports.publish = function( msg ){
	console.log('mqtt publish ' + client);
	client.publish(topic, msg);
}

/*
 * comment below if you want to work with mqtt server


var net = require('net');
var host = "localhost";
var port = 1883; //8032;
console.log('connecting to ' + host + ":" + port);
var conn = net.connect({ port: port, host: host });
conn.setEncoding('utf8');
// when receive data back, print to console
conn.on('data',function(data) {
	console.log(data);
});

// when server closed
conn.on('close',function() {
	console.log('connection is closed');
});

conn.on('end',function() {
	console.log('connection is ended');
});

exports.publish = function( msg ){
	console.log(msg);
	conn.write(msg+"\n")
}

 */
