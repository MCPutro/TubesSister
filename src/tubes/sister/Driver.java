/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes.sister;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import tubes.sister.View.View;
import tubes.sister.controller.Controller;
import tubes.sister.controller.getIP;

/**
 *
 * @author Mu'ti C Putro
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    

    public static void main(String[] args) throws UnknownHostException {
        // TODO code application logic here
        View v = new View();
        Controller c = new Controller(v);
        
//        String hostName = InetAddress.getLocalHost().getHostName();
//        System.out.println(hostName);
//        InetAddress[] a = InetAddress.getAllByName(hostName);
//        for (int i = 0; i < a.length; ) {
//            if( a[i] instanceof Inet6Address){
//                System.out.println(a[i]);
//                break;
//            }else
//            {
//                i++;
//            }
//        }
//            System.out.println(getIP.getHostAddress());
//            System.out.println(getIP.getHostName());
//            InetAddress[] a = InetAddress.getAllByName("IFLAB03-165");
//            System.out.println(a[1].getHostAddress());

    }
}
