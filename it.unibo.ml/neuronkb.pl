/*
neuronkb.pl
*/

fire :- i1,i2,!.
fire :- i1,i3,!.
fire :- i2,i3,!.


update( INPUT, on, firing ) :- 
	%% stdout <- println(update( INPUT,on )),
	addRule( INPUT ),
	fire,!,
	stdout <- println("	--- neuronkb fires --- ").
update( INPUT, on, notfiring ) :- 
	stdout <- println("	--- neuronkb DOES NOT fire --- ").
update( INPUT,off, notfiring ) :- 
	removeRule( INPUT ).

%i1
%i2

initNeuronNet  :- stdout <- println("	--- neuronkb loaded --- ").

:- initialization( initNeuronNet ).