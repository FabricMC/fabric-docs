---
title: 프로젝트 생성하기
description: Fabric 템플릿 모드 생성기로 새 모드를 생성하는 방법에 대한 단계별 설명서입니다.
authors:
  - IMB11
  - Cactooz
---

Fabric은 Fabric 템플릿 모드 생성기를 사용하여 간단하게 새로운 모드를 생성하는 방법을 제공하고 있습니다. 원하는 경우, 예제 모드 리포지토리를 사용하여 직접 새 프로젝트를 생성할 할 수 있습니다. 자세한 내용은 [직접 프로젝트 생성](#manual-project-creation)을 참조하십시오.

## 프로젝트 생성하기 {#generating-a-project}

[Fabric 템플릿 모드 생성기](https://fabricmc.net/develop/template/)를 사용하여 새 프로젝트를 생성할 수 있습니다. 모드 이름, 패키지 이름, 그리고 Minecraft 버전 등의 필수 항목을 입력해야 합니다.

패키지의 이름은 소문자여야 하며, 점으로 분류되고, 다른 개발자의 패키지와의 충돌을 피하기 위해 고유해야 합니다. 일반적으로 `com.example.modid`와 같은 형식의 인터넷 도메인의 역순으로 지정됩니다.

![생성기 미리보기](/assets/develop/getting-started/template-generator.png)

Kotlin을 사용하거나, Mojang의 공식 매핑을 사용하거나, 데이터 생성기를 사용하고 싶은 경우 `고급 옵션`에서 적절한 옵션을 선택할 수 있습니다.

![고급 옵션 섹션](/assets/develop/getting-started/template-generator-advanced.png)

필수 입력란을 모두 입력했다면, `생성` 버튼을 클릭하여 zip 파일 형식으로 새 프로젝트를 다운로드할 수 있습니다.

다운로드한 zip 파일을 원하는 위치에 압축 해제한 후, 다음과 같이 IntelliJ IDEA 등의 IDE에서 압축 해제한 폴더를 열어줍니다:

![프로젝트 열기 프롬프트](/assets/develop/getting-started/open-project.png)

## 프로젝트 가져오기 {#importing-the-project}

IntelliJ IDEA에서 프로젝트를 열면, IDE가 자동으로 Gradle의 구성 요소를 불러온 다음 필요한 작업을 수행할 것입니다.

다음과 같이 좌측 아래에 Gradle 빌드 스크립트에 대한 알림이 표시되면, `Import Gradle Project (Gradle 프로젝트 가져오기)`를 선택해야 합니다:

![Gradle 프롬프트](/assets/develop/getting-started/gradle-prompt.png)

프로젝트가 모두 가져와졌다면, 좌측의 프로젝트 탐색기에서 프로젝트 파일을 확인할 수 있을 것입니다. 이렇게 되면 모드 개발을 시작할 수 있습니다.

## 직접 프로젝트 생성 {#manual-project-creation}

:::warning
리포지토리를 복사하려면 [Git](https://git-scm.com/)이 설치되어 있어야 합니다.
:::

Fabric 템플릿 모드 생성기를 사용할 수 없는 경우, 아래 과정으로 직접 프로젝트를 생성할 수 있습니다.

먼저 다음 명령어를 실행하여 Git으로 리포지토리를 복제합니다:

```sh
git clone https://github.com/FabricMC/fabric-example-mod.git my-mod-project
```

이렇게 하면 `my-mod-project`라는 새 폴더에 리포지토리가 복제됩니다.

복제한 리포지토리에서 `.git` 폴더를 삭제해야 합니다. 그런 다음, IntelliJ IDEA 등의 IDE로 프로젝트를 엽니다. 만약 파일 탐색기에서 `.git` 폴더가 보이지 않는다면, 파일 탐색기 설정에서 숨긴 항목 표시를 활성화해야 할 수 있습니다.

IntelliJ IDEA에서 프로젝트를 열면, IDE가 자동으로 Gradle의 구성 요소를 불러온 다음 필요한 작업을 수행할 것입니다.

위에서 언급했듯이, 좌측 아래에 Gradle 빌드 스크립트에 대한 알림이 표시되면, `Import Gradle Project (Gradle 프로젝트 가져오기)`를 선택해야 합니다.

### 템플릿 수정하기 {#modifying-the-template}

프로젝트가 모두 가져와졌다면, 다음 단계를 통해 프로젝트의 세부 정보를 만들고자 하는 모드와 맞게 수정해야 합니다.

- 프로젝트의 `gradle.properties` 파일의 `maven_group` 및 `archive_base_name` 속성을 모드와 일치하도록 수정합니다.
- `fabric.mod.json` 파일의 `id`, `name` 및 `description` 속성을 모드와 일치하도록 수정합니다.
- Minecraft 버전, 매핑, 로더와 Loom 버전이 지원하고자 하는 버전과 일치하는지 확인합니다. (<https://fabricmc.net/develop/> 에서 확인할 수 있습니다)

또 모드의 메인 클래스와 패키지 이름이 모드와 일치하게 변경할 수 있습니다.
