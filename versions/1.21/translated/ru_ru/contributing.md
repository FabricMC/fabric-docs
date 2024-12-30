---
title: Руководство по внесению вклада
description: Руководство по внесению вклада в документацию Fabric

search: false
---

# Руководство по внесению вклада {#contributing}

Этот сайт использует [VitePress](https://vitepress.dev/) для генерации статического HTML-кода из различных Markdown-файлов. Вы можете ознакомиться с Markdown-расширениями для VitePress [здесь](https://vitepress.dev/guide/markdown#features).

Есть три пути для внесения своего вклада для сайта:

- [Перевод документации](#translating-documentation)
- [Вклад в содержимое](#contributing-content)
- [Как внести свой вклад](#how-to-contribute)

Все материалы должны соответствовать нашим [рекомендациям по стилю](#style-guidelines).

## Перевод документации {#translating-documentation}

Если вы хотите перевести документацию на свой язык, вы можете сделать это на [странице Fabric на Crowdin](https://crowdin.com/project/fabricmc).

<!-- markdownlint-disable-next-line titlecase-rule -->

## <Badge type="tip">new-content</Badge> Внесение контента {#contributing-content}

Основной способ внести вклад в документацию Fabric — это внесение контента.

Все материалы, добавляемые в контент, проходят следующие этапы, каждому из которых соответствует метка:

1. <Badge type="tip">локально</Badge> Подготовьте свои изменения и отправьте PR
2. <Badge type="tip">stage:expansion</Badge>: Руководство по расширению при необходимости
3. <Badge type="tip">этап:проверка</Badge>: проверка содержимого
4. <Badge type="tip">stage:cleanup</Badge>: Грамматика, Линтер...
5. <Badge type="tip">stage:ready</Badge>: Готово к релизу!

Всё содержимое должно соответствовать нашим [рекомендациям по стилю](#style-guidelines).

### 1. Подготовьте свои изменения {#1-prepare-your-changes}

Этот сайт с открытым исходным кодом, он разрабатывается в репозитории GitHub, что означает, что мы полагаемся на поток GitHub:

1. [Создайте форк репозитория на GitHub](https://github.com/FabricMC/fabric-docs/fork)
2. Создайте новую ветку в вашем форке
3. Сделайте свои изменения в этой ветке
4. Откройте запрос на изменение в оригинальном репозитории (Pull Request)

Вы можете прочитать больше о потоке GitHub [здесь](https://docs.github.com/ru/get-started/using-github/github-flow).

Вы можете либо внести изменения из веб-интерфейса на GitHub, либо разработать и предварительно просмотреть сайт локально.

#### Клонирование вашего форка {#clone-your-fork}

Если вы хотите разрабатывать локально, вам нужно будет установить [Git](https://git-scm.com/).

После этого клонируйте свой форк репозитория с помощью:

```sh
# make sure to replace "your-username" with your actual username
git clone https://github.com/your-username/fabric-docs.git
```

#### Установка зависимостей {#install-dependencies}

**Если вы хотите предварительно локально просматривать свои изменения, вам нужно будет установить [Node.js версии 18 или выше](https://nodejs.org/en/)**.

После этого убедитесь, что все зависимости установлены с помощью:

```sh
npm install
```

#### Запуск сервера разработки {#run-the-development-server}

Это позволит вам предварительно просматривать ваши изменения локально по адресу `localhost:5173` и автоматически перезагрузит страницу при внесении изменений.

```sh
npm run dev
```

Теперь вы можете открыть сайт и просматривать его из браузера, посетив `http://localhost:5173`.

#### Создание веб-сайта {#building-the-website}

Это преобразует все файлы Markdown в статические HTML-файлы и разместит их в папке `.vitepress/dist`:

```sh
npm run build
```

#### Предварительный просмотр созданного веб-сайта {#previewing-the-built-website}

Это запустит локальный сервер на порту 3000, отображающий содержимое, найденное в `.vitepress/dist`:

```sh
npm run preview
```

#### Открытие запроса {#opening-a-pull-request}

Если вы довольны своими изменениями, вы можете `push` ваши изменения:

```sh
git add .
git commit -m "Description of your changes"
git push
```

Затем, зайдите по ссылке в выводе команды `git push`, чтобы открыть PR.

### 2. <Badge type="tip">stage:expansion</Badge> Руководство по расширению, если необходимо {#2-guidance-for-expansion-if-needed}

Если команда по документированию считает, что вы могли бы расширить свой запрос, один из членов команды добавит метку <Badge type="tip">stage:expansion</Badge> к вашему запросу на извлечение вместе с комментарием, объясняющим, что, по их мнению, вы могли бы расширить. Если вы согласны с этим предложением, вы можете дополнить свой реквест.

Если по документированию считается, что вы могли бы расширить свой запрос, один из членов команды, добавит метку <0>stage:expansion</0> к вашему запросу вместе с комментарием, объясняющим, что, по их мнению, нужно изменить. Затем группа по документированию добавит метку <Badge type="tip">требуется помощь</Badge> к вашему PR.

### 3. <Badge type="tip">stage:verification</Badge> Проверка содержимого {#3-content-verification}

Это самый важный этап, поскольку он гарантирует точность контента и его соответствие руководству по стилю Fabric Documentation.

На этом этапе необходимо ответить на следующие вопросы:

- Все ли содержания верно?
- Весь ли контент актуален?
- Охватывает ли контент все случаи, например, различные операционные системы?

### 4. <Badge type="tip">этап:очистка</Badge> Очистка {#4-cleanup}

На этом этапе происходит следующее:

- Исправление любых грамматических ошибок с помощью [LanguageTool](https://languagetool.org/)
- Анализ всех файлов Markdown с помощью [`markdownlint`](https://github.com/DavidAnson/markdownlint)
- Форматирование всего кода Java с использованием [Checkstyle](https://checkstyle.sourceforge.io/)
- Другие различные исправления и улучшения

## <Badge type="tip">framework</Badge> Вносящий вклад в фреймворк {#contributing-framework}

Фреймворк относится к внутренней структуре веб-сайта. Любые запросы, которые изменяют фреймворк веб-сайта, будут помечены меткой <Badge type="tip">framework</Badge>.

К вашему сведению, вы должны делать pull requests, касающиеся фреймворка, только после консультации с командой документации на [Discord-сервере Fabric](https://discord.gg/v6v4pMv) или через issue.

:::info
Изменение файлов боковой панели и конфигурации панели навигации не считается pull request, касающимся фреймворка.
:::

## Стандарты оформления {#style-guidelines}

Если вы насчёт чего-то не уверены, можете задать свой вопрос в [Discord-сервере Fabric](https://discord.gg/v6v4pMv) или через GitHub Discussions.

### Корректура (проверка грамматики и т. д.)

Вся оригинальная документация написана на английском языке в соответствии с американскими правилами грамматики.

### Добавляем данные в начало файла {#add-data-to-the-frontmatter}

Все страницы должны иметь заголовок и описание.

Не забывайте также добавлять ваше имя пользователя GitHub в поле `authors` в начале Markdown-файла! Таким образом, мы сможем отдать вам должное.

```md
---
title: Title of the Page
description: This is the description of the page.
authors:
  - your-username
---

# Title of the Page {#title-of-the-page}

...
```

### Добавьте "Якорь" к заголовкам {#add-anchors-to-headings}

Каждый заголовок должен иметь якорь, который используется для создания ссылки на этот заголовок:

```md
# This Is a Heading {#this-is-a-heading}
```

Якорь должен использовать строчные буквы, цифры и дефисы.

### Разместите код внутри `/reference` мода {#place-code-within-the-reference-mod}

Если вы создаёте или изменяете страницы, содержащие код, разместите этот код в каком-нибудь месте мода с примерами (он находится в папке `/reference` репозитория). Фрагмент кода будет выглядеть следующим образом:

Это выведет все линии с 15 по 21 из файла `FabricDocsReference.java` из мода с примерами:

::: code-group

```md
<<< @/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java{15-21}
```

<<< @/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java{15-21}[java]

:::

Затем используйте [функцию вставки кода от VitePress](https://vitepress.dev/guide/markdown#import-code-snippets), чтобы встроить код, или, если вам требуется более широкий контроль, вы можете использовать функцию [transclude из `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

Например, это встроит секции файла выше, которые помечены тегом `#entrypoint`:

::: code-group

```md
@[code transcludeWith=#entrypoint](@/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java)
```

@[code transcludeWith=#entrypoint](@/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java)

:::

### Создавайте боковую панель для каждого нового раздела {#create-a-sidebar-for-each-new-section}

Если вы создаете новую категорию, вам следует создать новую боковую панель в папке `.vitepress/sidebars` и добавить её в файл `config.mts`.

Если вам нужна помощь с этим, спросите [в дискорде Fabric](https://discord.gg/v6v4pMv) в канале `#docs`.

### ⚠️ **Ссылки на другие страницы должны быть относительными.** ⚠️

При создании новой страницы вы должны добавить ее на соответствующую боковую панель в папке `.vitepress/sidebars`.

Опять же, если вам нужна помощь, спросите в дискорде Fabric в канале `#docs`.

### Используйте `/assets` для хранения медиаконтента {#place-media-in-assets}

Любые изображения должны находиться в соответствующем месте в папке `/assets`.

### Используйте относительные ссылки! ⚠️ **Если ты ссылаешься на другие страницы, используй относительные ссылки.** ⚠️ {#use-relative-links}

Это требуется для правильной работы системы с разными версиями, которая заранее обрабатывает ссылки для добавления версии. Если вы используете обычные ссылки, номер версии не будет добавлен к ссылке.

Вы также не должны добавлять расширение файла к ссылке.

Например, для страницы в папке `/players`, ссылка на страницу `installing-fabric` по пути `/players/installing-fabric.md`, вы должны сделать следующее:

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
