package Aparkarma.repository;

import Aparkarma.model.Usuari;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositori per manegar els objectes de la classe Usuari a la base de dades
 * psotgres
 *
 * @author Mohamed Roukdi
 */
@Repository
public interface UsuariRepository extends JpaRepository<Usuari, Integer> {

    /**
     * retorna els usuaris d'un tipus
     *
     * @param admin tipus d'usuari
     * @return llista d'usuaris
     */
    @Query(value = "select u from Usuari u where u.admin=:admin")
    List<Usuari> findByType(@Param("admin") boolean admin);

    /**
     * retorna l'usuari amb el login i clau rebudes
     *
     * @param login login d'usuari
     * @return usuari
     */
    @Transactional
    @Query("SELECT u FROM Usuari u WHERE u.login = :login")
    Optional<Usuari> findByUser(@Param("login") String login);

    /**
     * retorna la cuantitat d'usuaris guardats amb un login rebut.
     *
     * @param login login d'usuari
     * @return usuari
     */
    @Transactional
    @Query("SELECT COUNT(u) > 0 FROM Usuari u WHERE u.login = :login")
    boolean existsByLogin(@Param("login") String login);

    /**
     * retorna una lista amb els Ids d'usuari que ja están donats d'alta.
     *
     * @return List
     */
    @Transactional
    @Query(value = "SELECT u.id FROM Usuari u ORDER BY u.id ASC")
    List<Integer> findByIds();

}
