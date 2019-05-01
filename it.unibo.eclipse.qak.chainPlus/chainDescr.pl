chain(1, led1 ).
chain(2, led2 ).
%% chain(3, led3 ).

getLedNames(LEDNAMES) :-
	findall( NAME, chain(  _, NAME ), LEDNAMES),
	length(LEDNAMES, NUM),
	assert( staticChainLedNum(NUM) ).

getNextLedName( LedName ) :-
	value( stepcounter,V ),
	chain(V,LedName),
	stdout <- println(  getNextLedName(LedName,V)  ),
	inc( stepcounter,1,V1).
	
resetLedCounter :- assign( stepcounter,1  ).	 %%create value( counter,1 )

addLed( LedName ):-
	getVal( counter, V ),
	assertz( chain(V,LedName) ),
	outtput( addLed( chain(V,LedName) ) ),
	inc( counter,1,_).
removeLed( LedName ):-
	staticChainLedNum( N ),
	getVal( counter, V ),
	V > N,
	retract( chain(POS,LedName) ),
	dec( counter,1,_),
	%%output( removeLed( chain(POS,LedName) ) ),
	!.
removeLed( _  ).