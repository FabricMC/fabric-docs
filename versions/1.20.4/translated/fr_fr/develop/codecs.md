---
title: Codecs
description: Un guide complet pour comprendre et utiliser le système de codecs de Mojang pour la sérialisation et désérialisation d'objets.
authors:
  - enjarai
  - Syst3ms

search: false
---

# Codecs

Les codecs sont un système de sérialisation facile d'objets Java, et est inclus dans la librairie DataFixerUpper (DFU) de Mojang, qui vient avec Minecraft. Dans la création de mods, ils peuvent être utilisés comme une alternative à GSON et Jankson pour lire des fichiers JSON personnalisés.

Les codecs sont utilisés en tandem avec une autre API de DFU, `DynamicOps`. Un codec définit la structure d'un objet, et les `DynamicOps` (litt. 'opérations dynamiques') définissent un format de (dé)sérialisation, comme JSON ou NBT. Cela signifie que n'importe quel codec peut être utilisé avec n'importe quelles `DynamicOps`, et vice versa, pour une flexibilité accrue.

## Utilisation des codecs

### Sérialisation et désérialisation

En premier lieu, un codec est utilisé pour sérialiser et désérialiser des objets vers et à partir d'un format donné.

Puisque quelques classes vanilla définissent déjà des codecs, on peut prendre ceux-là en exemple. Mojang fournit également deux `DynamicOps` par défaut, `JsonOps` et `NbtOps`, qui recouvrent la plupart des utilisations.

Supposons qu'on veuille sérialiser une `BlockPos` en JSON et inversement. C'est possible en utilisant le codec stocké statiquement en `BlockPos.CODEC` avec les méthodes `Codec#encodeStart` et `Codec#parse`, respectivement.

```java
BlockPos pos = new BlockPos(1, 2, 3);

// Serialisation de la BlockPos en JsonElement
DataResult<JsonElement> result = BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos);
```

Quand on manipule un codec, les valeurs renvoyées prennent la forme d'un `DataResult` (litt. 'résultat de données'). C'est un adaptateur qui représente soit un succès, soit un échec. On peut l'utiliser de plusieurs façons : si on veut juste la valeur sérialisée, `DataResult#result` renverra un `Optional` contenant la valeur, alors que `DataResult#resultOrPartial` permet également de fournir une fonction pour prendre en charge d'éventuelles erreurs qui se sont produites. La seconde est particulièrement utile pour les ressources de packs de données, où il est souhaitable de signaler les erreurs sans créer de problèmes autre part.

Prenons donc notre valeur sérialisée et transformons-la en `BlockPos` :

```java
// Si on écrivait un vrai mod, il faudrait évidemment prendre en charge les Optionals vides
JsonElement json = result.resultOrPartial(LOGGER::error).orElseThrow();

// Voici notre valeur JSON, qui devrait correspondre à `[1,2,3]`,
// puisque c'est le format que le codec de BlockPos utilise.
LOGGER.info("BlockPos sérialisée : {}", json);

// Maintenant on désérialise le JsonElement en BlockPos
DataResult<BlockPs> result = BlockPos.CODEC.parse(JsonOps.INSTANCE, json);

// Encore une fois, on extrait directement notre valeur du résultat
BlockPos pos = result.resultOrPartial(LOGGER::error).orElseThrow();

// Et on peut voir qu'on a sérialisé et désérialisé notre BlockPos avec succès !
LOGGER.info("BlockPos désérialisée : {}", pos);
```

### Codecs intégrés

Comme mentionné ci-dessus, Mojang a déjà défini des codecs pour plusieurs classes vanilla et Java standard, y compris, sans s'y limiter, `BlockPos`, `BlockState`, `ItemStack`, `Identifier`, `Text` et les `Pattern`s regex. Les codecs pour les classes de Mojang sont souvent des champs statiques nommés `CODEC` dans la classe-même, les autres se situant plutôt dans la classe `Codecs`. Par exemple, on peut utiliser `Registries.BLOCK.getCodec()` pour obtenir un `Codec<Block>` qui sérialise l'identifiant du bloc et inversement.

L'API des codecs contient déjà des codecs pour des types primitifs, comme `Codec.INT` et `Codec.STRING`. Ceux-ci sont disponibles statiquement dans la classe `Codec`, et servent souvent de briques pour des codecs plus avancés, comme expliqué ci-dessous.

