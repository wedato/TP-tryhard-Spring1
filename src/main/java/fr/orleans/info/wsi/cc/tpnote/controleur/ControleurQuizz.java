package fr.orleans.info.wsi.cc.tpnote.controleur;

import fr.orleans.info.wsi.cc.tpnote.modele.*;
import fr.orleans.info.wsi.cc.tpnote.modele.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Array;
import java.net.URI;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;



@RestController
@RequestMapping("/api/quizz")

public class ControleurQuizz {
    private final FacadeQuizz facadeQuizz;
    public ControleurQuizz(FacadeQuizz facadeQuizz) {
        this.facadeQuizz = facadeQuizz;
    }

    /*
    POST /api/quizz/utilisateur
    - ne nécessite aucune authentification
    - deux paramètres dans le body de la requête:
        - pseudo doit être un email valide
        - password doit être un mot de passe non vide
    - Codes :
        - 201 quand l'utilisateur est bien créé
        - 406 quand les informations dans les paramètres sont incorrectes
        - 409 quand l'adresse mail est déjà utilisée
        */
    @PostMapping(value = "/utilisateur")
    public ResponseEntity<String> inscription(@RequestParam String pseudo, @RequestParam String password) {
        try {
            int utilisateur = facadeQuizz.creerUtilisateur(pseudo, password);
            URI nextLocation = ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(utilisateur)
                    .toUri();
            return ResponseEntity.created(nextLocation).build();
        } catch (EmailDejaUtiliseException e) {
            return ResponseEntity.status(409).build();
        } catch (EmailNonValideException | MotDePasseObligatoireException e) {
            return ResponseEntity.status(406).build();
        }
    }

    /*
    GET /api/quizz/utilisateur/{idUtilisateur}
    - pour tous les utilisateurs authentifiés
    - Codes :
        - 200 quand l'utilisateur est bien récupéré avec un objet Utilisateur dans
          le body de la réponse
        - 403 quand un utilisateur essaie de récupérer un profil qui n'est pas le sien
        */
    @GetMapping(value = "/utilisateur/{idUtilisateur}")
    public ResponseEntity<Utilisateur> getUtilisateur(Principal principal, @PathVariable int idUtilisateur){
        try{
            String email = principal.getName();
            Utilisateur utilisateur = facadeQuizz.getUtilisateurByEmail(email);
            if(utilisateur.getIdUtilisateur() != idUtilisateur) {
                return ResponseEntity.status(403).build();
            }
            return ResponseEntity.ok(utilisateur);
        } catch (UtilisateurInexistantException e){
            return ResponseEntity.status(404).build();
        }

    }

    /*
    POST /api/quizz/question
    - requête authentifiée uniquement disponible pour les professeurs (rôle PROFESSEUR)
    - Requiert dans le body de la requête :
        - une structure Json de la classe Question contenant au moins les champs :
            * libelleQuestion : le libellé de la question
            * reponsesPossibles : un tableau de réponses
    - Codes :
        - 201 : si la question a pu être créée sans erreur + Location de la ressource créée
        - 406 : si les attributs de l'objets envoyés ne sont pas conformes aux attentes (voir
        la fonction creerQuestion dans FacadeQuizz.java
        - 403 : si la personne authentifiée n'a pas accès à cette URI
     */
    @PostMapping(value = "/question")
    public ResponseEntity<String> creerQuestion(Principal principal, @RequestBody Question question){
        try{
            String email = principal.getName();
            Utilisateur utilisateur = facadeQuizz.getUtilisateurByEmail(email);
            String idQuestion = facadeQuizz.creerQuestion(utilisateur.getIdUtilisateur(),question.getLibelleQuestion(), question.getReponsesPossibles());
            URI nextLocation = ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(idQuestion)
                    .toUri();
            return ResponseEntity.created(nextLocation).build();
        }catch (UtilisateurInexistantException e){
            return ResponseEntity.status(403).build();
        } catch (LibelleQuestionNonRenseigneException |AuMoinsDeuxReponsesException e) {
            return ResponseEntity.status(406).build();
        }
    }

    /*
    GET /api/quizz/question/{idQuestion}
    - requête authentifiée disponible pour toutes les personnes authentifiées
    - retourne dans le body l'objet Question correspondant à l'identifiant
    - Codes :
        - 200 : si la question existe
        - 404 : si aucune question ne correspond à cet identifiant
     */
    @GetMapping(value = "/question/{idQuestion}")
    public ResponseEntity<Question> recupererQuestion(@PathVariable String idQuestion){
        try{
            return ResponseEntity.ok(facadeQuizz.getQuestionById(idQuestion));
        } catch (QuestionInexistanteException e){
            return ResponseEntity.notFound().build();
        }

    }

    /*
    PUT /api/quizz/question/{idQuestion}/vote
    - requête authentifiée uniquement disponible pour les étudiants (rôle ETUDIANT)
    - contient dans le body de la requête un paramètre idReponse qui permettra
    à un étudiant de voter pour la réponse concernée
    - Codes :
        - 202 : le vote a été accepté
        - 409 : l'étudiant a déjà voté pour cette question
        - 406 : l'identifiant idReponse n'est pas correct
        - 404 : l'identifiant idQuestion ne correspond à aucune ressource existante
        - 403 : si la personne authentifiée n'a pas accès à cette URI
     */
    @PutMapping(value = "/question/{idQuestion}/vote")
    public ResponseEntity<Void> voter(Principal principal, @PathVariable String idQuestion, @RequestParam int idReponse){
        try {
            int idUtilisateur = facadeQuizz.getIdUserByEmail(principal.getName());
            facadeQuizz.voterReponse(idUtilisateur,idQuestion, idReponse);
            return ResponseEntity.status(202).build();
        } catch (EmailInexistantException e) {
            return ResponseEntity.status(403).build();
        } catch (QuestionInexistanteException e) {
            return ResponseEntity.status(404).build();
        } catch (NumeroPropositionInexistantException e) {
            return ResponseEntity.status(406).build();
        } catch (ADejaVoteException e) {
            return ResponseEntity.status(409).build();
        }
    }

    /*
    GET /api/quizz/question/{idQuestion}/vote
    - requête authentifiée uniquement disponible pour les professeurs (rôle PROFESSEUR)
    - contient dans le body de la réponse le résultat des votes à la question idQuestion
    - Codes :
        - 200 : le résultat a bien été récupéré
        - 404 : l'identifiant idQuestion ne correspond à aucune ressource existante
        - 403 : si la personne authentifiée n'a pas accès à cette URI
     */
    @GetMapping(value = "/question/{idQuestion}/vote")
    public ResponseEntity<List<ResultatVote>> recupererResultatVote(@PathVariable String idQuestion){
        try {
            ResultatVote[] resultatVotes = facadeQuizz.getResultats(idQuestion);
            List<ResultatVote> resultatVoteList = Arrays.stream(resultatVotes).toList();
            return ResponseEntity.ok().body(resultatVoteList);
        } catch (QuestionInexistanteException e) {
            return ResponseEntity.status(404).build();
        }
    }
}