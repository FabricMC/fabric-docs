---
title: Byty Bloków
description: Dowiedz się, jak tworzyć byty bloków dla swoich własnych bloków.
authors:
  - natri0, Maksiurino
---

Byty bloków stanowią sposób na przechowywanie dodatkowych danych dla bloku, które nie są częścią stanu bloku: zawartości ekwipunku, własnej nazwy itd.
W grze Minecraft stosuje się byty bloków do tworzenia bloków, takich jak skrzynie, piece i bloki poleceń.

Jako przykład utworzymy blok, który zlicza, ile razy został kliknięty prawym przyciskiem myszy.

## Tworzenie Bytów Bloków {#creating-the-block-entity}

Aby Minecraft rozpoznał i załadował nowe byty bloków, musimy utworzyć typ bytu bloku. Można to zrobić poprzez rozszerzenie klasy `BlockEntity` i zarejestrowanie jej w nowej klasie `ModBlockEntities`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Zarejestrowanie `BlockEntity` daje w wyniku `BlockEntityType` podobny do `COUNTER_BLOCK_ENTITY`, którego użyliśmy powyżej:

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/entity/ModBlockEntities.java)

:::tip
Zwróć uwagę, że konstruktor `CounterBlockEntity` przyjmuje dwa parametry, natomiast konstruktor `BlockEntity` przyjmuje trzy: `BlockEntityType`, `BlockPos` i `BlockState`.
Gdybyśmy nie zakodowali na stałe `BlockEntityType`, klasa `ModBlockEntities` nie skompilowałaby się! Dzieje się tak, ponieważ `BlockEntityFactory`, jest interfejsem funkcyjnym, opisuje funkcję, która przyjmuje tylko dwa parametry, podobnie jak nasz konstruktor.
:::

## Tworzenie bloku {#creating-the-block}

Następnie, aby faktycznie użyć bytu bloku, potrzebujemy bloku implementującego `BlockEntityProvider`. Utwórzmy jeden i nazwijmy go `CounterBlock`.

:::tip
Można do tego podejść na dwa sposoby:

- utworzyć blok rozszerzający `BlockWithEntity` i zaimplementować metodę `createBlockEntity`
- utworzyć blok, który implementuje `BlockEntityProvider` samodzielnie i nadpisać metodę `createBlockEntity`

W tym przykładzie wykorzystamy pierwsze podejście, ponieważ `BlockWithEntity` również udostępnia przydatne narzędzia.
:::

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Użycie `BlockWithEntity` jako klasy nadrzędnej oznacza, że musimy również zaimplementować metodę `createCodec`, co jest dość proste.

W przeciwieństwie do bloków, które są singletonami, dla każdego bytu bloku tworzony jest nowy element bloku. Do tego celu służy metoda `createBlockEntity`, która przyjmuje pozycję i `BlockState`, i zwraca `BlockEntity` lub `null`, jeśli nie powinno go być.

Nie zapomnij zarejestrować bloku w klasie `ModBlocks` tak jak w poradniku [Tworzenie pierwszego bloku](../blocks/first-block):

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

## Korzystanie z bytów bloków {#using-the-block-entity}

Teraz gdy mamy blok, możemy go użyć do zapisania liczby kliknięć prawym przyciskiem myszy na bloku. Zrobimy to, dodając pole `clicks` do klasy `CounterBlockEntity`:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Metoda `setChanged` używana w `incrementClicks` informuje grę, że dane tej jednostki zostały zaktualizowane. Będzie to przydatne, gdy dodamy metody serializujące licznik i wczytujące go z pliku zapisu.

Następnie musimy zwiększać wartość tego pola za każdym razem, gdy klikniemy prawym przyciskiem myszy na bloku. Można to zrobić poprzez nadpisanie metody `useWithoutItem` w klasie `CounterBlock`:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

Ponieważ `BlockEntity` nie jest przekazywany do metody, używamy `world.getBlockEntity(pos)` i jeśli `BlockEntity` jest nieprawidłowy, zwracamy z metody.

![Komunikat "You've clicked the block for the 6th time" na ekranie po kliknięciu prawym przyciskiem myszy](/assets/develop/blocks/block_entities_1.png)

