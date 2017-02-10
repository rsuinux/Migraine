Application Android pour les migraineux.
Première lignes: 18 Aout 2016
Autheur: R. Suinot <remi [at] suinot [point] org>

En cours d'écriture.
A la fin, l'utilisateur pourra:
  1/ indiquer le début de crise
  2/ ce qu'il a pris comme antalgique
  3/ noter sa douleur
  3 bis/ plusieurs fois!
  4/ ajouter/supprimer des antalqique dans le panneau de configuration
  5/ exporter les données (ou non)
  6/ grapher les données (en temps ou en douleur)

Un certain nombre de points fonctionnent déjà.

/*--------------------------------------- */
 20 Nov 2016
/*--------------------------------------- */
Ajout de la gestion de la base Migraine et des tables migraines et douleurs
Retour de données sur l'activité principale.
Ajout d'une activité "Historique" 
	-> listview à écrire pour elle.

/*--------------------------------------- */
01 fevrier 2017
/*--------------------------------------- */
Modification de la base de donnée

visualisation rapide de tous les evenements 
Ajout d'un historique par un clique sur un élément
deux états possible d'un évenement: 
  en cours-> pn peut dans ce cas ajouter une note de douleur, un médicament pris et un commentaire ou même terminer l'évenement
  terminé: dans ce cas, un clique donne l'historique de toute les douleurs et médicaments notés avec leurs commentaire pour cet évenement.

/*--------------------------------------- */
09 fevrier 2017
/*--------------------------------------- */
1/ To Do: suppresion d'un évennement par clique long (evenement en cours ou pas) suite à un premier test utilisateur grandeur réel.
2/ To Do: erreur lors de la rotation de l'ecran: pas de redessin du listview evenement..
