package Aparkarma.socket;

import Aparkarma.gestors.GestorUsuari;
import static Aparkarma.gestors.GestorUsuari.usuarisConectats;
import Aparkarma.model.Usuari;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

/**
 * Classe que gestiona la comunicación amb el client
 *
 * @author Mohamed Roukdi
 */
public class SesioClient extends Thread {

    //solicituds
    public static final char BOTO_SORTIR = '0';
    public static final int BOTO_INICIAR_SESSIO = '1';
    public static final char BOTO_ALTA_NOU_USUARI = '2';
    public static final char BOTO_BAIXA_USUARIO = '3';
    public static final char BOTO_ADMIN_ELIMINA_USER = '4';
    public static final char BOTO_LISTAR_USUARIS_DESDE_ADMIN = '5';
    public static final char BOTO_CAMBIAR_CLAU = '6';
    public static final char BOTO_BUSCAR_APARCAMENTS = '7';
    public static final char BOTO_EDITAR_PERFIL = '8';
    public static final char BOTO_ANUNCIAR_APARCAMENT = '9';
    public static final char BOTO_TORNAR_A_PRINCIPAL = 'r';
    public static final char USUARI_ADMIN = '1';
    public static final String SEPARADOR_ARGUMENTS = "- ";
    //respostes
    public static final char SOLICITUD_INCORRECTE = '0';
    public static final char SOLICITUD_CORRECTE = '1';
    public static final byte PANTALLA_LOGIN = 0;
    public static final byte PANTALLA_PRINCIPAL = 1;
    public static final byte PANTALLA_PRINCIPAL_ADMINISTRADOR = 2;
    public static final byte PANTALLA_PERFIL_USUARI = 3;

    private final GestorUsuari gestorUsuari;
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private byte pantallaActual = PANTALLA_LOGIN;
    private Usuari usuari = null;
    private boolean sortir = false;
    private int idSesio;

    public SesioClient(Socket socket, GestorUsuari gestorUsuari, int idSesio) {
        this.socket = socket;
        this.gestorUsuari = gestorUsuari;
        this.idSesio = idSesio;
    }

