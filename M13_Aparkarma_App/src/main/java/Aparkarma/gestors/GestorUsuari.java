package Aparkarma.gestors;

import Aparkarma.dao.UsuariDao;
import Aparkarma.model.Usuari;
import static Aparkarma.socket.SesioClient.SEPARADOR_ARGUMENTS;
import static Aparkarma.socket.SesioClient.SOLICITUD_CORRECTE;
import static Aparkarma.socket.SesioClient.SOLICITUD_INCORRECTE;
import static Aparkarma.socket.SesioClient.USUARI_ADMIN;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Classe per manegar els objectes de la classe Usuari a la base de dades
 *
 * @author Mohamed Roukdi
 */
@Service
@Component
public class GestorUsuari {

    public static List<Usuari> usuarisConectats = new ArrayList<>();

    @Autowired
    private UsuariDao usuariDao;

    /**
     * Insereix un usuari administrado inicial en cas que no existeixi cap
     * usuari
     *
     */
    public void crearUsuariAdminInicial() throws Exception {
        if (usuarisConectats.isEmpty()) {
            Usuari usuari = new Usuari(1, "root", "root", "root", true);
            usuariDao.inserir(usuari);
        }
    }

    /**
     * Insereix un nou usuari a la base de dades, asignant-li un id que no el té
     * cap altra usuari
     *
     * @param dades de l'usuari a afegir
     * @return misatge de crat, no cret, o error
     */
    public String crearUsuariNou(String[] dadesRebudes) {
        boolean admin = false;
        if (dadesRebudes[4].charAt(0) == (USUARI_ADMIN)) {
            admin = true;
        }
        int id = buscarIdNou();
        Usuari usuari = new Usuari(id, dadesRebudes[1], dadesRebudes[2], dadesRebudes[3], admin);
        try {
            if (!jaExisteix(usuari.getLogin())) {
                usuariDao.inserir(usuari);
                return SOLICITUD_CORRECTE + SEPARADOR_ARGUMENTS + "Usuari creat corrrectamente";

            } else {
                return SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Aques usuari ja existeix a la BBDD";
            }
        } catch (Exception ex) {
            return SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Error al servidor";
        }

    }

    //métode que retorna el id més baix que no el tingui cap usuari
    private int buscarIdNou() {
        int idDisponible = 1;
        while (usuariDao.llistatIdsEnAlta().contains(idDisponible)) {
            idDisponible++;
        }
        return idDisponible;
    }

    /**
     * busca si existeix un usuari a la base de dades amb el login (user)
     *
     * @param login el user de l'usuari a afegir
     * @return si ja existeix o no el nom de l'usuari
     */
    public boolean jaExisteix(String login) throws Exception {
        return usuariDao.jaExisteixLogin(login);
    }

    /**
     * retorna l'id de l'usuari amb el login i clau rebudes, i 0 si no existeix
     *
     * @param login tipus d'usuari
     * @param clau tipus d'usuari
     * @return id d'usuari
     */
    public Usuari buscarUsuariAmbUserIClau(String[] dadesRebudes) {
        String login = dadesRebudes[1];
        String clau = dadesRebudes[2];
        try {
            Usuari usuari = usuariDao.comprobarUsuariByUser(login);
            if (usuari.getClau().equals(clau)) {
                return usuari;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * Elimina un usuari de la base de dades
     *
     * @param login l'identificador de l'usuari
     * @return text amb el resultat
     * @throws GestorException si l'usuari no existeix
     */
    public String eliminar(String login) {
        Usuari usuari = usuariDao.comprobarUsuariByUser(login);
        try {
            if (usuari != null) {
                usuariDao.eliminar(usuari.getId());
                return SOLICITUD_CORRECTE + SEPARADOR_ARGUMENTS + "L'usuari " + login + " s'ha eliminat correctament";
            } else {
                return SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "No existeix l'usuari a la BBDD";
            }
        } catch (Exception ex) {
            return SOLICITUD_INCORRECTE + SEPARADOR_ARGUMENTS + "Error en el servidor";
        }

    }

}
