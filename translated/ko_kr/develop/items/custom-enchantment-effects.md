---
title: 사용자 지정 마법 부여 효과
description: 마법 부여 효과를 추가하는 방법을 알아보세요.
authors:
  - krizh-p
---

버전 1.21부터, Minecraft의 사용자 지정 마법 부여는 "데이터 기반" 접근 방식을 사용합니다. 이는 공격 피해를 높이는 등, 간단한 마법 부여의 추가는 쉽게 만들었지만, 복잡한 것은 추가하기 어렵게 만들었습니다. 이 과정은 마법 부여를 _효과 구성 요소_로 분해하는 것부터 시작합니다.

효과 구성 요소는 마법 부여의 특별한 효과를 정의하는 코드를 포함합니다. Minecraft는 아이템 손상, 밀치기, 경험치와 같이 다양한 기본 효과를 지원합니다.

:::tip
[Enchantment definition: Effect components - Minecraft Wiki](https://minecraft.wiki/w/Enchantment_definition#Effect_components)에서 만들고자 하는 효과가 이미 추가되었는지 확인해보세요. 이 설명서는 "간단한" 데이터 기반 마법 부여를 만들 수 있다는 가정 하에 작성되었으며, 기본적으로 지원되지 않는 사용자 지정 마법 부여 효과를 추가하는 것에 초점을 두었습니다.
:::

## 사용자 지정 마법 부여 효과 {#custom-enchantment-effects}

`enchantment` 패키지를 만드는 것부터 시작합니다. 그런 다음, 그 안에 `effect` 패키지도 추가합니다. 그리고, `LightningEnchantmentEffect` 레코드를 생성합니다.

생성자를 만들고, `EnchantmentEntityEffect` 인터페이스를 구현합니다. 효과를 인코딩, 디코딩할 수 있게 `CODEC` 상수 필드도 추가합니다. 코덱에 대한 자세한 내용은 [여기](../codecs)를 참조하십시오.

앞으로 만들 대부분의 코드는 `apply()` 이벤트에 추가됩니다. 이 메소드는 마법 부여가 작동할 조건을 만족하면 실행됩니다. 지금으로선, 대상에게 벼락이 치는 간단한 코드를 작성해봅시다. 이후 개체가 공격되면 이 `Effect`가 호출되게 구성할 것입니다.

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/enchantment/effect/LightningEnchantmentEffect.java)

여기, `amount` 변수는 마법 부여의 레벨에 따라 증가하는 값입니다. 레벨에 따라 마법 부여의 효과에 변화를 줄 때 사용할 수 있습니다. 위 코드를 보면, 마법 부여의 레벨을 벼락이 생성될 횟수로 사용하고 있습니다.

## 마법 부여 효과 등록 {#registering-the-enchantment-effect}

모드의 다른 구성 요소와 마찬가지로, 마법 부여 효과도 `EnchantmentEffect` 레지스트리에 등록해야 합니다. 이를 위해, `ModEnchantmentEffects` 클래스를(이름은 마음대로 지어도 됩니다) 만들고 마법 부여를 등록하는 도우미 메소드를 추가해줍시다. 모드 진입점에서 도우미 메소드를 실행하는 것을 잊지 마세요.

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantmentEffects.java)

## 마법 부여 추가 {#creating-the-enchantment}

마법 부여 효과가 추가되었습니다! 마지막으로 방금 추가한 마법 부여 효과가 적용된 마법 부여를 추가해봅시다. 데이터 팩처럼 모드 리소스에 JSON 파일을 만들어도 되지만, 이 설명서에서는 Fabric의 데이터 생성 도구로 동적으로 JSON을 생성해 볼 것입니다. 먼저, `EnchantmentGenerator` 클래스를 생성합니다.

클래스 안에서, 먼저 새로운 마법 부여를 등록한 다음, `configure()` 메소드를 사용하여 JSON을 프로그램적으로 생성할 것입니다.

@[code transcludeWith=#entrypoint](@/reference/latest/src/client/java/com/example/docs/datagen/EnchantmentGenerator.java)

계속하기 전에, 먼저 프로젝트에 데이터 생성이 구성되어 있는지 확인해야 합니다. 잘 모르겠다면, [데이터 생성 관련 문서](../data-generation/setup)를 참조해보세요.

마지막으로, 데이터 생성 작업에 `EnchantmentGenerator`를 추가하도록 모드에 알려야 합니다. `EnchantmentGenerator`를 `onInitializeDataGenerator` 메소드에 추가하기만 하면 됩니다.

@[code transclude={24-24}](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

이제, 모드의 데이터 생성 작업을 실행하면, `generated` 폴더에 마법 부여 JSON이 생성될 것입니다. 아마도 다음과 같이 생성되었을 것입니다:

@[code](@/reference/latest/src/main/generated/data/fabric-docs-reference/enchantment/thundering.json)

`en_us.json`에 마법 부여의 번역 키를 추가하는것도 잊지 마세요:

```json
"enchantment.FabricDocsReference.thundering": "Thundering",
```

이제 사용자 지정 마법 부여 효과가 작동할 것입니다! 위에서 추가한 마법을 무기에 적용한 다음 몹을 공격해서 테스트할 수 있습니다. 아마도 다음과 같이 작동할 것입니다:

<VideoPlayer src="/assets/develop/enchantment-effects/thunder.webm">Thundering 마법 부여를 사용하는 모습</VideoPlayer>
