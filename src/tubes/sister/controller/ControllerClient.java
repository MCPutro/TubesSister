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
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;
import tubes.sister.View.Client;
import tubes.sister.View.View;

/**
 *
 * @author Mu'ti C Putro
 */
public class ControllerClient implements ActionListener,Runnable{

    private Socket client;
//    private BufferedReader chat = new BufferedReader(new InputStreamReader(System.in));
    
    private final Client clientView;
    private final String address;
    private final int port;
    
    private Thread t;

    public ControllerClient(Client clientView, String address, int port) {
        this.clientView = clientView;
        this.address = address;
        this.port = port;
        
        this.clientView.setVisible(true);
        this.clientView.getReplayBox().addActionListener(this);
        
        
        this.t = new Thread(this);
        t.start();
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {//input 
        Object o = e.getSource();
        if(o.equals(this.clientView.getReplayBox())){
            try {
                PrintWriter output = new PrintWriter(client.getOutputStream(),true);                
                String pes = this.clientView.getReplayBox().getText();
                output.println(pes);
                clientView.append(pes);
                this.clientView.getReplayBox().setText("");
                if(pes.equalsIgnoreCase("exit")){
                    client.close();
                    close();
                }
            } catch (Exception ex) {
            }
        }
    }

    private void close(){
        if(t.isAlive()){
            t.interrupt();
        }
        JOptionPane.showMessageDialog(null, "Disconnect !");
        this.clientView.setVisible(false);
        System.exit(1);
    }
    
    @Override
    public void run() {
        try {
            this.client = new Socket(this.address, this.port);
            Scanner input = new Scanner(client.getInputStream());
            System.out.println("client konek");
            String replay, pesan;
            do {                
                replay = input.nextLine();
                System.out.println("pesan masuk : "+replay);
                this.clientView.append("\n-----------------\n"+replay+"\n-----------------\n");
            } while (!"exit".equalsIgnoreCase(replay));
            client.close();
            this.close();
        } catch (Exception e) {
        }
    }
    
}
