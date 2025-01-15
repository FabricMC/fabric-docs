# Diretrizes de Contribuição da Documentação do Fabric

Este site usa [VitePress](https://vitepress.dev/) para gerar HTML estático de vários arquivos do Markdown. Você deve estar familiarizado com as extensões Markdown que o VitePress suporta [aqui](https://vitepress.dev/guide/markdown#features).

## Sumário

- [Diretrizes de contribuição da documentação do Fabric](#fabric-documentation-contribution-guidelines)
  - [Como Contribuir](#how-to-contribute)
  - [Estrutura Da Contribuição](#contributing-framework)
  - [Conteúdo Da Contribuição](#contributing-content)
    - [Diretrizes A Se Seguir](#style-guidelines)
    - [Instruções para Expandir](#guidance-for-expansion)
    - [Verificação De Conteúdo](#content-verification)
    - [Apuração](#cleanup)
  - [Traduzindo a Documentação](#translating-documentation)

## Como contribuir

É recomendado que você crie uma nova "branch" no seu "fork do repositório" a cada "pull request" que você faça. Isso faz ser mais fácil de gerenciar múltiplos "pull requests" de uma vez.

**Se você quiser visualizar suas alterações localmente, basta instalar [Node.js 18+](https://nodejs.org/en/)**

Antes de executar alguns desses comandos, certifique-se de executar `npm install` para instalar todas as dependências.

**Executando o ambiente de desenvolvimento.**

Isso vai permitir que você visualize as alterações localmente em: `localhost:3000` e recarregará automaticamente a página quando você fizer alterações.

```sh
npm run dev
```

**Fazendo o Website.**

Isto irá compilar todos os arquivos Markdown em arquivos HTML estáticos e colocá-los em: `.vitepress/dist`

```sh
npm run build
```

**Visualizando o Site Feito.**

Isso iniciará um servidor local na porta 3000 servindo o conteúdo encontrado em `.vitepress/dist`

```sh
npm run preview
```

## Estrutura Da Contribuição

"Estrutura" refere-se a estrutura interna do website, qualquer "pull request" que modifique essa "estrutura" do site vai ser relatada como: `framework`

Na verdade, você só deve fazer solicitações e "pull request" de frameworks após consultar a equipe da documentação do [Fabric Discord](https://discord.gg/v6v4pMv) ou após relatar um problema.

**Observação: modificar os arquivos da barra lateral e da configuração da barra de navegação não conta como uma solicitação de "pull request de framework.**

## Contribuindo com Conteúdo

Contribuições com conteúdo são a principal forma de contribuir com a documentação do Fabric.

Todo o conteúdo deve seguir as nossas diretrizes.

### Diretrizes A Se Seguir

Todas as páginas de site de documentação do Fabric devem seguir as diretrizes. Se estiver em duvida de alguma coisa, você pode perguntar no [Fabric Discord](https://discord.gg/v6v4pMv) ou por meio de discussões no GitHub.

As diretrizes a serem seguidas:

1. Todas as páginas devem ter a descrição e o título no topo, a mostra.

   ```md
   ---
   título: Esse é o Título da Pagina
   Descrição: Essa é a descrição da página
   autores:
     - NomeAleatórioDoGitHub
   ---

   # ...
   ```

2. Se você criar ou modificar páginas contendo código, coloque o código em um local apropriado com o mod de referência (localizado na pasta `/reference` do repositório). Em seguida, use o [recurso de "snippet" de código oferecido pelo "VitePress"](https://vitepress.dev/guide/markdown#import-code-snippets) para incorporar o código ou, se precisar de maior controle, você pode usar o [transcluir recurso de `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

   **Exemplo.**

   ```md
   <<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21 java}
   ```

   Isso incorporará as linhas 15-21 do arquivo `FabricDocsReference.java` no mod de referência.

   O trecho do código ficará assim:

   ```java
     @Override
     public void onInitialize() {
       // Este código é executado quando o Minecraft esta pronto para carregar mods.
       // No entanto, algumas coisas (como recursos) ainda podem não ser inicializadas.
       // Proceda com atenção.

       LOGGER.info("Olá Mundo Fabric!");
     }
   ```

   **Exemplo de transclusão:**

   ```md
   @[code transcludeWith=#test_transclude](@/reference/.../blah.java)
   ```

   Isso incorporará as seções de `blah.java` marcadas com a tag `#test_transclude`.

   Como por exemplo:

   ```java
   public final String test = "Tchau Mundo!"

   // #test_transclude
   public void test() {
     System.out.println("Olá Mundo!");
   }
   // #test_transclude
   ```

   Somente o código entre as tags `#test_transclude` será incorporado.

   ```java
   public void test() {
     System.out.println("Olá Mundo!");
   }
   ```

3. A documentação original inteira é escrita em Inglês, seguindo as regras da gramática Estadunidense. Embora você possa usar o [LanguageTool](https://languagetool.org/) para verificar sua gramática enquanto digita,mas não se encuque muito com isso. Nossa equipe de documentação revisará e corrigirá a gramática durante a fase de revisão. No entanto, se esforçar para acertar inicialmente pode nos poupar um bom tempo.

4. Se você estiver criando uma nova seção, você deve criar uma nova barra lateral na pasta `.vitepress/sidebars` e adicioná-la ao arquivo `config.mts`. Se você precisa de assistência com isso, por favor peça no [Discord do Fabric](https://discord.gg/v6v4pMv) no canal `#docs`.

5. Ao criar uma nova página, você deve adicioná-la à barra lateral relevante na pasta `.vitepress/sidebars`. De novo, se precisar de ajuda, nos chame no Discord do Fabric no canal `#docs`.

6. Quaisquer imagens devem ser colocadas em um "local adequado" na pasta `/assets`.

7. ⚠️ **Ao vincular outras páginas, use links relativos.** ⚠️

   Isso se deve ao sistema de versionamento em vigor, que processará os links para adicionar uma versão de antemão. Se você usar links absolutos, o número da versão não será adicionado ao link.

   Por exemplo, para uma página na pasta `/players`, para vincular à página `installing-fabric` encontrada em `/players/installing-fabric.md`, você teria que fazer o seguinte:

   ```md
   [Isto é um link para outra página](./installing-fabric)
   ```

   Você **NÃO** deve fazer o seguinte:

   ```md
   [Isto é um link para outra página](/players/installing-fabric)
   ```

Todas as contribuições de conteúdo passam por três etapas:

1. Instruções para Expandir (se necessário)
2. Verificação de Conteúdo
3. Revisão (Gramatica, etc.)

### Instruções para Expandir

Se a equipe de documentação julgar que você poderia expandir seu "pull request", um membro da equipe adicionará o rótulo `can-expand` ao seu "pull request" com um comentário explicando o que eles pensam que você poderia expandir. Se você concordar com a sugestão, poderá expandir seu "pull request"

**Não se sinta pressionado em expandir seu "pull request".** Se não quiser expandir seu "pull request", você pode simplesmente solicitar que o rótulo `can-expand` seja removido.

Se você não deseja expandir seu "Pull Request", mas deseja que outra pessoa a expanda posteriormente, é melhor criar um "Issue" na [página de "issues"](https://github.com/FabricMC/fabric-docs/issues) e explique o que você acha que pode ser expandido.

### Verificação de Conteúdo

Todas as "Pull Requests" que adicionam conteúdo passam por verificação de conteúdo, esta é a etapa mais importante, pois garante que o conteúdo seja preciso e siga as diretrizes da documentação do Fabric.

### Verificação

Esta etapa é onde a equipe de documentação corrigirá quaisquer problemas gramaticais e fará quaisquer outras alterações que julgar necessárias antes de mesclar o "Pull Request"!

## Traduzindo a Documentação

Se você desejar traduzir a documentação para a sua linguagem, você pode fazer isso na [pagina do Crowdin do Fabric](https://crowdin.com/project/fabricmc).
