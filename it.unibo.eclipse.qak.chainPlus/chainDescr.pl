chain(1, led1 ).
chain(2, led2 ).
%% chain(3, led3 ).

getLedNames(LEDNAMES) :-
	findall( NAME, chain(  _, NAME ), LEDNAMES).

getNextLedName( LedName ) :-
	value( counter,V ),
	chain(V,LedName),
	%%stdout <- println(  getNextLedName(LedName)  ),
	inc( counter,1,_).
	
resetLedCounter :- assign( counter,1  ).	 %%create value( counter,1 )

assign( I,V ):- retract( value(I,_) ),!, assert( value( I,V )).
assign( I,V ):- assert( value( I,V )).
getVal( I, V ):- value(I,V), !.
getVal( I, failure ).
inc(I,K,N):-
	value( I,V ),
	N is V + K,
	assign( I,N ).
dec(I,K,N):-
	value( I,V ),
	N is V - K,
	assign( I,N ).	