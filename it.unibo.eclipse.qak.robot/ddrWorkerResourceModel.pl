/*
===============================================================
ddrWorkerResourceModel.pl
===============================================================
*/
model( type(actuator, leds),      name(led1), value(off) ).
model( type(sensor, temperature), name(t1),   value(25)  ).

getModelItem( TYPE, CATEG, NAME, VALUE ) :-
		model( type(TYPE, CATEG), name(NAME), value(VALUE) ).
changeModelItem( CATEG, NAME, VALUE ) :-
 		replaceRule( 
			model( type(TYPE, CATEG), name(NAME), value(_) ),  
			model( type(TYPE, CATEG), name(NAME), value(VALUE) ) 		
		),!,
		%%output( changedModelAction(CATEG, NAME, VALUE) ),
		( changedModelAction(CATEG, NAME, VALUE) %%to be defined by the appl designer
		  ; true ).		%%to avoid the failure if no changedModelAction is defined
		

emitevent( EVID, EVCONTENT ) :- 
	actorobj( Actor ), 
	output( eeeeemit( Actor, EVID, EVCONTENT ) ),
	Actor <- emit( EVID, EVCONTENT ).
%%%  initialize

initResourceTheory :- output("ddrWorkerResourceModel ...").
:- initialization(initResourceTheory).



/*  		
	changedModelAction( temperature, t1, V  ):-
 		    eval( ge, V , 30 ), !,  
 		    changemodelitem( leds, led1, on).		     
 	changedModelAction( temperature, t1, V  ):-	 
 			changemodelitem( leds, led1, off).     			 			
 	changedModelAction( leds, led1, V  ):-
 			emitevent( ctrlEvent, ctrlEvent( leds, led1, V) ).
 */
