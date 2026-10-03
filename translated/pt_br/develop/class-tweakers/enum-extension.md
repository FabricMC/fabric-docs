---
title: Extensão de Enum
description: Aprenda a como adicionar entradas aos Enums com Mixin e Modificadores de Classes.
authors:
  - cassiancc
  - CelDaemon
  - its-miroma
  - Jab125
  - LlamaLad7
  - MildestToucan
---

Extensão de Enum é um recurso do Mixin que pode confiavelmente adicionar novas entradas a um Enum.

Ao ter Enums do Minecraft como alvo, você pode usar Mixins com [modificadores de classes](../class-tweakers) para exibir novas entradas de Enum no código-fonte descompilado. Se isso for configurado para ser [transitivo](../class-tweakers/index#transitive-entries), mods que dependem do seu também verão as entradas adicionadas.

::: warning

Extensão de Enum requer pelo menos Loader 0.19.0 para suporte de Mixin e pelo menos Loom 1.16 para suporte de modificador de classe.

Além disso, os cabeçalhos dos arquivos de modificadores de classe devem especificar `v2` como a versão para usar extensões de Enum.

:::

## Criando o Mixin {#creating-the-mixin}

Antes de criar a classe mixin, certifique-se que o Loader 0.19.0 ou superior é uma dependência explicita no seu arquivo `fabric.mod.json`:

```json:no-line-numbers
...
"depends": {
  ...
  "fabricloader": ">=0.19.0"
  ...
}
...
```

Ainda se você estiver usando a versão correta como uma dependência do Gradle, você deve explicitamente depender da versão 0.19.0 ou superior para habilitar esse recurso de Mixin.

Para criar uma extensão de Enum, crie um `enum` no seu pacote de mixin, anote-o com `@Mixin` e adicione suas constantes a ele como se eles fossem parte da classe de Enum-alvo. Por exemplo, vamos adicionar uma nova entrada à `RecipeBookType`:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/RecipeBookTypeMixin.java#enum_extension_no_impls_example_mixin

:::warning IMPORTANTE

Você sempre deve prefixar as contantes de Enum que você adiciona com o ID do seu mod para garantir unicidade. Para esses documentos, nós usaremos `EXAMPLE_MOD_`.

:::

### Passando Argumentos de Construtor {#passing-constructor-arguments}

Se o Enum alvo não possui um construtor padrão, você deve fazer um shadow do construtor da classe-alvo e passar os argumentos necessários para a sua declaração de entrada adicionada.

Por exemplo, vamos adicionar uma nova entrada `RecipeCategory`. Crie um construtor que combina com o construtor desejado na classe-alvo e anote-o com `@Shadow`.

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/RecipeCategoryMixin.java#enum_extension_ctor_impls_example_mixin

### Implementando Métodos Abstratos {#implementing-abstract-methods}

Para implementar um método-alvo abstrato de Enum, faça um shadow do método abstrato, e então sobrescreva-o e implemente-o na sua entrada adicionada. Por exemplo, vamos adicionar uma nova entrada `ConversionType:`:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/ConversionTypeMixin.java#enum_extension_abstract_method_impls_example_mixin

### Acessando o Ordinal do Enum Atual {#accessing-current-enum-ordinal}

Você pode precisar pegar o ordinal da sua entrada Enum adicionada para passá-la a um construtor. Para fazer isso, o Mixin fornece o método `MixinIntrinsics.currentEnumOrdinal()`, que retorna o índice correto enquanto considera as contribuições dos outros mods.

Como um exemplo, vamos criar um mixin para o `IllagerSpell` da versão vanilla e adicionar um feitiço, cuja ordinal é passada como o primeiro argumento do construtor:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/IllagerSpellMixin.java#enum_extension_current_enum_ordinal_example_mixin

Agora você pode ter certeza que `currentEnumOrdinal()` retornará o índice correto, mesmo se outro mod também estendesse esse mesmo Enum.

## Criando a Entrada do Modificador de Classe {#making-the-class-tweaker-entry}

Se você está tendo como alvo um Enum do Minecraft, você pode usar uma entrada de modificador de classe para visivelmente modificar o Enum alvo no código-fonte descompilado.

Para optar usar esse recurso, lembre-se de usar Loom 1.16 ou superior e de definir a [versão do cabeçalho do arquivo](../class-tweakers/index#file-format) para `v2`.

A sintaxe para uma entrada de extensão de Enum é:

```classtweaker:no-line-numbers
extend-enum  <targetClassName>  <ENUM_CONSTANT_NAME>
```

Para modificação de classe, as classes usam os seus [nomes internos](../mixins/bytecode#class-names).

Por exemplo, o modificador de classe para a contante `RecipeBookType` que nós adicionamos na [sessão de mixin](#creating-the-mixin) seria:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#enum_extension_no_impls_example_entry{classtweaker:no-line-numbers}

## Aplicando Mudanças {#applying-changes}

Para visualizar as entradas de Enum adicionadas no código-fonte descompilado, você deve recarregar o seu projeto do Gradle e [regenerar fontes](../getting-started/generating-sources).
Tenha certeza de reabrir qualquer classe-alvo do código-fonte descompilado para ver as modificações.

::: tip

Se as modificações não aparecem, você pode tentar [validar o arquivo](../class-tweakers/index#validating-the-file) e checar se algum erro aparece.

:::

<!---->

::: info

Você não verá [argumentos de construtor passados](#passing-constructor-arguments), [implementação de métodos](#implementing-abstract-methods) ou outros elementos no código-fonte descompilado.
Isso porque esses são tratados pelo mixin e são apenas aplicados durante o runtime.

:::

Você agora pode usar a contante de Enum no seu código:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum_extension_added_constant_usage_example

Se você está apenas a adicionando com um mixin e ela não está no código-fonte descompilado, você pode verificar comparando os nomes:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum_extension_added_constant_no_ct_usage_example_check

Se você precisa usar a constante em múltiplas áreas, obtenha ela ao chamar `valueOf` e armazenando o resultado em um campo:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum_extension_added_constant_no_ct_usage_example_store

## Armadilhas Comuns {#pitfalls}

Extensões de Enum não podem garantir que as entradas que você adiciona não quebrarão nada.

É a sua responsabilidade revisar os usos do Enum-alvo e tentar prevenir problemas quando possível. Se você está incapaz de resolver algum, e ocorrem travamentos, pode ser melhor não usar nenhuma extensão de Enum.

Essa sessão fala sobre padrões para ficar de olho e evitar ao estender Enums, mas isso não é exaustivo.

### Trocar Expressões {#switch-expressions}

Instruções switch são frequentemente usados para lidar com constantes de Enum. Por conta disso, um travamento pode acontecer se uma expressão switch não lida com entradas adicionadas por outros mods. Por exemplo, suponha que nós temos a seguinte expressão switch:

<<< @/reference/latest/src/main/java/com/example/docs/enum_extension/ExampleModEnumExtension.java#enum_extension_problematic_switch_expr_example

Note como não há uma clausula `default`. Mesmo que nós tenhamos lidado com todos os valores no Enum Vanilla e com os nossos próprios, isso lançaria uma exceção caso outro mod adicionasse uma entrada diferente.

Como você pode prevenir isso? Não há uma maneira universal de evitar travamentos - a sua abordagem deve ser adaptada dependendo do caso. Porém, de modo geral:

- Se a expressão `switch` está em um método vanilla, você pode usar um mixin para editá-la
- Se a expressão `switch` vem de um mod, você deve tentar entrar em contato com os desenvolvedores, para desenvolver juntos uma abordagem compatível. Caso contrário, você pode ter que criar um mixin para o outro mod.

### Enums Serializados {#serialized-enums}

Certas entradas de Enum são serializadas automaticamente. Um exemplo é o Enum `Variants` na classe `Axolotl`.

Estendendo essas Enums serializaria a sua entrada customizada no namespace do Minecraft, e em algumas versões isso pode acontecer baseado no ID númerico.
Isso não é bom porque pode afetar os indices de todas as outras entradas.

É melhor evitar estender Enums se as suas entidades são serializadas desse jeito. Em vez disso, você pode querer procurar por uma API, caso disponível.
