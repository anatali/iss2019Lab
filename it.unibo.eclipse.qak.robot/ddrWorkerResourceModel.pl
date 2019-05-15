/*
===============================================================
ddrWorkerResourceModel.pl
===============================================================
*/
 
model( actuator, robot, state(stopped) ).

action(robot, move(w)) :- changeModel( actuator, robot, state(movingForward)  ).
action(robot, move(s)) :- changeModel( actuator, robot, state(movingBackward) ).
action(robot, move(a)) :- changeModel( actuator, robot, state(rotateLeft)     ).
action(robot, move(d)) :- changeModel( actuator, robot, state(rotateRight)    ).
action(robot, move(h)) :- changeModel( actuator, robot, state(stopped)        ).


changeModel( CATEG, NAME, state(VALUE) ) :-
 		replaceRule( 
			model( CATEG,  NAME, state(_) ),  
			model( CATEG,  NAME, state(VALUE) ) 		
		),
		showResourceModel.
				
showResourceModel :- 
	stdout <- print("[ "),
	model( CATEG, NAME, STATE ),
 	stdout <- print( model( CATEG, NAME, STATE ) ),
	stdout <- println(" ]").
 			

dialog( FileName ) :-  
	java_object('javax.swing.JFileChooser', [], Dialog),
	Dialog <- showOpenDialog(_),
	Dialog <- getSelectedFile returns File,
	File <- getName returns FileName. 		 

output( M ) :- stdout <- println( M ).


initResourceTheory :- output("ddrWorkerResourceModel").
:- initialization(initResourceTheory).
		
		
		