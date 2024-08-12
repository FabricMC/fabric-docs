---
title: 播放声音事件（SoundEvent）
description: 学习如何播放声音事件（SoundEvent）。

search: false
---

# 播放声音事件（SoundEvent）

Minecraft 有大量的声音供您选择。 查看 `SoundEvents` 类以查看 Mojang 提供的所有原版声音事件实例。

## 在您的模组中使用声音

使用声音时请确保在逻辑服务端执行 `playSound()` 方法。

在此示例中，自定义交互项的 `useOnEntity()` 和 `useOnBlock()` 方法用于播放“放置铜块”和掠夺者声音。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/CustomSoundItem.java)

`playerSound()` 方法与 `LivingEntity` 对象一起使用。 只需要指定 SoundEvent、音量（volume）和音高（pitch）。 您还可以使用世界实例中的 `playSound()` 方法以获得更高级别的控制。

@[code lang=java transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/CustomSoundItem.java)

### 声音事件与声音组（SoundCategory）

声音事件定义了播放哪个声音。 您也可以[注册您自己的声音事件](./custom)以包含您自己的声音。

Minecraft 在游戏设置中有多个音频滑块。 `SoundCategory` 枚举类用于确定哪个滑块可以调整您声音的音量。

### 音量和音高

音量参数可能有些误导。 在 `0.0f - 1.0f` 的范围内可以改变声音的实际音量。 如果数字大于这个范围，将使用 `1.0f` 的音量，并且仅使用您所在的距离。可以听到声音，进行调整。 通过 `volume * 16` 可以粗略算出方块距离。

音高参数会增加或减少音高数值，还会改变声音的持续时间。 在 `(0.5f - 1.0f)` 范围内，音高和速度会减小，而较大的数字会增加音高和速度。 `0.5f` 以下的数字将保持 `0.5f`。
