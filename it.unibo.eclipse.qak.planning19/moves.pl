%%
%% moves.pl
%%

%% move(M)  M=w|..


showMoves :- move( M ), output( move( M ) ), fail.
showMoves.			

output( M ) :- stdout <- println( M ).

wduration(0).
direction(downDir).

%-------------------------------------------------
%  TuProlo FEATURES of the QActor mind
%-------------------------------------------------
dialog( FileName ) :-  
	java_object('javax.swing.JFileChooser', [], Dialog),
	Dialog <- showOpenDialog(_),
	Dialog <- getSelectedFile returns File,
	File <- getName returns FileName. 		 


initMoveKb :- output("moves kb loaded").
:- initialization(initMoveKb).


