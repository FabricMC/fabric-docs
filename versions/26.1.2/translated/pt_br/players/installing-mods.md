---
title: Instalando Mods
description: Um guia passo a passo de como instalar mods para Fabric.
authors:
  - IMB11
---

Este guia o orientará na instalação de mods para Fabric usando o Minecraft Launcher.

Para launchers de terceiros, você deve consultar suas devidas documentações.

## 1. Baixar o Mod

::: warning

Você deve baixar mods apenas de fontes que confia. Para mais informações sobre como encontrar mods, veja o guia [Encontrando Mods Confiáveis](./finding-mods).

:::

A maioria dos mods requer Fabric API, o qual pode ser instalado por meio do [Modrinth](https://modrinth.com/mod/fabric-api) ou [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api).

Ao instalar mods, certifique-se de que:

- Eles funcionam na versão do Minecraft você quer jogar. Por exemplo, um mod feito para a versão 1.21.8, pode não funcionar na versão 1.21.11.
- São para Fabric, não outro loader.
- São para a edição correta do Minecraft (Java Edition).

## 2. Mova o Mod para a pasta `mods` {#2-move-the-mod-to-the-mods-folder}

A pasta mods pode ser encontrada nos seguintes locais para cada sistema operacional.

Você pode colar esses caminhos na barra de endereços do seu explorador de arquivos para rapidamente achar a pasta.

::: code-group

```text:no-line-numbers [Windows]
%appdata%\.minecraft\mods
```

```text:no-line-numbers [macOS]
~/Library/Application Support/minecraft/mods
```

```text:no-line-numbers [Linux]
~/.minecraft/mods
```

:::

Uma vez encontrada a pasta `mods`, você pode mover os arquivos `.jar` dos mods para dentro dela.

![Installed mods in the mods folder](/assets/players/installing-mods.png)

## 3. Pronto! {#3-you-re-done}

Uma vez que os mods foram movidos para a pasta `mods`, você pode abrir o Minecraft Launcher e selecionar o perfil do Fabric no menu suspenso no canto inferior esquerdo e clicar em jogar!

![Minecraft Launcher with Fabric profile selected](/assets/players/installing-fabric/launcher-screen.png)

## Solução de problemas {#troubleshooting}

Se encontrar algum problema enquanto segue o guia, você pode pedir ajuda no [Discord do Fabric](https://discord.fabricmc.net/) no canal `#player-support`.

Você também pode tentar resolver o problema por conta própria lendo as páginas de solução de problemas:

- [Crash Reports](./troubleshooting/crash-reports)
- [Uploading Logs](./troubleshooting/uploading-logs)
