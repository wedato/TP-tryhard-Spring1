package fr.orleans.info.wsi.cc.tpnote.modele;

public class Utilisateur {

    private int idUtilisateur;
    private String emailUtilisateur;
    private String motDePasseUtilisateur;
    private static int ID=0;

    public String getEmailUtilisateur() {
        return emailUtilisateur;
    }

    public void setEmailUtilisateur(String emailUtilisateur) {
        this.emailUtilisateur = emailUtilisateur;
    }

    public String getMotDePasseUtilisateur() {
        return motDePasseUtilisateur;
    }

    public void setMotDePasseUtilisateur(String motDePasseUtilisateur) {
        this.motDePasseUtilisateur = motDePasseUtilisateur;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Utilisateur() {
    }

    public Utilisateur(String emailUtilisateur, String motDePasseUtilisateur) {
        this.emailUtilisateur = emailUtilisateur;
        this.motDePasseUtilisateur = motDePasseUtilisateur;
        this.idUtilisateur = ID;
        ID++;
    }


    /**
     * Permet de réinitialiser le compteur
     */
    public static void resetCompteur(){
        ID=0;
    }
}
