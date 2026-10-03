---
title: Instalando Java no Linux
description: Um guia passo a passo de como instalar o Java no Linux.
authors:
  - IMB11
next: false
---

Esse guia lhe mostrará como instalar Java 25 no Linux.

O Iniciador do Minecraft vem com o seu próprio Java instalado. Então, essa sessão somente é relevante, se você quiser usar o instalador baseado em `.jar` ou se você quiser usar o Servidor de Minecraft `.jar`.

## 1. Verificar Se o Java Já Está Instalado {#1-check-if-java-is-already-installed}

Abra um terminal, digite `java -version`, e pressione <kbd>Enter</kbd>.

![Terminal com "java -version" digitado](/assets/players/installing-java/linux-java-version.png)

::: warning

Para usar Minecraft 26.1, você precisará pelo menos do Java 25 instalado.

Se esse comando exibir qualquer versão inferior a 25, você precisará atualizar a sua instalação Java existente.

:::

## 2. Baixando e instalando Java 25 {#2-downloading-and-installing-java}

Nós recomendamos usar o OpenJDK 25, que está disponível para a maioria das distribuições Linux.

### Arch Linux {#arch-linux}

::: info

Para mais informações sobre a instalação do Java no Arch Linux, veja [Arch Linux Wiki](https://wiki.archlinux.org/title/Java).

:::

Você pode instalar a última versão JRE nos repositórios oficiais:

```sh
sudo pacman -S jre-openjdk
```

Se você estiver executando um servidor que não precisa de uma interface gráfica, você poderá instalar a versão headless:

```sh
sudo pacman -S jre-openjdk-headless
```

Se você planeja desenvolver "mods", você precisará do JDK:

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu {#debian-ubuntu}

Você pode instalar o Java 25 usando `apt` com os seguintes comandos:

```sh
sudo apt update
sudo apt install openjdk-25-jdk
```

### Fedora {#fedora}

Você pode instalar o Java 25 usando `dnf` com os seguintes comandos:

```sh
sudo dnf install java-25-openjdk
```

Se você não precisa de uma IU gráfica, você pode usar a versão própria para isso:

```sh
sudo dnf install java-25-openjdk-headless
```

Se você planeja desenvolver "mods", você precisará do JDK:

```sh
sudo dnf install java-25-openjdk-devel
```

### Outras versões de Linux {#other-linux-distributions}

Se a sua distribuição não está listada acima, você pode baixar a versão mais recente do JRE pelo [Adoptium](https://adoptium.net/installation/linux)

Você deveria consultar um guia próprio para a sua versão, se você planeja desenvolver mods.

## 3. Verificar Se o Java 25 Está Instalado {#3-verify-that-java-is-installed}

Assim que a instalação estiver concluída, você pode verificar se o Java 25 está instalado abrindo um terminal e digitando `java-version`.

Se o comando for executado corretamente, você verá o que foi mostrado anteriormente, no local onde a versão do Java é mostrada:

![Terminal com "java -version" digitado](/assets/players/installing-java/linux-java-version.png)
