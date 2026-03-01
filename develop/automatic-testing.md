---
title: Automated Testing
description: A guide to writing automatic tests with Fabric Loader JUnit.
authors:
  - kevinthegreat1
  - brainage04
---

This page explains how to write code to automatically test parts of your mod. There are two ways to automatically test your mod: unit tests with Fabric Loader JUnit or game tests with the Gametest framework from Minecraft.

Unit tests should be used to test components of your code, such as methods and helper classes, while game tests spin up an actual Minecraft client and server to run your tests, which makes it suitable for testing features and gameplay.

## Unit Testing {#unit-testing}

Since Minecraft modding relies on runtime byte-code modification tools such as Mixin, simply adding and using JUnit normally would not work. That's why Fabric provides Fabric Loader JUnit, a JUnit plugin that enables unit testing in Minecraft.

### Setting up Fabric Loader JUnit {#setting-up-fabric-loader-junit}

First, we need to add Fabric Loader JUnit to the development environment. Add the following to your dependencies block in your `build.gradle`:

@[code transcludeWith=:::automatic-testing:1](@/reference/build.gradle)

Then, we need to tell Gradle to use Fabric Loader JUnit for testing. You can do so by adding the following code to your `build.gradle`:

@[code transcludeWith=:::automatic-testing:2](@/reference/latest/build.gradle)

### Writing Tests {#writing-tests}

Once you reload Gradle, you're now ready to write tests.

These tests are written just like regular JUnit tests, with a bit of additional setup if you want to access any registry-dependent class, such as `ItemStack`. If you're comfortable with JUnit, you can skip to [Setting Up Registries](#setting-up-registries).

#### Setting Up Your First Test Class {#setting-up-your-first-test-class}

Tests are written in the `src/test/java` directory.

One naming convention is to mirror the package structure of the class you are testing. For example, to test `src/main/java/com/example/docs/codec/BeanType.java`, you'd create a class at `src/test/java/com/example/docs/codec/BeanTypeTest.java`. Notice how we added `Test` to the end of the class name. This also allows you to easily access package-private methods and fields.

Another naming convention is to have a `test` package, such as `src/test/java/com/example/docs/test/codec/BeanTypeTest.java`. This prevents some problems that may arise with using the same package if you use Java modules.

After creating the test class, use <kbd>⌘/CTRL</kbd>+<kbd>N</kbd> to bring up the Generate menu. Select Test and start typing your method name, usually starting with `test`. Press <kbd>ENTER</kbd> when you're done. For more tips and tricks on using the IDE, see [IDE Tips and Tricks](./getting-started/tips-and-tricks#code-generation).

![Generating a test method](/assets/develop/misc/automatic-testing/unit_testing_01.png)

You can, of course, write the method signature by hand, and any instance method with no parameters and a void return type will be identified as a test method. You should end up with the following:

![A blank test method with test indicators](/assets/develop/misc/automatic-testing/unit_testing_02.png)

