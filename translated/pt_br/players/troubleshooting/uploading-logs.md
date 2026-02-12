---
title: Subindo arquivos de Log (Logs)
description: Como subir arquivos de Log para resolução de problemas.
authors:
  - IMB11
---

Quando houver Correção de Falhas, é frenquentemente necessário prover arquivos de log para ajudar a identificar a causa das mesmas.

## Por que eu deveria subir Logs? {#why-should-i-upload-logs}

Subir Logs, permite outros a ajudar na resolução dos seus erros muito mais rapidamente do que, simplesmente, colocá-los em Chats ou posts de Fóruns. Isso também permite que você compartilhe os seus Logs, sem ter que copiá-los e colá-los múltiplas vezes.

Alguns Sites de Armazenamento também fornecem destacamento de Sintaxe para os Logs, o que os tornam muito mais fáceis de serem lidos. Também, podem censurar informações sensíveis. Como, por exemplo, 'nome de usuário' ou informações do sistema.

## Relatórios de Travamento {#crash-reports}

Relatórios de Travamento são automaticamente gerados quando o jogo trava. Eles contêm informações sobre o travamento e não os reais Logs do jogo. Eles estão localizados no repositório `crash-reports` dentro do diretório do jogo.

Para mais informações sobre Relatórios de Travamento, veja [Crash Reports](./crash-reports).

## Localizando os Logs {#locating-logs}

Esse guia cobre o Iniciador Oficial de Minecraft (Popularmente referido como "Vanilla Launcher") - para outros iniciadores, você deve consultar suas respectivas documentações.

Os Logs estão localizados no repositório `Logs` dentro do Diretório do jogo. O mesmo pode ser encontrado nas seguintes localidades, dependendo do seu sistema operacional:

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

O Log mais recente tem o nome de `latest.log` e os anteriores usam o padrão nominal `yyyy-mm-dd_number.log.gz`.

## Subindo Logs para a Internet {#uploading-logs-online}

Os Logs podem ser submetidos online a uma variedade de serviços, como, por exemplo:

- [Pastebin](https://pastebin.com/)
- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
