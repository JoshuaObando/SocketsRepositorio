package GUI;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;

public class ClienteGUI extends JFrame{
 
    private JTextField campoEntrada;
    private JTextArea areaChat;
    private JButton botonEnviar;
    private Socket clienteSocket;
    private DataOutputStream salidaAlServidor;
    private BufferedReader entradaDelServidor;

    public ClienteGUI() {
        setTitle("Cliente de Chat");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        campoEntrada = new JTextField();
        campoEntrada.setBounds(10, 10, 260, 25);
        add(campoEntrada);

        botonEnviar = new JButton("Enviar");
        botonEnviar.setBounds(280, 10, 90, 25);
        add(botonEnviar);

        areaChat = new JTextArea();
        areaChat.setBounds(10, 50, 360, 200);
        areaChat.setEditable(false);
        add(areaChat);

        botonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String mensaje = campoEntrada.getText();
                    salidaAlServidor.writeBytes(cifrarMensaje(mensaje) + '\n');
                    String respuesta = entradaDelServidor.readLine();
                    areaChat.append("Servidor confirmó el mensaje\n");
                    areaChat.append("Servidor: " + descifrarMensaje(respuesta) + "\n");
                    campoEntrada.setText("");
                } catch (IOException ex) {
                    areaChat.append("Error: " + ex.getMessage() + "\n");
                }
            }
        });

        try {
            clienteSocket = new Socket("localhost", 6789);
            salidaAlServidor = new DataOutputStream(clienteSocket.getOutputStream());
            entradaDelServidor = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            areaChat.append("Conectado al servidor\n");
        } catch (IOException ex) {
            areaChat.append("Error al conectar con el servidor: " + ex.getMessage() + "\n");
        }
    }

    private String cifrarMensaje(String mensaje) {
        return new StringBuilder(mensaje).reverse().toString();
    }

    private String descifrarMensaje(String mensaje) {
        return new StringBuilder(mensaje).reverse().toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClienteGUI().setVisible(true);
            }
        });
    }
}