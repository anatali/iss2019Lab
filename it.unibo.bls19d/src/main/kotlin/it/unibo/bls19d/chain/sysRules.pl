%==============================================
% DEFINED BY THE S
% CONTEXT HANDLING UTILTY RULESYSTEM DESIGNER
%==============================================
getCtxNames(CTXNAMES) :-
	findall( NAME, context( NAME, _, _, _ ), CTXNAMES).
getCtxPortNames(PORTNAMES) :-
	findall( PORT, context( _, _, _, PORT ), PORTNAMES).
getTheContexts(CTXS) :-
	findall( context( CTX, HOST, PROTOCOL, PORT ), context( CTX, HOST, PROTOCOL, PORT ), CTXS).
getTheActors(ACTORS) :-
	findall( qactor( A, CTX ), qactor( A, CTX, CLASS ), ACTORS).

%% Feb 2019
getOtherContexts(OTHERCTXS, MYSELF) :-
	findall( 
		context( CTX, HOST, PROTOCOL, PORT ), 
		(context( CTX, HOST, PROTOCOL, PORT ), CTX \== MYSELF), 	 
		OTHERCTXS
	).
getOtherContextNames(OTHERCTXS, MYSELF) :-
	findall(
		CTX,
		(context( CTX, HOST, PROTOCOL, PORT ), CTX \== MYSELF),
		OTHERCTXS
	).
/*
	stdout <- println(CTXS),
	'$tolist'(CTXS,OTHERCTXS),
	stdout <- println( aaa(OTHERCTXS) ).
*/
	
getTheActors(ACTORS,CTX) :-
	findall( qactor( A, CTX, CLASS ), qactor( A, CTX, CLASS ),   ACTORS).
getActorNames(ACTORS,CTX) :-
    findall( NAME, qactor( NAME, CTX, CLASS ),   ACTORS).

getCtxHost( NAME, HOST )  :- context( NAME, HOST, PROTOCOL, PORT ).
getCtxPort( NAME,  PORT ) :- context( NAME, HOST, PROTOCOL, PORT ).
getCtxProtocol( NAME,  PROTOCOL ) :- context( NAME, HOST, PROTOCOL, PORT ).

getMsgId( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , MSGID  ).
getMsgType( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , MSGTYPE ).
getMsgSenderId( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , SENDER ).
getMsgReceiverId( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , RECEIVER ).
getMsgContent( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , CONTENT ).
getMsgNum( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , SEQNUM ).

checkMsg( MSG, MSGLIST, PLANLIST, RES ) :- 
	%%stdout <- println( checkMsg( MSG, MSGLIST, PLANLIST, RES ) ),
	checkTheMsg(MSG, MSGLIST, PLANLIST, RES).	
checkTheMsg( MSG, [], _, dummyPlan ).
checkTheMsg( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ), [ MSGID | _ ], [ PLAN | _ ], PLAN ):-!.
checkTheMsg( MSG, [_|R], [_|P], RES ):- 
	%%stdout <- println( checkMsg( MSG, R, P, RES ) ),
	checkTheMsg(MSG, R, P, RES).

msgContentToPlan( MSG, CONTENTLIST,PLANLIST,RES ):-
	%stdout <- println( msgContentToPlan( MSG, CONTENTLIST,PLANLIST,RES) ),
	msgContentToAPlan( MSG, CONTENTLIST,PLANLIST,RES ).
msgContentToAPlan( MSG, [], _, dummyPlan ).
msgContentToAPlan( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ), [ CONTENT | _ ], [ PLAN | _ ], PLAN ):-!.
msgContentToAPlan( event(  CONTENT  ), [ CONTENT | _ ], [ PLAN | _ ], PLAN ):-!.
msgContentToAPlan( MSG, [_|R], [_|P], RES ):- 
	%stdout <- println( msgContentToAPlan( MSG, R, P, RES ) ),
	msgContentToPlan(MSG, R, P, RES).	



/*
--------------------------------
DEC2018
--------------------------------
*/
%% version 1.5.13.1
removeCtx( CtxName, HOST, PORT ) :-
	%% stdout <- println( removeCtx(  CtxName ) ),
	retract( context( CtxName, HOST, _ , PORT ) ),!,
	removeAllActors( CtxName ).
	 
removeAllActors( CtxName ) :-
	retract( qactor( NAME, CtxName, _ ) ),
	removeAllActors( CtxName ).
removeAllActors( CtxName ).  

showSystemConfiguration :-
	stdout <- println("&&&&&&&&&&&&&&&&&&SysRules&&&&&&&&&&&&&&&&&&&&"),
  	getTheContexts(CTXS),
	stdout <- println('CONTEXTS IN THE SYSTEM:'),
	showElements(CTXS),
	stdout <- println('ACTORS   IN THE SYSTEM:'),
	getTheActors(ACTORS),
	showElements(ACTORS),
	stdout <- println("&&&&&&&&&&&&&&&&&&SysRules&&&&&&&&&&&&&&&&&&&&").
 
showElements(ElementListString):- 
	text_term( ElementListString, ElementList ),
 	showListOfElements(ElementList).
showListOfElements([]).
showListOfElements([C|R]):-
	stdout <- println( C ),
	showElements(R).
	
%==============================================
% MEMENTO
%==============================================
%%% :- stdout <- println( hello ).
%%% --------------------------------------------------
% context( NAME, HOST, PROTOCOL, PORT )
% PROTOCOL : "TCP" | "UDP" | "SERIAL" | "PROCESS" | ...
%%% --------------------------------------------------

%%% --------------------------------------------------
% msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
% MSGTYPE : dispatch request answer
%%% --------------------------------------------------

