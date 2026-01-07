---
title: 自动化测试
description: 使用 Fabric Loader JUnit 写自动化测试的指南。
authors:
  - kevinthegreat1
---

此页面解释了如何在你的模组中编写自动化测试。 有两种对你的mod进行自动化测试的方法：使用Fabric Loader JUnit进行单元测试或使用Minecraft游戏测试框架进行游戏内测试。

单元测试用于测试你代码中的组件，比如方法和工具类；游戏内测试则启动Minecraft客户端与服务端来运行你的测试，它适用于测试功能和游玩过程。

## 单元测试 {#unit-testing}

由于Minecraft mod运行依赖于运行时字节码修改工具比如Mixin，仅仅使用JUnit一般不会生效。 这就是为什么Fabric提供了Fabric Loader JUnit，一个针对Minecraft mod单元测试的JUnit插件。

### 配置Fabric Loader JUnit {#setting-up-fabric-loader-junit}

首先，我们需要将Fabric Loader JUnit添加到开发环境。 将以下依赖添加到你的`build.gradle`：

@[code transcludeWith=:::automatic-testing:1](@/reference/build.gradle)

然后，我们需要告诉Gradle使用Fabric Loader JUnit来测试。 你可以通过将以下代码添加到`build.gradle`来做到这件事：

@[code transcludeWith=:::automatic-testing:2](@/reference/latest/build.gradle)

### 编写测试 {#writing-tests}

您已重新加载Gradle，您现在已经可以编写测试了。

