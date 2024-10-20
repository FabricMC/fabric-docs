---
title: 명령어 만들기
description: 복잡한 인수와 동작을 가진 명령어를 만들어 보세요.
authors:
  - dicedpixels
  - i509VCB
  - pyrofab
  - natanfudge
  - Juuxel
  - solidblock
  - modmuss50
  - technici4n
  - atakku
  - haykam
  - mschae23
  - treeways
  - xpple
---

# 명령어 만들기 {#creating-commands}

"명령어 만들기"에서는 모드 개발자가 명령어를 통한 기능을 추가하는 방법에 대해 설명합니다. 이 튜토리얼에서는 Brigadier의 일반적인 명령어 구조는 무엇이며, 어떻게 명령어를 등록하는지 알아볼 것입니다.

::: info
Brigadier is a command parser and dispatcher written by Mojang for Minecraft. It is a tree-based command library where
you build a tree of commands and arguments.

Brigadier는 오픈 소스로, 원본 소스 코드는 여기에서 확인할 수 있습니다: <https://github.com/Mojang/brigadier>

## `Command` 인터페이스 {#the-command-interface}

`com.mojang.brigadier.Command`는 특정 코드를 실행하고 `CommandSyntaxException`을 던지는 기능형 인터페이스 이며, _명령어의 소스_ 의 타입을 결정하는 제네릭 타입 `S`를 가집니다. 인수처럼, 하위 명령어 노드도 필수적이진 않습니다.
명령어 소스는 명령어를 실행한 대상자를 의미합니다. Minecraft에서, 명령어 소스는 서버를 의미하는 `ServerCommandSource`, 명령 블록, 원격 연결 (RCON), 그리고 플레이어와 엔티티가 있습니다.

`Command`의 `run(CommandContext<S>)` 메소드는 `CommandContext<S>`를 인수로 받아 정수를 반환합니다. 명령어 컨텍스트에선 명령어 소스 `S`와, 인수, 분석된 명령어 노드 또는 명령어의 입력을 받아올 수 있습니다.

다른 기능형 인터페이스처럼, 이 인터페이스에는 대부분 람다식 또는 메소드 참조가 사용됩니다.

```java
Command<ServerCommandSource> command = context -> {
    return 0;
};
```

치트를 켜지 않으면 대부분의 명령어를 탭 자동 완성에서 볼 수 없는 이유이기도 합니다. 일반적으로 음수 값은 명령어를 실행하는데 실패했고, 아무것도 실행되지 않았음을 의미합니다. `0`은 명령어가 성공적으로 처리되었음을 의미하고, 양수 값은 명령어가 성공적으로 작동했으며 어떠한 작업이 실행되었음을 의미합니다. Brigadier는 성공을 나타내는 상수 `Command#SINGLE_SUCCESS` 를 제공하고 있습니다.

### `ServerCommandSource`의 역할 {#what-can-the-servercommandsource-do}

예를 들어, 명령어가 전용 서버 환경에서만 등록되도록 해보겠습니다. `ServerCommandSource`는 명령어를 실행한 엔티티, 명령어가 실행된 세계 또는 서버 등 명령어가 실행될 때 추가적인 컨텍스트를 제공합니다.

`CommandContext` 인스턴스에서 `getSource()` 메소드를 호출해 명령어 컨텍스트에서 명령어 소스에 접근할 수도 있습니다.

```java
Command<ServerCommandSource> command = context -> {
    ServerCommandSource source = context.getSource(); 
    return 0;
};
```

## 기본 명령어의 등록

명령어는 Fabric API에서 제공하는 `CommandRegistrationCallback` 을 통해 등록됩니다.

:::info
콜백을 등록하는 방법은 [이벤트](../events) 가이드를 참고하세요.
:::

이벤트는 모드 초기화 단계에 등록되어야 합니다.

콜백은 다음 세 가지 매개변수를 가집니다.

