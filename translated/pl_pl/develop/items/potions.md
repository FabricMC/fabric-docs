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

@[code lang=java transclude={18-27}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Instancja 'StatusEffectInstance' posiada 3 argumenty:

- `RegistryEntry<StatusEffect> type` - Efekt. Używamy naszego własnego efektu tutaj. Alternatywnie, możesz użyć efektów gry niezmodyfikowanej za pomocą domyślnej klasy 'StatusEffects'.
- `int duration` - Czas trwania efektu w tickach.
- `int amplifier` - Mnożnik dla efektu. Na przykład, Pośpiech II będzie mieć mnożnik o wartości 1.

:::info
Aby stworzyć swój własny efekt, proszę zobacz poradnik o [Efektach](../entities/effects).
:::

### Rejestrowanie Mikstury {#registering-the-potion}

W naszym inicjalizatorze będziemy używać wydarzenia 'FabricBrewingRecipeRegistryBuilder.BUILD' aby zarejestrować nasza miksturę używając metody 'BrewingRecipeRegistry.registerPotionRecipe'.

@[code lang=java transclude={29-40}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

'registerPotionRecipe' posiada 3 argumenty:

- `RegistryEntry<Potion> input` - Wpis w rejestrze oryginalnej mikstury. Zazwyczaj może to być Butelka Wody (Water Bottle) albo Niezidentyfikowana Mikstura (Awkward Potion).
- `Item item` - Przedmiot, który jest głównym składnikiem mikstury.
- `RegistryEntry<Potion> output` - Wpis w rejestrze mikstury wyjściowej.

Z zarejestrowaniem, możesz wywarzyć Kartoflowa miksturę używając ziemniaka.

![Efekt w Ekwipunku Gracza](/assets/develop/tater-potion.png)
