---
title: 피해 유형
description: 사용자 정의 피해 유형을 추가하는 방법을 알아보세요.
authors:
  - dicedpixels
  - hiisuuii
  - mattidragon

search: false
---

# 피해 유형

피해 유형은 엔티티가 입을 수 있는 피해(대미지)의 종류를 의미합니다. Minecraft 1.19.4 부터, 새로운 피해 유형의 추가는 데이터 기반이 되어, JSON 파일을 통해 생성됩니다.

## 피해 유형 추가하기

_Tater_ 라는 이름의 사용자 정의 피해 유형을 추가해 봅시다. 이는 피해 유형의 JSON 파일을 생성하며 시작됩니다. 이 파일은 모드 리소스의 `data` 디렉토리의 `damage_type` 폴더에 저장됩니다.

```:no-line-numbers
resources/data/fabric-docs-reference/damage_type/tater.json
```

파일은 다음과 같은 구조를 가지게 됩니다.

@[code lang=json](@/reference/latest/src/main/generated/data/fabric-docs-reference/damage_type/tater.json)

이 사용자 정의 피해 유형을 플레이어가 살아있는 엔티티가 아닌 것에서 피해를 입었을 때 [허기 피로](https://minecraft.wiki/w/Hunger#Exhaustion_level_increase)를 0.1만큼 올리도록 만들어 봅시다. 참고로, 피해 크기는 세계의 난이도에 비례합니다.

::: info

JSON 파일 구조에 대한 자세한 내용은 [Minecraft 위키 (영문)](https://minecraft.wiki/w/Damage_type#JSON_format) 를 참고하십시오.

:::

### 코드를 통해 피해 유형에 접근하기

코드를 통해 추가한 사용자 정의 피해 유형에 접근하고 싶다면, `DamageSource` 인스턴스를 생성하기 위해 `RegistryKey`에 접근해야 합니다.

`RegistryKey` 는 다음 코드로 불러올 수 있습니다.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/FabricDocsReferenceDamageTypes.java)

### 피해 유형 사용하기

피해 유형 사용의 예시를 만들어 보기 위해, 먼저 사용자 정의 블록 _Tater Block_을 추가해보겠습니다. _Tater Block_은 살아있는 엔티티가 밟으면 _Tater_ 피해를 입힙니다.

피해를 주기 위해 먼저 `onSteppedOn` 메소드를 덮어(Override) 쓰겠습니다.

사용자 정의 피해 유형의 `DamageSource`를 생성하며 시작합니다.

@[code lang=java transclude={21-24}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

그리고, `entity.damage()` 메소드에 `DamageSource`와 피해 크기를 입력하여 호출합니다.

@[code lang=java transclude={25-25}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

블록의 코드 전문은 다음과 같습니다.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

이제 살아있는 엔티티가 블록 위에 서면, 사용자 정의 피해 유형으로 크기 5 (2.5 하트) 의 피해를 입게 됩니다.

### 사용자 정의 사망 메세지

`en_us.json` 파일의 `death.attack.<message_id>` 키를 수정해 사용자 정의 피해 유형의 사망 메세지를 수정할 수 있습니다.

@[code lang=json transclude={4-4}](@/reference/latest/src/main/resources/assets/fabric-docs-reference/lang/en_us.json)

이제 사용자 정의 피해 유형으로 사망하면, 다음 사망 메세지를 보게 될 것입니다.

![플레이어 인벤토리에서 보여지는 효과](/assets/develop/tater-damage-death.png)

### 피해 유형 태그

일부 피해 유형은 갑옷, 상태 효과 등을 무시할 수 있습니다. 태그는 이러한 피해 유형의 속성을 제어하는데 사용됩니다.

피해 유형 태그는 `data/minecraft/tags/damage_type` 에 저장됩니다.

::: info

내장된 피해 유형 태그와 설명은 [Minecraft 위키 (영문)](https://minecraft.wiki/w/Tag#Damage_types) 를 참고하십시오.

:::

Tater 피해 유형을 `bypasses_armor` 피해 유형 태그에 추가해 봅시다.

이러한 태그에 사용자 정의 피해 유형을 추가하려면, 먼저 `minecraft` 네임스페이스로 JSON 파일을 생성해야 합니다.

```:no-line-numbers
data/minecraft/tags/damage_type/bypasses_armor.json
```

파일은 다음과 같은 구조를 가집니다.

@[code lang=json](@/reference/latest/src/main/generated/data/minecraft/tags/damage_type/bypasses_armor.json)

`replace` 키를 `false` 로 설정하여 기존 태그의 값을 제거하지 않도록 주의하세요.
