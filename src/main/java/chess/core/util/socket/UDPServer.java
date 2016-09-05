package chess.core.util.socket;

/**
 * Created by pengyi on 2016/1/14.
 */
public class UDPServer extends UDPAgent {
    public static void main(String[] args) throws Exception {
        new UDPServer(2008).start();
    }
    public UDPServer(int port) {
        super(port);
    }
}
