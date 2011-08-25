# What is "Jerl"? ##

"Jerl" is starting out as a set of simple examples and metrics on Java and Erlang talking to each other.

## Sopwith Camel

The example consists out of two components: 

* Java Erlang Server => 'JerlServer.java'

A very simple "while(true){...}" server that utilizes "com.ericsson.otp.erlang.*" Erlang built in "Jinterface" library ["http://www.erlang.org/doc/apps/jinterface/java/index.html"](http://www.erlang.org/doc/apps/jinterface/java/index.html)

* Erlang Bombardier => 'sopwith_camel.erl'

'Sopwith Camel' was a British World War I single-seat biplane fighter that was credited with shooting down 1,294 enemy aircraft, more than any other Allied fighter in the First World War.

The only difference beteen Sopwith Camel fighter and 'sopwith_camel.erl' is the 'sopwith_camel.erl' drops only friendly bombs. Think about each bomb being a soft mood booster, or .. a $100 bill that falls on you right from the sky.

```erlang
bomb_it( 0 )-> ok; 
bomb_it( N ) -> 
    { jerl, 'jerl@icloudx' } ! { self(), abomb }, 
    bomb_it( N-1 ).
```

### How Do I Try It?

* Compile JerlServer, start Erland server node for JerlServer to use and start JerlServer:

```bash
$ javac -cp .:/usr/local/lib/erlang/lib/jinterface-1.5.4/priv/OtpErlang.jar JerlServer.java 
$ erl -sname jerl -detached
$ java -cp .:/usr/local/lib/erlang/lib/jinterface-1.5.4/priv/OtpErlang.jar JerlServer
Jerl is ready to take a bomb...

```

* Specify your hostname in 'sopwith_camel.erl'

```erlang
bomb_it( 0 )-> ok; 
bomb_it( N ) -> 
    { jerl, 'jerl@YOUR_HOST_NAME' } ! { self(), abomb }, 
    bomb_it( N-1 ).
```

* Run Erlang interpeter, compile 'sopwith_camel', and start bombardiering:

```bash
$ erl -sname sopwith_camel
```
```erlang
Erlang R14B02 (erts-5.8.3) [source] [smp:4:4] [rq:4] [async-threads:0] [hipe] [kernel-poll:false]

Eshell V5.8.3  (abort with ^G)
(sopwith_camel@icloudx)1> c(sopwith_camel).
{ok,sopwith_camel}
(sopwith_camel@icloudx)2> timer:tc(sopwith_camel, bomb_it, [1000]).  
{17811,ok}
(sopwith_camel@icloudx)3> timer:tc(sopwith_camel, bomb_it, [10000]).
{64968,ok}
(sopwith_camel@icloudx)4> timer:tc(sopwith_camel, bomb_it, [100000]).
{671625,ok}
(sopwith_camel@icloudx)5> timer:tc(sopwith_camel, bomb_it, [140000]).
{1033616,ok}
(sopwith_camel@icloudx)9>
```

The above time results are in microseconds => roughly I can send 140,000 messages a second from a single Erlang process to a single JerlServer ( MacBook Pro, Dual Core i7 2.8GHz, 8GB RAM )
