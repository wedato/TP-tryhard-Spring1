package fr.orleans.info.wsi.cc.tpnote.controleur;

import fr.orleans.info.wsi.cc.tpnote.modele.exceptions.ADejaVoteException;
import fr.orleans.info.wsi.cc.tpnote.modele.exceptions.NumeroPropositionInexistantException;
import fr.orleans.info.wsi.cc.tpnote.modele.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api/quizz")

public class ControleurQuizz {

    @Autowired
    FacadeQuizz facadeQuizz;
    String location = "/api/quizz";

    /*POST /api/quizz/utilisateur
    - ne nécessite aucune authentification
    - deux paramètres dans le body de la requête:
        - pseudo doit être un email valide
        - password doit être un mot de passe non vide
    - Codes :
        - 201 quand l'utilisateur est bien créé
        - 406 quand les informations dans les paramètres sont incorrectes
        - 409 quand l'adresse mail est déjà utilisée*/

    @PostMapping(value = "/utilisateur")
    public ResponseEntity<String> inscription(@RequestParam String pseudo, @RequestParam String password) {
        try {
            int utilisateur = facadeQuizz.creerUtilisateur(pseudo, password);
            return ResponseEntity.created(URI.create(location + "/utilisateur" + utilisateur)).build();
        } catch (EmailDejaUtiliseException e) {
            return ResponseEntity.status(409).build();
        } catch (EmailNonValideException | MotDePasseObligatoireException e) {
            return ResponseEntity.status(406).build();
        }
    }

    /*GET /api/quizz/utilisateur/{idUtilisateur}
    - pour tous les utilisateurs authentifiés
    - Codes :
        - 200 quand l'utilisateur est bien récupéré avec un objet Utilisateur dans
          le body de la réponse
        - 403 quand un utilisateur essaie de récupérer un profil qui n'est pas le sien*/
    @GetMapping(value = "/utilisateur/{idUtilisateur}")
    public ResponseEntity<Utilisateur> getUtilisateur(Principal principal, @PathVariable int idUtilisateur) throws UtilisateurInexistantException {
        String email = principal.getName();
        Utilisateur utilisateur = facadeQuizz.getUtilisateurByEmail(email);
        if(utilisateur.getIdUtilisateur() != idUtilisateur) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(utilisateur);
    /*public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable int idUtilisateur) {
        try {
            return ResponseEntity.ok(facadeQuizz.getUtilisateurById(idUtilisateur));
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.status(403).build();
        }*/
    }


    /*POST /api/quizz/question
    - requête authentifiée uniquement disponible pour les professeurs (rôle PROFESSEUR)
    - Requiert dans le body de la requête :
        - une structure Json de la classe Question contenant au moins les champs :
            * libelleQuestion : le libellé de la question
            * reponsesPossibles : un tableau de réponses
    - Codes :
        - 201 : si la question a pu être créée sans erreur + Location de la ressource créée
        - 406 : si les attributs de l'objets envoyés ne sont pas conformes aux attentes (voir
        la fonction creerQuestion dans FacadeQuizz.java
        - 403 : si la personne authentifiée n'a pas accès à cette URI*/


}
