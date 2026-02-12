---
title: Інструкції щодо внеску
description: Інструкції щодо додавання до документації Fabric.
---

Цей сайт використовує [VitePress](https://vitepress.dev/) для створення статичного HTML з різних файлів Markdown. Ви повинні ознайомитися з розширеннями [Markdown, які підтримує VitePress](https://vitepress.dev/guide/markdown#features).

Ви можете зробити свій внесок у розвиток цього сайту трьома способами:

- [Переклад документації](#translating-documentation)
- [Додатковий вміст](#contributing-content)
- [Додатковий фреймворк](#contributing-framework)

Усі внески мають відповідати нашим [правилам стилю](#style-guidelines).

## Переклад документації {#translating-documentation}

Якщо ви хочете перекласти документацію вашою мовою, ви можете зробити це на [сторінці Fabric Crowdin](https://crowdin.com/project/fabricmc).

<!-- markdownlint-disable titlecase-rule -->

## <Badge type="tip">new-content</Badge> Додатковий вміст {#contributing-content}

<!-- markdownlint-enable titlecase-rule -->

Внесок умісту є основним способом внеску в документацію Fabric.

Усі внески вмісту проходять наступні етапи, кожен із яких пов’язаний із міткою:

1. <Badge type="tip">locally</Badge> — підготуйте свої зміни та створіть запит на зміни
2. <Badge type="tip">stage:expansion</Badge> — вказівки щодо розширення, якщо це необхідно
3. <Badge type="tip">stage:verification</Badge> — перевірка вмісту
4. <Badge type="tip">stage:cleanup</Badge> — граматика, лінтування…
5. <Badge type="tip">stage:ready</Badge>: — готово до об’єднання!

Весь вміст має відповідати нашим [правилам стилю](#style-guidelines).

### 1. Підготуйте свої зміни {#1-prepare-your-changes}

Ця сторінка має відкритий код у репозиторії GitHub, що означає, що ми покладаємося на потік GitHub:

1. [Форк репозиторію GitHub](https://github.com/FabricMC/fabric-docs/fork)
2. Створіть нову гілку у вашому форку
3. Унесіть зміни в цю гілку
4. Відкрийте запит на зміни у вихідному репозиторію

Ви можете прочитати більше про [потік GitHub](https://docs.github.com/en/get-started/using-github/github-flow).

Ви можете вносити зміни за допомогою вебінтерфейсу на GitHub або розробляти та переглядати сторінку локально.

#### Клонування вашого форку {#clone-your-fork}

Якщо ви хочете розробляти локально, вам потрібно буде встановити [Git](https://git-scm.com/).

Після цього клонуйте свій форк репозиторію за допомогою:

```sh
# make sure to replace "your-username" with your actual username
git clone https://github.com/your-username/fabric-docs.git
```

#### Установлення залежностей {#install-dependencies}

Якщо ви хочете переглянути свої зміни локально, вам потрібно буде встановити [Node.js 18+](https://nodejs.org/uk/).

Після цього переконайтеся, що встановили всі залежності за допомогою:

```sh
npm install
```

#### Запуск сервера розробки {#run-the-development-server}

Це дозволить вам переглянути ваші зміни локально на `localhost:5173` і автоматично перезавантажить сторінку, коли ви внесете зміни.

```sh
npm run dev
```

Тепер ви можете відкривати та переглядати сторінку у браузері, відвідавши `http://localhost:5173`.

#### Створення сторінки {#building-the-website}

Це скомпілює всі файли Markdown у статичні файли HTML і розмістить їх у `.vitepress/dist`:

```sh
npm run build
```

#### Попередній перегляд створеної сторінки {#previewing-the-built-website}

Це запустить локальний сервер на порту `4173`, який буде обслуговувати вміст, знайдений у теці `.vitepress/dist`:

```sh
npm run preview
```

#### Відкриття запиту на зміни {#opening-a-pull-request}

Після того, як ви задоволені своїми змінами, ви можете `push` свої зміни:

```sh
git add .
git commit -m "Description of your changes"
git push
```

Потім перейдіть за посиланням у виводі `git push`, щоб відкрити запит на зміни.

### 2. <Badge type="tip">stage:expansion</Badge> — рекомендації щодо розширення, якщо це необхідно {#2-guidance-for-expansion-if-needed}

Якщо команда документації вважає, що ви можете розширити свій запит на зміну, член команди додасть мітку <Badge type="tip">stage:expansion</Badge> до вашого запиту разом із коментарем, у якому пояснюється, що, на їхню думку, ви можете розширити. Якщо ви згодні з пропозицією, ви можете розширити свій запит на зміну.

Якщо ви не хочете розширювати свій запит на зміни, але ви раді, щоб хтось інший розширив його пізніше, вам слід створити проблему на [сторінці проблем](https://github.com/FabricMC/ fabric-docs/issues) і поясніть, що, на вашу думку, можна розширити. Потім команда документації додасть мітку <Badge type="tip">help-wanted</Badge> до вашого запиту.

### 3. <Badge type="tip">stage:verification</Badge> — перевірка вмісту {#3-content-verification}

Це найважливіший етап, оскільки він гарантує, що вміст є точним і відповідає посібнику зі стилю документації Fabric.

На цьому етапі необхідно відповісти на наступні питання:

- Увесь вміст правильний?
- Увесь вміст актуальний?
- Чи охоплює вміст усі випадки, наприклад різні операційні системи?

### 4. <Badge type="tip">stage:cleanup</Badge> — очищення {#4-cleanup}

На цьому етапі відбувається наступне:

- Виправлення будь-яких граматичних проблем за допомогою [LanguageTool](https://languagetool.org/)
- Лінтування всіх файлів Markdown за допомогою [`markdownlint`](https://github.com/DavidAnson/markdownlint)
- Форматування усього коду Java за допомогою [Checkstyle](https://checkstyle.sourceforge.io/)
- Інші різноманітні виправлення та покращення

## <Badge type="tip">framework</Badge> — додаткова структура {#contributing-framework}

Фреймворк належить до внутрішньої структури сайту, будь-які запити на зміни, які змінюють фреймворк сайту, позначатимуться міткою <Badge type="tip">framework</Badge>.

Ви дійсно повинні робити запити на зміну фреймворку лише після консультації з командою документації на [Fabric Discord](https://discord.fabricmc.net/) або через проблему.

::: info

Зміна файлів бокової панелі та налаштувань навігаційної панелі не вважається запитом на зміну фреймворку.

:::

## Посібник зі стилю {#style-guidelines}

Якщо ви в чомусь не впевнені, ви можете запитати в [Fabric Discord](https://discord.fabricmc.net/) або через дискусії GitHub.

### Пишіть оригінал американською англійською мовою {#write-the-original-in-american-english}

Уся оригінальна документація написана англійською мовою з дотриманням американських правил граматики.

### Додайте дані до передньої частини {#add-data-to-the-frontmatter}

Кожна сторінка повинна мати `description` і `title` на передній частині.

Не забувайте також додати своє ім’я користувача GitHub до `authors` у передній частині файлу Markdown! Таким чином ми можемо надати вам належну згадку.

```yaml
---
title: Title of the Page
description: This is the description of the page.
authors:
  - your-username
---
```

### Додайте прив’язки до заголовків {#add-anchors-to-headings}

Кожен заголовок повинен мати прив’язку, яка використовується для посилання на цей заголовок:

```md
## This Is a Heading {#this-is-a-heading}
```

Прив'язка повинна використовувати лише малі літери, цифри та дефіс.

### Розмістіть код у прикладному моді {#place-code-within-the-example-mod}

Якщо ви створюєте або змінюєте сторінки, що містять код, розмістіть код у відповідному місці в прикладі мода (розташованого в теці `/reference` сховища). Потім скористайтеся [функцією фрагмента коду, запропонованою VitePress](https://vitepress.dev/guide/markdown#import-code-snippets), аби вставити код.

Наприклад, аби виділити рядки 15-21 файлу `ExampleMod.java` з мода:

::: code-group

```md
<<< @/reference/latest/src/main/java/com/example/docs/ExampleMod.java{15-21}
```

<<< @/reference/latest/src/main/java/com/example/docs/ExampleMod.java{15-21}[java]

:::

Якщо вам потрібен більший діапазон контролю, ви можете скористатися [функцією перенесення з `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

Наприклад, це дозволить вставити розділи файлу вище, позначені теґом `#entrypoint`:

::: code-group

```md
@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)
```

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

:::

### Створіть бокову панель для кожного нового розділу {#create-a-sidebar-for-each-new-section}

Якщо ви створюєте новий розділ, вам слід створити нову бокову панель у теці `.vitepress/sidebars` й додати її до файлу `i18n.mts`.

Якщо вам потрібна допомога з цим, запитайте на каналі `#docs` [Fabric Discord](https://discord.fabricmc.net/).

### Додайте нові сторінки до відповідних бокових панелей {#add-new-pages-to-the-relevant-sidebars}

Створюючи нову сторінку, ви повинні додати її до відповідної бокової панелі в теці `.vitepress/sidebars`.

Знову ж таки, якщо вам потрібна допомога, запитайте у Fabric Discord на каналі `#docs`.

### Розмістіть медіа в `/assets` {#place-media-in-assets}

Будь-які зображення слід розміщувати у відповідному місці в теці `/public/assets`.

### Використовуйте відносні посилання! {#use-relative-links}

Це пов’язано з наявною системою керування версіями, яка попередньо оброблятиме посилання для додавання версії. Якщо ви використовуєте абсолютні посилання, номер версії не буде додано до посилання.

Ви також не повинні додавати розширення файлу до посилання.

Наприклад, щоб зробити посилання на сторінку, знайдену в `/players/index.md` зі сторінки `/develop/index.md`, вам потрібно буде зробити наступне:

::: code-group

```md:no-line-numbers [✅ Correct]
This is a relative link!
[Page](../players/index)
```

```md:no-line-numbers [❌ Wrong]
This is an absolute link.
[Page](/players/index)
```

```md:no-line-numbers [❌ Wrong]
This relative link has the file extension.
[Page](../players/index.md)
```

:::

<!---->
