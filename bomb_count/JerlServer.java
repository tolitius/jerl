import com.ericsson.otp.erlang.*;

/**
 *  <p> Java / Erlang Server that is listening for messages passed to 'jerlAThostname' </p>
 *
 *  <p> Can be started as: 
 *
 *          "java -cp .:[path_to_erlang]/lib/jinterface-1.5.4/priv/OtpErlang.jar JerlServer" </p>
 *  <em>      
 *          e.g. on OS/X:
 *          "java -cp .:/usr/local/lib/erlang/lib/jinterface-1.5.4/priv/OtpErlang.jar JerlServer"</em>
 **/
public class JerlServer {

    public static void main( String[] args ) throws Exception {

        OtpNode erlangNode = new OtpNode( "jerl" );
        OtpMbox mailBox = erlangNode.createMbox( "jerl" );
        OtpErlangPid mailBoxErlangProcessId = mailBox.self();

        OtpErlangTuple erlangMessage;

        long messageCounter = 0;

        System.out.println( "Jerl is ready to take a bomb..." );
        while( true ) {

            erlangMessage = ( OtpErlangTuple ) mailBox.receive();
            //System.out.println( "DEBUG: received.... => " + erlangMessage.toString() );

            messageCounter++;

            // patternt matching.. "{ _, count } ->"
            if ( "count".equals( erlangMessage.elementAt(1).toString() ) ) {

                replyToErlangProcess( mailBox, erlangMessage, new OtpErlangLong( --messageCounter ) );
                // reseting a counter
                messageCounter = 0;
            }
        }
    }

    /** 
     * <p> Replies back to a caller with an {ok, Reply} tuple by translating a raw Erlang message to a tuple,
     *     getting a caller id and sending a message to it.</p>
     *
     * @param receivedMessage: should be in a tuple in a form of { callerPid, message }
     * @param replyMessage:    any instance of the OtpErlangObject family
     */
    private static void replyToErlangProcess( OtpMbox mailBox,
                                              OtpErlangTuple receivedMessage,
                                              OtpErlangObject replyMessage ) {

        OtpErlangPid callerPid = ( OtpErlangPid ) receivedMessage.elementAt( 0 );

        OtpErlangObject[] reply = new OtpErlangObject[2];
        reply[0] = new OtpErlangAtom("ok");
        reply[1] = replyMessage;
        OtpErlangTuple replyTuple = new OtpErlangTuple( reply );

        // reply back to a caller
        mailBox.send( callerPid, replyTuple );
    }
}