## Construction de codecs

Maintenant qu'on sait utiliser les codecs, regardons comment construire le nôtre. Supposons qu'on ait la classe suivante, et qu'on veuille en désérialiser des instances à partir de fichiers JSON :

```java
public class CoolBeansClass {

    private final int beansAmount;
    private final Item beanType;
    private final List<BlockPos> beanPositions;

    public CoolBeansClass(int beansAmount, Item beanType, List<BlockPos> beanPositions) {...}

    public int getBeansAmount() { return this.beansAmount; }
    public Item getBeanType() { return this.beanType; }
    public List<BlockPos> getBeanPositions() { return this.beanPositions; }
}
```

Le fichier JSON correspondant pourrait ressembler à :

```json
{
  "beans_amount": 5,
  "bean_type": "beanmod:mythical_beans",
  "bean_positions": [
    [1, 2, 3],
    [4, 5, 6]
  ]
}
```

On peut créer un codec pour cette classe par assemblage de codecs plus simples. Dans ce cas-ci, il en faut un pour chaque champ :

- un `Codec<Integer>`
- un `Codec<Item>`
- un `Codec<List<BlockPos>>`

Le premier est un des codecs primitifs de la classe `Codec` mentionnés plus haut, plus précisément `Codec.INT`. Le deuxième s'obtient à partir du registre `Registries.ITEM` et sa méthode `getCodec()` qui renvoie un `Codec<Item>`. Il n'y a pas de codec par défaut pour `List<BlockPos>`, mais on peut en créer un à partir de `BlockPos.CODEC`.

### Listes

`Codec#listOf` crée une version liste de n'importe quel codec :

```java
Codec<List<BlockPos>> listCodec = BlockPos.CODEC.listOf();
```

