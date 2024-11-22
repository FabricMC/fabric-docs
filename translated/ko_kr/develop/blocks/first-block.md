---
title: 첫 번째 블록 만들기
description: Minecraft에서의 첫 번째 블록을 만드는 법을 알아봅시다.
authors:
  - IMB11
  - xEobardThawne
  - its-miroma
---

# 첫 번째 블록 만들기 {#creating-your-first-block}

블록은 Minecraft에서의 건축 블록입니다 (말장난 아님). Minecraft의 다른 모든 것들처럼, 마찬가지로 레지스트리에 저장됩니다.

## 블록 클래스 준비하기 {#preparing-your-blocks-class}

만약 [첫 번째 블록 만들기](../items/first-item) 페이지를 성공했다면, 이 과정은 아주 친숙하게 느껴질 것입니다. 블록과 그 아이템을 등록하는 메서드를 만들어야 합니다.

이 메서드를 `ModBlocks`이라 불리는 클래스에 넣어야 합니다. (아니면 마음대로 이름 지으세요).

Mojang은 바닐라 블록과 매우 비슷한 무언가를 수행합니다. `Blocks` 클래스를 참조해 어떻게 했는지 참고해도 됩니다.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

---

아이템처럼, 블록 인스턴스가 포함된 모든 정적 필드가 초기화되도록 클래스가 불러와졌음을 확인해야 합니다.

이것을 정적 초기화를 트리거하기 위해 모드 이니셜라이저에서 호출될 수 있는 더미 `initialize` 메서드를 만듦으로써 할 수도 있습니다.

:::info
만약 정적 초기화가 무엇인지 모르겠다면, 이는 클래스에서 정적 필드를 초기화하는 과정입니다. 이는 JVM에 의해 클래스가 로드될 때 끝나며, 어느 클래스의 인스턴스가 만들어지기 전에 끝납니다.
:::

```java
public class ModBlocks {
    // ...

    public static void initialize() {}
}
```

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/FabricDocsReferenceBlocks.java)

## 블록을 추가하고 등록하기 {#creating-and-registering-your-block}

아이템처럼, 블록은 생성자에서 `Blocks.Settings` 클래스를 이용해 소리 효과와 채굴 단계와 같은 블록의 속성을 지정합니다.

여기선 모든 옵션을 다루지는 않습니다. 클래스를 직접 보면 자명한 다양한 옵션을 볼 수 있습니다.

예시로, 흙의 속성을 가지고 있지만, 다른 재질의 간단한 블록을 만들어 볼 것입니다.

