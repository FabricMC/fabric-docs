---
title: Instalar Java en Windows
description: Una guía paso a paso sobre como instalar Java en Windows.
authors:
  - IMB11
---

# Instalar en Java en Windows

Esta guía te enseñará como instalar Java 17 en Windows.

El launcher de Minecraft viene con su propia instalación de Java, por lo que esta sección solo es relevante si quieres usar el instalador de Fabric `.jar`, o si quieres usar el `.jar` del Servidor de Minecraft.

## 1. Verifica si Java ya está instalado.

Para comprobar si Java ya está instalado, primero debes abrir la línea de comandos.

Puedes abrirla presionando <kbd>Win</kbd> + <kbd>R</kbd> y escribiendo `cmd.exe` en la caja de texto que aparece.

![Diálogo de Ejecución de Windows con "comd.exe" en la barra de ejecución](/assets/players/installing-java/windows-run-dialog.png)

Una vez abierta la línea de comandos, escribe `java -version` y presiona <kbd>Enter</kbd>.

Si el comando corre exitosamente, verás algo similar a esto. Si el comando falla, procede al siguiente paso.

![Línea de comandos con "java -version" escrito.](/assets/players/installing-java/windows-java-version.png)

:::warning
Para usar la mayoría de las versiones modernas de Minecraft, necesitarás Java 17 instalado como mínimo. Si el comando muestra cualquier versión menor a 17, necesitarás actualizar tu instalación de Java existente.
:::

## 2. Descarga el instalador de Java 17.

Para instalar Java 17, debes descargar el instalar desde [Adoptium](https://adoptium.net/en-GB/temurin/releases/?os=windows\&package=jdk\&version=17).

Querrás descargar la versión `Windows Installer (.msi)`:

![Página de descargas de Adoptium con el Windows Installer (.msi) remarcado](/assets/players/installing-java/windows-download-java.png)

Escoge `x86` si tienes un sistema operativo de 32 bits, o `x64` si tienes un sistema operativo de 64 bits.

La mayoría de las computadoras modernas tienen un sistema operativo de 64 bits. Si no estás seguro, intenta con la descarga de 64 bits.

## 3. ¡Corre el instalador!

Sigue los pasos del instalador para instalar Java 17. Cuando llegues a esta página, debes elegir la opción de "La funcionalidad entera será instalada en el disco duro local" para las siguientes funcionalidades:

- `Set JAVA_HOME environment variable` - Esto será agregado a tu PATH.
- `JavaSoft (Oracle) registry keys`

![Instalador de Java 17 con "Set JAVA\_HOME variable" y "JavaSoft (Oracle) registry keys" remarcados.](/assets/players/installing-java/windows-wizard-screenshot.png)

Una vez terminado, puedes hacer clic en `Next` y continuar con la instalación.

## 4. Verificar si Java 17 ya está instalado.

Una vez terminada la instalación, puedes verificar si Java 17 está instalado abriendo la línea de comandos de nuevo y escribiendo `java -version`.

Si el comando corre exitosamente, verás algo similar a lo mostrado antes, donde la versión de Java se muestra:

![Línea de comandos con "java -version" escrito.](/assets/players/installing-java/windows-java-version.png)

***

Si encuentras problemas, puedes pedir ayuda en el canal de `#player-support` en el servidor de [Discord de Fabric](https://discord.gg/v6v4pMv).
