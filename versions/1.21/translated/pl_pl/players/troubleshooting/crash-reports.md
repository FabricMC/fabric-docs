---
title: Raporty awarii
description: Dowiedz się, co robić z raportami awarii i jak je odczytywać.
authors:
  - IMB11

search: false
---

# Raporty awarii {#crash-reports}

:::tip
Jeśli masz jakiekolwiek problemy ze znalezieniem przyczyny awarii, możesz poprosić o pomoc na [Discordzie Fabric](https://discord.gg/v6v4pMv) na kanale `#player-support` lub `#server-admin-support`.
:::

Raporty awarii są bardzo ważną częścią rozwiązywania problemów z grą lub serwerem. Zawierają one dużo informacji na temat awarii i mogą pomóc w ustaleniu jej przyczyny.

## Znajdowanie raportów awarii {#finding-crash-reports}

Raporty awarii są przechowywane w folderze `crash-reports` w katalogu gry. W przypadku serwera przechowywane są w folderze `crash-reports` w katalogu serwera.

W przypadku innych launcherów należy zapoznać się z ich dokumentacją, aby wiedzieć, gdzie znaleźć raporty awarii.

Raporty awarii znajdziesz w następujących lokalizacjach:

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\crash-reports
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/crash-reports
```

```:no-line-numbers [Linux]
~/.minecraft/crash-reports
```

:::

## Odczytywanie raportów awarii {#reading-crash-reports}

Raporty awarii są bardzo długie i potrafią być skomplikowane do odczytania. Zawierają one jednak dużo informacji na temat awarii i mogą pomóc w ustaleniu jej przyczyny.

W tym przewodniku będziemy wykorzystywać [ten raport awarii](/assets/players/crash-report-example.txt).

:::details Pokaż raport awarii

<<< @/public/assets/players/crash-report-example.txt{log}

:::

### Sekcje raportu awarii {#crash-report-sections}

Raporty awarii składają się z kilku sekcji, a każda z nich oddzielona jest nagłówkiem:

- `---- Minecraft Crash Report ----`, podsumowanie raportu. Ta sekcja zawiera główny błąd, który spowodował awarię, godzinę jego wystąpienia oraz powiązany ślad stosu. Jest to najważniejsza sekcja raportu awarii, ponieważ ślad stosu zazwyczaj zawiera odniesienia do moda, który spowodował awarię.
- `-- Last Reload --`, ta sekcja nie jest szczególnie przydatna, chyba że awaria wystąpiła podczas przeładowywania zasobów (<kbd>F3</kbd> + <kbd>T</kbd>). Zawiera ona godzinę ostatniego przeładowania i powiązany ślad stosu wszelkich błędów, które wystąpiły podczas procesu przeładowywania. Błędy te są zazwyczaj powodowane przez paczki zasobów i można je zignorować, chyba że powodują problemy z grą.
- `-- System Details --`, ta sekcja zawiera informacje o Twoim systemie, takie jak system operacyjny, wersja Javy i ilość pamięci przydzielonej grze. Jest przydatna do określenia, czy używasz odpowiedniej wersji Javy oraz czy grze została przydzielona wystarczająca ilość pamięci.
  - W tej sekcji Fabric doda niestandardową linijkę `Fabric Mods`, a po niej listę wszystkich zainstalowanych modów. Ta sekcja jest przydatna do określenia, czy między modami mogły wystąpić jakiekolwiek konflikty.

### Analizowanie raportu awarii {#breaking-down-the-crash-report}

Teraz, gdy już wiemy, co zawiera każda sekcja raportu awarii, możemy zacząć go analizować i znaleźć przyczynę awarii.

Korzystając z powyższego przykładu, możemy przeanalizować raport awarii i znaleźć jej przyczynę, w tym mody, które ją spowodowały.

Ślad stosu w sekcji `---- Minecraft Crash Report ----` jest w tym przypadku najważniejszy, ponieważ zawiera główny błąd, który spowodował awarię.

:::details Pokaż błąd

<<< @/public/assets/players/crash-report-example.txt{7 log}

:::

Ze względu na liczbę modów wymienionych w śladzie stosu, może być trudno od razu wskazać winowajcę, ale pierwszą rzeczą, jaką należy zrobić, jest poszukanie moda, który spowodował awarię.

W tym przypadku modem, który spowodował awarię jest `snownee`, ponieważ jest to pierwszy mod wymieniony w śladzie stosu.

Patrząc jednak na liczbę wymienionych modów, możliwe jest, że istnieją pewne problemy z kompatybilnością między modami, a mod, który spowodował awarię, może nie być winowajcą. W takim przypadku najlepiej zgłosić awarię autorowi moda i pozwolić mu ją zbadać.

## Awarie mixinów {#mixin-crashes}

:::info
Mixiny są sposobem, w jaki mody mogą modyfikować grę bez konieczności modyfikowania jej kodu źródłowego. Są one używane przez wiele modów i stanowią bardzo potężne narzędzie dla twórców modów.
:::

Kiedy mixin ulegnie awarii, zazwyczaj zostanie wspomniany w śladzie stosu wraz z klasą, którą modyfikuje.

Metody mixinów będą zawierać w śladzie stosu `modid$handlerName`, gdzie `modid` to ID moda, a `handlerName` to nazwa obsługi mixina.

```:no-line-numbers
... net.minecraft.class_2248.method_3821$$$modid$handlerName() ... // [!code focus]
```

Możesz wykorzystać te informacje, aby znaleźć moda, który spowodował awarię, a następnie zgłosić ją autorowi moda.

## Co robić z raportami awarii {#what-to-do-with-crash-reports}

Najlepszą rzeczą, jaką można zrobić z raportami awarii to przesłanie ich na stronę do wklejania tekstów, a następnie udostępnienie linku autorowi moda przez jego system zgłaszania błędów lub jakąkolwiek inną formą komunikacji (np. na Discordzie).

Pozwoli to autorowi moda na zbadanie awarii, potencjalne jej odtworzenie i rozwiązanie problemu, który ją spowodował.

Popularne strony do wklejania i udostępniania raportów awarii:

- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
- [Pastebin](https://pastebin.com/)
