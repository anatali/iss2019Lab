/*
* =====================================
* bls0.js
* =====================================
*/
var ledOn = true;	//global variable

buttonPress = function(ledId){
	ledOn = ! ledOn;
	if( ledOn ) document.getElementById(ledId).style.backgroundColor='#00FF33';
	else document.getElementById(ledId).style.backgroundColor='#FF0000';
	println(  "The led is " + ledOn );
}
