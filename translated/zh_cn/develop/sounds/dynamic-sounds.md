---
title: 动态交互式音效
description: 创建更动态和交互式的音效
authors:
  - PEQB1145
---

::: info 前提条件
本页面基于 [播放音效](../sounds/using-sounds) 和 [创建自定义音效](../sounds/custom) 页面构建！
:::

## `SoundEvents` 的局限性 {#problems-with-soundevents}

正如我们在 [使用音效](../sounds/using-sounds) 页面中所了解的，即使在逻辑服务器端使用 `SoundEvent` 有些反直觉，但却是更可取的。毕竟，客户端需要处理传输到你耳机中的声音，对吗？

这种想法是正确的。从技术上讲，音频需要由客户端处理。然而，对于简单的 `SoundEvent` 播放，服务器端在中间准备了一个很大的步骤，乍一看可能并不明显：哪些客户端应该能听到这个声音？

在逻辑服务器端使用声音将解决广播 `SoundEvent` 的问题。简单来说，在跟踪范围内的每个客户端（`LocalPlayer`）都会收到一个网络数据包来播放这个特定的声音。声音事件基本上是从逻辑服务器端广播到每个参与的客户端，而你根本不需要考虑它。声音按照指定的音量和音高值播放一次。

但如果这还不够呢？如果需要音效能够循环播放、在播放过程中动态改变音量和音高，并且所有这些都基于来自像 `Entities` 或 `BlockEntities` 这样的实体的值呢？

对于这种用例，在逻辑服务器端简单使用 `SoundEvent` 是不够的。

## 准备音频文件 {#preparing-the-audio-file}

我们将为另一个 `SoundEvent` 创建一个新的**循环**音频。如果你能找到一个已经能够无缝循环的音频文件，可以直接按照 [创建自定义音效](../sounds/custom) 的步骤操作。如果声音还不能完美循环，我们需要为循环做准备。

