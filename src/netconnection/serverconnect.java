package netconnection;

/**
 *
 * @author Dmitry
 */
 
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class serverconnect {
        Socket echoSocket = null;
         PrintWriter out = null;
         BufferedReader in = null;
       
    public serverconnect()
    {
    
    }
    public String talk(String host,String port,String request)
    {
    String ret="";
        try {
            echoSocket = new Socket(host, Integer.parseInt(port));
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: "+host+".");
            return "";
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: "+host+".");
            return "";
        }
	    out.println(request);
        try {
            ret = in.readLine();
        } catch (IOException ex) {
            Logger.getLogger(serverconnect.class.getName()).log(Level.SEVERE, null, ex);
        }
	out.close();
        try {
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(serverconnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            echoSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(serverconnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    return ret;
    }
    public static void main(String[] args) throws IOException {

     serverconnect sc=new serverconnect();
     
             String ret=sc.talk("127.0.0.1", "5005", "SC -SD- dima 123456");
             System.out.println(ret);
    }
}
