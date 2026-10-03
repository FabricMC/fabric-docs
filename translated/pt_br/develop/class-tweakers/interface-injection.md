---
title: Injeção de Interface
description: Aprenda a como implementar interfaces nas classes do Minecraft no código-fonte descompilado.
authors-nogithub:
  - salvopelux
authors:
  - Daomephsta
  - CelDaemon
  - Earthcomputer
  - its-miroma
  - Juuxel
  - MildestToucan
  - Sapryx
  - SolidBlock-cn
---

A injeção de interface é um tipo de [modificador de classe](../class-tweakers/) usado para adicionar implementações de interface nas classes do Minecraft no código-fonte descompilado.

A implementação sendo visível no código-fonte descompilado remove a necessidade de transmitir para a interface para usar os seus métodos.

Além disso, injeções de interface podem ser [transitivas](../class-tweakers/index#transitive-entries), permitindo que bibliotecas exponham os seus métodos adicionados aos mods que dependem deles mais facilmente.

Para mostrar a injeção de interface, os trechos dessa página usarão um exemplo onde nós adicionamos um novo método ajudante ao `FlowingFluid`.

## Criando a Interface {#creating-the-interface}

Em um pacote que não seja o seu pacote de mixins, crie a interface que você gostaria de injetar:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/BucketEmptySoundGetter.java#interface_injection_example_interface

No nosso caso, lançaremos uma exceção por padrão, pois planejamos implementar o método por meio de um mixin.

::: warning

Os métodos das interfaces injetadas devem ser todas `default` para serem injetadas com um modificador de classe, mesmo se você planeja implementar os métodos na classe alvo usando um mixin.

Os métodos também devem ser prefixados pelo ID do seu mod com um separador como `$` ou `_`, fazendo com que eles não entrem em conflito com os métodos adicionados de outros mods.

:::

## Implementando a Interface {#implementing-the-interface}

::: tip

Se os métodos da interface estão totalmente implementados com os `default`s da interface, você não precisa usar um mixin para injetar a interface, a [entrada do modificador de classe](#making-the-class-tweaker-entry) será o suficiente.

:::

Para criar sobrescritas dos métodos da interface na classe alvo você deve usar um mixin que implementa a interface e tenha como alvo a classe que você deseja injetar a interface.

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/FlowingFluidMixin.java#interface_injection_example_mixin

A sobrescrita será adicionada a classe alvo em tempo de execução, mas não aparecerá no código-fonte descompilado mesmo se você usar um modificador de classe para deixar a implementação de interface visível.

## Criando a Entrada do Modificador de Classe {#making-the-class-tweaker-entry}

A injeção de interface usa a seguinte sintaxe:

```classtweaker:no-line-numbers
inject-interface    <targetClassName>    <injectedInterfaceName>
```

Para modificação de classe, classes e interfaces use os seus [nomes internos](../mixins/bytecode#class-names).

Para a nossa interface de exemplo, a entrada deve ser:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#interface_injection_example_entry{classtweaker:no-line-numbers}

### Interfaces Genéricas #generic-interfaces}

Se a sua interface possui tipos genéricos, você pode especificá-los na entrada. Para isso, adicione `<>` colchetes angulares no final do nome da interface com os tipos genéricos em formato de assinatura do bytecode Java entre os colchetes.

O formato de assinatura é:

| Descrição               | Exemplo Java             | Sintaxe                                                                           | Exemplo de Formato de Assinatura |
| ----------------------- | ------------------------ | --------------------------------------------------------------------------------- | -------------------------------- |
| Tipo de classe          | `java.lang.String`       | Formato do [Descritor](../mixins/bytecode#type-descriptors)                       | `Ljava/lang/String;`             |
| Tipo de array           | `java.lang.String[]`     | Formato do [Descritor](../mixins/bytecode#type-descriptors)                       | `[Ljava/lang/String;`            |
| Primitivo               | `boolean`                | Caractere do [Descritor](../mixins/bytecode#type-descriptors)                     | `Z`                              |
| Variável de tipo        | `T`                      | `T` + nome + `;`                                                                  | `TT;`                            |
| Tipo de classe genérica | `java.util.List<T>`      | L + [nome interno](../mixins/bytecode#class-names) + `<` + tipos genericos + `>;` | `Ljava/util/List<TT;>;`          |
| Curinga                 | `?`, `java.util.List<?>` | `*` caractere                                                                     | `*`, `java/util/List<*>;`        |
| Limite curinga extends  | \`? extende String       | `+` + o limite                                                                    | `+Ljava/lang/String;`            |
| Limite curinga super    | \`? super string         | `-` + o limite                                                                    | `-Ljava/lang/String;`            |

Então para injetar a interface:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/GenericInterface.java#interface_injection_generic_interface

com os genéricos `<? extends String, Boolean[]>`

A entrada do modificador de classe ficaria:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#interface_injection_generic_interface_entry{classtweaker:no-line-numbers}

## Aplicando Mudanças {#applying-changes}

Para ver a sua implementação de interface aplicada, você deve recarregar o seu projeto do Gradle e [regenerar os arquivos de código-fonte](../getting-started/generating-sources).
Certifique-se de abrir novamente qualquer classe alvo do código-fonte descompilado para ver as modificações.

::: tip

Se as modificações não aparecem, você pode tentar [validar o arquivo](../class-tweakers/index#validating-the-file) e verificar se algum erro aparece.

:::

<!---->

Agora os métodos adicionados podem ser usados nas instâncias da classe onde a interface foi injetada:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/ExampleModInterfaceInjection.java#interface_injection_using_added_method

Você também pode sobrescrever os métodos nas sub-classes do alvo de injeção da interface caso necessário.
