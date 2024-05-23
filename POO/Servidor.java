package POO;

import java.io.*;
import java.net.*;

public class Servidor {

    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String response;

        ServerSocket welcomeSocket = new ServerSocket(6789);

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            clientSentence = inFromClient.readLine();
            response = processCommand(clientSentence) + '\n';
            outToClient.writeBytes(response);
        }
    }

    private static String processCommand(String command) {
        String[] parts = command.split(" ", 2);
        String commandName = parts[0];

        if ("suma".equals(commandName)) {
            return solveMathProblem(parts[1]);
        } else {
            return "Comando desconocido: " + commandName;
        }
    }

    private static String solveMathProblem(String problem) {
        String[] parts = problem.split("\\+");
        if (parts.length == 2) {
            try {
                int num1 = Integer.parseInt(parts[0]);
                int num2 = Integer.parseInt(parts[1]);
                return String.valueOf(num1 + num2);
            } catch (NumberFormatException e) {
                return "Error: los operandos deben ser números enteros.";
            }
        } else {
            return "Error: el formato del problema es incorrecto.";
        }
    }
}