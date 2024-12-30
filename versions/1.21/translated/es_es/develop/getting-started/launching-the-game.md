---
title: Corriendo tu Juego
description: Aprende a utilizar los perfiles de lanzamiento varios para cargar y depurar tus mods en un entorno de juego en vivo.
authors:
  - IMB11

search: false
---

# Corriendo tu Juego

Fabric Loom provee una variedad de perfiles de lanzamiento para ayudarte a correr y depurar tus mods en un entorno de juego en vivo. Esta guía cubrirá los perfiles de lanzamiento varios y como usarlos para depurar y probar tus mods.

## Perfiles de Lanzamiento

Si estás usando IntelliJ IDEA, puedes encontrar los perfiles de lanzamiento en la esquina superior derecha de la ventana. Haz clic en el menú desplegable para ver los perfiles de lanzamiento disponibles.

Debería haber un perfil para el cliente y el servidor, con la opción de correr cualquiera de los dos normalmente o en modo de depuración:

![Perfiles de Lanzamiento](/assets/develop/getting-started/launch-profiles.png)

## Tareas de Gradle

Si estás usando la línea de comandos, puedes usar los siguientes comandos de Gradle para empezar el juego:

- `./gradlew runClient` - Lanza el juego en modo de cliente.
- `./gradlew runServer` - Lanza el juego en modo de servidor.

El único problema con esta opción es que no puedes depurar tu código fácilmente. Si quieres depurar tu código, deberás usar los perfiles de lanzamiento en IntelliJ IDEA o mediante la integración de Gradle de tu IDE.

## Intercambiar Clases en tiempo de ejecución

Cuando corres el juego en modo de depuración, puedes intercambiar clases sin tener que reiniciar el juego. Esto es útil para probar cambios en tu código rápidamente.

Sin embargo, existen varias limitaciones:

- No puedes agregar o remover métodos
- No puedes cambiar los parámetros de un método
- No puedes agregar o remover miembros

## Intercambiar Mixins en tiempo de ejecución

Si estás usando Mixins, puedes intercambiar tus clases de Mixin sin tener que reiniciar el juego. Esto es útil para probar cambios en tu Mixins rápidamente.

Sin embargo, deberás instalar el _Mixin java agent_ para que esto funcione.

### 1. Localiza el _Mixin Library Jar_ (Jar de Librería de Mixin)

En IntelliJ IDEA, puedes encontrar el jar de librería de mixin en la sección de "Librerías Externas" en la sección de "Proyecto":

![Librería de Mixin](/assets/develop/getting-started/mixin-library.png)

Deberás copiar el "absolute path" (dirección absoluta) del jar para el siguiente paso.

### 2. Agrega el argumento de VM de `-javaagent`

En tus perfiles de lanzamiento de "Minecraft Client" y/o "Minecraft Server", agrega los siguientes argumentos de VM:

```:no-line-numbers
-javaagent:"dirección absoluta del jar de librería de mixin aquí"
```

![Captura de pantalla los Argumentos de VM](/assets/develop/getting-started/vm-arguments.png)

Ahora deberías poder modificar los contenidos de tus métodos mixin durante la depuración y ver los cambios en el juego sin tener que reiniciarlo.
