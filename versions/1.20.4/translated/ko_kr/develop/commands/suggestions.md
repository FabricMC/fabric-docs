---
title: 명령어 제안
description: 어떻게 명령어 인수를 플레이어에게 제안하는지 알아보세요.
authors:
  - IMB11

search: false
---

# 명령어 제안

Minecraft에는 `/give` 명령어처럼 많은 경우에서 사용되는 강력한 명령어 제안 체계가 잡혀 있습니다. 이 체계는 플레이어에게 명령어 인수 값을 제안하고, 유저가 제안된 값을 선택할 수 있게 해줍니다.

## 제안 공급자

`SuggestionProvider`는 클라이언트에 전송될 제안 리스트를 만드는데 사용됩니다. 이는 `CommandContext`와 `SuggestionBuilder`를 인수로 받고 `Suggestions`를 반환하는 기능형 인터페이스 입니다. `SuggestionProvider`는 `CompletableFuture`를 반환해 인수가 항상 바로 사용 가능한것은 아닙니다.

## 제안 공급자 사용하기

제안 공급자를 사용하려면, 인수 빌더의 `suggests` 메소드를 호출해야 합니다. 이 메드는 `SuggestionProvider`를 인수로 받고 제안 공급자가 덧붙여진 새로운 인수 빌더를 반환합니다.

@[code java transcludeWith=:::9 highlight={4}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## 내장된 제안 공급자

시스템에 내장되어 있는 몇 가지 제안 공급자도 있습니다.

| 제안 공급자                                    | 설명                                     |
| ----------------------------------------- | -------------------------------------- |
| `SuggestionProviders.SUMMONABLE_ENTITIES` | 생성 가능한 모든 엔티티를 제안합니다.  |
| `SuggestionProviders.AVAILABLE_SOUNDS`    | 재생 가능한 모든 소리를 제안합니다.   |
| `LootCommand.SUGGESTION_PROVIDER`         | 가능한 모든 전리품 테이블을 제안합니다. |
| `SuggestionProviders.ALL_BIOMES`          | 가능한 모든 생물 군계를 제안합니다.   |

## 직접 제안 공급자 만들기

내장된 제안 공급자에 필요로 하는 것이 없다면, 직접 자신만의 제안 공급자를 만들 수도 있습니다. 이렇게 하려면, 먼저 `SuggestionProvider` 인터페이스를 구현(Implement)하는 클래스를 만들고, `getSuggestion` 메소드를 덮어써야(Override) 합니다.

예를 들어, 서버의 모든 플레이어 이름을 제안하는 제안 공급자를 만들어 보겠습니다.

@[code java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/PlayerSuggestionProvider.java)

이 제안 공급자를 사용하려면, 그냥 간단하게 인수 빌더의 `.suggests` 메소드에 만든 인스턴스를 전달하기만 하면 됩니다.

물론, 제안 공급자는 (이미 제공된 인수처럼) 명령어 컨텍스트를 읽어 명령어의 상태에 따라 제안을 변경할 수 있기 때문에, 제안 공급자가 더 복잡해질 수 있습니다.

플레이어의 인벤토리의 아이템을 제안하거나, 플레이어 근처에 있는 엔티티를 제안할 수도 있습니다.