À noter que les codecs créés ainsi se désérialisent toujours en `ImmutableList`. Si une liste mutable est nécessaire, on pourrait faire appel à [xmap](#mutually-convertible-types-and-you) pour effectuer la conversion.

### Fusion de codecs pour les classes similaires à des records

Avec les codecs pour chaque champ à notre disposition, on peut les combiner en un codec pour la classe avec un `RecordCodecBuilder`. On présuppose ici que la classe a un constructeur avec tous les champs qu'on veut sérialiser, et que ces champs ont des getters associés. C'est idéal pour des classes de type record, mais fonctionne également pour des classes normales.

Voyons voir comment créer un codec pour notre `CoolBeansClass` :

```java
public static final Codec<CoolBeansClass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.fieldOf("beans_amount").forGetter(CoolBeansClass::getBeansAmount),
    Registries.ITEM.getCodec().fieldOf("bean_type").forGetter(CoolBeansClass::getBeanType),
    BlockPos.CODEC.listOf().fieldOf("bean_positions").forGetter(CoolBeansClass::getBeanPositions)
    // Jusqu'à 16 champs peuvent être déclarés ici
).apply(instance, CoolBeansClass::new));
```

Chaque argument à la méthode `group` spécifie un codec, un nom de champ, et une méthode getter. L'appel à `Codec#fieldOf` convertit le codec en [codec map](#mapcodec) et celui à `forGetter` indique la méthode getter utilisée pour obtenir la valeur du champ à partir d'une instance de la classe. Enfin, `apply` spécifie le constructeur utilisé pour créer de nouvelles instances. Attention, l'ordre des champs dans la méthode `group` doit être le même que celui des arguments dans le constructeur.

On peut également utiliser `Codec#optionalFieldOf` dans ce contexte pour rendre un champ facultatif, comme expliqué dans la section [Champs facultatifs](#optional-fields).

### MapCodec, et non pas Codec&lt;Map&gt; {#mapcodec}

`Codec#fieldOf` transforme un `Codec<T>` en `MapCodec<T>` qui est une variante de `Codec<T>`, sans en être une implémentation directe. Comme leur nom peut le suggérer, les codecs map sérialisent leurs valeurs dans en maps clés-valeurs, ou plutôt leur équivalent dans les `DynamicOps` utilisées. Certaines fonctions peuvent en nécessiter un au lieu d'un codec normal.

Essentiellement, cette manière de créer un codec map encapsule simplement la valeur du codec initial dans une map, avec le nom de champ donné pour clé. Par exemple, un `Codec<BlockPos>` sérialiserait en JSON ainsi :

```json
[1, 2, 3]
```

Mais quand transformé en `MapCodec<BlockPos>` via `BlockPos.CODEC.fieldOf("pos")`, donnerait ceci :

```json
{
  "pos": [1, 2, 3]
}
```

Les codecs map servent principalement à être assemblés afin de construire un codec pour une classe avec plusieurs champs, comme expliqué dans la section [Fusion de codecs pour les classes similaires à des records](#merging-codecs-for-record-like-classes) ci-dessus.

#### Champs facultatifs

`Codec#optionalFieldOf` permet de créer un codec map facultatif. Si le champ en question n'est pas présent pendant la désérialisation, celui-ci va le déséraliser soit en `Optional` vide, soit une valeur par défaut donnée.

```java
// Sans valeur par défaut
MapCodec<Optional<BlockPos>> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos");

// Avec valeur par défaut
MapCodec<BlockPos> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos", BlockPos.ORIGIN);
```

Attention, les champs facultatifs vont ignorer silencieusement toute erreur lors de la désérialisation. Si le champ est présent mais la valeur invalide, il sera toujours désérialisé en la valeur par défaut.

**Depuis la 1.20.2**, Minecraft (et non pas DFU!) fournit cependant `Codecs#createStrictOptionalFieldCodec`, qui échoue à désérialiser si la valeur du champ est invalide.

### Constantes, contraintes et composition

#### Unit

`Codec.unit` sert à créer un codec qui désérialise toujours en une valeur constante, indépendamment de l'entrée. Lors de la sérialisation, ce codec ne fera rien.

```java
Codec<Integer> leSensDuCodec = Codec.unit(42);
```

#### Intervalles numériques

`Codec.intRange` et ses acolytes `Codec.floatRange` et `Codec.doubleRange` servent à créer un codec qui accepte seulement des valeurs numériques dans un intervalle donné, **bornes incluses**. Cela vaut et pour la sérialisation, et pour la désérialisation.

```java
// Ne peut excéder 2
Codec<Integer> amountOfFriendsYouHave = Codec.intRange(0, 2);
```

#### Pair

`Codec.pair` fusionne deux codecs `Codec<A>` et `Codec<B>` en un `Codec<Pair<A, B>>`. Il faut garder à l'esprit que cela ne marche correctement qu'avec des codecs qui sérialisent un champ précis, comme [des codec maps convertis](#mapcodec) ou [des codecs records](#merging-codecs-for-record-like-classes).
Le codec résultant sérialisera en une map qui combine les champs des deux codecs utilisés.

Par exemple, l'exécution de ce code :

```java
// Création de deux codecs encapsulés distincts
Codec<Integer> firstCodec = Codec.INT.fieldOf("un_nombre").codec();
Codec<Boolean> secondCodec = Codec.BOOL.fieldOf("cette_phrase_est_fausse").codec();

// Fusion en un codec paire
Codec<Pair<Integer, Boolean>> pairCodec = Codec.pair(firstCodec, secondCodec);

// Utilisation pour sérialiser des données
DataResult<JsonElement> result = pairCodec.encodeStart(JsonOps.INSTANCE, Pair.of(23, true));
```

Donnera ce JSON en sortie :

```json
{
  "un_nombre": 23,
  "cette_phrase_est_fausse": true
}
```

#### Either

`Codec.either` fusionne deux codecs `Codec<A>` et `Codec<B>` en un `Codec<Either<A,B>>`. Pendant la désérialisation, le codec résultat essaiera d'utiliser le premier codec, et _seulement si cela échoue_, essaiera d'utiliser le second.
Si le second échoue à son tour, l'erreur du _second_ codec sera renvoyée.

#### Maps

Pour gérer des `Map`s avec des clés arbitraires commes des `HashMap`s, `Codec.unboundedMap` peut être utilisé. Celle-ci renvoie un `Codec<Map<K, V>>`, étant donnés un `Codec<K>` et un `Codec<V>`. Le codec résultant sérialisera en un objet JSON ou équivalent relativement aux `DynamicOps` utilisées.

À cause de limitations du JSON et du NBT, le codec associé à la clé _doit_ sérialiser en texte. Cela comprend des codecs pour des types qui ne sont pas des textes, mais qui sérialisent ainsi, comme `Identifier.CODEC`. Voir l'exemple suivant :

```java
// Création d'un codec pour une Map d'identifiants à entiers
Codec<Map<Identifier, Integer>> mapCodec = Codec.unboundedMap(Identifier.CODEC, Codec.INT);

// Utilisation pour sérialiser des données
DataResult<JsonElement> result = mapCodec.encodeStart(JsonOps.INSTANCE, Map.of(
    new Identifier("example", "nombre"), 23,
    new Identifier("example", "le_nombre_plus_cool"), 42
));
```

Cela donnera ce JSON en sortie :

```json
{
  "example:nombre": 23,
  "example:le_nombre_plus_cool": 42
}
```

Remarquons que ça marche parce que `Identifier.CODEC` sérialise directement en un texte. On peut arriver au même résultat pour des objets qui ne se sérialisent pas en texte grâce à [xmap et compagnie](#mutually-convertible-types-and-you) pour faire la conversion.

### Les joies des types interconvertibles

#### xmap

Supposons qu'on ait deux classes qui peuvent être converties entre elles, mais sans relation parent-enfant. Par exemple, une `BlockPos` et un `Vec3d` vanilla. Si on a un codec pour l'un, `Codec#xmap` permet de créer un codec pour l'autre en donnant une fonction de conversion pour chaque direction.

`BlockPos` possède déjà un codec, mais imaginons que non. On peut en créer un à partir du codec de `Vec3d` comme ceci :

```java
Codec<BlockPos> blockPosCodec = Vec3d.CODEC.xmap(
    // Conversion de Vec3d en BlockPos
    vec -> new BlockPos(vec.x, vec.y, vec.z),
    // Conversion de BlockPos en Vec3d
    pos -> new Vec3d(pos.getX(), pos.getY(), pos.getZ())
);

// Si vous convertissez une classe pré-existante (`X` par exemple)
// en une classe à vous (`Y`) ainsi, il peut être pratique
// d'ajouter des méthodes `toX` and `fromX` (statique) à `Y`
// et d'utiliser des références de méthodes dans l'appel à `xmap`.
```

#### flatComapMap, comapFlatMap, and flatXMap

`Codec#flatComapMap`, `Codec#comapFlatMap` et `flatXMap` ressemblent à `xmap`, mais permettent à l'une ou aux deux fonctions de conversions de renvoyer un `DataResult`. C'est utile en pratique car il n'est pas forcément toujours possible de convertir une instance donnée d'un objet.

Prenons par exemple les `Identifier`s vanilla. Utiliser `xmap` dans ce cas nécessiterait des exceptions inélégantes si la conversion échouait.
Par conséquent, son codec intégré est en réalité un `comapFlatMap` sur `Codec.STRING`, ce qui illustre bien son utilisation :

```java
public class Identifier {
    public static final Codec<Identifier> CODEC = Codec.STRING.comapFlatMap(
        Identifier::validate, Identifier::toString
    );

    // ...

    public static DataResult<Identifier> validate(String id) {
        try {
            return DataResult.success(new Identifier(id));
        } catch (InvalidIdentifierException e) {
            return DataResult.error("Not a valid resource location: " + id + " " + e.getMessage());
        }
    }

    // ...
}
```

Ces méthodes sont très utiles, mais leurs noms sont assez cryptiques, voici donc un tableau pour aider à se souvenir laquelle utiliser :

| Méthode                 | A -> B toujours valide ? | B -> A toujours valide ? |
| ----------------------- | ------------------------ | ------------------------ |
| `Codec<A>#xmap`         | Oui                      | Oui                      |
| `Codec<A>#comapFlatMap` | Non                      | Oui                      |
| `Codec<A>#flatComapMap` | Oui                      | Non                      |
| `Codec<A>#flatXMap`     | Non                      | Non                      |

### Répartition par registre

Si on définit un registre de codecs, `Codec#dispatch` permet d'utiliser l'un des codecs selon la valeur d'un champ dans les données sérialisées. C'est utile lorsque les objets à désérialiser ont une structure différente selon leur type, mais représentent une même chose.

Par exemple, imaginons une interface abstraite `Bean` avec deux classes qui l'implémentent : `StringyBean` et `CountingBean`. Pour les sérialiser via une répartition par registre, plusieurs choses sont nécessaires :

- Des codecs distincts pour chaque type de `Bean`.
- Une classe/record `BeanType<T extends Bean>` qui représente le type de blob, et peut fournir le codec associé.
- Une méthode de `Bean` qui renvoie son `BeanType<?>`.
- Une map ou un registre pour associer des `Identifier`s à des `BeanType<?>`s.
- Un `Codec<TypeBlob<?>>` à partir de ce registre. En utilisant un `net.minecraft.registry.Registry`, cela s'obtient facilement avec `Registry#getCodec`.

Une fois tout ceci fait, on peut créer un codec de répartition par registre pour les beans :

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/Bean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanType.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/StringyBean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/CountingBean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanTypes.java)

```java
// On peut créer un codec pour les types de bean
// grâce au registre précédemment créé
Codec<BeanType<?>> beanTypeCodec = BeanType.REGISTRY.getCodec();

// Et à partir de ça, un codec de répartition par registre pour beans !
// Le premier argument est le nom du champ correspondant au type.
// Si omis, il sera égal à "type".
Codec<Bean> beanCodec = beanTypeCodec.dispatch("type", Bean::getType, BeanType::getCodec);
```

Notre nouveau codec sérialisera les beans en JSON ainsi, en n'utilisant que les champs en rapport avec leur type spécifique :

```json
{
  "type": "example:stringy_bean",
  "stringy_string": "Ce bean est textuel !"
}
```

```json
{
  "type": "example:counting_bean",
  "counting_number": 42
}
```

### Codecs récursifs

Il est parfois utile d'avoir un codec qui s'utilise _soi-même_ pour décoder certains champs, par exemple avec certaines structures de données récursives. Le code vanilla en fait usage pour les objets `Text`, qui peuvent stocker d'autres `Text`s en tant qu'enfants. Un tel codec peut être construit grâce à `Codecs#createRecursive`.

À titre d'exemple, essayons de sérialiser une liste simplement chaînée. Cette manière de représenter une liste consiste en des nœuds qui contiennent et une valeur, et une référence au prochain nœud de la liste. La liste est alors représentée par son premier nœud, et pour la parcourir, il suffit de continuer à regarder le nœud suivant juste qu'à ce qu'il n'en existe plus. Voici une implémentation simple de nœuds qui stockent des entiers.

```java
public record ListNode(int value, ListNode next) {}
```

Il est impossible de construire un codec comme d'habitude, puisque quel codec utiliserait-on pour le champ `next` ? Il faudrait un `Codec<ListNode>`, ce qui est précisément ce qu'on veut obtenir ! `Codecs#createRecursive` permet de le faire au moyen d'un lambda magique en apparence :

```java
Codec<ListNode> codec = Codecs.createRecursive(
  "ListNode", // un nom pour le codec
  selfCodec -> {
    // Ici, `selfCodec` représente le `Codec<ListNode>`, comme s'il était déjà construit
    // Ce lambda doit renvoyer le codec qu'on aurait voulu utiliser depuis le départ,
    // qui se réfère à lui-même via `selfCodec`
    return RecordCodecBuilder.create(instance ->
      instance.group(
        Codec.INT.fieldOf("value").forGetter(ListNode::value),
         // le champ `next` sera récursivement traité grâce à l'auto-codec
        Codecs.createStrictOptionalFieldCodec(selfCodec, "next", null).forGetter(ListNode::next)
      ).apply(instance, ListNode::new)
    );
  }
);
```

Un `ListNode` sérialisé pourrait alors ressembler à ceci :

```json
{
  "value": 2,
  "next": {
    "value": 3,
    "next" : {
      "value": 5
    }
  }
}
```

## Références

- Il y a une documentation bien plus exhaustive des codecs et des APIs attenantes dans la [JavaDoc DFU non-officielle](https://kvverti.github.io/Documented-DataFixerUpper/snapshot/com/mojang/serialization/Codec).
- La structure globale de ce guide s'inspire beaucoup de [la page du Forge Community Wiki sur les codecs](https://forge.gemwire.uk/wiki/Codecs), qui propose une approche du même sujet plus centrée autour de Forge.
