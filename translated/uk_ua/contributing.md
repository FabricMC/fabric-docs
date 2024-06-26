# Правила внесення внесків до документації Fabric

Цей вебсайт використовує [VitePress](https://vitepress.dev/) для генерації статичного HTML з різних файлів Markdown. Вам слід ознайомитися з розширеннями Markdown, які підтримує VitePress, [тут](https://vitepress.dev/guide/markdown#features).

## Зміст

- [Правила внесення внесків до документації Fabric](#fabric-documentation-contribution-guidelines)
  - [Як зробити внесок](#how-to-contribute)
  - [Внески до фреймворку](#contributing-framework)
  - [Внески до контенту](#contributing-content)
    - [Рекомендації щодо стилю](#style-guidelines)
    - [Напрямки для розширення](#guidance-for-expansion)
    - [Перевірка вмісту](#content-verification)
    - [Завершення](#cleanup)
  - [Переклад документації](#translating-documentation)

## Як зробити внесок

Рекомендується створювати нову гілку у своєму форку репозиторію для кожного запиту на злиття, який ви робите. Це полегшує управління кількома запитами на злиття одночасно.

**Якщо ви хочете переглянути свої зміни локально, вам потрібно встановити [Node.js 18+](https://nodejs.org/en/)**

Перш ніж виконувати будь-які з цих команд, переконайтеся, що ви виконали `npm install`, щоб встановити всі залежності.

**Запуск сервера розробки:**

Це дозволить вам переглянути свої зміни локально за адресою `localhost:3000` і автоматично перезавантажить сторінку при внесенні змін.

```sh
npm run dev
```

**Побудова вебсайту:**

Це скомпілює всі файли Markdown у статичні HTML-файли та помістить їх у папку `.vitepress/dist`

```sh
npm run build
```

**Перегляд побудованого вебсайту:**

Це запустить локальний сервер на порту 3000, який буде обслуговувати вміст, знайдений у папці `.vitepress/dist`

```sh
npm run preview
```

## Внески до фреймворку

Термін "фреймворк" належить до внутрішньої структури вебсайту. Будь-які запити на злиття, які модифікують фреймворк вебсайту, повинні мати мітку framework.

Вам слід робити запити на злиття фреймворку лише після консультації з командою документації у [Discord-сервері Fabric](https://discord.gg/v6v4pMv) або через створення проблеми.

**Примітка: Зміни в файлах бічної панелі та конфігурації панелі навігації не вважаються запитами на зміни в фреймворку.**

## Внески до вмісту

Внесок контенту є основним способом внесення у документацію Fabric.

Весь вміст повинен відповідати нашим стилевим вимогам.

### Рекомендації щодо стилю

Всі сторінки на вебсайті документації Fabric повинні дотримуватися стилевого керівництва. Якщо ви не впевнені в чомусь, ви можете запитати у [Discord-сервері Fabric](https://discord.gg/v6v4pMv) або через обговорення на GitHub.

Стилеве керівництво:

1. На всіх сторінках повинен бути заголовок та опис на початку файлу.

   ```md
   ---
   title: This is the title of the page
   description: This is the description of the page
   authors:
     - GitHubUsernameHere
   ---

   # ...
   ```

2. Якщо ви створюєте або модифікуєте сторінки з кодом, помістіть код у відповідне місце в довідковому модулі (розташованому у папці `/reference`). Потім використовуйте функціонал вставки коду, пропонований [VitePress](https://vitepress.dev/guide/markdown#import-code-snippets), або, якщо вам потрібно більше контролю, можете використовувати функцію [`transclude` з `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

   **Наприклад:**

   ```md
   <<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21 java}
   ```

   Це вбудує рядки 15-21 файлу `FabricDocsReference.java` у довідковий модуль.

   Отриманий фрагмент коду буде виглядати таким чином:

   ```java
     @Override
     public void onInitialize() {
       // This code runs as soon as Minecraft is in a mod-load-ready state.
       // However, some things (like resources) may still be uninitialized.
       // Proceed with mild caution.

       LOGGER.info("Hello Fabric world!");
     }
   ```

   Приклад функції `transclude`:

   ```md
   @[code transcludeWith=#test_transclude](@/reference/.../blah.java)
   ```

   Це вбудує розділи `blah.java`, які позначені тегом `#test_transclude`.

   Наприклад:

   ```java
   public final String test = "Bye World!"

   // #test_transclude
   public void test() {
     System.out.println("Hello World!");
   }
   // #test_transclude
   ```

   Буде вбудовано лише код між тегами `#test_transclude`.

   ```java
   public void test() {
     System.out.println("Hello World!");
   }
   ```

3. Вся оригінальна документація пишеться англійською мовою, дотримуючись американських правил граматики. Ви можете використовувати [LanguageTool](https://languagetool.org/) для перевірки граматики під час набору тексту, але не переживайте занадто за це. Наша команда документації перегляне та виправить граматичні помилки під час етапу завершення. Проте, спроба правильно сформулювати текст відразу може зекономити наш час.

4. Якщо ви створюєте новий розділ, ви повинні створити нову бічну панель у папці `.vitepress/sidebars` та додати її до файлу `config.mts`. Якщо вам потрібна допомога з цим, будь ласка, запитайте у каналі `#docs` у [Discord-сервері Fabric](https://discord.gg/v6v4pMv).

5. При створенні нової сторінки ви повинні додати її до відповідної бічної панелі у папці `.vitepress/sidebars`. Знову ж таки, якщо вам потрібна допомога, запитайте у каналі `#docs` у [Discord-сервері Fabric](https://discord.gg/v6v4pMv).

6. Будь-які зображення повинні бути розміщені у відповідному місці у папці `/assets`.

7. ⚠️ **При посиланні на інші сторінки використовуйте відносні посилання.** ⚠️

   Це через систему версіювання, яка буде обробляти посилання, щоб додати значення версії. Якщо ви використовуєте абсолютні посилання, значення версії не буде додано до посилання.

   Наприклад, для сторінки у папці `/players`, щоб посилатися на сторінку `installing-fabric`, що знаходиться у `/players/installing-fabric.md`, потрібно зробити так:

   ```md
   [Це посилання на іншу сторінку](./installing-fabric)
   ```

   Ви **НЕ ПОВИННІ** робити наступне:

   ```md
   [Це посилання на іншу сторінку](/players/installing-fabric)
   ```

Всі внески вмісту проходять три етапи:

1. Напрямки для розширення (якщо можливо)
2. Перевірка вмісту
3. Завершення (виправлення граматики й так далі)

### Напрямки для розширення

Якщо команда документації вважає, що ви можете розширити свій запит на злиття, член команди додасть мітку розширення до вашого запиту на злиття поряд із коментарем, пояснюючи, що, на їхню думку, можна було б розширити. Якщо ви згодні з пропозицією, ви можете розширити свій запит на злиття.

**Не відчувайте тиску на розширення свого запиту на злиття.** Якщо ви не хочете розширювати свій запит на злиття, ви можете просто попросити видалити мітку розширення.

Якщо ви не хочете розширювати свій запит на злиття, але ви згодні, щоб хтось інший розширив його в майбутньому, найкраще створити питання на [сторінці запитів](https://github.com/FabricMC/fabric-docs/issues) та пояснити, що, на вашу думку, можна було б розширити.

### Перевірка вмісту

Всі запити на злиття, які додають вміст, проходять перевірку вмісту. Це найважливіший етап, оскільки він забезпечує точність контенту та відповідність стилевому керівництву документації Fabric.

### Завершення

На цьому етапі команда документації виправить будь-які граматичні помилки та внесе будь-які інші зміни, які вони вважають необхідними, перед злиттям запиту на злиття!

## Переклад документації

Якщо ви бажаєте перекласти документацію на свою мову, ви можете це зробити на [сторінці Fabric Crowdin](https://crowdin.com/project/fabricmc).
