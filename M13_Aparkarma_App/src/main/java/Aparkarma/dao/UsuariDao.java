package Aparkarma.dao;

import Aparkarma.model.Usuari;
import Aparkarma.repository.UsuariRepository;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Component DAO per manegar els objectes de la classe Usuari a la base de dades
 *
 * @author Mohamed Roukdi
 */
@Component
public class UsuariDao {

    @Autowired
    private UsuariRepository usuariRepository;

    /**
     * Insereix un nou usuari a la base de dades
     *
     * @param usuari l'usuari a afegir
     * @throws Exception si l'usuari ja existeix
     */
    public void inserir(Usuari usuari) throws Exception {
        usuariRepository.save(usuari);
    }

    /**
     * Obtenir el Ids donats d'alta
     *
     * @return lista amb el ids que ja están donats d'alta
     */
    public List<Integer> llistatIdsEnAlta() {
        return usuariRepository.findByIds();
    }

    /**
     * comprova si un usuari esta a la base de dades
     *
     * @param login l'identificador de l'usuari
     * @return ture o false segons si existeix o no
     * @throws Exception si l'usuari no existeix
     */
    public boolean jaExisteixLogin(String login) throws Exception {
        return usuariRepository.existsByLogin(login);
    }

    /**
     * retorna l'usuari amb el login i clau rebudes
     *
     * @param login login d'usuari
     * @param clau clau d'usuari
     * @return usuari
     */
    public Usuari comprobarUsuariByUser(String login) {
        try {
            return usuariRepository.findByUser(login).orElseThrow(() -> null);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Obtenir el numero d'usuaris creats
     *
     * @return numero d'usuaris creats
     */
    public int contarUsuarisCreats() {
        return (int) usuariRepository.count();
    }

    /**
     * Elimina un usuari de la base de dades
     *
     * @param id l'identificador de l'usuari
     * @throws GestorException si l'usuari no existeix
     */
    public void eliminar(int id) throws Exception {
        usuariRepository.deleteById(id);
    }

}
