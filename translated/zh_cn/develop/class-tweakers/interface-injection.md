---
title: 接口注入
description: 学习如何在反编译后的源代码中实现 Minecraft 类的接口。
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

接口注入是一种[类调整](../class-tweakers/)，用于在反编译源码中为 Minecraft 类添加接口实现。

由于该实现会显示在类的反编译源码中，因此在使用接口中的方法时，无需再强制转换为该接口类型。

除此之外，接口注入还可以是[传递性的](../class-tweakers/index#transitive-entries)，这使得库能够更方便地向依赖它们的模组暴露新增的方法。

为了演示接口注入，本页的代码片段将使用一个示例：为 `FlowingFluid` 添加一个新的辅助方法。

## 创建接口 {#creating-the-interface}

在一个非 mixin 包中，创建你想要注入的接口：

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/BucketEmptySoundGetter.java#interface-injection-example-interface

在本例中，我们会默认抛出异常，因为我们计划通过 mixin 来实现该方法。

::: warning

通过类调整器注入的接口，其所有方法都必须是 `default` 方法，即使你计划使用 mixin 在目标类中实现这些方法也是如此。

方法名也应当以你的模组 ID 作为前缀，并使用 `$` 或 `_` 等分隔符，这样可以避免与其他模组添加的方法发生冲突。

:::

## 实现接口 {#implementing-the-interface}

::: tip

如果接口中的方法已经通过接口自身的 `default` 实现完整实现，那么你不需要使用 mixin 来注入接口，只需要添加[类调整器条目](#making-the-class-tweaker-entry)即可。

:::

若要在目标类中重写接口方法，你应当使用一个实现该接口、并以目标类为目标的 mixin。

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/FlowingFluidMixin.java#interface-injection-example-mixin

这些重写方法会在运行时被添加到目标类中，但即使你使用类调整器让接口实现显示出来，它们也不会出现在反编译源码中。

## 制作类调整器条目 {#making-the-class-tweaker-entry}

接口注入使用以下语法：

```:no-line-numbers
inject-interface    <targetClassName>    <injectedInterfaceName>
```

在类调整中，类和接口都使用它们的[内部名称](../mixins/bytecode#class-names)。

对于我们的示例接口，条目如下：

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#interface-injection-example-entry{classtweaker:no-line-numbers}

### 泛型接口 {#generic-interfaces}

如果你的接口带有泛型，也可以在条目中指定泛型。 为此，需要在接口名称末尾添加 `<>` 尖括号，并在括号中使用 Java 字节码签名格式填写泛型。

签名格式如下：

| 描述    | Java 示例                 | 语法                                                           | 签名格式示例                    |
| ----- | ----------------------- | ------------------------------------------------------------ | ------------------------- |
| 类类型   | `java.lang.String`      | [类型描述符](../mixins/bytecode#type-descriptors)格式               | `Ljava/lang/String;`      |
| 数组类型  | `java.lang.String[]`    | [类型描述符](../mixins/bytecode#type-descriptors)格式               | `[Ljava/lang/String;`     |
| 原始类型  | `boolean`               | [类型描述符](../mixins/bytecode#type-descriptors)字符               | `Z`                       |
| 类型参数  | `T`                     | `T` + 名称 + `;`                                               | `TT;`                     |
| 泛型类类型 | `java.util.List<T>`     | L + [内部名称](../mixins/bytecode#class-names) + `<` + 泛型 + `>;` | `Ljava/util/List<TT;>;`   |
| 通配符   | `?`、`java.util.List<?>` | `*` 字符                                                       | `*`, `java/util/List<*>;` |
| 上界通配符 | `? extends String`      | `+` + 边界类型                                                   | `+Ljava/lang/String;`     |
| 下界通配符 | `? super String`        | `-` + 边界类型                                                   | `-Ljava/lang/String;`     |

因此，如果要注入以下接口：

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/GenericInterface.java#interface-injection-generic-interface

并指定泛型 `<? extends String, Boolean[]>`，

那么类调整器条目应为：

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#interface-injection-generic-interface-entry{classtweaker:no-line-numbers}

## 应用更改 {#applying-changes}

若要看到接口实现生效，你必须刷新 Gradle 项目并[重新生成源码](../getting-started/generating-sources)。
如果修改没有出现，可以尝试[验证](../class-tweakers/index#validating-the-file)该文件，并检查是否有错误。

现在，新增的方法可以直接在被注入接口的类实例上使用：

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/ExampleModInterfaceInjection.java#interface-injection-using-added-method

如有需要，你也可以在接口注入目标类的子类中重写这些方法。
