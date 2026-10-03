---
title: Динамічна реєстрація
description: "Дізнайтеся, про динамічні реєстри: що це таке, коли вони стають у пригоді та як створити власний за допомогою Fabric API."
authors:
  - Jimmy474
---

Реєстр — це централізована «телефонна книга», що зіставляє унікальні ID, як-от `minecraft:items`, із конкретними об'єктами.

Існує два типи реєстрів: статичні реєстри, як-от реєстри блоків і предметів, які фіксуються під час запуску, тоді як динамічні, тобто власні, заповнюються під час виконання програми даними з JSON-файлів у пакетах даних.

Вони корисні з багатьох причин:

- Вони відділяють логіку від умісту.
- Інші творці модів можуть додавати новий уміст за допомогою пакетів даних, замість того щоб вносити зміни безпосередньо у ваш код.
- Гравці можуть змінювати стандартні значення, як-от вартість у мані чи ціну покращення, замінюючи записи даних у пакеті даних.
- Динамічні реєстри належать конкретному світу. Вони завантажуються під час відкриття світу й очищується, коли цей світ закривається.
- Динамічні реєстри розв'язують проблему «жорстко закодованого вмісту». Замість того щоб жорстко прописувати кожне вміння, завдання чи покращення безпосередньо в коді Java за допомогою переліків або статичних списків, ви визначаєте в коді шаблон, а фактичний вміст завантажуєте з даних.

Створімо динамічний реєстр для системи магічних умінь.

## Налаштування класу {#class-setup}

Спершу створіть клас, що представляє запис у реєстрі. Це простий контейнер даних для значень, пов’язаних із кожним магічним умінням, як-от назва, вартість у мані тощо. Для кодування та декодування запису потрібен [`Codec`](./codecs).

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MagicSkillsRegistryEntry.java#main

