---
title: 사용자 지정 아이템 그룹
description: 아이템 그룹을 만들고 아이템을 추가하는 방법을 알아보세요.
authors:
  - IMB11
---

아이템 그룹은 크리에이티브 보관함에서 아이템이 표시되는 탭을 의미합니다. 아이템 그룹을 추가하면 아이템을 별도의 탭에 표시할 수 있습니다. 모드에 수 많은 아이템이 추가되어 있고, 플레이어가 쉽게 접근할 수 있도록 정렬하고 싶다면 유용하게 사용할 수 있습니다.

## 아아이템 그룹 추가하기 {#creating-the-item-group}

아이템 그룹을 추가하는건 놀랍도록 간단합니다. 아이템 클래스에 아이템 그룹과 레지스트리 키가 저장될 정적 상수 필드를 만들고, 바닐라 아이템 그룹에 아이템을 추가했던 것처럼 아이템 그룹 이벤트를 사용하기만 하면 됩니다:

@[code transcludeWith=:::9](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::_12](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

이제 크리에이티브 보관함에서 새로운 아이템 그룹을 볼 수 있을 것입니다. 하지만, 아직 번역되지 않았기 떄문에, 첫 아이템을 번역했던 것처럼 언어 파일에 키를 추가해야 합니다.

![크리에이티브 보관함의 아이템 그룹이 번역되지 않은 모습](/assets/develop/items/itemgroups_0.png)

## 번역 키 추가하기 {#adding-a-translation-key}

아이템 그룹 빌더에서 `displayName` 메소드에 `Text.translatable`을 사용했다면, 언어 파일에 번역을 추가해야 합니다.

```json
{
  "itemGroup.example_mod": "Fabric Docs Reference"
}
```

이제, 보시다시피, 아이템 그룹이 정상적인 이름을 가진 것을 볼 수 있을 것입니다:

![아이템과 번역이 완전히 적용된 아이템 그룹](/assets/develop/items/itemgroups_1.png)
