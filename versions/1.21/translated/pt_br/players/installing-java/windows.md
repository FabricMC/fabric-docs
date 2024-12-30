---
title: Instalando Java no Windows
description: Um guia passo a passo de como instalar Java no Windows.
authors:
  - IMB11

search: false
---

# Instalando Java no Windows

Este guia o orientará na instalação do Java 17 no Windows.

O Launcher do Minecraft vem com sua própria instalação do Java, portanto esta seção só é relevante se você quiser usar o instalador baseado em `.jar` do Fabric ou se quiser usar o `.jar` do Servidor do Minecraft.

## 1. Verificar Se o Java Já Está Instalado

Para verificar se o Java já está instalado, você deve primeiro abrir o prompt de comando.

Você pode fazer isso apertando <kbd>Win</kbd> + <kbd>R</kbd> e digitando `cmd.exe` na janela aberta.

![Caixa de diálogo Executar do Windows com "cmd.exe" na barra de execução](/assets/players/installing-java/windows-run-dialog.png)

Após abrir o prompt de comando, digite `java -version` e pressione <kbd>Enter</kbd>.

Se o comando for executado com êxito, você verá algo parecido com isto. Se o comando falhar, prossiga para o próximo passo.

![Prompt de comando com "java -version" digitado](/assets/players/installing-java/windows-java-version.png)

:::warning
Para usar a maioria das versões modernas do Minecraft, você precisará ter pelo menos o Java 17 instalado. Se este comando exibir uma versão inferior a 17, será necessário atualizar sua instalação do Java atual.
:::

## 2. Baixar o Instalador do Java 17

Para instalar o Java 17, você precisará baixar o instalador pelo [Adoptium](https://adoptium.net/en-GB/temurin/releases/?os=windows&package=jdk&version=17).

Você deverá baixar a versão `Windows Installer (.msi)`:

![Página de download do Adoptium com Windows Installer (.msi) destacado](/assets/players/installing-java/windows-download-java.png)

Você deverá escolher 'x86' se tiver um sistema operacional 32-bit, ou 'x64' se tiver um sistema operacional 64-bit.

A maioria dos computadores modernos terão um sistema operacional 64-bit. Em caso de dúvida, tente usar o download 64-bit.

## 3. Executar o Instalador!

Siga os passos do instalador para instalar o Java 17. Ao chegar nesta página, você deve definir os seguintes recursos como "Todo o recurso será instalado no disco rígido local":

- `Set JAVA_HOME environment variable` - Isso será adicionado ou seu PATH.
- `JavaSoft (Oracle) registry keys`

![Instalador do Java 17 com "Set JAVA_HOME variable" e "JavaSoft (Oracle) registry keys" destacados](/assets/players/installing-java/windows-wizard-screenshot.png)

Assim que fizer isto, clique em 'Next' e continue com a instalação.

## 4. Verificar se o Java 17 está instalado

Assim que a instalação terminar, você pode verificar se o Java 17 está instalado abrindo o prompt de comando novamente e digitando `java -version`.

Se o comando for executado com êxito, você verá algo como mostrado anteriormente onde a versão do Java é exibida:

![Prompt de comando com "java -version" digitado](/assets/players/installing-java/windows-java-version.png)

---

Se você encontrar algum problema, sinta-se à vontade para pedir ajuda no [Discord do Fabric](https://discord.gg/v6v4pMv) no canal `#player-support`.
