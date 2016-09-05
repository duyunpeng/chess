package chess.core.util.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by pengyi on 2015/10/20.
 */
public class UdpClientSocket {
    private byte[] buffer = new byte[1024];

    private DatagramSocket clientSocket = null;

    /**
     * 构造函数，创建UDP客户端
     * @throws Exception
     */
    public UdpClientSocket(int port) throws Exception {
        clientSocket = new DatagramSocket(port);
    }

    /**
     * 设置超时时间，该方法必须在bind方法之后使用.
     * @param timeout 超时时间
     * @throws Exception
     */
    public final void setSoTimeout(final int timeout) throws Exception {
        clientSocket.setSoTimeout(timeout);
    }

    /**
     * 获得超时时间.
     * @return 返回超时时间
     * @throws Exception
     */
    public final int getSoTimeout() throws Exception {
        return clientSocket.getSoTimeout();
    }

    public final DatagramSocket getSocket() {
        return clientSocket;
    }

    /**
     * 向指定的服务端发送数据信息.
     * @param host 服务器主机地址
     * @param port 服务端端口
     * @param bytes 发送的数据信息
     * @return 返回构造后俄数据报
     * @throws IOException
     */
    public final DatagramPacket send(final String host, final int port,
                                     final byte[] bytes) throws IOException {
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, InetAddress
                .getByName(host), port);
        clientSocket.send(dp);
        return dp;
    }

    /**
     * 接收从指定的服务端发回的数据.
     * @param lhost 服务端主机
     * @param lport 服务端端口
     * @return 返回从指定的服务端发回的数据.
     * @throws Exception
     */
    public final String receive(final String lhost, final int lport)
            throws Exception {
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        clientSocket.receive(dp);
        String info = new String(dp.getData(), 0, dp.getLength());
        return info;
    }

    /**
     * 关闭udp连接.
     */
    public final void close() {
        try {
            clientSocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}