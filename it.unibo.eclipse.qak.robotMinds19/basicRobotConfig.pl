robot( virtual, "8999" ).  %%the port is the default used by clientWenvObjTcp.kt
%%robot( realmbot, "COM6" ).  %% /dev/ttyUSB0
%% robot( realnano, "" ).

 
/*
CONFIGURATION PARAMS FOR THE sonar PIPE
*/
limitDistance(12).
minDistance( 2 ).
maxDistance( 50 ).
maxDelta( 1 ).
amplif( 6	).   %%%sonar data amplification for the radar, that does D/3

%% USED IN mbotSupport via sonardatafilter
modeReact( oop ).
%% modeReact( pipe ).
%% modeReact( model ).