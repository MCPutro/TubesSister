/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tubes.sister.controller;

import java.awt.event.*;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import tubes.sister.View.Client;
import tubes.sister.View.Server;
import tubes.sister.View.View;

/**
 *
 * @author Mu'ti C Putro
 */
public class Controller implements ActionListener, FocusListener{

    
    View view;

    public Controller(View view) {
        this.view = view;
        this.view.setVisible(true);
        
        this.view.getTombolLogin().addActionListener(this);
        this.view.getPortServer().addFocusListener(this);
        this.view.getOkeServer().addActionListener(this);
        
        this.view.getOk().addActionListener(this);
        this.view.getPort().addFocusListener(this);
        this.view.getIpv6().addFocusListener(this);
        
        System.out.println("oke");
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        Object a = e.getSource();
        try {
            if(a.equals(this.view.getTombolLogin())){
                if(this.view.getStatus().getSelectedItem().toString().equalsIgnoreCase("server")){
                    this.view.show(this.view.getPanel(), "server");
                }
                else
                    this.view.show(this.view.getPanel(), "client");
            }
            else
            if(a.equals(this.view.getOkeServer())){//start server            
                this.view.setVisible(false);
                new ControllerServer(new Server(), Integer.parseInt(this.view.getPortServer().getText()));
            }
            else
            if(a.equals(this.view.getOk())){//start client
                String ip = this.view.getIpv6().getText();
                int port = Integer.parseInt(this.view.getPort().getText());
                new ControllerClient(new Client(), ip, port);
                this.view.setVisible(false);
//                InetAddress ip = InetAddress.getByName(s);
//                Inet6Address ip6 = Inet6Address.getByAddress(s, ip.getAddress(), null);
//                System.out.println(ip6);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error");
        }
        
    }

    @Override
    public void focusGained(FocusEvent e) {
        Object focus = e.getSource();
        if(focus.equals(this.view.getPortServer())){
            this.view.getPortServer().setText("");
        }
        else
        if(focus.equals(this.view.getPort())){
            if(this.view.getPort().getText().equalsIgnoreCase("port")){
                this.view.getPort().setText("");
            }
        }
        else
        if(focus.equals(this.view.getIpv6())){
            if(this.view.getIpv6().getText().equalsIgnoreCase("ipv6")){
                this.view.getIpv6().setText("");
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        Object focus = e.getSource();
        if(focus.equals(this.view.getPort())){
            if(this.view.getPort().getText().equals("")){
                this.view.getPort().setText("port");
            }
        }
        else
        if(focus.equals(this.view.getIpv6())){
            if(this.view.getIpv6().getText().equals("")){
                this.view.getIpv6().setText("IPv6");
            }
                    
        }
    }
    
}
