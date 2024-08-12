---
title: Riprodurre Suoni
description: Impara come riprodurre eventi sonori.

search: false
---

# Riprodurre Suoni

Minecraft ha una grande selezione di suoni da cui puoi scegliere. Controlla la classe `SoundEvents` per vedere tutte le istanze di eventi sonori vanilla che Mojang ha predisposto.

## Usare Suoni nella Tua Mod

Assicurati di eseguire il metodo `playSound()` sul lato del server logico quando usi i suoni!

In questo esempio, i metodi `useOnEntity()` e `useOnBlock()` per un oggetto interattivo personalizzato sono usati per riprodurre un suono "piazzando blocco di rame" e un suono "predone".

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/CustomSoundItem.java)

Il metodo `playSound()` è usato con l'oggetto `LivingEntity`. Solo il SoundEvent, il volume e il tono devono essere specificati. Puoi anche usare il metodo `playSound()` dall'istanza del mondo per avere un livello di controllo più alto.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/CustomSoundItem.java)

### SoundEvent e SoundCategory

Il SoundEvent definisce quale suono verrà riprodotto. Puoi anche [registrare i tuoi SoundEvents](./custom) per includere il tuo suono.

Minecraft ha vari slider audio nelle impostazioni del gioco. L'enum `SoundCategory` è usato per determinare quale slider controllerà il volume del tuo suono.

### Volume e Tono

Il parametro volume può causare un po' di confusione. Nell'intervallo `0.0f - 1.0f` il volume reale del suono può essere cambiato. Se il numero diventa più grande di ciò, il volume di `1.0f` verrà usato e soltanto la distanza, nella quale il tuo suono può essere udito, viene cambiata. La distanza in blocchi può essere approssimativamente calcolata come `volume * 16`.

Il parametro pitch alza o abbassa il valore del tono e cambia anche la durata del suono. Nell'intervallo `(0.5f - 1.0f)` il tono e la velocità si abbassano, mentre valori maggiori alzano il tono e la velocità. Numeri al di sotto di `0.5f` rimarranno al valore di tono `0.5f`.
