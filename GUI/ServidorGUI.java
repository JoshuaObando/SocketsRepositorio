package GUI;


import java.io.*;
import java.net.*;
import javax.swing.*;

public class ServidorGUI extends JFrame{
  
    private JTextArea areaLog;
    private ServerSocket servidorSocket;
    private BufferedWriter escritorLog;

    public ServidorGUI() {
        setTitle("Servidor de Chat");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        areaLog = new JTextArea();
        areaLog.setBounds(10, 10, 360, 240);
        areaLog.setEditable(false);
        add(areaLog);

        try {
            escritorLog = new BufferedWriter(new FileWriter("chat_log.txt", true));
        } catch (IOException e) {
            areaLog.append("Error al abrir el archivo de registro: " + e.getMessage() + "\n");
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    servidorSocket = new ServerSocket(6789);
                    areaLog.append("Servidor iniciado en el puerto 6789\n");

                    while (true) {
                        Socket conexionSocket = servidorSocket.accept();
                        new Thread(new ManejadorCliente(conexionSocket)).start();
                    }
                } catch (IOException e) {
                    areaLog.append("Error: " + e.getMessage() + "\n");
                }
            }
        }).start();
    }

    private class ManejadorCliente implements Runnable {
        private Socket clienteSocket;
        private BufferedReader entradaDelCliente;
        private DataOutputStream salidaAlCliente;

        public ManejadorCliente(Socket socket) {
            this.clienteSocket = socket;
        }

        @Override
        public void run() {
            try {
                entradaDelCliente = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                salidaAlCliente = new DataOutputStream(clienteSocket.getOutputStream());

                String mensajeCliente;
                while ((mensajeCliente = entradaDelCliente.readLine()) != null) {
                    String mensajeDescifrado = descifrarMensaje(mensajeCliente);
                    areaLog.append("Cliente: " + mensajeDescifrado + "\n");
                    escritorLog.write("Cliente: " + mensajeDescifrado + "\n");
                    escritorLog.flush();
                    String respuesta = "Mensaje recibido";
                    salidaAlCliente.writeBytes(cifrarMensaje(respuesta) + '\n');
                }
            } catch (IOException e) {
                areaLog.append("Error: " + e.getMessage() + "\n");
            }
        }

        private String cifrarMensaje(String mensaje) {
            return new StringBuilder(mensaje).reverse().toString();
        }

        private String descifrarMensaje(String mensaje) {
            return new StringBuilder(mensaje).reverse().toString();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServidorGUI().setVisible(true);
            }
        });
    }
}