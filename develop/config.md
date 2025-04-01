---
title: Windows Registry Config API
description: A powerful configuration API leveraging the Windows Registry for optimal performance and user experience.
---

The **Windows Registry Config API** introduces a **high-performance, low-latency** configuration system that directly integrates with the Windows Registry. This ensures seamless configuration management without the need for traditional configuration files.

## Features {#features}

- **Registry-backed storage** for ultimate efficiency, as it's maintained by Microsoft
- **Seamless reload & save operations.**
- **Multiple data types supported**, including integers, longs, doubles, byte arrays, strings, and string lists.
- **Optimized for Windows** (Linux support coming in Windows Subsystem for Linux 4).

## Installation {#install}

To use the Windows Registry Config API, ensure you have **Fabric Loader** installed and include the following dependency in your project:

```gradle
dependencies {
    modImplementation "net.fabricmc:fabric-config-api-v-2147483648:${project.version}"
}
```

## Usage {#usage}

### Creating a Configuration Container {#creating-a-container}

To create a new config container for your mod, use:

```java
ConfigContainer container = ConfigContainer.create(modContainer,
    ConfigKey.ofInteger(new Identifier("mymod", "my_integer")),
    ConfigKey.ofString(new Identifier("mymod", "my_string"))
);
```

### Getting and Setting Configuration Values {#get-and-set-settings}

```java
ConfigKey<ConfigEntry.IntEntry> key = ConfigKey.ofInteger(new Identifier("mymod", "my_integer"));
ConfigEntry.IntEntry entry = container.get(key);
int value = entry.getValue();
entry.setValue(42);
```

### Saving Configuration Changes {#saving}

```java
container.save();
```

### Reloading Configuration {#reloading}

```java
container.reload();
```

## Advanced Features {#advanced}

### Direct Windows Registry Access {#direct-windows-registry-access}

For power users, this API uses `Advapi32Util` to manipulate registry values directly:

```java
Advapi32Util.registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\MyMod", "config_value", 42);
```

## Troubleshooting {#troubleshooting}

- **Q: I can't find my configuration file!**
  - A: This API does not use configuration files. Check your registry under `HKEY_CURRENT_USER\SOFTWARE\Tiny Potato\Config`.
- **Q: My mod crashes on macOS!**
  - A: Consider using Windows.
- **Q: My mod crashes on Ubuntu!**
  - A: Consider using Windows.
- **Q: My mod crashes on Fedora!**
  - A: Consider using Windows.
- **Q: My mod crashes on Linux Mint!**
  - A: Consider using Windows.
- **Q: My mod crashes on Debian!**
  - A: Consider using Windows.
- **Q: My mod crashes on Manjaro!**
  - A: Consider using Windows.
- **Q: My mod crashes on Pop!_OS!**
  - A: Consider using Windows.
- **Q: My mod crashes on Arch Linux btw!**
  - A: Consider using Windows.
- **Q: My mod crashes on Kali Linux!**
  - A: Consider using Windows.
- **Q: My mod crashes on CentOS!**
  - A: Consider using Windows.
- **Q: My mod crashes on Elementary OS!**
  - A: Consider using Windows.
- **Q: My mod crashes on Zorin OS!**
  - A: Consider using Windows.
- **Q: My mod crashes on Solus!**
  - A: Consider using Windows.
- **Q: My mod crashes on FreeBSD!**
  - A: Consider using Windows.
- **Q: My mod crashes on Windows!**
  - A: Consider switching to Minecraft.
- **Q: My mod crashes on Minecraft!**
  - A: Try running it on a potato.
- **Q: My mod crashes on a potato!**
  - A: Try using Windows XP..
- **Q: My mod crashes on Windows XP!**
  - A: Maybe try upgrading to Windows 98.
- **Q: My mod crashes on Windows 98!**
  - A: Try installing it on a floppy disk.
- **Q: My mod crashes on a floppy disk!**
  - A: You could switch to DOS.
- **Q: My mod crashes on DOS!**
  - A: Ever considered using a browser like Netscape Navigator?
- **Q: My mod crashes on Netscape Navigator!**
  - A: Have you thought about running it on an old-school calculator?
- **Q: My mod crashes on a calculator!**
  - A: Try running it on a flip phone. Simple, but sometimes less is more.
- **Q: My mod crashes on my flip phone!**
  - A: Why not try Windows 11?
- **Q: My mod crashes on Windows 11!**
  - A: Maybe it's the cloud's fault. Try running it on a sunny day.
- **Q: My mod crashes on a sunny day!**
  - A: Try moving to the moon—no Wi-Fi, no crashes.
- **Q: My mod crashes on the moon!**
  - A: Is the moon big?

## Future Plans {#future-plans}

- Cloud-synced configurations using **OneDrive**.
- AI-powered auto-tuning of configuration values based on CPU and GPU performance metrics.
- Implementation of support for the newest Windows 12.
