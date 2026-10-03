---
title: Geração de Tradução
description: Um guia para configurar a geração de tradução com datagen.
authors:
  - CelDaemon
  - IMB11
  - MattiDragon
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
  - sjk1949
---

<!---->

:::info PRÉ-REQUISITOS

Tenha certeza que você completou o processo de [configuração do datagen](./setup) primeiro.

:::

## Configuração {#setup}

Primeiro, vamos criar o nosso **provedor**. Lembre-se, provedores são o que geram realmente dados para nós. Crie uma classe que estende `FabricLanguageProvider` e preencha os métodos base:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java#datagen_translations_provider

::: tip

Você precisará de um provedor diferente para cada idioma que você deseja gerar (por exemplo, um `ExampleEnglishLangProvider` e um `ExamplePirateLangProvider`).

:::

Para finalizar a configuração, adicione esse provedor ao seu `DataGeneratorEntrypoint` dentro do método `onInitializeDataGenerator`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_translations_register

## Criando Traduções {#creating-translations}

Além de criar traduções brutas, traduções dos `Identifier`s e copiar elas de um arquivo existente (passando um `Path`), existem métodos ajudantes para traduzir itens, blocos, tags, status, entidades, efeitos de mob, abas do modo criativo, atributos de entidade e encantamentos. Simplesmente chame `add` no `translationBuilder` com o que você quer traduzir e no que isso será traduzido:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java#datagen_translations_build

## Usando Traduções {#using-translations}

Traduções geradas tomam o lugar de várias traduções adicionadas em outros tutoriais, mas você pode também usá-las em qualquer lugar que você utilizar um objeto `Component`. No nosso exemplo, caso gostaríamos de permitir que pacotes de recursos traduzissem a nossa saudação, nós usamos `Component.translatable` no lugar de `Component.literal`:

```java
ChatComponent chatHud = Minecraft.getInstance().gui.getChat();
chatHud.addMessage(Component.literal("Hello there!")); // [!code --]
chatHud.addMessage(Component.translatable("text.example-mod.greeting")); // [!code ++]
```
