-module( sopwith_camel ). 
-export( [bomb_it/1] ). 

% asks Jerl how many bombs were received, and reports back
bomb_it( 0 )-> 

    { jerl, 'jerl@icloudx' } ! { self(), count }, 

    receive
        { ok, MessageCount } -> io:format("jerl received ~p bombs~n", [MessageCount]);
        Garbage              -> io:format("jerl sent garbage back: ~p~n", [Garbage])

    after 1500 ->
        io:format( "got tired of waiting for jerl.. ~n" )
    end; 

% recursively drops friendly 'N' bombs ( messages ) to whoever is listening
bomb_it( N ) -> 
    { jerl, 'jerl@icloudx' } ! { self(), abomb }, 
    bomb_it( N-1 ).
