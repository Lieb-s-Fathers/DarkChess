package io;

import java.io.*;

public class Read {
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

    public int nextInt() throws IOException {
        streamTokenizer.nextToken();
        return (int) streamTokenizer.nval;
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
        fileInputStream.close();
    }
}