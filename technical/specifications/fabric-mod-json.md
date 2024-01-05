---
title: fabric.mod.json Specification (v1)
description: The latest (v1) specification for the fabric.mod.json file.
---

# `fabric.mod.json` Specification (v1)

This page covers the latest (v1) specification for the `fabric.mod.json` file used to identify Fabric mods.

## Definitions

### Entrypoint

Defines the entrypoint for the mod.

#### Properties

- `adapter` (String): The language adapter to use. Default value is `"default"`.
- `value` (String): The entrypoint function or class.

### Contact Information

Information for contacting mod authors.

#### Properties

- `email` (String): Contact e-mail pertaining to the mod.
- `irc` (String): IRC channel pertaining to the mod. Must be a valid URL format.
- `homepage` (String): Project or user homepage. Must be a valid HTTP/HTTPS address.
- `issues` (String): Project issue tracker. Must be a valid HTTP/HTTPS address.
- `sources` (String): Project source code repository. Must be a valid URL.

### Environment

Defines the environment where this mod will be loaded.

- Enum Values: `*`, `client`, `server`

### Nested Jar

Describes a nested JAR to be loaded alongside the outer mod JAR.

#### Properties

- `file` (String): A string value pointing to a path from the root of the JAR to a nested JAR.

### Person

Represents an individual associated with the mod.

#### Properties

- `name` (String): The name of the person.
- `contact` (Object): Contact information for the person.

### Version Ranges

Defines version ranges for the mod.

### Version Range

Represents a version range that matches versions.

## Properties

- `id` (String): The mod identifier.
- `version` (String): The mod version.
- `schemaVersion` (Integer): The version of the fabric.mod.json schema. It's a constant set to `1`.
- `environment` (Environment): Reference to the environment definition.
- `entrypoints` (Object): The entrypoints used by this mod.
  - `main` (Array of Entrypoint): The entrypoint for all environments (classes must implement ModInitializer).
  - `client` (Array of Entrypoint): The entrypoint for the client environment (classes must implement ClientModInitializer).
  - `server` (Array of Entrypoint): The entrypoint for the server environment (classes must implement DedicatedServerModInitializer).
  - `preLaunch` (Array of Entrypoint): The entrypoint called just before the game instance is created (classes must implement PreLaunchEntrypoint).
  - `fabric-datagen` (Array of Entrypoint): The entrypoint for the data generator environment (classes must implement DataGeneratorEntrypoint).
  - `fabric-gametest` (Array of Entrypoint): The entrypoint for the Game Test environment (classes must implement FabricGameTest).
  - `...` (Array of Entrypoint): Custom mod entrypoints, you should refer to the mod's code to determine the interface to implement and what the entrypoint ID value is.
- `jars` (Array of Nested Jar): Contains an array of nestedJar objects.
- `languageAdapters` (Object): A string→string dictionary, connecting namespaces to LanguageAdapter implementations.
- `mixins` (Array): Paths to mixin files or objects defining mixin paths and environment.
- `accessWidener` (String): Path to an access widener definition file.
- `depends` (Object): id→versionRange map for dependencies causing a hard failure if not met.
- `recommends` (Object): id→versionRange map for dependencies causing a soft failure (warning) if not met.
- `suggests` (Object): id→versionRange map for dependencies used as metadata.
- `conflicts` (Object): id→versionRange map for dependencies causing a soft failure (warning) if met.
- `breaks` (Object): id→versionRange map for dependencies causing a hard failure if met.
- `name` (String): Name of the mod.
- `description` (String): Description of the mod.
- `authors` (Array of Person): The direct authorship information.
- `contributors` (Array of Person): Contributors to this mod.
- `contact` (Contact Information): Contact information for the mod.
- `license` (String or Array of Strings): The license(s) the mod uses.
- `icon` (String or Object): Path(s) to PNG file(s) for the mod icon(s).
- `custom` (Object): A map of namespace:id→value for custom data fields.

## Required Fields

- `id`
- `version`
- `schemaVersion`
