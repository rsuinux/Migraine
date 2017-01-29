package org.suinot.migraine;

/**
 * Created by remi on 26/11/16.
 */

public class Constantes {

    public interface constantes {

        /* Nom de la base */
        static final String NOM_DB_MIGRAINES = "Migraines.db";
        static final int VERSION_DB_MIGRAINES = 1;
        static final String NOM_BD_MEDICAMENTS = "Antalgiques.db";
        static final int VERSION_DB_MEDICAMENTS = 1;

        /* Définition de la table Migraine */
        static final String TABLE_MIGRAINES = "migraines";

        static final String COL_ID = "ID_MIGRAINE";
        static final int MIGRAINE_COL_ID = 0;
        static final String COL_NOM = "NOM";
        static final int MIGRAINE_COL_NOM = 1;
        static final String COL_DATE = "DATE";
        static final int MIGRAINE_COL_DATE = 2;
        static final String COL_HEURE = "HEURE";
        static final int MIGRAINE_COL_HEURE = 3;
        static final String COL_DUREE = "DUREE";
        static final int MIGRAINE_COL_DUREE = 4;
        static final String COL_COMMENTAIRE = "COMMENTAIRE";
        static final int MIGRAINE_COL_COMMENTAIRE = 5;
        static final String COL_ETAT = "ETAT";
        static final int MIGRAINE_COL_ETAT = 6;
        static final String COL_DATE_FIN="DATE_FIN";
        static final int MIGRAINE_COL_DATE_FIN =7;
        static final String COL_HEURE_FIN="HEURE_FIN";
        static final int MIGRAINE_COL_HEURE_FIN =8;

        /* Définition de la table Douleur */
        static final String TABLE_DOULEURS = "douleurs";

        static final String COL_DOULEUR_ID = "ID";
        static final int DOULEUR_COL_ID = 0;
        static final String COL_INTENSITE = "INTENSITE";
        static final int DOULEUR_COL_INTENSITE = 1;
        static final String COL_DOULEUR_DUREE = "DUREE";
        static final int DOULEUR_COL_DUREE = 2;
        static final String COL_DOULEUR_DATE = "DATE";
        static final int DOULEUR_COL_DATE = 3;
        static final String COL_DOULEUR_HEURE = "HEURE";
        static final int DOULEUR_COL_HEURE = 4;

        /* Définition de la table Médicaments */
        static final String TABLE_MEDIC = "medicaments";

        static final String COL_MEDIC_ID = "ID";
        static final int NUM_COL_MEDIC_ID = 0;
        static final String COL_MEDIC_NOM = "NOM";
        static final int NUM_COL_MEDIC_NOM = 1;
        static final String COL_MEDIC_DOSE = "DOSE";
        static final int NUM_COL_MEDIC_DOSE = 2;
        static final String COL_MEDIC_INVALIDE = "INVALIDE";
        static final int NUM_COL_MEDIC_INVALIDE = 3;

        /* Définition de la table Croisee */
        static final String TABLE_CROISEE = "croisee";

        static final String COL_CROISEE_ID = "ID";
        static final int NUM_COL_CROISEE_ID = 0;
        static final String COL_CROISEE_DOULEUR = "DOULEUR_C";
        static final int NUM_COL_CROISEE_DOULEUR = 1;
        static final String COL_CROISEE_MEDICAMENT = "MEDICAMENT_C";
        static final int NUM_COL_CROISEE_MEDICAMENT = 2;
        static final String COL_CROISEE_MIGRAINE = "MIGRAINE_C";
        static final int NUM_COL_CROISEE_MIGRAINE = 3;

        static final int RETOUR_NOUVELLE_MIGRAINE = 1; // retour de l'activity Nouvelle Migraine
        static final int RETOUR_MIGRAINE_EN_COURS = 2;  // retour de l'activity Migraine en cours
        static final int RETOUR_CONFIGURATION = 3;  // retour de l'activity Configuration
        static final int RETOUR_HISTORIQUE = 4;  // retour de l'activity Historique

        static final int DIALOG_INFORMATION = 1;
        static final int DIALOG_HISTORIQUE_UNITAIRE = 2;

    }
}
