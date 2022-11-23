import java.io.*;

public class Write {
    private FileOutputStream fileOutputStream;
    private BufferedWriter bufferedWriter;
    public PrintWriter printWriter;
    private OutputStreamWriter outputStreamWriter;

    Write(OutputStream outputStream) {
        outputStreamWriter = new OutputStreamWriter(outputStream);
        bufferedWriter = new BufferedWriter(outputStreamWriter);
        printWriter = new PrintWriter(bufferedWriter);
    }

    Write(File file) throws IOException {
        fileOutputStream = new FileOutputStream(file);
        outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        this.bufferedWriter = new BufferedWriter(outputStreamWriter);
        printWriter = new PrintWriter(bufferedWriter);
    }

    public void close() throws IOException {
        printWriter.flush();
        printWriter.close();
        bufferedWriter.close();
        outputStreamWriter.close();
        fileOutputStream.close();
    }

    public void flush() throws IOException {
        printWriter.flush();
    }
}
