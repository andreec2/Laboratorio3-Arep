package org.example.server;

import java.net.*;
import java.io.*;

public class HttpServer {
    private static volatile boolean running;
    private static ServerSocket serverSocket = null;
    private static Thread serverThread;

    public static void startServer() {
        if (running) {
            System.out.println("El servidor ya está en ejecución.");
            return;
        }

        running = true;
            try {
                serverSocket = new ServerSocket(35000);
                System.out.println("Servidor iniciado en http://localhost:35000");

                // Inicializar las rutas anotadas al inicio
                ClientHandler.initializeRoutes();

                while (running && !serverSocket.isClosed()) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        ClientHandler clientHandler = new ClientHandler(clientSocket);
                        clientHandler.run();
                    } catch (IOException e) {
                        if (running) {
                            System.err.println("Accept failed: " + e.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                if (running) {
                    System.err.println("Could not listen on port: 35000.");
                }
            }


        // Esperar un poco para asegurar que el servidor está listo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stopServer() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (serverThread != null) {
                serverThread.join(5000); // Esperar hasta 5 segundos a que el hilo termine
            }
            System.out.println("Servidor detenido.");
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al detener el servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        startServer();
    }
}