package web.server;

import model.Player;
import model.UserData;
import web.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static ArrayList<Socket> players = new ArrayList<>();
    public static UserData userData;
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5521);

        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        System.out.println(ip);

        System.out.println("服务器准备就绪");
        System.out.println("服务器信息：" + server.getInetAddress() + ":" + server.getLocalPort());

        // 等待客户端连接
        while (true) {
            //得到客户端
            Socket client = server.accept();
            //客户端构建异步线程
            ClientHandler clientHandler = new ClientHandler(client);
            //启动线程
            clientHandler.start();
        }

    }
}
