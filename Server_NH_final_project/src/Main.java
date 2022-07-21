import javafx.scene.control.Alert;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import java.util.Properties;
import javax.mail.*;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;

class Mailer{
    public static String send(String from,String password,String to,String sub,String msg){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,password);
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {return "-1";}
        return "1";
    }
}

// Server class
class Server {
    public static String checkAccount(String NationalCode,String user,String pass){
        File file=new File("data\\"+NationalCode+".txt");
        int numberOfAccount = 0 ;

        String s="";

        try {
            Scanner scan = new Scanner(file);
            // header

            scan.next() ;
            scan.next() ;
            scan.next() ;
            scan.next() ;
            scan.next() ;
            scan.next() ;
            // number of account
            s=scan.next();
            numberOfAccount = Integer.parseInt(s);
            for(int i=1;i<=numberOfAccount;i++){
                String AccN = scan.next();
                String Ammount = balance(AccN,0);
                String Alias = scan.next();
                scan.next();
                scan.next();
                String State = scan.next();
                String pass1 = scan.next();
                if(Ammount.equals(""))Ammount="0";
                if(AccN.equals(user)){
                    if(pass1.equals(pass)){
                        return "1";
                    }else{
                        return "0";
                    }
                }
            }
            scan.close();
        }
        catch(Exception ex) {
            return "-1";
        }
        return "-1";
    }
    public static String transfer(String user,String Destination, String Amount,String data1,String data2){
        String answer = "" ;

        // input

        File file=new File("data\\"+"log"+".txt");
        int numberOfAccount = 0 ;

            String s="";
         if(file.exists()) {
             try {
                 Scanner scan = new Scanner(file);
                 while (scan.hasNext()) {
                     answer += scan.next() + "\n";
                     answer += scan.next() + "\n";
                     answer += scan.next() + "\n";
                     answer += scan.next() + "\n";
                     answer += scan.next() + "\n";
                     answer += scan.next() + "\n";
                 }

                 scan.close();
             } catch (Exception ex) {
                 return "-1";
             }

         }


        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String strDate = dateFormat.format(date);
        String strTime = timeFormat.format(date);

        String Date = strDate + "-" + strTime ;
        answer += user + "\n" ;
        answer += Destination + "\n" ;
        answer += Date + "\n";
        answer += Amount + "\n" ;
        answer += data1 + "\n" ;
        answer += data2 + "\n" ;


        // output
        try {
            File file1 = new File("data\\"+"log"+".txt");
                FileWriter writer = new FileWriter(file1);
                writer.write(answer);
                writer.close();


        }
        catch(FileNotFoundException exc) {
            return "-1";
        }
        catch(Exception exe) {
            return "-1";
        }

        return "1";
    }


    public static String signUp(String data){
        String[] dataSplit = data.split("www");
        String nationalCode = dataSplit[0];
        String name = dataSplit[1];
        String email = dataSplit[2];
        String pass = dataSplit[3];
        String phone = dataSplit[4];

        File file1 = new File("data\\"+nationalCode+".txt");
        //String bal ="";
        //for(int i=0;i<7;i++) {
        //  bal = scan1.next();
        //}
        try {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            String strDate = dateFormat.format(date);
            String strTime = timeFormat.format(date);

            String Date = strDate + "-" + strTime ;
            //File file = new File("accounts\\"+name+".txt");
            //File file = new File("C:\\Users\\FRSDR\\Documents\\GitHub\\NH-Final-project\\data\\"+nameField.getText()+".txt");
            File file = new File("data\\"+nationalCode+".txt");
            if(file.createNewFile()) {
                FileWriter writer = new FileWriter(file);

                writer.write(nationalCode+"\n"+pass+"\n"+name+"\n"+phone+"\n"+email+"\n"+Date+"\n"+"0");
                writer.close();
                return "1";
            }
            else {
                return "0";
            }

        }
        catch(FileNotFoundException exc) {
            return "-1";
        }
        catch(Exception exe) {
            return "-1";
        }
    }

