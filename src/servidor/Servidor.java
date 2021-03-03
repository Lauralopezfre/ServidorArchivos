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
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author laura
 */
public class Servidor {

    private static final int ECHOMAX = 255;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        int port = 7171;
        ExecutorService executor = Executors.newFixedThreadPool(10);

        DatagramSocket socket = new DatagramSocket(port);
        DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

        while (true) {
            socket.receive(packet);
            System.out.println("Manejando cliente en: " + packet.getAddress().getHostAddress()
                    + " en el puerto " + packet.getPort());

            String nombreArchivo = new String(packet.getData(), packet.getOffset(), packet.getLength());
            
            File archivo = Paths.get("archivos\\"+nombreArchivo).toFile();

            
            
            if (archivo.isFile()) {
                Scanner in=new Scanner(archivo);
                String s="";
                try{
                    s+=in.nextLine();
                }catch(Exception e){
                    System.out.println("Valió algo: "+e.getMessage());
                }
                packet.setData(s.getBytes());
            } else {
                packet.setData("Archivo No Encontrado".getBytes());
            }

            socket.send(packet);
        }
    }

}
