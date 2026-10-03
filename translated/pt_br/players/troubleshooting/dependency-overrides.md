---
title: Sobrescrita de Dependências
description: Um guia para sobrescrever dependências definidas no `fabric.mod.json` de um mod.
authors:
  - cassiancc
  - skycatminepokie
  - ytg1234
authors-nogithub:
  - kb1000
resources:
  https://semver.org/: Versionamento Semântico
---

<!---->

::: warning

As sobrescritas de Dependências são usadas para dar aos desenvolvedores de modpacks controle sobre seus mods. Isso não deve ser usado por jogadores normais.

É importante entender como os [campos de dependências são estruturados](../../develop/loader/fabric-mod-json#semantic-versioning) antes de continuar.

:::

Às vezes, ao montar um modpack, você pode encontrar mods com requerimentos de dependências inconvenientes - por exemplo, um mod pode ser muito rigoroso, requirindo o Minecraft `26.1`, apesar dele funcionar na versão `26.1.2`.

Para contrariar isso, o Fabric Loader permite que você sobrescreva os requerimentos de dependências, desse modo você pode tentar carregar um mod em uma versão de Minecraft para a qual ele não foi desenvolvido.

::: tip

Sobrescrever dependências deve ser apenas uma solução temporária se possível. Se o mod for ativamente mantido, considere reportar essa incompatibilidade no registro de erros e deixar os desenvolvedores originais resolverem o problema.

:::

## Configuração {#setup}

:::: info

Para este exemplo, nós vamos usar o seguinte `fabric.mod.json` para um mod com o id `example-mod`. A qualquer momento, você pode trocar a guia em um bloco de código para ver como a sobrescrita de dependência afeta este `fabric.mod.json`.

:::details `fabric.mod.json`

```json
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

::::

Primeiro, crie um arquivo nomeado `fabric_loader_dependencies.json` dentro da pasta `.minecraft/config`.

Em seguida, preenchemos o arquivo com o seguinte conteúdo padrão:

::: code-group

```json [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {} // [!code highlight]
  }
}
```

```json [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

Vamos analisar ele linha por linha.

Primeiro, nós temos `version`, que especifica a versão da especificação da sobrescrita de dependência que nós gostaríamos de usar. No momento da escrita dessa página, a versão mais recente é a versão `1`.

Segundo, nós temos um objeto `overrides` que conterá todas as nossas sobrescritas de dependências para vários mods. Para começar, ele inclui uma entrada vazia para `example-mod` para qual podemos adicionar sobrescritas de dependências.

As chaves dentro do objeto do mod pode ser um dos 5 tipos de dependências (`depends`, `recommends`, `suggests`, `conflicts`, `breaks`). O valor de qualquer uma daquelas chaves deve ser um objeto JSON. Este objeto JSON segue a mesma estrutura que um [`fabric.mod.json` objeto de dependência do](../../develop/loader/fabric-mod-json#semantic-versioning).

A chave pode ser opcionalmente prefixado com `+` ou `-` (por exemplo, `"+depends"`, `"-breaks"`).

::: tabs

== Prefixado com +

Se a chave é prefixada com `+`, as entradas dentro daquele objeto JSON serão adicionadas (ou sobrescritas caso já existirem) ao mod.

```json{5}
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "+depends": {
        "minecraft": ""
      }
    }
  }
}
```

== Prefixado com -

Se a chave é prefixada com `-`, o valor de cada entrada é ignorada completamente e o Fabric Loader removerá essas entradas do mapa de dependências resultante.

```json{5}
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "-depends": {
        "minecraft": ""
      }
    }
  }
}
```

== Sem um Prefixo

Se a chave não é prefixada, o objeto de dependência será substituído completamente. **Tenha cautela ao prefixar suas chaves!**

```json{5}
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "depends": {
        "minecraft": ""
      }
    }
  }
}
```

:::

## Sobrescrevendo Dependências {#overriding-dependencies}

Vamos presumir que um mod com o ID `example-mod` depende **exatamente** da versão do Minecraft `26.1`, mas nós queremos que ele funcione nas outras versões 26.1. Vamos ver como podemos fazer isso:

::: code-group

```json{5-6} [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "depends": {
        "minecraft": "26.1.x"
      }
    }
  }
}
```

```json{2,5-6} [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1.x"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

Agora uma dependência `"minecraft"` será sobrescrita se especificada (e nós sabemos ser). Há outra forma de fazer isso:

::: code-group

```json{5-6} [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "-depends": {
        "minecraft": "IGNORED"
      }
    }
  }
}
```

```json{2,5-6} [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1.x"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

Como especificado acima, o valor da chave `"minecraft"` será ignorado ao remover as dependências. Se uma dependência com um requisito de ID de mod `minecraft` é encontrada, ela será removida do nosso mod alvo `example-mod`.

Nós também podemos sobrescrever inteiramente o bloco `depends`, mas com grandes poderes vem grandes responsabilidades. Tenha cautela.

Além de mudar a dependência `minecraft`, nós também queremos remover todas as dependências `suggests`. Nós podemos fazer isso removendo o prefixo da chave `suggests`, o que a substitui com um objeto vazio, essencialmente limpando seu conteúdo. Isso ficaria assim:

::: code-group

```json [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "-depends": {
        "minecraft": ""
      },
      "suggests": {} // [!code highlight]
    }
  }
}
```

```json [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {} // [!code highlight]
}
```

:::

<!---->
