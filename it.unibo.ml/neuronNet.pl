/*
neuronNet.pl
*/

/*
%% c = a or b
connected(a,c,1).
connected(a,c,2).
connected(b,c,2).
connected(b,c,3).
*/

%% c = a and b
connected(a,c,1).
connected(b,c,2).
 


inputName(c,1,ci1).
inputName(c,2,ci2).
inputName(c,3,ci3).

output(N,on):-
	connected(N,D,I),
	inputName(D,I,C),
	addRule( C ),
	%%propagateTo(D),
	fail.
output(N,on).

output(N,off):-
	connected(N,D,I),
	inputName(D,I,C),
	removeRule( C ),
	%%propagateTo(D),
	fail.
output(N,off).

%%propagate(D,I) :- forward D -m s : s(I).
 	
		
firing(N):-
	inputName(N,1,I1),
	inputName(N,2,I2),
 	inputName(N,3,I3),
 	atleastTwo(I1,I2,I3).
 	%%,!,
 	%%output(N,on).
%%firing(N):-output(N,off).
 	
atleastTwo(I1,I2,I3):- I1,I2,!. 
atleastTwo(I1,I2,I3):- I1,I3,!. 	
atleastTwo(I1,I2,I3):- I2,I3,!. 	 

 
initNeuronNet  :- stdout <- println("	--- neuronkb loaded --- ").

:- initialization( initNeuronNet ).