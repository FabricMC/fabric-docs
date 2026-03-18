---
title: Mikstury
description: Dowiedz się, jak stworzyć własne mikstury dla różnych efektów.
authors:
  - dicedpixels
  - Drakonkinst
  - JaaiDead
  - PandoricaVi
---

Mikstury, to materiały możliwe do spożycia, które przyznają istocie efekt. Gracz może warzyć mikstury używając statywu alchemicznego, lub otrzymując je jako przedmioty (itemy) z innych mechanik w grze.

## Własne Mikstury {#custom-potions}

Tak samo jak przedmioty i bloki, mikstury muszą być zarejestrowane.

### Tworzenie Własnej Mikstury {#creating-the-potion}

Zacznijmy od zdeklarowania pola do przechowania twojej instancji 'Potion'. Będziemy bezpośrednio używać klasy implementującej 'ModInitializer' do przechowywania owej mikstury.

@[code lang=java transclude={18-27}](@/reference/1.21.4/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Instancja 'MobEffectInstance' posiada 3 argumenty:

- `Holder<MobEffect> type` - Efekt. Używamy naszego własnego efektu tutaj. Alternatywnie, możesz użyć efektów gry niezmodyfikowanej za pomocą domyślnej klasy 'MobEffects'.
- `int duration` - Czas trwania efektu w tickach.
- `int amplifier` - Mnożnik dla efektu. Na przykład, Pośpiech II będzie mieć mnożnik o wartości 1.

:::info
Aby stworzyć swój własny efekt, proszę zobacz poradnik o [Efektach](../entities/effects).
:::

### Rejestrowanie Mikstury {#registering-the-potion}

W naszym inicjalizatorze będziemy używać wydarzenia 'FabricPotionBrewingBuilder.BUILD' aby zarejestrować nasza miksturę używając metody 'PotionBrewing.addMix'.

@[code lang=java transclude={29-40}](@/reference/1.21.4/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

'addMix' posiada 3 argumenty:

- `Holder<Potion> input` - Wpis w rejestrze oryginalnej mikstury. Zazwyczaj może to być Butelka Wody (Water Bottle) albo Niezidentyfikowana Mikstura (Awkward Potion).
- `Item item` - Przedmiot, który jest głównym składnikiem mikstury.
- `Holder<Potion> output` - Wpis w rejestrze mikstury wyjściowej.

Z zarejestrowaniem, możesz wywarzyć Kartoflowa miksturę używając ziemniaka.

![Efekt w Ekwipunku Gracza](/assets/develop/tater-potion.png)
