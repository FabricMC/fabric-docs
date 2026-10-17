---
title: Règles du jeu
description: Un guide pour ajouter des règles personnalisées.
authors:
  - cassiancc
  - Jummit
  - modmuss50
  - Wind292
authors-nogithub:
  - mysterious_dev
  - solacekairos
---

<!---->

:::info PREREQUIS

Vous pourrez compléter la [génération de la traduction](./data-generation/translations) en premier, mais cela n'est pas nécessaire.

:::

Les règles agissent comme une configuration spécifique au monde que le joueur peut changer dans le jeu grâce à une commande. Ces variables contrôlent, normalement, quelques fonctions du monde, par exemple `pvp`, `spawn_monster` et `advance_time` contrôle si le PvP est autorisé, l'apparition des mobs, et le temps qui passe.

## Création d'une règle {#creating-a-game-rule}

Pour créer une règle personnalisée, créer premièrement une classe `GameRules` ; c'est là où on déclara nos règles du jeu. Dans cette classe, déclarer deux constantes : un identifiant de la règle et la règle elle-même.

<<< @/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#gamerule_class

La catégorie (`.category(GameRuleCategory.MISC)`) détermine quelle catégorie la règle chargera pendant l'écran de la génération du monde. Cet exemple utilise la catégorie **Outils et utilitaire** fourni par la version vanilla, mais des catégories supplémentaires peuvent être rajoutées via `GameRuleCategory.register`. Dans cet exemple, nous avons créé une règle booléenne avec une valeur par défaut `false` et un id de `bad_vision`. La valeur stockée dans les règles ne sont pas limité aux booléens ; autres types valides comprenant `Double`s, `Integer`s, et `Enum`s.

Exemple d'une règle qui stocke une double :

<<< @/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#double

## Accéder à une règle {#accessing-a-game-rule}

Maintenant qu'on a une règle de jeu et son `Identifier`, vous pouvez y accéder partout avec la méthode `serverLevel.getGameRules().get(GAMERULE)`, où l'argument `.get()` est la règle de jeu constante et pas l'id de la règle.

<<< @/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#badvision_get

Vous pouvez aussi utiliser ceci pour accès aux règles du jeu de base :

<<< @/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#vanilla

Par exemple, une règle qui applique l'effet de Cécité à tous les joueurs si vrai, l'implémentation serai comme :

<<< @/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#badvision_implement

## Traductions {#traductions}

Maintenant, nous devons donner à notre règle un nom à afficher pour qu'elle soit compris depuis le menu des règles du jeu. Pour le faire via la génération de données, ajouter ces lignes suivantes à votre langage :

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java#gamerule_name

Dernièrement, donnons une description à notre règle. Pour le faire via la génération de données, ajouter ces lignes suivantes à votre langage :

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java#gamerule_description

::: info

Ces traductions de touche sont utilisés quand le texte est affiché dans le menu des règles. Si vous ne voulez pas utiliser la génération de données, vous pouvez les écrire à la main dans votre `assets/example-mod/lang/en_us.json`.

```json
"example-mod.bad_vision": "Bad Vision",
"gamerule.example-mod.bad_vision": "Gives every player the blindness effect",
```

:::

## Changement des règles dans le jeu {#changing-game-rules-in-game}

Maintenant, vous devriez être capable de changer la valeur de votre règle dans le jeu avec la commande `/gamerules` comme ceci :

```mcfunction
/gamerule example-mod:bad_vision true
```

La règle s'affiche maintenant dans la catégorie **Outils et utilitaire** dans l'écran d'édition des règles du jeu.

![L'écran de la génération du monde montrant la règle du jeu Bad vision](/assets/develop/game-rules/world-creation.png)
