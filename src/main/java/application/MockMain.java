package application;

import java.io.*;

public class MockMain {

    public static void main(String[] args) throws IOException {

        ProcessBuilder pb = new ProcessBuilder("./src/main/resources/scripts/tshark_script.sh");

        var pb2 = pb.redirectErrorStream(true);

        var process = pb2.start();

//        var process = pb.start();

        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;


        while ((line = br.readLine()) != null) {
            System.out.println(line);
            System.out.println("Next line");
        }

    }

}