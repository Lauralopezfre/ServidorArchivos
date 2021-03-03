/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author laura
 */
public class Servidor {

    private static final int ECHOMAX = 9;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        int port = 7171;
        ExecutorService executor = Executors.newFixedThreadPool(10);

        DatagramSocket socket = new DatagramSocket(port);
        DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);
        
        //File archivotxt = new File("/archivos/examen.txt");
        
        
        
        while (true) {
            socket.receive(packet);
            System.out.println("Manejando cliente en: " + packet.getAddress().getHostAddress() + 
                    " en el puerto " + packet.getPort());
            
            packet.setData("Asi es pa".getBytes());
            socket.send(packet);
            packet.setLength(ECHOMAX);
        }
    }

}
