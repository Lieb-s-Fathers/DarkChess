import java.io.*;

public class Read {
    private BufferedReader bufferedReader;
    private StreamTokenizer streamTokenizer;

    Read(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.streamTokenizer = new StreamTokenizer(bufferedReader);
    }

    public int nextInt() throws IOException {
        streamTokenizer.nextToken();
        return (int)streamTokenizer.nval;
    }

    public double nextDouble() throws IOException {
        streamTokenizer.nextToken();
        return streamTokenizer.nval;
    }

    public String nextLine() throws IOException {
        return bufferedReader.readLine();
    }

    public void close() throws IOException {
        bufferedReader.close();
    }
}