/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadManager;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author angel
 */
public class ThreadManager implements Runnable {

    private final ExecutorService executor;
    private final DatagramSocket socket;
    private DatagramPacket packet;
    private final int ECHOMAX=255;

    public ThreadManager(ExecutorService executor, DatagramSocket socket) {
        this.executor=executor;
        this.socket=socket;
    }

    public void atenderHilo() throws IOException {
        packet=new DatagramPacket(new byte[ECHOMAX], ECHOMAX);
        socket.receive(packet);
        executor.execute(new ThreadManager(executor, socket));
        
        System.out.println("Manejando cliente en: " + packet.getAddress().getHostAddress()
                + " en el puerto " + packet.getPort() + " mensaje " + new String(packet.getData()));

        String nombreArchivo = new String(packet.getData(), packet.getOffset(), packet.getLength());

        File archivo = Paths.get("archivos\\" + nombreArchivo).toFile();

        if (archivo.isFile()) {
            Scanner in = new Scanner(archivo);
            String s = "";
            try {
                s += in.nextLine();
            } catch (Exception e) {
                System.out.println("Valió algo: " + e.getMessage());
            }
            
            if (s.length() > ECHOMAX) {
                byte k = 1;
                for (int i = 0; i < s.length(); i += ECHOMAX) {
                    byte[] orden = {k};
                    packet.setData(orden);
                    socket.send(packet);
                    
                    String paquetito = s.substring(i, i + ECHOMAX);
                    packet.setData(paquetito.getBytes());
                    socket.send(packet);
                    k++;
                }
                
            } else {
                packet.setData(s.getBytes());
                socket.send(packet);
            }
            
        } else {
            packet.setData("Archivo No Encontrado".getBytes());
        }
        byte[] fin = {0};
        packet.setData(fin);
        socket.send(packet);
    }

    @Override
    public void run() {
        try {
            atenderHilo();
        } catch (IOException ex) {
            System.out.println("ERROR");
        }
    }
}