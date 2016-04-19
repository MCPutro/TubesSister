/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes.sister.controller;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mu'ti C Putro
 */
public class percobaan {

//    public static void main(String[] args) throws UnknownHostException {
//        ArrayList<String> list= new ArrayList<>();
//            
//        try {
//            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//            while (interfaces.hasMoreElements()) {
//                NetworkInterface current = interfaces.nextElement();
////                System.out.println(current);
//                if (!current.isUp() || current.isLoopback() || current.isVirtual()) {
//                    continue;
//                }
//                Enumeration<InetAddress> addresses = current.getInetAddresses();
//                while (addresses.hasMoreElements()) {
//                    InetAddress current_addr = addresses.nextElement();
//                    if (current_addr.isLoopbackAddress()) {
//                        continue;
//                    }
////                    System.out.println(current_addr.getHostAddress()+" <<");
//                    list.add(current_addr.getHostAddress());
//                }
//            }
//        } catch (SocketException ex) {
//            Logger.getLogger(percobaan.class.getName()).log(Level.SEVERE, null, ex);
//        }            
//        
//        String ip4 = InetAddress.getLocalHost().getHostAddress();
//        System.out.println(ip4+" <<<");
//        for (int i = 0; i < list.size(); i++) {
//            if(ip4.equalsIgnoreCase(list.get(i))){
//                String[] g = list.get(i+1).split("%");
//                System.out.println(g[0]);
////                System.out.println(list.get(i+1));
//                break;
//            }
//        }
//        
//        
//    }
    
    public static void main(String[] args) {
        dataConnect dc = new dataConnect();
        dc.connect();
    }
}
