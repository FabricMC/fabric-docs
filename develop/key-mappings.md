---
title: Custom Key Mappings
description: Creating custom key mappings and reacting to them.
authors:
  - cassiancc
  - dicedpixels
---

Minecraft handles user input from peripherals such as the keyboard and mouse using key mappings.
Many of these key mappings can be configured through the settings menu.

With help of Fabric API, you can create your own custom key mappings and react to them in your mod.

Key mappings are exclusive to the client. So, registration and reacting to key mappings should
be done on the client side. You can use the **client initializer** for this.

## Creating a Key Mapping {#creating-a-key-mapping}

A key mapping consist of two parts. The mapping to a key and the category it belongs to.

Let's start with creating a category. A category defines a group of key mappings that will be shown
together in the settings menu.

@[code lang=java transcludeWith=:::category](@/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java)

Next, we can create a key mapping. We will be using Fabric API's `KeyBindingHelper` to register
our key mapping at the same time.

@[code lang=java transcludeWith=:::key_mapping](@/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java)

Sticky keys can also be created with `KeyBindingHelper` by passing a `ToggleKeyMapping` instance.

Once registered, you can find your key mappings in _Options_ > _Controls_ > _Key Binds_.

![Untranslated Key Category and Mapping](/assets/develop/key-mappings/untranslated.png)

## Translations {#translations}

You'll need to provide translations for both the key mapping and the category.

Category name translation key takes the form of `key.category.<namespace>.<path>`. The key mapping
translation key will be the one you provided when creating the key mapping.

Translations can be added manually or using [data generation](./data-generation/translations).

```json
{
  "key.category.example-mod.custom_category": "Example Mod Custom Category",
  "key.example-mod.send_to_chat": "Send to Chat"
}
```

![Translated Key Category and Mapping](/assets/develop/key-mappings/translated.png)

## Reacting to Key Mappings {#reacting-to-key-mappings}

Now that we have a key mapping, we can react to it using a client tick event.

@[code lang=java transcludeWith=:::client_tick_event](@/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java)

This will print "Key Pressed!" to the in-game chat every time the mapped key is pressed.

![Message in Chat](/assets/develop/key-mappings/keybind_result.png)
