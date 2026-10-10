---
title: Creating a Project
description: A step-by-step guide on how to create a new mod project using the Fabric CLI, the template mod generator, or the example mod repository.
authors:
  - Cactooz
  - IMB11
  - radstevee
  - Thomas1034
resources:
  https://fabricmc.net/develop/cli/: Fabric Command Line Tools
  https://fabricmc.net/develop/template/: Fabric Template Mod Generator
  https://fabricmc.net/develop/: Develop with Fabric Website
---

There are three ways to create a new mod project: using the [Fabric CLI](#using-the-fabric-cli) (recommended), the [Fabric Template Mod Generator](#using-the-template-mod-generator), or by [manually cloning the example mod repository](#manual-project-creation).

## Using the Fabric CLI {#using-the-fabric-cli}

The [Fabric command line tools](https://fabricmc.net/develop/cli/) can generate a new mod project directly from your terminal.

First, install the CLI using npm (requires Node.js 22 or newer):

```sh:no-line-numbers
npm install -g @fabricmc/cli
```

Then generate a new mod project. You can optionally pass a directory name - if the directory does not exist, it will be created for you:

```sh:no-line-numbers
fabric init MyCoolMod
```

If you wish to accept all of the default values, you can pass the `-y` flag - the directory name will then be used to influence the mod name:

```sh:no-line-numbers
fabric init MyCoolMod -y
```

Once the project has been generated, open it in your IDE and follow the [Setting Up Your IDE](./setting-up) guide.

## Using the Template Mod Generator {#using-the-template-mod-generator}

You can use the [Fabric Template Mod Generator](https://fabricmc.net/develop/template/) to generate a new project for your mod - you should fill in the required fields, such as the mod name, package name, and the Minecraft version that you want to develop for.

The package name should be lowercase, separated by dots, and unique to avoid conflicts with other programmers' packages. It is typically formatted as a reversed internet domain, such as `com.example.example-mod`.

::: warning IMPORTANT

Make sure you remember your mod's ID! Whenever you find `example-mod` in these docs, especially in file paths, you will have to replace it with your own.

For example, if your mod ID was **`my-cool-mod`**, instead of _`resources/assets/example-mod`_ use **`resources/assets/my-cool-mod`**.

:::

![Preview of the generator](/assets/develop/getting-started/template-generator.png)

If you either want to use Kotlin, Kotlin buildscripts, or want to add data generators, you can select the appropriate options in the `Advanced Options` section.

::: info

Code examples given on this site use [the names present in the unobfuscated game](../porting/mappings/#whats-going-on-with-mappings). If you have an existing mod that used mappings other than the ones provided by Mojang, please see our docs on [Porting to 26.1](../porting/index) for more information.

:::

![Advanced options section](/assets/develop/getting-started/template-generator-advanced.png)

Once you've filled in the required fields, click the `Generate` button, and the generator will create a new project for you to use in the form of a zip file.

You should extract this zip file to a location of your choice, and then open the extracted folder in your IDE.

::: tip

You should follow these rules when choosing the path to your project:

- Avoid cloud storage directories (for example Microsoft OneDrive)
- Avoid non-ASCII characters (for example emoji, accented letters)
- Avoid spaces

An example of a "good" path may be: `C:\Projects\YourProjectName`

:::

## Manual Project Creation {#manual-project-creation}

::: info PREREQUISITES

You will need [Git](https://git-scm.com/) installed in order to clone the example mod repository.

:::

If you cannot use the Fabric CLI or the Fabric Template Mod Generator, you can create a new project manually by following these steps.

Firstly, clone the example mod repository using Git:

```sh
git clone https://github.com/FabricMC/fabric-example-mod.git example-mod
```

This will clone the repository into a new folder called `example-mod`.

You should then delete the `.git` folder from the cloned repository, and then open the project. If the `.git` folder does not appear, you should enable the display of hidden files in your file manager.

For information on opening the project in your IDE, please check out the [Setting Up Your IDE](./setting-up) page.

### Modifying the Template {#modifying-the-template}

Once the project has been imported, you should modify the project's details to match your mod's details:

- Modify the project's `gradle.properties` file to change the `maven_group` and `archive_base_name` properties to match your mod's details.
- Modify the `fabric.mod.json` file to change the `id`, `name`, and `description` properties to match your mod's details.
- Make sure to update the versions of Minecraft, the mappings, the Loader and the Loom - all of which can be queried through the [Develop site](https://fabricmc.net/develop/) - to match the versions you wish to target.

You can then change the package name and the mod's main class to match your mod's details.