## Zapisywanie i ładowanie danych {#saving-loading}

Teraz gdy mamy już funkcjonalny blok, powinniśmy sprawić, aby licznik nie resetował się pomiędzy restartami gry. Można to zrobić poprzez serializację do NBT podczas zapisywania gry i deserializację podczas jej ładowania.

Zapisywanie do NBT odbywa się za pomocą poleceń `ReadView` i `WriteView`. Te viewy odpowiadają za przechowywanie błędów kodowania/dekodowania i śledzenie rejestrów w całym procesie serializacji.

Możesz odczytać dane z `ReadView` za pomocą metody `read`, przekazując `Codec` dla żądanego typu. Podobnie, możesz zapisywać do `WriteView` używając metody `store`, przekazując Codec dla typu i wartości.

Istnieją również metody dla prymitywów, takie jak `getInt`, `getShort`, `getBoolean` itp. do odczytu oraz `putInt`, `putShort`, `putBoolean` itp. do zapisu. View udostępnia również metody umożliwiające pracę z listami, typami dopuszczającymi wartości null i obiektami zagnieżdżonymi.

Serializacja odbywa się za pomocą metody `saveAdditional`:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Tutaj dodajemy pola, które powinny zostać zapisane w przekazanym `WriteView`: w przypadku counter block jest to pole `clicks`.

Odczyt przebiega podobnie: otrzymujesz wartości zapisane wcześniej w `ReadView` i zapisujesz je w polach BlockEntity:

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Teraz, jeśli zapiszemy i ponownie wczytamy grę, blok licznika powinien być kontynuowany od miejsca, w którym został przerwany podczas zapisywania.

Chociaż `writeAdditional` i `loadAdditional` obsługują zapisywanie i ładowanie na dysk oraz z dysku, nadal istnieje problem:

- Serwer zna poprawną wartość `clicks`.
- Klient nie otrzymuje poprawnej wartości, gdy ładowany jest chunk.

Aby to naprawić, nadpisujemy `getUpdateTag`:

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Teraz gdy grach zaloguje się lub zbliży się do chunku, gdzie istnieje blok, od razu zobaczy poprawną wartość licznika.

## Tickery {#tickers}

Interfejs `BlockEntityProvider` definiuje również metodę o nazwie `getTicker`, która może być używana do uruchamiania kodu przy każdym cyklu dla każdej instancji bloku. Możemy to zaimplementować, tworząc metodę statyczną, która będzie używana jako `BlockEntityTicker`:

Metoda `getTicker` powinna również sprawdzać, czy przekazany typ `BlockEntityType` jest taki sam, jak ten, którego używamy, i jeśli tak, zwracać funkcję, która będzie wywołana przy każdym ticku. Na szczęście istnieje funkcja narzędziowa, która wykonuje sprawdzenie w `BlockWithEntity`:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java)

`CounterBlockEntity::tick` to odwołanie do metody statycznej `tick`, którą powinniśmy utworzyć w klasie `CounterBlockEntity`. Takie strukturyzowanie nie jest konieczne, ale dobrym zwyczajem jest zachowanie czystości i organizacji kodu.

Załóżmy, że chcemy, aby licznik można było zwiększać tylko raz na 10 ticków (2 razy na sekundę). Możemy to zrobić, dodając pole `ticksSinceLast` do klasy `CounterBlockEntity` i zwiększając je co każdy tick:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

Nie zapomnij o serializacji i deserializacji tego pola!

Teraz możemy użyć `ticksSinceLast`, aby sprawdzić, czy licznik można zwiększyć w `incrementClicks`:

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java)

:::tip
Jeśli byt bloku nie jest zaznaczony, spróbuj sprawdzić kod rejestracyjny! Powinien przekazać bloki, które są prawidłowe dla tego bytu, do `BlockEntityType.Builder`, w przeciwnym razie w konsoli pojawi się ostrzeżenie:

```log
[13:27:55] [Server thread/WARN] (Minecraft) Block entity example-mod:counter @ BlockPos{x=-29, y=125, z=18} state Block{example-mod:counter_block} invalid for ticking:
```

:::
