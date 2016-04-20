/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes.sister.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JOptionPane;
import tubes.sister.View.Server;
import tubes.sister.View.View;

/**
 *
 * @author Mu'ti C Putro
 */
public class ControllerServer implements ActionListener, Runnable {

    private ServerSocket ss;
    private Socket client;
//    private final BufferedReader chat = new BufferedReader(new InputStreamReader(System.in));

    private final Server server;
    private final int port;
    private Thread t;

    private dataConnect dc;

    public ControllerServer(Server server, int port, dataConnect dc) {
        this.server = server;
        this.port = port;
        this.server.setVisible(true);
        this.server.getReplayBox().addActionListener(this);

        this.t = new Thread(this);
        t.start();

        this.dc = dc;
        this.load();
    }

    private void close() {
        try {
            if (t.isAlive()) {

                t.interrupt();
                JOptionPane.showMessageDialog(null, "Disconnect !");
                String query = "UPDATE `host` SET `status`= 0 WHERE `ip` = '" + getIP.getHostAddress() + "';";
                dc.doQuery(query);
                this.server.dispose();
                Controller c = new Controller(new View());
            }
        } catch (Exception e) {
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {//input 
        Object o = e.getSource();
        if (o.equals(this.server.getReplayBox())) {
            try {
                PrintWriter output = new PrintWriter(client.getOutputStream(), true);
                String pes = this.server.getReplayBox().getText();
                output.println(pes);
                server.append(pes);
                this.server.getReplayBox().setText("");
                String query = "INSERT INTO `tubesSister`(`pengirim`, `penerima`, `tanggal`, `pesan`) VALUES ("
                        + "'Server',"
                        + "'Client',"
                        + "'" + getTanggal() + "',"
                        + "'" + pes + "');";
                dc.doQuery(query);
                if (pes.equalsIgnoreCase("exit")) {
//                    JOptionPane.showMessageDialog(null, "cek");
                    dc.doQuery("");
                    client.close();
                    ss.close();
                    close();

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
//    public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_RED = "\u001B[31m";

    @Override
    public void run() {
        try {
            this.ss = new ServerSocket(port);
            this.client = ss.accept();
            JOptionPane.showMessageDialog(null, "client konek");

            Scanner input = new Scanner(client.getInputStream());
            String replay = "";
            try {
                do {
                    replay = input.nextLine();
//                    System.out.println("pesan masuk : "+replay);
                    String s = "---------------------------------------------------------------------------------------------------\n"
                            + "from : Client\n"
                            + getTanggal()
                            + "\npesan : \n"
                            + replay
                            + "\n---------------------------------------------------------------------------------------------------";

                    String query = "INSERT INTO `tubesSister`(`pengirim`, `penerima`, `tanggal`, `pesan`) VALUES ("
                            + "'Client',"
                            + "'Server',"
                            + "'" + getTanggal() + "',"
                            + "'" + replay + "');";
                    dc.doQuery(query);

                    server.append(s);
                } while (!"exit".equalsIgnoreCase(replay));
            } catch (Exception e) {
            }
            client.close();
            ss.close();
            close();

        } catch (Throwable e) {
            e.printStackTrace();
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
                if (rs.getString(1).equalsIgnoreCase("client")) {
                    String s = "---------------------------------------------------------------------------------------------------\n"
                            + "from : Client\n"
                            + rs.getString(3) + "\n"
                            + "pesan : \n"
                            + rs.getString(4) + "\n"
                            + "---------------------------------------------------------------------------------------------------\n";
                    this.server.getShowMessage().append(s);
//                    System.out.println(s);
                } else {
                    this.server.getShowMessage().append(rs.getString(4) + "\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
