# 🎹 MusicaLau - Générateur de Musique Interactif

Bienvenue dans le dépôt du **Générateur de Musique Interactif**, une application développée pour **MusicaLau**, un
magasin d’instruments de musique souhaitant proposer une solution numérique accessible à tous.

## 🎯 Objectif du Projet

L'objectif est de permettre aux utilisateurs de créer de la musique via une interface interactive, sans avoir besoin
d’acheter un véritable instrument. Cette application se veut ludique, éducative et accessible, en réponse au besoin de
MusicaLau de toucher un plus large éventail d’acheteurs avec une solution numérique pour des budgets plus modestes.

## 🧩 Fonctionnalités Principales (Selon les Consignes)

- 🎹 **Choix d’un instrument** :
    - L'application doit permettre de choisir l'instrument que l’on souhaite utiliser et l’afficher.
    - Instruments minimum requis :
        - Piano (avec sélection du nombre d’octaves, minimum 2)
        - Xylophone
      - "Video Game" (instrument de style jeu vidéo)
  - **Un seul instrument est affiché à la fois** dans l’interface.
  - Une fois sélectionné, l'utilisateur pourra jouer de l’instrument.

- 🎼 **Fonctionnalités complémentaires** :
    - **Ouvrir** : Charger une partition de musique (ex : `mario.txt`, `bella_ciao.txt` fournis dans
      `consignes/partitions/`) à jouer avec l'instrument actif.
    - **Enregistrer** : Sauvegarder les notes jouées dans un fichier, avec un nom choisi par l'utilisateur.
    - **Retour** : Revenir au menu de sélection d’instruments.
    - **Quitter** : Fermer l'application.

## 🛠️ Caractéristiques Techniques

- **Langage** : Java
- **Interface Graphique** : Swing
- **Génération des Sons** : Les sons émis par les instruments doivent être **générés par le code** (pas de fichiers
  audio externes - sauf pour le chat).
- **Piano** : L’utilisateur doit pouvoir choisir le nombre d’octaves qu’il souhaite utiliser (minimum 2).
- **Interaction** : Interface interactive permettant de **jouer en temps réel**.

## 💡 Nos Innovations

Les éléments demandés dans les consignes initiales ne constituent pas l’intégralité de notre projet. Une
partie sera également évaluée sur les apports et améliorations que vous ferez. Nous avons implémenté les innovations suivantes :

- **Ajout d’instruments supplémentaires** : En plus du Piano, Xylophone et "Video Game", nous avons ajouté :
    - Tambour (Drums)
    - Orgue (Organ)
- **Lecteur de fichier MP3 (pour le thème "Chat")** : Une fonctionnalité spécifique permettant de jouer un son externe
  pour une expérience thématique.
- **Animations des touches et des boutons** : Pour une interface plus dynamique et réactive visuellement.
- **Enregistrement des partitions au format réutilisable** : Les notes jouées peuvent être enregistrées dans un fichier
  `.txt` respectant le même format que les partitions fournies (`note durée`). Cela permet de rejouer facilement les
  créations de l'utilisateur avec n'importe quel instrument de l'application.
- **Visualisation graphique des notes jouées** : Dans chaque instrument.
- **Création de nouvelles partitions** : Pour plus de fun, jouer `au_clair_de_la_lune.txt` ou encore `fur_elise.txt`

## 🚀 Comment Exécuter le Projet

Pour compiler et exécuter ce projet Java, vous aurez besoin de :

1. **JDK (Java Development Kit)** : Assurez-vous d'avoir un JDK installé (version 8 ou supérieure recommandée). Vous
   pouvez le télécharger depuis le site d'Oracle ou opter pour une distribution OpenJDK.
2. **Maven**: Assurez-vous qu'il soit installé et configuré.
3. **IDE (Environnement de Développement Intégré) ou Ligne de Commande** :
    * **Avec un IDE (IntelliJ IDEA, Eclipse, NetBeans)** :
        1. Clonez ce dépôt : `git clone <https://github.com/agathemczk/piano>`
        2. Ouvrez le projet dans votre IDE. L'IDE devrait détecter la structure Maven si applicable.
        3. Localisez la classe principale (dans le module `main` `com.pianoo.main.Main`) et exécutez-la.
    * **En Ligne de Commande (avec Maven, si applicable)** :
      En vous plaçant à la racine du projet (où se trouve le `pom.xml` principal).
