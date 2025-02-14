---
title: 충돌 보고서
description: 충돌 보고서가 어떤 역할을 가지고, 어떻게 읽는지 알아보세요.
authors:
  - IMB11
---

:::tip
충돌의 원인을 찾는 데 어려움이 있으시다면, [Fabric Discord (영어)](https://discord.gg/v6v4pMv) 의 `#player-support` 또는 `#server-admin-support` 채널에 도움을 요청할 수 있습니다.
:::

충돌 보고서는 게임 또는 서버의 문제를 해결하기 위해 굉장히 중요한 부분 중 하나입니다. 이러한 충돌 보고서는 충돌에 관련된 많은 정보를 포함하고 있으며, 충돌의 원인을 찾는 데 도움이 됩니다.

## 충돌 보고서 찾기 {#finding-crash-reports}

충돌 보고서는 게임 디렉토리의 `crash-reports` 폴더에 저장됩니다. 서버의 경우, 서버 디렉토리의 `crash-reports` 폴더에 저장됩니다.

서드 파티 런처의 경우, 충돌 보고서를 찾으려면 해당 런처의 문서를 참고해야 할 수 있습니다.

충돌 보고서는 다음과 같은 위치에서 찾을 수 있습니다.

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\crash-reports
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/crash-reports
```

```:no-line-numbers [Linux]
~/.minecraft/crash-reports
```

:::

## 충돌 보고서 읽기 {#reading-crash-reports}

충돌 보고서는 매우 길고, 읽기 매우 어려울 수 있습니다. 하지만, 충돌에 관련된 많은 정보를 포함하고 있고, 충돌의 원인을 찾는데 유용합니다.

이 가이드에선, [이 충돌 보고서를 예시로](/assets/players/crash-report-example.txt) 보고서를 읽어볼 것입니다.

:::details 충돌 보고서

<<< @/public/assets/players/crash-report-example.txt{log}

:::

### 충돌 보고서의 목차 {#crash-report-sections}

충돌 보고서는 여러 목차로 이루어져 있으며, 각 목차는 다음 제목을 가집니다.

- `---- Minecraft Crash Report ----`, 보고서 요약본. 이는 충돌을 일으킨 주요 오류, 발생한 시간, 그리고 관련 스택트레이스로 이루어집니다. 대부분의 경우에서 스택트레이스에 충돌을 일으킨 모드의 리퍼런스가 포함되므로 보고서의 가장 중요한 부분이라고 할 수 있습니다.
- `-- Last Reload --`, 충돌이 리소스 다시 로드 (<kbd>F3</kbd>+<kbd>T</kbd>) 도중 발생하지 않았다면 대부분 불필요합니다. 다시 로드가 진행된 시간, 다시 로드 중 발생한 오류의 관련 스택 트레이스 등을 포함합니다. 이러한 오류는 대부분 리소스 팩에서 발생하며, 게임에 문제를 일으키지 않는 한 신경 쓸 필요는 없습니다.
- 운영체제, Java 버전, 게임에 할당된 메모리의 양 등이 기록됩니다. 게임에 적당한 양의 메모리가 할당되었는지, 올바른 Java 버전을 사용했는지 등을 확인할 때 유용합니다.
  - Fabric의 경우, 여기에 설치된 모드가 기록되는 `Fabric Mods:` 가 추가됩니다. 모드 간 충돌을 파악할 때 유용합니다.

### 충돌 보고서로 할 수 있는 것 {#breaking-down-the-crash-report}

이제 충돌 보고서의 목차를 알았으니, 충돌 보고서를 분해해서 충돌의 원인이 무엇인지 파악할 수 있게 되었습니다.

아래에 링크된 예시를 통해, 충돌 보고서를 분석하고 충돌을 일으킨 원인과 모드를 찾아봅시다.

이 예시에서는 `---- Minecraft Crash Report ----` 안 목차의 스택트레이스에서 충돌을 일으킨 주요 오류가 기록되어 있어 가장 중요한 부분이라고 할 수 있습니다.

:::details 오류 표시

<<< @/public/assets/players/crash-report-example.txt{7 log}

:::

스택트레이스에 언급된 모드의 개수에 따라, 정확히 지목하기 어려울 수 있지만, 가장 먼저 해야 할 일은 충돌을 일으킨 모드를 찾는 것입니다.

이 예시에서는, `snownee`가 스택트레이스에서 처음으로 언급되었기 때문에 충돌을 일으킨 모드입니다.

하지만, 여러 모드가 언급되었다면, 모드 간의 호환성 문제일 수도 있으며, 충돌을 일으킨 모드에는 문제가 없을 가능성도 있습니다. 이러한 경우에는, 모드 개발자에게 충돌을 신고하고, 충돌을 조사할 수 있도록 하는 것이 최선책입니다.

## Mixin 충돌 {#mixin-crashes}

:::info
Mixin은 모드가 게임의 소스 코드를 수정하지 않고도 게임을 수정할 수 있도록 하는 방법 중 하나입니다. 이는 많은 모드에서 사용되고, 개발자에게 매우 강력한 도구입니다.
:::

Mixin이 충돌하면, 스택트레이스에는 충돌한 Mixin과 그 Mixin이 수정하려고 한 클래스가 기록됩니다.

메소드 Mixin은 스택트레이스에 `modid$handlerName`로 기록되는데, `modid`는 모드의 ID, `handlerName`은 Mixin 처리기의 이름입니다.

```:no-line-numbers
... net.minecraft.class_2248.method_3821$$$modid$handlerName() ... // [!code focus]
```

이 정보를 통해 충돌을 일으킨 모드를 찾고, 모드 개발자에게 충돌을 신고할 수 있게 됩니다.

## 충돌 보고서로 뭘 하나요 {#what-to-do-with-crash-reports}

충돌 보고서로 할 수 있는 최고의 행동은 보고서를 Paste에 업로드하고, 이슈 트래커 또는 커뮤니티 (Discord 등)에 게시하여 모드 개발자에게 공유하는 것입니다.

이는 모드 개발자가 충돌을 재현하는 등 분석하고, 충돌을 해결할 수 있게 합니다.

일반적으로 충돌 보고서에 사용되는 기록 사이트는 다음과 같습니다.

- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
- [Pastebin](https://pastebin.com/)
