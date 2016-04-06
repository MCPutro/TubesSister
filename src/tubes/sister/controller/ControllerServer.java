/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tubes.sister.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;
import tubes.sister.View.Server;
import tubes.sister.View.View;

/**
 *
 * @author Mu'ti C Putro
 */
public class ControllerServer implements ActionListener,Runnable{

    private ServerSocket ss;
    private Socket client;
//    private final BufferedReader chat = new BufferedReader(new InputStreamReader(System.in));
    
    private final Server server;
    private final int port;
    private Thread t;

    public ControllerServer(Server server, int port) {
        this.server = server;
        this.port = port;
        this.server.setVisible(true);
        this.server.getReplayBox().addActionListener(this);
        
        
        this.t = new Thread(this);
        t.start();
    }
    
    private void close(){
        if(t.isAlive()){
            t.interrupt();
        }
        
        JOptionPane.showMessageDialog(null, "Disconnect !");
        this.server.setVisible(false);
        Controller c = new Controller(new View());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {//input 
        Object o = e.getSource();
        if(o.equals(this.server.getReplayBox())){
            try {
                PrintWriter output = new PrintWriter(client.getOutputStream(),true);                
                String pes = this.server.getReplayBox().getText();
                output.println(pes);
                server.append(pes);
                this.server.getReplayBox().setText("");
                if(pes.equalsIgnoreCase("exit")){
                    client.close();
                    ss.close();
                    close();
                    
                }
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public void run() {
        try {
            this.ss = new ServerSocket(port);
//            System.out.println("sever on ");
            this.client = ss.accept();
//            System.out.println("client konek");
            JOptionPane.showMessageDialog(null, "client konek");
                    
            Scanner input = new Scanner(client.getInputStream());
            String replay = "";
            try {
                do {                
                    replay = input.nextLine();
//                    System.out.println("pesan masuk : "+replay);
                    server.append("\n-----------------\n"+replay+"\n-----------------\n");
                } while (!"exit".equalsIgnoreCase(replay));
            } catch (Exception e) {}
            client.close();
            ss.close();
            close();
            
            
            
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    
    
    
    
}
