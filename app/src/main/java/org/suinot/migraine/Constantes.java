package org.suinot.migraine;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by remi on 26/11/16.
 */

public class Constantes {

    public interface constantes {
        
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

        /* Définition de la table Douleur */
         static final String TABLE_DOULEURS = "douleurs";

         static final String COL_DOULEUR_ID = "ID";
         static final int DOULEUR_COL_ID = 0;
         static final String COL_ID_MEDICAMENT = "MEDICAMENT";
         static final int DOULEUR_COL_ID_MEDICAMENT = 1;
         static final String COL_INTENSITE = "INTENSITE";
         static final int DOULEUR_COL_INTENSITE = 2;
         static final String COL_DOULEUR_DUREE = "DUREE";
         static final int DOULEUR_COL_DUREE = 3;
         static final String COL_DOULEUR_DATE = "DATE";
         static final int DOULEUR_COL_DATE = 4;
         static final String COL_DOULEUR_HEURE = "HEURE";
         static final int DOULEUR_COL_HEURE = 5;

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
        static final String COL_CROISEE_MIGRAINE = "MIGRAINE_C";
        static final int NUM_COL_CROISEE_MIGRAINE = 2;

    }
}
