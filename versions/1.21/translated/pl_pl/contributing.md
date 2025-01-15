---
title: Wytyczne współtworzenia
description: Wytyczne dotyczące współtworzenia i wnoszenia wkładu do dokumentacji Fabric.
---

# Wytyczne współtworzenia {#contributing}

Ta strona używa [VitePress](https://vitepress.dev/) do generowania statycznego HTML-a z różnych plików Markdown. Warto zapoznać się z rozszerzeniami Markdown, które obsługuje VitePress, [tutaj](https://vitepress.dev/guide/markdown.html#features).

Istnieją trzy sposoby, w jakie możesz pomóc współtworzyć tę stronę:

- [Tłumaczenie dokumentacji](#translating-documentation)
- [Współtworzenie treści](#contributing-content)
- [Współtworzenie frameworka](#contributing-framework)

Wszystkie współtworzone treści i wniesiony wkład muszą być zgodne z naszymi [wytycznymi dotyczącymi stylu](#style-guidelines).

## Tłumaczenie dokumentacji {#translating-documentation}

Jeśli chcesz przetłumaczyć dokumentację na swój język, możesz to zrobić dołączając do projektu [Fabric na platformie Crowdin](https://crowdin.com/project/fabricmc).

<!-- markdownlint-disable-next-line titlecase-rule -->

## <Badge type="tip">new-content</Badge> Współtworzenie treści {#contributing-content}

Tworzenie nowych treści jest głównym sposobem na przyczynienie się do rozwoju dokumentacji Fabric.

Wszystkie nowe współtworzone treści przechodzą przez następujące etapy, z których każdy jest powiązany z etykietą:

1. <Badge type="tip">lokalnie</Badge> Przygotuj swoje zmiany i utwórz PR
2. <Badge type="tip">stage:expansion</Badge>: Pomoc w rozbudowie treści (w razie potrzeby)
3. <Badge type="tip">stage:verification</Badge>: Weryfikowanie treści
4. <Badge type="tip">stage:cleanup</Badge>: Sprawdzanie gramatyki, błędów itp.
5. <Badge type="tip">stage:ready</Badge>: Gotowe do wdrożenia!

Wszystkie treści muszą być zgodne z naszymi [wytycznymi dotyczącymi stylu](#style-guidelines).

### 1. Przygotuj swoje zmiany {#1-prepare-your-changes}

Ta strona jest open-source i rozwijana jest w repozytorium na GitHubie, co oznacza, że polegamy na GitHubowym schemacie pracy:

1. [Sforkuj repozytorium GitHub](https://github.com/FabricMC/fabric-docs/fork)
2. Stwórz nową gałąź w swoim forku
3. Wprowadź zmiany na tej gałęzi
4. Utwórz Pull Request w oryginalnym repozytorium

Więcej informacji o GitHubowym schemacie pracy znajdziesz [tutaj](https://docs.github.com/en/get-started/using-github/github-flow).

Zmiany możesz wprowadzać bezpośrednio z interfejsu internetowego na GitHubie lub lokalnie edytować i podglądać zmiany na stronie.

#### Klonowanie swojego forka {#clone-your-fork}

Jeśli chcesz wprowadzać zmiany lokalnie, będzie ci potrzebny [Git](https://git-scm.com/).

Po jego zainstalowaniu sklonuj swoje sforkowane repozytorium za pomocą:

```sh
# make sure to replace "your-username" with your actual username
git clone https://github.com/your-username/fabric-docs.git
```

#### Instalowanie zależności {#install-dependencies}

Jeśli chcesz lokalnie podglądać swoje zmiany na stronie, będzie ci potrzebny [Node.js 18+](https://nodejs.org/en/).

Po jego zainstalowaniu zainstaluj wszystkie wymagane zależności za pomocą:

```sh
npm install
```

#### Uruchamianie serwera deweloperskiego {#run-the-development-server}

Serwer ten pozwala na podglądanie w czasie rzeczywistym wprowadzonych zmian lokalnie pod adresem `localhost:5173`, automatycznie przeładowując stronę po wprowadzeniu zmian. Uruchamia się go za pomocą:

```sh
npm run dev
```

Teraz możesz otworzyć i przeglądać stronę z przeglądarki, odwiedzając `http://localhost:5173`.

#### Budowanie strony {#building-the-website}

Spowoduje to skompilowanie wszystkich plików Markdown do statycznych plików HTML i umieszczenie ich w `.vitepress/dist`.

```sh
npm run build
```

#### Podglądanie zbudowanej strony {#previewing-the-built-website}

Spowoduje to uruchomienie lokalnego serwera na porcie `4173` wyświetlającego zawartość znajdującą się w `.vitepress/dist`.

```sh
npm run preview
```

#### Tworzenie Pull Requesta {#opening-a-pull-request}

Po wprowadzeniu odpowiednich zmian możesz je s`push`ować za pomocą:

```sh
git add .
git commit -m "Description of your changes"
git push
```

Następnie, aby utworzyć PR, otwórz link wyświetlony w wyniku polecenia `git push`.

### 2. <Badge type="tip">stage:expansion</Badge> Pomoc w rozbudowie treści {#2-guidance-for-expansion-if-needed}

Jeśli nasz zespół od dokumentacji uzna, że twój PR można rozbudować, członek zespołu doda do niego etykietę <Badge type="tip">stage:expansion</Badge> wraz z komentarzem wyjaśniającym, co mogłoby zostać rozbudowane. Jeśli się zgadasz z podanymi sugestiami, możesz rozbudować swój PR.

Jeśli nie chcesz rozbudowywać swojego PR, ale chcesz, aby zrobił to ktoś inny w późniejszym terminie, musisz utworzyć zgłoszenie w [zakładce „Issues”](https://github.com/FabricMC/fabric-docs/issues) i wyjaśnić, co mogłoby zostać rozbudowane. Nasz zespół od dokumentacji doda następnie do twojego PR etykietę <Badge type="tip">help-wanted</Badge>.

### 3. <Badge type="tip">stage:verification</Badge> Weryfikowanie treści {#3-content-verification}

Jest to najważniejszy etap, ponieważ zapewnia, że treść jest poprawna i zgodna z wytycznymi dokumentacji Fabric dotyczącymi stylu.

Na tym etapie należy odpowiedzieć na następujące pytania:

- Czy wszystkie treści są poprawne?
- Czy wszystkie treści są aktualne?
- Czy treść obejmuje wszystkie przypadki, takie jak różne systemy operacyjne?

### 4. <Badge type="tip">stage:cleanup</Badge> Porządkowanie {#4-cleanup}

Na tym etapie wykonuje się następujące czynności:

- Poprawianie wszelkich błędów gramatycznych za pomocą [LanguageTool](https://languagetool.org/)
- Analizowanie (linting) pod kątem potencjalnych błędów wszystkich plików Markdown za pomocą [`markdownlint`](https://github.com/DavidAnson/markdownlint)
- Formatowanie całego kodu Java za pomocą [Checkstyle](https://checkstyle.sourceforge.io/)
- Inne drobne poprawki lub ulepszenia

## <Badge type="tip">framework</Badge> Współtworzenie frameworka {#contributing-framework}

Framework odnosi się do wewnętrznej struktury strony. Wszelkie pull requesty, które go modyfikują będą oznaczone etykietą <Badge type="tip">framework</Badge>.

Pull requesty dotyczące frameworka należy tworzyć wyłącznie po konsultacji z zespołem od dokumentacji na [Discordzie Fabric](https://discord.gg/v6v4pMv) lub za pośrednictwem zgłoszenia.

:::info
Modyfikowanie plików paska bocznego i konfiguracji paska nawigacyjnego nie jest zaliczane jako pull request dotyczący frameworka.
:::

## Wytyczne dotyczące stylu {#style-guidelines}

Jeśli nie masz pewności co do czegokolwiek, możesz zapytać na [Discordzie Fabric](https://discord.gg/v6v4pMv) lub za pośrednictwem dyskusji na GitHubie.

### Pisz wszystko w amerykańskim angielskim {#write-the-original-in-american-english}

Cała oryginalna dokumentacja jest napisana w języku angielskim, zgodnie z amerykańskimi zasadami gramatyki.

### Dodaj metadane do strony {#add-data-to-the-frontmatter}

Każda strona musi zawierać tytuł (`title`) oraz opis (`description`) w segmencie nazywanym Frontmatter, który znajduje się na początku pliku.

Pamiętaj również dodać swoją nazwę użytkownika na GitHubie w polu `authors`! W ten sposób będziemy mogli przyznać Ci należne wyróżnienie.

```md
---
title: Title of the Page
description: This is the description of the page.
authors:
  - your-username
---

# Title of the Page {#title-of-the-page}

...
```

### Dodaj zakotwiczenia do nagłówków {#add-anchors-to-headings}

Każdy nagłówek być zakotwiczony, aby można było go zlinkować:

```md
# This Is a Heading {#this-is-a-heading}
```

Zakotwiczenie może składać się tylko z małych liter, myślników i cyfr.

### Umieść kod w modzie `/reference` {#place-code-within-the-reference-mod}

Jeśli tworzysz lub modyfikujesz strony zawierające kod, umieść kod w odpowiednim miejscu w modzie referencyjnym (znajdującym się w folderze `/reference` repozytorium). Następnie użyj funkcji [importowania fragmentów kodu oferowanej przez VitePress](https://vitepress.dev/guide/markdown#import-code-snippets), aby osadzić kod.

Na przykład, aby wyróżnić linie 15-21 pliku `FabricDocsReference.java` z moda referencyjnego, użyj:

::: code-group

```md
<<< @/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java{15-21}
```

<<< @/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java{15-21}[java]

:::

Jeśli potrzebujesz większej kontroli, możesz użyć [funkcji transkluzji z `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

Dla przykładu ten kod osadzi sekcje powyższego pliku, które są oznaczone tagiem `#entrypoint`:

::: code-group

```md
@[code transcludeWith=#entrypoint](@/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java)
```

@[code transcludeWith=#entrypoint](@/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java)

:::

### Utwórz pasek boczny dla każdej nowej sekcji {#create-a-sidebar-for-each-new-section}

Jeśli tworzysz nową sekcję musisz stworzyć dla niej nowy pasek boczny w folderze `.vitepress/sidebars` i dodać go do pliku `i18n.mts`.

Jeśli potrzebujesz z tym pomocy, zapytaj na [Discordzie Fabric](https://discord.gg/v6v4pMv) na kanale `#docs`.

### Dodaj nowe strony do odpowiednich pasków bocznych {#add-new-pages-to-the-relevant-sidebars}

Podczas tworzenia nowej strony należy dodać ją do odpowiedniego paska bocznego w folderze `.vitepress/sidebars`.

Jeśli potrzebujesz z tym pomocy, zapytaj na [Discordzie Fabric](https://discord.gg/v6v4pMv) na kanale `#docs`.

### Umieść multimedia w folderze `/assets` {#place-media-in-assets}

Wszelkie obrazy powinny zostać umieszczone w odpowiednim miejscu w folderze `/public/assets`.

### Używaj linków względnych! {#use-relative-links}

Powodem tego jest istniejący system wersjonowania, który przetwarza linki, dodając na początku numer wersji. Jeśli użyjesz linku bezwzględnego, numer wersji nie zostanie do niego dodany.

Nie należy również dodawać rozszerzenia pliku do linku.

Na przykład, aby utworzyć link do strony znajdującej się w `/players/index.md` ze strony `/develop/index.md`, należy zrobić to w następujący sposób:

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
