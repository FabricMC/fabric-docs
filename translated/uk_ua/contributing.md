---
title: Інструкції щодо внеску
description: Інструкції щодо додавання до документації Fabric.
---

Ця сторінка використовує [VitePress](https://vitepress.dev/) для створення статичного HTML з різних файлів Markdown. Ви повинні ознайомитися з розширеннями Markdown, які підтримує VitePress [тут](https://vitepress.dev/guide/markdown#features).

Ви можете зробити свій внесок у розвиток цієї сторінки трьома способами:

- [Переклад документації](#translating-documentation)
- [Додатковий вміст](#contributing-content)
- [Додатковий каркас](#contributing-framework)

Усі внески мають відповідати нашим [правилам стилю](#style-guidelines).

## Переклад документації {#translating-documentation}

Якщо ви хочете перекласти документацію вашою мовою, ви можете зробити це на [сторінці Fabric Crowdin](https://crowdin.com/project/fabricmc).

<!-- markdownlint-disable-next-line titlecase-rule -->

## <Badge type="tip">new-content</Badge> Додатковий вміст {#contributing-content}

Внесок вмісту є основним способом внеску в документацію Fabric.

Внесок вмісту є основним способом внеску в документацію Fabric.

1. <Badge type="tip">locally</Badge> Підготуйте свої зміни та створіть запит на зміни
2. <Badge type="tip">stage:expansion</Badge>: вказівки щодо розширення, якщо це необхідно
3. <Badge type="tip">stage:verification</Badge>: Перевірка вмісту
4. <Badge type="tip">stage:cleanup</Badge>: Граматика, Лінтування...
5. <Badge type="tip">stage:ready</Badge>: готовий до об’єднання!

Весь вміст має відповідати нашим [правилам стилю](#style-guidelines).

### 1. Підготуйте свої зміни {#1-prepare-your-changes}

Ця сторінка є відкритим кодом і розроблений у репозиторії GitHub, що означає, що ми покладаємося на потік GitHub:

1. [Форк репозиторію GitHub](https://github.com/FabricMC/fabric-docs/fork)
2. Створіть нову гілку у вашому форку
3. Внесіть зміни в цю гілку
4. Відкрийте запит на зміни у вихідному репозиторію

Ви можете прочитати більше про потік GitHub [тут](https://docs.github.com/en/get-started/using-github/github-flow).

Ви можете прочитати більше про потік GitHub [тут](https://docs.github.com/en/get-started/using-github/github-flow).

#### Клонування вашого форку {#clone-your-fork}

Якщо ви хочете розробляти локально, вам потрібно буде встановити [Git](https://git-scm.com/).

Якщо ви хочете розробляти локально, вам потрібно буде встановити [Git](https://git-scm.com/).

```sh
# make sure to replace "your-username" with your actual username
git clone https://github.com/your-username/fabric-docs.git
```

#### Встановлення залежностей {#install-dependencies}

Якщо ви хочете переглянути свої зміни локально, вам потрібно буде встановити [Node.js 18+](https://nodejs.org/en/).

Якщо ви хочете переглянути свої зміни локально, вам потрібно буде встановити [Node.js 18+](https://nodejs.org/en/).

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

### 2. <Badge type="tip">stage:expansion</Badge> Рекомендації щодо розширення, якщо це необхідно {#2-guidance-for-expansion-if-needed}

Якщо команда документації вважає, що ви можете розширити свій запит на зміну, член команди додасть мітку <Badge type="tip">stage:expansion</Badge> до вашого запиту разом із коментарем, у якому пояснюється, що, на їхню думку, ви можете розширити. Якщо ви згодні з пропозицією, ви можете розширити свій запит на отримання.

Якщо ви не хочете розширювати свій запит на зміни, але ви раді, щоб хтось інший розширив його пізніше, вам слід створити проблему на [сторінці проблем](https://github.com/FabricMC/ fabric-docs/issues) і поясніть, що, на вашу думку, можна розширити. Потім команда документації додасть мітку <Badge type="tip">help-wanted</Badge> до вашого запиту.

### 3. <Badge type="tip">stage:verification</Badge> Перевірка вмісту {#3-content-verification}

Це найважливіший етап, оскільки він гарантує, що вміст є точним і відповідає посібнику зі стилю документації Fabric.

Це найважливіший етап, оскільки він гарантує, що вміст є точним і відповідає посібнику зі стилю документації Fabric.

- Чи весь вміст правильний?
- Чи весь вміст актуальний?
- Чи охоплює вміст усі випадки, наприклад різні операційні системи?

### 4. <Badge type="tip">stage:cleanup</Badge> Очищення {#4-cleanup}

На цьому етапі відбувається наступне:

- Виправлення будь-яких граматичних проблем за допомогою [LanguageTool](https://languagetool.org/)
- Лінтування всіх файлів Markdown за допомогою [`markdownlint`](https://github.com/DavidAnson/markdownlint)
- Форматування всього коду Java за допомогою [Checkstyle](https://checkstyle.sourceforge.io/)
- Інші різноманітні виправлення або покращення

## <Badge type="tip">framework</Badge> Додатковий каркас {#contributing-framework}

Каркас стосується внутрішньої структури сторінки, будь-які запити на зміни, які змінюють каркас сторінки, позначатимуться міткою <Badge type="tip">framework</Badge>.

Каркас стосується внутрішньої структури сторінки, будь-які запити на зміни, які змінюють каркас сторінки, позначатимуться міткою <Badge type="tip">framework</Badge>.

:::info
Зміна файлів бічної панелі та налаштування навігаційної панелі не вважається каркасом запитом на зміни.
:::

## Правила стилю {#style-guidelines}

Якщо ви в чомусь не впевнені, ви можете запитати в [Fabric Discord](https://discord.gg/v6v4pMv) або через GitHub Discussions.

### Напишіть оригінал американською англійською {#write-the-original-in-american-english}

Вся оригінальна документація написана англійською мовою з дотриманням американських правил граматики.

### Додайте дані до Frontmatter {#add-data-to-the-frontmatter}

Кожна сторінка повинна мати `description` і `title` на передній частині.

Не забувайте також додати своє ім’я користувача GitHub до `authors` у передній частині файлу Markdown! Таким чином ми можемо надати вам належне згадування.

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

Прив'язка повинна використовувати малі літери, цифри та тире.

### Розмістіть код у моді `/reference` {#place-code-within-the-reference-mod}

Якщо ви створюєте або змінюєте сторінки, що містять код, розмістіть код у відповідному місці в посилання моду (розташованому в теці`/reference` сховища). Потім скористайтеся [функцією фрагмента коду, запропонованою VitePress](https://vitepress.dev/guide/markdown#import-code-snippets), щоб вставити код.

Наприклад, щоб виділити рядки 15-21 файлу `FabricDocsReference.java` з посилання моду:

::: code-group

```md
<<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21}
```

<<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21}[java]

:::

Якщо вам потрібен більший діапазон контролю, ви можете скористатися [функцією перемикання з `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

Якщо вам потрібен більший діапазон контролю, ви можете скористатися [функцією перемикання з `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

::: code-group

```md
@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)
```

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

:::

### Створіть бічну панель для кожного нового розділу {#create-a-sidebar-for-each-new-section}

Якщо ви створюєте новий розділ, вам слід створити нову бічну панель у теці `.vitepress/sidebars` й додати її до файлу `i18n.mts`.

Якщо ви створюєте новий розділ, вам слід створити нову бічну панель у теці `.vitepress/sidebars` й додати її до файлу `i18n.mts`.

### Додайте нові сторінки до відповідних бічних панелей {#add-new-pages-to-the-relevant-sidebars}

Створюючи нову сторінку, ви повинні додати її до відповідної бічної панелі в теці `.vitepress/sidebars`.

Створюючи нову сторінку, ви повинні додати її до відповідної бічної панелі в теці `.vitepress/sidebars`.

### Розмістіть медіа в `/assets` {#place-media-in-assets}

Будь-які зображення слід розміщувати у відповідному місці в теці `/public/assets`.

### Використовуйте відносні посилання! {#use-relative-links}

Це пов’язано з наявною системою керування версіями, яка попередньо оброблятиме посилання для додавання версії. Якщо ви використовуєте абсолютні посилання, номер версії не буде додано до посилання.

Ви також не повинні додавати розширення файлу до посилання.

Ви також не повинні додавати розширення файлу до посилання.

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
