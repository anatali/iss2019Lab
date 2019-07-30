%%
%% butlerplansapriori.pl
%%

%% The butler starts each plan in the home position 
plan( prepare, [fridge, table, paintry, table, home] ). 
plan( add,     [fridge, table, home] ). 					 
plan( clear,   [table, fridge, table, dishwasher, home] ).   
plan( clearnofood, [table, dishwasher, home] ).              


storePlan( ACTION ):-
	plan( ACTION, P ),
	output( plan( ACTION, P ) ),
	store( P ).
	
store([]).
store([TARGET|R]) :- assert(butlerMove(TARGET)), store(R).

consume(TARGET,X,Y,DIRECTION):- 
	retract( butlerMove(TARGET) ),
	position(TARGET,X,Y,DIRECTION).

position( home,       0, 0, downDir ).
position( fridge,     6, 0, upDir   ).
position( table,      5, 3, leftDir ).
position( paintry,    1, 4, downDir ).
position( dishwasher, 5, 4, downDir ).


output( M ) :- stdout <- println( M ).

%-------------------------------------------------
%  TuProlog FEATURES 
%-------------------------------------------------
dialog( FileName ) :-  
	java_object('javax.swing.JFileChooser', [], Dialog),
	Dialog <- showOpenDialog(_),
	Dialog <- getSelectedFile returns File,
	File <- getName returns FileName. 		  


butlerplansaprioriinit :- output( "butlerplansapriori LOADED" ).
:- initialization( butlerplansaprioriinit ).
