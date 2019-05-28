/*
* =====================================
* frontend/uniboSupports/mqttUtils.js
* =====================================
*/
const mqtt   = require ('mqtt');  //npm install --save mqtt
const topic  = "unibo/qak/events";

//var client = mqtt.connect('mqtt://iot.eclipse.org');
//var client = mqtt.connect('mqtt://192.168.1.100');
var client   = mqtt.connect('mqtt://192.168.43.229');
var io  ; 	//Upgrade for socketIo;

console.log("mqtt client= " + client );

exports.setIoSocket = function ( iosock ) {
 	io    = iosock;
	console.log("mqtt SETIOSOCKET io=" + io);
}


client.on('connect', function () {
	  client.subscribe( topic );
	  console.log('client has connected successfully ');
});

//The message usually arrives as buffer, so I had to convert it to string data type;
client.on('message', function (topic, message){
  //console.log("mqtt io="+ io );
  //msg(modelContent,event,resourcemodel,none,content(robot(state(5))),74)
  console.log("mqtt RECEIVES:"+ message.toString()); //if toString is not given, the message comes as buffer
  var msgStr          = message.toString();
  var spRobot         = msgStr.indexOf("robot");
  var spSonarRobot    = msgStr.indexOf("sonarRobot");
  var sp1    = msgStr.indexOf("state");
  var msgStr = msgStr.substr(sp1);
  var sp2    = msgStr.indexOf("))");
  if( spRobot > 0 ) 
  var msg    = "";
  if( spRobot > 0 )      msg = "robot - "
  if( spSonarRobot > 0 ) msg = "sonarRobot - "
  msg = msg + message.toString().substr(sp1,sp2+1);
  io.sockets.send( msg );
});

exports.publish = function( msg, topic ){
	//console.log('mqtt publish ' + client);
	client.publish(topic, msg);
}

