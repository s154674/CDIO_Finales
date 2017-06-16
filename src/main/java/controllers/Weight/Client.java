// Dette er en redigeret client tager fra stackoverflow.com
package controllers.Weight;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client implements Runnable {

    BufferedReader br2;
    PrintWriter pr1;
    Socket socket;
    Thread t1, t2;
    SocketMessage sim, som;

    public Client(SocketMessage sim, SocketMessage som) {
        try {
            t1 = new Thread(this);
            t2 = new Thread(this);
            socket = new Socket("169.254.2.3", 8000);
            this.sim = sim;
            this.som = som;
            t1.start();
            t2.start();

        } catch (Exception e) {
        }
    }

    public void run() {
        while(true) {
            try {
                if (Thread.currentThread() == t2) {
                    do {
                        if (som.getMessage().equals("")) {Thread.sleep(100);} else {
                        pr1 = new PrintWriter(socket.getOutputStream(), true);
                        pr1.println(som.getMessage());
                        som.setMessage("");}
                    } while (!sim.getMessage().equals("END"));
                } else {
                    do {
                        br2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        sim.setMessage(br2.readLine());
                    } while (!sim.getMessage().equals("END"));
                }
            } catch (Exception e) {
            }
        }

    }
}