:::tip
또한 이미 존재하는 블록의 설정을 복사하기 위해 `AbstractBlock.Settings.copy(AbstractBlock block)을 사용할 수 있습니다. 이 상황에서, 흙의 설정을 복사하기 위해 `Blocks.DIRT\`를 사용할 수도 있지만 예시를 위해 빌더를 사용할 것입니다.
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

자동으로 블록 아이템을 만들기 위해, 이전 단계에서 만든 `register` 메서드의 `shouldRegisterItem` 매개변수를 `true`를 전달하면 됩니다.

### 아이템 그룹에 블록 추가하기 {#adding-your-block-to-an-item-group}

`BlockItem` 이 자동으로 만들어졌고 등록되었기 때문에, 아이템 그룹에 추가시키기 위해, `Block.asItem()` 메서드를 사용해 `BlockItem` 인스턴스를 가져올 것입니다.

예시로, [사용자 지정 아이템 그룹](../items/custom-item-groups) 페이지에서 만든 사용자 지정 그룹을 사용할 것입니다.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

---

이제 크리에이티브 인벤토리에 블록이 있고, 세계에 설치할 수 있음을 알 수 있습니다!

![적합한 모델이나 텍스처가 없는 세계의 블록](/assets/develop/blocks/first_block_0.png).

여기엔 몇가지 문제가 있습니다. 블록의 이름이 없으며, 텍스처도 없고 블록 모델이나 아이템 모델도 없습니다.

## 블록 번역 추가하기 {#adding-block-translations}

번역을 추가하기 위해, 번역 파일 (`assets/<mod id here>/lang/en_us.json`)에 번역 키를 만들어야 합니다.

Minecraft는 이 번역을 크리에이티브 인벤토리나 명령어 피드백과 같은 블록의 이름이 표시되는 다른 곳에 사용할 것입니다.

```json
{
    "block.mod_id.condensed_dirt": "Condensed Dirt"
}
```

모드를 빌드하기 위해 게임을 다시 시작하거나 <kbd>F3</kbd> + <kbd>T</kbd>를 눌러 변경 사항을 적용할 수 있습니다. 그러면 크리에이티브 인벤토리나 통계 화면과 같은 다른 곳에 블록의 이름이 있는 것이 보일 것입니다.

## 모델 및 텍스처 {#models-and-textures}

모든 블록의 텍스처는 `assets/<mod id here>/textures/block` 폴더에서 찾을 수 있습니다. 예시 텍스처인 "거친 흙" 블록은 무료로 사용할 수 있습니다.

<DownloadEntry type="Texture" visualURL="/assets/develop/blocks/first_block_1.png" downloadURL="/assets/develop/blocks/first_block_1_small.png" />

텍스처가 게임 안에서 보이게 하려면, "거친 흙" 블록의 다음 위치에서 찾을 수 있는 블록 및 아이템 모델을 만들어야 합니다:

- `assets/<mod id here>/models/block/condensed_dirt.json`
- `assets/<mod id here>/models/item/condensed_dirt.json`

이 아이템 모델은 꽤 단순합니다. 대부분의 블록 모델이 GUI에서 렌더되는 것을 지원하기 때문에그저 상위 모델로 블록 모델을 사용할 수 있습니다:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/condensed_dirt.json)

하지만, 이 블록 모델은, 이 상황에서, `block/cube_all` 모델의 상위 모델이어야 합니다:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/block/condensed_dirt.json)

게임으로 불러왔을 때, 여전히 텍스처가 없을 수도 있습니다. 왜냐하면 블록 상태 정의를 추가해야 하기 때문입니다.

## 블록 상태 정의 만들기 {#creating-the-block-state-definition}

블록 상태 정의는 현재 상태의 블록을 기반으로 어떤 모델을 렌더할지 게임에게 지시하는 데 쓰입니다.

이 복잡한 상태 정의가 없는 예시 블록의 경우, 오직 한 개의 항목만이 정의에 필요합니다.

파일은 `assets/mod_id/blockstates` 폴더에 위치해야 하며, 이름은 `ModBlocks` 클래스를 등록했을 때 사용한 블록 ID와 일치해야 합니다. 예시로, 만약 블록 ID가 `condensed_dirt`라면, 파일 이름은 `condensed_dirt.json`이 되어야 합니다.

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/blockstates/condensed_dirt.json)

블록 상태는 정말 복잡합니다. 그러므로, [블록 상태](./blockstates) 페이지에서 다루겠습니다.

게임을 다시 시작하거나, <kbd>F3</kbd> + <kbd>T</kbd>을 통해 다시 불러와 변경 사항을 적용하세요. 블록 텍스처가 인벤토리 안과 실제 세계에서 볼 수 있을 것입니다.

![적합한 모델이나 텍스처가 있는 세계의 블록](/assets/develop/blocks/first_block_4.png)

## 블록 떨굼 추가하기 {#adding-block-drops}

서바이벌 모드에서 블록을 부쉈을 때, 블록이 떨어지지 않는 것을 봤을 수도 있습니다. 아마 이 기능을 원했을 수도 있습니다. 하지만 블록을 부숴서 떨어지게 만들기 위해 전리품 테이블블록을 부쉈을 때, 블록이 떨어지지 않는 것을 봤을 수도 있습니다. 아마 이 기능을 원했을 수도 있습니다. 하지만 블록을 부숴서 떨어지게 만들기 위해 노획물 목록을 구현해야 합니다. 노획물 목록은 `data/<mod id here>/loot_table/blocks/` 폴더에 위치해야 합니다.

:::info
노획물 목록에 대해 더 자세히 알고 싶으면, [Minecraft 위키 - 노획물 목록](https://ko.minecraft.wiki/w/%EB%85%B8%ED%9A%8D%EB%AC%BC_%EB%AA%A9%EB%A1%9D) 페이지를 참고할 수 있습니다.
:::

@[code](@/reference/latest/src/main/resources/data/fabric-docs-reference/loot_tables/blocks/condensed_dirt.json)

이 노획물 목록은 블록이 부서질 때나 폭발할 때 단일 블록 아이템의 떨굼을 제공합니다.

## 부술 도구 추천하기 {#recommending-a-harvesting-tool}

아마 특정한 도구만을 이용해 블록을 부술 수 있도록 하고 싶을 수 있습니다. 예시로 삽을 이용해 블록을 더 빠르게 부술 수 있도록 하고 싶게 할 수 있습니다.

모든 도구 태그는 `data/minecraft/tags/block/mineable/` 폴더에 있을 것입니다. 파일 이름은 사용한 도구에 따라 다르며, 다음을 따라 달라집니다:

- `hoe.json` (괭이)
- `axe.json` (도끼)
- `pickaxe.json` (곡괭이)
- `shovel.json` (삽)

이 파일의 내용은 꽤 단순합니다. 태그에 추가되어야 할 아이템의 목록입니다.

이 예시는 "거친 흙" (Condensed Dirt) 블록을 `shovel` (삽) 태그에 추가시킵니다.

@[code](@/reference/latest/src/main/resources/data/minecraft/tags/mineable/shovel.json)

만약 블록을 부수기 위해 특정한 도구를 사용해야 하게 하길 원한다면, 블록 설정에 `.requiresTool()`을 추가하고 적절한 채굴 태그를 추가해야 합니다.

## 채굴 단계 {#mining-levels}

비슷하게, 채굴 단계 태그는 `data/minecraft/tags/block/` 폴더에서 찾을 수 있고, 다음 형식을 따릅니다:

- `needs_stone_tool.json` - 최소 단계가 돌 도구
- `needs_iron_tool.json` - 최소 단계가 철 도구
- `needs_diamond_tool.json` - 최소 단계가 다이아몬드 도구

이 파일은 부술 도구 파일과 같은 형식을 가지고 있습니다. 태그에 추가되어야 할 아이템의 목록입니다.

## 추가 사항 {#extra-notes}

만약 많은 블록을 추가하려 한다면, 아마도 블록 및 아이템 모델을 만드는 과정, 블록 상태 정의, 노획물 목록 등을 자동화하기 위하여 [데이터 생성](https://fabricmc.net/wiki/tutorial:datagen_setup)을 사용하는 것을 고려하여 볼만 합니다.
