/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author angel
 */
public class ThreadManager implements Runnable {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private int ECHOMAX;

    public ThreadManager(DatagramSocket socket, DatagramPacket packet, int ECHOMAX) {
        this.socket = socket;
        this.packet = packet;
        this.ECHOMAX = ECHOMAX;
        run();
    }

    public void atenderHilo() throws IOException {
        
        System.out.println("Manejando cliente en: " + packet.getAddress().getHostAddress()
                + " en el puerto " + packet.getPort() + " mensaje " + new String(packet.getData()));

        packet.setData("Asi es pa".getBytes());
        socket.send(packet);
        packet.setLength(ECHOMAX);
        
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
