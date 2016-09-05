package tcp;

import chess.game.landlords.listener.LandlordsTcpService;
import org.testng.annotations.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by yjh on 16-6-13.
 */
public class ThreeCard {

    @Test
    public void tcpClient() {
        System.out.println(LandlordsTcpService.userClients.size());
    }

    @Test
    public void connection() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9002);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("{\"type\":\"1\",\"data\":\"1\"}");
        dos.writeUTF("{\"type\":\"2\",\"data\":{\"multiple\":\"10\"}}");
        while (true) {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String message = dataInputStream.readUTF();
            System.out.println(message);
        }
    }

    @Test
    public void connection1() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9002);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("{\"type\":\"1\",\"data\":\"2\"}");
        dos.writeUTF("{\"type\":\"2\",\"data\":{\"multiple\":\"10\"}}");
        while (true) {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String message = dataInputStream.readUTF();
            System.out.println(message);
        }
    }

    @Test
    public void connection2() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9002);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("{\"type\":\"1\",\"data\":\"3\"}");
        dos.writeUTF("{\"type\":\"2\",\"data\":{\"multiple\":\"10\"}}");
        while (true) {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String message = dataInputStream.readUTF();
            System.out.println(message);
        }
    }

    @Test
    public void connection3() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9002);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("{\"type\":\"1\",\"data\":\"4\"}");
        dos.writeUTF("{\"type\":\"2\",\"data\":{\"multiple\":\"10\"}}");
        while (true) {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String message = dataInputStream.readUTF();
            System.out.println(message);
        }
    }

    @Test
    public void connection4() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9002);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("{\"type\":\"1\",\"data\":\"5\"}");
        dos.writeUTF("{\"type\":\"2\",\"data\":{\"multiple\":\"10\"}}");
        while (true) {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String message = dataInputStream.readUTF();
            System.out.println(message);
        }
    }

}
