/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes.sister.controller;

import java.awt.event.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import tubes.sister.View.Client;
import tubes.sister.View.Server;
import tubes.sister.View.View;

/**
 *
 * @author Mu'ti C Putro
 */
public class Controller implements ActionListener, FocusListener {

    private dataConnect dc;
    private View view;
//    private getIP ip;
    
    private ArrayList<String> listH;
    private ArrayList<Integer> listPot;

    int select = -1;
            
    public Controller(View view) {
        dc = new dataConnect();
        dc.connect();
        listH = new ArrayList<>();
        listPot = new ArrayList<>();
                

        this.view = view;
        this.view.setVisible(true);

        this.view.getTombolLogin().addActionListener(this);
        this.view.getPortServer().addFocusListener(this);
        this.view.getOkeServer().addActionListener(this);

        this.view.getOk().addActionListener(this);
        this.view.getPort().addFocusListener(this);
//        this.view.getIpv6().addFocusListener(this);
        this.view.getIp6().addActionListener(this);

        
        System.out.println("oke");
//        ip = new getIP();
//        System.out.println(ip.get());
        listHost();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object a = e.getSource();
        try {
            if (a.equals(this.view.getTombolLogin())) {
                if (this.view.getStatus().getSelectedItem().toString().equalsIgnoreCase("server")) {
                    this.view.show(this.view.getPanel(), "server");
                } 
                else {
                    this.view.show(this.view.getPanel(), "client");
                }
            } else if (a.equals(this.view.getOkeServer())) {//start server            
                if (dc.doQuery("UPDATE `host` SET `port`=" + this.view.getPortServer().getText() + ",`status`=1 WHERE `ip` = '" + getIP.getHostAddress()+ "';") > 0) {
                    this.view.setVisible(false);
                    new ControllerServer(new Server(), Integer.parseInt(this.view.getPortServer().getText()), dc);
                } 
                else 
                if (dc.doQuery("insert into host values ('" + getIP.getHostAddress()+ "','" + this.view.getPortServer().getText() + "'," + 1 + ",'"+getIP.getHostName()+"')") > 0) {
                    this.view.setVisible(false);
                    new ControllerServer(new Server(), Integer.parseInt(this.view.getPortServer().getText()), dc);
                }
            } else if (a.equals(this.view.getOk())) {//start client
//                String ip = this.view.getIpv6().getText();
                int port = Integer.parseInt(this.view.getPort().getText());
//                new ControllerClient(new Client(), this.view.getIp6().getSelectedItem().toString(), port, dc);
                new ControllerClient(new Client(), this.listH.get(select), port, dc);
                this.view.setVisible(false);
//                InetAddress ips = InetAddress.getByName(s);
//                Inet6Address ip6 = Inet6Address.getByAddress(s, ips.getAddress(), null);
//                System.out.println(ip6);
            } else if (a.equals(this.view.getIp6())) {
                select = this.view.getIp6().getSelectedIndex()-1;
                this.view.getPort().setText(this.listPot.get(select)+"");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error");
        }

    }

    public void listHost() {
        try {
            ResultSet rs = dc.retrieveQuery("select * from host where status = 1");
            while (rs.next()) {
                view.getIp6().addItem(rs.getString(4)+" - "+rs.getString(1));
                this.listH.add(rs.getString(1));
                this.listPot.add(new Integer(rs.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        Object focus = e.getSource();
        if (focus.equals(this.view.getPortServer())) {
            this.view.getPortServer().setText("");
        } else if (focus.equals(this.view.getPort())) {
            if (this.view.getPort().getText().equalsIgnoreCase("port")) {
                this.view.getPort().setText("");
            }
        } 
//        else if (focus.equals(this.view.getIpv6())) {
//            if (this.view.getIpv6().getText().equalsIgnoreCase("ipv6")) {
//                this.view.getIpv6().setText("");
//            }
//        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        Object focus = e.getSource();
        if (focus.equals(this.view.getPort())) {
            if (this.view.getPort().getText().equals("")) {
                this.view.getPort().setText("port");
            }
        } 
//        else if (focus.equals(this.view.getIpv6())) {
//            if (this.view.getIpv6().getText().equals("")) {
//                this.view.getIpv6().setText("IPv6");
//            }
//        }
    }

}