同样，大多数现代 DAW（数字音频工作站软件）都应该能够做到这一点，但如果音频编辑稍微复杂一些，我喜欢使用 [Reaper](https://www.reaper.fm/)。

### 设置 {#set-up}

我们的 [起始声音](https://freesound.org/people/el-bee/sounds/644881/) 将来自一个引擎。

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_0.wav" type="audio/wav">
    你的浏览器不支持 audio 元素。
</audio>

让我们将文件加载到我们选择的 DAW 中。

![加载了音频文件的 Reaper](/assets/develop/sounds/dynamic-sounds/step_0.png)

我们可以听到和看到，引擎在开头启动，在结尾停止，这对于循环音效来说并不理想。让我们把这些部分剪掉，并调整时间选区手柄以匹配新的长度。同时启用 `Toggle Repeat` 模式，让我们在调整音频时能够让它循环播放。

![修剪后的音频文件](/assets/develop/sounds/dynamic-sounds/step_1.png)

### 去除干扰性音效元素 {#removing-disruptive-audio-elements}

如果我们仔细听，背景中有哔哔声，可能是来自机器的声音。我认为这在游戏中听起来不好，所以让我们试着去除它。

这是一种持续的声音，在整个音频长度中保持其频率不变。所以一个简单的 EQ 滤波器应该足以将其滤除。

Reaper 自带了一个 EQ 滤波器，叫做 "ReaEQ"。在其他 DAW 中，这个工具可能位置不同、名称各异，但现在使用 EQ 在大多数 DAW 中都是标准操作。

如果你确定你的 DAW 没有可用的 EQ 滤波器，可以在网上查找免费的 VST 替代品，或许可以安装到你选择的 DAW 中。

在 Reaper 中使用 Effects 窗口来添加 "ReaEQ" 音频效果，或者任何其他 EQ。

![添加 EQ 滤波器](/assets/develop/sounds/dynamic-sounds/step_2.png)

如果我们现在播放音频，同时保持 EQ 滤波器窗口打开，EQ 滤波器将在其显示器中显示输入音频。
我们可以看到很多波峰。

![识别问题](/assets/develop/sounds/dynamic-sounds/step_3.png)

如果你不是训练有素的音频工程师，这部分主要是关于实验和“反复试错”。在节点 2 和 3 之间有一个相当明显的凸起。让我们移动节点，只降低那部分的频率。

![降低了不良频率](/assets/develop/sounds/dynamic-sounds/step_4.png)

此外，一个简单的 EQ 滤波器也可以实现其他效果。例如，削减高频和/或低频可以给人以通过无线电传输的声音的印象。

你也可以叠加更多音频文件、改变音高、添加一些混响或者使用更复杂的音效，如“位压缩器”。音效设计可以很有趣，尤其是当你偶然发现音频的好听变体时。实验是关键，也许你的音效最终会比以前更好。

我们将只继续使用 EQ 滤波器，用它来去除有问题的频率。

### 对比 {#comparison}

让我们来比较原始文件和清理后的版本。

在原始声音中，你可以听到来自引擎电气元件的明显嗡嗡声和哔哔声。

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_5_first.ogg" type="audio/ogg">
    你的浏览器不支持 audio 元素。
</audio>

使用 EQ 滤波器，我们几乎完全去除了它。这样听起来肯定更悦耳。

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_5_second.ogg" type="audio/ogg">
    你的浏览器不支持 audio 元素。
</audio>

### 使其循环 {#making-it-loop}

如果我们让声音播放到结尾，然后让它从头开始，可以清楚地听到过渡的发生。目标是通过应用平滑的过渡来消除这种情况。

首先，从结尾剪下一段，其大小与你希望的过渡时间相同，并将其放置在新音频轨道的开头。
在 Reaper 中，你可以通过将光标移动到剪切位置并按 <kbd>S</kbd> 来分割音频。

![剪切结尾并将其移动到新轨道](/assets/develop/sounds/dynamic-sounds/step_6.png)

你可能需要将第一个音频轨道的 EQ 音频效果也复制到第二个轨道上。

现在让新轨道结尾部分的声音淡出，并让第一个音频轨道开头的声音淡入。

![使用淡入淡出的音频轨道进行循环](/assets/develop/sounds/dynamic-sounds/step_7.png)

### 导出 {#exporting}

导出包含两个音频轨道、但只有一个音频通道（单声道）的音频，并为 mod 中的 `.ogg` 文件创建一个新的 `SoundEvent`。
如果你不确定如何操作，请查阅 [创建自定义音效](../sounds/custom) 页面。

这是现在为命名为 `ENGINE_LOOP` 的 `SoundEvent` 准备的最终循环引擎音频。

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_8.ogg" type="audio/ogg">
    你的浏览器不支持 audio 元素。
</audio>

## 使用 `SoundInstance` {#using-a-soundinstance}

为了在客户端播放声音，需要一个 `SoundInstance`。它们仍然会用到 `SoundEvent`。

如果你只想播放像 UI 元素点击这样的声音，已经存在现成的 `SimpleSoundInstance` 类。

请记住，这只会由执行这部分代码的特定客户端播放。

@[code lang=java transcludeWith=:::1](@/reference/client/java/com/example/docs/ExampleModDynamicSound.java)

::: warning
请注意，`SoundInstance` 继承自的 `AbstractSoundInstance` 类带有 `@Environment(EnvType.CLIENT)` 注解。

这意味着这个类（及其所有子类）将**仅**在客户端可用。

如果你尝试在逻辑服务器端上下文中使用它，最初在单人游戏中可能不会发现问题，
但在多人游戏环境中的服务器将会崩溃，因为它完全无法找到那部分代码。

如果你在这些问题上遇到困难，建议你使用 [在线模板生成器](https://fabricmc.net/develop/template/)
创建你的 mod，并开启 `Split client and common sources` 选项。
:::

`SoundInstance` 的功能可以比仅仅播放一次声音更强大。

查看 `AbstractSoundInstance` 类，了解它可以跟踪哪些类型的值。
除了通常的 volume（音量）和 pitch（音高）变量，它还保存 XYZ 坐标以及是否应该在 `SoundEvent` 结束后重复播放。

再看它的子类 `AbstractTickableSoundInstance`，我们还引入了 `TickableSoundInstance` 接口，它为 `SoundInstance` 添加了 ticking（周期更新）功能。

因此，要利用这些实用工具，只需为你的自定义 `SoundInstance` 创建一个新类，并从 `MovingSoundInstance` 扩展。

@[code lang=java transcludeWith=:::1](@/reference/client/java/com/example/docs/sound/instance/CustomSoundInstance.java)

使用你的自定义 `Entity` 或 `BlockEntity` 来代替那个基本的 `LivingEntity` 实例，可以给你更多的控制权，例如在 `tick()` 方法中基于访问器方法，
但你并不一定需要一个这样的声源引用。相反，你也可以从其他地方访问一个 `BlockPos`，
或者甚至只在构造函数中手动设置一次。

只需记住，`SoundInstance` 中引用的所有对象都是来自客户端的版本。
在特定情况下，逻辑服务器端的实体属性可能与它的客户端副本不同。
如果你发现你的值不匹配，请确保你的值是通过实体的 `EntityDataAccessor`、`BlockEntity` 从服务器到客户端（S2C）的数据包或完全自定义的 S2C 网络数据包同步的。

在创建完自定义的 `SoundInstance` 后，只要它是在客户端使用声音管理器（sound manager）执行的，就可以在任何地方使用它。
同样，你也可以在必要时手动停止自定义的 `SoundInstance`。

@[code lang=java transcludeWith=:::2](@/reference/client/java/com/example/docs/ExampleModDynamicSound.java)

现在声音循环将只对运行该 SoundInstance 的客户端播放。在这种情况下，声音将跟随 `LocalPlayer` 自身。

这就完成了创建和使用简单自定义 `SoundInstance` 的讲解。

## 高级 Sound Instance {#advanced-sound-instances}

::: warning
以下内容涵盖高级主题。

此时你应牢固掌握 Java、面向对象编程、泛型和回调系统。

对 `Entities`、`BlockEntities` 和自定义网络的知识也会有很大帮助，有助于理解高级音效的用例和应用。
:::

为了展示如何创建更复杂的 `SoundInstance` 系统，我们将添加额外的功能、抽象和实用工具，使在更大范围内使用这类声音更容易、更动态和更灵活。

### 理论 {#theory}

让我们思考一下使用 `SoundInstance` 的目标是什么。

-  只要链接的自定义 `EngineBlockEntity` 在运行，声音就应该循环播放。
-  `SoundInstance` 应该随之移动，跟随其自定义的 `EngineBlockEntity` 位置 _(`BlockEntity` 不会移动，所以这在 `Entities` 上可能更有用)_
-  我们需要平滑的过渡。打开或关闭几乎不应该是瞬时的。
-  基于外部因素（例如声源）改变音量和音高。

总结一下，我们需要跟踪一个自定义 `BlockEntity` 的实例，
在 `SoundInstance` 运行时根据该自定义 `BlockEntity` 的值调整音量（volume）和音高（pitch）值，并实现“过渡状态”。

如果你计划制作几个行为方式不同的 `SoundInstances`，我建议创建一个新的抽象类 `AbstractDynamicSoundInstance`，
它实现默认行为，并让实际的自定义 `SoundInstance` 类继承它。

如果你只计划使用一个，你可以跳过抽象超类，直接在自定义 `SoundInstance` 类中实现该功能。

此外，拥有一个集中式位置来跟踪、播放和停止 `SoundInstance` 将是个好主意。这意味着它需要处理传入的信号，
例如来自自定义从服务器到客户端（S2C）网络数据包的信号，列出所有当前正在运行的实例，并处理特殊情况，例如哪些声音允许同时播放，以及哪些声音
在激活时可能禁用其他声音。
为此，可以创建一个新的 `DynamicSoundManager` 类，以便轻松地与这个声音系统交互。

当我们完成后，我们的整个声音系统可能看起来像这样。

![自定义声音系统的结构](/assets/develop/sounds/dynamic-sounds/custom-dynamic-sound-handling.jpg)

::: info
所有这些枚举、接口和类都是新创建的。请根据你的具体用例调整这个系统和实用工具。
这只是如何处理此类主题的一个示例。
:::

### `DynamicSoundSource` 接口 {#dynamicsoundsource-interface}

如果你选择创建一个新的、更模块化的自定义 `AbstractDynamicSoundInstance` 类作为超类，
你可能希望引用一种以上类型的 `Entity`，甚至也可能是 `BlockEntity`。

在这种情况下，利用抽象是关键。
与其直接引用例如一个自定义的 `BlockEntity`，不如只跟踪一个提供数据的接口，这解决了这个问题。

接下来，我们将使用一个名为 `DynamicSoundSource` 的自定义接口。所有希望利用该动态声音功能的类都会实现它，
比如你的自定义 `BlockEntity`、实体，或者甚至通过 Mixin 用于现有类，如 `Zombie`。它基本上只代表声源的必要数据。

@[code lang=java transcludeWith=:::1](@/reference/main/java/com/example/docs/sound/DynamicSoundSource.java)

创建完这个接口后，确保也在必要的类中实现它。

::: info
这是一个实用工具，可能同时在客户端和逻辑服务器端使用。

所以，如果你使用了“分割源代码”选项，这个接口应该存储在 common（通用）包中，而不是仅限客户端的包中。
:::

### `TransitionState` 枚举 {#transitionstate-enum}

如前所述，你可以使用客户端的 `SoundManager` 来停止正在运行的 `SoundInstance`，但这会导致 SoundInstance 瞬间静音。
我们的目标是，当停止信号到来时，不停止声音，而是执行其“过渡状态”的结束阶段。只有在结束阶段完成后，
自定义的 `SoundInstance` 才应该停止。

`TransitionState` 是一个新创建的枚举，包含三个值。它们将用于跟踪声音应处于哪个阶段。

-   `STARTING` 阶段：声音从静音开始，但音量缓慢增加
-   `RUNNING` 阶段：声音正常播放
-   `ENDING` 阶段：声音从原始音量开始，缓慢降低直至静音

技术上，一个仅包含阶段的简单枚举可能就足够了。

```java
public enum TransitionState {
    STARTING, RUNNING, ENDING
}
`

但当这些值通过网络发送时，你可能想为它们定义一个 `ResourceLocation`，或者甚至添加其他自定义值。

@[code lang=java transcludeWith=:::1](@/reference/main/java/com/example/docs/sound/TransitionState.java)

::: info
同样，如果你使用“分割源代码”，需要考虑在哪里使用这个枚举。
从技术上讲，只有仅在客户端可用的自定义 `SoundInstance` 会使用这些枚举值。

但如果这个枚举在其他任何地方使用，例如在自定义网络数据包中，你可能需要将这个枚举也放入 common 包中，
而不是仅限客户端的包中。
:::

### `SoundInstanceCallback` 接口 {#soundinstancecallback-interface}

这个接口用作回调。目前，我们只需要一个 `onFinished` 方法，但如果你需要从 `SoundInstance` 对象发送其他信号，也可以添加自己的方法。

@[code lang=java transcludeWith=:::1](@/reference/client/java/com/example/docs/sound/instance/SoundInstanceCallback.java)

在需要处理传入信号的任何类上实现这个接口，例如我们很快要创建的 `AbstractDynamicSoundInstance`，
并在自定义 `SoundInstance` 本身中实现这个功能。

### `AbstractDynamicSoundInstance`类 {#abstractdynamicsoundinstance-class}

让我们最终开始实现动态 `SoundInstance` 系统的核心。`AbstractDynamicSoundInstance` 是一个新创建的 `abstract` 类。
它实现了我们的自定义 `SoundInstances` 的默认定义功能和实用工具，后者将继承它。

我们可以利用之前 [#using-a-soundinstance] 的 `CustomSoundInstance`，并在此基础上进行改进。
现在，我们将引用我们的 `DynamicSoundSource`，而不是 `LivingEntity`。
此外，我们将定义更多属性。

-   `TransitionState`：用于跟踪当前阶段
-   tick duration（周期持续时间）：用于控制开始和结束阶段应持续多久
-   volume（音量）和 pitch（音高）的最小值和最大值
-   布尔值：用于通知此实例是否已完成并可被清理
-   tick 持有器：用于跟踪当前声音的进度。
-   一个回调：当 `SoundInstance` 实际完成时，发送信号回 `DynamicSoundManager` 以进行最终清理

@[code lang=java transcludeWith=:::1](@/reference/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

然后在抽象类的构造函数中为自定义 `SoundInstance` 设置默认起始值。

@[code lang=java transcludeWith=:::2](@/reference/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

构造函数完成后，你需要允许 `SoundInstance` 能够播放。

@[code lang=java transcludeWith=:::3](@/reference/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

现在到了这个动态 `SoundInstance` 的重要部分。基于实例的当前 tick（周期计数），它可以应用不同的值和行为。

@[code lang=java transcludeWith=:::4](@/reference/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

如你所见，我们还没有在这里应用 volume（音量）和 pitch（音高）调制。我们只应用共享的行为。
所以，在这个 `AbstractDynamicSoundInstance` 类中，我们只提供基本结构和工具给子类，
由子类自己决定它们实际想要应用哪种类型的声音调制。

那么，让我们看一些这种声音调制方法的例子。

@[code lang=java transcludeWith=:::5](@/reference/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

如你所见，归一化值与线性插值（lerp）结合有助于将数值塑造成首选的音频极限值。
请注意，如果你添加多个改变相同值的方法，你将需要观察和调整它们如何协同工作。

现在，我们只需要添加剩余的实用方法，就完成了 `AbstractDynamicSoundInstance` 类。

@[code lang=java transcludeWith=:::6](@/reference/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

### `SoundInstance` 实现示例 {#example-soundinstance-implementation}

如果我们看一下实际的、继承自新创建的 `AbstractDynamicSoundInstance` 的自定义 `SoundInstance` 类，我们只需要考虑
什么条件会导致声音停止，以及我们想要应用什么声音调制。

@[code lang=java transcludeWith=:::1](@/reference/client/java/com/example/docs/sound/instance/EngineSoundInstance.java)

### `DynamicSoundManager` 类 {#dynamicsoundmanager-class}

我们之前 [#using-a-soundinstance] 讨论了如何播放和停止 `SoundInstance`。为了清理、集中和管理这些交互，你可以创建自己的
基于此功能的 `SoundInstance` 处理器。

这个新的 `DynamicSoundManager` 类将管理自定义的 `SoundInstances`，因此它也只会在客户端可用。此外，一个客户端应该只允许存在该类的一个实例。一个客户端存在多个声音管理器没有太大意义，并且会使交互更加复杂。
所以，让我们使用一个["单例设计模式"](https://refactoring.guru/design-patterns/singleton/java/example)。

@[code lang=java transcludeWith=:::1](@/reference/client/java/com/example/docs/sound/DynamicSoundManager.java)

在基本结构正确之后，你可以添加与声音系统交互所需的方法。

-   播放声音
-   停止声音
-   检查是否有声音当前正在播放

@[code lang=java transcludeWith=:::2](@/reference/client/java/com/example/docs/sound/DynamicSoundManager.java)

除了仅有一个当前正在播放的所有 `SoundInstances` 的列表外，你也可以跟踪哪些声音源正在播放哪些声音。
例如，一个引擎同时有两个引擎声音是没有意义的，而多个引擎播放它们各自的引擎声音
是一个有效的边界情况。为了简单起见，我们只创建了一个 `List<AbstractDynamicSoundInstance>`，但在许多情况下，使用 `HashMap` 来映射 `DynamicSoundSource` 和 `AbstractDynamicSoundInstance` 可能是更好的选择。

### 使用高级声音系统 {#using-the-advanced-sound-system}

要使用这个声音系统，只需使用 `DynamicSoundManager` 的方法或 `SoundInstance` 的方法。使用实体或自定义从服务器到客户端（S2C）网络中的 `onStartedTrackingBy` 和 `onStoppedTrackingBy`，你现在可以启动和停止你的自定义动态 `SoundInstances`。

@[code lang=java transcludeWith=:::1](@/reference/client/java/com/example/docs/network/ReceiveS2C.java)

最终产品可以根据声音阶段调整其音量以平滑过渡，并根据来自声源的压力值改变音高。

<VideoPlayer src="/assets/develop/sounds/dynamic-sounds/engine-block-sound.webm">带有动态声音变化的引擎 BlockEntity</VideoPlayer>

你可以向声源添加另一个值，以跟踪"过热"值，此外，如果该值大于 0，让一个嘶嘶的 `SoundInstance` 慢慢淡入，或者为你的自定义动态 `SoundInstances` 添加一个新接口，为声音类型分配优先级值，如果这些声音
彼此冲突，这有助于选择播放哪个声音。

使用当前的系统，你可以轻松地同时处理多个 `SoundInstance`，并根据你的需要设计音频。
