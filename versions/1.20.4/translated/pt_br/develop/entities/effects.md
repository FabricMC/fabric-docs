---
title: Efeitos de Estado
description: Aprenda a adicionar efeitos de estado personalizados.
authors:
  - dicedpixels
  - YanisBft
  - FireBlast
  - Friendly-Banana
  - SattesKrokodil
authors-nogithub:
  - siglong
  - tao0lu

search: false
---

# Efeitos de Estado

Efeitos de estado, conhecidos como efeitos, são condições que podem afetar uma entidade. Eles podem ser de natureza positiva, negativa ou neutra. O jogo base aplica esses efeitos de vários modos, como comidas, poções, etc.

O comando `/effect` pode ser usado para aplicar efeitos numa entidade.

## Efeitos Personalizados

Neste tutorial adicionaremos um novo efeito personalizado chamado _Tater_, que lhe dará um ponto de experiência a cada tick do jogo.

### Estenda `StatusEffect`

Vamos criar uma classe de efeito personalizado estendendo `StatusEffect`, sendo uma classe base para todos os efeitos.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### Registrando seu Efeito Personalizado

Similar a registração de blocos e itens, usamos `Registry.register` para registrar nosso efeito ao registro de `STATUS_EFFECT`. Isso pode ser feito no nosso inicializador.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/FabricDocsReferenceEffects.java)

### Traduções e Texturas

Você pode atribuir um nome ao seu efeito e providenciar uma textura de ícone que aparecerá na tela de inventário do jogador.

#### Textura

O ícone de textura é um PNG de 18x18. Coloque seu ícone personalizado em:

```:no-line-numbers
resources/assets/fabric-docs-reference/textures/mob_effect/tater.png
```

![Efeito no inventário do jogador](/assets/develop/tater-effect.png)

#### Traduções

Assim como outras traduções, você pode adicionar uma entrada com o formato de ID `"effect.<mod-id>.<effect-identifier>": "Value"` ao arquivo de idioma.

::: code-group

```json[assets/fabric-docs-reference/lang/en_us.json]
{
  "effect.fabric-docs-reference.tater": "Tater"
}
```

### Testando

Uso o comando `/effect give @p fabric-docs-reference:tater` para dar ao jogador nosso efeito Tater.
Use `/effect clear @p fabric-docs-reference:tater` para remover o efeito.

::: info
Para criar uma poção que utiliza este efeito, consulte o [guia de Poções](../items/potions).
:::
