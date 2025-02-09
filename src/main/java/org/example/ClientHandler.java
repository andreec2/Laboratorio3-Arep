package org.example;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler {
    private final Socket clientSocket;
    private static final Map<String, Method> annotatedRoutes = new HashMap<>();

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    // Método para inicializar las rutas anotadas
    public static void initializeRoutes() {
        scanAndLoadComponents("org.example");
    }

    public static void scanAndLoadComponents(String basePackage) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = basePackage.replace('.', '/');
            var resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                var resource = resources.nextElement();
                String decodedPath = java.net.URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8);
                File directory = new File(decodedPath);

                if (directory.exists()) {
                    String[] files = directory.list();
                    if (files != null) {
                        for (String file : files) {
                            if (file.endsWith(".class")) {
                                String className = basePackage + "." + file.substring(0, file.length() - 6);
                                System.out.println("Cargando clase: " + className);
                                loadComponent(Class.forName(className));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadComponent(Class<?> c) {
        if (!c.isAnnotationPresent(RestController.class)) {
            return;
        }

        for (Method m : c.getDeclaredMethods()) {
            if (m.isAnnotationPresent(GetMapping.class)) {
                GetMapping annotation = m.getAnnotation(GetMapping.class);
                annotatedRoutes.put(annotation.value(), m);
                System.out.println("Endpoint cargado: " + annotation.value() + " -> " + m.getName());
            }
        }
    }

    public void run() {
        try {
            OutputStream out = clientSocket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String requestLine = in.readLine();
            if (requestLine != null) {
                String[] requestParts = requestLine.split(" ");
                if (requestParts.length >= 2) {
                    String method = requestParts[0];
                    String file = requestParts[1];
                    URI requestFile = new URI(file);
                    String path = requestFile.getPath();
                    String query = requestFile.getQuery();

                    System.out.println("Método: " + method + ", Path: " + path);

                    if ("GET".equals(method)) {
                        if (annotatedRoutes.containsKey(path)) {
                            handleAnnotatedRoute(path, query, out);
                        } else {
                            // Si el path es "/" servir index.html
                            if ("/".equals(path)) {
                                path = "/index.html";
                            }
                            serveStaticFile(path, out);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleAnnotatedRoute(String path, String query, OutputStream out) {
        try {
            Method method = annotatedRoutes.get(path);
            String name = query != null && query.startsWith("name=") ?
                    java.net.URLDecoder.decode(query.substring(5), StandardCharsets.UTF_8) :
                    "World";

            String result = (String) method.invoke(null, name);
            String responseBody = "{\"message\": \"" + result + "\"}";

            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: " + responseBody.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                    "\r\n" +
                    responseBody;

            out.write(response.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            sendError(out, 500, "Error interno del servidor");
        }
    }

    private void serveStaticFile(String path, OutputStream out) {
        try {
            // Eliminar el slash inicial para buscar en resources
            String resourcePath = path.startsWith("/") ? path.substring(1) : path;
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("public/" + resourcePath);

            if (inputStream != null) {
                byte[] fileBytes = inputStream.readAllBytes();
                String contentType = getContentType(path);

                String headers = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: " + contentType + "\r\n" +
                        "Content-Length: " + fileBytes.length + "\r\n" +
                        "\r\n";

                out.write(headers.getBytes(StandardCharsets.UTF_8));
                out.write(fileBytes);
                out.flush();
            } else {
                sendError(out, 404, "Archivo no encontrado");
            }
        } catch (IOException e) {
            sendError(out, 500, "Error interno del servidor");
        }
    }

    private String getContentType(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".gif")) return "image/gif";
        return "application/octet-stream";
    }

    private void sendError(OutputStream out, int code, String message) {
        try {
            String errorResponse = "HTTP/1.1 " + code + " " + message + "\r\n" +
                    "Content-Type: text/html\r\n" +
                    "\r\n" +
                    "<html><body><h1>" + code + " - " + message + "</h1></body></html>";
            out.write(errorResponse.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}