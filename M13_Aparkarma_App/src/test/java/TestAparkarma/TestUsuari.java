package TestAparkarma;

import Aparkarma.Finestra;
import Aparkarma.SpringBootIniciador;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.*;
import java.net.Socket;
import org.junit.jupiter.api.AfterAll;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Order;
import org.springframework.boot.SpringApplication;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUsuari {

    private static Thread filServidor;
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;
    public static final String SOLICITUD_CORRECTE = "1";

    @BeforeAll
    public static void setUp() throws Exception {        
        Finestra.activat = true;//servidor activat
        filServidor = new Thread(() -> {
            SpringApplication.run(SpringBootIniciador.class, "12345");
        });
        filServidor.start();
        System.out.println("Esperant iniciació del servidor...");
        Thread.sleep(10000);//esperem 10 segons per que el servidor s'inicï
    }

    @AfterAll
    public static void tearDown() throws Exception {
        socket.close();
        in.close();
        out.close();
        System.out.println("Servidor i client tancats correctament.");
    }

    @Test
    @Order(1)
    public void testCrearUsuari() throws IOException {
        assertTrue(crearUsuari("user1", "usuari un", "pass1", true));
        assertTrue(crearUsuari("user2", "usuari dos", "pass2", false));
        assertFalse(crearUsuari("user1", "usuari un", "pass1", false));//creant de nou un usuari que ja existeix
    }

    @Test
    @Order(2)
    public void testIniciarSesio() throws IOException {
        assertTrue(iniciarSesio("user1", "pass1"));
        assertFalse(iniciarSesio("user1", "pass1"));//usuari ja conectat
        assertFalse(iniciarSesio("user1111", "pass1111"));//usuari que no existeix
    }

    @Test
    @Order(3)
    public void testTancarSesio() throws IOException {
        assertTrue(cerrarSesion("user2", "pass2"));
    }

    @Test
    @Order(4)
    public void testAdminEliminaUsuari() throws IOException {
        assertTrue(eliminarUsuarisCreats("user2"));
        assertTrue(eliminarUsuarisCreats("user1"));
        assertFalse(eliminarUsuarisCreats("user222"));//eliminant un usuari que no existeix
    }

    private boolean crearUsuari(String login, String nom, String clau, boolean admin) throws IOException {
        socket = new Socket("localhost", 12345);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println("2- " + login + "- " + nom + "- " + clau + "- " + (admin ? "1" : "0"));
        String resposta = in.readLine();
        return resposta.startsWith(SOLICITUD_CORRECTE);
    }

    private boolean iniciarSesio(String login, String clau) throws IOException {
        socket = new Socket("localhost", 12345);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println("1- " + login + "- " + clau);
        String resposta = in.readLine();
        return resposta.startsWith(SOLICITUD_CORRECTE);
    }

    private boolean cerrarSesion(String login, String clau) throws IOException {
        socket = new Socket("localhost", 12345);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println("1- " + login + "- " + clau);//iniciant sesió válida
        out.println("0");//tancant la sesió
        String resposta = in.readLine();
        return resposta.startsWith(SOLICITUD_CORRECTE);
    }

    private boolean eliminarUsuarisCreats(String login) throws IOException {
        socket = new Socket("localhost", 12345);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println("1- " + "root" + "- " + "root");//iniciant sesió válida i usuari admin
        in.readLine();
        out.println("4- " + login);//eliminant l'usuari 
        String resposta = in.readLine();
        out.println("0");//tancant la sesió de l'administrador
        in.readLine();
        return resposta.startsWith(SOLICITUD_CORRECTE);
    }
}
