# Руководство по внесению вклада в документацию Fabric

Этот сайт использует [VitePress](https://vitepress.dev/) для генерации статического HTML-кода из различных Markdown-файлов. Вы можете ознакомиться с Markdown-расширениями для VitePress [здесь](https://vitepress.dev/guide/markdown#features).

## Содержание

- [Руководство по внесению вклада в документацию Fabric](#fabric-documentation-contribution-guidelines)
  - [Как внести свой вклад](#how-to-contribute)
  - [Вклад в фреймворк](#contributing-framework)
  - [Вклад в содержимое](#contributing-content)
    - [Стандарты оформления](#style-guidelines)
    - [Справка по дополнениям](#guidance-for-expansion)
    - [Проверка содержимого](#content-verification)
    - [Полировка](#cleanup)
  - [Перевод документации](#translating-documentation)

## Как внести свой вклад

Создавайте новые ветки в вашем форке на каждый pull request, что делаете. Это упрощает одновременное управление несколькими pull requests.

**Если вы хотите предварительно локально просматривать свои изменения, вам нужно будет установить [Node.js версии 18 или выше](https://nodejs.org/en/)**.

Прежде чем выполнять любые из следующих команд, убедитесь, что выполнили команду `npm install` для установки всех зависимостей.

**Запуск сервера для разработки:**

Это позволит вам предварительно просматривать ваши изменения локально по адресу `localhost:3000` и автоматически перезагрузит страницу при внесении изменений.

```sh
npm run dev
```

**Компиляция веб-сайта:**

Это преобразует все файлы Markdown в статические HTML-файлы и разместит их в папке `.vitepress/dist`

```sh
npm run build
```

**Предварительный просмотр собранного веб-сайта:**

Это запустит локальный сервер на порту 3000, отображающий содержимое, найденное в `.vitepress/dist`

```sh
npm run preview
```

## Вклад в развитие фреймворка

Под фреймворком понимается внутренняя структура веб-сайта; любые pull requests, изменяющие фреймворк сайта, должны быть помечены меткой `framework`.

К вашему сведению, вы должны делать pull requests, касающиеся фреймворка, только после консультации с командой документации на [Discord-сервере Fabric](https://discord.gg/v6v4pMv) или через issue.

**Примечание: Изменение файлов боковой панели и конфигурации панели навигации не считается pull request, касающимся фреймворка.**

## Вклад в содержимое

Вклад в содержимое является основным способом внести свой вклад в документацию Fabric.

Все материалы должны соответствовать нашим стандартам оформления.

### Стандарты оформления

Все страницы веб-сайта документации Fabric должны следовать стандартам оформления. Если вы насчёт чего-то не уверены, можете задать свой вопрос в [Discord-сервере Fabric](https://discord.gg/v6v4pMv) или через GitHub Discussions.

Наши стандарты оформления включают следующее:

1. Все страницы должны иметь заголовок и описание.

   ```md
   ---
   title: Это заголовок страницы
   description: Это описание страницы
   authors:
     - ТутЧьё-тоИмяПользователяСGitHub
   ---

   # ...
   ```

2. Если вы создаёте или изменяете страницы, содержащие код, разместите этот код в каком-нибудь месте мода с примерами (он находится в папке `/reference` репозитория). Затем используйте [функцию вставки кода от VitePress](https://vitepress.dev/guide/markdown#import-code-snippets), чтобы встроить код, или, если вам требуется более широкий контроль, вы можете использовать функцию [transclude из `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

   **Пример:**

   ```md
   <<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21 java}
   ```

   Это выведет все линии с 15 по 21 из файла `FabricDocsReference.java` из мода с примерами.

   Фрагмент кода будет выглядеть следующим образом:

   ```java
     @Override
     public void onInitialize() {
       // This code runs as soon as Minecraft is in a mod-load-ready state.
       // However, some things (like resources) may still be uninitialized.
       // Proceed with mild caution.

       LOGGER.info("Hello Fabric world!");
     }
   ```

   **Пример с Transclude:**

   ```md
   @[code transcludeWith=#test_transclude](@/reference/.../blah.java)
   ```

   Таким образом будут вставлены фрагменты из `blah.java`, помеченные тегом `#test_transclude`.

   Пример:

   ```java
   public final String test = "Bye World!"

   // #test_transclude
   public void test() {
     System.out.println("Hello World!");
   }
   // #test_transclude
   ```

   Код между тегами `#test_transclude` будет выведен.

   ```java
   public void test() {
     System.out.println("Hello World!");
   }
   ```

3. Вся оригинальная документация написана на английском языке в соответствии с американскими правилами грамматики. Но вы можете использовать [LanguageTool](https://languagetool.org/) для проверки грамматики при вводе текста, не придав этому особого значения. Наша команда документации может проанализировать и исправить грамматику на этапе очистки. Однако, вы сэкономите нам время, если изначально всё будет правильно.

4. Если вы создаете новую категорию, вам следует создать новую боковую панель в папке `.vitepress/sidebars` и добавить её в файл `config.mts`. Если вам нужна помощь с этим, спросите [в дискорде Fabric](https://discord.gg/v6v4pMv) в канале `#docs`.

5. При создании новой страницы вы должны добавить ее на соответствующую боковую панель в папке `.vitepress/sidebars`. Опять же, если вам нужна помощь, спросите в дискорде Fabric в канале `#docs`.

6. Любые изображения должны находиться в соответствующем месте в папке `/assets`.

7. ⚠️ **Ссылки на другие страницы должны быть относительными.** ⚠️

   Это требуется для правильной работы системы с разными версиями, которая заранее обрабатывает ссылки для добавления версии. Если вы используете обычные ссылки, номер версии не будет добавлен к ссылке.

   Например, для страницы в папке `/players`, ссылка на страницу `installing-fabric` по пути `/players/installing-fabric.md`, вы должны сделать следующее:

   ```md
   [Ссылка на другую страницу](./installing-fabric)
   ```

   Вы **НЕ** должны делать так:

   ```md
   [Ссылка на другую страницу](/players/installing-fabric)
   ```

Все материалы проходят три этапа:

1. Справка по дополнению (если нужно)
2. Проверка содержимого
3. Корректура (проверка грамматики и т. д.)

### Справка по дополнениям

Если команда документации сочтёт, что ваш pull request можно дополнить, один из членов команды добавит метку `can-expand` к нему и оставит комментарий с пояснением, что именно можно добавить. Если вы согласны с предложением, вы можете принять его. Если вы согласны с этим предложением, вы можете дополнить свой реквест.

**Вы не обязаны расширять свой pull request.** Если вы не хотите вносить дополнительные изменения в ваш pull request, просто попросите убрать метку `can-expand`.

Если вы не хотите дополнять свой реквест, но будете рады, что кто-то другой дополнит его позже, лучше всего создать пост [на странице проблем](https://github.com/FabricMC/fabric-docs/issues) и объяснить, что, по вашему мнению, можно было бы дополнить.

### Проверка содержимого

Все реквесты, добавляющие содержимое, проходят проверку. Это самый важный этап, поскольку он гарантирует, что контент является точным и соответствует стандартам стиля документации Fabric.

### Полировка

На этом этапе наша команда исправит любые грамматические ошибки и внесёт некоторые изменения, которые она сочтёт необходимыми, прежде чем объединить реквест с основным проектом!

## Перевод документации

Если вы хотите перевести документацию на свой язык, вы можете сделать это на [странице Fabric на Crowdin](https://crowdin.com/project/fabricmc).
