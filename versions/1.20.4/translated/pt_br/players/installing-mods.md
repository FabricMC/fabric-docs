---
title: Instalando Mods
description: Um guia passo a passo de como instalar mods para Fabric.
authors:
  - IMB11

search: false
---

# Instalando Mods

Este guia o orientará na instalação de mods para Fabric usando o Minecraft Launcher.

Para launchers de terceiros, você deve consultar suas devidas documentações.

## 1. Baixar o Mod

:::warning
Você deve baixar mods apenas de fontes que confia. Para mais informações sobre achar mods, veja o guia [Encontrando Mods Confiáveis](./finding-mods).
:::

A maioria dos mods necessitam do Fabric API, que pode ser baixado através do [Modrinth](https://modrinth.com/mod/fabric-api) ou [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api).

Ao baixar mods, certifique-se de que:

- Eles funcionam na versão do Minecraft que você quer jogar. Um mod feito para a versão 1.20, por exemplo, pode não funcionar na versão 1.20.2.
- Eles são para o Fabric, não outro mod loader (carregador de mods).
- Eles são feitos para a edição certa do Minecraft (Edição Java).

## 2. Mover o Mod para a Pasta `mods`

A pasta de mods pode ser encontrada nos seguintes locais para cada sistema operacional.

Você pode colar esses endereços na barra de endereços do seu navegador de arquivos para rapidamente achar a pasta.

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

Encontrada a pasta `mods`, você pode mover os arquivos `.jar` dos mods para dentro dela.

![Mods instalados na pasta de mods](/assets/players/installing-mods.png)

## 3. Tudo Pronto!

Assim que movidos os mods para a pasta `mods`, você pode abrir o Minecraft Launcher, selecionar o perfil Fabric através do botão no canto inferior esquerdo e clicar em jogar!

![Minecraft Launcher com o perfil Fabric selecionado](/assets/players/installing-fabric/launcher-screen.png)

## Solução de Problemas

Se você encontrar algum problema, sinta-se à vontade para pedir ajuda no [Discord do Fabric](https://discord.gg/v6v4pMv) no canal `#player-support`.

Você também pode tentar diagnosticar o problema por si mesmo através das páginas de solução de problemas:

- [Relatórios de Crash](./troubleshooting/crash-reports)
- [Upload de Logs](./troubleshooting/uploading-logs)
