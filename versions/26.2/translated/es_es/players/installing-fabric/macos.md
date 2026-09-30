---
title: Instalar Fabric en macOS
description: Una guía paso a paso de cómo instalar Fabric en macOS.
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info PREREQUISITOS

Puede que debas [instalar Java](../installing-java/macos) antes de correr el `.jar`.

:::

<!-- #region common -->

## 1. Descargar el instalador de Fabric {#1-download-the-fabric-installer}

Descarga la versión `.jar` del instalador de Fabric del [sitio web de Fabric](https://fabricmc.net/use/), clicando en `Descargar instalador (Universal/.JAR)`.

## 2. Ejecutar el instalador de Fabric {#2-run-the-fabric-installer}

Cierra Minecraft y el Launcher de Minecraft antes de ejecutar el instalador.

::: tip

Puede que recibas un aviso de que Apple no pudo verificar el `.jar`. Para omitirlo, abre Ajustes del Sistema > Privacidad y Seguridad, y entonces haz click en `Abrir igualmente`. Confirma e introduce la contraseña del administrador si se solicitara.

![Ajustes del Sistema de macOS](/assets/players/installing-fabric/macos-settings.png)

:::

Tras abrir el instalador, deberías ver una pantalla como la siguiente:

![Instalador de Fabric con "Instalar" resaltado](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

Elige la versión de Minecraft que desees y haz clic en `Instalar`. Asegúrate de que `Create Profile` esté marcado.

### Instalar por medio de Homebrew {#installing-via-homebrew}

Si ya tienes [Homebrew](https://brew.sh) instalado, puedes instalar el Instalador de Fabric Installer usando `brew` como alternativa:

```sh
brew install fabric-installer
```

## 3. Finalizar Configuración {#3-finish-setup}

Una vez la instalación finalice, abre el Launcher de Minecraft. A continuación, selecciona el perfil de Fabric del desplegable de versiones y pulsa Jugar.

![Launcher de Minecraft con el perfil de Fabric seleccionado](/assets/players/installing-fabric/launcher-screen.png)

Ahora puedes añadir mods a tu partida. Ver la guía [Encontrar Mods Fiables](../finding-mods) para más información.

Si tienes algún problema, no dudes en pedir ayuda en el canal `#player-support` del [Discord de Fabric](https://discord.fabricmc.net/).
