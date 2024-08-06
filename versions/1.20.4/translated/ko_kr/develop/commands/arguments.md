---
title: 명령어 인수
description: 복잡한 인수를 가진 명령어를 만드는 방법을 알아보세요.

search: false
---

# 명령어 인수

인수는 대부분의 명령어에서 사용됩니다. 다시 말해, 어떤 인수는 값을 입력하지 않더라도 명령어가 정상 작동한다는 의미입니다. 하나의 노드는 여러 개의 타입을 가질 수 있지만, 타입이 모호해지면 오류의 원인이 될 수 있으므로 그런 경우는 최대한 피해야 합니다.

@[code lang=java highlight={3} transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

이런 경우에는, `/argtater` 명령어 다음에 정수를 입력해야 합니다. 예를 들어, 만약 `/argtater 3` 를 실행하면, `Called /argtater with value = 3` 라고 피드백 메세지를 받을 것입니다. 반대로 `/argtater` 를 아무런 인수 없이 실행하면, 명령어가 올바르게 작동하지 않을 것입니다.

이제 선택적인 두 번째 인수를 추가해보겠습니다.

@[code lang=java highlight={3,13} transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

이렇게 하면 한 개 또는 두 개의 정수형을 입력할 수 있게 됩니다. 만약 한 개의 정수만 입력하면, 피드백 메세지에선 한 가지 값만 출력될 것입니다. 반대로 두 개의 정수을 모두 입력하면, 피드백 메세지에선 두 개의 값을 모두 출력할 것입니다.

비슷한 처리를 두 번이나 정의할 필요는 없습니다. 대신, 비슷한 처리를 하는 두 가지 인수는 이렇게 만들 수 있습니다.

@[code lang=java highlight={3,5,6,7} transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## 사용자 정의 인수 타입

만약 바닐라에 사용하려는 인수 타입이 없다면, 직접 인수 타입을 만들 수도 있습니다. 만드는 방법을 알아보자면, 먼저 `ArgumentType<T>` 인터페이스를 상속하는 클래스를 만들어야 합니다.

이제 입력된 문자열을 원하는 타입으로 변환하기 위해 `parse` 메소드를 구현해야 합니다.

예를 들어, `{x, y, z}` 형태로 입력된 문자열을 `BlockPos`로 변환해보겠습니다.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/BlockPosArgumentType.java)

### 사용자 정의 인수 타입의 등록

:::warning
명령어를 올바르게 작동하게 하려면 서버와 클라이언트 모두에 사용자 정의 인수 타입을 등록해야 합니다!
:::

모드 초기화 단계의 `onInitialize` 메소드 에서 `ArgumentTypeRegistry` 클래스를 통해 사용자 정의 인수 타입을 등록할 수 있습니다.

@[code lang=java transcludeWith=:::11](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### 사용자 정의 인수 타입의 사용

명령어 빌더의 `.argument` 메소드에 인스턴스를 입력하여 명령어에 사용자 정의 인수 타입을 사용할 수 있습니다.

@[code lang=java transcludeWith=:::10 highlight={3}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

명령어를 실행하여 인수 형태가 작동하는지 여부를 확인할 수 있습니다.

![올바르지 않은 인수](/assets/develop/commands/custom-arguments_fail.png)

![올바른 인수](/assets/develop/commands/custom-arguments_valid.png)

![명령어 결과](/assets/develop/commands/custom-arguments_result.png)
