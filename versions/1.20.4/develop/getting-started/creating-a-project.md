---
title: Creating a Project
description: A step-by-step guide on how to create a new mod project using the Fabric template mod generator.
authors:
  - IMB11

search: false
---

# Creating a Project {#creating-a-project}

Fabric provides an easy way to create a new mod project using the Fabric Template Mod Generator - if you want, you can manually create a new project using the example mod repository, you should refer to the [Manual Project Creation](#manual-project-creation) section.

## Generating a Project {#generating-a-project}

You can use the [Fabric Template Mod Generator](https://fabricmc.net/develop/template/) to generate a new project for your mod - you should fill in the required fields, such as the package name and mod name, and the Minecraft version that you want to develop for.

![Preview of the generator](/assets/develop/getting-started/template-generator.png)

If you want to use Kotlin, or want to add data generators, you can select the appropriate options in the `Advanced Options` section.

![Advanced options section](/assets/develop/getting-started/template-generator-advanced.png)

Once you've filled in the required fields, click the `Generate` button, and the generator will create a new project for you to use in the form of a zip file.

You should extract this zip file to a location of your choice, and then open the extracted folder in IntelliJ IDEA:

![Open Project Prompt](/assets/develop/getting-started/open-project.png)

## Importing the Project {#importing-the-project}

Once you've opened the project in IntelliJ IDEA, the IDE should automatically load the project's Gradle configuration and perform the necessary setup tasks.

If you receive a notification talking about a Gradle build script, you should click the `Import Gradle Project` button:

![Gradle Prompt](/assets/develop/getting-started/gradle-prompt.png)

Once the project has been imported, you should see the project's files in the project explorer, and you should be able to start developing your mod.

## Manual Project Creation {#manual-project-creation}

::: warning
You will need [Git](https://git-scm.com/) installed in order to clone the example mod repository.
:::

If you cannot use the Fabric Template Mod Generator, you can create a new project manually by following these steps.

Firstly, clone the example mod repository using Git:

```sh
git clone https://github.com/FabricMC/fabric-example-mod/ my-mod-project
```

This will clone the repository into a new folder called `my-mod-project`.

You should then delete the `.git` folder from the cloned repository, and then open the project in IntelliJ IDEA. If the `.git` folder does not appear, you should enable the display of hidden files in your file manager.

Once you've opened the project in IntelliJ IDEA, it should automatically load the project's Gradle configuration and perform the necessary setup tasks.

Again, as previously mentioned, if you receive a notification talking about a Gradle build script, you should click the `Import Gradle Project` button.

### Modifying the Template {#modifying-the-template}

Once the project has been imported, you should modify the project's details to match your mod's details:

- Modify the project's `gradle.properties` file to change the `maven_group` and `archive_base_name` properties to match your mod's details.
- Modify the `fabric.mod.json` file to change the `id`, `name`, and `description` properties to match your mod's details.
- Make sure to update the versions of Minecraft, the mappings, the Loader and the Loom - all of which can be queried through <https://fabricmc.net/develop/> - to match the versions you wish to target.

You can obviously change the package name and the mod's main class to match your mod's details.
