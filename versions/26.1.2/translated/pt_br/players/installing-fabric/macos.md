---
title: Instalando o Fabric no macOS
description: Um guia passo a passo de como instalar o Fabric no macOS.
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info PRÉ-REQUISITOS

Você pode precisar [instalar o Java](../installing-java/macos) antes de executar o `.jar`.

:::

<!-- #region common -->

## 1. Baixar o instalador do Fabric {#1-download-the-fabric-installer}

Baixe a versão do `.jar` do instalador do Fabric direto do [Site do Fabric](https://fabricmc.net/use/), clicando em "Download installer (Universal/.JAR)".

## 2. Execute o Instalador do Fabric {#2-run-the-fabric-installer}

Feche o Minecraft e o Iniciador antes de executar o Instalador.

::: tip

Você pode receber um aviso de que a Apple não pode verificar o arquivo `.jar`. Para contorná-lo, abra "Ajustes do Sistema" > "Privacidade e Segurança", então, clique em `Abrir mesmo assim`. Confirme e digite sua Senha de Administrador, se for pedido.

![Ajustes do Sistema do macOS](/assets/players/installing-fabric/macos-settings.png)

:::

Assim que você abrir o instalador, você deveria ver uma tela como essa:

![O instalador do Fabric com "Install" destacado](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

Selecione a sua versão escolhida de Minecraft e clique em `Install`. Certifique-se de que o campo `Create Profile` está marcado.

### Faça a instalação via Homebrew {#installing-via-homebrew}

Se você já tiver o [Homebrew](https://brew.sh) instalado, você pode instalar o Fabric usando `brew`:

```sh
brew install fabric-installer
```

## 3. Finalize a instalação {#3-finish-setup}

Uma vez que a instalação esteja completa, abra o Iniciador do Minecraft. Então, selecione o perfil do Fabric no menu suspenso e clique em Jogar.

![O Iniciador de Minecraft com o perfil do Fabric selecionado](/assets/players/installing-fabric/launcher-screen.png)

Você pode agora, adicionar mods ao seu jogo. Veja o guia [Encontrando Mods Confiáveis](../finding-mods) para mais informações.

Se você encontrar problemas, sinta-se a vontade para pedir ajuda em [Discord do Fabric](https://discord.fabricmc.net/) no canal `#player-support`.