    public static String balance(String AccN,int change){
        File file=new File("data\\"+"balance"+".txt");
        int res = -1 ;
        String write = "";
        try {
            Scanner scan1 = new Scanner(file);
            while(scan1.hasNext()) {
                String Account = scan1.next();
                String Amount = scan1.next();
                write += Account + "\n";
                if (Account.equals(AccN)) {
                    int value = Integer.parseInt(Amount);
                    value += change ;
                    res = value;
                    write += value + "\n" ;
                }else{
                    write += Amount + "\n" ;
                }
            }
            scan1.close();
        }
        catch(Exception ex) {
            return ex.toString();
        }
        // output
        try {
            //File file = new File("accounts\\"+name+".txt");
            //File file = new File("C:\\Users\\FRSDR\\Documents\\GitHub\\NH-Final-project\\data\\"+nameField.getText()+".txt");
            File file1 = new File("data\\"+"balance"+".txt");
            if(!file1.createNewFile()) {

                FileWriter writer = new FileWriter(file1);
                writer.write(write);
                writer.close();
            }
            else {
                return "-1";
            }

        }
        catch(FileNotFoundException exc) {
            return "-1";
        }
        catch(Exception exe) {
            return "-1";
        }

        return res+"";
    }
    public static String removeBalance(String AccN){
        File file=new File("data\\"+"balance"+".txt");
        int res = -1 ;
        String write = "";
        try {
            Scanner scan1 = new Scanner(file);
            while(scan1.hasNext()) {
                String Account = scan1.next();
                String Amount = scan1.next();

                if (Account.equals(AccN)) {

                }else{
                    write += Account + "\n";
                    write += Amount + "\n" ;
                }
            }
            scan1.close();
        }
        catch(Exception ex) {
            return ex.toString();
        }
        // output
        try {
            //File file = new File("accounts\\"+name+".txt");
            //File file = new File("C:\\Users\\FRSDR\\Documents\\GitHub\\NH-Final-project\\data\\"+nameField.getText()+".txt");
            File file1 = new File("data\\"+"balance"+".txt");
            if(!file1.createNewFile()) {

                FileWriter writer = new FileWriter(file1);
                writer.write(write);
                writer.close();
            }
            else {
                return "-1";
            }

        }
        catch(FileNotFoundException exc) {
            return "-1";
        }
        catch(Exception exe) {
            return "-1";
        }

        return res+"";
    }
    public static String ReadLogIn(String user,String pass){
        File file=new File("data\\"+user+".txt");
        if(!file.exists()) {
            return "0";
        }
        else {
            String s="";
            try {
                Scanner scan = new Scanner(file);
                s =scan.next();
                s =scan.next();
                scan.close();
            }
            catch(Exception ex) {
                return "-1";
            }
            if(s.equals(pass)) {
                return "1";

            }
            else {
                return "0";

            }

        }

    }
    public static String ReadClientArea(String user){

        File file=new File("data\\"+user+".txt");
        int numberOfAccount = 0 ;
        String answer = "" ;

        String s="";

        try {
            Scanner scan = new Scanner(file);
            // header

            scan.next() ;
            scan.next() ;
            scan.next() ;
            scan.next() ;
            scan.next() ;
            scan.next() ;
            // number of account
            s=scan.next();
            numberOfAccount = Integer.parseInt(s);
            for(int i=1;i<=numberOfAccount;i++){
                String AccN = scan.next();
                String Ammount = balance(AccN,0);
                String Alias = scan.next();
                String Check = scan.next();
                scan.next();
                String State = scan.next();
                scan.next();
                if(Ammount.equals(""))Ammount="0";
                if(State.equals("1"))answer+=AccN+"sep"+Alias+"sep"+Ammount+"sep"+Check+"sep";
                //if(State.equals("1") )list.add(AccN + "\t\t" + Alias + "\t\t" + Ammount);
            }
            scan.close();
            return answer;
        }
        catch(Exception ex) {
            return "-1";
        }

    }
    public static String ReadLog(String user){

        File file=new File("data\\"+"log"+".txt");
        int numberOfAccount = 0 ;
        String answer = "" ;

        String s="";

        try {
            if(!file.exists()) {
            }
            else {

                try {
                    Scanner scan = new Scanner(file);
                    // header
                    while (scan.hasNext()) {
                        String Acc1 = scan.next();
                        String Acc2 = scan.next();
                        String time = scan.next();
                        String amount = scan.next();
                        String data1 = scan.next();
                        String data2 = scan.next();


                        if(Acc1.equals(user)){
                            answer += "(-)"+"www" + Acc1 + "www" + Acc2 + "www" + amount + "www" + time + "www" + data1 + "www" + data2 + "www" ;
                        }
                        if(Acc2.equals(user)){
                            answer += "(+)"+"www" + Acc1 + "www" + Acc2 + "www" + amount + "www" + time + "www" + data1 + "www" + data2 + "www";
                        }
                    }
                    scan.close();
                }
                catch(Exception ex) {
                    return "-1";

                }
            }
            return answer;
        }
        catch(Exception ex) {
            return "-1";
        }

    }
    public static String CloseAccount(String user,String data){
        // input

        File file=new File("data\\"+user+".txt");
        int numberOfAccount = 0 ;
        String answer = "" ;
        if(!file.exists()) {
            return "-1";
        }
        else {
            String s="";

            try {
                Scanner scan = new Scanner(file);
                // header

                answer += scan.next() + "\n";
                answer += scan.next() + "\n";
                answer += scan.next() + "\n";
                answer += scan.next() + "\n";
                answer += scan.next() + "\n";
                answer += scan.next() + "\n";
                // number of account
                s=scan.next();
                numberOfAccount = Integer.parseInt(s);
                answer += numberOfAccount + "\n" ;
                for(int i=1;i<=numberOfAccount;i++){
                    String Account = scan.next();
                    answer += Account + "\n" ;
                    answer += scan.next() + "\n";
                    answer += scan.next() + "\n";
                    answer += scan.next() + "\n";
                    if (Account.equals(data)) {
                        scan.next() ;
                        answer += "0" + "\n" ;
                    }else{
                        answer += scan.next() + "\n";
                    }
                    answer += scan.next() + "\n";
                }

                scan.close();
            }
            catch(Exception ex) {
                return "-1";
            }
        }

        // output
        try {
            File file1 = new File("data\\"+user+".txt");
            if(!file1.createNewFile()) {
                FileWriter writer = new FileWriter(file1);

                writer.write(answer);
                writer.close();
                return "1";
            }
            else {
                return "-1";
            }

        }
        catch(FileNotFoundException exc) {
            return "-1";
        }
        catch(Exception exe) {
            return "-1";
        }
    }
    public static String NewAccount(String user,String data){
        int accno = 234 ;
        double r= Math.random();
        accno = 1000000000 + (int)(r * 999999999);

        String[] dataSplit = data.split("www");

        // input

        File file=new File("data\\"+user+".txt");
        int numberOfAccount = 0 ;
        String answer = "" ;
        if(!file.exists()) {
            return "-1";
        }
        else {
            String s="";

            try {
                Scanner scan = new Scanner(file);
                // header

                answer += scan.next() + "\n";
                answer += scan.next() + "\n";
                answer += scan.next() + "\n";
                answer += scan.next() + "\n";
                answer += scan.next() + "\n";
                answer += scan.next() + "\n";
                // number of account
                s=scan.next();
                numberOfAccount = Integer.parseInt(s);
                numberOfAccount ++ ;
                answer += numberOfAccount + "\n" ;
                for(int i=1;i<numberOfAccount;i++){
                    answer += scan.next() + "\n";
                    answer += scan.next() + "\n";
                    answer += scan.next() + "\n";
                    answer += scan.next() + "\n";
                    answer += scan.next() + "\n";
                    answer += scan.next() + "\n";
                }


                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                String strDate = dateFormat.format(date);
                String strTime = timeFormat.format(date);

                String Date = strDate + "-" + strTime ;
                answer += accno + "\n" ;
                answer += dataSplit[1] + "\n" ;
                answer += dataSplit[0] + "\n" ;
                answer += Date + "\n";
                answer += "1" + "\n" ;
                answer += dataSplit[3]+ "\n" ;

                scan.close();
            }
            catch(Exception ex) {
                return "-1";
            }
        }

        // output
        try {
            //File file = new File("accounts\\"+name+".txt");
            //File file = new File("C:\\Users\\FRSDR\\Documents\\GitHub\\NH-Final-project\\data\\"+nameField.getText()+".txt");
            File file1 = new File("data\\"+user+".txt");
            if(!file1.createNewFile()) {

                FileWriter writer = new FileWriter(file1);
                writer.write(answer);
                writer.close();
            }
            else {
                return "-1";
            }

        }
        catch(FileNotFoundException exc) {
            return "-1";
        }
        catch(Exception exe) {
            return "-1";
        }






        file=new File("data\\"+"balance"+".txt");
        answer = "" ;
        if(!file.exists()) {
        }
        else {
            String s="";

            try {
                Scanner scan = new Scanner(file);
                // header
                while (scan.hasNext()) {
                    answer += scan.next() + "\n";
                    answer += scan.next() + "\n";
                }
                scan.close();
            }
            catch(Exception ex) {
                return "-1";

            }
        }
        answer += accno + "\n";
        answer += dataSplit[2] + "\n";

        // output
        try {
            //File file = new File("accounts\\"+name+".txt");
            //File file = new File("C:\\Users\\FRSDR\\Documents\\GitHub\\NH-Final-project\\data\\"+nameField.getText()+".txt");
            File file1 = new File("data\\"+"balance"+".txt");

            FileWriter writer = new FileWriter(file1);
            writer.write(answer);
            writer.close();

        }
        catch(FileNotFoundException exc) {
            return "-1";
        }
        catch(Exception exe) {
            return "-1";
        }
        return accno+"";

    }
    public static String Loan(String user,String pass,String accountNumber,String loan,String amount){
        File file = new File("data\\"+"Loan"+".txt");
        int type = 0;
        if(loan.equals("30s"))type=3;
        if(loan.equals("60s"))type=6;
        if(loan.equals("120s"))type=12;
        if(loan.equals("240s"))type=240;
        int value = Integer.parseInt(amount);
        value /= type ;
        String write = "";
        if(file.exists()) {
            try {
                Scanner scan = new Scanner(file);
                while (scan.hasNext()) {
                    String Account = scan.next();
                    String Amount = scan.next();
                    String date = scan.next();
                    write += Account + "\n";
                    write += Amount + "\n";
                    write += date + "\n";
                }
                scan.close();
            } catch (Exception ex) {
                return ex.toString();
            }
        }
        for(int i=1;i<=type;i++){
            write += accountNumber + "\n" ;
            write += value + "\n";
            Date Now = new Date();
            write += (Now.getTime() + type * 10000)+"\n";
         }
        // output
        try {
            //File file = new File("accounts\\"+name+".txt");
            //File file = new File("C:\\Users\\FRSDR\\Documents\\GitHub\\NH-Final-project\\data\\"+nameField.getText()+".txt");
            File file1 = new File("data\\"+"Loan"+".txt");
            file1.createNewFile();

                FileWriter writer = new FileWriter(file1);
                writer.write(write);
                writer.close();


        }
        catch(FileNotFoundException exc) {
            return "-1";
        }
        catch(Exception exe) {
            return "-1";
        }

        return "1";
    }

