---
title: Instalando Java no Windows
description: Um guia passo a passo de como instalar o Java no Windows.
authors:
  - IMB11
  - skycatminepokie
next: false
---

Esse guia lhe mostrará como instalar Java 25 no Windows.

O Launcher do Minecraft vem com sua própria instalação do Java, portanto esta seção só é relevante se você quiser usar o instalador baseado em `.jar` do Fabric ou se quiser usar o `.jar` do Servidor do Minecraft.

## 1. Verificar Se o Java Já Está Instalado

Para verificar se o Java já está instalado, você deve primeiro abrir o prompt de comando.

Você pode fazer isso pressionando <kbd>Windows</kbd>+<kbd>R</kbd> e digitando `cmd.exe` na caixa que aparecerá.

![Caixa de diálogo Executar do Windows com "cmd.exe" na barra de execução](/assets/players/installing-java/windows-run-dialog.png)

Após abrir o prompt de comando, digite `java -version` e pressione <kbd>Enter</kbd>.

Se o comando for executado com êxito, você verá algo parecido com isto. Se o comando falhar, prossiga para o próximo passo.

![Prompt de comando com "java -version" digitado](/assets/players/installing-java/windows-java-version.png)

::: warning

Para usar o Minecraft 26.1, você precisará pelo menos do Java 25 instalado.

Se esse comando mostrar qualquer versão inferior a 25, você precisará atualizar a sua instalação Java existente; continue lendo essa página.

:::

## 2. Baixar o instalador do Java 25 {#2-download-the-java-installer}

Para instalar o Java 25, você precisará baixar o instalador direto do [Adoptium](https://adoptium.net/temurin/releases?version=25&os=windows&arch=any&mode=filter).

Você precisará baixar a versão do `Windows Installer (.msi)`:

![Página para baixar o Adoptium com Windows Installer (.msi) highlighted](/assets/players/installing-java/windows-download-java.png)

Você deverá escolher `x86` se você tiver um sistema 32-bit ou `x64` se você tiver um sistema 64-bit.

A vasta maioria dos computadores modernos terão um sistema 64-bit. Se você não tiver certeza, tente baixar o instalador para 64-bit.

## 3. Execute o instalador! {#3-run-the-installer}

Siga as instruções no instalador para instalar o Java 25. Quando você chegar até essa página, você deve configurar os seguintes recursos para "Todos os recursos serão instalados no Disco Local":

- `Set JAVA_HOME environment variable` - Isso será adicionado ao diretório PATH.
- `JavaSoft (Oracle) registry keys`

![O instalador Java 25 "Set JAVA_HOME variable" e as chaves de registro "JavaSoft (Oracle)" destacadas](/assets/players/installing-java/windows-wizard-screenshot.png)

Uma vez que tenha feito isso, você pode clicar em `Next` e continuar com a Instalação.

::: warning

Nem sempre o Windows dirá para outros programas que o Java está instalado, pelo menos até que o computador seja reiniciado.

**Certifique-se de reiniciar o seu computador antes de continuar!**

:::

## 4. Verificar Se o Java 25 Está Instalado {#4-verify-that-java-is-installed}

Assim que a instalação terminar, você pode verificar se o Java 25 está instalado abrindo o prompt de comando novamente e digitando `java -version`.

Se o comando for executado corretamente, você verá o que foi mostrado anteriormente, no local onde a versão do Java é mostrada:

![Prompt de comando com "java -version" digitado](/assets/players/installing-java/windows-java-version.png)

Se você encontrar problemas, sinta-se a vontade para pedir ajuda em [Discord do Fabric](https://discord.fabricmc.net/) no canal `#player-support`.
