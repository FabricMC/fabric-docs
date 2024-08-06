---
title: 기본 렌더링 개념
description: Minecraft의 렌더링 엔진에 사용되는 기본적인 렌더링의 개념을 알아보세요.
authors:
  - IMB11
  - "0x3C50"

search: false
---

# 기본 렌더링 개념

::: warning
Although Minecraft is built using OpenGL, as of version 1.17+ you cannot use legacy OpenGL methods to render your own things. Instead, you must use the new `BufferBuilder` system, which formats rendering data and uploads it to OpenGL to draw.

간단히 요약하자면, Minecraft의 렌더링 시스템을 사용하거나, `GL.glDrawElements()`를 활용하는 자체 렌더링 시스템을 구축해야 합니다.
:::

이 튜토리얼에서는 새로운 렌더링 시스템을 만들며 렌더링의 기본 사항과 주요 용어, 개념에 대해 알아볼 것입니다.

Minecraft에서 렌더링은 여러 `DrawContext` 메소드를 통해 추상화 되어있어, 이 튜토리얼에 언급된대로 하지 않아도 되지만, 그래도 렌더링의 기본 개념을 이해할 필요는 있습니다.

## `Tessellator`

`Tessellator`는 Minecraft에서 렌더링에 사용하는 기본(Main) 클래스입니다. 이는 싱글톤이므로, 게임에 오직 하나의 인스턴스만 존재할 수 있습니다. `Tessellator.getInstance()`를 사용하여 인스턴스를 불러올 수 있습니다.

## `BufferBuilder`

`BufferBuilder`는 OpenGL에 렌더링을 포맷하고 업로드하는 클래스입니다. 이는 화면에 게임을 그리기 위해 OpenGL에 업로드되는 Buffer를 생성합니다.

`Tessellator`는 `BufferBuilder`를 생성하기 위해 사용됩니다. `Tessellator.getBuffer()`를 사용해 `BufferBuilder`를 생성할 수 있습니다.

### `BufferBuilder` 초기화 하기

`BufferBuilder`에 무엇이든 쓰기 전에, 먼저 초기화해야 합니다. 이는 `VertexFormat` 및 그리기 모드를 입력으로 받는 `BufferBuilder.begin(...)` 메소드를 통해 할 수 있습니다.

#### Vertex(꼭지점) 포맷

`VertexFormat`은 데이터 버퍼에 포함할 요소를 정의하고 어떻게 물체가 OpenGL에서 처리되어야 하는지에 대한 개요를 만듭니다.

`VertexFormat`에는 다음과 같은 요소가 있습니다.

| 요소                                            | 포맷                                                                                      |
| --------------------------------------------- | --------------------------------------------------------------------------------------- |
| `BLIT_SCREEN`                                 | `{ position (3 floats: x, y and z), uv (2 floats), color (4 ubytes) }`                  |
| `POSITION_COLOR_TEXTURE_LIGHT_NORMAL`         | `{ position, color, texture uv, texture light (2 shorts), texture normal (3 sbytes) }`  |
| `POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL` | `{ position, color, texture uv, overlay (2 shorts), texture light, normal (3 sbytes) }` |
| `POSITION_TEXTURE_COLOR_LIGHT`                | `{ position, texture uv, color, texture light }`                                        |
| `POSITION`                                    | `{ position }`                                                                          |
| `POSITION_COLOR`                              | `{ position, color }`                                                                   |
| `LINES`                                       | `{ position, color, normal }`                                                           |
| `POSITION_COLOR_LIGHT`                        | `{ position, color, light }`                                                            |
| `POSITION_TEXTURE`                            | `{ position, uv }`                                                                      |
| `POSITION_COLOR_TEXTURE`                      | `{ position, color, uv }`                                                               |
| `POSITION_TEXTURE_COLOR`                      | `{ position, uv, color }`                                                               |
| `POSITION_COLOR_TEXTURE_LIGHT`                | `{ position, color, uv, light }`                                                        |
| `POSITION_TEXTURE_LIGHT_COLOR`                | `{ position, uv, light, color }`                                                        |
| `POSITION_TEXTURE_COLOR_NORMAL`               | `{ position, uv, color, normal }`                                                       |

