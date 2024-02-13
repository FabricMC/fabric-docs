---
title: Instalar Mods
description: Una guía paso a paso sobre como instalar mods para Fabric.
authors:
  - IMB11
---

# Instalar Mods

Esta guía te enseñará sobre como instalar mods para Fabric usando el Launcher de Minecraft.

Para launchers o lanzadores de terceros, consulta su documentación.

## 1. Descarga el Mod

:::warning
Solo deberías descargar mods desde fuentes en las que confíes. Para más información sobre como encontrar mods, visita la guía sobre [Encontrar Mods](./finding-mods.md).
:::

La mayoría de los mods requieren Fabric API, el cual puede ser descargado desde [Modrinth](https://modrinth.com/mod/fabric-api) o [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api)

Cuando descargues mods, asegúrate que:

- Son compatibles con la versión de Minecraft en la que juegues. Por ejemplo, un mod que funcione en la versión 1.20, puede que no funcione en la versión 1.20.2.
- Son hechos para Fabric y no otro lanzador de mods (mod loader).
- Finalmente, que sean compatibles con la edición de Minecraft correcta (Edición de Java).

## 2. Mueve el archivo del mod al folder de `mods`.

El folder de mods puede ser encontrado en las siguientes localizaciones dependiendo de tu sistema operativo.

Usualmente puedes copiar y pegar estas direcciones de archivo en la barra de direcciones de tu explorador de archivos para encontrar el folder rápidamente.

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\mods
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/mods
```

```:no-line-numbers [Linux]
~/.minecraft/mods
```

:::

Una vez que hayas encontrado el folder de `mods`, puedes colocar el archivo `.jar` del mod ahí.

![Mods instalados en el folder de mods.](/assets/players/installing-mods.png)

## 3. ¡Hemos terminado!

¡Una vez que hayas movido los mods al folder de `mods`, puedes abrir el Launcher de Minecraft, seleccionar el perfil de Fabric desde el menú deslizador en la esquina inferior izquierda y presionar Jugar!

![Launcher de Minecraft con el perfil de Fabric seleccionado.](/assets/players/installing-fabric/launcher-screen.png)

## Solucionar Problemas

Si encuentras problemas tratando de seguir esta guía, puedes solicitar ayuda en el servidor de [Discord de Fabric](https://discord.gg/v6v4pMv), en el canal de `#player-support`.

También puedes intentar solucionar el problema tu mismo leyendo las guías sobre como solucionar problemas:

- [Reportes de Crasheo](./troubleshooting/crash-reports.md)
- [Subir Logs](./troubleshooting/uploading-logs.md)
