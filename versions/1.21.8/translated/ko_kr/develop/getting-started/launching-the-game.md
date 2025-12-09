---
title: 게임 실행하기
description: 실제 게임 환경에서 모드를 디버깅하기 위해 다양한 실행 프로필을 활용하는 방법에 대해 알아보세요.
authors:
  - IMB11
  - Tenneb22
---

Fabric Loom은 실제 게임 환경에서 모드를 디버깅할 수 있게 돕는 다양한 실행 프로필을 제공합니다. 이 설명서는 모드를 디버깅하고 테스트하기 위해 다양한 실행 프로필을 활용하는 방법에 대해 알아봅니다.

## 실행 프로필 {#launch-profiles}

IntelliJ IDEA를 사용하고 있다면, 창의 우측 상단 모서리에서 실행 프로필을 찾을 수 있습니다. 드롭다운 메뉴를 클릭하여 사용 가능한 실행 프로필을 확인할 수 있습니다.

다음과 같이 단순 실행과 디버그 모드로 실행 가능한 클라이언트와 서버 프로필이 있어야 합니다:

![실행 프로필](/assets/develop/getting-started/launch-profiles.png)

## Gradle 작업 {#gradle-tasks}

명령줄을 사용한다면, 다음 Gradle 작업으로 게임을 실행할 수 있습니다:

- `./gradlew runClient` - 클라이언트 모드로 게임을 시작합니다.
- `./gradlew runServer` - 서버 모드로 게임을 시작합니다.

이 방식의 유일한 문제는 코드를 쉽게 디버깅할 수 없다는 것입니다. 코드를 디버깅하려면, IntelliJ IDEA 등 IDE에서 제공하는 Gradle 통합의 실행 프로필을 사용해야 합니다.

## 클래스 핫스왑 {#hotswapping-classes}

게임을 디버그 모드로 실행하면, 게임을 재시작하지 않고도 클래스를 핫스왑(수정)할 수 있습니다. 이는 코드의 변경 사항을 빠르게 테스트할 때 유용합니다.

하지만 몇 가지 제한 사항이 존재합니다:

- 메소드를 추가하거나 제거할 수 없습니다
- 메소드의 매개변수를 수정할 수 없습니다
- 필드를 추가하거나 제거할 수 없습니다

하지만, [JetBrains 런타임](https://github.com/JetBrains/JetBrainsRuntime)을 사용하면 대부분의 제한 사항을 우회할 수 있으며, 심지어 클래스와 메소드를 추가하거나 제거할 수도 있습니다. 이는 게임을 재시작하지 않고도 대부분의 변경 사항을 적용할 수 있게 _해야_ 합니다.

Minecraft 실행 구성에 다음 VM 변수를 추가하는 것을 잊지 마세요:

```:no-line-numbers
-XX:+AllowEnhancedClassRedefinition
```

## Mixin 핫스왑 {#hotswapping-mixins}

Mixin을 사용한다면, 아래 과정을 통해 게임을 재시작하지 않고 Mixin 클래스를 핫스왑할 수 있습니다. 이는 Mixin 변경 사항을 빠르게 테스트하기 유용합니다.

이를 위해선 먼저 Mixin Java 에이전트를 설치해야 합니다.

### 1. Mixin 라이브러리 Jar 위치 지정 {#1-locate-the-mixin-library-jar}

IntelliJ IDEA를 사용중이라면, 다음과 같이 "프로젝트 구조"의 "외부 라이브러리"에서 Mixin 라이브러리 Jar를 찾을 수 있습니다:

![Mixin 라이브러리](/assets/develop/getting-started/mixin-library.png)

다음 과정을 위해 Jar의 "상대 경로"를 복사해야 합니다.

### 2. `-javaagent` VM 변수 추가 {#2-add-the--javaagent-vm-argument}

"Minecraft Client"와 "Minecraft Server" 실행 구성에서, 다음 VM 매개변수를 추가해야 합니다:

```:no-line-numbers
-javaagent:"<여기에 Mixin 라이브러리 Jar 상대 경로 입력>"
```

![VM 매개변수가 강조된 실행 구성](/assets/develop/getting-started/vm-arguments.png)

이제, 디버깅 중에 Mixin 메소드를 수정할 수 있으며, 게임을 재시작하지 않고 변경 사항을 확인할 수 있습니다.
