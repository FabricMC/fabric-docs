---
title: Configurando tu entorno de desarrollo
description: Una guía paso a paso para como configurar un entorno de desarollo para crear mods usando Fabric.
authors:
  - IMB11
  - andrew6rant
  - SolidBlock-cn
  - modmuss50
  - daomephsta
  - liach
  - telepathicgrunt
  - 2xsaiko
  - natanfudge
  - mkpoli
  - falseresync
  - asiekierka
authors-nogithub:
  - siglong

search: false
---

# Configurando tu entorno de desarrollo

Para empezar a desarollar mods usando Fabric, necesitarás configurar un entorno de desarrollo usando IntelliJ IDEA.

## Instalar Java 17

Para desarrollar mods para Minecraft 1.20.4, necesitarás JDK 17.

Si necesitas ayuda instalando Java, puedes ver nuestras guías sobre la instalación de Java en la [sección de guías para jugadores](../../players/index)

## Instalando IntelliJ IDEA

:::info
Obviamente puedes usar otros IDEs, como Eclipse o Visual Studio Code, pero la mayoría de las páginas en esta documentación asumirán que estás usando IntelliJ IDEA - deberías visitar la documentación para tu IDE si estás usando alguno diferente.
:::

Si no tienes IntelliJ IDEA instalado, puedes descargarlo desde la [página web oficial](https://www.jetbrains.com/idea/download/) - sigue los pasos de instalación para tu sistema operativo.

La edición de Comunidad de IntelliJ IDEA es gratis y de fuente abierta, y es la versión recomendada para desarrollar mods con Fabric.

Puede que tengas que deslizar la página hacia abajo para encontrar los enlaces para descargar la edición de Comunidad - se ve de la siguiente manera:

![Sugerencia de descarga de IntelliJ edición de Comunidad](/assets/develop/getting-started/idea-community.png)

## Instalando _Plugins_ en IDEA

Aunque estos plugins no son estrictamente necesarios, hacen el desarrollo de mods con Fabric mucho más fácil - deberías considerar instalarlos.

### Minecraft Development

El plugin de Minecraft Development provee soporte para desarrollar mods con Fabric, y es el plugin más importante para instalar.

Puedes instalarlo abriendo IntelliJ IDEA, y luego navegando a `File > Settings > Plugins > Marketplace Tab` - busca `Minecraft Development` en la barra de búsqueda, y luego haz clic en el botón de `Install` (Instalar).

Alternativamente, puedes descargarlo desde la [página web del plugin](https://plugins.jetbrains.com/plugin/8327-minecraft-development) y luego puedes instalarlo navegando a `File > Settings > Plutins > Install Plugin from Disk`.
