var express     	= require('express');
var path         	= require('path');
//var favicon      	= require('serve-favicon');
var logger       	= require('morgan');	//see 10.1 of nodeExpressWeb.pdf;
//var cookieParser 	= require('cookie-parser');
var bodyParser   	= require('body-parser');
var fs           	= require('fs');
var index           = require('./appServer/routes/index');				 

mqttUtils           = require('./uniboSupports/mqttUtils'); //(***)
 
var app = express();

// view engine setup;
app.set('views', path.join(__dirname, 'appServer', 'views'));	 
app.set('view engine', 'ejs');

//create a write stream (in append mode) ;
var accessLogStream = fs.createWriteStream(path.join(__dirname, 'morganLog.log'), {flags: 'a'})
app.use(logger("short", {stream: accessLogStream}));

//Creates a default route. Overloads app.use('/', index);
//app.get("/", function(req,res){ res.send("Welcome to frontend Server"); } );

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));				//shows commands, e.g. GET /pi 304 23.123 ms - -;
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
//app.use(cookieParser());

app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'jsCode'))); //(***)

//DEFINE THE ROUTES ;
//app.use('/', index);		 

//Creates a default route for /pi;
app.get('/info', function (req, res) {
  res.send('This is the frontend-Unibo!')
});

	app.get('/', function(req, res) {
  		res.render("index");
  	});	

/*
 * ====================== COMMANDS ================
 */
	app.post("/w", function(req, res) {
 		delegate( "w", "moving forward", req, res);		
	});	
	app.post("/s", function(req, res) {
		delegate( "s", "moving backward", req, res );
	});	
	app.post("/a", function(req, res) {
 		delegate( "a", "moving left", req, res );
 	});	
	app.post("/d", function(req, res) {
  		delegate( "d", "moving right", req, res );
 	});	
	app.post("/h", function(req, res) {
  		delegate( "h", "stopped", req, res );
 	});		

//=================== UTILITIES =========================
function delegate( hlcmd, newState, req, res ){
	emitRobotCmd(hlcmd);
    res.render("index");	
}
var emitRobotCmd = function( cmd ){ //called by delegate;
 	var eventstr = "msg(usercmd,event,js,none,usercmd("+cmd +"),1)"
  	console.log("emits> "+ eventstr);
 	mqttUtils.publish( eventstr );	//topic  = "unibo/qasys";
}

/*
* ====================== REPRESENTATION ================
*/

app.use( function(req,res){
	console.info("SENDING THE ANSWER " + req.result  );
	try{
		if( req.result != undefined) serverWithSocket.updateClient( JSON.stringify(req.result ) );
		res.send(req.result);
	}catch(e){console.info("SORRY ...");}
	} 
);

//app.use(converter());

// catch 404 and forward to error handler;
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler;
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page;
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;