Notice the green arrow indicators in the gutter: you can easily run a test by clicking them. Alternately, your tests will run automatically on every build, including CI builds such as GitHub Actions. If you're using GitHub Actions, don't forget to read [Setting Up GitHub Actions](#setting-up-github-actions).

Now, it's time to write your actual test code. You can assert conditions using `org.junit.jupiter.api.Assertions`. Check out the following test:

@[code lang=java transcludeWith=:::automatic-testing:4](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

For an explanation of what this code actually does, see [Codecs](./codecs#registry-dispatch).

#### Setting Up Registries {#setting-up-registries}

Great, the first test worked! But wait, the second test failed? In the logs, we get one of the following errors.

<<< @/public/assets/develop/automatic-testing/crash-report.log

This is because we're trying to access the registry or a class that depends on the registry (or, in rare cases, depends on other Minecraft classes such as `SharedConstants`), but Minecraft has not been initialized. We just need to initialize it a little bit to have registries working. Simply add the following code to the beginning of your `beforeAll` method.

@[code lang=java transcludeWith=:::automatic-testing:7](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

### Setting Up GitHub Actions {#setting-up-github-actions}

::: info

This section assumes that you are using the standard GitHub Action workflow included with the example mod and with the mod template.

:::

Your tests will now run on every build, including those by CI providers such as GitHub Actions. But what if a build fails? We need to upload the logs as an artifact so we can view the test reports.

Add this to your `.github/workflows/build.yaml` file, below the `./gradlew build` step.

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

## Game Tests {#game-tests}

Minecraft provides the game test framework for testing server-side features. Fabric additionally provides client game tests for testing client-side features, similar to an end-to-end test.

### Setting up Game Tests with Fabric Loom {#setting-up-game-tests-with-fabric-loom}

Both server and client game tests can be set up manually or with Fabric Loom. This guide will use Loom.

To add game tests to your mod, add the following to your `build.gradle`:

@[code transcludeWith=:::automatic-testing:game-test:1](@/reference/latest/build.gradle)

To see all available options, see [the Loom documentation on tests](./loom/fabric-api#tests).

#### Setting up Game Test Directory {#setting-up-game-test-directory}

::: info

You only need this section if you enabled `createSourceSet`, which is recommended. You can, of course, do your own Gradle magic, but you'll be on your own.

:::

If you enabled `createSourceSet` like the example above, your gametest will be in a separate source set with a separate `fabric.mod.json`. The module name defaults to `gametest`. Create a new `fabric.mod.json` in `src/gametest/resources/` as shown:

<<< @/reference/latest/src/gametest/resources/fabric.mod.json

Note that this `fabric.mod.json` expects a server game test at `src/gametest/java/com/example/docs/ExampleModGameTest`, and a client game test at `src/gametest/java/com/example/docs/ExampleModClientGameTest`.

### Writing Game Tests {#writing-game-tests}

You can now create server and client game tests in the `src/gametest/java` directory. Here is a basic example for each:

::: code-group

<<< @/reference/latest/src/gametest/java/com/example/docs/ExampleModGameTest.java [Server]

<<< @/reference/latest/src/gametest/java/com/example/docs/ExampleModClientGameTest.java [Client]

:::

See the respective Javadocs in Fabric API for more info.

#### Writing Game Tests for Player Commands {#writing-game-tests-for-player-commands}

Writing game tests for commands that can only be used by players is somewhat tricky, as the only way to create a `ServerPlayer` using a `GameTestHelper` instance is with the now deprecated/marked for removal `makeMockServerPlayerInLevel` method, and a normal `Player` instance isn't useful if you need to execute commands using a `ServerGamePacketListenerImpl` instance with the `handleChatCommand` method (and the only other instance of `ServerGamePacketListenerImpl` is in `PlayerList`, which only accepts `ServerPlayer` instances). Plus, the `makeMockServerPlayerInLevel` isn't all that useful - there's no way to specify the game type or the name of the player being spawned.

To create and spawn a `ServerPlayer` instance, we will take the deprecated/marked for removal `makeMockServerPlayerInLevel` method and modify it to accept game type and name arguments like so:

```java
public ServerPlayer makeMockServerPlayerInLevel(GameTestHelper helper, GameType gameType, String name) {
    CommonListenerCookie commonListenerCookie = CommonListenerCookie.createInitial(new GameProfile(UUID.randomUUID(), name), false);

    ServerLevel level = helper.getLevel();
    MinecraftServer server = level.getServer();
    assert server != null;

    ServerPlayer serverPlayer = new ServerPlayer(
        server, level, commonListenerCookie.gameProfile(), commonListenerCookie.clientInformation()
    ) {
        @Override
        public GameType gameMode() {
            return gameType;
        }
    };
    Connection connection = new Connection(PacketFlow.SERVERBOUND);
    new EmbeddedChannel(connection);
    server.getPlayerList().placeNewPlayer(connection, serverPlayer, commonListenerCookie);

    return serverPlayer;
}
```

Sending of commands can then be simulated by wrapping a command `String` with a `ServerboundChatCommandPacket` instance and sending it to the server using the previously mentioned `handleChatCommand` method like so:

```java
public void executeCommand(ServerPlayer player, String command) {
    player.connection.handleChatCommand(new ServerboundChatCommandPacket(command));
}
```

### Running Game Tests {#running-game-tests}

Server game tests will be run automatically with the `build` Gradle task. You can run client game tests with the `runClientGameTest` Gradle task.

### Run Game Tests on GitHub Actions {#run-game-tests-on-github-actions}

Existing GitHub Action workflows using `build` will run server game tests automatically. To run client game tests with GitHub Actions, add the following snippet to your `build.gradle` and the following job to your workflow. The Gradle snippet will run client game tests using [Loom's production run tasks](./loom/production-run-tasks), and the job will execute the production run task in the CI.

::: warning

Currently, game test may fail on GitHub Actions due to an error in the network synchronizer. If you encounter this error, you can add `-Dfabric.client.gametest.disableNetworkSynchronizer=true` to the JVM args in your production run task declaration.

:::

@[code transcludeWith=:::automatic-testing:game-test:2](@/reference/latest/build.gradle)
@[code transcludeWith=:::automatic-testing:game-test:3](@/.github/workflows/build.yaml)
