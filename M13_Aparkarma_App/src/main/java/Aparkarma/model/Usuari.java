package Aparkarma.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * La entitat Usuari
 *
 * @author Mohamed Roukdi
 */
@Entity
@Data
@NoArgsConstructor
public class Usuari implements Serializable {

    @Id
    private int id;
    private String login;
    private String nom;
    private String clau;
    private boolean admin;

    /**
     * Crea un usuari apartir del seu id, el login, el nom, la clau i el tipus
     * d'usuari (Administrado o normal).
     *
     * @param id identificador de l'usuari
     * @param userLogin nom d'usuari
     * @param nom nom del propietari de l'usuari
     * @param clau clau per iniciar la sesió de l'usuari
     */
    public Usuari(int id, String userLogin, String nom, String clau, boolean tipusUsuari) {
        this.id = id;
        this.login = userLogin;
        this.nom = nom;
        this.clau = clau;
        this.admin = tipusUsuari;
    }
    public int getId() {
        return id;
    }


    public String getLogin() {
        return login;
    }



    public String getNom() {
        return nom;
    }



    public String getClau() {
        return clau;
    }


    public boolean isAdmin() {
        return admin;
    }


}
