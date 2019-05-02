chain(1, led1 ).
chain(2, led2 ).
%% chain(3, led3 ).

getLedNames(LEDNAMES) :-
	findall( NAME, chain(  _, NAME ), LEDNAMES),
	length(LEDNAMES, NUM),
	assign( currentChainLedNum, NUM ),
	assign( staticChainLedNum, NUM ).
	
getNextLedName( LedName ) :-
	value( stepcounter,V ),
	chain(V,LedName),!,
	%% stdout <- println(  getNextLedName(LedName,V)  ),
	inc( stepcounter,1,V1).
getNextLedName( reset ) :- resetLedCounter, fail.
	 
resetLedCounter :- assign( stepcounter,1  ).	 %%create value( counter,1 )

addLed( LedName ):-
	inc( currentChainLedNum,1,V),
	stdout <- println( addLed( chain(V,LedName) ) ),
	assertz( chain(V,LedName) ).
removeLed( LedName ):-
	value( staticChainLedNum,  N ),
	value( currentChainLedNum, V ),
	V > N,!,
	retract( chain(POS,LedName) ),
	stdout <- println( removeLed( chain(POS,LedName) ) ),
	dec( currentChainLedNum,1,_).
removeLed( _  ).