    public static void main(String[] args)
    {
        ServerSocket server = null;

        try {

            server = new ServerSocket(990);
            server.setReuseAddress(true);

            while (true) {

                System.out.println("Waiting for Client ...");
                Socket client = server.accept();

                System.out.println("New client connected" + client.getInetAddress().getHostAddress());

                ClientHandler clientSock = new ClientHandler(client);

                new Thread(clientSock).start();
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException ed) {
                    ed.printStackTrace();
                }
            }
        }

    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }

        public void run()
        {
            PrintWriter out = null;
            BufferedReader in = null;
            try {

                out = new PrintWriter(
                        clientSocket.getOutputStream(), true);

                in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));

                String line;
                line = in.readLine();

                    System.out.println(" Sent from the client: "+line+"\n");

                    String[] x = line.split("sep");
                    int getOrSend = Integer.parseInt(x[0]);
                    int type = Integer.parseInt(x[1]);

                    String user = x[2] ,pass = x[3];
                    String data = x[4];
                    System.out.println(type);
                    if(type==1){
                        String out1 = ReadLogIn(user,pass) ;
                        System.out.println(out1);

                        out.println(out1);
                    }
                    if(type==2){
                        String out1 = ReadClientArea(user) ;
                        System.out.println(out1);
                        out.println(out1);
                    }
                    if(type==3){
                        String out1 = NewAccount(user,data) ;
                        System.out.println(out1);
                        out.println(out1);
                    }
                    if(type==4){
                        String[] dataSplit = data.split("www");
                        String accountNumber = dataSplit[0];
                        String password = dataSplit[1];
                        String destination = dataSplit[2];
                        String check = checkAccount(user,accountNumber,password);
                        if(check.equals("1")) {
                            if (!balance(destination, 0).equals("-1")) {

                                String out1 = CloseAccount(user, accountNumber);
                                int val = Integer.parseInt(balance(accountNumber, 0));
                                String out2 = transfer(accountNumber, destination, val + "", "Null", "Null");

                                removeBalance(accountNumber);
                                balance(destination, val);
                                System.out.println(out1);
                                out.println(out1);
                            }else{
                                System.out.println("-2");
                                out.println("-2");
                            }
                        }else{
                            System.out.println(check);
                            out.println(check);
                        }

                    }
                    if(type==5){
                        String out1 = signUp(data) ;
                        System.out.println(out1);
                        out.println(out1);
                    }
                    if(type==6){

                        String[] dataSplit = data.split("www");
                        String origin = dataSplit[0];
                        String Destination = dataSplit[1];
                        String Amount = dataSplit[2];
                        int value = Integer.parseInt(Amount);
                        int AccountValue = Integer.parseInt(balance(origin,0));

                        String res = checkAccount(user,origin,pass);



                        if(res.equals("1")) {
                            if (!balance(Destination, 0).equals("-1")){

                                if (AccountValue >= value) {
                                    String out1 = transfer(origin, Destination, Amount, dataSplit[3], dataSplit[4]);
                                    String outb1 = "-1", outb2 = "-1";
                                    if (!out1.equals("-1")) {
                                        outb1 = balance(origin, -value);
                                        outb2 = balance(Destination, value);

                                        //System.out.println("1");
                                        //out.println("1");
                                    }
                                    if (outb2.equals("-1")) {
                                        System.out.println("-2");
                                        out.println("-2");
                                    } else {
                                        System.out.println(out1);
                                        out.println(out1);
                                    }
                                } else {
                                    System.out.println("-3");
                                    out.println("-3");
                                }
                            }else{
                                System.out.println("-2");
                                out.println("-2");
                            }
                        }else{
                            System.out.println(res);
                            out.println(res);
                        }


                    }
                    if(type==7){
                        String out1 = ReadLog(user) ;
                        System.out.println(out1);
                        out.println(out1);
                    }
                    if(type==8){
                        int code = 234 ;
                        double r= Math.random();
                        code = 10000 + (int)(r * 9999);
                        String out1 = Mailer.send("NHfinalproject@gmail.com","pcqkrhp8",data,"Password code","Welcome to NH Final project\ncode :"+code);
                        if(out1.equals("1"))out1=code+"";
                        System.out.println(out1);
                        out.println(out1);
                    }
                    if(type==9){
                        String[] dataSplit = data.split("www");
                        String accountNumber = dataSplit[0];
                        String password = dataSplit[1];
                        String loan = dataSplit[2];
                        String amount = dataSplit[3];

                        String check = checkAccount(user,accountNumber,password);
                        if(check.equals("1")) {
                            String out2 = transfer("government",  accountNumber, amount + "", "Null", "Null");
                            balance(accountNumber, Integer.parseInt(amount));
                            String out1 = Loan(user, pass, accountNumber,loan,amount);
                            System.out.println(out1);
                            out.println(out1);
                        }else{
                            System.out.println("0");
                            out.println("0");
                        }
                    }
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                }
                catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                }
                catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
        }
    }
}
