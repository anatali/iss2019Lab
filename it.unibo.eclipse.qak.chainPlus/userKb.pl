vertical(   line(point(X, Y), point(X, Z)) ).
horizontal( line(point(X, Y), point(Z, Y)) ).

pos(1,point(1,5)).
pos(2,point(3,1)).
pos(3,point(3,3)).
pos(4,point(3,5)).
pos(4,point(3,7)).
pos(4,point(7,1)).
pos(4,point(7,5)).
pos(4,point(7,9)).

horizontalLine(P1,P2):- 
	pos(START,P1), 
	%stdout <- println( horizontalLine( P1 ) ),
	horizontal( line(P1,P2) ),
	pos(POS,P2), POS \== START.  
 
allHLines(P1,HL):-
	findall( secondPoint(P2), horizontalLine(P1,P2), HL).
	
chain(1, led1 ).
chain(2, led2 ).
%% chain(3, led3 ).

unify( A, B ) :- A = B.

getLedNames(LEDNAMES) :-
	findall( NAME, chain(  _, NAME ), LEDNAMES).

initStepCounter:- assign(stepcounter,1).
 	
getNextLedName( LedName ) :-
	value( stepcounter,V ),
	chain(V,LedName),!,
	%% stdout <- println(  getNextLedName(LedName,V)  ),
	inc( stepcounter,1,V1 ).
getNextLedName( reset ) :- initStepCounter, fail.
 
 

 