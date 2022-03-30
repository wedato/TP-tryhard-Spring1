package fr.orleans.info.wsi.cc.tpnote;

import fr.orleans.info.wsi.cc.tpnote.modele.FacadeQuizz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TpnoteApplication {

    public final static String emailProf="yohan.boichut@univ-orleans.fr";
    public final static String motDePasseProf="monMotDePasse";
    public final static String emailEtudiant="gerard.menvussaa@etu.univ-orleans.fr";
    public final static String motDePasseEtudiant="sonMotDePasse";


    @Autowired
    FacadeQuizz facadeQuizz;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {

            // Ajout d'un professeur au SI
            facadeQuizz.creerUtilisateur(emailProf,passwordEncoder.encode(motDePasseProf));

            // Ajout d'un étudiant au SI
            facadeQuizz.creerUtilisateur(emailEtudiant,passwordEncoder.encode(motDePasseEtudiant));


        };
    }


    public static void main(String[] args) {
        SpringApplication.run(TpnoteApplication.class, args);
    }

}