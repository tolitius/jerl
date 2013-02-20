-module( erl_server ).
-define( SERVER, jerl ).
-compile( export_all ).
 
start() -> register( ?SERVER, spawn( erl_server, loop, [0] ) ).

loop( MsgCount ) ->
    receive
        { _, abomb } ->
            loop( MsgCount + 1 );
        { From, count } ->
            From ! { ok, MsgCount },
            loop( 0 );
        shutdown -> ok
    end.
