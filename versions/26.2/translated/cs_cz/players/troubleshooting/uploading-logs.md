---
title: Nahrávání logů
description: Jak nahrávat logy pro řešení problémů.
authors:
  - IMB11
---

Při řešení problémů je pro vyhledání příčiny často nutné poskytnout logy.

## Proč bych měl nahrávat logy? {#why-should-i-upload-logs}

Nahrávání logů pomáhá ostatním poskytnout ti pomoc rychleji než jednoduše vložením logů do chatu nebo příspěvku na fóru. Také ti to umožňuje sdílet logy s ostatními bez nutnosti je kopírovat a vkládat.

Některé stránky speciálně určené pro vkládání logů poskytují zvýraznění syntaxu logů tak, aby se lépe četly, nebo cenzuru citlivých informací, jako uživatelského jména nebo systémových informací.

## Hlášení o chybách {#crash-reports}

Hlášení o chybách je automaticky vygenerováno při spadnutí hry. Obsahují pouze informace o chybě, ne logy ze hry. Nachází se ve složce `crash-reports` v adresáři hry.

Více informací o hlášeních o chybách vám poskytne tutoriál o [hlášeních o chybách](./crash-reports).

## Vyhledávání logů {#locating-logs}

Tenhle tutoriál je určen pouze pro oficiální Minecraft Launcher (také znám pod názvem "vanilla launcher") - u neoficiálních launcherů navštivte jejich dokumentaci.

Logy se nacházejí ve složce `logs` v adresáři hry; ten lze dle operačního systému najít v následujících lokacích:

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

Nejnovější log se jmenuje `latest.log`, předchozí logy jsou pojmenovávány pomocí schématu `yyyy-mm-dd_number.log.gz`.

## Nahrávání logů online {#uploading-logs-online}

Logy lze nahrát na mnoho stránek, jako jsou:

- [Pastebin](https://pastebin.com/)
- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