这些测试的编写方式与常规 JUnit 测试相同，如果你想访问任何依赖于注册表的类（例如`ItemStack`），则需要进行一些额外的设置。 如果你对 JUnit 比较熟悉，那么你可以跳至 [设置注册表](#setting-up-registries)。

#### 设置您的第一个测试类 {#setting-up-your-first-test-class}

测试编写于`src/test/java`目录下。

一种命名约定是镜像你正在测试的类的包结构。 例如，为了测试 `src/main/java/com/example/docs/codec/BeanType.java`，您应在 `src/test/java/com/example/docs/codec/BeanTypeTest.java` 创建一个类。 注意我们是如何将`Test`加入到类名称最后的。 这还允许你轻松访问包私有的方法和字段。

另一个命名约定是有一个 `test` 包，例如 `src/test/java/com/example/docs/test/codec/BeanTypeTest.java`。 如果你使用 Java 模块，这可以避免使用相同包时可能出现的一些问题。

创建测试类后，使用 <kbd>⌘/CTRL</kbd><kbd>N</kbd> 调出生成菜单。 选择测试并开始输入方法名称，通常以 `test` 开头。 完成时请按下 <kbd>ENTER</kbd> 键。 有关使用 IDE 的更多提示和技巧，请参阅 [IDE 提示和技巧](./getting-started/tips-and-tricks#code-generation)。

![正在生成一个测试方法](/assets/develop/misc/automatic-testing/unit_testing_01.png)

当然，你可以手写方法签名，任何没有参数且返回类型为 void 的实例方法都将被标识为测试方法。 你应该以这样结尾：

![一个带有测试指示的空测试方法](/assets/develop/misc/automatic-testing/unit_testing_02.png)

注意侧边栏中的绿色箭头指引——您可以简单地通过点击它们来运行一个测试。 或者，你的测试将在每次构建时自动运行，包括 GitHub Actions 等 CI 构建。 如果你正在使用 GitHub Actions，请不要忘记阅读[设置 GitHub Actions](#setting-up-github-actions)。

现在，该编写您的实测代码了。 你可以使用 `org.junit.jupiter.api.Assertions` 断言条件。 检查以下测试：

@[code lang=java transcludeWith=:::automatic-testing:4](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

有关此代码实际作用的解释，请参阅 [Codecs](./codecs#registry-dispatch)。

#### 设置注册表 {#setting-up-registries}

很好，首次测试运行了！ 但是稍等，第二次测试失败了？ 在日志文件中，我们得到了以下错误之一。

<<< @/public/assets/develop/automatic-testing/crash-report.log

这是因为我们正在尝试访问注册表或依赖于注册表的类（或者，在极少数情况下，依赖于其他 Minecraft 类，如 `SharedConstants`），但 Minecraft 尚未初始化。 我们只需要初始化以下就能使注册表工作。 在您的`beforeAll`函数前简单地加入以下代码。

@[code lang=java transcludeWith=:::automatic-testing:7](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

### 配置 GitHub Actions {#setting-up-github-actions}

:::info
本节假设您正在使用包含在示例模组和模组模板中的标准GitHub Action工作流。
:::

你的测试现在将在每个构建上运行，包括由 GitHub Actions 等 CI 提供商构建的构建。 但是如果它构建失败了呢？ 我们需要公开上传日志文件，这样我们就可以查看测试报告了。

将下列文本添加进 `.github/workflows/build.yml` 文件中 `./gradlew build` 步骤的下方。

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

## 游戏测试 {#game-tests}

Minecraft 提供了用于测试服务器端功能的游戏测试框架。 Fabric 还提供客户端游戏测试来测试客户端功能，类似于端到端测试。

### 使用 Fabric Loom 设置游戏测试 {#setting-up-game-tests-with-fabric-loom}

服务器和客户端游戏测试都可以手动设置或使用 Fabric Loom 设置。 本指南将使用 Loom。

要将游戏测试添加到你的模组，请将以下内容添加到你的 `build.gradle`：

@[code transcludeWith=:::automatic-testing:game-test:1](@/reference/latest/build.gradle)

要查看所有可用选项，请参阅 [Loom 测试文档](./loom/fabric-api#tests)。

#### 设置游戏测试目录 {#setting-up-game-test-directory}

:::info
如果你启用了 `createSourceSet`，则仅需要此部分，推荐这样做。 当然，你也可以自己编写 Gradle 代码，但你需要自行承担所有风险。
:::

如果你像上面的示例一样启用了`createSourceSet`，那么你的游戏测试将位于单独的源集中，并带有单独的`fabric.mod.json`。 模块名称默认为 `gametest`。 在`src/gametest/resources/`中创建一个新的`fabric.mod.json`，如下所示：

<<< @/reference/latest/src/gametest/resources/fabric.mod.json

请注意，此 `fabric.mod.json` 需要在 `src/gametest/java/com/example/docs/ExampleModGameTest` 进行服务器游戏测试，并在 `src/gametest/java/com/example/docs/ExampleModClientGameTest` 进行客户端游戏测试。

### 编写游戏测试 {#writing-game-tests}

你现在可以在 `src/gametest/java` 目录中创建服务器和客户端游戏测试。 以下是每个示例的基本示例：

::: code-group

<<< @/reference/latest/src/gametest/java/com/example/docs/ExampleModGameTest.java [Server]

<<< @/reference/latest/src/gametest/java/com/example/docs/ExampleModClientGameTest.java [Client]

:::

有关详细信息，请参阅 Fabric API 中的相应 Javadocs。

### 运行游戏测试 {#running-game-tests}

服务器游戏测试将通过 Gradle 任务 `build` 自动运行。 你可以使用 Gradle 任务 `runClientGameTest` 运行客户端游戏测试。

### 在 GitHub Actions 上运行游戏测试 {#run-game-tests-on-github-actions}

使用 `build` 的现有 GitHub Action 工作流将自动运行服务器游戏测试。 要使用 GitHub Actions 运行客户端游戏测试，请将以下代码片段添加到 `build.gradle` 文件，并将以下作业添加到你的工作流。 Gradle 代码片段将使用 [Loom 的生产运行任务](./loom/production-run-tasks)运行客户端游戏测试，该作业将在 CI 中执行生产运行任务。

:::warning
目前，由于网络同步器出现错误，游戏测试可能会在 GitHub Actions 上失败。 如果遇到此错误，你可以在生产运行任务声明中将 `-Dfabric.client.gametest.disableNetworkSynchronizer=true` 添加到 JVM 参数中。
:::

@[code transcludeWith=:::automatic-testing:game-test:2](@/reference/latest/build.gradle)

@[code transcludeWith=:::automatic-testing:game-test:3](@/.github/workflows/build.yml)
