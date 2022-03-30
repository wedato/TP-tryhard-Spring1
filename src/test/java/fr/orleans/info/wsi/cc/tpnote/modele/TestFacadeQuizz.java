package fr.orleans.info.wsi.cc.tpnote.modele;

import fr.orleans.info.wsi.cc.tpnote.modele.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestFacadeQuizz {

    FacadeQuizz instance;


    /**
     * Initialisation de la façade
     */
    @BeforeEach
    public void initialiseInstance(){
        instance = new FacadeQuizz();
    }


    /**
     * Tentative de création d'un compte avec un email déjà pris
     */


    @Test
    public void testCreerUtilisateur1()  {

        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";

        String email1 = "yohan.boichut@univ-orleans.fr";
        String motDePasse1 = "123456";
        Assertions.assertDoesNotThrow(()->this.instance.creerUtilisateur(email,motDePasse));
        Assertions.assertThrows(EmailDejaUtiliseException.class,() ->this.instance.creerUtilisateur(email1,motDePasse1));
    }


    /**
     * Tentative de création d'un compte avec un mot de passe blank
     */
    @Test
    public void testCreerUtilisateur2()  {

        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "     ";

        Assertions.assertThrows(MotDePasseObligatoireException.class,() ->this.instance.creerUtilisateur(email,motDePasse));
    }


    /**
     * Tentative de création d'un compte avec un mot de passe nul
     */
    @Test
    public void testCreerUtilisateur3()  {

        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = null;

        Assertions.assertThrows(MotDePasseObligatoireException.class,() ->this.instance.creerUtilisateur(email,motDePasse));
    }


    /**
     * Tentative d'une création de compte avec un email non valide
     */

    @Test
    public void testCreerUtilisateur4()  {

        String email = "yohan.boichutuniv-orleans.fr";
        String motDePasse = "1234";

        Assertions.assertThrows(EmailNonValideException.class,() ->this.instance.creerUtilisateur(email,motDePasse));
    }

    /**
     * Création d'un compte valide
     * @throws MotDePasseObligatoireException
     * @throws EmailNonValideException
     * @throws EmailDejaUtiliseException
     */

    @Test
    public void testCreerUtilisateur6() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException {

        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";
        Integer id = this.instance.creerUtilisateur(email,motDePasse);
        Assertions.assertNotNull(id,"le compte devrait être créé");
    }


    /**
     * Récupération correcte de l'identifiant d'un email existant
     * @throws MotDePasseObligatoireException
     * @throws EmailNonValideException
     * @throws EmailDejaUtiliseException
     * @throws EmailInexistantException
     */
    @Test
    public void testGetIdUserByEmail1() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, EmailInexistantException {
        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";
        Integer id = this.instance.creerUtilisateur(email,motDePasse);
        Assertions.assertEquals(id,instance.getIdUserByEmail(email));
    }


    /**
     * Tentative de récupération d'un identifiant pour un email inexistant
     * @throws MotDePasseObligatoireException
     * @throws EmailNonValideException
     * @throws EmailDejaUtiliseException
     * @throws EmailInexistantException
     */

    @Test
    public void testGetIdUserByEmail2() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, EmailInexistantException {
        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";
        String email1="gerard.menvuça@univ-orleans.fr";
        this.instance.creerUtilisateur(email,motDePasse);
        Assertions.assertThrows(EmailInexistantException.class,() ->instance.getIdUserByEmail(email1));
    }

    /**
     * Création correcte d'une question
     * @throws MotDePasseObligatoireException
     * @throws EmailNonValideException
     * @throws EmailDejaUtiliseException
     * @throws LibelleQuestionNonRenseigneException
     * @throws AuMoinsDeuxReponsesException
     */

    @Test
    public void testCreerQuestion1() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, LibelleQuestionNonRenseigneException, AuMoinsDeuxReponsesException {
        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";
        int id = this.instance.creerUtilisateur(email,motDePasse);
        String idQuestion = this.instance.creerQuestion(id,"Quelle est la couleur du cheval blanc d'Henry IV ?","Blanc","Noir","Rouge");
        Assertions.assertNotNull(idQuestion);
    }

    /**
     * Tentative de création d'une question avec un nombre de réponses insuffisant
     * @throws MotDePasseObligatoireException
     * @throws EmailNonValideException
     * @throws EmailDejaUtiliseException
     * @throws LibelleQuestionNonRenseigneException
     * @throws AuMoinsDeuxReponsesException
     */

    @Test
    public void testCreerQuestion2() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, LibelleQuestionNonRenseigneException, AuMoinsDeuxReponsesException {
        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";
        int id = this.instance.creerUtilisateur(email,motDePasse);
        Assertions.assertThrows(AuMoinsDeuxReponsesException.class,()-> this.instance.creerQuestion(id,"Quelle est la couleur du cheval blanc d'Henry IV ?","Rouge"));
    }


    /**
     * Tentative d'une création de question avec un libellé blank
     * @throws MotDePasseObligatoireException
     * @throws EmailNonValideException
     * @throws EmailDejaUtiliseException
     * @throws LibelleQuestionNonRenseigneException
     * @throws AuMoinsDeuxReponsesException
     */
    @Test
    public void testCreerQuestion3() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, LibelleQuestionNonRenseigneException, AuMoinsDeuxReponsesException {
        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";
        int id = this.instance.creerUtilisateur(email,motDePasse);
        Assertions.assertThrows(LibelleQuestionNonRenseigneException.class,()-> this.instance.creerQuestion(id,"     ","Rouge", "Noir"));
    }


    /**
     * Récupération d'une question existante par son identifiant
     * @throws MotDePasseObligatoireException
     * @throws EmailNonValideException
     * @throws EmailDejaUtiliseException
     * @throws LibelleQuestionNonRenseigneException
     * @throws AuMoinsDeuxReponsesException
     * @throws QuestionInexistanteException
     */

    @Test
    public void testGetQuestionById1() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, LibelleQuestionNonRenseigneException, AuMoinsDeuxReponsesException, QuestionInexistanteException {
        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";
        int id = this.instance.creerUtilisateur(email,motDePasse);
        String idQuestion = this.instance.creerQuestion(id,"Quelle est la couleur du cheval blanc d'Henry IV ?","Blanc","Noir","Rouge");
        Assertions.assertNotNull(this.instance.getQuestionById(idQuestion));
    }


    /**
     * Tentative de récupération d'une question avec un identifiant erroné
     * @throws MotDePasseObligatoireException
     * @throws EmailNonValideException
     * @throws EmailDejaUtiliseException
     * @throws LibelleQuestionNonRenseigneException
     * @throws AuMoinsDeuxReponsesException
     * @throws QuestionInexistanteException
     */

    @Test
    public void testGetQuestionById2() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, LibelleQuestionNonRenseigneException, AuMoinsDeuxReponsesException, QuestionInexistanteException {
        Assertions.assertThrows(QuestionInexistanteException.class,() ->this.instance.getQuestionById("identifiantQuestionBidon"));
    }


    /**
     * Vote correct d'un étudiant à une question existante et pour une réponse existante
     * @throws MotDePasseObligatoireException
     * @throws EmailNonValideException
     * @throws EmailDejaUtiliseException
     * @throws LibelleQuestionNonRenseigneException
     * @throws AuMoinsDeuxReponsesException
     * @throws QuestionInexistanteException
     * @throws NumeroPropositionInexistantException
     * @throws ADejaVoteException
     */
    @Test
    public void testVoterReponse1() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, LibelleQuestionNonRenseigneException, AuMoinsDeuxReponsesException, QuestionInexistanteException, NumeroPropositionInexistantException, ADejaVoteException {
        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";
        int id = this.instance.creerUtilisateur(email,motDePasse);
        String idQuestion = this.instance.creerQuestion(id,"Quelle est la couleur du cheval blanc d'Henry IV ?","Blanc","Noir","Rouge");

        String emailEtu = "etudiant.brillant@etu.univ-orleans.fr";
        String motDePasseEtu = "1234";
        int idEtu = this.instance.creerUtilisateur(emailEtu,motDePasseEtu);
        Assertions.assertDoesNotThrow(()->instance.voterReponse(idEtu,idQuestion,0));
    }

    /**
     * Tentative de double vote.
     * @throws MotDePasseObligatoireException
     * @throws EmailNonValideException
     * @throws EmailDejaUtiliseException
     * @throws LibelleQuestionNonRenseigneException
     * @throws AuMoinsDeuxReponsesException
     * @throws QuestionInexistanteException
     * @throws NumeroPropositionInexistantException
     * @throws ADejaVoteException
     */
    @Test
    public void testVoterReponse2() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, LibelleQuestionNonRenseigneException, AuMoinsDeuxReponsesException, QuestionInexistanteException, NumeroPropositionInexistantException, ADejaVoteException {
        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";
        int id = this.instance.creerUtilisateur(email,motDePasse);
        String idQuestion = this.instance.creerQuestion(id,"Quelle est la couleur du cheval blanc d'Henry IV ?","Blanc","Noir","Rouge");

        String emailEtu = "etudiant.brillant@etu.univ-orleans.fr";
        String motDePasseEtu = "1234";
        int idEtu = this.instance.creerUtilisateur(emailEtu,motDePasseEtu);
        instance.voterReponse(idEtu,idQuestion,0);
        Assertions.assertThrows(ADejaVoteException.class,()->instance.voterReponse(idEtu,idQuestion,1));
    }


    /**
     * Tentative de vote avec un mauvais identifiant de réponse
     * @throws MotDePasseObligatoireException
     * @throws EmailNonValideException
     * @throws EmailDejaUtiliseException
     * @throws LibelleQuestionNonRenseigneException
     * @throws AuMoinsDeuxReponsesException
     * @throws QuestionInexistanteException
     * @throws NumeroPropositionInexistantException
     * @throws ADejaVoteException
     */
    @Test
    public void testVoterReponse3() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, LibelleQuestionNonRenseigneException, AuMoinsDeuxReponsesException, QuestionInexistanteException, NumeroPropositionInexistantException, ADejaVoteException {
        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";
        int id = this.instance.creerUtilisateur(email,motDePasse);
        String idQuestion = this.instance.creerQuestion(id,"Quelle est la couleur du cheval blanc d'Henry IV ?","Blanc","Noir","Rouge");

        String emailEtu = "etudiant.brillant@etu.univ-orleans.fr";
        String motDePasseEtu = "1234";
        int idEtu = this.instance.creerUtilisateur(emailEtu,motDePasseEtu);
        Assertions.assertThrows(NumeroPropositionInexistantException.class,()->instance.voterReponse(idEtu,idQuestion,-1));
    }

    /**
     * Tentative de vote avec un mauvais identifiant de réponse
     * @throws MotDePasseObligatoireException
     * @throws EmailNonValideException
     * @throws EmailDejaUtiliseException
     * @throws LibelleQuestionNonRenseigneException
     * @throws AuMoinsDeuxReponsesException
     * @throws QuestionInexistanteException
     * @throws NumeroPropositionInexistantException
     * @throws ADejaVoteException
     */
    @Test
    public void testVoterReponse4() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, LibelleQuestionNonRenseigneException, AuMoinsDeuxReponsesException, QuestionInexistanteException, NumeroPropositionInexistantException, ADejaVoteException {
        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";
        int id = this.instance.creerUtilisateur(email,motDePasse);
        String idQuestion = this.instance.creerQuestion(id,"Quelle est la couleur du cheval blanc d'Henry IV ?","Blanc","Noir","Rouge");

        String emailEtu = "etudiant.brillant@etu.univ-orleans.fr";
        String motDePasseEtu = "1234";
        int idEtu = this.instance.creerUtilisateur(emailEtu,motDePasseEtu);
        Assertions.assertThrows(NumeroPropositionInexistantException.class,()->instance.voterReponse(idEtu,idQuestion,12));
    }


    /**
     * Tentative de vote avec un mauvais identifiant de question
     * @throws MotDePasseObligatoireException
     * @throws EmailNonValideException
     * @throws EmailDejaUtiliseException
     * @throws LibelleQuestionNonRenseigneException
     * @throws AuMoinsDeuxReponsesException
     * @throws QuestionInexistanteException
     * @throws NumeroPropositionInexistantException
     * @throws ADejaVoteException
     */

    @Test
    public void testVoterReponse5() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, LibelleQuestionNonRenseigneException, AuMoinsDeuxReponsesException, QuestionInexistanteException, NumeroPropositionInexistantException, ADejaVoteException {
        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";
        this.instance.creerUtilisateur(email,motDePasse);
        String emailEtu = "etudiant.brillant@etu.univ-orleans.fr";
        String motDePasseEtu = "1234";
        int idEtu = this.instance.creerUtilisateur(emailEtu,motDePasseEtu);
        Assertions.assertThrows(QuestionInexistanteException.class,()->instance.voterReponse(idEtu,"identifiant bidon",1));
    }



    @Test
    public void testGetUtilisateurByEmail1() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, UtilisateurInexistantException {
        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";
        this.instance.creerUtilisateur(email,motDePasse);
        Assertions.assertNotNull(this.instance.getUtilisateurByEmail(email));
    }

    @Test
    public void testGetUtilisateurByEmail2() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, UtilisateurInexistantException {
        String email = "yohan.boichut@univ-orleans.fr";
        Assertions.assertThrows(UtilisateurInexistantException.class,()->this.instance.getUtilisateurByEmail(email));
    }



    @Test
    public void testReInit() throws MotDePasseObligatoireException, EmailNonValideException, EmailDejaUtiliseException, LibelleQuestionNonRenseigneException, AuMoinsDeuxReponsesException {
        String email = "yohan.boichut@univ-orleans.fr";
        String motDePasse = "1234";
        int id = this.instance.creerUtilisateur(email,motDePasse);
        this.instance.creerQuestion(id,"Quelle est la couleur du cheval blanc d'Henry IV ?","Blanc","Noir","Rouge");
        instance.reinitFacade();
        Assertions.assertDoesNotThrow(()->this.instance.creerUtilisateur(email,motDePasse));


    }

}
