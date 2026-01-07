---
title: Tworzenie projektu
description: Przewodnik krok po kroku opisujący, jak utworzyć nowy projekt moda przy użyciu generatora szablonów modów Fabric.
authors:
  - Cactooz
  - IMB11
  - radstevee
  - Thomas1034
---

Fabric zapewnia łatwy sposób tworzenia nowych projektów modów za pomocą generatora szablonów modów Fabric. Jeśli chcesz, możesz ręcznie utworzyć nowy projekt, korzystając z przykładowego repozytorium modów. Zapoznaj się z sekcją [Ręczne tworzenie projektu](#manual-project-creation).

## Generowanie projektu {#generating-a-project}

Możesz użyć [Generatora szablonów modów Fabric](https://fabricmc.net/develop/template/), aby wygenerować nowy projekt dla swojego moda — powinieneś wypełnić wymagane pola, takie jak nazwa moda, nazwa pakietu i wersja Minecrafta, dla której chcesz tworzyć mod.

Nazwa pakietu powinna składać się z małych liter, być rozdzielona kropkami i unikalna, aby uniknąć konfliktów z pakietami innych programistów. Zazwyczaj jest ona formatowana jak odwrócona domena internetowa, np. `com.example.example-mod`.

:::warning WAŻNE

Na przykład, jeśli ID Twojego moda to **`my-cool-mod`**, zamiast _`resources/assets/example-mod`_ użyj **`resources/assets/my-cool-mod`**.
:::

![Podgląd generatora](/assets/develop/getting-started/template-generator.png)

Jeśli zamiast domyślnych mapowań Mojang chcesz użyć mapowań Kotlin lub Yarn programu Fabric albo chcesz dodać generatory danych, możesz wybrać odpowiednie opcje w sekcji `Advanced Options`.

:::info
Przykłady kodu podane na tej stronie używają [oficjalnych nazw Mojang](../migrating-mappings#mappings). Jeśli Twój mod nie korzysta z tych samych mapowań, w których napisano tę dokumentację, będziesz musiał przekonwertować przykłady, korzystając z witryn takich jak [mappings.dev](https://mappings.dev/) lub [Linkie](https://linkie.shedaniel.dev/mappings?namespace=yarn&translateMode=ns&translateAs=mojang_raw&search=).
:::

![Sekcja advanced options](/assets/develop/getting-started/template-generator-advanced.png)

Po wypełnieniu wymaganych pól kliknij przycisk `Generate`, a generator utworzy nowy projekt, który możesz wykorzystać w formie pliku zip.

Należy wypakować plik zip do wybranej lokalizacji, a następnie otworzyć wypakowany folder w środowisku IDE.

::: tip
You should follow these rules when choosing the path to your project:

- Unikaj katalogów przechowywania danych w chmurze (na przykład Microsoft OneDrive)
- Unikaj znaków spoza zestawu ASCII (na przykład emotek, znaków polskich)
- Unikaj spacji

Przykładem "dobrej" ścieżki może być: `C:\Projekty\NazwaTwojegoProjektu`
:::

## Ręczne tworzenie projektu {#manual-project-creation}

:::info WYMAGANIA WSTĘPNE
Aby sklonować przykładowe repozytorium modów, będziesz potrzebować zainstalowanego [Gita](https://git-scm.com/).
:::

Jeśli nie możesz skorzystać z generatora Fabric Template Mod Generator, możesz ręcznie utworzyć nowy projekt, wykonując poniższe kroki.

Najpierw sklonuj przykładowe repozytorium modów za pomocą Gita:

```sh
git clone https://github.com/FabricMC/fabric-example-mod/ my-mod-project
```

Spowoduje to sklonowanie repozytorium do nowego folderu o nazwie `my-mod-project`.

Następnie należy usunąć folder `.git` ze sklonowanego repozytorium i otworzyć projekt. Jeżeli folder `.git` nie pojawia się, należy włączyć wyświetlanie ukrytych plików w eksploratorze plików.

Po otwarciu projektu w środowisku IDE powinno ono automatycznie załadować konfigurację Gradle projektu i wykonać niezbędne zadania konfiguracyjne.

Jak już wcześniej wspomniano, jeśli otrzymasz powiadomienie dotyczące skryptu kompilacji Gradle, kliknij przycisk `Import Gradle Project`.

### Modyfikowanie szablonu {#modifying-the-template}

Po zaimportowaniu projektu należy zmodyfikować jego szczegóły, tak aby odpowiadały szczegółom moda:

- Zmodyfikuj plik `gradle.properties` projektu, zmieniając właściwości `maven_group` i `archive_base_name` tak, aby odpowiadały szczegółom Twojej moyfikacji.
- Zmodyfikuj plik `fabric.mod.json`, zmieniając właściwości `id`, `name` i `description` tak, aby odpowiadały szczegółom Twojego moda.
- Pamiętaj o aktualizacji wersji gry Minecraft, mappingów, Loadera i Loom (wszystkie te informacje można uzyskać za pomocą <https://fabricmc.net/develop/>), aby odpowiadały one docelowym wersjom.

Możesz oczywiście zmienić nazwę pakietu i główny class moda, aby odpowiadały szczegółom moda.
