---
title: 프로젝트 만들기
description: Fabric 템플릿 모드 생성기로 새 모드를 생성하는 방법에 대한 단계별 가이드입니다.
authors:
  - IMB11
  - Cactooz

search: false
---

# 프로젝트 만들기 {#creating-a-project}

페브릭에는 템플릿 생성기를 사용하여 새 모드를 생성하는 쉬운 방법이 있습니다. 원하는 경우 예제 모드 저장소를 사용하여 수동으로 새 프로젝트를 생성할 수 있습니다. [수동으로 프로젝트 생성](#manual-project -creation).

## 프로젝트 생성하기 {#generating-a-project}

[Fabric 템플릿 모드 생성기](https://fabricmc.net/develop/template/)로 새 프로젝트를 생성할 수 있습니다. 모드 이름, 패키지 이름, 그리고 개발하려면 Minecraft 버전 등의 필수 항목을 입력해야 합니다.

패키지의 이름은 소문자여야 하며, 점으로 분류되고, 다른 프로그래머의 패키지와의 충돌을 피하기 위해 고유해야 합니다. 주로, `com.example.modid`같은 형식의 역순 인터넷 도메인으로 지정됩니다.

![생성기 미리보기](/assets/develop/getting-started/template-generator.png)

만약 Kotlin을 사용하고 싶으면, Yarn 맵핑 보다는 Mojang의 공식 맵핑을 사용하거나, 데이터 생성기를 추가하고 싶은 경우, `고급 옵션`에서 적절한 옵션을 선택할 수 있습니다.

![고급 옵션 섹션](/assets/develop/getting-started/template-generator-advanced.png)

필수 필드를 입력한 후 '생성' 버튼을 클릭하면 zip 파일 형식으로 새 프로젝트를 다운받을 수 있습니다.

압축파일을 원하는 위치에 푼다음 IntelliJ에서 열어줍니다.

![프로젝트 열기 스크린샷](/assets/develop/getting-started/open-project.png)

## 프로젝트 가져오기 {#importing-the-project}

IntelliJ IDEA에서 프로젝트를 열면, IDE가 자동으로 Gradle의 구성 요소를 불러온 다음 필요한 작업을 수행할 것입니다.

Gradle 빌드 알림을 받으면 `Import Gradle Project` 버튼을 눌러야 합니다.

![Gradle 확인 스크린샷](/assets/develop/getting-started/gradle-prompt.png)

여기까지 하셨다면 프로젝트 탐색기에 프로젝트 파일이 뜨고 이제 모드 개발을 시작할 수 있습니다.

## 직접 프로젝트 생성 {#manual-project-creation}

:::warning
리포지토리를 복사하려면 [Git](https://git-scm.com/)이 설치되어 있어야 합니다.
:::

Fabric 템플릿 모드 생성기를 사용할 수 없는 경우 다음 단계에 따라 프로젝트를 수동으로 생성할 수 있습니다.

먼저 Git을 이용해 리포지토리를 복사해 줍니다.

```sh
git clone https://github.com/FabricMC/fabric-example-mod/my-mod-project
```

명령어를 실행하면 my-mod-project라는 새 폴더에 복사됩니다.

새로 생성된 폴더에서 `.git` 폴더를 삭제후 Intellij에서 열어줍니다. 만약 파일 탐색에서 `.git` 폴더가 안보인다면 파일 탐색기 설정에서 숨긴 항목 표시를 켜줍니다.

IntelliJ에서 프로젝트를 열면 에디터가 자동으로 필요한 작업을 실행합니다.

앞에서 말한 것처럼 Gradle 빌드 알림을 받으면 `Import Gradle Project` 버튼을 눌러줍니다.

### 템플릿 수정하기 {#modifying-the-template}

프로젝트를 가져온 후에는 모드 세부정보를 수정해야 합니다.

- 프로젝트의 `gradle.properties` 파일을 수정해 `maven_group` 및 `archive_base_name` 속성을 모드와 일치하도록 변경하세요.
- `fabric.mod.json` 파일을 수정하여 `id`, `name` 및 `description` 속성을 모드와 일치하도록 변경하세요.
- 대상으로 삼으려는 버전과 일치하게 <https://fabricmc.net/develop/>을(를) 통해 쿼리할 수 있는 Minecraft의 버전과 맵핑, 로더와 Loom 버전을 업데이트 해주세요.

모드와 패키지 이름이 일치하도록 패키지 이름과 모드의 메인 클래스 이름을 변경할 수 있습니다.
