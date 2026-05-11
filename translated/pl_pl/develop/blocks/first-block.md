---
title: Tworzenie pierwszego bloku
description: Naucz się, jak stworzyć swój pierwszy niestandardowy blok w Minecrafcie.
authors:
  - CelDaemon
  - Earthcomputer
  - IMB11
  - its-miroma
  - xEobardThawne
resources:
  https://docs.neoforged.net/docs/blocks/: Bloki - Dokumentacja NeoForge
---

Bloki stanowią podstawę gry Minecraft – tak jak wszystko inne w grze, są przechowywane w rejestrach.

## Przygotowywanie Klasy Naszego Bloku {#preparing-your-blocks-class}

Jeśli przeczytałeś/aś stronę [Tworzenie pierwszego własnego przedmiotu](../items/first-item), proces ten będzie Ci się wydawał bardzo znajomy — będziesz musiał utworzyć metodę, która zarejestruje Twój blok i jego item.

Powinieneś umieścić tę metodę w klasie `ModBlocks` (lub jakkolwiek chcesz ją nazwać).

Mojang robi to bardzo podobnym sposobem dla swoich bloków; jeśli chcesz zobaczyć, jak to zrobili, możesz zajrzeć do klasy `Blocks`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Podobnie jak w przypadku przedmiotów, należy upewnić się, że klasa jest załadowana, tak aby wszystkie statyczne pola zawierające wystąpienia bloku zostały zainicjowane.

Można to zrobić, tworząc fikcyjną metodę `initialize`, którą można wywołać w [inicjalizatorze moda](../getting-started/project-structure#entrypoints) w celu wywołania statycznej inicjalizacji.

::: info

Jeśli nie wiesz, na czym polega inicjalizacja statyczna, jest to proces inicjalizacji pól statycznych w klasie. Odbywa się, to gdy klasa jest ładowana przez maszynę wirtualną Java (JVM), i przed utworzeniem jakichkolwiek wystąpień klasy.

:::

```java
public class ModBlocks {
    // ...

    public static void initialize() {}
}
```

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/ExampleModBlocks.java)

## Tworzenie i rejestrowanie bloku {#creating-and-registering-your-block}

Podobnie jak przedmioty, bloki przyjmują w swoim konstruktorze klasę `BlockBehaviour.Properties`, która określa właściwości bloku, takie jak dźwięki i szybkość niszczenia.

Nie omówimy tutaj wszystkich opcji: możesz obejrzeć klasę i zapoznać się z różnymi opcjami, które powinny być oczywiste.

Na przykład, stworzymy prosty blok, który będzie miał właściwości bloku ziemi, ale będzie wykonany z innego materiału.

- Ustawienia bloku tworzymy w podobny sposób, jak robiliśmy to z ustawieniami przedmiotów w poradniku o przedmiotach.
- Przekazujemy metodzie `register` utworzenie instancji `Block` z ustawień bloku poprzez wywołanie konstruktora `Block`.

::: tip

Można również użyć `BlockBehaviour.Properties.ofFullCopy(BlockBehaviour block)`, aby skopiować ustawienia istniejącego bloku; w tym przypadku moglibyśmy użyć `Blocks.DIRT`, aby skopiować ustawienia bloku ziemi, ale dla przykładu użyjemy konstruktora.

:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Aby automatycznie utworzyć element bloku, możemy przekazać `true` do parametru `shouldRegisterItem` metody `register`, którą utworzyliśmy w poprzednim kroku.

### Dodawanie Przedmiotu Bloku do Karty w Ekwipunku Kreatywnym {#adding-your-block-s-item-to-a-creative-tab}

Ponieważ element `BlockItem` jest tworzony i rejestrowany automatycznie, aby dodać go do karty w ekwipunku kreatywnym, należy użyć metody `Block.asItem()` w celu pobrania instancji `BlockItem`.

W tym przykładzie dodamy blok do karty `BUILDING_BLOCKS`. Aby zamiast tego dodać blok do własnej karty, zapoznaj się z tematem [Niestandardowe karty kreadywne](../items/custom-creative-tabs).

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Powinieneś umieścić to w funkcji `initialize()` swojej klasy.

Teraz powinieneś zauważyć, że twój blok jest już w ekwipunku kreatywnym i możesz go umieścić w świecie gry!

![Blok w świecie bez odpowiedniego modelu lub tekstury](/assets/develop/blocks/first_block_0.png)

Jest jednak kilka problemów: przedmiot bloku nie ma nazwy, a sam blok nie ma tekstury, modelu bloku ani modelu przedmiotu.

## Dodawanie Tłumaczeń do Bloków {#adding-block-translations}

Aby dodać tłumaczenie, musisz stworzyć klucz tłumaczenia w pliku tłumaczenia - `assets/example-mod/lang/en_us.json` (możesz zmienić na `pl_pl.json`).

Minecraft będzie używać tego tłumaczenia w ekwipunku kreatywnym i innych miejscach, w których wyświetlana jest nazwa bloku, np. w informacjach zwrotnych od komend.

```json
{
  "block.example-mod.condensed_dirt": "Condensed Dirt"
}
```

Możesz ponownie uruchomić grę lub skompilować twój mod i nacisnąć <kbd>F3</kbd>+<kbd>T</kbd>, aby zastosować zmiany. Powinieneś zobaczyć, że blok ma nazwę w ekwipunku kreatywnym i innych miejscach.

## Modele i Tekstury {#models-and-textures}

Wszystkie tekstury bloków można znaleźć w folderze `assets/example-mod/textures/block` - przykładowa tekstura "Condensed Dirt" jest bezpłatna.

