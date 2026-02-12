---
title: Автотестування
description: Посібник із написання автоматичних тестів за допомогою Fabric Loader JUnit.
authors:
  - kevinthegreat1
---

На цій сторінці пояснюється, як написати код для автоматичного тестування частин вашого мода. Є два способи автоматично перевірити ваш мод: модульні тести за допомогою Fabric Loader JUnit або ігрові тести за допомогою фреймворку Gametest від Minecraft.

Модульний тест слід використовувати для тестування компонентів вашого коду, таких як методи та допоміжні класи, тоді як ігрові тести запускають реальний клієнт і сервер Minecraft для запуску ваших тестів, що робить його придатним для тестування функцій і ігрового процесу.

## Модульне тестування {#unit-testing}

Оскільки модифікація Minecraft покладається на інструменти модифікації байт-коду під час виконання, такі як Mixin, просто додавання та використання JUnit зазвичай не спрацює. Ось чому Fabric надає Fabric Loader JUnit, плаґін JUnit, який дає змогу проводити модульне тестування в Minecraft.

### Налаштування Fabric Loader JUnit {#setting-up-fabric-loader-junit}

По-перше, нам потрібно додати Fabric Loader JUnit до середовища розробки. Додайте наступне до блока залежностей у вашому `build.gradle`:

@[code transcludeWith=:::automatic-testing:1](@/reference/build.gradle)

Потім нам потрібно сказати Gradle використовувати Fabric Loader JUnit для тестування. Ви можете зробити це, додавши наступний код до свого `build.gradle`:

@[code transcludeWith=:::automatic-testing:2](@/reference/latest/build.gradle)

### Писання тестів {#writing-tests}

Перезавантаживши Gradle, ви готові писати тести.

