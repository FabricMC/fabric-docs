---
title: Subir Logs
description: Como subir logs para solucionar problemas.
authors:
  - IMB11
---

# Subir Logs

Durante la solución de problemas, muchas veces es necesario proveer los logs para ayudar a identificar la causa del problema.

## ¿Porqué debería subir los logs?

Subir los logs le permite a otros ayudarte a solucionar tus problemas más rápido comparado a simplemente pegar los logs en un chat o en una publicación de foro. También te permite compartir tus logs con otros sin tener que copiar y pegarlos.

Algunos sitios para pegar texto también remarcan la sintaxis del log, lo cual los hace más facil de leer, y pueden censurar información sensible, como tu nombre de usuario, o información de sistema.

## Reportes de Crasheos

Los reportes de crasheo son generados automáticamente cuando tu juego crashea. Solo contienen información sobre el crasheo, más no los logs enteros del juego. Están en el folder de `crash-reports` en el folder del juego.

Para más información sobre los reportes de crasheo, visita [Reportes de Crasheo](./crash-reports.md).

## Encontrar los Logs

Esta guía cubre el launcher oficial de Minecraft (comúnmente referido como el "launcher vanilla") - para launchers de terceros, puedes consultar su documentación.

Los logs se encuentran en el folder de `logs` en el folder del juego. El folder del juego puede ser encontrado en los siguientes lugares dependiendo de tu sistema operativo:

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft
```

```:no-line-numbers [Linux]
~/.minecraft
```

:::

Los logs más recientes se encuentran en el archivo `latest.log`, y los logs anteriores usan el siguiente patrón de nombramiento `aaa-mm-dd_numero.log.gz`.

## Subir Logs

Los logs pueden ser subidos en una variedad de servicios, como:

- [Pastebin](https://pastebin.com/)
- [GitHub Gist](https://gist.github.com/)
- [MCLogs](https://mclo.gs/)
