/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tubes.sister.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JOptionPane;
import tubes.sister.View.Client;

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

    private dataConnect dc;
    
    public ControllerClient(Client clientView, String address, int port, dataConnect dc) {
        this.clientView = clientView;
        this.address = address;
        this.port = port;
        
        this.clientView.setVisible(true);
        this.clientView.getReplayBox().addActionListener(this);
        
//        dc = new dataConnect();
//        dc.connect();
        this.dc = dc;
        
        this.t = new Thread(this);
        t.start();
        load();
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
                String query = "INSERT INTO `tubesSister`(`pengirim`, `penerima`, `tanggal`, `pesan`) VALUES ("
                        + "'Client',"
                        + "'Server',"
                        + "'"+getTanggal()+"',"
                        + "'"+pes+"');";
//                dc.doQuery(query);
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
        dc.close();
        JOptionPane.showMessageDialog(null, "Disconnect !");
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
                System.out.println("from  : "+replay);
                String s = "---------------------------------------------------------------------------------------------------\n"
                        + "From : Server\n"
                        + getTanggal()
                        + "\npesan : \n"
                        + replay+"\n---------------------------------------------------------------------------------------------------";
//                this.clientView.append("\n-----------------\n"+replay+"\n-----------------\n");
                this.clientView.append(s);
            } while (!"exit".equalsIgnoreCase(replay));
            client.close();
            this.close();
        } catch (Exception e) {
        }
    }
    
    private String getTanggal() {  
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");  
        Date date = new Date();  
        return dateFormat.format(date);  
    }
    
    private void load() {
        try {
            ResultSet rs = dc.retrieveQuery("select * from tubesSister;");
            while (rs.next()) {
                if (rs.getString(1).equalsIgnoreCase("server")) {
                    String s = "---------------------------------------------------------------------------------------------------\n"
                            + "from : Server\n"
                            + rs.getString(3)+"\n"
                            + "pesan : \n"
                            + rs.getString(4)+"\n"
                            + "---------------------------------------------------------------------------------------------------\n";
                    this.clientView.getShowMessage().append(s);
//                    System.out.println(s);
                }
                else
                {
                    this.clientView.getShowMessage().append(rs.getString(4)+"\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