Ці тести написані так само, як звичайні тести JUnit, з деякими додатковими налаштуваннями, якщо ви хочете отримати доступ до будь-якого залежного від реєстру класу, наприклад `ItemStack`. Якщо вам зручно працювати з JUnit, ви можете перейти до [налаштування реєстрів](#setting-up-registries).

#### Налаштування вашого першого тестового класу {#setting-up-your-first-test-class}

Тести записуються в каталозі `src/test/java`.

Однією з угод про іменування є показ структури пакета класу, який ви тестуєте. Наприклад, щоб перевірити `src/main/java/com/example/docs/codec/BeanType.java`, ви повинні створити клас у `src/test/java/com/example/docs/codec/BeanTypeTest..java`. Зверніть увагу, як ми додали `Test` в кінці назви класу. Це також дозволяє вам легко отримати доступ до приватних методів і полів пакета.

Інша угода про іменування полягає в наявності пакета `test`, наприклад `src/test/java/com/example/docs/test/codec/BeanTypeTest.java`. Це запобігає деяким проблемам, які можуть виникнути під час використання того самого пакета, якщо ви використовуєте модулі Java.

Після створення тестового класу натисніть <kbd>⌘/CTRL</kbd>+<kbd>N</kbd>, щоб відкрити меню «Згенерувати». Виберіть Test і почніть вводити назву методу, зазвичай починаючи з `test`. Натисніть <kbd>ENTER</kbd>, коли закінчите. Щоб отримати додаткові поради та підказки щодо використання IDE, перегляньте [поради та підказки IDE](./getting-started/tips-and-tricks#code-generation).

![Створення методу тестування](/assets/develop/misc/automatic-testing/unit_testing_01.png)

Ви, звичайно, можете написати підпис методу вручну, і будь-який екземпляр методу без параметрів і типом повернення void буде ідентифіковано як тестовий метод. Ви повинні отримати наступне:

![Пустий метод тестування з індикаторами тестування](/assets/develop/misc/automatic-testing/unit_testing_02.png)

Зверніть увагу на зелені стрілкові індикатори в жолобі: ви можете легко запустити тест, і натиснувши на нього. Крім того, ваші тести запускатимуться автоматично для кожної збірки, включаючи збірки CI, такі як GitHub Actions. Якщо ви використовуєте GitHub Actions, не забудьте прочитати [налаштування GitHub Actions](#setting-up-github-actions).

Тепер настав час написати ваш справжній тестовий код. Ви можете стверджувати умови за допомогою `org.junit.jupiter.api.Assertions`. Перегляньте наступний тест:

@[code lang=java transcludeWith=:::automatic-testing:4](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

Щоб отримати пояснення того, що насправді робить цей код, перегляньте [кодеки](./codecs#registry-dispatch).

#### Налаштування реєстрів {#setting-up-registries}

Чудово, перший тест спрацював! Але зачекайте, другий тест провалився? У журналах ми отримуємо одну з таких помилок.

<<< @/public/assets/develop/automatic-testing/crash-report.log

Це тому, що ми намагаємося отримати доступ до реєстру або класу, який залежить від реєстру (або, у рідкісних випадках, залежить від інших класів Minecraft, таких як `SharedConstants`), але Minecraft не має. Нам просто потрібно трохи його ініціалізувати, щоб реєстри працювали. Просто додайте наступний код на початок вашого методу `beforeAll`.

@[code lang=java transcludeWith=:::automatic-testing:7](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

### Налаштування GitHub Actions {#setting-up-github-actions}

::: info

У цьому розділі передбачається, що ви використовуєте стандартний робочий процес GitHub Action, який входить до прикладу мода та шаблону моду.

:::

Ваші тести тепер виконуватимуться на кожній збірці, включно з тестами постачальників CI, таких як GitHub Actions. Але що, якщо збірка не вдається? Нам потрібно завантажити журнали як артефакт, щоб ми могли переглядати звіти про тести.

Додайте це до свого файлу `.github/workflows/build.yaml` під кроком `./gradlew build`.

```yaml
- name: Store reports
  if: failure()
  uses: actions/upload-artifact@v4
  with:
    name: reports
    path: |
      **/build/reports/
      **/build/test-results/
```

## Ігрові тестування {#game-tests}

Minecraft надає ігрову тестувальну структуру для тестування функцій на стороні сервера. Fabric додатково надає тести клієнтських ігор для тестування функцій на стороні клієнта, подібно до наскрізного тесту.

### Налаштування ігрових тестів із Fabric Loom {#setting-up-game-tests-with-fabric-loom}

Як серверні, так і клієнтські ігрові тестування можна налаштувати вручну або за допомогою Fabric Loom. Цей посібник буде використовувати Loom.

Щоб додати ігрові тестування до свого моду, додайте наступне до свого `build.gradle`:

@[code transcludeWith=:::automatic-testing:game-test:1](@/reference/latest/build.gradle)

Щоб переглянути всі доступні параметри, перегляньте [документацію Loom щодо тестувань](./loom/fabric-api#tests).

#### Налаштування каталогу ігрового тестування {#setting-up-game-test-directory}

::: info

Цей розділ потрібен, лише якщо ви увімкнули `createSourceSet`, що рекомендовано. Ви, звісно, ​​можете зробити власну магію Gradle, але ви будете самі.

:::

Якщо ви ввімкнули `createSourceSet`, як у прикладі вище, ваше ігрове тестування буде в окремому вихідному наборі з окремим `fabric.mod.json`. Назва модуля усталено `gametest`. Створіть новий `fabric.mod.json` у `src/gametest/resources/`, як показано:

<<< @/reference/latest/src/gametest/resources/fabric.mod.json

Зауважте, що цей `fabric.mod.json` очікує тестування серверної гри у `src/gametest/java/com/example/docs/ExampleModGameTest`, а перевірку клієнтської гри у `src/gametest/java/com/example/docs/ExampleModClientGameTest`.

### Написання ігрового тестування {#writing-game-tests}

Тепер ви можете створювати серверні та клієнтські ігрові тестування в каталозі `src/gametest/java`. Ось базовий приклад для кожного:

::: code-group

<<< @/reference/latest/src/gametest/java/com/example/docs/ExampleModGameTest.java [Server]

<<< @/reference/latest/src/gametest/java/com/example/docs/ExampleModClientGameTest.java [Client]

:::

Для отримання додаткової інформації перегляньте відповідні Javadocs у Fabric API.

### Запуск ігрового текстування {#running-game-tests}

Серверні ігрові тестування запускатимуться автоматично із завданням `build` Gradle. Ви можете запускати клієнтські ігрові тестування за допомогою завдання Gradle `runClientGameTest`.

### Запуск ігрових тестувань з GitHub Actions {#run-game-tests-on-github-actions}

Наявні робочі процеси GitHub Action, які використовують `build`, запускатимуть серверні ігрові тестування автоматично. Щоб запустити тестування клієнтські ігрові тестування за допомогою дій GitHub, додайте наступний сніппет до свого `build.gradle`, а наступне завдання — до робочого процесу. Сніппет Gradle запускатиме тести клієнтської гри за допомогою [завдань виробничого запуску Loom](./loom/production-run-tasks), а завдання виконає завдання виробничого запуску в CI.

::: warning

Наразі тестування гри на GitHub Actions може завершитися помилкою через помилку мережевого синхронізатора. Якщо ви зіткнулися з цією помилкою, ви можете додати `-Dfabric.client.gametest.disableNetworkSynchronizer=true` до аргументів JVM у декларації робочого завдання.

:::

@[code transcludeWith=:::automatic-testing:game-test:2](@/reference/latest/build.gradle)

@[code transcludeWith=:::automatic-testing:game-test:3](@/.github/workflows/build.yaml)
