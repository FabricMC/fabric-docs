---
title: Przesyłanie logów
description: Jak przesyłać logi na potrzeby rozwiązywania problemów.
authors:
  - IMB11
---

Podczas rozwiązywania problemów często konieczne jest udostępnienie logów, aby móc zidentyfikować przyczynę problemu.

## Dlaczego warto przesyłać logi? {#why-should-i-upload-logs}

Przesyłanie logów pozwala innym pomóc ci w rozwiązywaniu problemów znacznie szybciej niż poprzez zwykłe wklejenie logów na czat lub post na forum. Pozwala również na udostępnianie logów innym, bez konieczności ich kopiowania i wklejania.

Niektóre strony do wklejania tekstu zapewniają również podświetlanie składni logów, co ułatwia ich czytanie, oraz mogą cenzurować poufne informacje, takie jak twoja nazwa użytkownika czy informacje o systemie.

## Raporty awarii {#crash-reports}

Raporty awarii są automatycznie generowane podczas awarii gry. Zawierają one jednak tylko informacje o awarii, a nie rzeczywiste logi gry. Znajdują się one w folderze `crash-reports` w katalogu gry.

Więcej informacji na temat raportów awarii znajdziesz w przewodniku [Raporty awarii](./crash-reports).

## Znajdowanie logów {#locating-logs}

Ten przewodnik dotyczy oficjalnego launchera Minecrafta (powszechnie określanego jako „vanilla launcher”) — w przypadku innych launcherów należy zapoznać się z ich dokumentacją.

Logi znajdują się w folderze `logs` w katalogu gry, który można znaleźć w następujących lokalizacjach dla poszczególnych systemów operacyjnych:

::: code-group

```text:no-line-numbers [Windows]
%appdata%\.minecraft
```

```text:no-line-numbers [macOS]
~/Library/Application Support/minecraft
```

```text:no-line-numbers [Linux]
~/.minecraft
```

:::

Najnowszy log ma nazwę `latest.log`, a poprzednie logi nazywane są według wzorca `rrrr-mm-dd_numer.log.gz`.

## Przesyłanie logów online {#uploading-logs-online}

Logi można przesłać do różnych usług, takich jak:

- [Pastebin](https://pastebin.com/)
- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