#### 그리기 모드

그리기 모드는 데이터가 그려지는 방법을 결정합니다. 다음과 같은 그리기 모드가 사용가능합니다.

| 그리기 모드                      | 설명                                                                                                             |
| --------------------------- | -------------------------------------------------------------------------------------------------------------- |
| `DrawMode.LINES`            | 각 요소는 2개의 꼭지점으로 구성되며 단일 선으로 표시됩니다.                                                             |
| `DrawMode.LINE_STRIP`       | 첫 번째 선만 2개의 꼭지점을 가집니다. 이후 꼭지점은 기존에 있던 꼭지점과 연결되어, 연속적인 줄을 만듭니다.                 |
| `DrawMode.DEBUG_LINES`      | `DrawMode.LINES`와 비슷하지만, 항상 선이 화면에서 1px 너비로 표시됩니다.                                             |
| `DrawMode.DEBUG_LINE_STRIP` | `DrawMode.LINE_STRIP`과 같지만, 항상 선이 화면에 1px 너비로 표시됩니다.                                           |
| `DrawMode.TRIANGLES`        | 각 요소가 3개의 꼭지점으로 만들어져, 삼각형이 구성합니다.                                                              |
| `DrawMode.TRIANGLE_STRIP`   | 첫 삼각형만 세 개의 꼭지점을 가집니다. 이후 추가된 꼭지점은 기존에 있던 두 꼭지점으로 새 삼각형을 구성하게 됩니다.             |
| `DrawMode.TRIANGLE_FAN`     | 첫 삼각형만 세 개의 꼭지점을 가집니다. 이후 추가된 꼭지점은 기존에 있던 첫 번째 꼭지점과 마지막 꼭지점으로 새 삼각형을 구성하게 됩니다. |
| `DrawMode.QUADS`            | 각 요소가 4개의 꼭지점으로 만들어져, 사각형이 구성합니다.                                                              |

### `BufferBuilder` 쓰기

`BufferBuilder`가 초기화 되면, 이제 데이터를 쓸 수 있습니다.

`BufferBuilder`로 버퍼를 만들거나, 꼭지점과 꼭지점을 연결할 수 있습니다. 꼭지점을 추가하려면, `buffer.vertex(matrix, float, float, float)` 메소드를 사용합니다. `matrix` 매개변수는 변환 행렬로, 아래에서 더 자세하게 설명할 예정입니다. 세 float 매개변수는 꼭지점의 (x, y, z) 좌표를 의미합니다.

사용하면 꼭지점에 추가적인 정보를 추가할 수 있게 꼭지점 빌더가 반환됩니다. 정보를 추가할 때 추가한 `VertexFormat` 순서를 따르는 것이 중요합니다. 그렇지 않으면 OpenGL이 데이터를 제대로 해석하지 못할 수 있습니다. 꼭지점을 모두 추가했다면, 이제 `.next()` 메소드를 호출해 봅시다. 이 메소드는 현재 꼭지점을 완성하고 다른 요소를 추가할 수 있도록 버퍼를 반환합니다.

컬링의 개념을 이해하는 것도 가치 있는 일입니다. 컬링은 플레이어의 시야에서 보이지 않는 3차원 면을 제거하는 기법입니다. 만약 면의 꼭지점이 잘못된 순서로 배열되어 있으면, 컬링으로 인해 면이 정상적으로 렌더링되지 않을 수 있습니다.

#### 변환 행렬이란?

변환 행렬은 벡터를 변환하기 위해 사용되는 4x4 행렬입니다. Minecraft에서는, 변환 행렬은 꼭지점 생성에서 입력된 좌표를 변환하는게 끝입니다. 변환을 통해 꼭지점의 크기를 키우거나, 움직이거나, 회전할 수 있습니다.

