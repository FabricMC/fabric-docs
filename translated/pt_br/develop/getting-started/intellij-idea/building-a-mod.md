---
title: Criando um Mod com IntelliJ IDEA
description: Aprenda como usar o IntelliJ IDEA para criar um mod de Minecraft que pode ser compartilhado ou testado em um ambiente de produção.
authors:
  - cassiancc
  - cputnam-a11y
  - gdude2002
  - Scotsguy
prev:
  text: Gerando Código-Fontes com IntelliJ IDEA
  link: ./generating-sources
next:
  text: Dicas e Truques no IntelliJ IDEA
  link: ./tips-and-tricks
---

No IntelliJ IDEA, abra a guia "Gradle" a direita e execute `build` em "tasks". Os JARs devem aparecer na pasta `build/libs` no diretório do seu projeto. Use o arquivo JAR com o nome mais curto fora do ambiente de desenvolvimento.

![A barra lateral do IntelliJ IDEA mostrando uma tarefa "build" destacada](/assets/develop/getting-started/build-idea.png)

![A pasta "build/libs" com os arquivos corrigidos destacados](/assets/develop/getting-started/build-libs.png)

## Instalando e Compartilhando {#installing-and-sharing}

Daqui em diante, o mod pode ser [instalado normalmente](../../../players/installing-mods), ou carregado a sites de hospedagem de mods confiáveis como o [CurseForge](https://www.curseforge.com/minecraft) e o [Modrinth](https://modrinth.com/discover/mods).
