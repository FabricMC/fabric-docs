---
title: 로그 업로드
description: 문제를 해결하기 위해 로그를 업로드하는 방법을 알아보세요.
authors:
  - IMB11

search: false
---

# 로그 업로드

문제를 해결할 때, 원인을 파악하기 위해 개발자에게 로그를 전송해야 하는 경우가 자주 있습니다.

## 로그를 업로드해야 하는 이유

로그를 단순히 복사-붙여넣기 하는것 보다 업로드하면 문제를 해결하는게 보다 수월해집니다. 또한 다른 곳으로 로그를 간단하게 전송할 수 있게 됩니다.

때때로 일부 서비스에서는 로그에 구문 강조를 지원하여, 더 쉽게 읽을 수 있게 하고, 사용자 아이디나 시스템 정보 등 민감한 정보를 걸러주기도 합니다.

## 충돌 보고서

충돌 리포트는 게임이 충돌하면 자동으로 생성되며, 게임의 전체 로그 대신 충돌 관련 정보만 포함하고 있습니다. 충돌 리포트는 게임 디렉토리의 `crash-reports` 폴더에 저장됩니다.

충돌 리포트에 대한 자세한 정보는 [충돌 리포트](./crash-reports) 페이지를 참고하세요.

## 로그 찾기

본 가이드는 공식 Minecraft 런처 (일반적으로 "바닐라 런처" 라고 불림) 를 위한 것이며, 서드파티 런처는 해당 런처의 문서를 확인해야 합니다.

로그는 게임 디렉토리의 `logs` 폴더에 저장됩니다. 각 운영체제에서 로그 폴더의 위치는 다음과 같습니다.

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft
```

```:no-line-numbers [Linux]
~/.minecraft
```

:::

최신 로그는 `latest.log` 에 저장되며, 이전 로그는 `연도-월-일_순서.log.gz` 의 이름 패턴으로 저장됩니다.

## 로그 업로드

로그는 다음처럼 여러 서비스로 업로드 될 수 있습니다.

- [Pastebin](https://pastebin.com/)
- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
