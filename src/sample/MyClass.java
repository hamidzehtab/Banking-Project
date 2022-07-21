package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyClass {
    public static String NationalCode = "";
    public static String Server(int getOrSend,int type,String user,String pass,String data){
        String output = "";
        try (Socket socket = new Socket("localhost", 990)) {

            // writing to server
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            // object of scanner class
            String line = getOrSend + "sep" + type + "sep" + user + "sep" + pass + "sep" + data;

                out.println(line);
                out.flush();

            output = in.readLine() ;
            System.out.println("Server replied "
                    + output);


        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

}
