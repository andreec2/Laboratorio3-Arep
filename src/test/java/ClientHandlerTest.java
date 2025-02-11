// ClientHandlerTest.java


import org.example.server.HttpServer;
import org.junit.jupiter.api.*;
import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ClientHandlerTest {

    private Thread serverThread;

    @BeforeEach
    void setUp() {
        serverThread = new Thread(() -> HttpServer.startServer());
        serverThread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @AfterEach
    void tearDown() {
        HttpServer.stopServer();
    }

    @Test
    void testGetIndexHtml() throws IOException {
        try (Socket socket = new Socket("localhost", 35000)) {
            // Enviar solicitud GET
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET /index.html HTTP/1.1");
            out.println("Host: localhost");
            out.println(); // Línea vacía importante
            out.flush();

            // Leer respuesta
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();

            assertNotNull(response);
            assertTrue(response.contains("200 OK"),
                    "La respuesta debería contener '200 OK', pero fue: " + response);

            // Leer el resto de la respuesta para verificar el contenido
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                content.append(line).append("\n");
            }

            assertTrue(content.toString().contains("Content-Type: text/html"),
                    "La respuesta debería incluir el Content-Type correcto");
        }
    }

    @Test
    void testGetAnnotatedEndpoint() throws IOException {
        try (Socket socket = new Socket("localhost", 35000)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET /nombre?name=Test&tipo=refresco HTTP/1.1");
            out.println("Host: localhost");
            out.println();
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();

            assertNotNull(response);
            assertTrue(response.contains("200 OK"));

            // Leer hasta encontrar el cuerpo de la respuesta
            StringBuilder content = new StringBuilder();
            String line;
            boolean bodyFound = false;
            while ((line = in.readLine()) != null) {
                if (line.isEmpty() && !bodyFound) {
                    bodyFound = true;
                    continue;
                }
                if (bodyFound) {
                    content.append(line);
                }
            }

            assertTrue(content.toString().contains("Test"),
                    "La respuesta debería contener el nombre proporcionado");
            assertTrue(content.toString().contains("refresco"),
                    "La respuesta debería contener el tipo de bebida proporcionado");
        }
    }

    @Test
    void testFileNotFound() throws IOException {
        try (Socket socket = new Socket("localhost", 35000)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET /archivo-no-existe.html HTTP/1.1");
            out.println("Host: localhost");
            out.println();
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();

            assertNotNull(response);
            assertTrue(response.contains("404"));
        }
    }

    @Test
    void testMathOperations() throws IOException {
        // Probar la operación de suma
        testMathEndpoint("/sum?a=5&b=3", "8");

        // Probar la operación de resta
        testMathEndpoint("/res?a=10&b=4", "6");

        // Probar la operación de multiplicación
        testMathEndpoint("/mul?a=6&b=7", "42");
    }

    private void testMathEndpoint(String path, String expectedResult) throws IOException {
        try (Socket socket = new Socket("localhost", 35000)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET " + path + " HTTP/1.1");
            out.println("Host: localhost");
            out.println();
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();

            assertNotNull(response);
            assertTrue(response.contains("200 OK"));

            // Leer hasta encontrar el cuerpo de la respuesta
            StringBuilder content = new StringBuilder();
            String line;
            boolean bodyFound = false;
            while ((line = in.readLine()) != null) {
                if (line.isEmpty() && !bodyFound) {
                    bodyFound = true;
                    continue;
                }
                if (bodyFound) {
                    content.append(line);
                }
            }

            assertTrue(content.toString().contains(expectedResult),
                    "La respuesta debería contener el resultado esperado: " + expectedResult);
        }
    }
}