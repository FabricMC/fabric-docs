# Pautas de Contribución para la Documentación de Fabric

Esta página web utiliza [VitePress](https://vitepress.vuejs.org/) para generar HTML estático a partir de varios archivos markdown. Debes familiarizarte con las extensiones de markdown que VitePress soporta [aquí.](https://vitepress.vuejs.org/guide/markdown.html#features)

## Índice de Contenido

- [Pautas de Contribución para la Documentación de Fabric](#fabric-documentation-contribution-guidelines)
  - [Como Contribuir](#how-to-contribute)
  - [Estructura de Contribuciones](#contributing-framework)
  - [Contribuir Contenido](#contributing-content)
    - [Pautas de Estilo](#style-guidelines)
    - [Guias para Expansiones](#guidance-for-expansion)
    - [Verificación de Contenido](#content-verification)
    - [Limpieza](#cleanup)

## Como Contribuir

Es recomendado que crees una nueva rama en tu bifurcación del repositorio por cada solicitud de extracción que crees. Esto facilita el manejo de multiples solicitudes de extracción al mismo tiempo.

**Si quieres tener una vista previa de tus cambios localmente, necesitarás instalar [Node.js 18+](https://nodejs.org/en/)**

Antes de correr cualquiera de estos comandos, asegúrate de correr `npm install` para instalar todas las dependencias.

**Ejecutar el servidor de desarrollo:**

Esto te permitirá ver una vista previa de tus cambios localmente en `localhost:3000`.

```bash
npm run dev
```

**Construyendo la página web:**

Esto compilará todos los archivos markdown en archivos HTML estáticos, los cuales estarán en `.vitepress/dist`

```bash
npm run build
```

**Viendo la vista previa de la página web:**

Esto ejecutará un servidor local en el puerto 3000 usando el contenido encontrado en `.vitepress/dist`

```bash
npm run preview
```

## Estructura de Contribuciones

Cualquier solicitud de extracción que modifique la estructura interna de la página web debe ser etiquetada con la etiqueta `framework`.

En realidad solo deberías realizar solicitudes de extracción para la estructura después de consultar con el equipo de documentación en el servidor de [Discord de Fabric](https://discord.gg/v6v4pMv) o mediante una propuesta.

**Nota: Modificar la configuración de los archivos de la barra lateral o la barra de navegación no cuenta como una solicitud de extracción de estructura.**

## Contribuir Contenido

La contribución de contenido es la manera principal para contribuir a la Documentación de Fabric.

Todo el contenido debe seguir nuestras pautas de estilo.

### Pautas de Estilo

Todas las páginas de la Documentación de Fabric deben seguir las pautas de estilo. Si no estás seguro sobre cualquier cosa, puedes preguntar en el servidor de [Discord de Fabric](https://discord.gg/v6v4pMv) o mediante las Discusiones de Github.

La guía de estilo es la siguiente:

1. Todas las páginas deben tener un título y una descripción como o en el asunto.

   ```md
   ---
   title: Este es el título de la página
   description: Esta es la descripción de la página
   authors:
     - UsuarioDeGithubAquí
   ---

   # ...
   ```

2. Si quieres crear o modificar páginas que contengan código, debes coloar el código en un lugar apropiado dentro del mod de referencia (localizado en el folder `/reference` del repositorio). Luego, utiliza la [funcionalidad de fragmentos de código ofrecida por VitePress](https://vitepress.dev/guide/markdown#import-code-snippets) para adjuntar el código, o si necesitas mayor control, puedes usar la [funcionalidad de transcluir de `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

   **Por ejemplo:**

   ```md
   <<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21 java}
   ```

   Esto adjuntará las líneas 15-21 del archivo `FabricDocsReference.java` del mod de referencia.

   El fragmento de código resultante se verá así:

   ```java
     @Override
     public void onInitialize() {
       // This code runs as soon as Minecraft is in a mod-load-ready state.
       // However, some things (like resources) may still be uninitialized.
       // Proceed with mild caution.

       LOGGER.info("Hello Fabric world!");
     }
   ```

   **Ejemplo de translcuir:**

   ```md
   @[code transcludeWith=#test_transclude](@/reference/.../blah.java)
   ```

   Esto adjuntará las secciones de `blah.java` marcadas con la etiqueta `#test_transclude`.

   Por ejemplo:

   ```java
   public final String test = "Bye World!"

   // #test_transclude
   public void test() {
     System.out.println("Hello World!");
   }
   // #test_transclude
   ```

   Solo se adjuntará el código entre las etiquetas de `#test_transclude`.

   ```java
   public void test() {
     System.out.println("Hello World!");
   }
   ```

3. Seguimos las reglas gramáticas del Inglés Estadounidense. Aunque puedes usar [LanguageTool](https://languagetool.org/) para verificar tu gramática mientras que escribes, no te estreses mucho por ello. Nuestro equipo de documentación verificará y corregirá la gramática durante la etapa de limpieza. Sin embargo, puedes ahorrarnos tiempo tomando esfuerzo en escribir bien.

4. Si estás creando una nueva sección, debes crear una nueva barra lateral en el folder de `.vitepress/sidebars` y agregarla en el archivo `config.mts`. Si necesitas ayuda con esto, puedes preguntar en el canal de `#wiki` en el servidor de [Discord de Fabric](https://discord.gg/v6v4pMv).

5. Cuando crees una nueva página, debes agregarla a la barra lateral correspondiente en el folder de `.vitepress/sidebars`. De nuevo, si necesitas ayuda, pregunta en el servidor de Discord de Fabric, en el canal de `#wiki`.

6. Cualquier imagen debe ser colocada en un lugar apropiado en el folder de `/assets`.

7. ⚠️ **Cuando enlaces a otras páginas, usa links relativos.** ⚠️

   Esto es debido al sistema de versiones presente, el cual procesará los links para agregar la versión antes. Si usas links absolutos, el número de versión no será agregado al link.

   Por ejemplo, para una página en el folder de `/players`, enlazar a la página de `installing-fabric` encontrada en `/players/installing-fabric.md` requerirá de lo siguiente:

   ```md
   [Esto es un link a otra página](./installing-fabric.md)
   ```

   **NO** hagas lo siguiente:

   ```md
   [Esto es un link a otra página](/player/installing-fabric)
   ```

Todas las contribuciones de contenido pasa por tres etapas:

1. Guías para expansiones (si es posible)
2. Verificación de Contenido
3. Limpieza (Gramática etc.)

### Guías para Expansiones

Si el equipo de documentación piensa que puedes expandir tu solicitud de extracción en cuanto al contenido, un miembro del equipo añadirá la etiqueta `expansion` a tu solicitud de extracción junto con un comentario explicando lo que el miembro de equipo piensa que puedes hacer para expandir tu solicitud. Si estás de acuerdo con la sugerencia, puedes expandir tu solicitud de extracción.

**No te sientas presionado en expandir tu solicitud de extracción.** Si no quieres expandirla, simplemente puedes pedir que se te remueva la etiqueta de `expansion`.

Si no quieres expandir tu solicitud de extracción, pero quisieras que otra persona lo expanda después, es mejor crear una propuesta en la [Página de Propuestas](https://github.com/FabricMC/fabric-docs/issues) y explicar lo que quisieras que se expanda.

### Verificación de Contenido

Esta etapa es la más importante, ya que asegura que el contenido es correcto y sigue las Pautas de Estilo de Documentación de Fabric.

### Limpieza

¡En esta etapa, el equipo de documentación arreglará problemas con la gramática y hará cualquier otros cambios lingüísticos necesarios antes de aceptar la solicitud de extracción!
