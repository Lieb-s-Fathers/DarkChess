package web.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static web.server.Server.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private boolean flag = true;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        super.run();
        System.out.println("新客户端连接：" + socket.getInetAddress() + ":" + socket.getPort());

        try {
            //得到打印流，用于数据输出：服务器回送数据
            PrintStream socketOutput = new PrintStream(socket.getOutputStream());
            //得到输入流，用于接受数据
            BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //客户端拿到一条数据
            String str = socketInput.readLine();
            if ("getUserData".equals(str)) {
//                Read in = new Read("/www/wwwroot/DarkChess/userData.txt");
                Read in = new Read("src/resources/userData.txt");
                String tempData = in.nextLine();
                while (tempData != null) {
                    socketOutput.println(tempData);
                    System.out.println(tempData);
                    tempData = in.nextLine();
                }
                flag = false;
            }

            if ("saveUserData".equals(str)) {
//                Write out = new Write("/www/wwwroot/DarkChess/userData.txt");
                Write out = new Write("src/resources/userData.txt");
                System.out.println("刷新排行榜");
                String tempData = socketInput.readLine();
                while (tempData != null) {
                    out.printWriter.println(tempData);
                    tempData = socketInput.readLine();
                }
                out.flush();
                out.close();
                flag = false;
            }

            if ("addOnlinePlayer".equals(str)) {
                players.add(socket);
                if (players.size() == 2) {
                    StartOnlineGame onlineGame = new StartOnlineGame(players.get(0), players.get(1));
                    onlineGame.start();
                    players = new ArrayList<>();
                }
            }

//            socketInput.close();
//            socketOutput.close();

        } catch (IOException e) {
            System.out.println("连接异常1");
            throw new RuntimeException(e);
        } finally {
            if (!flag){
                try {
                    System.out.println("close Socket");
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("客户端已退出：" + socket.getInetAddress() + ":" + socket.getPort());
            }
        }
    }


    private class Read {
        private BufferedReader bufferedReader;
        private StreamTokenizer streamTokenizer;
        private File inFile;
        private FileInputStream fileInputStream;

        public Read(String inFilePath) {
            try {
                this.inFile = new File(inFilePath);
                this.fileInputStream = new FileInputStream(inFile);
                this.bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                this.streamTokenizer = new StreamTokenizer(bufferedReader);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public String nextLine() throws IOException {
            return bufferedReader.readLine();
        }

        public void close() throws IOException {
            bufferedReader.close();
            fileInputStream.close();
        }
    }

    private class Write {

        private File file;
        private FileOutputStream fileOutputStream;
        private BufferedWriter bufferedWriter;
        public PrintWriter printWriter;
        private OutputStreamWriter outputStreamWriter;

        public Write(String filePath) {
            try {
                file = new File(filePath);
                fileOutputStream = new FileOutputStream(file);
                outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                this.bufferedWriter = new BufferedWriter(outputStreamWriter);
                printWriter = new PrintWriter(bufferedWriter);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public void close() {
            printWriter.flush();
            printWriter.close();
            try {
                bufferedWriter.close();
                outputStreamWriter.close();
                fileOutputStream.close();
            } catch (IOException ignored) {
            }

        }

        public void flush() {
            try {
                bufferedWriter.flush();
                outputStreamWriter.flush();
                printWriter.flush();
            } catch (IOException ignored) {

            }
        }
    }
}