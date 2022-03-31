package fr.orleans.info.wsi.cc.tpnote;

import org.springframework.stereotype.Component;

@Component
public interface DataTest {
    String emailProfBase();

    String motDePasseProfBase();

    String emailEtudiantBase();

    String motDePasseEtudiantBase();

    String libelleQuestion();

    String mauvaisLibelleQuestion();

    String identifiantQuestionBidon();

    String[] bonnesReponses();

    String[] mauvaisesReponses();

    String emailErrone();

    String mauvaisMotDePasse();
}
