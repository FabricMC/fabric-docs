---
title: Instalando o Fabric no macOS
description: Um guia passo a passo de como instalar o Java no macOS.
authors:
  - dexman545
  - ezfe
next: false
---

Esse guia irá ajudá-lo com a Instalação do Java 21 no macOS.

O Iniciador do Minecraft vem com o seu próprio Java instalado. Então, essa sessão somente é relevante, se você quiser usar o instalador baseado em ".jar" ou se você quiser usar o Servidor de Minecraft ".jar".

## 1. Verifique se o Java já está instalado {#1-check-if-java-is-already-installed}

No terminal (localizado em `/Aplicações/terminal.app`) digite o seguinte comando, e pressione <kbd>Enter</kbd>:

```sh
$(/usr/libexec/java_home -v 21)/bin/java --version
```

Você deve ver algo como isso:

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

Perceba o número da versão: No exemplo acima é `21.0.9`.

::: warning

Para usar o Minecraft 1.21.11, você precisará ter, pelo menos, o Java 21 instalado.

Se esse comando exibir qualquer versão inferior a 21, você precisará atualizar o seu Java; continue lendo essa página.

:::

## 2. Baixando and Instalando o Java 21 {#2-downloading-and-installing-java}

Nós recomendamos usar [Arquitetura Adoptium no OpenJDK 21](https://adoptium.net/temurin/releases?version=21&os=mac&arch=any&mode=filter):

![Página para baixar o Temurin Java](/assets/players/installing-java/macos-download-java.png)

Certifique-se de selecionar a versão "21 - LTS" e escolha o formato de instalação `.PKG`.
Você, também, deveria escolher a arquitetura correta, dependendo do Chip do seu sistema:

- Se você tiver um Chip Apple da Série M (M-Series Chip), escolha `aarch64` (Padrão)
- Se você tiver um Chip Intel, escolha `x64`
- Siga essas [instruções para saber qual chip está em seu Mac] (https://support.apple.com/en-us/116943)

Após baixar o instalador `.pkg`, execute-o e siga essas instruções:

![Instalador do Temurin Java](/assets/players/installing-java/macos-installer.png)

Você terá que digitar a sua senha de Administrador para completar a instalação:

![macOS senha comando](/assets/players/installing-java/macos-password-prompt.png)

### Usando o Homebrew {#using-homebrew}

Se você já tiver o [Homebrew](https://brew.sh) instalado, você pode instalar o Java 21 usando `brew`:

```sh
brew install --cask temurin@21
```

## 3. Certifique-se de que a versão do Java 21 esteja instalada {#3-verify-that-java-is-installed}

Uma vez que a instalação esteja completa, você pode verificar que o Java está devidamente instalado, ao abrir o Terminal novamente e digitar `$(/usr/libexec/java_home -v 21)/bin/java --version`.

Se o comando for executado corretamente, você verá o seguinte:

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

Se você encontrar problemas, sinta-se a vontade para pedir ajuda em [Discord do Fabric](https://discord.fabricmc.net/) no canal `#player-support`.
