# Updating Yarn mappings in a Java codebase

Loom allows semi-automatic updating of the mappings used in a Java codebase. Due to frequent changes in Yarn, this can be a useful tool for keeping a codebase up-to-date with the newest changes.

**Note:** This automated process currently does not handle Mixins or reflection, instances of these will need to be manually updated.

## Loom 0.2.6 and above

Say you want to migrate from 1.16.5 yarn to 1.17.1 yarn.

  - Go [here](https://fabricmc.net/develop), select the version to migrate to, and copy the Gradle command under "Mappings Migration", for example ```gradlew migrateMappings --mappings "1.17.1+build.40"```.  DO NOT modify your gradle.properties or build.gradle yet.   
  - Run the command in the root of your Gradle project.
  - Your migrated sources will appear in ```remappedSrc```. Verify that the migration produced valid migrated code.
  - Copy the sources from ```remappedSrc``` to the original folder. Keep the original sources backed up just in case.
  - Update your gradle.properties file according to the instructions in [the first site](https://fabricmc.net/develop).
  - Refresh the Gradle project in your IDE.
  - Check and update any Mixin targets that may be outdated.

If you want to go from Mojang's official mappings, AKA mojmap, to yarn, make sure your mappings in ```build.gradle``` is set to ```loom.officialMojangMappings()``` before running ```migrateMappings```. For more information, check out the dedicated [Mappings](https://fabricmc.net/wiki/tutorial:mappings) page on the Fabric wiki.

### Additional customization 
  * Specify from where to take your Java files with ```--input path/to/source```. Default: ```src/main/java```.
  * Specify where to output the remapped source with ```--output path/to/output```. Default: ```remappedSrc```. You can use ```src/main/java``` here to avoid having to copy the remapped classes, but make sure you have a backup.
  * Specify a custom place to retrieve the mappings from with ```--mappings some_group:some_artifact:some_version:some_qualifier''. Default: ''net.fabricmc:yarn:<version-you-inputted>:v2''. Use ''net.minecraft:mappings:<minecraft-version>``` to migrate to official Mojang mappings.

### Reporting issues
Loom uses [Mercury](https://github.com/CadixDev/Mercury) to remap Java source code, for problems with remapping please report issues to their [issue tracker](https://github.com/CadixDev/Mercury/issues), or discuss it through their communications channel (irc.esper.net #cadix).

## Loom 0.2.2-0.2.5

Some assembly required.

  - Figure out your target mappings version. For example, ```net.fabricmc:yarn:1.14.1 Pre-Release 2+build.2```.
  - Make sure the mappings for this version get created. This is the hacky part, as currently the only way to do it is to edit the ```minecraft``` and ```mappings``` fields in a ```build.gradle``` to the new version, run any Gradle command ("gradle build" will do, even if it crashes), then **change the fields back**.
  - Run the following magical wizardry command: ```gradle migrateMappings -PtargetMappingsArtifact="net.fabricmc:yarn:1.14.1 Pre-Release 2+build.2" -PinputDir=src/main/java -PoutputDir=remappedSrc```, where:
    * ```targetMappingsArtifact``` refers to the target mappings version. It is imperative that the build.gradle be set to the current mappings version of the mod when running this command!
    * ```inputDir``` is the input directory, containing Java source code,
    * ```outputDir``` is the output directory. It will be created if it is missing.
  - Copy the remapped source code to the input directory, if everything's fine.
  - Hope for the best.

*Note: You may need to specify the full paths in quotes, try this if you get file not found issues.*

This should work across Minecraft versions as well, provided we haven't massively broken Intermediaries or done something equally silly (aka: most of the time).
