package web;

import model.Player;
import model.UserData;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;


public class Client {
    private Socket socket;

    public Client()  {
        try {
            socket = new Socket("106.55.199.29", 5521);
//            socket = new Socket();
//            socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 5521), 3000);

            //超时时间
            socket.setSoTimeout(3000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //连接本地，端口号2000，超时时间3000ms
//        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 2000), 3000);

        System.out.println("已发起服务器连接，并进入后续流程");
        System.out.println("客户端信息："+socket.getLocalSocketAddress()+":"+socket.getLocalPort());
        System.out.println("服务器信息："+socket.getInetAddress()+":"+socket.getPort());

//        try {
//            //发送接收数据
//            todo(socket);
//        } catch (IOException e) {
//            System.out.println("异常关闭");
//        }
    }




    public UserData getUserData() throws IOException {
        UserData userData = new UserData();
        //得到Socket输出流，并转换为打印流
        OutputStream outputStream = socket.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);
        //得到Socket输入流，并转换为BufferedReader
        InputStream inputStream = socket.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str = "getUserData";
        socketPrintStream.println(str);

        String tempData = socketBufferedReader.readLine();
        while (tempData != null) {
            Player tempPlayer = new Player(tempData);
            userData.getPlayers().add(tempPlayer);
            tempData = socketBufferedReader.readLine();
        }

        socket.close();
        socketPrintStream.close();
        socketBufferedReader.close();
        System.out.println("客户端已退出");
        return userData;
    }

    public void saveUserData(UserData userData) throws IOException {

        //得到Socket输出流，并转换为打印流
        OutputStream outputStream = socket.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);
        //得到Socket输入流，并转换为BufferedReader
        InputStream inputStream = socket.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str = "saveUserData";
        socketPrintStream.println(str);

        for (Player player : userData.getPlayers()) {
            socketPrintStream.println(player);
        }

        socketPrintStream.close();
        socketBufferedReader.close();
        socket.close();
        System.out.println("客户端已退出");
    }

    public void close() throws IOException {
        //释放资源
//        this.close();
        System.out.println("客户端已经退出");
    }
}
