---
title: Instalando o Fabric no macOS
description: Um guia passo a passo de como instalar o Java no macOS.
authors:
  - dexman545
  - ezfe
next: false
---

Esse guia lhe mostrará como instalar Java 25 no macOS.

O Iniciador do Minecraft vem com o seu próprio Java instalado. Então, essa sessão somente é relevante, se você quiser usar o instalador baseado em ".jar" ou se você quiser usar o Servidor de Minecraft ".jar".

## 1. Verifique se o Java já está instalado {#1-check-if-java-is-already-installed}

No terminal (localizado em `/Aplicações/terminal.app`) digite o seguinte comando, e pressione <kbd>Enter</kbd>:

```sh
$(/usr/libexec/java_home -v 25)/bin/java --version
```

Você deve ver algo como isso:

```text:no-line-numbers
openjdk 25.0.2 2026-01-20 LTS
OpenJDK Runtime Environment Temurin-25.0.2+10 (build 25.0.2+10-LTS)
OpenJDK 64-Bit Server VM Temurin-25.0.2+10 (build 25.0.2+10-LTS, mixed mode, sharing)
```

Observe o número da versão: no exemplo acima é `25.0.9`.

::: warning

Para usar o Minecraft 26.1, você precisará pelo menos do Java 25 instalado.

Se esse comando mostrar qualquer versão inferior a 25, você precisará atualizar a sua instalação Java existente; continue lendo essa página.

:::

## 2. Baixando e instalando Java 25 {#2-downloading-and-installing-java}

Nós recomendamos usar [Arquitetura Adoptium no OpenJDK 25](https://adoptium.net/temurin/releases?version=25&os=mac&arch=any&mode=filter):

![Página para baixar o Temurin Java](/assets/players/installing-java/macos-download-java.png)

Certifique-se de selecionar a versão "25 - LTS" e escolha o formato de instalação `.PKG`.
Você, também, deveria escolher a arquitetura correta, dependendo do Chip do seu sistema:

- Se você tiver um Chip Apple da Série M (M-Series Chip), escolha `aarch64` (Padrão)
- Se você tiver um Chip Intel, escolha `x64`
- Siga essas [instruções para saber qual chip está em seu Mac] (https://support.apple.com/en-us/116943)

Após baixar o instalador `.pkg`, execute-o e siga essas instruções:

![Instalador do Temurin Java](/assets/players/installing-java/macos-installer.png)

Você terá que digitar a sua senha de Administrador para completar a instalação:

![macOS senha comando](/assets/players/installing-java/macos-password-prompt.png)

### Usando o Homebrew {#using-homebrew}

Se você já tiver o [Homebrew](https://brew.sh) instalado, você pode instalar o Java 25 usando `brew`:

```sh
brew install --cask temurin@25
```

## 3. Verificar Se o Java 25 Está Instalado {#3-verify-that-java-is-installed}

Uma vez que a instalação esteja completa, você pode verificar se o Java está ativo abrindo o Terminal novamente e digitando `$(/usr/libexec/java_home -v 25)/bin/java --version`.

Se o comando for executado corretamente, você verá o seguinte:

```text:no-line-numbers
openjdk 25.0.2 2026-01-20 LTS
OpenJDK Runtime Environment Temurin-25.0.2+10 (build 25.0.2+10-LTS)
OpenJDK 64-Bit Server VM Temurin-25.0.2+10 (build 25.0.2+10-LTS, mixed mode, sharing)
```

Se você encontrar problemas, sinta-se a vontade para pedir ajuda em [Discord do Fabric](https://discord.fabricmc.net/) no canal `#player-support`.
