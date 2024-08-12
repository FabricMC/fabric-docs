---
title: Codecs
description: Una guía completa para entender y usar el sistema de codecs de Mojang para la serialización y deserialización de objetos.
authors:
  - enjarai
  - Syst3ms

search: false
---

# Codecs

Los Codecs es un sistema para la fácil serialización de objetos de Java, el cual viene incluido en la librería de DataFixerUpper (DFU) de Mojang, el cual es incluido en Minecraft. En el contexto de desarrollo de mods, pueden ser usados como una alternativa a GSON y Jankson a la hora de leer y escribir archivos json, aunque se están haciendo cada vez más y más relevantes, a medida que Mojang reescribe bastante código viejo para que use Codecs.

Los codecs son usandos en conjunto con otro API de DFU, llamado `DynamicOps` (Operaciones Dinámicas). Un codec define la estructura de un objeto, mientras que un "dynamic ops" es usado para definir un formato con el cual (de) serializar, como json o NBT. Esto quiere decir que un codec puede user usado con cualquier "dynamic ops" y vice versa, permitiendo mayor flexibilidad.

## Usando Codecs

### Serializando y Deserializando

La manera básica de usar un codec es para serializar y deserializar objetos desde y a un formato en específico.

Ya que algunas clases vanila ya tienen codecs definidos, podemos usarlos como ejemplo. Mojang también nos ha dado dos clases de "dynamic ops" por defecto, llamadas `JsonOps` y `NbtOps`, las cuales tienden a ser usadas en la mayoría de los casos.

Digamos que queremos serializar un `BlockPos` a json y de vuelta. Podemos hacer esto mediante el codec guardado estáticamente en `BlockPos.CODEC` con los métodos `Codec#encodeStart` y `Codec#parse`, respectivamente.

```java
BlockPos pos = new BlockPos(1, 2, 3);

// Serializamos el BlockPos a un JsonElement
DataResult<JsonElement> result = BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos);
```

Cuando usamos codecs, los valores retornados vienen en la forma de un `DataResult` ("Resultado de Dato"). Esto es una envoltura que puede representar o un éxito o un fracaso. Podemos usarlo de varias maneras: Si solo queremos nuestro valor serializado, entonces `DataResult#result` simplemente nos dará un `Optional` que contiene nuestro valor, mientras que `DataResult#resultOrPartial` también nos deja suplír una función para manejar cualquier error que pudo haber ocurrido. Esto último es particularmente útil para recursos de datapacks ("paquetes de datos"), donde querríamos escribir o "loggear" errores sin causar problemas en otros lugares.

Ahora podemos obtener nuestro valor serializado y convertirlo de vuelta a un `BlockPos`:

```java
// Cuando desarrolles un mod, querrás obviamente manejar Opcionales vaciós apropiadamente
JsonElement json = result.resultOrPartial(LOGGER::error).orElseThrow();

// Aquí tenemos nuestro valor json, que debería corresponder a `[1, 2, 3]`,
// ya que es el formato usado por el codec de BlockPos.
LOGGER.info("Serialized BlockPos: {}", json);

// Ahora deserializaremos nuestro el JsonElement de vuelta a un BlockPos
DataResult<BlockPos> result = BlockPos.CODEC.parse(JsonOps.INSTANCE, json);

// Una vez más solo agarraremos nuestro valor del resultado
BlockPos pos = result.resultOrPartial(LOGGER::error).orElseThrow();

// ¡Y ahora podemos ver que hemos serializado y deserializado nuestro BlockPos exitósamente!
LOGGER.info("Deserialized BlockPos: {}", pos);
```

### Codecs Incluidos

Como hemos mencionado anterioramente, Mojang ya tiene definido varios codecs para varias clases vanila y clases de Java estándares, como por ejemplo, `BlockPos`, `BlockState`, `ItemStack`, `Identifier`, `Text`, y regex `Pattern`s (patrones regex). Los Codecs para las clases propias de Mojang usualmente se pueden encontrar como miembros estáticos con el nombre de `CODEC` en la clase misma, mientras que la mayoría de los demás se encuentran en la clase `Codecs`. Es importante saber que todos los registros vanila tienen un método `getCodec()`, por ejemplo, puedes usar `Registries.BLOCK.getCodec()` para tener un `Codec<Block>` el cual (de)serializa IDs de bloques.

