%% cell(X,Y,STATE).	%%	STATE 0 means obstacle, 1 means visited

%%direction( D ). 			%% D= sud, east, west, north
%%cell( X,Y, STATE )		%%CELL= 1 => done

initMap(DIRECTION) :-
	assign(x,0),
	assign(y,0),
	assert( direction(DIRECTION) ),
	retractall( cell(X,Y,STATE) ), 
	addRule( cell(0,0,1) ),
	output("floorMap initMap DONE ").		
 
changeDirection(NEWD)   :- direction( D ), changeTheDirection( D, NEWD ).
changeTheDirection( sud, east   ):- replaceRule( direction( sud ), direction( east )   ).
changeTheDirection( east,north  ):- replaceRule( direction( east ), direction( north ) ).
changeTheDirection( north,west  ):- replaceRule( direction( north ), direction( west ) ).
changeTheDirection( west,sud    ):- replaceRule( direction( west ), direction( sud )   ).
	
updateMapAfterStep :- 
	direction( D ),
	updateMap(D,X,Y),
	%%output( updateMap(D,X,Y) ),
	addRule( cell(X,Y,1) ).
	
updateMap(sud,X,Y):-
	getVal(x,X),
	getVal(y,LASTY),
	Y is LASTY+1,
	assign(y,Y). 
updateMap(east,X,Y):-
	getVal(y,Y),
	getVal(x,LASTX),
	X is LASTX + 1,
	assign(x,X).
updateMap(north,X,Y):-
	getVal(x,X),
	getVal(y,LASTY),
	Y is LASTY-1,
	assign(y,Y). 
updateMap(west,X,Y):-
	getVal(y,Y),
	getVal(x,LASTX),
	X is LASTX - 1,
	assign(x,X).
   
showMap :- 
	output("%%%%%%%%%%%%%%%%%%%%%% "),
	showCells,
	output("%%%%%%%%%%%%%%%%%%%%%% ").


showCells :-
 	cell( X,Y,STATE ),
 	output( cell( X,Y, STATE ) ),
	fail.
showCells.		

output( M ) :- stdout <- println( M ).

%-------------------------------------------------
%  TuProlog FEATURES 
%-------------------------------------------------
dialog( FileName ) :-  
	java_object('javax.swing.JFileChooser', [], Dialog),
	Dialog <- showOpenDialog(_),
	Dialog <- getSelectedFile returns File,
	File <- getName returns FileName. 		 

 
:- initialization(initMap).
 