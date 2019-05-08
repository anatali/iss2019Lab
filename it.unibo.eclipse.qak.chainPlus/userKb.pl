chain(1, led1 ).
chain(2, led2 ).
%% chain(3, led3 ).

unify( A, A ).

getLedNames(LEDNAMES) :-
	findall( NAME, chain(  _, NAME ), LEDNAMES).

initStepCounter:- assign(stepcounter,1).
 	
getNextLedName( LedName ) :-
	value( stepcounter,V ),
	chain(V,LedName),!,
	%% stdout <- println(  getNextLedName(LedName,V)  ),
	inc( stepcounter,1,V1 ).
getNextLedName( reset ) :- initStepCounter, fail.
 
/*
-----------------------------------------------
RULES COPIED FROM sysRules.pl
-----------------------------------------------
*/	

assign( I,V ) :-  retract( value(I,_) ),!, assert( value( I,V )).
assign( I,V ) :-  assert( value( I,V )).
getVal( I, V ):-  value(I,V), !.
getVal( I, fail ).
inc(I,K,N):-
	value( I,V ),
	N is V + K,
	assign( I,N ).
dec(I,K,N):-
	value( I,V ),
	N is V - K,
	assign( I,N ).

addRule( Rule ):-
	%%output( addRule( Rule ) ),
	assert( Rule ).
removeRule( Rule ):-
	retract( Rule ),
	%%output( removedFact(Rule) ),
	!.
removeRule( A  ):- 
	%%output( remove(A) ),
	retract( A :- B ),!.
removeRule( _  ).

replaceRule( Rule, NewRule ):-
	removeRule( Rule ),addRule( NewRule ).