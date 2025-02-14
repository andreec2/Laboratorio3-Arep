# TALLER MICROFRAMEWORKS WEB


Este proyecto implementa un servidor web minimalista en Java con soporte para el manejo de páginas HTML, imágenes PNG y solicitudes REST. Además, incorpora un framework IoC (Inversión de Control) que permite la construcción de aplicaciones web a partir de POJOs utilizando capacidades reflectivas de Java.

El servidor está diseñado para atender múltiples solicitudes de forma no concurrente y permite la carga dinámica de clases anotadas con @RestController, publicando servicios en las URIs correspondientes definidas mediante @GetMapping. También soporta el uso de @RequestParam para recibir parámetros en las solicitudes.

En su versión final, explorará el classpath en busca de clases con las anotaciones adecuadas y las cargará automáticamente.

Para demostrar su funcionamiento, el proyecto incluye una aplicación web de ejemplo que responde a solicitudes REST y muestra contenido HTML relacionado con REM.

![{77378D07-17EE-46F6-9941-1207A993392A}](https://github.com/user-attachments/assets/0d10e85d-2502-4756-9b86-7f727cf55d42)

## Arquitectura
Este proyecto sigue la arquitectura cliente-servidor. Utilizando el estilo arquitectonico de REST, Los clientes envían solicitudes HTTP al servidor, que maneja la lógica de los serviciso REST y devuelve respuestas en formato JSON. El servidor también puede retornar archivos estáticos, como HTML, CSS e imágenes. 

![{B47E569F-C1A3-4BF5-A76C-21482C66D2A2}](https://github.com/user-attachments/assets/bfae084e-bc1d-405c-b8b0-ccbab15e84da)

### Componentes del Proyecto 
   - **Servidor** : HttpServer: Maneja las conexiones y enruta las solicitudes a los servicios correspondientes.
     ClientHandler: Gestiona la comunicación con el cliente, procesa la solicitud y decide si debe retornar un archivo estático o delegar la solicitud a un servicio REST.

   - **Servicios REST**: ClientHandler: Maneja operaciones CRUD en objetos JSON y consulta archivos HTML estáticos guardados en el servidor.

   - **Archivos estáticos**: Los archivos estáticos se almacenan en el directorio public, permitiendo que el servidor sirva contenido HTML, CSS, JS e imágenes de manera eficiente.

   - **Anotaciones y Controladores**: Annotations: Se definen las anotaciones necesarias para identificar los componentes del framework. Estas incluyen:

@RestController: Marca una clase como un controlador REST.
@GetMapping: Especifica que un método maneja solicitudes GET a una URL determinada.
@RequestParam: Permite la recepción de parámetros en los métodos manejadores.

Controllers: Consumen las anotaciones definidas y procesan las solicitudes REST.

## Primeros Pasos
Estas instrucciones le permitirán obtener una copia del proyecto en funcionamiento en su máquina local para fines de desarrollo y prueba. 


### Requisitos Previos
Para ejecutar este proyecto, necesitarás tener instalado:

- Java JDK 8 o superior.
- Un IDE de Java como IntelliJ IDEA, Eclipse.
- Maven para manejar las dependencias 
- Un navegador web para interactuar con el servidor.

### Instalación 

1. Tener instalado Git en tu maquina local 
2. Elegir una carpeta en donde guardes tu proyecto
3. abrir la terminal de GIT --> mediante el clik derecho seleccionas Git bash here
4. Clona el repositorio en tu máquina local:
   ```bash
   git https://github.com/andreec2/Laboratorio3-Arep.git
   ```
5. Abre el proyecto con tu IDE favorito o navega hasta el directorio del proyecto 
6. Desde la terminal  para compilar el proyecto ejecuta:

   ```bash
   mvn clean install
   ```
7. compila el proyecto  

   ```bash
    mvn clean package
   ```
8. Corra el servidor en la clase httpServer "main" o ejecute el siguiente comando desde consola
   
      ```bash
    java -cp target/classes org.example.server.HttpServer
   ```
   Vera que el servidor esta listo y corriendo sobre el puerto 35000
   
9. Puedes Ingresar desde el navegador a la pagina:
    
    http://localhost:35000/index.html

10. Puedes interactuar con los endpoints RESTful (/api):
   - POST= http://localhost:35000/post.html

11. Para este taller, además de los recursos estáticos ya implementados, hemos definido nuevos endpoints aprovechando las capacidades reflexivas de Java. Para ello, hemos creado anotaciones como @GetMapping y @RequestParam, lo que permite manejar solicitudes de manera más flexible y estructurada.

Los nuevos endPoints definidos son los siguientes:

http://localhost:35000/nombre?name=andres

![image](https://github.com/user-attachments/assets/b2770f13-9728-4218-874c-382dbd8c0f29)

![image](https://github.com/user-attachments/assets/90de5267-dfe7-4bb1-b03f-307a4c452bf1)

http://localhost:35000/sum?a=6&b=5

![image](https://github.com/user-attachments/assets/f75b949a-0b74-4f3e-9d8d-728b0014b867)

![image](https://github.com/user-attachments/assets/2e8fb19f-c2b8-473e-90b7-41ef9afbdc82)

http://localhost:35000/res?a=6&b=5

![image](https://github.com/user-attachments/assets/d6c91eca-bb6a-483b-a96c-633557affca1)

![image](https://github.com/user-attachments/assets/39c49846-0f39-4dad-8a63-5df2868e2689)

http://localhost:35000/mul?a=6&b=5

![image](https://github.com/user-attachments/assets/b5d5ac39-7a0f-4f58-97b0-28bc29c7c3a1)

![image](https://github.com/user-attachments/assets/9094870b-ea48-46bc-a4db-ef6dfb83feaa)

## Ejecutar las pruebas

Se implementaron pruebas unitarias para los métodos de manejo de solicitudes HTTP (GET, POST, PUT, DELETE) en el servidor. Estas pruebas se realizaron utilizando JUnit y Mockito para simular las solicitudes y validar las respuestas.

Para ejecutar las pruebas:  
1. Desde tu IDE, ejecuta las clase AppTest.java o desde la terminal ejecutas:
   ```bash
   mvn test
   ```
![image](https://github.com/user-attachments/assets/167b96c5-1f11-4786-a4bb-3b04b5e43c4c)

![image](https://github.com/user-attachments/assets/b0f3182c-5346-4d78-b5bf-311731fa41af)

2. Pruebas de extremo a extremo

   - testGetRoutesInitialization()
     
  Propósito: Verifica que las rutas /app/helloWord, /app/hello y /app/pi están correctamente registradas en el servidor. 

  ![image](https://github.com/user-attachments/assets/927fe4dd-52c1-4528-8e81-35739174bb5f)

  - testHelloWorldRoute()

   Propósito: Verifica que la ruta /app/helloWord devuelve "Hello, world!".

   ![image](https://github.com/user-attachments/assets/a1a7d453-3867-4cfd-9fbd-16a7fc6a36e8)

   - testHelloPiRoute()

   Propósito: Verifica que la ruta /app/pi devuelve "3.141592653589793" (el valor de π).

   ![image](https://github.com/user-attachments/assets/c2ce88e6-8ea6-44f0-8f6d-09455eea53fc)

   - testHelloNameRoute()

   Propósito: Verifica que la ruta /app/hello devuelve "Hola, Andres!" cuando se envía el parámetro name=Andres.

   ![image](https://github.com/user-attachments/assets/24574f37-335f-43b3-aba0-6db194f67fd8)

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management


## Authors

* **Andres felipe montes ortiz** - 

