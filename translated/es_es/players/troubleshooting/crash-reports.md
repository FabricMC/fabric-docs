---
title: Reportes de Crasheos
description: Aprender sobre que hacer con los reportes de crasheo, y como leerlos.
authors:
  - IMB11
---

# Reportes de Crasheos

:::tip
Si estás teniendo dificultades encontrando la causa del crasheo, puedes pedir ayuda en el servidor de [Discord de Fabric](https://discord.gg/v6v4pMv), en el canal de `#player-support` o `server-admin-support`.
:::

Los reportes de crasheo son importantes para la solución de problemas con tu juego o servidor. Contienen mucha información sobre el crasheo, y pueden ayudarte a encontrar la causa del crasheo.

## Encontrar los Reportes de Crasheo

Los reportes de crasheo se encuentran en el folder de `crash-reports` en el folder de tu juego. Si estás usando un servidor, se encuentran en el folder de `crash-reports` en el folder del servidor.

Para launchers de terceros, puedes referirte a su documentación sobre donde encontrar los reportes de crasheo.

Los reportes de crasheo pueden ser encontrados en los siguientes lugares:

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\crash-reports
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/crash-reports
```

```:no-line-numbers [Linux]
~/.minecraft/crash-reports
```

:::

## Leer los Reportes de Crasheo

Los reportes de crasheo son muy largos, y pueden ser confusos de leer. Sin embargo, contienen mucha información sobre el crasheo, y pueden ayudarte a encontrar la causa del crasheo.

Para esta guía, estaremos utilizando el [siguiente reporte de crasheo como ejemplo.](https://github.com/FabricMC/fabric-docs/blob/main/public/assets/players/crash-report-example.txt)

### Secciones del Reporte de Crasheo

Los reportes de crasheo consisten de varias secciones, cada una separada por un encabezado:

- `---- Minecraft Crash Report ----`, el resumen del reporte. Esta sección contiene el error principal que ocasionó el crasheo, el tiempo en el que ocurrió, y el stack trace relevante. Esta es la sección más importante del reporte de crasheo, ya que el stack trace usualmente contiene referencias al mod que casuó el crasheo.
- `-- Last Reload --`, esta sección no es muy útil almenos que el crasheo ocurrió durante una recarga de recursos (<kbd>F3</kbd>+<kbd>T</kbd>). Esta sección contiene el tiempo en el que se hizo la última recarga de recursos, y el stack trace relevante de errores que ocurrieron durante el proceso de recarga. Estos errores usualmente son causados por paquetes de recursos, y pueden ser ignorados al menos que estén causando problemas en el juego.
- `-- System Details --`, esta sección contiene información sobre tu sistema, como el sistema operativo, la versión de Java, y la cantidad de memoria RAM alocada para el juego. Esta sección es útil para determinar si estás usando la versión correcta de Java, y si tienes suficiente memoria alocada para el juego.
  - En esta sección, Fabric incluye una línea personalizada que dice `Fabric Mods:`, seguida de una lista de los mods que tienes instalados. Esta sección es útil para determinar si pudo haber ocurrido algún conflicto entre los mods que tienes instalados.

### Entendiendo el Reporte de Crasheo

Ahora que sabemos de qué se trata cada sección del reporte de crasheo, podemos empezar a analizar y entender el reporte de crasheo y encontrar la causa del crasheo.

Usando el ejemplo enlazado arriba, podemos analizar el reporte de crasheo y encontrar la causa del crasheo, incluyendo los mods que causaron el crasheo.

El stack trace en la sección de `---- Minecraft Crash Report ----` es la más importante en este caso, ya que contiene el error principal que causó el crasheo. En este caso, el error es `java.lang.NullPointerException: Cannot invoke "net.minecraft.class_2248.method_9539()" because "net.minecraft.class_2248.field_10540" is null`.

Con la cantidad de mods mencionados en el stack trace, puede ser difícil encontrar el mod culpable, pero lo primero que se debe hacer es encontrar el mod que causó el crasheo.

```:no-line-numbers
at snownee.snow.block.ShapeCaches.get(ShapeCaches.java:51) 
at snownee.snow.block.SnowWallBlock.method_9549(SnowWallBlock.java:26) // [!code focus]
...
at me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockOcclusionCache.shouldDrawSide(BlockOcclusionCache.java:52)
at link.infra.indium.renderer.render.TerrainBlockRenderInfo.shouldDrawFaceInner(TerrainBlockRenderInfo.java:31)
...
```

En este caso, el mod que causó el crasheo es `snownee`, ya que es el primer mod mencionado en el stack trace.

Sin embargo, con la cantidad de mods mencionados, puede significar que hay problemas de compatibiildad entre los mods, y el mod que causó el crasheo puede que no sea el mod culpable. En este caso, es mejor reportar el crasheo al autor del mod, y dejar que investiguen el crasheo.

## Crasheos de Mixin

:::info
Los Mixins son una manea de modificar el juego sin tener que modificar el código fuente del juego. Son usados por muchos mods, y son una herramienta muy útil para desarrolladores de mods.
:::

Cuando un mixin crashea, usualmente mencionará el mixin en el stack trace, y la clase que el mixin está modificando.

Los métodos en un mixin contendrán `modid$handlerName` en el stack trace, donde `modid` es el ID del mod, y `handlerName` es el nombre del handler en el mixin.

```:no-line-numbers
... net.minecraft.class_2248.method_3821$$$modid$handlerName() ... // [!code focus]
```

Puedes usar esta información para encontrar el mod que ocasionó el crasheo, y reportar el crasheo al autor del mod.

## Que hacer con los Reportes de Crasheo

Lo mejor que puedes hacer con los reportes de crasheo es subirlos a un sitio para pegar texto, y luego compartir el link con el autor del mod, ya sea en su rastreador de problemas o mediante un canal de comunicación (Discord etc).

Esto le permitirá al autor investigar el crasheo, potencialmente reproducir el problema, y arreglarlo.

Algunos sitios comunes para pegar texto de reportes de crasheo son:

- [GitHub Gist](https://gist.github.com/)
- [Pastebin](https://pastebin.com/)
- [MCLogs](https://mclo.gs/)