La API de Codec también tiene codecs para tipos de variable primitivos, como `Codec.INT` y `Codec.STRING`. Estos se pueden encontrar como miembros estáticos en la clase `Codec`, y usualmente son la base de Codecs más complejos, como explicado a continuación.

## Construyendo Codecs

Ahora que hemos visto como usar codecs, veamos como podemos hacer nuestros propios codecs. Supongamos que tenemos la siguiente clase, y queremos deserializar instancias de ella desde archivos json:

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

El archivo json correspondiente puede verse algo así:

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

Podemos hacer un codec para esta clase integrando varios codecs más pequeños en uno más grande. En este caso, necesitaremos uno para cada miembro:

- un `Codec<Integer>`
- un `Codec<Item>`
- un `Codec<List<BlockPos>>`

Podemos obtener el primero usando los codecs primitivos ya mencionados, mediante la clase `Codec`, específicamente `Codec.INT`. Mientras que el segundo se puede obtener mediante el registro `Registries.ITEM`, el cuando tiene un método `getCodec()`, el cual retorna un `Codec<Item>`. No tenemos un codec por defecto para `List<BlockPos>`, pero podemos crear uno a partir de `BlockPos.CODEC`.

### Listas

`Codec#listOf` puede ser usado para crear una versión lista de cualquier codec:

```java
Codec<List<BlockPos>> listCodec = BlockPos.CODEC.listOf();
```

