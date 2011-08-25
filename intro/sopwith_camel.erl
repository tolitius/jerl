-module( sopwith_camel ). 
-export( [bomb_it/1] ). 

% will recursively drop 'N' bombs ( messages ) to whoever is listening
bomb_it( 0 )-> ok; 
bomb_it( N ) -> 
    { jerl, 'jerl@icloudx' } ! { self(), abomb }, 
    bomb_it( N-1 ).
