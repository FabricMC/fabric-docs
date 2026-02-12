---
title: Migrating Mappings using Ravel
description: Learn how to migrate your mod's obfuscation mappings using Ravel.
authors:
  - cassiancc
  - deirn
---

[Ravel](https://github.com/badasintended/ravel) is a plugin for IntelliJ IDEA to remap source files, based on [IntelliJ's PSI](https://plugins.jetbrains.com/docs/intellij/psi.html) and [Mapping-IO](https://github.com/FabricMC/mapping-io). It supports remapping Java, Kotlin, Mixins (written in Java), Class Tweakers, and Access Wideners.

Install it from [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/28938-ravel-remapper) or download the ZIP file from [GitHub Releases](https://github.com/badasintended/ravel/releases) and install it by clicking the gear icon on the plugin settings and clicking **Install Plugin From Disk**.

![IDEA Install Plugin From Disk](/assets/develop/misc/migrating-mappings/idea_local_plugin.png)

## Migrating Mappings {#migrating-mappings}

::: warning

Commit any changes before attempting to remap your sources! **Do not modify your `gradle.properties` or `build.gradle` yet!**

:::

Start by right clicking on a file open in the Editor and choose **Refactor** > **Remap Using Ravel**

![Right Click Menu](/assets/develop/misc/migrating-mappings/ravel_right_click.png)

A dialog like this will open. You can also open the dialog by clicking **Refactor** from the top menu.

![Ravel Dialog](/assets/develop/misc/migrating-mappings/ravel_dialog.png)

Next, add the mappings by clicking the + icon. Click the download option if you don't have them already.

::: info

If you do not see the download button, update Ravel to 0.3 or above.

:::

- For migrating from Yarn to Mojang Mappings, add the Yarn `mappings.tiny` file first, choose `named` as the **source** namespace and `official` as the **destination** namespace. Then, add the Mojang `client.txt` file and choose `target` as the **source** namespace and `source` as the **destination** namespace.
- For migrating from Mojang Mappings to Yarn, add the Mojang `client.txt` first, this time choosing `source` as the **source** and `target` as the **destination**. Then, add the Yarn `mappings.tiny` and choose `official` as the **source** and `named` as the **destination**.

Then, select the modules that you want to remap by clicking the + icon, or the icon on its left to add all modules.

Then, click **OK** and wait for it to finish remapping.

### Updating Gradle {#updating-gradle}

After the remapping finished, replace your mappings in your mod's `build.gradle`.

```groovy
dependencies {
    mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2" // [!code --]
    mappings loom.officialMojangMappings() // [!code ++]
    // Or the reverse if you're migrating from Mojang Mappings to Yarn
}
```

Also update your `gradle.properties`, remove `yarn_mappings` option or update it to the one you use.

```properties
yarn_mappings=1.21.11+build.3 # [!code --]
```

### Final Changes {#final-changes}

That's the bulk of the work done! You'll now want to go through your source code to check for any potentially outdated Mixin targets or code that was not remapped.

For problems that are detected by Ravel, you can search (<kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>F</kbd>) for `TODO(Ravel)`.

![Ravel TODO Search](/assets/develop/misc/migrating-mappings/ravel_todo.png)

Tools like [mappings.dev](https://mappings.dev/) or [Linkie](https://linkie.shedaniel.dev/mappings?namespace=yarn&translateMode=ns&translateAs=mojang_raw&search=&version=1.21.11) will be helpful to familiarize yourself with your new mappings.
