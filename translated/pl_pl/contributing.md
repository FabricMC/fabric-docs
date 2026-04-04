---
title: Wytyczne współtworzenia
description: Wytyczne dotyczące współtworzenia i wnoszenia wkładu do dokumentacji Fabric.
---

Ta strona używa [VitePress](https://vitepress.dev/) do generowania statycznego HTML-a z różnych plików Markdown. Powinieneś zapoznać się z [rozszerzeniami języka Markdown obsługiwanymi przez vitepress](https://vitepress.dev/guide/markdown#features).

Istnieją trzy sposoby, w jakie możesz pomóc współtworzyć tę stronę:

- [Tłumaczenie dokumentacji](#translating-documentation)
- [Współtworzenie treści](#contributing-content)
- [Współtworzenie frameworka](#contributing-framework)

Wszystkie współtworzone treści i wniesiony wkład muszą być zgodne z naszymi [wytycznymi dotyczącymi stylu](#style-guidelines).

## Tłumaczenie dokumentacji {#translating-documentation}

Jeśli chcesz przetłumaczyć dokumentację na swój język, możesz to zrobić dołączając do projektu [Fabric na platformie Crowdin](https://crowdin.com/project/fabricmc).

<!-- markdownlint-disable titlecase-rule -->

## <Badge type="tip">new-content</Badge> Współtworzenie treści {#contributing-content}

<!-- markdownlint-enable titlecase-rule -->

Głównym sposobem wniesienia wkładu do dokumentacji Fabric jest publikowanie treści.

Wszystkie treści przechodzą przez następujące etapy, z których każdy jest powiązany z etykietą:

1. <Badge type="tip">locally</Badge> Przygotuj zmiany i prześlij PR
2. <Badge type="tip">stage:expansion</Badge>: Wskazówki dotyczące rozbudowy, jeśli to konieczne
3. <Badge type="tip">stage:verification</Badge>: Weryfikacja treści
4. <Badge type="tip">stage:cleanup</Badge>: Gramatyka, sprawdzanie poprawności...
5. <Badge type="tip">stage:ready</Badge>: Gotowe do połączenia!

Cała treść musi być zgodna z naszymi [wytycznymi dotyczącymi stylu](#style-guidelines).

### 1. Przygotuj zmiany {#1-prepare-your-changes}

Ta strona internetowa jest oparta na otwartym kodzie źródłowym i jest rozwijana w repozytorium GitHub, co oznacza, że opieramy się na przepływie GitHub:

1. [Fork repozytorium github](https://github.com/FabricMC/fabric-docs/fork)
2. Utwórz nową gałąź na swoim forku
3. Wprowadź zmiany w tej gałęzi
4. Otwórz żądanie ściągnięcia do oryginalnego repozytorium

Możesz dowiedzieć się więcej o [Przepływ GitHub](https://docs.github.com/en/get-started/using-github/github-flow).

Zmiany można wprowadzać za pośrednictwem internetowego interfejsu użytkownika w serwisie GitHub lub można opracować witrynę i wyświetlić jej podgląd lokalnie.

#### Klonowanie Twojego forka {#clone-your-fork}

Jeśli chcesz rozwijać się lokalnie, musisz zainstalować [git](https://git-scm.com/).

Następnie sklonuj swój fork repozytorium za pomocą:

```sh
# make sure to replace "your-username" with your actual username
git clone https://github.com/your-username/fabric-docs.git
```

#### Instalowanie zależności {#install-dependencies}

Jeśli chcesz wyświetlić podgląd zmian lokalnie, musisz zainstalować [node.js 18+](https://nodejs.org/en/) i [pnpm](https://pnpm.io/).

Następnie pamiętaj o zainstalowaniu wszystkich zależności za pomocą:

```sh
pnpm install
```

#### Uruchamianie serwera deweloperskiego {#run-the-development-server}

Umożliwi ci to podgląd zmian lokalnie na `localhost:5173` i automatyczne przeładowanie strony po wprowadzeniu zmian.

```sh
pnpm dev
```

Teraz możesz otworzyć i przeglądać stronę internetową z poziomu przeglądarki, odwiedzając `http://localhost:5173`.

#### Budowanie strony internetowej {#building-the-website}

Spowoduje to skompilowanie wszystkich plików markdown do statycznych plików html i umieszczenie ich w `.vitepress/dist`:

```sh
pnpm build
```

#### Podgląd zbudowanej witryny {#previewing-the-built-website}

Spowoduje to uruchomienie lokalnego serwera na porcie `4173`, który będzie obsługiwał zawartość znalezioną w `.vitepress/dist`:

```sh
pnpm preview
```

#### Otwieranie żądania ściągnięcia {#opening-a-pull-request}

Gdy już będziesz zadowolony ze zmian, możesz je „wcisnąć”:

```sh
git add .
git commit -m "Description of your changes"
git push
```

Następnie kliknij link w wynikach polecenia `git push`, aby otworzyć żądanie ściągnięcia.

### 2. <Badge type="tip">stage:expansion</Badge> Wskazówki dotyczące rozbudowy w razie potrzeby {#2-guidance-for-expansion-if-needed}

Jeśli zespół dokumentacyjny uważa, że możesz rozszerzyć swoje żądanie ściągnięcia, członek zespołu doda do niego etykietę <Badge type="tip">stage:expansion</Badge> wraz z komentarzem wyjaśniającym, co ich zdaniem możesz rozszerzyć. Jeśli zgadzasz się z tą sugestią, możesz rozszerzyć swoje żądanie ściągnięcia.

Jeśli nie chcesz rozszerzać swojego pull requestu, ale nie masz nic przeciwko temu, żeby ktoś inny rozszerzył go później, powinieneś utworzyć zgłoszenie na [stronie zgłoszeń](https://github.com/FabricMC/fabric-docs/issues) i wyjaśnić, co Twoim zdaniem można rozszerzyć. Zespół zajmujący się dokumentacją doda następnie do Twojego PR etykietę <Badge type="tip">help-wanted</Badge>.

### 3. <Badge type="tip">stage:verification</Badge> Weryfikacja treści {#3-content-verification}

To najważniejszy etap, gdyż gwarantuje, że treść jest poprawna i zgodna ze stylem dokumentacji Fabric.

Na tym etapie należy odpowiedzieć na następujące pytania:

- Czy cała treść jest poprawna?
- Czy wszystkie treści są aktualne?
- Czy treść obejmuje wszystkie przypadki, np. różne systemy operacyjne?

### 4. <Badge type="tip">stage:cleanup</Badge> Czyszczenie {#4-cleanup}

Na tym etapie dzieje się, co następuje:

- Naprawa wszelkich błędów gramatycznych za pomocą [languagetool](https://languagetool.org/)
- Linting wszystkich plików markdown przy użyciu [`markdownlint`](https://github.com/DavidAnson/markdownlint)
- Formatowanie całego kodu Java przy użyciu [Checkstyle](https://checkstyle.sourceforge.io/)
- Inne różne poprawki lub ulepszenia

## <Badge type="tip">framework</Badge> Struktura wkładu {#contributing-framework}

Framework odnosi się do wewnętrznej struktury witryny internetowej. Wszelkie żądania ściągnięcia, które modyfikują framework witryny internetowej, będą oznaczone etykietą <Badge type="tip">framework</Badge>.

Żądania ściągnięcia frameworka należy składać wyłącznie po konsultacji z zespołem dokumentacji na [Discord Fabric](https://discord.fabricmc.net/) lub za pośrednictwem zgłoszenia.

::: info

Modyfikowanie plików paska bocznego i konfiguracji paska nawigacyjnego nie jest liczone jako żądanie ściągnięcia struktury.

:::

## Wytyczne dotyczące stylu {#style-guidelines}

Jeśli masz jakiekolwiek wątpliwości, możesz zapytać na [Fabric Discord](https://discord.fabricmc.net/) lub za pośrednictwem dyskusji GitHub.

### Napisz oryginał w amerykańskim angielskim {#write-the-original-in-american-english}

Wszystkie oryginalne dokumenty są sporządzone w języku angielskim, zgodnie z amerykańskimi zasadami gramatyki.

### Dodaj dane do frontmatter {#add-data-to-the-frontmatter}

Każda strona musi mieć tytuł i opis w tekście początkowym.

Pamiętaj, aby dodać także swoją nazwę użytkownika GitHub do „authors” w części frontmatter pliku Markdown! W ten sposób będziemy mogli przyznać Ci należne uznanie.

```yaml
---
title: Title of the Page
description: This is the description of the page.
authors:
  - your-username
---
```

### Dodaj kotwice do nagłówków {#add-anchors-to-headings}

Każdy nagłówek musi mieć kotwicę, która służy do linkowania do tego nagłówka:

```md
## This Is a Heading {#this-is-a-heading}
```

Kotwica musi zawierać małe litery, cyfry i myślniki.

### Umieść kod w przykładowym modzie {#place-code-within-the-example-mod}

Jeśli tworzysz lub modyfikujesz strony zawierające kod, umieść go w odpowiednim miejscu w przykładowym modzie (znajdującym się w folderze `/reference` w repozytorium). Następnie użyj [funkcji fragmentu kodu oferowanej przez vitepress](https://vitepress.dev/guide/markdown#import-code-snippets), aby osadzić kod.

Na przykład, aby podświetlić linie 15-21 pliku `examplemod.java` z moda:

::: code-group

```md
<<< @/reference/latest/src/main/java/com/example/docs/ExampleMod.java{15-21}
```

<<< @/reference/latest/src/main/java/com/example/docs/ExampleMod.java{15-21}[java]

:::

Jeśli potrzebujesz większego zakresu kontroli, możesz skorzystać z [funkcji transkluzji z `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

Na przykład spowoduje to osadzenie sekcji pliku powyżej oznaczonych tagiem `#entrypoint`:

::: code-group

```md
@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)
```

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

:::

### Utwórz pasek boczny dla każdej nowej sekcji {#create-a-sidebar-for-each-new-section}

Jeśli tworzysz nową sekcję, powinieneś utworzyć nowy pasek boczny w folderze `.vitepress/sidebars` i dodać go do pliku `i18n.mts`.

Jeśli potrzebujesz pomocy, zapytaj na kanale `#docs` [Fabric Discord](https://discord.fabricmc.net/).

### Dodaj nowe strony do odpowiednich pasków bocznych {#add-new-pages-to-the-relevant-sidebars}

Podczas tworzenia nowej strony należy dodać ją do odpowiedniego paska bocznego w folderze `.vitepress/sidebars`.

Jeśli potrzebujesz pomocy, zapytaj na Discordzie Fabric w kanale `#docs`.

### Umieść media w `/assets` {#place-media-in-assets}

Wszystkie obrazy należy umieścić w odpowiednim miejscu w folderze `/public/assets`.

### Używaj linków względnych! {#use-relative-links}

Wynika to z obowiązującego systemu kontroli wersji, który przetwarza łącza w celu wcześniejszego dodania wersji. Jeśli użyjesz linków bezwzględnych, numer wersji nie zostanie dodany do linku.

Nie należy także dodawać do linku rozszerzenia pliku.

Na przykład, aby utworzyć link do strony znajdującej się w `/players/index.md` ze strony `/develop/index.md`, należy wykonać następujące czynności:

::: code-group

```md:no-line-numbers [✅ Correct]
This is a relative link!
[Page](../players/index)
```

```md:no-line-numbers [❌ Wrong]
This is an absolute link.
[Page](/players/index)
```

```md:no-line-numbers [❌ Wrong]
This relative link has the file extension.
[Page](../players/index.md)
```

:::

<!---->