때로는 위치 행렬 또는 모델 행렬이라고도 합니다.

일반적으로 `DrawContext`의 `MatrixStack` 클래스를 통해 가져올 수 있습니다.

```java
drawContext.getMatrices().peek().getPositionMatrix();
```

#### 실전 예시: 삼각형 스트립 렌더링하기

현실적인 예시로 `BufferBuilder`를 쓰는 방법을 설명하는 것이 더 쉽습니다. `DrawMode.TRIANGLE_STRIP` 그리기 모드와 `POSITION_COLOR` 꼭지점 포맷으로 무언가를 렌더링하고 싶다고 가정해봅시다.

순서대로 HUD에 꼭지점을 그려봅시다.

```txt
(20, 20)
(5, 40)
(35, 40)
(20, 60)
```

`TRIANGLE_STRIP` 그리기 모드를 사용했으므로, 꼭지점을 그리면 다음과 같은 과정을 거쳐 사랑스러운 다이아몬드가 렌더링될 것입니다.

![두 삼각형의 꼭지점을 화면에 표시하는 4단계](/assets/develop/rendering/concepts-practical-example-draw-process.png)

이 튜토리얼에서는 HUD에 그리고 있으므로, `HudRenderCallback` 이벤트를 사용하겠습니다.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

이렇게 하면 HUD에 두 삼각형이 그려지게 됩니다.

![최종 결과](/assets/develop/rendering/concepts-practical-example-final-result.png)

:::tip
색을 지정하거나 꼭지점의 위치를 옮겨 어떤 변화가 일어나는지 알아보세요! 다른 그리기 모드와 꼭지점 포맷을 사용해 볼수도 있습니다.
:::

## `MatrixStack`

어떻게 `BufferBuilder`를 쓰는지 알았다면, 이제 어떻게 모델을 움직이고, 좀 더 멋진 분들은 어떻게 애니메이션을 적용할지 궁금할 수도 있습니다. 이제 `MatrixStack` 클래스가 나설 때입니다.

`MatrixStack` 클래스에는 다음 메소드가 포함되어 있습니다.

- `push()` - 새 행렬을 스택으로 (밀어) 넣습니다.
- `pop()` - 스택의 최상단 행렬을 소거합니다.
- `peek()` - 스택의 최상단 행렬을 반환합니다.
- `translate(x, y, z)` - 최상단 스택을 이동합니다.
- `scale(x, y, z)` - 최상단 스택의 크기를 조절합니다.

다음 섹션에서 알아볼 쿼터니언을 통해 최상단 행렬을 곱할 수도 있습니다.

위 예시에서는, `MatrixStack`과 `tickDelta` (프레임이 변경될 때 소요된 시간) 를 통해 다이아몬드의 크기를 조절할 수 있습니다.

::: warning
You must push and pop the matrix stack when you're done with it. If you don't, you'll end up with a broken matrix stack, which will cause rendering issues.

행렬을 변환하기 전에 행렬 스택을 넣었는지 확인하세요!
:::

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

![다이아몬드의 크기가 변화하는 모습을 보여주는 영상](/assets/develop/rendering/concepts-matrix-stack.webp)

## 쿼터니언 (회전)

쿼터니언은 3차원에서 회전을 표현하는 방법입니다. 이는 `MatrixStack`의 최상단 행렬을 `multiply(Quaternion, x, y, z)` 메소드를 통해 회전할 때 사용됩니다.

Minecraft가 `RotationAxis` 도구 클래스에 여러 쿼터니언 인스턴스를 먼저 생성해 두었기 때문에 쿼터니언 클래스를 바로 사용할 필요는 없습니다.

그럼 다이아몬드의 Z축을 회전해 봅시다. `MatrixStack`과 `multiply(Quaternion, x, y, z)` 메소드를 통해 회전할 수 있습니다.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

결과는 다음과 같습니다.

![다이아몬드의 Z축이 변화하는 모습을 보여주는 영상](/assets/develop/rendering/concepts-quaternions.webp)
