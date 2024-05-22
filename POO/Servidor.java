package POO;
import java.io.*;
import java.net.*;

public class Servidor {

    private ServerSocket serverSocket;

    public void iniciar(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
        while (true) {
            new ManejadorCliente(serverSocket.accept()).start();
        }
    }//Fin de metodo inicar

    public void detener() throws IOException {
        serverSocket.close();
    }//Fin de metodo detener

    private static class ManejadorCliente extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ManejadorCliente(Socket socket) {
            this.clientSocket = socket;
        }//Fin de metodo ManejadorCliente

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String lineaEntrada;
                while ((lineaEntrada = in.readLine()) != null) {
                    if ("salir".equalsIgnoreCase(lineaEntrada)) {
                        out.println("Adiós");
                        break;
                    }
                    out.println(lineaEntrada.toUpperCase());
                }

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        try {
            servidor.iniciar(6789);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}//Fin de la clase Servidor