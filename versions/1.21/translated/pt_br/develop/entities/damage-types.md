---
title: Tipos de dano
description: Aprenda a adicionar tipos de dano personalizados.
authors:
  - dicedpixels
  - hiisuuii
  - mattidragon
---

# Tipos de Dano {#damage-types}

Os tipos de dano definem os tipos de dano que as entidades podem sofrer. A partir do Minecraft 1.19.4, a criação de novos tipos de dano passou a ser baseada em dados, o que significa que eles são criados usando arquivos JSON.

## Criando um Tipo de Dano

Vamos criar um tipo de dano customizado chamado _Tater_. Começaremos criando um arquivo JSON para seu dano. Este arquivo será colocado na pasta `data` do seu mod, em uma subpasta chamada `damage_type`.

```:no-line-numbers
resources/data/fabric-docs-reference/damage_type/tater.json
```

Ele terá a seguinte estrutura:

@[code lang=json](@/reference/latest/src/main/generated/data/fabric-docs-reference/damage_type/tater.json)

Esse tipo de dano causa aumento de 0.1 na [exaustão de fome](https://minecraft.wiki/w/Hunger#Exhaustion_level_increase) toda vez que um jogador sofrer dano, quando o dano for causado por uma fonte viva que não seja um jogador (ex: um bloco). Além disso, a quantidade de dano causado escalonará com a dificuldade do mundo.

::: info

Consulte a [Wiki do Minecraft](https://pt.minecraft.wiki/w/Tipo_de_dano) para todas as chaves e valores possíveis.

:::

### Acessando Tipos de Dano Através de Código

Quando precisarmos acessar nosso tipo de dano através de código, usaremos a sua `RegistryKey` (Chave de Registro) para construir uma instância de `DamageSource` (Fonte de Dano).

A `RegistryKey` pode ser obtida da seguinte maneira:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/FabricDocsReferenceDamageTypes.java)

### Usando Tipos de Dano {#using-damage-types}

Para demonstrar o uso de tipos de dano personalizados, usaremos um bloco personalizado chamado _Tater Block_. Façamos com que quando uma entidade viva pisar em um _Tater Block_, ele causará dano de _Tater_.

Você pode substituir `onSteppedOn` para infligir este dano.

Começaremos criando uma `DamageSource` do nosso tipo de dano customizado.

@[code lang=java transclude={21-24}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Então, chamamos `entity.damage()` com o nosso `DamageSource`e uma quantidade.

@[code lang=java transclude={25-25}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

A implementação completa do bloco:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Agora, quando uma entidade viva pisar no nosso bloco, ela sofrerá 5 de dano (2,5 corações) usando nosso tipo de dano personalizado.

### Mensagem de Morte Personalizada

Você pode definir uma mensagem de morte para o tipo de dano no formato de `death.attack.<message_id>` no arquivo `en_us.json` do mod.

@[code lang=json transclude={4-4}](@/reference/latest/src/main/resources/assets/fabric-docs-reference/lang/en_us.json)

Ao morrer devido ao nosso tipo de dano, você verá a seguinte mensagem:

![Efeito no inventário do jogador](/assets/develop/tater-damage-death.png)

### Tags de Tipo de Dano

Alguns danos podem ignorar a armadura, efeitos de estado, e similares. Tags (etiquetas) são usadas para controlar tais propriedades dos tipos de dano.

As tags de tipo de dano existentes se encontram em `data/minecraft/tags/damage_type`.

::: info

Consulte a [Wiki do Minecraft](https://pt.minecraft.wiki/w/Tipo_de_dano) para uma lista compreensiva de tags de tipos de dano.

:::

Vamos adicionar nosso dano Tater para a tag `bypasses_armor` (ignora armadura).

Para adicionar nosso dano a uma dessas tags, criamos um arquivo JSON sob o namespace de `minecraft`.

```:no-line-numbers
data/minecraft/tags/damage_type/bypasses_armor.json
```

Com o seguinte conteúdo:

@[code lang=json](@/reference/latest/src/main/generated/data/minecraft/tags/damage_type/bypasses_armor.json)

Certifique-se de que sua tag não substitua a tag existente definindo a chave `replace` como `false`.