Debe ser recalcado que los codecs creados de esta manera siempre se deserializarán a un `ImmutableList` (lista inmutable o no modificable). Si en cambio necesitas una lista mutable, puedes usar [xmap](#mutually-convertible-types-and-you) para convertir a una durante la deserialización.

### Combinar Codecs para Clases como Records

Ahora que tenemos codecs separados para cada miembro, podemos combinarlos en un solo codec usando un `RecordCodecBuilder`. Esto asume que nuestra clase tiene un constructor que contiene cada miembro que queremos serializar, y que cada miembro tiene su propio método adquiridor (getter method). Esto lo hace perfecto para usar en conjunto con records, pero también pueden ser usados con clases normales.

Veamos como podemos crear un codec para nuestra clase `CoolBeansClass`:

```java
public static final Codec<CoolBeansClass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.fieldOf("beans_amount").forGetter(CoolBeansClass::getBeansAmount),
    Registries.ITEM.getCodec().fieldOf("bean_type").forGetter(CoolBeansClass::getBeanType),
    BlockPos.CODEC.listOf().fieldOf("bean_positions").forGetter(CoolBeansClass::getBeanPositions)
    // El máximo de miembros que se pueden declarar aquí es 16
).apply(instance, CoolBeansClass::new));
```

Cada línea en el grupo especifica un codec, un nombre para el miembro, y el método adquiridor. El llamado a `Codec#fieldOf` es usado para convertir el codec a un [map codec](#mapcodec), y el llamado a `forGetter` especifica el método adquiridor que se usará para obtener el valor del miembro a partir de una instancia de la clase. Mientras tanto, el llamado a `apply` especifica que el constructor usado para crear nuevas instancias. Es importante saber que el orden de los miembros en el grupo debe ser el mismo que el orden de los argumentos del constructor.

También puedes usar `Codec#optionalFieldOf` en este contexto para hacer un miembro opcional, como explicado en la sección de [Miembros Opcionales](#optional-fields).

### MapCodec, sin ser confundido con Codec&lt;Map&gt; {#mapcodec}

Llamar `Codec#fieldOf` convertirá un `Codec<T>` a un `MapCodec<T>`, el cual es una implementación variante, pero no directa de `Codec<T>`. Como su nombre lo indica, los `MapCodec`s garantizan la serialización a una asociación (map) llave a valor, o su equivalente en el `DynamicOps` usado. Algunas funciones pueden requerir una, en vez de un codec regular.

Esta manea particular de crear un `MapCodec` esencialmente empaqueta los valores del codec fuente dentro de una asociación (map), usando el nombre del miembro proveído como la llave. Por ejemplo, un `Codec<BlockPos>`, cuando es serializado a json, puede verse así:

```json
[1, 2, 3]
```

Pero cuando es convertido a un `MapCodec<BlockPos>` usando `BlockPos.CODEC.fieldOf("pos")`, se verá así:

```json
{
  "pos": [1, 2, 3]
}
```

Aunque el uso más común para los map codecs es para que se combinen con otros map codecs para construir un codec para una clase completa con varios miemros, como fue explicado en la sección [Combinar Codecs para Clases como Records](#merging-codecs-for-record-like-classes), también pueden ser convertidos de vuelta a codecs regulares usando `MapCodec#codec`, el cual mantendrá el mismo comportamiento para empaquetar sus valores de entrada.

#### Codecs Opcionales

`Codec#optionalFieldOf` puede ser usado para crear un map codec opcional. Esto hará que, cuando el miembro especificado no esté presente en el contenedor durante la deserialización, se deserialize a un `Optional` vacío, o a un valor por defecto especificado.

```java
// Sin valor por defecto
MapCodec<Optional<BlockPos>> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos");

// Con valor por defecto
MapCodec<BlockPos> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos", BlockPos.ORIGIN);
```

Ten en cuenta que los miembros opcionales ignorarán silenciosamente cualquier error que ocurra durante la deserialización. Esto significa que si el miembro está presente, pero el valor es inválido, el miembro siempre se deserializará al valor por defecto.

**Desde la 1.20.2**, debido a Minecraft (no DFU!) en cambio si provee `Codecs#createStrictOptionalFieldCodec`, el cual falla en la deserialización si el valor del miembro es inválido.

### Constantes, Restricciones, y Composición

#### Unidad

`Codec.unit` puede usarse para crear un codec el cual siempre deserializará a un valor constante, independiente del valor dado. Cuando se serialice, no hará nada.

```java
Codec<Integer> theMeaningOfCodec = Codec.unit(42);
```

#### Rangos Numéricos

`Codec.intRange` y sus amigos, `Codec.floatRange` y `Codec.doubleRange` pueden ser usados para crear un codec que solo acepta valores numéricos dentro de un rango **inclusivo**. Esto se aplica tanto en la serialización y deserialización.

```java
// No puede ser mayor a 2
Codec<Integer> amountOfFriendsYouHave = Codec.intRange(0, 2);
```

#### Pares

`Codec.pair` combina dos codecs, `Codec<A>` y `Codec<B>`, a un `Codec<Pair<A, B>>`. Ten en cuenta que solo funciona bien con codecs que serializan a un miembro específico, como un [`MapCodec`s convertidos](#mapcodec) o
[codecs de record](#merging-codecs-for-record-like-classes).
El codec resultante será serializado a una asociación (map) que combina los miembros de ambos codecs usados.

Por ejemplo, al correr este código:

```java
// Crea dos codecs empaquetados
Codec<Integer> firstCodec = Codec.INT.fieldOf("i_am_number").codec();
Codec<Boolean> secondCodec = Codec.BOOL.fieldOf("this_statement_is_false").codec();

// Combinalos a un codec par
Codec<Pair<Integer, Boolean>> pairCodec = Codec.pair(firstCodec, secondCodec);

// Usalo para serializar datos
DataResult<JsonElement> result = pairCodec.encodeStart(JsonOps.INSTANCE, Pair.of(23, true));
```

Producirá este json:

```json
{
  "i_am_number": 23,
  "this_statement_is_false": true
}
```

#### Cualquiera (Either)

`Codec.either` combina dos codecs, `Codec<A>` y `Codec<B>`, a un `Codec<Either<A, B>>`. El codec resultante intentará, durante la deserialización, usar el primer codec, y _solo si eso falla_, entonces intentará usar el segundo.
Si el segundo también falla, el error del _segundo_ codec será retornado.

#### Asociaciones (Maps)

Para procesar asociaciones con llaves arbitrarias, como `HashMap`s, se puede usar `Codec.unboundedMap`. Esto nos dá un `Codec<Map<K, V>>`, para un `Codec<K>` y un `Codec<V>` dado. El codec resultante será serializado a un objeto json o el equivalente disponible en el dynamic ops actual.

Debido a las limitaciones de json y nbt, los codecs para las llaves _deben_ ser serializados a una cadena de caracteres (string). Esto incluye codecs para tipos que no son cadenas de caracteres por su cuenta, pero si se serializan a ellas, como `Identifier.CODEC`. Vea el ejemplo a continuación:

```java
// Crea un codec para un mapa de identificadores a numeros enteros
Codec<Map<Identifier, Integer>> mapCodec = Codec.unboundedMap(Identifier.CODEC, Codec.INT);

// Usalo para serializar datos
DataResult<JsonElement> result = mapCodec.encodeStart(JsonOps.INSTANCE, Map.of(
    new Identifier("example", "number"), 23,
    new Identifier("example", "the_cooler_number"), 42
));
```

Esto producirá este json:

```json
{
  "example:number": 23,
  "example:the_cooler_number": 42
}
```

Como puedes ver, esto funciona porque `Identifier.CODEC` se serializa directamente a una cadena de caracteres. Un efecto similar puede ser logrado para objetos simples que no se serializan a cadenas de caracteres usando [xmap y sus amigos](#mutually-convertible-types-and-you) para convertirlos.

### Tipos Mutuamente Convertibles y Tú

#### xmap

Digamos que tenemos dos clases que pueden ser convertidas una a la otra, pero no tienen una relación pariente-hijo. Por ejemplo un `BlockPos`y `Vec3d` vanila. Si tenemos un codec para uno, podemos usar `Codec#xmap` para crear un codec para el otro especificando una función convertidora en ambas direcciones.

`BlockPos` ya tiene un codec, pero pretendamos que no lo tiene. Podemos crear uno para él basándolo en el codec de `Vec3d` así:

```java
Codec<BlockPos> blockPosCodec = Vec3d.CODEC.xmap(
    // Convierte Vec3d a Blockpos
    vec -> new BlockPos(vec.x, vec.y, vec.z),
    // Convierte BlockPos a Vec3d
    pos -> new Vec3d(pos.getX(), pos.getY(), pos.getZ())
);

// Cuando convertimos una clase ya existente (`X` por ejemplo)
// a tu propia clase (`Y`) de esta manera, puede ser útil tener
// métodos `toX` y un método estático `fromX` en la clase `Y` y usar
// referencias a métodos en tu llamado a `xmap`.
```

#### flatComapMap, comapFlatMap, and flatXMap

`Codec#flatComapMap`, `Codec#comapFlatMap` y `flatXMap` son similares a xmap, pero permiten que una o ambass de las funciones convertidoras retornen un DataResult. En práctica esto es útil porque una instancia de un objeto puede no ser válida para conversión.

Toma por ejemplo `Identifier`s vanila. Aunque todos los identificadores pueden ser convertidos a cadenas de caracteres, no todas las cadenas de caracteres son identificadores válidos, así que usar xmap significaría tirar excepciones si la conversión falla.
Debido a esto, su codec incluído es en realidad un `comapFlatMap` en `Codec.STRING`, ilustrando como se pueden usar:

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

Aunque estos métodos son muy útiles, sus nombres son un poco confusos, así que aquí hay una tabla para que puedas recordar mejor cuál usar:

| Método                  | ¿A -> B siempre válido? | ¿B -> A siempre válido? |
| ----------------------- | ----------------------- | ----------------------- |
| `Codec<A>#xmap`         | Sí                      | Sí                      |
| `Codec<A>#comapFlatMap` | No                      | Sí                      |
| `Codec<A>#flatComapMap` | Sí                      | No                      |
| `Codec<A>#flatXMap`     | No                      | No                      |

### Despachador de Registros

`Codec#dispatch` nos permite definir un registro de codecs y despachar a uno en específico basado en el valor de un miembro en los datos serializados. Esto es muy útil cuando deserializamos objetos que tienen diferentes miembros dependiendo de su tipo, pero que igual representan la misma cosa.

Por ejemplo, digamos que tenemos una interfaz abstracta `Bean`, con dos clases implementadoras: `StringyBean` y `CountingBean`. Para serializarlas con un despachador de registros, necesitaremos algunas cosas:

- Codecs separados para cada tipo de bean.
- Una clase `BeanType<T extends Bean>` o un record que represente el tipo de bean, y que pueda retornar un codec para él.
- Una función en `Bean` para obtener su `BeanType<?>`.
- Una asociación o registro para asociar `Identifier`s a `BeanType<?>`s.
- Un `Codec<BeanType<?>>` basado en este registro. Si usas un `net.minecraft.registry.Registry`, puedes hacer una fácilmente con `Registry#getCodec`.

Con todo esto, puedes crear un despacho de registros para beans:

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/Bean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanType.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/StringyBean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/CountingBean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanTypes.java)

```java
// Ahora podemos crear un codec para los tipos de bean
// basado en el registro previamente creado
Codec<BeanType<?>> beanTypeCodec = BeanType.REGISTRY.getCodec();

// ¡Y basado en esto, aquí esta nuestro codec de despacho de registros para beans!
// El primer argumento es el nombre del miembro para el tipo de bean.
// Cuando no se especifica, se usa el valor por defecto "type".
Codec<Bean> beanCodec = beanTypeCodec.dispatch("type", Bean::getType, BeanType::getCodec);
```

Nuestro nuevo codec serializará beans a json de esta manera, usando solo los campos relevantes a su tipo especificado:

```json
{
  "type": "example:stringy_bean",
  "stringy_string": "This bean is stringy!"
}
```

```json
{
  "type": "example:counting_bean",
  "counting_number": 42
}
```

### Codecs Recursivos

A veces es útil tener un codec que se use a _sí mismo_ para decodificar ciertos miembros, por ejemplo cuando estamos lidiando con ciertas estructuras de datos recursivas. En el código vanilla, esto es usado para objetos `Text` (Texto), los cuales tienen otros objetos `Text` como hijos. Tal codec puede ser construido usando `Codecs#createRecursive`.

Por ejemplo, tratemos de serializar una lista enlazada. Esta manera de representar listas consiste en varios nodos que contienen un valor y una referencia al siguiente nodo en la lista. La lista es entonces representada mediante el primer nodo, y para caminar por la lista se sigue el siguiente nodo hasta que no quede ninguno. Aquí está una implementación simple de los nodos que guardan números enteros.

```java
public record ListNode(int value, ListNode next) {}
```

No podemos construir un codec para esto mediante métodos ordinarios, porque ¿qué codec usaríamos para el miembro de `next`? ¡Tendríamos que usar un `Codec<ListNode>`, que es lo que estamos construyendo actualmente! `Codecs#createRecursive` nos permite lograr eso con una expresión lambda mágica:

```java
Codec<ListNode> codec = Codecs.createRecursive(
  "ListNode", // a name for the codec
  selfCodec -> {
    // Here, `selfCodec` represents the `Codec<ListNode>`, as if it was already constructed
    // This lambda should return the codec we wanted to use from the start,
    // that refers to itself through `selfCodec`
    return RecordCodecBuilder.create(instance ->
      instance.group(
        Codec.INT.fieldOf("value").forGetter(ListNode::value),
         // the `next` field will be handled recursively with the self-codec
        Codecs.createStrictOptionalFieldCodec(selfCodec, "next", null).forGetter(ListNode::next)
      ).apply(instance, ListNode::new)
    );
  }
);
```

Un `ListNode` serializado se podría ver algo así:

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

## Fuentes y Referencias

- Puedes encontrar una documentación más completa sobre codecs y los APIs relacionados en los [Javadocs de DFU no oficiales](https://kvverti.github.io/Documented-DataFixerUpper/snapshot/com/mojang/serialization/Codec).
- La estructura general de esta guía fue inspirada en gran medida por la [página Wiki de Forge sobre codecs](https://forge.gemwire.uk/wiki/Codecs), una versión de esta página más enfocada para Forge.
