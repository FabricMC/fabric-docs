---
title: 모드 설치
description: Fabric용 모드를 설치하는 방법에 대한 단계적 가이드입니다.
authors:
  - IMB11
---

이 가이드에서는 공식 Minecraft 런처의 Fabric에 모드를 설치하는 방법을 알려줄 것입니다.

제3자 런처의 경우, 해당 런처의 문서를 참조하여 주세요.

## 1. 모드 다운로드 {#1-download-the-mod}

:::warning
항상 신뢰할 수 있는 출처에서 모드를 다운로드해야 합니다. [안전한 모드 찾기](./finding-mods)에서 더 많은 정보를 알아볼 수 있습니다.
:::

대부분의 모드는 Fabric API를 필요로 하며, [Modrinth](https://modrinth.com/mod/fabric-api)와 [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api)에서 공식 모드를 다운로드할 수 있습니다.

모드를 다운로드할 때, 아래 사항을 확인하여 주세요:

- 플레이하고자 하는 Minecraft 버전이 지원되는지 확인해야 합니다. 예를 들어, 버전 1.20을 지원하는 모드는 1.20.2에서 작동하지 않을 수 있습니다.
- 모드 로더가 Fabric인지 확인해야 합니다.
- 더욱이, 해당 모드가 Minecraft: "Java Edition"용인지 확인해야 합니다.

## 2. 다운로드한 모드를 `mods` 폴더로 옮기기 {#2-move-the-mod-to-the-mods-folder}

`mods` 폴더는 운영 체제에 따라 다음과 같은 위치에서 찾을 수 있습니다:

(파일 탐색기/Finder의 주소 표시줄에 아래 주소를 복사하여 붙여넣으면 빠르게 폴더로 이동할 수 있습니다)

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\mods
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/mods
```

```:no-line-numbers [Linux]
~/.minecraft/mods
```

:::

`mods` 폴더를 찾으셨다면, 다운로드한 모드 `.jar` 파일을 폴더에 이동하면 됩니다.

![Mods 폴더에 모드가 설치된 모습](/assets/players/installing-mods.png)

## 3. 이제 끝났습니다! {#3-you-re-done}

`mods` 폴더에 모드를 이동했다면, Minecraft 런처를 실행하고 왼쪽 아래의 버전 선택에서 Fabric을 선택하고 플레이를 클릭하면 됩니다.

![Fabric 프로필이 선택된 Minecraft 런처](/assets/players/installing-fabric/launcher-screen.png)

## 문제 해결 {#troubleshooting}

설치 과정 중 문제가 발생했다면, [Fabric Discord (영어)](https://discord.gg/v6v4pMv)의 `#player-support` 채널에서 도움을 요청할 수 있습니다.

스스로 문제 해결 페이지를 읽고 직접 문제를 해결할 수도 있습니다:

- [충돌 보고서](./troubleshooting/crash-reports)
- [로그 업로드](./troubleshooting/uploading-logs)
