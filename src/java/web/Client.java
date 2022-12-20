package web;

import model.ChessColor;
import model.Player;
import model.UserData;

import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

import static view.ModeSelection.onlineWaitFrame;


public class Client {
    private Socket socket;
    private UserData userData = new UserData();
    //得到Socket输出流，并转换为打印流
    private OutputStream outputStream;
    private PrintStream socketPrintStream;
    //得到Socket输入流，并转换为BufferedReader
    private InputStream inputStream;
    private BufferedReader socketBufferedReader;

    public Client()  {
        try {
            //连接本地，端口号5521
//            socket = new Socket("106.55.199.29", 5521);
            socket = new Socket();
            socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 5521), 3000);

            //超时时间
            socket.setSoTimeout(3000);
            outputStream = socket.getOutputStream();
            socketPrintStream = new PrintStream(outputStream);
            inputStream = socket.getInputStream();
            socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "服务器未连接");
            throw new RuntimeException(e);
        }

        System.out.println("已发起服务器连接，并进入后续流程");
        System.out.println("客户端信息："+socket.getLocalSocketAddress()+":"+socket.getLocalPort());
        System.out.println("服务器信息："+socket.getInetAddress()+":"+socket.getPort());
    }

    public UserData getUserData() throws IOException {
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

    public void startOnlineGame() throws IOException {
        String str = "addOnlinePlayer";
        socketPrintStream.println(str);


        //todo: 连接服务器
        new Thread(()->{
            try {
                socket.setSoTimeout(600000);
                String str1 = socketBufferedReader.readLine();
                if ("start".equals(str1)){
                    str1 = socketBufferedReader.readLine();
                    if ("RED".equals(str1)){
                       onlineWaitFrame.newOnlineGame(ChessColor.RED);
                    }
                    if ("BLACK".equals(str1)) {
                        onlineWaitFrame.newOnlineGame(ChessColor.BLACK);
                    }

                    socketPrintStream.close();
                    socketBufferedReader.close();
                }
            } catch (IOException e) {
                try {
                    JOptionPane.showMessageDialog(null, "连接超时");
                    socket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                throw new RuntimeException(e);
            }

        }).start();


//        socket.close();
//        System.out.println("客户端已退出");
    }

    public String getStep() throws IOException {
        String str = socketBufferedReader.readLine();
        while ("cheat".equals(str)) {
            str = socketBufferedReader.readLine();
        }
        return str;
    }

    public void sendStep(String str){
        socketPrintStream.println(str);
    }

    public void close() throws IOException {
        //释放资源
        System.out.println("客户端已经退出");
    }
}
