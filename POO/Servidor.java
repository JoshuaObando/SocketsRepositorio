package POO;

import java.io.*;
import java.net.*;
import java.util.function.BiFunction;

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

        switch (commandName) {
            case "suma":
                return ResolverProblema(parts[1], (a, b) -> a + b);
            case "resta":
                return ResolverProblema(parts[1], (a, b) -> a - b);
            case "multiplica":
                return ResolverProblema(parts[1], (a, b) -> a * b);
            case "divide":
                return ResolverProblema(parts[1], (a, b) -> {
                    if (b == 0) {
                        throw new ArithmeticException("No se puede dividir por cero.");
                    }
                    return a / b;
                });

            default:
                return "Comando desconocido: " + commandName;
        }
    }

    private static String ResolverProblema(String problema, BiFunction<Double, Double, Double> operacion) {
        String[] parts = problema.split("\\s+");
        if (parts.length == 2) {
            try {
                double num1 = Double.parseDouble(parts[0]);
                double num2 = Double.parseDouble(parts[1]);
                return String.valueOf(operacion.apply(num1, num2));
            } catch (NumberFormatException e) {
                return "Error: los operandos deben ser números.";
            } catch (ArithmeticException e) {
                return "Error: " + e.getMessage();
            }
        } else {
            return "Error: el formato del problema es incorrecto.";
        }
    }
}