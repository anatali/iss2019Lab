/*
neuronConnkb.pl
*/

inputName( 1, i1).
inputName( 2, i2).
inputName( 3, i3).

atleastTwo(I1,I2,I3):- I1,I2,!. 
atleastTwo(I1,I2,I3):- I1,I3,!. 	
atleastTwo(I1,I2,I3):- I2,I3,!. 	 

firing :-
	inputName( 1,I1),
	inputName( 2,I2),
 	inputName( 3,I3),
 	atleastTwo(I1,I2,I3).

setConnection(N,I):-
	%% stdout <- println(setConnection( N,I )),
	addRule( connectedTo(N,I) ).

prepareConnectionsToSend :-
	findall( link(N,I), connectedTo(N,I), CONNLIST),
	%% stdout <- println(prepareConnectionsToSend( CONNLIST )),
	setTempConnection(CONNLIST).

setTempConnection( [] ).
setTempConnection( [LINK|R] ):-
	%% stdout <- println(setTempConnection( LINK )),
	addRule( LINK ),
 	setTempConnection(R).


update( INPUT,on )  :- inputName(INPUT,V), addRule( V ).
update( INPUT,off ) :- inputName(INPUT,V), removeRule( V ).


 
initneuronConnkb  :- stdout <- println("	--- neuronConnkb loaded --- ").

:- initialization( initneuronConnkb ).
 