    //Métode per controlar la pantalla actual del client
    @Override
    public void run() {
        try {
            System.out.println("Usuari nou conectat.");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (!sortir) {
                switch (pantallaActual) {
                    case PANTALLA_LOGIN ->
                        pantallaLogin();
                    case PANTALLA_PRINCIPAL ->
                        pantallaPincipal();
                    case PANTALLA_PRINCIPAL_ADMINISTRADOR ->
                        pantallaAdministrador();
                    case PANTALLA_PERFIL_USUARI ->
                        pantallaPerfilUsuari();
                    default -> {
                    }
                }
            }
            in.close();
            out.close();
        } catch (Exception ex) {
            out.println(SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Error, en el servidor amb la sesió: " + idSesio);
        }

    }

    //Métode per gestionar les accions de la pantalla inicial (login)
    private void pantallaLogin() throws Exception {
        System.out.println("Obrint la pantalla de login del programa.");
        while (pantallaActual == PANTALLA_LOGIN) {
            String[] parametresRebutsDelClient = in.readLine().split(SEPARADOR_ARGUMENTS);
            char accioSolicitada = parametresRebutsDelClient[0].charAt(0);
            System.out.println("Solicitud del client: " + Arrays.toString(parametresRebutsDelClient));

            switch (accioSolicitada) {
                case BOTO_INICIAR_SESSIO ->
                    out.println(respostaIniciSesio(parametresRebutsDelClient));
                case BOTO_ALTA_NOU_USUARI ->
                    out.println(gestorUsuari.crearUsuariNou(parametresRebutsDelClient));
                default ->
                    out.println(SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Error, no existeix cap resposta a aquesta solicitud.");
            }
        }
    }

    //Métode per gestionar les accions de la pantalla principal
    private void pantallaPincipal() throws Exception {
        System.out.println("Obrint la pantalla pricipal de l'usuari: " + usuari.getLogin());
        while (pantallaActual == PANTALLA_PRINCIPAL && !sortir) {
            String[] parts = in.readLine().split(SEPARADOR_ARGUMENTS);
            char accioSolicitada = parts[0].charAt(0);
            System.out.println("Solicitud del client: " + Arrays.toString(parts));

            switch (accioSolicitada) {
                case BOTO_BUSCAR_APARCAMENTS ->
                    out.println(SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Funcio no operativa en aquest TEA");
                case BOTO_ANUNCIAR_APARCAMENT ->
                    out.println(SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Funcio no operativa en aquest TEA");
                case BOTO_EDITAR_PERFIL ->
                    pantallaActual = PANTALLA_PERFIL_USUARI;
                case BOTO_SORTIR -> {
                    out.println(tancarSesio());
                    pantallaActual = PANTALLA_LOGIN;
                }
                default ->
                    out.println(SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Error, no existeix cap resposta a aquesta solicitud.");
            }
        }
    }

    //Métode per gestionar les accions de la pantalla principal de l'usuari admin
    private void pantallaAdministrador() throws Exception {

        System.out.println("Obrint la pantalla pricipal de l'usuari administrador: " + usuari.getLogin());
        while (pantallaActual == PANTALLA_PRINCIPAL_ADMINISTRADOR && !sortir) {
            String[] parts = in.readLine().split(SEPARADOR_ARGUMENTS);
            char accioSolicitada = parts[0].charAt(0);
            System.out.println("Solicitud del client: " + Arrays.toString(parts));

            switch (accioSolicitada) {
                case BOTO_ADMIN_ELIMINA_USER ->
                    out.println(gestorUsuari.eliminar(parts[1]));
                case BOTO_LISTAR_USUARIS_DESDE_ADMIN ->
                    out.println(SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Funcio no operativa en aquest TEA");
                case BOTO_EDITAR_PERFIL ->
                    pantallaActual = PANTALLA_PERFIL_USUARI;
                case BOTO_SORTIR -> {
                    out.println(tancarSesio());
                    pantallaActual = PANTALLA_LOGIN;
                }
                default ->
                    out.println(SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Error, no existeix cap resposta a aquesta solicitud.");
            }
        }
    }

    //Métode per gestionar les accions de la pantalla del perfil d'usuari
    private void pantallaPerfilUsuari() throws Exception {
        System.out.println("Obrint la pantalla del perfil de l'usuari: " + usuari.getLogin());
        while (pantallaActual == PANTALLA_PERFIL_USUARI && !sortir) {
            String[] parts = in.readLine().split(SEPARADOR_ARGUMENTS);
            char accioSolicitada = parts[0].charAt(0);
            System.out.println("Solicitud del client: " + Arrays.toString(parts));

            switch (accioSolicitada) {
                case BOTO_BAIXA_USUARIO -> {
                    out.println(SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Funcio no operativa t");
                    tancarSesio();
                }
                case BOTO_CAMBIAR_CLAU ->
                    out.println(SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Funcio no operativa en aquest TEA");
                case BOTO_TORNAR_A_PRINCIPAL -> {
                    out.println(SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Funcio no operativa en aquest TEA");
                    if (usuari.isAdmin()) {
                        pantallaActual = PANTALLA_PRINCIPAL_ADMINISTRADOR;
                    } else {
                        pantallaActual = PANTALLA_PRINCIPAL;
                    }
                }
                default ->
                    out.println(SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Error, no existeix cap resposta a aquesta solicitud.");
            }
        }
    }

    //funció que verifica si existeix l'usuari, la clau es correcta y no está conectat.
    private String respostaIniciSesio(String[] parts) {
        usuari = gestorUsuari.buscarUsuariAmbUserIClau(parts);
        char admin = '0';
        if (usuari != null) {
            if (usuarisConectats.contains(usuari)) {
                return SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Usuari ja conectat.";
            } else {
                if (usuari.isAdmin()) {
                    admin = USUARI_ADMIN;
                    pantallaActual = PANTALLA_PRINCIPAL_ADMINISTRADOR;
                } else {
                    pantallaActual = PANTALLA_PRINCIPAL;
                }
                usuarisConectats.add(usuari);
                return SOLICITUD_CORRECTE + SEPARADOR_ARGUMENTS + admin + SEPARADOR_ARGUMENTS + "Benvingut " + usuari.getNom() + SEPARADOR_ARGUMENTS + idSesio;
            }
        } else {
            return SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Usuari o contrasenya erronis.";
        }
    }

    //funció que tanca la sesió amb el client.
    public String tancarSesio() {
        sortir = true;
        usuarisConectats.remove(usuari);
        System.out.println("Desconectat l'usuari: " + usuari.getLogin());
        usuari = null;
        return "" + SOLICITUD_CORRECTE;
    }

}
