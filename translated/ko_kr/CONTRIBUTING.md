# Fabric 문서 기여 가이드라인

이 웹사이트는 [VitePress](https://vitepress.dev/)를 사용해 다양한 마크다운 파일에서 정적 HTML을 생성합니다. [여기](https://vitepress.dev/guide/markdown.html#features)에서 VitePress가 지원하는 마크다운 확장 기능에 익숙해져야 합니다.

## 목차

- [Fabric 문서 기여 가이드라인](#fabric-documentation-contribution-guidelines)
  - [기여하기](#how-to-contribute)
  - [기여 프레임워크](#contributing-framework)
  - [기여 콘텐츠](#contributing-content)
    - [스타일 가이드라인](#style-guidelines)
    - [확장을 위한 가이드](#guidance-for-expansion)
    - [콘텐츠 인증](#content-verification)
    - [정리](#cleanup)
  - [문서 번역](#translating-documentation)

## 기여하기

매번 새로운 끌어오기 요청을 만들 때 마다 포크 저장소에 새로운 분기를 생성하는게 좋습니다. 이렇게 하면 여러 개의 끌어오기 요청을 관리하기 더 수월해집니다.

**만약 로컬에서 변경 사항을 미리 보려면, [Node.js 18+](https://nodejs.org/en/) 을 설치해야 합니다.**

아래 명령어를 실행하기 전에, 먼저 `npm install` 을 실행하여 모든 종속성을 설치했는지 확인하세요.

**개발 서버 시작하기**

개발 서버는 `localhost:3000`에서 로컬 변경 사항을 미리 볼 수 있게 합니다. 코드가 변경되면 변경 사항을 자동으로 적용합니다.

```bash
npm run dev
```

**웹사이트 빌드하기**

웹사이트를 빌드하면 모든 마크다운 파일이 정적 HTML로 컴파일되어 `.vitepress/dist` 에 저장됩니다.

```bash
npm run build
```

**빌드된 웹사이트 미리 보기**

`.vitepress/dist`에 저장된 콘텐츠를 `localhost:3000` 에서 볼 수 있도록 합니다.

```bash
npm run preview
```

## 프레임워크에 기여하기

프레임워크는 웹사이트의 내부 구조로써, 웹사이트의 프레임워크를 수정하는 모든 끌어오기 요청은 `framework` 라벨이 적용되어야 합니다.

프레임워크 끌어오기 요청은 [Fabric Discord](https://discord.gg/v6v4pMv) 또는 GitHub 이슈에서 문서 개발 팀과 모든 협의가 끝난 후에만 생성해야 합니다.

**(사이드바 또는 네비게이션 바 구성 변경은 프레임워크 끌어오기 요청으로 간주하지 않습니다)**

## 문서 콘텐츠에 기여하기

문서 콘텐츠에 기여하는건 Fabric 문서에 기여하는 가장 기본적인 방법입니다.

모든 콘텐츠는 아래 스타일 가이드라인을 준수해야 합니다.

### 스타일 가이드라인

Fabric 문서 웹사이트의 모든 페이지는 스타일 가이드를 준수해야 합니다. 가이드에 대해 궁금한것이 있다면, [Fabric Discord](https://discord.gg/v6v4pMv) GitHub 토론을 통해 질문할 수 있습니다.

스타일 가이드는 다음과 같습니다.

1. 모든 페이지는 파일 최상단에 제목과 설명이 입력되어야 합니다.

   ```md
   ---
   title: 여기에 페이지 제목 입력
   description: 여기에 페이지 설명 입력
   authors:
     - 여기에-GitHub-아이디-입력
   ---

   # ...
   ```

2. 페이지에 코드를 추가하는 경우, 코드를 리퍼런스 모드의 소스 코드에 기입해야 합니다. (모드는 `/reference` 폴더에 있습니다) 만약 span을 더 복잡하게 제어해야 하는 경우, [`markdown-it-vuepress-code-snippet-enhanced` 의 트랜스클루전 기능](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced)을 이용할 수 있습니다.

   **예시:**

   ```md
   <<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21 java}
   ```

   이는 리퍼런스 모드의 `FabricDocsReference.java` 파일의 15-21 줄을 임베드합니다.

   임베드된 코드는 다음처럼 표시됩니다.

   ```java
     @Override
     public void onInitialize() {
       // This code runs as soon as Minecraft is in a mod-load-ready state.
       // However, some things (like resources) may still be uninitialized.
       // Proceed with mild caution.

       LOGGER.info("Hello Fabric world!");
     }
   ```

   **트랜스클루전 예시**

   ```md
   @[code transcludeWith=#test_transclude](@/reference/.../blah.java)
   ```

   이렇게 하면 `blah.java` 파일에서 `#test_transclude` 태그가 적용된 섹션이 임베드 됩니다.

   예를 들어, `blah.java` 파일의 콘텐츠가 다음과 같을 때,

   ```java
   public final String test = "Bye World!"

   // #test_transclude
   public void test() {
     System.out.println("Hello World!");
   }
   // #test_transclude
   ```

   `#test_transclude` 태그 사이의 코드만 임베드 되므로 실제 표시되는 부분은 다음과 같습니다.

   ```java
   public void test() {
     System.out.println("Hello World!");
   }
   ```

3. 모든 원본 문서는 미국 문법의 영어로 작성되었습니다. [LanguageTool](https://languagetool.org/)로 문서의 문법이 올바른지 확인할 수 있으니, 너무 신경쓰지 않아도 됩니다. 그리고, 정리(Cleanup) 단계에서 문서 팀이 문법을 고치고 오류를 해결하고 있습니다. 다만, 문서 팀이 시간을 아낄 수 있도록 수시로 문법을 확인해 주세요.

4. 새로운 섹션을 만들 때는, `.vitepress/sidebars` 폴더에 `config.mts` 파일을 생성해 새로운 사이드바를 생성해야 합니다. 만약 도움이 필요하시다면, [Fabric Discord](https://discord.gg/v6v4pMv)의 `#docs` 채널에 질문해 주세요.

5. 새로운 페이지를 생성할 때도, `.vitepress/sidebars` 폴더에 연결을 추가해야 합니다. 역시나, 도움이 필요하시면 Fabric Discord의 `#docs` 채널에 질문하시면 됩니다.

6. 모든 이미지는 `/assets` 폴더의 적당한 위치에 저장되어야 합니다.

7. ⚠️ **다른 페이지를 언급하려는 경우 연결 링크를 사용하십시오** ⚠️

   이렇게 해야 버전 관리 체계가 연결된 페이지의 버전을 올바르게 처리할 수 있습니다. 절대 링크를 사용하면, 버전이 링크에 추가되지 않습니다.

   예를 들어서, `/players` 폴더의 `installing-fabric` 페이지를 언급하려는 경우, 절대 경로는 `/players/installing-fabric.md` 이지만, 다음과 같이 연결해야 합니다.

   ```md
   [This is a link to another page](./installing-fabric.md)
   ```

   반대로 다음과 같이 입력하면 **문제가 발생**하게 됩니다!

   ```md
   [This is a link to another page](/player/installing-fabric.md)
   ```

모든 콘텐츠 기여의 끌어오기 요청은 다음 단계를 거치게 됩니다.

1. 문서 내용 확장 (가능한 경우)
2. 내용 검증
3. 정리 (문법 등)

### 문서 내용 확장

만약 문서 팀이 끌어오기 요청의 내용을 확장할 수 있을것으로 보이면, 문서 팀이 가능한 확장 내용과 함께 `expansion` 라벨을 끌어오기 요청에 추가하게 됩니다. 제안에 동의하신다면, 문서 내용을 확장하시면 됩니다.

**팀의 제안에 압박을 느낄 필요는 없습니다.** 끌어오기 요청을 확장하고 싶지 않은 경우, 간단히 `expansion` 라벨을 제거하시면 됩니다.

개인적인 사정으로 확장이 어렵거나, 이후에 다른 분이 확장하기를 원하신다면, 이후에 [Issues 페이지](https://github.com/FabricMC/fabric-docs/issues)에 바라는 확장 방향을 설명한 이슈를 생성하시는것도 좋은 방법입니다.

### 내용 검증

이는 내용이 정확하고 Fabric 문서 스타일 가이드를 준수하는지 확인하는 단계이므로 가장 중요합니다.

### 정리

끌어오기 요청을 병합하기 전, 문서 팀이 문서의 문법을 고치고 내용을 정리하는 단계입니다.

## 문서 번역하기

문서를 한국어 또는 다른 언어로 번역하고 싶다면, [Fabric Crowdin](https://crowdin.com/project/fabricmc)에서 번역에 참여할 수 있습니다.
