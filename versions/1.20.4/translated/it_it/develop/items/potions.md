---
title: Pozioni
description: Impara come aggiungere pozioni personalizzate per vari effetti di stato.
authors:
  - dicedpixels
  - PandoricaVi
  - Drakonkinst

search: false
---

Le pozioni sono oggetti consumabili che conferiscono un effetto a un'entità. Un giocatore può preparare delle pozioni usando l'Alambicco oppure ottenerle come oggetti attraverso varie meccaniche di gioco.

## Pozioni Personalizzate

Aggiungere una pozione segue un percorso simile a quello per aggiungere un oggetto. Dovrai creare un'istanza della tua pozione e registrarla chiamando `PotionBrewing.addMix`.

:::info
Quando l'API di Fabric è presente, `PotionBrewing.addMix` è reso disponibile attraverso un Access Widener.
:::

### Creare la Pozione

Iniziamo dichiarando un attributo per conservare la tua istanza `Potion`. Useremo direttamente la classe dell'initializer per conservarla.

@[code lang=java transclude={18-27}](@/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java)

Passiamo una istanza di `MobEffectInstance`, che prende 3 parametri:

- `MobEffect type` - Un effetto. Qui usiamo il nostro effetto personalizzato. In alternativa puoi accedere agli effetti vanilla attraverso `net.minecraft.entity.effect.MobEffects`.
- `int duration` - Durata dell'effetto espressa in tick di gioco.
- `int amplifier` - Un amplificatore per l'effetto. Per esempio, Sollecitudine II avrebbe un amplificatore di 1.

:::info
Per creare il tuo effetto personalizzato, per favore guarda la guida [Effetti](../entities/effects).
:::

### Registrare la Pozione

Nel nostro initializer, chiamiamo `PotionBrewing.addMix`.

@[code lang=java transclude={30-30}](@/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java)

`addMix` prende 3 parametri:

- `Potion input` - La pozione iniziale. Solitamente questa può essere una Ampolla d'Acqua o una Pozione Strana.
- `Item item` - L'oggetto che rappresenta l'ingrediente principale della pozione.
- `Potion output` - La pozione risultante.

Se utilizzi l'API di Fabric, l'invoker mixin non è necessario e si può effettuare una chiamata diretta a `PotionBrewing.addMix`.

L'esempio per intero:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java)

Una volta registrato, puoi distillare una pozione Tater usando una patata.

![Effetto nell'inventario del giocatore](/assets/develop/tater-potion.png)

:::info Registrare Pozioni Usando un `Ingredient`

Con l'aiuto dell'API di Fabric, è possibile registrare una pozione usando un `Ingredient` anziché un `Item` usando `
net.fabricmc.fabric.api.registry.FabricPotionBrewing`.
:::

### Registrare la Pozione Senza l'API di Fabric

Senza l'API di Fabric, `PotionBrewing.addMix` sarà privato. Per accedere a questo metodo usa il seguente invoker mixin o usa un Access Widener.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/mixin/potion/PotionBrewingInvoker.java)
