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
public class getIP {

    public static String getHostAddress() {
        ArrayList<String> list = new ArrayList<>();
        try {
//            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//            while (interfaces.hasMoreElements()) {
//                NetworkInterface current = interfaces.nextElement();
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

//            String ipv4 = InetAddress.getLocalHost().getHostAddress();
//            for (int i = 0; i < list.size(); i++) {
//                if(ipv4.equalsIgnoreCase(list.get(i))){
//                    String[] s = list.get(i+1).split("%");
//                    return s[0];
//                }
//            }
//                        return null;

            String host = InetAddress.getLocalHost().getHostName();
            InetAddress[] a = InetAddress.getAllByName(host);
            String[] as = a[1].getHostAddress().split("%");
            return as[0];
        } catch (Throwable ex) {
            Logger.getLogger(percobaan.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    
    public static String getHostName(){
        try {
            String host = InetAddress.getLocalHost().getHostName();
            InetAddress[] a = InetAddress.getAllByName(host);
            return a[0].getHostName();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
