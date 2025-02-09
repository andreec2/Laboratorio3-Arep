package org.example.server;

import java.net.*;
import java.io.*;

public class HttpServer {
    static boolean running;
    static ServerSocket serverSocket = null;

    public static void main(String[] args) throws IOException {
        running = true;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        // Inicializar las rutas anotadas al inicio
        ClientHandler.initializeRoutes();

        System.out.println("Servidor iniciado en http://localhost:35000");
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.run();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
    }
}