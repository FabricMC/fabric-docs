---
title: Modificadores de Classes
description: Aprenda o que são modificadores de classe e como configurá-los.
authors:
  - cassiancc
  - Earthcomputer
  - its-miroma
  - MildestToucan
---

Modificadores de classes, anteriormente conhecidos como alargadores de acesso antes de ganhar mais funcionalidades, fornecem ferramentas de transformação complementares a manipulação de bytecode com Mixin. Eles também permitem que algumas modificações em tempo de execução se tornem acessíveis dentro do ambiente de desenvolvimento.

::: warning

Modificadores de classes não são exclusivos de uma versão do Minecraft específica, mas estão apenas disponíveis para Fabric Loader 0.18.0 e Loom 1.12 ou superior, e podem ter como alvo apenas classes do Minecraft Vanilla.

:::

## Configuração {#setup}

### Formato do Arquivo {#file-format}

Os arquivos dos modificadores de classe são convencionalmente nomeados conforme o seu modid, `example-mod.classtweaker`, para ajudar os plugins IDE a reconhecê-los. Eles devem ser armazenados em `resources`.

O arquivo deve ter o seguinte cabeçalho como a sua primeira linha:

```classtweaker
classTweaker  v1  official
```

Alguns recursos podem exigir uma versão maior que `v1` - que será mencionada em suas respectivas páginas.

Os arquivos do modificador de classe podem ter linhas em branco e comentários começando com `#`. Comentários podem começar no final de uma linha.

A síntaxe pode variar conforme o recurso utilizado, mas cada modificação é declarada como `entries` em linhas separadas e começa com um "diretivo" especificando o tipo de modificação a aplicar.
Os elementos de uma entrada podem ser separadas usando qualquer espaço em branco, incluindo abas.

#### Entradas Transitivas {#transitive-entries}

Para fazer com que as suas mudanças ao código-fonte descompilado seja visível aos mods que dependem delas, prefixe o diretivo com `transitive-`:

```classtweaker:no-line-numbers
# Transitive Access Widening directives
transitive-accessible
transitive-extendable
transitive-mutable

# Transitive Interface Injection directive
transitive-inject-interface

# Transitive Enum Extension directive
transitive-extend-enum
```

### Especificando a Localização do Arquivo {#specifying-the-file-location}

A localização do arquivo do modificador de classe deve ser especificado nos seus arquivos `build.gradle` e `fabric.mod.json`. Não esqueça que você deve também depender do Fabric Loader 0.18.0 ou superior para usar modificadores de classe.

As especificações estão ainda nomeadas conforme os alargadores de acesso para preservar a compatibilidade retroativa.

#### build.gradle {#build-gradle}

<<< @/reference/latest/build.gradle#classtweaker_setup_gradle

#### fabric.mod.json {#fabric-mod-json}

```json:no-line-numbers
...

"accessWidener": "example-mod.classtweaker",

...
```

Após especificar a localização do arquivo no seu arquivo `build.gradle`, tenha certeza de recarregar o seu projeto Gradle no IDE.

## Validando o Arquivo {#validating-the-file}

Por padrão, modificadores de classe ignorarão entradas referenciando modificações alvo que não podem ser encontradas. Para verificar se todas as classes, campos e métodos especificados no arquivo são válidos, execute a tarefa do Gradle `validateAccessWidener`.

Erros irão apontar qualquer entrada inválida, porém eles podem ser não específicos sobre qual parte de uma entrada é inválida.