<DownloadEntry visualURL="/assets/develop/blocks/first_block_1.png" downloadURL="/assets/develop/blocks/first_block_1_small.png">Tekstura</DownloadEntry>

Aby tekstura była widoczna w grze, musisz utworzyć model bloku, który utworzysz jako `assets/example-mod/models/block/condensed_dirt.json` dla bloku "Condensed Dirt". W tym bloku wykorzystamy typ modelu `block/cube_all`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/block/condensed_dirt.json)

Aby blok był widoczny w Twoim ekwipunku, musisz utworzyć [Ustawienia wyglądu przedmiotu](../items/first-item#creating-the-client-item) wskazujący na model Twojego bloku. W tym przykładzie element klienta dla bloku "Condensed Dirt" można znaleźć w pliku `assets/example-mod/items/condensed_dirt.json`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/condensed_dirt.json)

::: tip

Musisz utworzyć ustawienia wyglądu przedmiotu tylko wtedy, gdy zarejestrowałeś `BlockItem` wraz ze swoim blokiem!

:::

Po załadowaniu gry możesz zauważyć, że nadal brakuje tekstury na ustawionym przez ciebie bloku. Dzieje się tak ponieważ trzeba dodać definicję stanu bloku (blockstate).

## Tworzenie definicji stanu bloku {#creating-the-block-state-definition}

Definicja stanu bloku służy do poinstruowania gry, który model ma renderować na podstawie bieżącego stanu bloku.

W przypadku przykładowego bloku, który nie ma złożonego stanu bloku, w definicji potrzebny jest tylko jeden wpis.

Plik ten powinien znajdować się w folderze `assets/example-mod/blockstates`, a jego nazwa powinna odpowiadać identyfikatorowi bloku użytemu podczas rejestrowania bloku w klasie `ModBlocks`. Na przykład, jeśli identyfikator bloku to `condensed_dirt`, plik powinien nazywać się `condensed_dirt.json`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/blockstates/condensed_dirt.json)

::: tip

Stany bloków są niezwykle trudne, dlatego też zostaną omówione na [osobnej stronie](./blockstates).

:::

Ponowne uruchomienie gry lub ponowne wczytanie za pomocą <kbd>F3</kbd>+<kbd>T</kbd> w celu zastosowania zmian — powinieneś móc zobaczyć teksturę bloku w ekwipunku i fizycznie w świecie:

![Blok w świecie z odpowiednią teksturą i modelem](/assets/develop/blocks/first_block_4.png)

## Dodawanie Łupu z Bloku {#adding-block-drops}

Podczas niszczenia bloku w trybie przetrwania możesz zauważyć, że blok nic nie upuszcza. Ta funkcjonalność może okazać się przydatna, jednak aby blok wypadał jako przedmiot po rozbiciu, musisz zaimplementować jego tabelę łupów (ang. _loot table_). Plik tabeli łupów powinien znajdować się w folderze `data/example-mod/loot_table/blocks`.

::: info

Aby lepiej zrozumieć tabele łupów, możesz zapoznać się ze stroną [Minecraft Wiki - Tabele Łupów](https://minecraft.wiki/w/Loot_table).

:::

@[code](@/reference/latest/src/main/resources/data/example-mod/loot_tables/blocks/condensed_dirt.json)

Ta tabela łupów przedstawia pojedynczy przedmiot, który można zdobyć, gdy blok zostanie zniszczony lub gdy zostanie wysadzony w powietrze w wyniku eksplozji.

## Polecanie narzędzia do zbioru {#recommending-a-harvesting-tool}

Możesz również sprawić, że blok będzie można zbierać tylko przy użyciu konkretnego narzędzia, na przykład możesz chcieć, aby blok dało się szybciej zbierać łopatą.

Wszystkie tagi narzędzi powinny zostać umieszczone w folderze `data/minecraft/tags/block/mineable/`, gdzie nazwa pliku zależy od rodzaju użytego narzędzia, jednego z następujących:

- `hoe.json` (motyka)
- `axe.json` (siekiera)
- `pickaxe.json` (kilof)
- `shovel.json` (łopata)

Zawartość pliku jest dość prosta — jest to lista przedmiotów, które należy dodać do tagu.

Ten przykład dodaje blok "Condensed Dirt" do tagu `shovel`.

@[code](@/reference/latest/src/main/resources/data/minecraft/tags/mineable/shovel.json)

Jeśli chcesz, aby do wydobycia bloku wymagane było jakieś narzędzie, musisz dodać `.requiresCorrectToolForDrops()` do ustawień bloku, a także dodać odpowiedni tag poziomu wydobycia.

## Poziomy Wydobywania {#mining-levels}

Podobnie, tag poziomu wydobycia można znaleźć w folderze `data/minecraft/tags/block` i zachowuje on następujący format:

- `needs_stone_tool.json` - Minimalny poziom narzędzi to narzędzia kamiennymi
- `needs_iron_tool.json` - Minimalny poziom narzędzi to narzędzia żelaznymi
- `needs_diamond_tool.json` - Minimalny poziom narzędzi to narzędzia diamentowe.

Plik ma ten sam format, co plik narzędzia do zbioru — listę elementów, które mają zostać dodane do tagu.

## Dodatkowe Informacje {#extra-notes}

Jeśli dodajesz wiele bloków do swojego moda, możesz użyć [Generatora Danych](../data-generation/setup) w celu zautomatyzowania procesu tworzenia modeli bloków i przedmiotów, definicji stanu bloku i tabel łupów.
