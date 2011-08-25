## Jerl: Bomb Count Example
    
A build up of the "Intro" example that counts received messages on a Java side and sends the count back to the Erlang process.
The example consists out of two components: 

### Java Erlang Server => [JerlServer.java](https://github.com/anatoly-polinsky/jerl/blob/master/bomb_count/JerlServer.java)

A very simple "while(true){...}" server that utilizes Erlang built in [Jinterface library](http://www.erlang.org/doc/apps/jinterface/java/index.html) to receive / parse / send messages
In this example JerlServer does nothing with messages it receives.

JerlServer recives all the messages and counts them until a message { _, count } sent to it. 
Once it receives this message it replies to an Erlang process with the count of all messages it received since the last time a { _, count } message was sent to it.

### Erlang Bombardier => [sopwith_camel.erl](https://github.com/anatoly-polinsky/jerl/blob/master/bomb_count/sopwith_camel.erl)

"Sopwith Camel" was a British World War I single-seat biplane fighter that was credited with shooting down 1,294 enemy aircraft, more than any other Allied fighter in the First World War.

The only difference between Sopwith Camel fighter and 'sopwith_camel.erl' is the 'sopwith_camel.erl' drops only friendly bombs. Think about each bomb being a soft mood booster, or .. a $100 bill that falls on you right from the sky.

```erlang
% asks Jerl how many bombs were received, and reports back
bomb_it( 0 ) -> ... 

% recursively drops friendly 'N' bombs ( messages ) to whoever is listening
bomb_it( N ) -> ... 
```

### How Do I Ride the Camel?

* Compile JerlServer, start Erlang Port Mapper Daemon and start JerlServer:

```bash
$ javac -cp .:/usr/local/lib/erlang/lib/jinterface-1.5.4/priv/OtpErlang.jar JerlServer.java 
$ /usr/local/lib/erlang/bin/epmd &
$ java -cp .:/usr/local/lib/erlang/lib/jinterface-1.5.4/priv/OtpErlang.jar JerlServer
Jerl is ready to take a bomb...

```

###### _EPMD is started automatically with any Erlang interpreter, so if there is a live interpreter up, it is already started_

* Specify your hostname in "sopwith_camel.erl"

```erlang
bomb_it( N ) -> 
    { jerl, 'jerl@YOUR_HOST_NAME' } ! { self(), abomb }, 
    ... ...
```

* Run Erlang interpeter, compile "sopwith_camel", and start bombardiering:

```bash
$ erl -sname sopwith_camel
```
```erlang
Erlang R14B02 (erts-5.8.3) [source] [smp:4:4] [rq:4] [async-threads:0] [hipe] [kernel-poll:false]

Eshell V5.8.3  (abort with ^G)
(sopwith_camel@icloudx)1> c(sopwith_camel).
{ok,sopwith_camel}
(sopwith_camel@icloudx)2> timer:tc(sopwith_camel, bomb_it, [1000]).
jerl received 1000 bombs
{12592,ok}
(sopwith_camel@icloudx)3> timer:tc(sopwith_camel, bomb_it, [10000]).
jerl received 10000 bombs
{131846,ok}
(sopwith_camel@icloudx)4> timer:tc(sopwith_camel, bomb_it, [85000]).
jerl received 85000 bombs
{1033570,ok}
(sopwith_camel@icloudx)5>
```

The above time results are in microseconds => roughly I can send and receive 85,000 messages a second from a single Erlang process to a single JerlServer ( MacBook Pro, Dual Core i7 2.8GHz, 8GB RAM )
