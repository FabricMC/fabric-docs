---
title: Власні компоненти даних
description: Дізнайтеся, як додавати власні дані до своїх предметів за допомогою нової системи компонентів 1.20.5.
authors:
  - Romejanic
  - ekulxam
---

Оскільки ваші предмети стають складнішими, вам може знадобитися зберігати спеціальні дані, пов’язані з кожним предметом. Гра дозволяє зберігати постійні дані в `ItemStack`, а з версії 1.20.5 ми це робимо за допомогою **компонентів даних**.

Компоненти даних замінюють дані NBT із попередніх версій структурованими типами даних, які можна застосувати до `ItemStack` для зберігання постійних даних про цей стек. Компоненти даних мають простір назви, тобто ми можемо реалізувати власні компоненти даних для зберігання спеціальних даних про `ItemStack` і доступу до них пізніше. Повний список компонентів даних можна знайти на цій [сторінці вікі Minecraft](https://minecraft.wiki/w/Data_component_format#List_of_components).

Разом із реєстрацією власних компонентів ця сторінка охоплює загальне використання API компонентів, яке також стосується компонентів усталеного типу. Ви можете переглянути та отримати доступ до визначень усіх компонентів у класі `DataComponents`.

## Реєстрація компонентів {#registering-a-component}

Як і будь-що інше у вашому моді, вам потрібно буде зареєструвати свій спеціальний компонент за допомогою `DataComponentType`. Цей тип компонента приймає загальний аргумент, що містить тип значення вашого компонента. Ми зосередимося на цьому більш детально нижче, коли будемо розглядати [звичайні](#basic-data-components) і [розширені](#advanced-data-components) компоненти.

Виберіть розумний клас, щоб розмістити це. Для цього прикладу ми створимо новий пакет під назвою `component` і клас, який буде містити всі наші типи компонентів під назвою `ModComponents`. Переконайтеся, що ви викликаєте `ModComponents.initialize()` у своєму [ініціалізаторі мода](../getting-started/project-structure#entrypoints).

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

Це базовий шаблон для реєстрації типу компонента:

```java
public static final DataComponentType<?> MY_COMPONENT_TYPE = Registry.register(
    BuiltInRegistries.DATA_COMPONENT_TYPE,
    Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "my_component"),
    DataComponentType.<?>builder().persistent(null).build()
);
```

Тут варто звернути увагу на кілька речей. У першому та четвертому рядках ви можете побачити `?`. Це буде замінено типом значення вашого компонента. Ми незабаром заповнимо це.

По-друге, ви повинні надати `Identifier`, що містить призначений ідентифікатор вашого компонента. Це простір назв з ID вашого мода.

Нарешті, у нас є `DataComponentType.Builder`, який створює фактичний екземпляр `DataComponentType`, який реєструється. Тут міститься ще одна важлива деталь, яку нам потрібно буде обговорити: `Кодек` вашого компонента. Наразі це `null`, але незабаром ми його також заповнимо.

## Звичайні компоненти даних {#basic-data-components}

Звичайні компоненти даних (наприклад, `minecraft:damage`) складаються з одного значення даних, наприклад `int`, `float`, `boolean` або `String`.

Як приклад, створімо значення `Integer`, яке відстежуватиме, скільки разів гравець натискає ПКМ, тримаючи наш предмет. Оновімо реєстрацію нашого компонента до такого:

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

Ви бачите, що тепер ми передаємо `<Integer>` як наш загальний тип, вказуючи, що цей компонент зберігатиметься як одне значення `int`. Для нашого кодека ми використовуємо наданий кодек `ExtraCodecs.POSITIVE_INT`. Ми можемо обійтися використанням звичайних кодеків для таких простих компонентів, як цей, але для складніших сценаріїв може знадобитися спеціальний кодек (про це коротко розглянемо пізніше).

Якщо ви запустите гру, ви зможете ввести таку команду:

![/give показує власні компоненти](/assets/develop/items/custom_component_0.png)

Коли ви виконуєте команду, ви повинні отримати предмет, що містить компонент. Однак наразі ми не використовуємо наш компонент, щоб зробити щось корисне. Почнімо з читання значення компонента таким чином, який ми можемо побачити.

## Читання значення компонента {#reading-component-value}

Додаймо новий предмет, який буде збільшувати лічильник щоразу, коли ним натиснули ПКМ. Вам слід прочитати сторінку [власні інтерактивні предмети](./custom-item-interactions), на якій описано методи, які ми використовуватимемо в цьому посібнику.

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

Не забудьте зареєструвати предмет у своєму класі `ModItems`.

```java
public static final Item COUNTER = register("counter", CounterItem::new, new Item.Properties());
```

Ми збираємося додати код підказки, щоб показати поточне значення кількості натискань, коли ми наводимо курсор на наш предмет в інвентарі. Ми можемо використати метод `get()` у нашому `ItemStack`, щоб отримати значення нашого компонента таким чином:

```java
int count = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
```

Це поверне поточне значення компонента як тип, який ми визначили під час реєстрації нашого компонента. Потім ми можемо використовувати це значення, щоб додати запис підказки. Додайте цей рядок до методу `appendHoverText` у класі `CounterItem`:

```java
public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
  int count = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
  textConsumer.accept(Component.translatable("item.example-mod.counter.info", count).withStyle(ChatFormatting.GOLD));
}
```

::: warning

Починаючи з версії 1.21.5, `appendHoverText` застаріло. Тепер рекомендовано реалізувати `TooltipProvider` як такий. Для цього знадобиться [створення спеціального класу компонентів](#advanced-data-components).

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/component/ComponentWithTooltip.java)

Потім ви можете зареєструвати `TooltipProvider` через `ComponentTooltipAppenderRegistry`. Це викликається в `onInitialize` в `ModInitializer`.

@[code lang=java transcludeWith=#tooltip_provider](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

Крім того, ви можете використовувати `ItemTooltipCallback`, щоб замінити `appendHoverText`. Це викликається в `onInitializeClient` в `ClientModInitializer`.

@[code lang=java transcludeWith=#tooltip_provider_client](@/reference/latest/src/client/java/com/example/docs/ExampleModClient.java)

:::

Не забудьте оновити файл мови (`/assets/example-mod/lang/en_us.json`, для української `uk_ua.json`) і додати ці два рядки:

```json
{
  "item.example-mod.counter": "Counter",
  "item.example-mod.counter.info": "Used %1$s times"
}
```

Запустіть гру та виконайте цю команду, щоб отримати новий лічильник із кількістю 5.

```mcfunction
/give @p example-mod:counter[example-mod:click_count=5]
```

Коли ви наведете курсор на цей предмет у своєму інвентарі, ви побачите кількість, що показується у спливній підказці!

![Спливна підказка, у якій показано «Використано 5 разів»](/assets/develop/items/custom_component_1.png)

Однак, якщо ви дасте собі новий лічильник _без_ спеціального компонента, гра аварійно завершить роботу, коли ви наведете курсор на предмет у своєму інвентарі. Ви повинні побачити таку помилку у звіті про збій:

```log
java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "net.minecraft.world.item.ItemStack.get(net.minecraft.core.component.DataComponentType)" is null
        at com.example.docs.item.custom.CounterItem.appendHoverText(LightningStick.java:45)
        at net.minecraft.world.item.ItemStack.getTooltipLines(ItemStack.java:767)
```

Як і очікувалося, оскільки `ItemStack` наразі не містить екземпляра нашого спеціального компонента, виклик `stack.get()` із нашим типом компонента поверне значення `null`.

Є три рішення, які ми можемо використати для розв'язання цієї проблеми.

### Установлення усталеного значення компонента {#setting-default-value}

Коли ви реєструєте свій предмет і передаєте об’єкт `Item.Properties` своєму конструктору предмета, ви також можете надати список компонентів усталено, які застосовуються до всіх нових предметів. Якщо ми повернемося до нашого класу `ModItems`, де ми реєструємо `CounterItem`, ми зможемо додати усталене значення для нашого спеціального компонента. Додайте це, щоб нові предмети показували кількість «0».

@[code transcludeWith=::\_13](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Коли створюється новий предмет, до нього автоматично застосовуватиметься наш спеціальний компонент із заданим значенням.

::: warning

За допомогою команд можна видалити усталений компонент із `ItemStack`. Вам слід звернутися до наступних двох розділів, щоб правильно впоратися зі сценарієм, коли компонент відсутній у вашому предметі.

:::

### Читання з усталених значенням {#reading-default-value}

Крім того, під час читання значення компонента ми можемо використовувати метод getOrDefault() нашого об’єкта `ItemStack`, щоб повернути вказане усталене значення, якщо компонент відсутній у стосі. Це захистить від будь-яких помилок, спричинених відсутнім компонентом. Ми можемо налаштувати код підказки так:

```java
int count = stack.getOrDefault(ModComponents.CLICK_COUNT_COMPONENT, 0);
```

Як бачите, цей метод приймає два аргументи: тип нашого компонента, як і раніше, і усталене значення яке повертається, якщо компонента немає.

### Перевірка наявності компонента {#checking-if-component-exists}

Ви також можете перевірити наявність певного компонента в `ItemStack` за допомогою методу `has()`. Це приймає тип компонента як аргумент і повертає `true` або `false` залежно від того, чи містить стек цей компонент.

```java
boolean exists = stack.has(ModComponents.CLICK_COUNT_COMPONENT);
```

### Виправлення помилки {#fixing-the-error}

Ми підемо до третього варіанту. Тож разом із додаванням усталеного значення компонента також перевіримо, чи присутній компонент у стосі, і покажемо лише підказку, якщо вона є.

@[code transcludeWith=::3](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

Запустіть гру ще раз і наведіть вказівник мишки на предмет без компонента, ви повинні побачити, що він показує «Used 0 times» і більше не завершує роботу гри.

![Спливна підказка показує «Використано 0 разів»](/assets/develop/items/custom_component_2.png)

Спробуйте створити собі лічильник, видаливши наш спеціальний компонент. Для цього можна використати цю команду:

```mcfunction
/give @p example-mod:counter[!example-mod:click_count]
```

При наведенні курсора на цей предмет підказка повинна бути відсутня.

![Зустрічний предмет без підказки](/assets/develop/items/custom_component_7.png)

## Оновлення значення компоненту {#setting-component-value}

Тепер спробуймо оновити значення нашого компонента. Ми будемо збільшувати кількість натискань кожного разу, коли використовуємо наш лічильник. Щоб змінити значення компонента в `ItemStack`, ми використовуємо метод `set()` таким чином:

```java
stack.set(ModComponents.CLICK_COUNT_COMPONENT, newValue);
```

Це бере наш тип компонента та значення, яке ми хочемо встановити. У цьому випадку це буде наша нова кількість натискань. Цей метод також повертає старе значення компонента (якщо воно є), що може бути корисним у деяких ситуаціях. Наприклад:

```java
int oldValue = stack.set(ModComponents.CLICK_COUNT_COMPONENT, newValue);
```

Налаштуймо новий метод `use()`, щоб зчитувати стару кількість натискань, збільшити її на один, а потім встановити оновлену кількість натискань.

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

Тепер спробуйте запустити гру та натиснути ПКМ з предметом лічильника в руці. Якщо ви відкриєте свій інвентар і подивіться на предмет знову, ви побачите, що число використання зросло на кількість разів, які ви натискали на нього.

![Спливна підказка показує «Used 8 times»](/assets/develop/items/custom_component_3.png)

## Видалення значення компонента {#removing-component-value}

Ви також можете видалити компонент зі свого `ItemStack`, якщо він більше не потрібен. Це робиться за допомогою методу `remove()`, який приймає тип вашого компонента.

```java
stack.remove(ModComponents.CLICK_COUNT_COMPONENT);
```

Цей метод також повертає значення компонента перед видаленням, тому ви також можете використовувати його наступним чином:

```java
int oldCount = stack.remove(ModComponents.CLICK_COUNT_COMPONENT);
```

## Розширенні компоненти даних {#advanced-data-components}

Вам може знадобитися зберегти кілька атрибутів в одному компоненті. Наприклад, компонент `minecraft:food` зберігає кілька значень, пов’язаних із їжею, як-от `nutrition`, `saturation`, `eat_seconds` тощо. У цьому посібнику ми називатимемо їх «композитними» компонентами.

Для складених компонентів ви повинні створити клас `record` для зберігання даних. Це тип, який ми зареєструємо в нашому типі компонента, і що ми будемо читати та записувати під час взаємодії з `ItemStack`. Почніть зі створення нового класу записів у пакеті `component`, який ми створили раніше.

```java
public record MyCustomComponent() {
}
```

Зверніть увагу, що після назви класу є набір дужок. Тут ми визначаємо список властивостей, які ми хочемо мати у нашого компонента. Додаймо float і логічне значення, які називаються `temperature` і `burnt` відповідно.

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/component/MyCustomComponent.java)

Оскільки ми визначаємо спеціальну структуру даних, для нашого випадку використання не буде попередньо наявного `Codec`, як у випадку з [базовим компонентом](#basic-data-components). Це означає, що нам доведеться створити власний кодек. Визначмо один у нашому класі записів за допомогою `RecordCodecBuilder`, на який ми зможемо посилатися після реєстрації компонента. Щоб отримати докладніші відомості про використання `RecordCodecBuilder`, ви можете звернутися до [цього розділу сторінки кодеків](../codecs#merging-codecs-for-record-like-classes).

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/component/MyCustomComponent.java)

Ви бачите, що ми визначаємо список настроюваних полів на основі примітивних типів `Codec`. Однак ми також повідомляємо їй, як називаються наші поля, використовуючи `fieldOf()`, а потім використовуючи `forGetter()`, щоб повідомити грі, який атрибут нашого запису заповнити.

Ви також можете визначити необов’язкові поля, використовуючи `optionalFieldOf()` і передаючи усталене значення як другий аргумент. Будь-які поля, не позначені як додаткові, будуть обов’язковими під час налаштування компонента за допомогою `/give`, тому переконайтеся, що ви позначили будь-які необов’язкові аргументи як такі під час створення кодека.

Нарешті, ми викликаємо `apply()` і передаємо конструктор нашого запису. Щоб дізнатися більше про те, як створити кодеки та про складніші варіанти використання, обов’язково прочитайте сторінку [кодеків](../codecs).

Реєстрація складеного компонента аналогічна попередній. Ми просто передаємо наш клас запису як загальний тип, а наш настроюваний `Codec` — методу `codec()`.

@[code transcludeWith=::3](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

Тепер запустіть гру. Використовуючи команду `/give`, спробуйте застосувати компонент. Значення складених компонентів передаються як об’єкт, укладений у `{}`. Якщо ви поставите порожні фігурні дужки, ви побачите повідомлення про помилку про те, що необхідний ключ `temperature` відсутній.

![Надайте команду, яка показує відсутній ключ «temperature»](/assets/develop/items/custom_component_4.png)

Додайте значення температури до об’єкта за допомогою синтаксису `temperature:8.2`. Ви також можете додатково передати значення для `burnt`, використовуючи той самий синтаксис, але або `true` або `false`. Тепер ви маєте побачити, що команда дійсна та може надати вам предмет, що містить компонент.

![Дійсна команда give, яка показує обидві властивості](/assets/develop/items/custom_component_5.png)

### Отримання, налаштування та видалення додаткових компонентів {#getting-setting-removing-advanced-comps}

Використання компонента в коді таке ж, як і раніше. Використання `stack.get()` поверне екземпляр вашого класу `record`, який потім можна використовувати для читання значень. Оскільки записи доступні лише для читання, вам потрібно буде створити новий екземпляр свого запису, щоб оновити значення.

```java
// read values of component
MyCustomComponent comp = stack.get(ModComponents.MY_CUSTOM_COMPONENT);
float temp = comp.temperature();
boolean burnt = comp.burnt();

// set new component values
stack.set(ModComponents.MY_CUSTOM_COMPONENT, new MyCustomComponent(8.4f, true));

// check for component
if (stack.contains(ModComponents.MY_CUSTOM_COMPONENT)) {
    // do something
}

// remove component
stack.remove(ModComponents.MY_CUSTOM_COMPONENT);
```

Ви також можете встановити усталене значення для складеного компонента, передавши об’єкт компонента у ваш `Item.Properties`. Наприклад:

```java
public static final Item COUNTER = register(
    "counter",
    CounterItem::new,
    new Item.Properties().component(ModComponents.MY_CUSTOM_COMPONENT, new MyCustomComponent(0.0f, false))
);
```

Тепер ви можете зберігати власні дані в `ItemStack`. Використовуйте відповідально!

![Предмет із підказками щодо кількості натискань, температури та згорань](/assets/develop/items/custom_component_6.png)
