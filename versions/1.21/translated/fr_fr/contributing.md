---
title: Lignes directrices pour les contributions
description: Lignes directrices pour les contributions à la documentation de Fabric.
---

# Lignes directrices pour les contributions {#contributing}

Ce site utilise [VitePress](https://vitepress.dev/) pour générer du HTML statique à partir de divers fichiers Markdown. Vous devriez vous familiariser avec les extensions Markdown que VitePress supporte [ici](https://vitepress.dev/guide/markdown#features).

Il y a trois façons de contribuer à ce site web :

- [Traduire la documentation](#translating-documentation)
- [Contribuer au contenu](#contributing-content)
- [Contribuer au framework](#contributing-framework)

Toutes les contributions doivent suivre nos [lignes directrices de style](#style-guidelines).

## Traduire la documentation {#translating-documentation}

Si vous souhaitez traduire la documentation dans votre langue, vous pouvez le faire sur la [page Crowdin de Fabric](https://crowdin.com/project/fabricmc).

<!-- markdownlint-disable-next-line titlecase-rule -->

## <Badge type="tip">new-content</Badge> Contribuer au contenu {#contributing-content}

Les contributions de contenu sont le principal moyen de contribuer à la documentation de Fabric.

Toutes les contributions de contenu passent par les étapes suivantes, chacune étant associée à une étiquette :

1. <Badge type="tip">locally</Badge> Préparez vos modifications et soumettez une PR
2. <Badge type="tip">stage:expansion</Badge>: Guide pour l'expansion si nécessaire
3. <Badge type="tip">stage:verification</Badge>: Vérification du contenu
4. <Badge type="tip">stage:cleanup</Badge>: Grammaire, Linting...
5. <Badge type="tip">stage:ready</Badge>: Prêt à être fusionné !

Tout le contenu doit suivre nos [lignes directrices de style](#style-guidelines).

### 1. Préparez vos modifications {#1-prepare-your-changes}

Ce site est open-source et est développé dans un dépôt GitHub, ce qui signifie que nous nous appuyons sur le flux GitHub :

1. [Forkez le dépôt Github](https://github.com/FabricMC/fabric-docs/fork)
2. Créez une nouvelle branche sur votre fork
3. Effectuez vos modifications sur cette branche
4. Ouvrez une Pull Request vers le dépôt original

Vous pouvez en savoir plus sur le flux GitHub [ici](https://docs.github.com/en/get-started/using-github/github-flow).

Vous pouvez soit apporter des modifications depuis l'interface web de GitHub, soit développer et prévisualiser le site localement.

#### Cloner votre fork {#clone-your-fork}

Si vous souhaitez développer localement, vous devrez installer [Git](https://git-scm.com/).

Ensuite, clonez votre fork du dépôt avec :

```sh
# make sure to replace "your-username" with your actual username
git clone https://github.com/your-username/fabric-docs.git
```

#### Installer les dépendances {#install-dependencies}

Si vous souhaitez prévisualiser vos modifications localement, vous devrez installer [Node.js 18+](https://nodejs.org/en/).

Ensuite, assurez-vous d'installer toutes les dépendances avec :

```sh
npm install
```

#### Exécuter le serveur de développement {#run-the-development-server}

Cela vous permettra de prévisualiser vos modifications localement à l'adresse `localhost:5173` et rechargera automatiquement la page lorsque vous apporterez des modifications.

```sh
npm run dev
```

Vous pouvez maintenant ouvrir et parcourir le site web depuis le navigateur en visitant `http://localhost:5173`.

#### Construire le site web {#building-the-website}

Cela va compiler tous les fichiers Markdown en fichiers HTML statiques et les placer dans `.vitepress/dist` :

```sh
npm run build
```

#### Prévisualiser le site web construit {#previewing-the-built-website}

Cela va démarrer un serveur local sur le port 4173 servant le contenu trouvé dans `.vitepress/dist` :

```sh
npm run preview
```

#### Ouvrir une Pull Request {#opening-a-pull-request}

Une fois que vous êtes satisfait de vos modifications, vous pouvez `push` vos modifications :

```sh
git add .
git commit -m "Description of your changes"
git push
```

Ensuite, suivez le lien dans la sortie de `git push` pour ouvrir une PR.

### 2. <Badge type="tip">stage:expansion</Badge> Guide pour l'expansion si nécessaire {#2-guidance-for-expansion-if-needed}

Si l'équipe de documentation pense que vous pourriez développer votre pull request, un membre de l'équipe ajoutera l'étiquette <Badge type="tip">stage:expansion</Badge> à votre pull request avec un commentaire expliquant ce qu'ils pensent que vous pourriez développer. Si vous êtes d'accord avec la suggestion, vous pouvez développer votre pull request.

Si vous ne souhaitez pas développer votre pull request, mais que vous êtes d'accord pour que quelqu'un d'autre le fasse ultérieurement, vous devriez créer une issue sur la page des [Issues](https://github.com/FabricMC/fabric-docs/issues) et expliquer ce que vous pensez pouvoir être développé. L'équipe de documentation ajoutera alors l'étiquette <Badge type="tip">help-wanted</Badge> à votre PR.

### 3. <Badge type="tip">stage:verification</Badge> Vérification du contenu {#3-content-verification}

C'est l'étape la plus importante car elle garantit que le contenu est exact et suit le guide de style de la documentation Fabric.

À cette étape, les questions suivantes doivent être répondues :

- Tout le contenu est-il correct ?
- Tout le contenu est-il à jour ?
- Le contenu couvre-t-il tous les cas, comme les différents systèmes d'exploitation ?

### 4. <Badge type="tip">stage:cleanup</Badge> Nettoyage {#4-cleanup}

À cette étape, les actions suivantes sont effectuées :

- Correction des problèmes de grammaire à l'aide de [LanguageTool](https://languagetool.org/)
- Vérification de tous les fichiers Markdown à l'aide de [`markdownlint`](https://github.com/DavidAnson/markdownlint)
- Formatage de tout le code Java à l'aide de [Checkstyle](https://checkstyle.sourceforge.io/)
- Autres corrections ou améliorations diverses

## <Badge type="tip">framework</Badge> Contribuer au Framework {#contributing-framework}

Le framework fait référence à la structure interne du site web. Toute pull request modifiant le framework du site web sera étiquetée avec l'étiquette <Badge type="tip">framework</Badge>.

Vous devriez vraiment ne faire des pull requests de framework qu'après avoir consulté l'équipe de documentation sur le [Discord de Fabric](https://discord.gg/v6v4pMv) ou via une issue.

:::info
La modification des fichiers de la barre latérale et de la configuration de la barre de navigation ne compte pas comme une pull request de framework.
:::

## Lignes directrices de style {#style-guidelines}

Si vous avez des doutes sur quoi que ce soit, vous pouvez poser vos questions sur le [Discord de Fabric](https://discord.gg/v6v4pMv) ou via les Discussions GitHub.

### Écrire l'original en anglais américain {#write-the-original-in-american-english}

Toute la documentation originale est rédigée en anglais, en suivant les règles de grammaire américaines.

### Ajouter des données au Frontmatter {#add-data-to-the-frontmatter}

Chaque page doit avoir un `title` et une `description` dans le frontmatter.

N'oubliez pas d'ajouter également votre nom d'utilisateur GitHub à `authors` dans le frontmatter du fichier Markdown ! De cette manière, nous pouvons vous attribuer le mérite qui vous revient.

```md
---
title: Title of the Page
description: This is the description of the page.
authors:
  - your-username
---

# Title of the Page {#title-of-the-page}

...
```

### Ajouter des ancres aux titres {#add-anchors-to-headings}

Chaque titre doit avoir une ancre, qui est utilisée pour créer un lien vers ce titre :

```md
# This Is a Heading {#this-is-a-heading}
```

L'ancre doit utiliser des caractères en minuscules, des chiffres et des tirets.

### Placer le code dans le mod `/reference` {#place-code-within-the-reference-mod}

Si vous créez ou modifiez des pages contenant du code, placez le code à un emplacement approprié dans le mod de référence (situé dans le dossier `/reference` du dépôt). Ensuite, utilisez la [fonctionnalité d'extrait de code offerte par VitePress](https://vitepress.dev/guide/markdown#import-code-snippets) pour intégrer le code.

Par exemple, pour mettre en évidence les lignes 15-21 du fichier `FabricDocsReference.java` du mod de référence :

::: code-group

```md
<<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21}
```

<<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21}[java]

:::

Si vous avez besoin d'un contrôle plus étendu, vous pouvez utiliser la [fonctionnalité de transclusion de `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

Par exemple, ceci intégrera les sections du fichier ci-dessus qui sont marquées avec l'étiquette `#entrypoint` :

::: code-group

```md
@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)
```

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

:::

### Créer une barre latérale pour chaque nouvelle section {#create-a-sidebar-for-each-new-section}

Si vous créez une nouvelle section, vous devez créer une nouvelle barre latérale dans le dossier `.vitepress/sidebars` et l'ajouter au fichier `i18n.mts`.

Si vous avez besoin d'aide, veuillez poser votre question dans le canal #docs du [Discord de Fabric](https://discord.gg/v6v4pMv).

### Ajouter de nouvelles pages aux barres latérales pertinentes {#add-new-pages-to-the-relevant-sidebars}

Lors de la création d'une nouvelle page, vous devez l'ajouter à la barre latérale pertinente dans le dossier `.vitepress/sidebars`.

Encore une fois, si vous avez besoin d'aide, demandez dans le canal `#docs` du Discord de Fabric.

### Placer les médias dans `/assets` {#place-media-in-assets}

Toutes les images doivent être placées à un endroit approprié dans le dossier `/public/assets`.

### Utilisez des liens relatifs ! {#use-relative-links}

Cela est dû au système de versioning en place, qui traitera les liens pour ajouter la version au préalable. Si vous utilisez des liens absolus, le numéro de version ne sera pas ajouté au lien.

Vous ne devez pas non plus ajouter l'extension de fichier au lien.

Par exemple, pour créer un lien vers la page trouvée dans `/players/index.md` depuis la page `/develop/index.md`, vous devriez faire ce qui suit :

::: code-group

```md:no-line-numbers [✅ Correct]
This is a relative link!
[Page](../players/index)
```

```md:no-line-numbers [❌ Wrong]
This is an absolute link.
[Page](/players/index)
```

```md:no-line-numbers [❌ Wrong]
This relative link has the file extension.
[Page](../players/index.md)
```

:::
