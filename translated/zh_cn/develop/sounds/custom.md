---
title: 创建自定义声音
description: 了解如何通过注册表添加和使用新声音。
authors:
  - JR1811
---

## 准备音频文件{#preparing-the-audio-file}

你的音频文件需要转化为特定格式。 OGG Vorbis 是一种用于音频等多媒体数据的开放式容器格式，Minecraft 的声音文件就使用了这种格式。 为了避免 Minecraft 在处理声音传播距离时出现问题，你的音频必须只有单声道（Mono）。

许多现代 DAW（数字音频工作站）软件都可以使用这种格式导入和导出。 在下面的例子中，我们将使用免费开源软件“[Audacity](https://www.audacityteam.org/)”将音频文件转换成正确的格式，当然其他的 DAW 也可以做到。

![Audacity 中未准备好的音频文件](/assets/develop/sounds/custom_sounds_0.png)

在本例中，将[哨声](https://freesound.org/people/strongbot/sounds/568995/)作为例子导入 Audacity。 这个声音目前保存为 `.wav` 文件，有两个音频通道（立体声）。 按照自己的需求编辑音频，并确保使用“音轨头”顶部的下拉元素删除其中一个音频通道。

![分割立体声轨](/assets/develop/sounds/custom_sounds_1.png)

![删除一个音频通道](/assets/develop/sounds/custom_sounds_2.png)

导出或渲染音频文件时，请确认选择的是 OGG 文件格式。 有些 DAW（如 REAPER）可能支持多种 OGG 音频层格式。 在这种情况下，选择 OGG Vorbis 即可。

![导出为 OGG 文件](/assets/develop/sounds/custom_sounds_3.png)

另外，记住，音频文件可能显著增加你的模组的大小。 如有必要，在编辑和导出文件时适量压缩音频本身，以尽量减小导出的文件大小。

## 加载音频文件{#loading-the-audio-file}

要在你的模组中使用音频文件，要添加新的 `resources/assets/example-mod/sounds` 目录，并将导出的音频文件 `metal_whistle.ogg` 放入该目录中。

如果 `resources/assets/example-mod/sounds.json` 文件还未生成，继续创建该文件，并将你的音效添加到音效条目中。

@[code lang=json](@/reference/latest/src/main/resources/assets/example-mod/sounds.json)

字幕（subtitle）条目为玩家提供了更多的关于该声音的信息。 在 `resources/assets/example-mod/lang` 目录下的语言文件中会用到声音文件，如果游戏内字幕设置已打开且正在播放自定义声音，则会显示这个字幕。

## 注册自定义声音{#registering-the-custom-sound}

要将自定义声音添加到模组，在你的[模组的初始化器](../getting-started/project-structure#entrypoints)中注册 SoundEvent。

```java
Registry.register(BuiltInRegistries.SOUND_EVENT, Identifier.fromNamespaceAndPath(MOD_ID, "metal_whistle"),
        SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(MOD_ID, "metal_whistle")));
```

## 整理整理{#cleaning-up-the-mess}

根据注册表项的数量，入口点类可能很快就会变得十分杂乱。 为了避免这种情况，我们可以使用一个新的辅助类。

在新创建的辅助类中添加两个新方法： 一个用于注册所有声音，一个用于初始化该类。 之后就可以根据需要，添加新的自定义 `SoundEvent` 常量了。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/sound/CustomSounds.java)

如此，模组的初始化器只需实现一行即可注册所有的自定义 SoundEvents。

@[code lang=java transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/sound/ExampleModSounds.java)

## 使用自定义的 SoundEvent{#using-the-custom-soundevent}

使用辅助类去访问自定义的 SoundEvent。 查看[播放声音](./using-sounds)页面，了解如何播放声音。
