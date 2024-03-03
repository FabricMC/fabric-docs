---
title: Instalar Java en Linux
description: Una guía paso a paso sobre como instalar Java en Linux.
authors:
  - IMB11
---

# Instalar Java en Linux

Esta guía te enseñará como instalar Java 17 en Linux.

## 1. Verifica si Java ya está instalado.

Abre una terminal, escribe `java -version`, y presiona <kbd>Enter</kbd>.

![Terminal con el comando "java -version" escrito.](/assets/players/installing-java/linux-java-version.png)

:::warning
Para jugar en la mayoría de las versiones modernas de Minecraft, necesitarás Java 17 como mínimo. Si este comando muestra una versión de Java menor a 17, necesitarás actualizar tu instalación de Java existente.
:::

## 2. Descargar e Instalar Java 17

Recomendamos usar OpenJDK 17, el cual está disponible en múltiples distribuciones de Linux.

### Arch Linux

:::info
Para más información sobre como instalar Java en Linux, visita la [Wiki de Arch Linux](https://wiki.archlinux.org/title/Java).
:::

Puedes instalar el JRE (Entorno de Ejecución de Java) desde los respositorios oficiales:

```bash
sudo pacman -S jre-openjdk
```

Si estás corriendo un servidor sin una interfaz gráfica, puedes instalar la versión "headless" (sin cabecera) en su lugar:

```bash
sudo pacman -S jre-openjdk-headless
```

Si planeas desarrollar mods, necesitarás el JDK (Entorno de Desarrollo de Java) en cambio:

```bash
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu

Puedes instalar Java 17 usando `apt` con los siguientes comandos:

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

### Fedora

Puedes instalar Java 17 usando `dnf` con los siguientes comandos:

```bash
sudo dnf install java-17-openjdk
```

Si no requieres de una interfaz gráfica, puedes instalar la versión "headless" en su lugar:

```bash
sudo dnf install java-17-openjdk-headless
```

Si planeas desarrollar mods, necesitarás el JDK en cambio:

```bash
sudo dnf install java-17-openjdk-devel
```

### Otras Distribuciones de Linux

Si tu distribución no fue mencionada arriba, puedes instalar el JRE más reciente desde [AdoptOpenJDK](https://adoptium.net/en-GB/temurin.html)

Puedes consultar otra guía para tu distribución si planeas desarrollar mods.

## 3. Verifica si Java 17 ya está instalado.

Una vez terminada la instalación, puedes verificar si Java 17 ya está instalado abriendo una terminal y escribiendo el comando `java -version`.

Si el comando corre exitosamente, deberías ver algo similar a lo mostrado antes, donde se muestra la versión de Java:

![Terminal con el comando "java -version" escrito.](/assets/players/installing-java/linux-java-version.png)
