/*
===============================================================
ddrWorkerResourceModel.pl
===============================================================
*/
 
model( actuator, robot, state(stopped) ). %% initial state

action(robot, move(w)) :- changeModel( actuator, robot, movingForward  ).
action(robot, move(s)) :- changeModel( actuator, robot, movingBackward ).
action(robot, move(a)) :- changeModel( actuator, robot, rotateLeft     ).
action(robot, move(d)) :- changeModel( actuator, robot, rotateRight    ).
action(robot, move(h)) :- changeModel( actuator, robot, stopped        ).


changeModel( CATEG, NAME, VALUE ) :-
   replaceRule( model(C,N,_),  model(C,N,state(VALUE)) ),
   showResourceModel.	%% at each change, show the model
				
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
		
		
		