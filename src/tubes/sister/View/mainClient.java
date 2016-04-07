/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tubes.sister.View;

import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author Niyung
 */
public class mainClient {
    public static void main(String[] args) {
        try {
            String s;
            final Socket client = new Socket("localhost", 11223);
//            final Socket client = new Socket("fe80::20d6:6a32:64d9:18ab", 9999);
            
            Scanner input = new Scanner(client.getInputStream());
//            PrintWriter output = new PrintWriter(client.getOutputStream(),true);
            System.out.println("client terhubung");
            
//            Scanner chat = new Scanner(System.in);
            String replay, pesan;
            
             new Thread(){
                    PrintWriter output = new PrintWriter(client.getOutputStream(),true);
                    Scanner chat = new Scanner(System.in);
                    String pes;
                    @Override
                    public void run(){
                        while(true){   
                            try {
                                pes = chat.nextLine();
                                output.println(pes);
                                if(pes.equalsIgnoreCase("exit"))
                                    break;
                            } catch (Exception e) {
                            }
                        }
                        this.stop();
                    }
                }.start();
            
            
            do {                
//                pesan = chat.nextLine();
//                output.println(pesan);
                
               
                
                replay = input.nextLine();
                System.out.println("pesan masuk : "+replay);
                
                
                
            } while (!"exit".equalsIgnoreCase(replay));
            client.close();
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
