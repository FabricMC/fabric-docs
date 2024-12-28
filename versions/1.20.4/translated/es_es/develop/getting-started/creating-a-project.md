---
title: Creando un Proyecto
description: Una guía paso a paso sobre como crear un proyecto de mod usando el generador de plantillas de mods de Fabric.
authors:
  - IMB11

search: false
---

# Creando un Proyecto

Fabric provee una manera fácil de crear un nuevo proyecto de mod usando el Generador de Plantillas de Mods de Fabric - si quieres, puedes crear un nuevo proyecto manualmelnte usando el repositorio del mod de ejemplo, deberías visitar la sección de [Creación Manual de Proyectos](#manual-project-creation).

## Generando un Proyecto

Puedes usar el [Generador de Plantillas de Mods de Fabric](https://fabricmc.net/develop/template/) para generar un nuevo proyecto para tu mod - deberías llenar los campos requeridos, como el nombre del paquete y el nombre del mod, y la versión de Minecraft para la cual quieres desarrollar.

![Vista previa del generador](/assets/develop/getting-started/template-generator.png)

Si quieres usar Kotlin, o quieres agregar generadores de datos, puedes seleccionar las opciones correspondientes en la sección de `Advanced Options` (Opciones Avanzadas).

![Sección de Opciones Avanzadas](/assets/develop/getting-started/template-generator-advanced.png)

Una vez llenados los campos requeridos, haz clic en el botón de `Generate` (Generar), y el generador creará un nuevo proyecto para ti en la forma de archivo zip.

Deberías extraer este archivo zip en un lugar de tu elección, y luego abre el folder extraído en IntelliJ IDEA:

![Sugerencia de Proyecto Abierto](/assets/develop/getting-started/open-project.png)

## Importando el Proyecto

Una vez que has abierto el proyecto en IntelliJ IDEA, el IDEA debería automáticamente cargar las configuraciones de Gradle y realizar las tareas de configuración necesarias.

Si recibes una notificación que habla sobre un _script_ de Gradle, deberías hacer clic en el botón de `Import Gradle Project` (Importar Proyecto de Gradle):

![Sugerencia de Gradle](/assets/develop/getting-started/gradle-prompt.png)

Una vez que el proyecto ha sido importado, deberías ver los archivos del proyecto en el explorador del proyecto, y deberías poder empezar a desarrollar tu mod.

## Creación Manual de Proyectos

:::warning
Necesitarás tener instalado [Git](https://git-scm.com/) para clonar el repositorio del mod de ejemplo.
:::

Si no puedes usar el Generador de Plantillas de Mods de Fabric, puedes crear un nuevo proyecto siguiendo los siguientes pasos.

Primero, clona el repositorio del mod de ejemplo usando Git:

```sh
git clone https://github.com/FabricMC/fabric-example-mod/ my-mod-project
```

Esto clonará el repositorio a un nuevo folder llamado `my-mod-project`.

Después, deberías eliminar el folder de `.git` del repositorio clonado, y luego abre el proyecto en IntelliJ IDEA. Si el folder de `.git` no aparece, deberías habilitar la visualización de archivos ocultos en tu administrador de archivos.

Una vez que has abierto el proyecto en IntelliJ IDEA, el IDEA debería automáticamente cargar las configuraciones de Gradle y realizar las tareas de configuración necesarias.

Nuevamente, como ya hemos dicho, si recibes una notificación que habla sobre un _script_ de Gradle, deberías hacer clic en el botón de `Import Gradle Project` (Importar Proyecto de Gradle).

### Modificando La Plantilla

Una vez que el proyecto ha sido importado, deberías poder modificar los detalles del proyecto para que encajen con los detalles de tu mod:

- Modifica el archivo de `gradle.properties` de tu proyecto para cambiar las propiedades de `maven_group` (grupo de maven) y `archive_base_name` (nombre base del archivo) para que sean los de tu mod.
- Modifica el archivo de `fabric.mod.json` para cambiar las propiedades de `id`, `name`, y `description` para que sean los de tu mod.
- Asegúrate de actualizar las versiones de Minecraft, los mapeos, el cargador de Fabric y Loom - todos ellos pueden ser consultados en https://fabricmc.net/develop/ - para tener las versiones deseadas.

Obviamente también puedes cambiar el nombre del paquete y la clase principal para que sean las de tu mod.
