package tcp;

import chess.game.landlords.listener.LandlordsTcpService;
import org.testng.annotations.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by yjh on 16-6-13.
 */
public class Landlords {

    @Test
    public void tcpClient() {
        System.out.println(LandlordsTcpService.userClients.size());
    }

}