- `CommandDispatcher<ServerCommandSource> dispatcher` - 명령어를 등록, 분석하고 실행하는 데 사용됩니다. `S`는 명령어 디스패처가 지원하는 명령어 소스의 타입입니다.
- `CommandRegistryAccess registryAccess` - 특정한 명령어 인수 메소드에 입력될 수 있는 레지스트리의 추상화를 제공합니다.
- `CommandManager.RegistrationEnvironment environment` - 명령어가 등록되는 서버의 유형을 식별합니다.

이제 모드 초기화에서 간단한 명령어를 한번 등록해봅시다.

@[code lang=java transcludeWith=:::test_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

`sendFeedback()` 메소드의 첫 번째 인수는 보내질 텍스트이며, 텍스트 오브젝트의 필요 없는 인스턴스화를 막기 위해 `Supplier<Text>` 로 제공됩니다.

두 번째 인수는 다른 관리자에게 피드백을 전송할지 결정합니다. 일반적으로, 세계 시간이나 플레이어의 점수를 출력하는 등 세계에 영향을 주지 않는 명령어라면 `false` 로 설정됩니다. 반대로 시간이나 플레이어의 점수를 변경하는 등 명령어가 세계에 영향을 준다면 `true` 가 되게 됩니다.

만약 명령어를 처리하는 데 실패한다면, `sendFeedback()`을 호출하는 대신에 바로 예외를 던질 수 있습니다.

`CommandSyntaxException`은 일반적으로 명령어나 인수의 구문 오류를 나타낼 때 던져집니다. 원한다면 자신만의 예외도 구현할 수 있습니다.

방금 등록한 명령어를 실행하려면, 대소문자를 구분하여 `/test_command`를 입력하면 됩니다.

:::info
이 시점부터, `.execute()` 빌더에 전달된 람다식 이내에서 작성된 로직을 개별 메소드로 추출하겠습니다. 그런 다음 `.execute()`에 대한 메소드 참조를 전달할 수 있습니다. 이는 명확함을 위해 수행되었습니다.
:::

### 등록 환경 {#registration-environment}

원하는 경우 명령어가 특정한 상황에만 등록되도록 할 수도 있습니다. 예를 들어, 명령어가 전용 서버 환경에서만 등록되도록 해보겠습니다.

@[code lang=java highlight={2} transcludeWith=:::dedicated_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_dedicated_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### 명령어의 요구 사항 {#command-requirements}

관리자만 실행 가능한 명령어를 만들고 싶다고 가정해봅시다. `require()` 메소드를 사용하기 딱 좋은 상황이군요. `require()` 메소드는 `ServerCommandSource`를 제공하고 `CommandSource`가 명령어를 실행할 수 있는지 판단하는 `Predicate<S>`을 인수로 가집니다.

@[code lang=java highlight={3} transcludeWith=:::required_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_required_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

이렇게 하면 명령 블록을 포함해 명령어 소스가 적어도 레벨 2 관리자는 되어야 명령어를 실행할 수 있게 됩니다. 요구 사항을 충족하지 못한다면 명령어는 등록조차 되지 않게 됩니다.

하지만, 명령어가 등록이 되지 않아 레벨 2 관리자가 아닌 플레이어에게는 탭 자동 완성에서 표시되지 않는다는 단점이 있습니다. 치트를 켜지 않으면 대부분의 명령어를 탭 자동 완성에서 볼 수 없는 이유이기도 합니다.

### 하위 명령어

하위 명령어를 추가하려면, 먼저 상위 명령어의 리터럴 노드를 등록해야 합니다. 그런 다음, 상위 명령어의 리터럴 노드 다음에 하위 명령어의 리터럴 노드를 덧붙이면 됩니다.

@[code lang=java highlight={3} transcludeWith=:::sub_command_one](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_sub_command_one](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

인수처럼, 하위 명령어 노드도 필수적이진 않습니다. 아래와 같은 상황에선, `/command_two`와 `/command_two sub_command_two`  모두 올바른 명령어가 되게 됩니다.

@[code lang=java highlight={2,8} transcludeWith=:::sub_command_two](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_command_sub_command_two](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## 클라이언트 명령어 {#client-commands}

Fabric API는 `net.fabricmc.fabric.api.client.command.v2` 패키지에 클라이언트측 명령어를 등록할 때 사용되는 `ClientCommandManager` 클래스를 가지고 있습니다. 이러한 코드는 오로지 클라이언트측 코드에만 있어야 합니다.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

## 명령어 리다이렉션 {#command-redirects}

"별칭 (Aliases)"로도 알려진 명령어 리다이렉션은 명령어의 기능을 다른 명령어로 리다이렉트(전송)하는 방법입니다. 명령어의 이름을 변경하고 싶지만, 기존 이름도 지원하고 싶을 때 유용하게 사용될 수 있습니다.

:::warning
Brigadier는 [인수를 사용해 명령 노드만 리다이렉트 시킬 것입니다](https://github.com/Mojang/brigadier/issues/46). 만약 인수 없이 명령 노드를 리다이렉션하려면 예제에 설명된 것과 동일한 로직에 대한 참조와 함께 '.executes()\` 빌더를 제공하세요.
:::

@[code lang=java transcludeWith=:::redirect_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_redirected_by](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## 자주 묻는 질문 {#faq}

### 왜 내 코드가 컴파일되지 않나요? {#why-does-my-code-not-compile}

- `CommandSyntaxException` 예외를 던지거나 처리해 보세요. `CommandSyntaxException`은 `RuntimeException`이 아닙니다. 만약 던진다면, 메소드 시그니처에서 `CommandSyntaxException`을 발생시키는 메소드에 있어야 합니다. 그렇지 않으면, 처리되어야 합니다.
  Brigadier가 확인된 예외를 처리하고 게임에서 적절한 오류 메세지를 전송할 것입니다.

- 제네릭 타입이 올바른지 확인해 보세요. 가끔 제네릭 타입에 문제가 있을 수도 있습니다. (대부분의 경우처럼) 명령어를 서버에 등록하려 한다면, `LiteralArgumentBuilder.literal` 대신에 `CommandManager.literal`, 또는 `RequiredArgumentBuilder.argument` 대신에 `CommandManager.argument`를 사용중인지 확인하세요.

- `sendFeedback()` 메소드를 확인해 보세요. 두 번째 인수에 불리언을 추가하는 것을 잊었을지도 모릅니다. 그리고 Minecraft 1.20부터 첫 번째 인수가 `Text`가 아니라 `Supplier<Text>` 임을 기억하세요.

- 명령어는 무조건 정수를 반환해야 합니다. 명령어를 등록할 때, `execute()` 메소드는 `Command` 객체를 (대부분의 경우 람다식으로) 받게 됩니다. 람다식은 무조건 정수를 반환해야 합니다.

### 런타임에서 명령어를 등록할 수 있나요? <br>

::: warning
You can do this, but it is not recommended. You would get the `CommandManager` from the server and add anything commands
you wish to its `CommandDispatcher`.

그 다음에는, `CommandManager.sendCommandTree(ServerPlayerEntity)`를 통해 모든 플레이어에게 다시 명령어 트리를 전송해야 합니다.

클라이언트는 로컬로 완료 오류를 보여주기 위해 로그인 단계 중에 (또는 관리자 패킷이 전송되었을 때) 서버로부터 명령어 트리를 받아 캐시하기 때문에 필수적인 작업입니다.
:::

### 런타임에서 명령어를 등록 해제할 수 있나요? {#can-i-unregister-commands-at-runtime}

::: warning
You can also do this, however, it is much less stable than registering commands at runtime and could cause unwanted side
effects.

간단하게 하려면, Brigadier를 리플렉션해서 노드를 제거해야 합니다. 그 다음에는, `CommandManager.sendCommandTree(ServerPlayerEntity)`를 통해 모든 플레이어에게 다시 명령어 트리를 전송해야 합니다.

업데이트된 명령어 트리를 전송하지 않으면, 서버가 명령어 처리에 실패해도 클라이언트는 아직 명령어가 존재한다고 표시할 것입니다.
:::
