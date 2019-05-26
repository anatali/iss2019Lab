%% cell(X,Y,STATE).	%%	STATE 0 means obstacle, 1 means visited
	
updateMapAfterStep :-
	inc(y,1,Y),
	assign(y,Y),
	output( y(Y) ),	
	getVal(x,X),
	output( x(X) ),
	addRule( cell(X,Y,1) ),
	showMap. 
 
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

initMap :-
	output("initMap  "),
	assign(x,1),
	assign(y,1),
	addRule( cell(1,1,1) ).		

:- initialization(initMap).
 