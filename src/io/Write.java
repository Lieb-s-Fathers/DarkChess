package io;

import java.io.*;
public class Write {

    public static String defaultOutFilePath = System.getProperty("user.dir").replace("\\","/")+"save.out";

    private File file;
    private FileOutputStream fileOutputStream;
    private BufferedWriter bufferedWriter;
    public PrintWriter printWriter;
    private OutputStreamWriter outputStreamWriter;

//    Write(OutputStream outputStream) {
//        outputStreamWriter = new OutputStreamWriter(outputStream);
//        bufferedWriter = new BufferedWriter(outputStreamWriter);
//        printWriter = new PrintWriter(bufferedWriter);
//    }

    public Write(String filePath) {

        try {
            file = new File(filePath);
            fileOutputStream = new FileOutputStream(file, true);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            this.bufferedWriter = new BufferedWriter(outputStreamWriter);
            printWriter = new PrintWriter(bufferedWriter);
        } catch (FileNotFoundException e) {
            //todo File not Found

        }
    }

    public void close() {
        printWriter.flush();
        printWriter.close();
        try {
            bufferedWriter.close();
            outputStreamWriter.close();
            fileOutputStream.close();
        } catch (IOException e) {
        }

    }

    public void flush() {
        try {
            bufferedWriter.flush();
            outputStreamWriter.flush();
            printWriter.flush();
        }catch (IOException e) {

        }
    }
}
