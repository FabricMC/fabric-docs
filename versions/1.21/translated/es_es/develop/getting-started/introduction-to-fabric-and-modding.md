---
title: Introducción a Fabric y el desarrollo de Mods
description: "Una breve introducción a Fabric y el desarrollo de mods en Minecraft: Edición de Java."
authors:
  - IMB11
  - itsmiir
authors-nogithub:
  - basil4088

search: false
---

# Introducción a Fabric y el desarrollo de Mods

## Requisitos Previos

Antes de empezar, deberías tener un entendimiento básico de programación en Java, y conocimiento de Programación Orientada a Objetos (POO).

Si no estás familiarizados con estos conceptos, deberías investigar algunos tutoriales sobre Java y POO antes de que empiezas a desarrollar mods, aquí hay algunos de los recursos que puedes usar para aprender Java y POO:

- [W3: Java Tutorials](https://www.w3schools.com/java/)
- [Codecademy: Learn Java](https://www.codecademy.com/learn/learn-java)
- [W3: Java OOP](https://www.w3schools.com/java/java_oop.asp)
- [Medium: Introduction to OOP](https://medium.com/@Adekola_Olawale/beginners-guide-to-object-oriented-programming-a94601ea2fbd)

### Terminología

Antes de empezar, veamos algunos de los términos que usaremos cuando desarrollemos mods para Fabric:

- **Mod**: Una modificación al juego, que añade nuevas funciones o características o que cambia funciones o características existentes.
- **Mod Loader** (Lanzador o Cargador de Mods): Una herramienta que carga los mods en el juego, como el Lanzador de Fabric.
- **Mixin**: Una herramienta para modificar el código del juego durante el tiempo de ejecución - visita [Introducción a los Mixins](https://fabricmc.net/wiki/tutorial:mixin_introduction) para más información.
- **Gradle**: Una herramienta para la automatización de construcción que se usa para compilar y construir los mods, usado por Fabric para construir mods.
- **Mappings** (Mapeos): Un conjunto de mapeos que traducen o "mapean" código ofuscado a código legible.
- **Obfuscation** (Ofuscación): El proceso de hacer que el código sea difícil de entender, usando por Mojang para proteger el código de Minecraft.
- **Remapping** (Remapeado): El proceso de mapear código ofuscado a código legible.

## ¿Qué es Fabric?

Fabric is un conjunto de herramientas ligeras para moddear Minecraft: Edición de Java.

Está diseñado para ser una plataforma de moddeado simple y fácil de entender. Fabric es un proyecto dirigido por la comunidad, y es de fuente abierta, lo que significa que cualquiera puede contribuir al proyecto.

Deberías saber de los cuatro componentes principales de Fabric:

- **Fabric Loader** (Lanzador de Fabric): Un lanzador de mods flexible diseñado para Minecraft y otros juegos y aplicaciones, que funciona independiente de la plataforma.
- **Fabric Loom**: Un plugin o extensión de Gradle para desarrollar y depurar mods fácilmente.
- **Fabric API**: Un conjunto de APIs y herramientas para desarrolladores de mods para ser usadas durante la creación de mods.
- **Yarn**: Un conjunto de mapeos de Fabric abiertos. Cualquiera puede usarlos bajo la licencia de Creative Commons Zero.

## ¿Por qué es necesario Fabric para crear mods para Minecraft?

> El moddeado es el proceso de modificar el juego con el fin de cambiar su comportamiento y añadir nuevas funciones - en el caso de Minecraft, esto puede ser cualquier cosa, como añadir nuevos items, bloques, o entidades, o cambiar las mecánicas del juego o añadiendo nuevos modos de juego.

Minecraft: Edición de Java es ofuscado por Mojang, haciendo que la modificación sea dificil por sí sola. Sin embargo, mediante la ayuda de herramientas para la creación de mods como Fabric, la creación de mods se hace mucho más facil. Hay varios sistemas sde mapeo que pueden asistir en el proceso.

Loom remappea el código ofuscado a código legible usando estos mappings, permitiéndole a los desarrolladores de mods entender y modificar el código del juego. Yarn es una elección popular y excelente para los mpapings, pero también existen otras opciones. Cada proyecto de mappings tiene sus propias ventajas o enfoque.

Loom te permite fácilmente desarrollar y compilar mods basados en código remapeado, y el Lanzador de Fabric te permite cargar estos mods en el juego.

## ¿Qué provee el Fabric API, y por qué es necesario?

> El Fabric API es un conjunto de APIs y herramientas para desarrolladores de mods para ser usadas durante la creación de mods.

Fabric API provee un conjunto amplio de APIs basadas en la funcionalidad de Minecraft existente - por ejemplo, provee nuevos ganchos y eventos para ser usados por desarrolladores de mods, o provee nuevas utilidades para facilitar el desarrollo de mods - como _access wideners_ (ampliadores de acceso) transitivos y la abilidad de usar registros internos, como el registro de items compostables.

Mientras que Fabric API ofrece funciones poderosas, algunas tares, como la registración básica de bloques, puede ser lograda sin ella, usando los APIs vanilla.
