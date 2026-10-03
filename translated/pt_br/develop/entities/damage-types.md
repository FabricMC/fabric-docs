---
title: Tipos de dano
description: Aprenda a adicionar tipos de dano personalizados.
authors:
  - dicedpixels
  - hiisuuii
  - mattidragon
resources:
  https://minecraft.wiki/w/Damage_type: Tipos de Dano - Minecraft Wiki
  https://docs.neoforged.net/docs/resources/server/damagetypes/: Tipos de Dano e Fontes de Dano - Documentação do NeoForge (exceto datagen)
---

Os tipos de dano definem os tipos de dano que as entidades podem sofrer. A partir do Minecraft 1.19.4, a criação de novos tipos de dano passou a ser baseada em dados, o que significa que eles são criados usando arquivos JSON.

## Criando um Tipo de Dano

Vamos criar um tipo de dano customizado chamado _Tater_. Começaremos criando um arquivo JSON para seu dano. Este arquivo será colocado na pasta `data` do seu mod, em uma subpasta chamada `damage_type`.

```text:no-line-numbers
resources/data/example-mod/damage_type/tater.json
```

Ele terá a seguinte estrutura:

<<< @/reference/latest/src/main/generated/data/example-mod/damage_type/tater.json

Esse tipo de dano causa aumento de 0.1 na [exaustão de fome](https://minecraft.wiki/w/Hunger#Exhaustion_level_increase) toda vez que um jogador sofrer dano, quando o dano for causado por uma fonte viva que não seja um jogador (ex: um bloco). Além disso, a quantidade de dano causado escalonará com a dificuldade do mundo.

::: info

Consulte a [Wiki do Minecraft](https://pt.minecraft.wiki/w/Tipo_de_dano) para todas as chaves e valores possíveis.

:::

### Acessando Tipos de Dano Através de Código

Quando precisamos acessar os nossos tipos de dano personalizados pelo código, nós usaremos o seu `ResourceKey` para construir uma instância do `DamageSource`.

O `ResourceKey` pode ser obtido da seguinte maneira:

<<< @/reference/latest/src/main/java/com/example/docs/damage/ExampleModDamageTypes.java#damage_type

### Usando Tipos de Dano {#using-damage-types}

Para demonstrar o uso de tipos de dano personalizados, usaremos um bloco personalizado chamado _Tater Block_. Façamos com que quando uma entidade viva pisar em um _Tater Block_, ele causará dano de _Tater_.

Você pode sobrescrever `stepOn` para causar esse dano.

Começaremos criando uma `DamageSource` do nosso tipo de dano customizado.

<<< @/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java#create_damage_source

Então, nós chamamos `entity.hurtServer()` com o nível atual, o nosso `DamageSource` e uma quantia.

<<< @/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java#hurt_entity

A implementação completa do bloco:

<<< @/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java#complete_block

Agora, quando uma entidade viva pisar no nosso bloco, ela sofrerá 5 de dano (2,5 corações) usando nosso tipo de dano personalizado.

### Mensagem de Morte Personalizada

Você pode definir uma mensagem de morte para o tipo de dano no formato de `death.attack.<message_id>` no arquivo `en_us.json` do mod.

```json
{
  "death.attack.tater": "%1$s morreu por dano de Bolinho de Batata!"
}
```

Ao morrer devido ao nosso tipo de dano, você verá a seguinte mensagem:

![Efeito no inventário do jogador](/assets/develop/tater-damage-death.png)

### Tags de Tipo de Dano

Alguns tipos de danos podem ignorar armadura, efeitos de criaturas e coisas do gênero. Tags (etiquetas) são usadas para controlar tais propriedades dos tipos de dano.

As tags de tipo de dano existentes se encontram em `data/minecraft/tags/damage_type`.

::: info

Recorra à [Minecraft Wiki](https://minecraft.wiki/w/Damage_type_tag_(Java_Edition)) para uma lista compreensiva de tags de tipos de dano.

:::

Vamos adicionar nosso dano Tater para a tag `bypasses_armor` (ignora armadura).

Para adicionar nosso dano a uma dessas tags, criamos um arquivo JSON sob o namespace de `minecraft`.

```text:no-line-numbers
data/minecraft/tags/damage_type/bypasses_armor.json
```

Com o seguinte conteúdo:

<<< @/reference/latest/src/main/generated/data/minecraft/tags/damage_type/bypasses_armor.json

Certifique-se de que sua tag não substitua a tag existente definindo a chave `replace` como `false`.
