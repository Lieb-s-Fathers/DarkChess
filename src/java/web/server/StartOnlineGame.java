package web.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class StartOnlineGame extends Thread {

    public StartOnlineGame(Socket player1, Socket player2) {
        try {
            //得到打印流，用于数据输出：服务器回送数据
            //得到输入流，用于接受数据
            System.out.println(player2.getInetAddress() + " " + player2.getPort());
            System.out.println(player1.getInetAddress() + " " + player1.getPort());


            PrintStream[] socketOutputs = {new PrintStream(player1.getOutputStream()), new PrintStream(player2.getOutputStream())};
            BufferedReader[] socketInputs = {new BufferedReader(new InputStreamReader(player1.getInputStream())), new BufferedReader(new InputStreamReader(player2.getInputStream()))};
            socketOutputs[0].println("start");
            socketOutputs[1].println("start");
            System.out.println("start");
            socketOutputs[0].println("RED");
            System.out.println("Red");
            socketOutputs[1].println("BLACK");
            System.out.println("Black");

            //客户端拿到一条数据
            int currentPlayer = 0;
            String str = "";
            boolean flag = true;
            do {
                flag = true;
                if (currentPlayer == 0) {
                    str = socketInputs[0].readLine();
                    socketOutputs[1].println(str);
                    flag = check(str);
                    System.out.println(str);
                } else {
                    str = socketInputs[1].readLine();
                    socketOutputs[0].println(str);
                    flag = check(str);
                    System.out.println(str);
                }
                if (flag) currentPlayer = (currentPlayer + 1) % 2;
            } while ("finish".equals(str));

            socketInputs[0].close();
            socketInputs[1].close();
            socketOutputs[0].close();
            socketOutputs[1].close();

        } catch (IOException e) {
            System.out.println("连接异常2");
            throw new RuntimeException(e);
        } finally {
            try {
                player1.close();
                player2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("客户端已退出：" + player1.getInetAddress() + ":" + player1.getPort());
        System.out.println("客户端已退出：" + player2.getInetAddress() + ":" + player2.getPort());
    }

    private boolean check(String str) {
        return !"cheat".equals(str);
    }

}

