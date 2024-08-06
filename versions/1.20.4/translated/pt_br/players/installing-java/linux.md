---
title: Instalando Java no Linux
description: Um guia passo a passo de como instalar Java no Linux.
authors:
  - IMB11

search: false
---

# Instalando Java no Linux

Este guia o orientará na instalação do Java 17 no Linux.

## 1. Verificar Se o Java Já Está Instalado

Abra um terminal, digite `java -version`, e pressione <kbd>Enter</kbd>.

![Terminal com "java -version" digitado](/assets/players/installing-java/linux-java-version.png)

:::warning
Para usar a maioria das versões modernas do Minecraft, você precisará ter pelo menos o Java 17 instalado. Se este comando exibir uma versão inferior a 17, será necessário atualizar sua instalação do Java atual.
:::

## 2. Baixando e instalando Java 17

Recomendamos usar o OpenJDK 17, que está disponível para a maioria das distribuições Linux.

### Arch Linux

:::info
Para mais informações na instalação do Java no Arch Linux, veja a [Wiki do Arch Linux](https://wiki.archlinux.org/title/Java_(Portugu%C3%AAs)).
:::

Você pode instalar a JRE mais recente através de repositórios oficiais:

```sh
sudo pacman -S jre-openjdk
```

Se estiver rodando um servidor sem a necessidade de uma interface gráfica, você pode instalar a versão headless:

```sh
sudo pacman -S jre-openjdk-headless
```

Se você planeja desenvolver mods, precisará do JDK:

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu

Você pode instalar o Java 17 usando `apt` com os seguintes comandos:

```sh
sudo apt update
sudo apt install openjdk-17-jdk
```

### Fedora

Você pode instalar o Java 17 usando `dnf` com os seguintes comandos:

```sh
sudo dnf install java-17-openjdk
```

Se você não precisa de uma interface gráfica, você pode instalar a versão headless:

```sh
sudo dnf install java-17-openjdk-headless
```

Se você planeja desenvolver mods, precisará do JDK:

```sh
sudo dnf install java-17-openjdk-devel
```

### Outras distribuições Linux

Se sua distribuição não foi listada acima, você pode baixar a JRE mais recente pelo [Adoptium](https://adoptium.net/temurin/)

Você deve consultar um guia próprio de sua distribuição se planeja desenvolver mods.

## 3. Verificar se o Java 17 está instalado

Assim que a instalação terminar, você pode verificar se o Java 17 está instalado abrindo o terminal novamente e digitando `java -version`.

Se o comando for executado com êxito, você verá algo como mostrado anteriormente onde a versão do Java é exibida:

![Terminal com "java -version" digitado](/assets/players/installing-java/linux-java-version.png)
