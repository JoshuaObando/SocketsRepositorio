package POO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

    private Socket clienteSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void iniciarConexion(String ip, int puerto) throws IOException {
        clienteSocket = new Socket(ip, puerto);
        out = new PrintWriter(clienteSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
    }//Fin de metodo iniciarConexion

    public String enviarMensaje(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }//Fin de metodo enviarMensaje

    public void detenerConexion() throws IOException {
        in.close();
        out.close();
        clienteSocket.close();
    }//Fin de metodo detenerConexion
    
 }//Fin de la clase