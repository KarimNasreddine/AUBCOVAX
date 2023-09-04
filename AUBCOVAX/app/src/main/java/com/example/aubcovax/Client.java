package com.example.aubcovax;

import java.net.*;
import java.io.*;


public class Client {
    String hostName="192.168.1.36";
    int portNum= 7800;
    Socket clientSocket;
    DataInputStream DIS;
    DataOutputStream DOS;

    //Run method
    public String Run(String str1) {
        try {
            System.out.println("SPOT 3");
            clientSocket= new Socket (hostName, portNum);
            System.out.println("SPOT 4");
            DIS= new DataInputStream(this.clientSocket.getInputStream());
            DOS= new DataOutputStream(this.clientSocket.getOutputStream());
            DOS.writeUTF(str1);
            DOS.flush();
            String StringReceived = DIS.readUTF();
            return StringReceived;
        }
        catch(UnknownHostException e ) {

            System.exit(1);
            return " ";
        } catch(IOException ee) {

            System.exit(1);
            return " ";
        }
    }
}
