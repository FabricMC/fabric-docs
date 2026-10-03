---
title: Modelos de Blocos
description: Um guia para escrever e entender modelos de blocos.
authors:
  - Fellteros
  - its-miroma
resources:
  https://minecraft.wiki/w/Model#Block_models: Modelos de Blocos - Minecraft Wiki
---

<!-- markdownlint-disable search-replace -->

Essa página irá te orientar na escrita dos seus próprios modelos de blocos e no entendimento de todas as suas opções e possibilidades.

## O que São Modelos de Blocos? {#what-are-block-models}

Modelos de blocos são essencialmente a definição da aparência e dos elementos visuais de um bloco. Eles especificam a textura, translação, rotação, escala e outros atributos do modelo.

Os modelos são armazenados como arquivos JSON dentro da sua pasta `resources`.

## Estrutura do Arquivo {#file-structure}

Todos os modelos de blocos possuem uma estrutura definida que precisa ser seguida. Ela começa com chaves "{}" vazias, que representa a **tag raiz** do modelo. Aqui está um breve esquema de como os modelos de blocos são estruturados:

```json
{
  "parent": "...",
  "ambientocclusion": "true/false",
  "display": {
    "<position>": {
      "rotation": [0.0, 0.0, 0.0],
      "translation": [0.0, 0.0, 0.0],
      "scale": [0.0, 0.0, 0.0]
    }
  },
  "textures": {
    "particle": "...",
    "<texture_variable>": "..."
  },
  "elements": [
    {
      "from": [0.0, 0.0, 0.0],
      "to": [0.0, 0.0, 0.0],
      "rotation": {
        "origin": [0.0, 0.0, 0.0],
        "axis": "...",
        "angle": "...",
        "rescale": "true/false"
      },
      "shade": "true/false",
      "light_emission": "...",
      "faces": {
        "<key>": {
          "uv": [0, 0, 0, 0],
          "texture": "...",
          "cullface": "...",
          "rotation": "...",
          "tintindex": "..."
        }
      }
    }
  ]
}
```

<!-- @include: ../items/item-models.md#parent -->

Defina essa tag para {#file-structure} para usar um modelo criado pelo ícone especificado. Rotação pode ser obtida pelo [blockstates](./blockstates).

### Oclusão de Ambiente {#ambient-occlusion}

```json
{
  "ambientocclusion": "true/false"
}
```

Essa tag especifica se deve ser usado a [oclusão de ambiente](https://en.wikipedia.org/wiki/Ambient_occlusion). O valor padrão é `true`.

<!-- @include: ../items/item-models.md#display -->

### Texturas {#textures}

```json
{
  "textures": {
    "particle": "...",
    "<texture_variable>": "..."
  }
}
```

A tag `textures` armazena as texturas do modelo, na forma de um identificador ou de uma textura variável. Ela contém três objetos adicionais:

1. `particle`: _String_. Define a textura de onde as partículas serão carregadas. Essa textura é usada como uma sobreposição se você estiver em um portal do nether e também para as texturas de água e lava paradas. Também é considerada uma textura variável que pode ser referenciada como `#particle`.
2. `<texture_variable>`: _String_. Cria uma variável e atribui uma textura. Pode ser posteriormente referenciado com o prefixo `#` (Por exemplo: "top": "namespace:path"`⇒`#top\`)

<!-- @include: ../items/item-models.md#elements -->

<!-- @include: ../items/item-models.md#from -->

`from` especifica o ponto inicial do cuboide conforme o esquema `[x, y, z]`, relativo ao canto esquerdo inferior. `to` especifica o ponto final. Um cuboide tão grande como um bloco padrão começaria em `[0, 0, 0]` e terminaria em `[16, 16, 16]`.
Os valores de ambos devem ser entre **-16** e **32**, que significa que todo modelo de bloco pode ter no máximo uma dimensão de 3x3 blocos.

<!-- @include: ../items/item-models.md#rotation -->

`rotation` define a rotação de um elemento. Ela contém mais quatro valores:

1. `origin`: _Três valores de ponto flutuante_. Define o centro de rotação conforme o esquema `[x, y, z]`.
2. `axis`: _String_. Especifica a direção de rotação, ela deve ser um desses: `x`, `y` and `z`.
3. `angle`: _Ponto flutuante_. Especifica o ângulo de rotação. Varia entre **-45** a **45**.
4. `rescale`: _Valor Booleano_. Especifica se deve dimensionar as faces por todo o bloco. O valor padrão é `false`.

<!-- @include: ../items/item-models.md#shade_to_faces -->

1. `uv`: _Quatro valores inteiros._. Define a área da textura que será usada conforme o esquema `[x1, y1, x2, y2]`. Se não estiver definido, o valor padrão é igual à posição xyz do elemento.
   Trocando os valores de `x1` e `x2` (por exemplo, de `0, 0, 16, 16` para `16, 0, 0, 16`) inverte a textura. UV é opcional, e se não for fornecido, ele é automaticamente gerado com base na posição do elemento.
2. `texture`: _String_. Especifica a textura da face na forma de uma [textura variável](#textures), prefixado com `#`.
3. `cullface`: _String_. Pode ser: `down`, `up`, `north`, `south`, `west`, ou `east`. Especifica se uma face não precisa ser renderizada quando há um bloco tocando-a na posição especificada.
   Ele também determina de qual lado do bloco o nível de luz será obtido para iluminar a face e, se não for definido, o padrão será o próprio lado.
4. `rotation`: _Valor inteiro_. Rotaciona a textura no sentido horário por um número especificado de graus em incrementos de 90 graus. Rotação não afeta qual parte da textura é usado.
   Em vez disso, isso equivale a uma permutação dos vértices da textura selecionada (selecionados implicitamente ou explicitamente por meio de `uv`).
5. `tintidex`: _Valor inteiro_. Tinge a textura daquela face usando um valor de tingimento. O valor padrão, `-1`, indica para não usar o tingimento.
   Qualquer outro número é fornecido para o `BlockColors` para adquirir o valor de tingimento correspondente para aquele índice (retorna branco quando o bloco não possui um índice de tingimento definido).
