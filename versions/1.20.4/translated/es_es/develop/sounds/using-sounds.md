---
title: Reproducir SoundEvents (Eventos de Sonido)
description: Aprende a reproducir eventos de sonido.

search: false
---

# Reproducir SoundEvents (Eventos de Sonido)

Minecraft tiene una gran selección de sonidos para elegir. Mira la clase de `SoundEvents` para ver todas las instancias de eventos de sonido vanilla que Mojang provee.

## Usando Sonidos en Tu Mod

¡Asegúrate de ejecutar el método `playSond()` en el lado del servidor lógico cuando uses sonidos!

En este ejemplo, los métodos de `useOnEntity()` y `useOnBlock()` para un item interactivo personalizado son usados para reproducir sonidos de "colocar un bloque de cobre" y de saqueador.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/CustomSoundItem.java)

El método `playSound()` es usado con el objeto de `LivingEntity` (Entidad Viviente). Solamente se tienen que especificar el SoundEvent, el volumen y el tono. También puedes usar el método de `playSound()` de la instancia del mundo para tener un mayor grado de control.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/CustomSoundItem.java)

### SoundEvent y SoundCategory (Categoría de Sonido)

El SoundEvent define que sonido será reproducido. También puedes [registrar tu propio SoundEvent](./custom) para incluir tu propio sonido.

Minecraft tiene varios deslizadores de audio en las opciones del juego. El enum (enumeración) de `SoundCategory` es usado para determinar que deslizador ajustará el volumen de tu sonido.

### Volumen y Tono

El parámetro de volumen puede ser algo engañoso. El volumen del sonido puede ser cambiado en el rango de `0.0f - 1.0f`. Si el número es mayor que eso, el volumen de `1.0f` será usado y solo la distancia la que tu sonido puede ser escuchado, será ajustada. La distancia en bloques puede ser calculada aproximadamente con `volumen * 16`.

El parámetro de tono aumenta o disminuye el valor de tono y también cambia la duración del sonido. En el rango `(0.5f - 1.0f)` el tono y la velocidad son disminuídas, mientras que valores números incrementarán el tono y la velocidad. Números por debajo de `0.5f` se mantendrán en el valor de tono de `0.5f`.