- `name` — назва вміння.
- `manaCost` — вартість у мані для вміння.
- `onUseMcFunction` — [функція](https://minecraft.wiki/w/Function_(Java_Edition)), яку сервер може виконувати при використанні вміння. Наявність цього в реєстрі дозволить іншим пакетам даних налаштовувати логіку будь-якого вміння або додавати нові вміння з власними функціями.

## Реєстрація реєстру {#registering-the-registry}

Кожен реєстр реєструється за допомогою ключа, що його однозначно ідентифікує, тож створимо цей ключ і клас для його зберігання. Ми назвемо його `ExampleModRegistries`:

::: tip

Рекомендується оголошувати ключі реєстру в спільному класі, оскільки це полегшить керування кількома реєстрами.

:::

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#main

Виклик `ExampleModRegistries.initialize()` з нашого [ініціалізатора мода](./getting-started/project-structure#entrypoints).

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModDynamicRegistries.java#main

Потім зареєструйте його за допомогою `DynamicRegistries` з Fabric API, що пропонує дві різні стратегії: `DynamicRegistries.register()` або `DynamicRegistries.registerSynced()`.

### Використання `register()` {#using-register}

`DynamicRegistries.register()` створює несинхронізований реєстр. Він є лише на сервері, але немає на клієнті. Використовуйте це, коли клієнту ніколи не потрібно читати реєстр.

У нашому прикладі це не має значення, але ось як це зробити:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#simple

### Використання `registerSynced()` {#using-register-synced}

::: info

Ключі, що використовуються в наведених нижче методах, створюються так само, як ми створили [`MAGIC_SKILLS_REGISTRY_KEY`](#registering-the-registry), але з іншою назвою.

:::

`DynamicRegistries.registerSynced()` створює синхронізований реєстр. Коли клієнт приєднується до світу, сервер автоматично синхронізує дані цього реєстру з клієнтом. Використовуйте це, коли клієнту потрібні дані для рендера, інтерфейсу, підказок або іншої логіки на стороні клієнта.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#synced

`DynamicRegistries.registerSynced()` має перевантаження, яке приймає другий кодек для декодування на стороні клієнта. Це корисно, якщо клієнту не потрібні всі поля з повного запису на сервері.

У нашому випадку на клієнтській стороні потрібні лише поля [`name` і `manaCost`](#class-setup), тому створимо [`Codec`](./codecs), який не містить `onUseMcFunction`, і передамо його до `registerSynced`:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MagicSkillsRegistryEntry.java#client_codec

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#double_codec

### `SyncOption` {#sync-option}

Обидві перевантажені версії методу `DynamicRegistries.registerSynced()` приймають аргументи `SyncOption` у кінці списку параметрів для налаштування поведінки синхронізації. Єдиний доступний варіант для використання:

- `SKIP_WHEN_EMPTY`: Синхронізує реєстр лише тоді, коли він містить записи. Це може допомогти із сумісністю для клієнтів, яким може не знадобитися реєстр.

Приклад:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#with_option

## Заповнення реєстру {#populating-the-registry}

JSON-файли використовуються для створення записів у реєстрі. Структура JSON повинна відповідати [`MagicSkillsRegistryEntry`](#class-setup). У цьому прикладі наш клас запису має три поля, тому JSON-файл для запису `healing_skill` може виглядати так:

<<< @/reference/latest/src/main/generated/data/example-mod/example-mod/magic_skills_synced_registry/healing_skill.json

Початкові файли JSON зберігаються в теці `src/main/resources/data/example-mod/example-mod/magic_skills_registry/`.

::: info

Повторення `example-mod/example-mod` не є помилкою.

Перший `example-mod` — це простір імен запису, що додається. Другий `example-mod` походить від самого ID реєстру. Використання ID вашого мода для обох випадків є стандартною практикою; це дозволяє іншим модам або пакетам даних додавати записи до вашого реєстру, використовуючи власні простори імен.

Наприклад, `another-mod` може захотіти додати елементи до нашого `magic_skills_registry`; для цього він використовуватиме файли, розміщені за шляхом `src/main/resources/data/another-mod/example-mod/magic_skills_registry/`.

:::

### ID запису {#entry-id}

ID запису — це унікальний ключ для кожного запису; він може бути корисним для доступу до конкретного запису в реєстрі. Він складається з імені файлу та [ключа реєстру](#registering-the-registry). Наприклад, оскільки наш початковий JSON-файл має назву `healing_skill.json`, ID запису:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#entry_id

## Доступ до даних реєстру {#accessing-the-registry-data}

Динамічні реєстри завантажуються разом зі світом; отримати до них доступ можна через клас `RegistryAccess`, використовуючи ключ вашого реєстру.
Екземпляри `RegistryAccess` можна отримати з багатьох класів, найпоширенішими з яких є `MinecraftServer`, `ServerLevel`, `ClientLevel`, `Entity` тощо.

:::warning ВАЖЛИВО

Під час доступу до екземпляра `RegistryAccess` із класу, призначеного виключно для клієнтської частини, як-от, `ClientLevel`, доступні лише [синхронізовані реєстри](#using-register-synced).

:::

### Отримання запису реєстру {#get-the-entire-registry}

До реєстрів можна отримати доступ за допомогою методу `lookup` класу `RegistryAccess`, який повертає `Optional<Registry<T>>`, де `T` — це тип реєстру.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#get_registry

### Отримання конкретних записів {#get-a-specific-entry}

Доступ до конкретних записів можна отримати за допомогою методу `get` класу `RegistryAccess`, який повертає `Optional<Holder.Reference<T>>`, де `T` — це тип реєстру.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#get_specific_registry_entry

Ознайомтеся з [ID запису](#entry-id), щоб дізнатися, як отримати `HEALING_SKILL_ENTRY_ID`.

У нашому випадку ми можемо використати цей метод, щоб отримати запис про магічну здібність, яку користувач застосував на сервері, а потім видобути поле [`onUseMcFunction`](#class-setup) для виконання mcfunction.

### Перебір усіх записів {#iterate-over-all-entries}

Записи реєстру ітерувати можна для різних цілей, наприклад, для заповнення елементів інтерфейсу. У нашому випадку ми можемо використати цей метод, щоб заповнити екран власними віджетами, як-от:

<<< @/reference/latest/src/client/java/com/example/docs/dynamic_registries/screens/ExampleModMagicSkillsScreen.java#iterate_over_registry_entries

:::details Спеціальний екран, що заповнюється даними з реєстру

![Приклад екрана магічних умінь](/assets/develop/dynamic_registry/magic_skills_screen.png)

Дізнайтеся більше про створення власних [екранів](./rendering/gui/custom-screens) і [віджетів](./rendering/gui/custom-widgets).

:::

## Теґи для спеціальних записів реєстру {#tags-for-custom-registry-entries}

Теґи — це спосіб групування кількох записів. Наприклад, ми можемо створити теґи на кшталт _attack_ та _defense_, щоб згрупувати схожі магічні вміння.

Наприклад, теґ атаки буде визначено в `data/example-mod/tags/example-mod/magic_skills_registry/attacking_skills.json`:

<<< @/reference/latest/src/main/generated/data/example-mod/tags/example-mod/magic_skills_synced_registry/attacking_skills.json

### Використання теґів у коді {#using-tags-in-code}

Створіть ключ теґу, щоб перевірити, чи містить цей теґ записи.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModTags.java#tag

Ми можемо використовувати цей метод, щоб перевірити, чи є вміння атаки.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModTags.java#tag_usage
