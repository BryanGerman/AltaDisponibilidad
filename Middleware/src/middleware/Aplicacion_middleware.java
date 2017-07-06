package middleware;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ClientInfoStatus;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Timer;

/**
 *
 * @author Bryan German
 */
public class Aplicacion_middleware {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int portNumber = 5000;
        int contador = 0;
        File archivo;
        FileWriter fw;
        PrintWriter pw;
        FileReader fr;
        BufferedReader br;
        ArrayList<String> users = new ArrayList<String>();

        try {

            ServerSocket serverSocket = new ServerSocket(portNumber);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado");
                users.add(clientSocket.getRemoteSocketAddress().toString());
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine, outputLine;
                String informacion;
                contador = 0;
                outputLine = "Ingrese el usuario";
                out.println(outputLine);
                while ((inputLine = in.readLine()) != null) {
                     if(contador == 1){
                    	 outputLine = "Ingrese la contrasena";
                     }
                     else if(contador ==2){
                    	 outputLine = "Ingrese el mensaje;"
                     }
                    contador++;
                    out.println(outputLine);
                    
                    informacion += in.readLine()+"\t";
                }
                long inicio =  System.currentTimeMillis();
                Thread.sleep(1000);
                long fin =  System.currentTimeMillis(); 
                int tiempo = (inicio - fin)/1000;
                if(tiempo >1){
                	for (int i = 0; i < users.size(); i++) {
						if(users.get(i).equals(clientSocket.getInetAddress().getHostAddress())){
							users.remove(i);
						}
					}
                }

            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());

        } catch (Exception ex) {
            Logger.getLogger(Aplicacion_middleware.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
