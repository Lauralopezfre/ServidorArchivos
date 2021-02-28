/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author laura
 */
public class Servidor {
    private static final int ECHOMAX = 255;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        if(args.length != 1){
            throw new IllegalArgumentException("Parameter(s): <Port>");
        }
        int port = Integer.parseInt(args[0]);
        
        DatagramSocket socket = new DatagramSocket(port);
        DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);
        
        while(true){
            socket.receive(packet);
            System.out.println("Manejando cliente en: " + packet.getAddress().getHostAddress() + " en el puerto " + packet.getPort());
            
            socket.send(packet);
            packet.setLength(ECHOMAX);
            
        }
    }
    
}
