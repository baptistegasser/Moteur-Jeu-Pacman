# Moteur de Jeu et Pacman
[![GitHub license](https://img.shields.io/github/license/baptistegasser/Moteur-Jeu-Pacman.svg)](LICENSE)
![Version](https://img.shields.io/badge/verson-0.1--alpha-informational)

Ce projet contient un moteur de jeu 2D ainsi qu'un jeu de démo reprenant le concept de Pacman.

## Installation
### Pré-requis
- git
- maven
- JDK >= 11

### Instructions
Cloner le repo avec git et se placer à la racine.
```shell
git clone https://github.com/baptistegasser/Moteur-Jeu-Pacman.git
cd Moteur-Jeu-Pacman
```


## Utilisation
Afin que le projet soit cross-platform, un uber-jar est cré à l'aide de maven shade.

Pour le construire, simplement utilisé maven :
```shell
mvn package
```

Pour utiliser le jar:
```shell
java -jar shade/Pacman.jar
```


## Licence
[MIT](LICENSE)
