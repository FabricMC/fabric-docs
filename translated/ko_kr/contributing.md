---
title: 기여 가이드라인
description: Fabric 문서 기여 가이드라인
---

이 웹사이트는 [VitePress](https://vitepress.dev/)를 사용해 다양한 마크다운 파일에서 정적 HTML을 생성합니다. [여기](https://vitepress.dev/guide/markdown#features)에서 VitePress가 지원하는 마크다운 확장 기능에 익숙해져야 합니다.

이 웹사이트에 기여하실 수 있는 세 가지의 방법이 있습니다:

- [문서 번역](#translating-documentation)
- [기여 콘텐츠](#contributing-content)
- [기여 프레임워크](#contributing-framework)

Fabric 문서 웹사이트의 모든 페이지는 스타일 가이드를 준수해야 합니다.

## 문서 번역하기

문서를 한국어 또는 다른 언어로 번역하고 싶다면, [Fabric Crowdin](https://crowdin.com/project/fabricmc)에서 번역에 참여할 수 있습니다.

<!-- markdownlint-disable-next-line titlecase-rule -->

## <Badge type="tip">new-content</Badge> 콘텐츠 기여 {#contributing-content}

문서 콘텐츠에 기여하는 것은 Fabric 문서에 기여하는 가장 기본적인 방법입니다.

모든 콘텐츠 기여는 다음 단계를 따르며 각 단계는 라벨과 연결됩니다:

1. <Badge type="tip">로컬에서</Badge> 변경 사항을 준비하고 PR을 푸시
2. <Badge type="tip">stage:expansion</Badge> 필요한 경우의 확장 가이드라인
3. <Badge type="tip">stage:verification</Badge>: 콘텐츠 인증
4. <Badge type="tip">stage:cleanup</Badge>: 문법, 린팅...
5. <Badge type="tip">stage:ready</Badge>: 합체 준비 완료!

모든 콘텐츠는 아래 스타일 가이드라인을 준수해야 합니다.

### 1. 당신의 변경 사항을 준비하세요 {#1-prepare-your-changes}

이 웹사이트는 오픈 소스이며, GitHub 리포지토리에서 개발되었으므로, 다음 GitHub의 흐름을 따릅니다:

1. [GitHub 리포지토리 포크](https://github.com/FabricMC/fabric-docs/fork)
2. 포크에 새 브랜치 만들기
3. 해당 브랜치에서 변경하기
4. 원본 리포지토리에 끌어오기 요청 열기

GitHub 흐름에 대한 자세한 내용은 [여기](https://docs.github.com/en/get-started/using-github/github-flow)에서 확인할 수 있습니다.

GitHub의 웹 UI에서 변경하거나 로컬에서 웹을 개발하고 미리 볼 수 있습니다.

#### 포크 복사하기 {#clone-your-fork}

로컬에서 개발하려면 [Git](https://git-scm.com/)을 설치해야 합니다.

그런 다음, 리포지토리의 포크를 다음과 같이 복제하세요:

```sh
# make sure to replace "your-username" with your actual username
git clone https://github.com/your-username/fabric-docs.git
```

#### 종속성 설치 {#install-dependencies}

**만약 로컬에서 변경 사항을 미리 보려면, [Node.js 18+](https://nodejs.org/en/) 을 설치해야 합니다.**

그런 다음 모든 종속성을 다음과 같이 설치하세요:

```sh
npm install
```

#### 개발 서버 실행 {#run-the-development-server}

개발 서버는 `localhost:5173`에서 로컬 변경 사항을 미리 볼 수 있게 합니다. 코드가 변경되면 변경 사항을 자동으로 적용합니다.

```sh
npm run dev
```

이제 브라우저에서 `http://localhost:5173`을 방문해 웹사이트를 열고 탐색할 수 있습니다.

#### 웹사이트 빌드 {#building-the-website}

웹사이트를 빌드하면 모든 마크다운 파일이 정적 HTML로 컴파일되어 `.vitepress/dist` 에 저장됩니다.

```sh
npm run build
```

#### 웹사이트 빌드 프리뷰 {#previewing-the-built-website}

`.vitepress/dist`에 저장된 콘텐츠를 포트 `4173` 에서 볼 수 있도록 합니다:

```sh
npm run preview
```

#### 끌어오기 요청 열기 {#opening-a-pull-request}

만약 변경 사항에 만족하면, 변경 사항을 `푸시`할 수 있습니다:

```sh
git add .
git commit -m "Description of your changes"
git push
```

그런 다음, `git push`의 출력에 있는 링크를 따라 PR을 여세요.

### 2. <Badge type="tip">stage:expansion</Badge> 필요한 경우의 확장 가이드라인 {#2-guidance-for-expansion-if-needed}

만약 문서화 팀에서 끌어오기 요청을 확장할 수 있다고 생각하면, 팀원 중 한 명이 끌어오기 요청에 <Badge type="tip">stage:expansion</Badge> 라벨과 함께 확장할 수 있다고 생각하는 내용을 설명하는 댓글을 추가할 것입니다. 제안에 동의하신다면, 문서 내용을 확장하시면 됩니다.

끌어오기 요청에 따라 확장하길 원치 않지만, 이후 다른 사람이 확장하길 바랃나면, [Issues 페이지](https://github.com/FabricMC/fabric-docs/issues)에서 이슈를 생성하고 확장할 수 있다고 생각하는 내용을 설명해야 합니다. 추후 문서팀은 당신의 PR에 <Badge type="tip">help-wanted</Badge> 라벨을 추가할 것입니다.

### 3. <Badge type="tip">stage:verification</Badge> 콘텐츠 인증 {#3-content-verification}

이 단계는 콘텐츠가 정확하고 Fabric의 문서 스타일 가이드를 준수하는지의 여부를 확인하는 단계이므로 가장 중요합니다.

이 단계에서는 다음 질문에 답해야 합니다:

- 모든 콘텐츠가 올바른가요?
- 모든 콘텐츠가 최신인가요?
- 콘텐츠가 다양한 운영 체제 등의 모든 경우에 적용되나요?

### 4. <Badge type="tip">stage:cleanup</Badge> 클린업 {#4-cleanup}

이 단계에서는 다음과 같은 일이 발생합니다:

- [[LanguageTool(https://languagetool.org/)을 사용하여 문법 문제 수정
- [`markdownlint`](https://github.com/DavidAnson/markdownlint)를 사용해 모든 마크다운 파일 린트하기
- [Checkstyle](https://checkstyle.sourceforge.io/)을 사용해 모든 Java 코드 서식 지정
- 다른 기타 수정 또는 개선 사항

## <Badge type="tip">framework</Badge> 프레임워크에 기여하기 {#contributing-framework}

프레임워크는 웹사이트의 내부 구조를 나타내며, 모든 끌어오기 요청은 <Badge type="tip">framework</Badge> 라벨이 붙습니다.

프레임워크 끌어오기 요청은 [Fabric Discord](https://discord.gg/v6v4pMv) 또는 GitHub 이슈에서 문서 개발 팀과 모든 협의가 끝난 후에만 생성해야 합니다.

:::info
사이드바 또는 네비게이션 바 구성 변경은 프레임워크 끌어오기 요청으로 간주하지 않습니다.
:::

## 스타일 가이드라인 {#style-guidelines}

가이드에 대해 궁금한것이 있다면, [Fabric Discord](https://discord.gg/v6v4pMv) GitHub 토론을 통해 질문할 수 있습니다.

### 원본을 미국식 영어로 작성하기 {#write-the-original-in-american-english}

모든 원본 문서는 미국 문법의 영어로 작성되었습니다.

### Frontmatter에 데이터 추가하기 {#add-data-to-the-frontmatter}

모든 페이지는 파일 최상단에 제목과 설명이 입력되어야 합니다.

Markdown 파일의 frontmatter에 있는 `authors`에 GitHub 사용자 이름을 추가하는 것을 잊지 마세요! 이렇게 하면, 저희는 당신에게 적당한 크레딧을 제공할 수 있습니다.

```yaml
---
title: Title of the Page
description: This is the description of the page.
authors:
  - your-username
---
```

### 제목에 앵커 추가하기 {#add-anchors-to-headings}

각 제목은 해당 제목에 연결하는 데 쓰이는 앵커가 있어야 합니다:

```md
## This Is a Heading {#this-is-a-heading}
```

앵커에는 반드시 소문자, 숫자 및 대시를 사용해야 합니다.

### `/reference` 모드 이내 코드 배치하기 {#place-code-within-the-reference-mod}

만약 코드를 포함하는 페이지를 만들거나 수정하는 경우, 코드를 적절한 위치 내의 리퍼런스 모드 에 배치해야 합니다. (리포지토리의 `/reference` 폴더에 있습니다) 그런 다음, [VitePress의 코드 스니펫 기능](https://vitepress.dev/guide/markdown#import-code-snippets)을 이용하여 코드를 임베드합니다.

예를 들어, 리퍼런스 모드에서 `FabricDocsReference.java` 파일의 15에서 21번째 줄을 하이라이트하려면:

::: code-group

```md
<<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21}
```

<<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21}[java]

:::

만약 span을 더 복잡하게 제어해야 하는 경우, [`markdown-it-vuepress-code-snippet-enhanced` 의 트랜스클루전 기능](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced)을 이용할 수 있습니다.

예를 들어, 이렇게 하면, 위 파일에서 `#entrypoint` 태그로 표시된 섹션이 임베드될 것입니다.

::: code-group

```md
@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)
```

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

:::

### 각 섹션마다 새 사이드바 만들기 {#create-a-sidebar-for-each-new-section}

새로운 섹션을 만들 때는, `.vitepress/sidebars` 폴더에 `config.mts` 파일을 생성해 새로운 사이드바를 생성해야 합니다.

만약 도움이 필요하시다면, [Fabric Discord](https://discord.gg/v6v4pMv)의 `#docs` 채널에 질문해 주세요.

### 관련 사이드 바에 새 페이지 추가하기 {#add-new-pages-to-the-relevant-sidebars}

새로운 페이지를 생성할 때도, `.vitepress/sidebars` 폴더에 연결을 추가해야 합니다.

역시나, 도움이 필요하시면 Fabric Discord의 `#docs` 채널에 질문하시면 됩니다.

### `/assets`에 미디어 배치하기 {#place-media-in-assets}

모든 이미지는 `/assets` 폴더의 적당한 위치에 저장되어야 합니다.

### 상대적 링크 사용하기! {#use-relative-links}

이렇게 해야 버전 관리 시스템이 마련되어 있어 링크를 처리해 미리 버전을 추가하기 때문입니다. 절대 링크를 사용하면, 버전 번호가 링크에 추가되지 않습니다.

또한 링크에 파일 확장자를 추가해서는 안 됩니다.

예를 들어서, `/players` 폴더의 `installing-fabric` 페이지를 언급하려는 경우, 절대 경로는 `/players/installing-fabric.md` 이지만, 다음과 같이 연결해야 합니다.

::: code-group

```md:no-line-numbers [✅ Correct]
This is a relative link!
[Page](../players/index)
```

```md:no-line-numbers [❌ Wrong]
This is an absolute link.
[Page](/players/index)
```

```md:no-line-numbers [❌ Wrong]
This relative link has the file extension.
[Page](../players/index.md)
```

:::
