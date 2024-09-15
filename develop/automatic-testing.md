---
title: Automated Testing
description: A guide to writing automatic tests with Fabric Loader JUnit.
authors:
  - kevinthegreat1
---

# Automated Testing {#automated-testing}

This page explains how to write code to automatically test parts of your mod. There are two ways to automatically test your mod: unit tests with Fabric Loader JUnit or game tests with the Gametest framework from Minecraft.

Unit tests should be used to test components of your code, such as methods and helper classes, while game tests spin up an actual Minecraft client and server to run your tests, which makes it suitable for testing features and gameplay.

::: warning
Currently, this guide only covers unit testing.
:::

## Unit Testing {#unit-testing}

Since Minecraft modding relies on runtime byte-code modification tools such as Mixin, simply adding and using JUnit normally would not work. That's why Fabric provides Fabric Loader JUnit, a JUnit plugin that enables unit testing in Minecraft.

### Setting up Fabric Loader JUnit {#setting-up-fabric-loader-junit}

First, we need to add Fabric Loader JUnit to the development environment. Add the following to your dependencies block in your `build.gradle`:

@[code lang=groovy transcludeWith=:::automatic-testing:1](@/reference/build.gradle)

Then, we need to tell Gradle to use Fabric Loader JUnit for testing. You can do so by adding the following code to your `build.gradle`:

@[code lang=groovy transcludeWith=:::automatic-testing:2](@/reference/latest/build.gradle)

#### Split Sources {#split-sources}

::: info
This section is planned to become irrelevant after the release of Loom 1.8. For more information, track [this issue](https://github.com/FabricMC/fabric-loom/issues/1060).
:::

If you are using split sources, you also need to add either the client or server source set to the test source set. Fabric Loader JUnit defaults to client, so we'll add the client source set to our testing environment with the following in `build.gradle`:

@[code lang=groovy transcludeWith=:::automatic-testing:3](@/reference/build.gradle)

### Writing Tests {#writing-tests}

Once you reload Gradle, you're now ready to write tests.

These tests are written just like regular JUnit tests, with a bit of additional setup if you want to access any registry-dependent class, such as `ItemStack`. If you're comfortable with JUnit, you can skip to [Setting Up Registries](#setting-up-registries).

#### Setting Up Your First Test Class {#setting-up-your-first-test-class}

Tests are written in the `src/test/java` directory.

One naming convention is to mirror the package structure of the class you are testing. For example, to test `src/main/java/com/example/docs/codec/BeanType.java`, you'd create a class at `src/test/java/com/example/docs/codec/BeanTypeTest.java`. Notice how we added `Test` to the end of the class name. This also allows you to easily access package-private methods and fields.

Another naming convention is to have a `test` package, such as `src/test/java/com/example/docs/test/codec/BeanTypeTest.java`. This prevents some problems that may arise with using the same package if you use Java modules.

After creating the test class, use <kbd>⌘/CTRL</kbd><kbd>N</kbd> to bring up the Generate menu. Select Test and start typing your method name, usually starting with `test`. Press <kbd>ENTER</kbd> when you're done. For more tips and tricks on using the IDE, see [IDE Tips and Tricks](ide-tips-and-tricks#code-generation).

![Generating a test method](/assets/develop/misc/automatic-testing/unit_testing_01.png)

You can, of course, write the method signature by hand, and any instance method with no parameters and a void return type will be identified as a test method. You should end up with the following:

![A blank test method with test indicators](/assets/develop/misc/automatic-testing/unit_testing_02.png)

Notice the green arrow indicators in the gutter: you can easily run a test by clicking them. Alternately, your tests will run automatically on every build, including CI builds such as GitHub Actions. If you're using GitHub Actions, don't forget to read [Setting Up GitHub Actions](#setting-up-github-actions).

Now, it's time to write your actual test code. You can assert conditions using `org.junit.jupiter.api.Assertions`. Check out the following test:

@[code lang=java transcludeWith=:::automatic-testing:4](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

For an explanation of what this code actually does, see [Codecs](codecs#registry-dispatch).

#### Setting Up Registries {#setting-up-registries}

Great, the first test worked! But wait, the second test failed? In the logs, we get one of the following errors.

@[code lang=java transcludeWith=:::automatic-testing:5](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

This is because we're trying to access the registry or a class that depends on the registry (or, in rare cases, depends on other Minecraft classes such as `SharedConstants`), but Minecraft has not been initialized. We just need to initialize it a little bit to have registries working. Simply add the following code to the beginning of your `beforeAll` method.

@[code lang=java transcludeWith=:::automatic-testing:7](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

### Setting Up GitHub Actions {#setting-up-github-actions}

::: info
This section assumes that you are using the standard GitHub Action workflow included with the example mod and with the mod template.
:::

Your tests will now run on every build, including those by CI providers such as GitHub Actions. But what if a build fails? We need to upload the logs as an artifact so we can view the test reports.

Add this to your `.github/workflows/build.yml` file, below the `./gradlew build` step.

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
