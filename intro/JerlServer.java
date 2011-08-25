import com.ericsson.otp.erlang.*;

/**
 *  <p> Java / Erlang Server that is listening for messages passed to 'jerlAThostname' </p>
 *
 *  <p> Can be started as: 
 *
 *          "java -cp .:[path_to_erlang]/lib/jinterface-1.5.4/priv/OtpErlang.jar JerlServer" </p>
 *  <em>      
 *          for example on OS/X:  
 *          "java -cp .:/usr/local/lib/erlang/lib/jinterface-1.5.4/priv/OtpErlang.jar JerlServer"</em>
 **/
public class JerlServer {

    public static void main( String[] args ) throws Exception {

        OtpNode erlangNode = new OtpNode( "jerl" );
        OtpMbox mailBox = erlangNode.createMbox( "jerl" );

        OtpErlangObject erlangMessage;

        System.out.println( "Jerl is ready to take a bomb..." );
        while( true ) {

            erlangMessage = mailBox.receive();
            // System.out.println( " received.... => " + erlangMessage.toString() );
        }
    }
}
