---
title: 로그 업로드
description: 문제를 해결하기 위해 로그를 업로드하는 방법을 알아보세요.
authors:
  - IMB11
---

문제를 해결할 때, 모드 개발자가 원인을 파악하기 위해 로그를 전송해야 하는 경우가 자주 있습니다.

## 왜 로그를 업로드해야 하나요? {#why-should-i-upload-logs}

로그를 업로드하면, 채팅 또는 게시글에 단순히 복사하고 붙여넣는 것보다 훨씬 더 수월하게 문제를 해결할 수 있게 합니다. 다시 복사하고 붙여넣을 필요 없이 간단하게 로그를 공유할 수도 있습니다.

일부 기록 서비스는 로그에 구문 강조를 지원하여 더 쉽게 읽을 수 있게 하고, 사용자 아이디와 시스템 정보와 같은 민감한 정보를 숨기기도 합니다.

## 충돌 보고서 {#crash-reports}

충돌 보고서는 게임이 충돌하면 자동으로 생성되며, 게임의 전체 로그 대신 충돌에 관련된 정보만 포함하고 있습니다. 로그는 게임 디렉토리의 `logs` 폴더에 저장되며, 로그는 게임 디렉토리의 `logs` 폴더에 저장되며, 충돌 보고서는 게임 디렉토리의 `crash-reports` 폴더에 저장됩니다.

충돌 보고서에 대한 자세한 정보는 [충돌 보고서](./crash-reports) 문서를 참고하세요.

## 로그 찾기 {#locating-logs}

본 가이드는 공식 Minecraft 런처(일반적으로 "바닐라 런처" 라고 불림)를 위한 것이며, 제3자  런처는 해당 런처의 문서를 확인해야 합니다.

로그는 게임 디렉토리의 `logs` 폴더에 저장되며, 게임 디렉토리는 운영 체제에 따라 다음과 같은 위치에서 찾을 수 있습니다:

::: code-group

```text:no-line-numbers [Windows]
%appdata%\.minecraft
```

```text:no-line-numbers [macOS]
~/Library/Application Support/minecraft
```

```text:no-line-numbers [Linux]
~/.minecraft
```

:::

가장 최근 로그는 `latest.log` 에 저장되고, 이전 로그는 `연도-월-일_순서.log.gz` 의 이름 패턴으로 압축되어 저장됩니다.

## 로그 업로드 {#uploading-logs-online}

로그는 다음과 같이 여러 기록 서비스로 업로드 할 수 있습니다:

- [Pastebin](https://pastebin.com/)
- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
