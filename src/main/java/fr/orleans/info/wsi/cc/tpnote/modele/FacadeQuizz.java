package fr.orleans.info.wsi.cc.tpnote.modele;

import fr.orleans.info.wsi.cc.tpnote.modele.exceptions.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FacadeQuizz {

    private List<Utilisateur> users = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();

    /**
     *
     * @param email : email valide
     * @param password : mot de passe utilisateur non vide et chiffré (lors de son intégration au web-service)
     * @return identifiant entier
     * @throws EmailDejaUtiliseException : email déjà utilisé
     * @throws EmailNonValideException : email n'est pas de la bonne forme
     * @throws MotDePasseObligatoireException : le mot de passe est Blank ou nul
     */

    public int creerUtilisateur(String email,String password) throws EmailDejaUtiliseException, EmailNonValideException, MotDePasseObligatoireException {
        int id;

        if (users.stream().anyMatch(user -> user.getEmailUtilisateur().equals(email))) {
            throw new EmailDejaUtiliseException();
        } else if (!OutilsPourValidationEmail.patternMatches(email)) {
            throw new EmailNonValideException();
        } else if (password == null || password.isBlank()) {
            throw new MotDePasseObligatoireException();
        } else {
            Utilisateur utilisateur = new Utilisateur(email, password);
            id = utilisateur.getIdUtilisateur();
            users.add(utilisateur);
        }
        return id;
    }

    /**
     * Permet de récupérer l'identifiant int d'un utilisateur par son E-mail
     * @param email
     * @return identifiant int
     */

    public int getIdUserByEmail(String email) throws EmailInexistantException {
        return users.stream()
                .filter(utilisateur -> utilisateur.getEmailUtilisateur().equals(email))
                .findFirst()
                .orElseThrow(EmailInexistantException::new)
                .getIdUtilisateur();
    }

    /**
     * Permet à un professeur de créer une question
     * @param idUser id du professeur (on suppose qu'uniquement les professeurs pourront accéder à cette fonctionnalité donc
     *               pas besoin de vérifier s'il s'agit d'un professeur ou s'il s'agit d'un utilisateur existant)
     * @param libelleQuestion : libellé de la question
     * @param libellesReponses : libellés des réponses possibles
     * @return identifiant aléatoire chaîne de caractère (UUID)
     * @throws AuMoinsDeuxReponsesException : au moins deux réponses sont attendues
     * @throws LibelleQuestionNonRenseigneException : le libellé doit être obligatoirement non vide (non blank)
     */

    public String creerQuestion(int idUser, String libelleQuestion, String... libellesReponses) throws AuMoinsDeuxReponsesException, LibelleQuestionNonRenseigneException {
        String q;

        if (libellesReponses.length < 2) {
            throw new AuMoinsDeuxReponsesException();
        } else if (libelleQuestion.isBlank()) {
            throw new LibelleQuestionNonRenseigneException();
        } else {
            Question question = new Question(idUser, libelleQuestion, libellesReponses);
            q = question.getIdQuestion();
            questions.add(question);
        }
        return q;
    }


    /**
     * Permet de récupérer une question par son identifiant
     * @param idQuestion : id de la question concernée
     * @return l'objet Question concerné
     * @throws QuestionInexistanteException : l'identifiant donné ne correspond à aucune question
     */

    public Question getQuestionById(String idQuestion) throws QuestionInexistanteException {
        return questions.stream()
                .filter(question -> question.getIdQuestion().equals(idQuestion))
                .findFirst()
                .orElseThrow(QuestionInexistanteException::new);
    }

    /**
     * Permet à un étudiant de voter pour une réponse
     * @param idUser : identifiant entier de l'étudiant en question (là encore on suppose que l'idUser est correct et que c'est bien un étudiant. Cette
     *               vérification est déléguée au contrôleur REST)
     * @param idQuestion : identifiant de la question concernée
     * @param numeroProposition : numéro de la proposition (les réponses possibles sont stockées dans un tableau donc le
     *                          numéro correspond à l'indice dans le tableau)
     * @throws ADejaVoteException : l'étudiant concerné a déjà voté à cette question (éventuellement pour une autre réponse)
     * @throws NumeroPropositionInexistantException : le numéro de la proposition n'est pas un indice correct du tableau des propositions
     * de la question
     * @throws QuestionInexistanteException : la question identifiée n'existe pas
     */

    public void voterReponse(int idUser,String idQuestion, int numeroProposition) throws ADejaVoteException,
            NumeroPropositionInexistantException, QuestionInexistanteException {
            questions.stream()
                    .filter(question -> question.getIdQuestion().equals(idQuestion))
                    .findAny()
                    .orElseThrow(QuestionInexistanteException::new)
                    .voterPourUneReponse(idUser, numeroProposition);
    }


    /**
     * Vous devez dans la fonction ci-dessous vider toutes vos structures de données.
     * Pensez à remettre à 0 vos éventuels compteurs statiques (probablement dans la classe utilisateur)
     */

    public void reinitFacade(){
        Utilisateur.resetCompteur();
        users.clear();
        questions.clear();
    }


    /**
     * Permet de récupérer un utilisateur par son email
     * @param username
     * @return
     */
    public Utilisateur getUtilisateurByEmail(String username) throws UtilisateurInexistantException {
        return users.stream()
                .filter(utilisateur -> utilisateur.getEmailUtilisateur().contains(username))
                .findFirst()
                .orElseThrow(UtilisateurInexistantException::new);
    }


    /**
     * Permet de récupérer le résultat d'un vote à une question
     * @param idQuestion
     * @return
     * @throws QuestionInexistanteException
     */

    // on peut vraiment code à 3 ?!
    public ResultatVote[] getResultats(String idQuestion) throws QuestionInexistanteException {
        return getQuestionById(idQuestion).getResultats();
    }
}
