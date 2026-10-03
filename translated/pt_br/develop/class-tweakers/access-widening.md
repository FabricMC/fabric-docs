---
title: Access Widening
description: Aprenda a como usar access wideners dos arquivos de modificadores de classe.
authors-nogithub:
  - lightningtow
  - siglong
authors:
  - An-m1654
  - Ayutac
  - cassiancc
  - CelDaemon
  - cootshk
  - Earthcomputer
  - florensie
  - froyo4u
  - haykam821
  - hYdos
  - its-miroma
  - kb-1000
  - kcrca
  - liach
  - lmvdz
  - matjojo
  - MildestToucan
  - modmuss50
  - octylFractal
  - OroArmor
  - T3sT3ro
  - Technici4n
  - TheGlitch76
  - UpcraftLP
  - YTG1234
---

Access Widener é um tipo de [modificador de classe](../class-tweakers)] usado para flexibilizar o limite de acesso a classes, métodos e campos, refletindo essa mudança no código-fonte descompilado.
Isso inclui tornando-os públicos, estendível e/ou mutável.

Entradas de access widener podem ser [transitivas](../class-tweakers/index#transitive-entries) para fazer mudanças visíveis aos mods dependendo do seu mod.

Para acessar campos ou métodos, pode ser mais seguro e simples usar [mixins](../mixins/accessors), mas existem duas situações onde accessadores são insuficientes e access wideners são necessários:

- Se você precisa acessar uma classe `private`, `protected` ou package-private
- Se você precisa sobrescrever um método `final` ou criar uma subclasse da classe `final`

No entanto, ao contrário dos [acessores de mixin](https://wiki.fabricmc.net/tutorial:mixin_accessors), [modificadores de classe](../class-tweakers) apenas funcionam em classes do Minecraft Vanilla, e não em outros mods.

## Access Directives {#access-directives}

As entradas de access widener começam com um das três palavra-chaves das diretivas básicas para especificar o tipo de modificação que deve ser aplicada.

Depois da palavra-chave vem os parâmetros, normalmente os alvos do widening.

A mesma classe, método ou campo podem ser alvos de múltiplas entradas de access widening, uma em cada linha.

Access directives também podem ser tornadas [transitivas](../class-tweakers/index#transitive-entries) ao adicionar o prefixo `transitive-` antes da base do access directive.

### Accessible {#accessible}

`accessible` pode ter como alvo classes, métodos e campos:

- Classes e Campos são tornados públicos.
- Métodos são tornados públicos e finais se eram originalmente privados.

Ao tornar um método ou campo acessível, a sua classe também é tornada acessível.

### Extendable {#extendable}

`extendable` pode ter como alvo apenas classes e métodos:

- Classes são tornados públicas e não-finais.
- Métodos são tornados não-finais.

Ao criar um método estendível, a sua classe também é tornada estendível.

### Mutable {#mutable}

`mutable` pode tornar um campo não-final.

## Especificando Alvos {#specifying-targets}

Para modificadores de classe, classes usam os [nomes internos](../mixins/bytecode#class-names). Para campos e métodos, você deve especificar os nomes de suas classes, os seus nomes e os seus [descritores de bytecode](../mixins/bytecode#field-and-method-descriptors).

::: tabs

== Classes

Formato:

```classtweaker:no-line-numbers
<accessible / extendable>    class    <className>
```

Exemplo:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#accesswidening_examples_classes

== Métodos

Formato:

```classtweaker:no-line-numbers
<accessible / extendable>    method    <className>    <methodName>    <methodDescriptor>
```

Exemplo:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#accesswidening_examples_methods

== Campos

Formato:

```classtweaker:no-line-numbers
<accessible / mutable>    field    <className>    <fieldName>    <fieldDescriptor>
```

Exemplo:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#accesswidening_examples_fields

:::

## Gerando Entradas {#generating-entries}

Escrever entradas de access wideners manualmente gasta muito tempo, além de ser sujeito a erros humanos. Vamos dar uma olhada nas ferramentas que simplificam parte do processo ao te permitir gerar e copiar entradas.

### mcsrc.dev {#mcsrc-dev}

[mcsrc](https://mcsrc.dev) permite que você descompile e navegue na fonte do Minecraft pelo navegador e copie Mixin ou alvos do access transformer para a área de transferência.

Para copiar uma entrada de access widerer, primeiro navegue até a classe que você quer modificar, clique com o botão direito do mouse no seu alvo para abrir um menu pop-up.

![Clicando com o botão direito no mcsrc](/assets/develop/class-tweakers/access-widening/mcsrc-right-click-on-aw-target.png)

Então, clique no `Copy Class Tweaker / Access Widener`, uma confirmação deve aparecer no topo da página.

![Confirmação de cópia do AW no mcsrc](/assets/develop/class-tweakers/access-widening/mcsrc-aw-copy-confirmation.png)

Você pode então colar a entrada no seu arquivo de modificador de classe.

### Plugin de Desenvolvimento do Minecraft (IntelliJ IDEA) {#mcdev-plugin}

[Plugin de Desenvolvimento do Minecraft](../getting-started/intellij-idea/setting-up#installing-idea-plugins), também conhecido como MCDev, é um plugin do IntelliJ IDEA que ajuda em vários aspectos do desenvolvimento de mods do Minecraft.
Por exemplo, ele te permite copiar entradas de access widener do código-fonte descompilado alvo para a área de transferência.

Para copiar uma entrada de access widerer, primeiro navegue até a classe que você quer modificar, clique com o botão direito do mouse no seu alvo para abrir um menu pop-up.

![Clicando com o botão direito em um alvo com MCDev](/assets/develop/class-tweakers/access-widening/mcdev-right-click-on-aw-target.png)

Então, clique em `Copy / Paste Special` e `AW Entry`.

![Copy/Paste special com MCDev](/assets/develop/class-tweakers/access-widening/mcdev-copy-paste-special-menu.png)

Agora uma confirmação deve aparecer no elemento que você clicou com o botão direito.

![Confirmação da cópia do AW com MCDev](/assets/develop/class-tweakers/access-widening/mcdev-aw-copy-confirmation.png)

Você pode então colar a entrada no seu arquivo de modificador de classe.

## Aplicando Mudanças {#applying-changes}

Para ver as suas mudanças aplicadas, você deve recarregar o seu projeto do Gradle e [regenerar fontes](../getting-started/generating-sources). Os elementos que você tinha como alvo devem ter os seus limites de acesso modificados de acordo. Tenha certeza de reabrir qualquer classe-alvo do código-fonte descompilado para ver as modificações.

::: tip

Se as modificações não aparecem, você pode tentar [validar o arquivo](../class-tweakers/index#validating-the-file) e verificar se algum erro aparece.

:::

<!---->
