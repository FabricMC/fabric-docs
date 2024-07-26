---
title: Automatic Testing
description: A guide to writing automatic tests with Fabric Loader JUnit.
authors:
  - kevinthegreat1
---

# Automatic Testing {#automatic-testing}

This page explains how to write code to automatically test parts of your mod.
There are two ways to automatically test your mod:
units tests with Fabric Loader JUnit or game tests with the Gametest framework from Minecraft.
Units tests should be used to test components of your code such as methods and helper classes
while gametests spins up an entire Minecraft client and server to run your tests
which makes it more suitable for testing features and gameplay.
Currently, this guide only covers unit testing.

## Unit Testing with Fabric Loader JUnit {#unit-testing-with-fabric-loader-junit}

Due to the nature of Minecraft modding such as obfuscation, simply adding and using JUnit normally will not work.
That's why Fabric provides Fabric Loader JUnit, a JUnit plugin that enables unit testing in Minecraft.

### Setting up Fabric Loader JUnit {#setting-up-fabric-loader-junit}

First, we need to add Fabric Loader JUnit to the development environment. Add the following to your dependencies in your `build.gradle`:

@[code lang=groovy transcludeWith=:::automatic-testing:1](@/reference/build.gradle)

Then, we need to tell gradle to use Fabric Loader JUnit for testing. You can do so by adding the following code the to your `build.gradle`:

@[code lang=groovy transcludeWith=:::automatic-testing:2](@/reference/build.gradle)

#### Split Sources {#split-sources}

If you are using split sources, you also need to add either the client or server source set to the test source set.
Fabric Loader JUnit defaults to client so we'll add the client source set to our testing environment with the following in `build.gradle`:

@[code lang=groovy transcludeWith=:::automatic-testing:3](@/reference/build.gradle)

### Writing Tests {#writing-tests}

Once you reload gradle, we are now ready to write tests.
Tests are written exactly the same as regular JUnit test,
except with an extra bit of setup if you want to access any registry dependent class, such as `ItemStack`.
If you're conformable with JUnit, you can skip to [Setting Up Registries](#setting-up-registries).

#### Setting Up Your First Test Class {#setting-up-your-first-test-class}

Tests are written in the `src/test/java` directory and should have the same package structure as the class you are testing.
For example, if I want to test `src/main/java/com/example/docs/codec/BeanType.java`, 
I would create a class at `src/test/java/com/example/docs/codec/BeanTypeTest.java`.
(Note that the convention is to copy the class name and add `Test` at the end.)

(Note that the following instructions may be specific to Intellij. However, it should be trivial to follow with a different ide.)
After you've created the test class, use <kbd>COMMAND</kbd>/<kbd>CTRL</kbd> + <kbd>N</kbd> to bring up the generate menu.
Select test and start typing your choice of method name, usually starting with `test`. Press <kbd>ENTER</kbd> when you're done.
For more tips and tricks on using the ide, see [IDE Tips and Tricks](ide-tips-and-tricks#code-generation).

![Generating a test method](/assets/develop/misc/automatic-testing/unit_testing_01.png)

You can, of course, write the method signature out by hand, and any instance method with no parameters and void return type will be identified as a test method.
You should end up with the following. Notice the green arrow test method indicators in the gutter.

![A blank test method with test indicators](/assets/develop/misc/automatic-testing/unit_testing_02.png)

You can easily run a test by clicking them or your tests will run automatically everytime you build,
including in CI programs such as GitHub Actions. If you're using GitHub Actions, don't forget to read the [Setting Up CI](#setting-up-ci-github-actions) section.

Now, it's time to write your actual test code. You can assert conditions using `org.junit.jupiter.api.Assertions`.
I ended up with the following test. For an explanation of what this code actually does, see [Codecs](codecs#registry-dispatch).

@[code lang=java transcludeWith=:::automatic-testing:4](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

#### Setting Up Registries {#setting-up-registries}

Great, the first test worked! But wait, the second test failed? In the logs, we get one of the following errors.

@[code lang=java transcludeWith=:::automatic-testing:5](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

This is because we're trying to access the registry, or a class that depends on the registry
(or in rare cases, depends on other Minecraft classes such as `SharedConstants`),
but Minecraft is nowhere near initialized. We just need to initialize it just a little bit to have registries working.
Simply add the following code to your class.

@[code lang=java transcludeWith=:::automatic-testing:7](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

### Setting Up CI (GitHub Actions) {#setting-up-ci-github-actions}

Your tests will now run every time you build, including in CI providers such as GitHub Actions.
But what if a build fails? We need to upload the logs as an artifact, so we can view the test reports.

(Note that this assumes you are using the standard GitHub Action workflow included with the example mod or mod template.)
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
