---
title: Paramètres de Commandes
description: Apprenez comment créer des commandes avec des paramètres complexes.
---

# Paramètres de commandes {#command-arguments}

La notion de paramètres (`Argument`) est utilisée dans la plupart des commandes. Des fois, ces paramètres peuvent être optionnels, ce qui veut dire que si vous ne donnez pas ce paramètre, la commande va quand même s'exécuter. Un nœud peut avoir plusieurs types de paramètres, mais n'oubliez pas qu'il y a une possibilité d'ambiguïté, qui devrait toujours être évitée.

@[code lang=java highlight={3} transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Dans ce cas d'exemple, après la commande textuelle `/argtater`, vous devez donner un nombre. Par exemple, si vous exécutez `/argtater 3`, vous allez avoir en retour le message `Called /argtater with value = 3`. Si vous tapez `/argater` sans arguments, la commande ne pourra pas être correctement analysée.

Nous ajoutons ensuite un second paramètre optionnel :

@[code lang=java highlight={3,13} transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Maintenant, vous pouvez donner un ou deux arguments de type nombre à la commande. Si vous donnez un seul nombre, vous allez avoir en retour un texte avec une seule valeur d'affichée. Si vous donnez deux nombres, vous allez avoir en retour un texte avec deux valeurs d'affichées.

Vous pouvez penser qu'il n'est pas nécessaire de spécifier plusieurs fois des exécutions similaires. Donc, nous allons créer une méthode qui va être utilisée pour les deux cas d'exécution.

@[code lang=java highlight={3,5,6,7} transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Types de paramètre personnalisé {#custom-argument-types}

Si le type de paramètre de commande dont vous avez besoin n'existe pas en vanilla, vous pouvez toujours le créer par vous-même. Pour le faire, vous avez besoin de créer une nouvelle classe qui hérite l'interface `ArgumentType<T>` où `T` est le type du paramètre.

Vous devrez implémenter la méthode `parse`, qui va donc analyser la saisie de chaine de caractères afin de la transformer en le type désiré.

Par exemple, vous pouvez créer un type de paramètre qui transforme une chaine de caractères en `BlockPos` avec le format suivant : `{x, y, z}`

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/BlockPosArgumentType.java)

### Enregistrer des types de paramètres personnalisés {#registering-custom-argument-types}

:::warning
Vous devez enregistrer votre paramètre de commande personnalisée à la fois sur le serveur et sur le client, sinon dans le cas contraire cette commande ne fonctionnera pas !
:::

Vous pouvez enregistrer votre paramètre de commande personnalisé dans la méthode `onInitialize` de l'initialiseur de votre mod en utilisant la classe `ArgumentTypeRegistry` :

@[code lang=java transcludeWith=:::11](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Utiliser des types de paramètre personnalisé {#using-custom-argument-types}

Nous pouvons utiliser notre paramètre de commande personnalisé dans une commande, en passant une instance de ce dernier dans la méthode `.argument` du constructeur de commande.

@[code lang=java transcludeWith=:::10 highlight={3}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

En exécutant la commande, on peut vérifier si le paramètre de commande fonctionne ou pas :

![Argument invalide](/assets/develop/commands/custom-arguments_fail.png)

![Argument valide](/assets/develop/commands/custom-arguments_valid.png)

![Résultat de commande](/assets/develop/commands/custom-arguments_result.png)
