package fr.orleans.info.wsi.cc.tpnote.modele;

import fr.orleans.info.wsi.cc.tpnote.modele.exceptions.*;
import org.springframework.stereotype.Component;

@Component
public class FacadeQuizz {


    /**
     *
     * @param email : email valide
     * @param password : mot de passe utilisateur non vide
     * @return identifiant entier
     * @throws EmailDejaUtiliseException : email déjà utilisé
     * @throws EmailNonValideException : email n'est pas de la bonne forme
     * @throws MotDePasseObligatoireException : le mot de passe est Blank ou nul
     */

    public int creerUtilisateur(String email,String password) throws EmailDejaUtiliseException, EmailNonValideException, MotDePasseObligatoireException {
        return 0;
    }

    /**
     * Permet de récupérer l'identifiant int d'un utilisateur par son E-mail
     * @param email
     * @return identifiant int
     */

    public int getIdUserByEmail(String email) throws EmailInexistantException {
        return 0;
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
        return null;
    }


    /**
     * Permet de récupérer une question par son identifiant
     * @param idQuestion : id de la question concernée
     * @return l'objet Question concerné
     * @throws QuestionInexistanteException : l'identifiant donné ne correspond à aucune question
     */

    public Question getQuestionById(String idQuestion) throws QuestionInexistanteException {
        return null;
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

    }


    /**
     * Vous devez dans la fonction ci-dessous vider toutes vos structures de données.
     * Pensez à remettre à 0 vos éventuels compteurs statiques (probablement dans la classe utilisateur)
     */

    public void reinitFacade(){
    //TODO
    }

}
