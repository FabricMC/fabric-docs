---
title: Instalando Java no Linux
description: Um guia passo a passo de como instalar o Java no Linux.
authors:
  - IMB11
next: false
---

Este guia o orientará na instalação do Java 17 no Linux.

O Iniciador do Minecraft vem com o seu próprio Java instalado. Então, essa sessão somente é relevante, se você quiser usar o instalador baseado em `.jar` ou se você quiser usar o Servidor de Minecraft `.jar`.

## 1. Verificar Se o Java Já Está Instalado

Abra um terminal, digite `java -version`, e pressione <kbd>Enter</kbd>.

![Terminal com "java -version" digitado](/assets/players/installing-java/linux-java-version.png)

::: warning

Para usar o Minecraft 1.21.11, você precisará ter, pelo menos, o Java 21 instalado.

Se esse comando exibir qualquer versão inferior a 21, você precisará atualizar o seu Java; continue lendo essa página.

:::

## 2. Baixando and Instalando o Java 21 {#2-downloading-and-installing-java}

Nós recomendamos usar o "OpenJDK 21", que está disponível para a maioria das versões do Linux.

### Arch Linux {#arch-linux}

::: info

Para mais informações sobre a instalação do Java no Arch Linux, veja [Arch Linux Wiki](https://wiki.archlinux.org/title/Java).

:::

Você pode instalar a última versão JRE dos repositórios oficiais:

```sh
sudo pacman -S jre-openjdk
```

Se você estiver executando um servidor sem precisar de uma IU gráfica, você pode instalar as versões próprias para isso:

```sh
sudo pacman -S jre-openjdk-headless
```

Se você planeja desenvolver "mods", você precisará do JDK:

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu {#debian-ubuntu}

Você pode instalar o Java 21, usando `apt`, junto aos seguintes comandos:

```sh
sudo apt update
sudo apt install openjdk-21-jdk
```

### Fedora {#fedora}

Você pode instalar o Java 21, usando `dnf`, junto aos seguintes comandos:

```sh
sudo dnf install java-21-openjdk
```

Se você não precisa de uma IU gráfica, você pode usar a versão própria para isso:

```sh
sudo dnf install java-21-openjdk-headless
```

Se você planeja desenvolver "mods", você precisará do JDK:

```sh
sudo dnf install java-21-openjdk-devel
```

### Outras versões de Linux {#other-linux-distributions}

Se sua versão não está listada acima, você pode baixar a última versão JRE direto do [Adoptium](https://adoptium.net/temurin/)

Você deveria consultar um guia próprio para a sua versão, se você planeja desenvolver mods.

## 3. Certifique-se de que a versão do Java 21 esteja instalada {#3-verify-that-java-is-installed}

Uma que vez que a instalação esteja completa, você pode se certificar disso abrindo um terminal de comando e digitando `java -version`.

Se o comando for executado corretamente, você verá o que foi mostrado anteriormente, no local onde a versão do Java é mostrada:

![Terminal com "java -version" digitado](/assets/players/installing-java/linux-java-version.png)
