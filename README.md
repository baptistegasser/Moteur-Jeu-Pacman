# Moteur de jeu et Pac-Man
[![GitHub license](https://img.shields.io/github/license/baptistegasser/Moteur-Jeu-Pacman.svg)](LICENSE)

> Projet réalisé dans le cadre du cours de Génie Logiciel de M1 Informatique.

Le but est de créer un moteur de jeu fonctionnel et d'utiliser celui-ci afin de créer un jeu
de type Pac-Man afin de démontrer que le moteur est fonctionnel et utilisable.

## Dépendances
- require Java JDK 11 or superior
- maven v3.6.3+ (previous version might work but might have unpredicted behaviors)

### Installation
Cloner le repository git:
```shell
git clone https://github.com/baptistegasser/Moteur-Jeu-Pacman.git
```

Créer le jar:
```shell
cd Moteur-Jeu-Pacman
mvn clean package
```

Exécuter Pac-Man:
```shell
java -jar pacman/shade/pacman.jar
```

## Utilisation
Le jeu se déroule de manière assez intuitive.
Pac-Man est déplaçable avec les touches fléchées du clavier.
Il est également possible de mettre le jeu en pause en appuyant sur la touche Echap.

### Déroulement du jeu
Le but est de passer de niveau en niveau.
Pour passer au niveau suivant, Pac-Man doit manger toutes les gommes (pac) d'un niveau.
Si Pac-Man est touché par un fantôme, il perd une vie. Au bout de trois vies perdu, la partie est perdue.
Il existe 2 bonus :
 * classique -> une grosse gomme qui permet de manger les fantômes.
 * arc-en-ciel -> une gomme arc-en-ciel qui permet de manger les murs en face de soi.

## License
[MIT](LICENSE)