package POO;

import java.io.*;
import java.net.*;

public class Cliente {

     public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));//Aca se lee la entrada del usuario
        Socket clientSocket = new Socket("localhost", 6789);//Aca se asigna el puerto y la dirección del servidor

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());//Aca se envia la información al servidor
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//Aca se lee la información que envia el servidor

        sentence = inFromUser.readLine();//Aca se lee la entrada del usuario
        outToServer.writeBytes(sentence + '\n');//Aca se envía la información al servidor
        modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence);

        clientSocket.close();
    }

 }//Fin de la clase