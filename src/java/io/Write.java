package io;

import java.io.*;

public class Write {

    public static String defaultOutFilePath = System.getProperty("user.dir").replace("\\", "/") + "/save";
    public static String defaultOutFile = defaultOutFilePath + "/save.txt";
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
