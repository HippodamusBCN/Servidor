package Aparkarma;

import Aparkarma.gestors.GestorUsuari;
import Aparkarma.socket.SesioClient;
import java.net.ServerSocket;
import java.net.Socket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author Mohamed Roukdi
 */
@SpringBootApplication
@Configuration
@EnableJpaRepositories
public class SpringBootIniciador implements ApplicationRunner {

    public static ServerSocket serverSocket;
    private static Socket clientSocket;
    private int idSesio = 0;
    @Autowired
    private GestorUsuari gestorUsuari;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String port = args.getSourceArgs()[0];
        try {
            serverSocket = new ServerSocket(Integer.parseInt(port));
            System.out.println("Servidor iniciat al port: " + port);
            gestorUsuari.crearUsuariAdminInicial();
            while (Finestra.activat) {
                clientSocket = serverSocket.accept();
                System.out.println("Nova connexió acceptada");
                idSesio++;
                new SesioClient(clientSocket, gestorUsuari, idSesio).start();

            }
        } catch (Exception e) {
            System.out.println("Error al iniciar el servidor");
        }
    }

}
