---
title: Loom
description: Gradle용 Fabric Loom 플러그인에 대한 문서입니다.
authors:
  - Atakku
  - caoimhe
  - daomephsta
  - jamieswhiteshirt
  - Juuxel
  - kb-1000
  - modmuss50
  - SolidBlock-cn
---

Fabric Loom(약칭 'Loom')은 Fabric 생태계의 모드 개발을 위한 [Gradle](https://gradle.org/) 플러그인입니다.

Loom은 개발 환경에서 Minecraft와 모드를 설치하는 유틸리티를 제공하며, Minecraft의 난독화된 코드와 버전 간 차이점에 상관 없이 모드를 배포할 수 있게 합니다. Fabric 로더를 사용하기 위한 실행 구성과, Mixin 컴파일 과정, Fabric 로더의 Jar-in-Jar 시스템을 위한 유틸리티도 제공하고 있습니다.

Loom은 _모든_ 버전의 Minecraft: Java Edition을 지원하며, 버전 독립적이기 때문에 심지어 Fabric API에서 "공식" 지원하지 않는 버전에서도 사용할 수 있습니다.

이 페이지는 Loom의 모든 옵션과 기능에 대한 리퍼런스입니다. 모드 개발을 시작하고자 한다면, [시작하기](getting-started/setting-up-a-development-environment) 페이지를 참조하십시오.

## 하위 프로젝트를 종속성으로 설정 {#subprojects}

다른 Loom 프로젝트에 종속하는 다중 프로젝트 구조를 준비하고 있다면, 다른 프로젝트에 종속할 때 `namedElements` 구성을 사용해야 합니다. 기본적으로, 프로젝트의 "빌드 출력"은 intermediary로 리맵됩니다. `namedElements` 구성은 리맵되지 않은 프로젝트의 빌드 출력이 포함되어 있습니다.

```groovy
dependencies {
 implementation project(path: ":name", configuration: "namedElements")
}
```

만약 멀티 프로젝트 구조에서 개별 소스 셋을 사용하고 있다면, 예를 들어 다른 프로젝트의 클라이언트 소스 셋에 종속하려면 아래처럼 구성해야 합니다.

```groovy
dependencies {
 clientImplementation project(":name").sourceSets.client.output
}
```

## 일반 & 클라이언트 코드 분할 {#split-sources}

수년간, 서버 측의 일반 코드가 실수로 클라이언트 전용 코드를 호출하여 충돌하는 경우가 빈번했습니다. 새로운 Loom과 로더는 클라이언트 전용 코드를 자체 소스 셋에서만 사용할 수 있도록 하는 기능을 제공합니다. 이렇게 하면 컴파일 단계에서부터 문제를 찾아낼 수 있습니다. 빌드는 여전히 클라이언트와 서버 모두에서 사용 가능한 단일 Jar 파일을 출력합니다.

아래 `build.gradle`에서 가져온 일부 Groovy 스니펫을 사용하면 이 기능을 활성화할 수 있습니다. 이제 모드가 두 개의 소스 셋으로 나뉘었기에, 모드의 소스 셋을 정의하기 위해 새로운 DSL 필드를 사용해야 합니다. 이는 Fabric 로더가 모드의 클래스 경로를 그룹화할 수 있게 합니다. 또 이러한 기능은 다른 복잡한 멀티 프로젝트 구성에서도 유용합니다.

일반 코드와 클라이언트 코드를 분할하려면 Minecraft 1.18 (1.19 이상이 권장됩니다), 로더 0.14와 Loom 1.0 이상이 필요합니다.

```groovy
loom {
 splitEnvironmentSourceSets()

 mods {
   modid {
     sourceSet sourceSets.main
     sourceSet sourceSets.client
   }
 }
 }
```

## 자주 발생하는 문제 해결 {#issues}

때때로 Loom이나 Gradle이 손상된 캐시로 인하여 빌드에 실패할 수 있습니다. 명령줄에서 `./gradlew build --refresh-dependencies`를 실행하면 Gradle과 Loom이 강제로 모든 캐시 파일을 다시 다운로드하고 생성합니다. 이 과정은 몇 분 정도 소요될 수 있지만, 캐시와 관련된 대부분의 문제를 해결할 수 있습니다.

## 개발 환경 설정 {#setup}

Loom은 IDE에서 작업 공간을 설정하는 즉시 사용할 수 있도록 설계되었습니다. Minecraft 작업 공간이 생성될 때에는 내부에서 다음과 같이 꽤 많은 일이 일어납니다:

- 설정된 Minecraft 버전에 맞는 클라이언트와 서버 Jar를 공식 소스에서 다운로드
- `@Environment` 및 `@EnvironmentInterface` 어노테이션을 위해 클라이언트 Jar와 서버 Jar를 병합하여 병합된(Merged) Jar를 생성
- 구성된 매핑 다운로드
- 병합된 Jar를 Intermediary 매핑으로 리맵한 Intermediary Jar 생성
- 병합된 Jar를 구성된 매핑(일반적으로 Yarn 매핑)으로 리맵하여 매핑된(Mapped) Jar 생성
- (선택적) 매핑된 Jar를 디컴파일하여 매핑된 소스 Jar와 Linemap 생성, 생성된 Linemap을 매핑된 Jar에 적용
- Minecraft 종속성 추가
- Minecraft 어셋 다운로드
- 모드 종속성 처리 후 추가

## 캐시 {#caches}

- `${GRADLE_HOME}/caches/fabric-loom`: 사용자 캐시, 사용자의 모든 Loom 프로젝트에서 공유. Minecraft 어셋, Jar 파일 등
- `.gradle/loom-cache`: 최상위 프로젝트의 영구 캐시, 본 프로젝트와 하위 프로젝트가 공유. 종속중인 모드 Jar 등 리맵된 모드 캐시
- `**/build/loom-cache`: 프로젝트 개별 빌드 캐시

## 종속성 구성 {#configurations}

- `minecraft`: 개발 환경에서 사용할 Minecraft의 버전
- `mappings`: 개발 환경에서 사용할 매핑
- `modImplementation`, `modApi`, `modRuntime`: 모드 종속성을 위해 추가된 `implementation`, `api`, `runtime`의 변형. 개발 환경에서 사용하는 매핑에 맞게 리맵되며 포함된 하위 Jar이 모두 제거됩니다
- `include`: `remapJar` 작업의 출력에 Jar-in-Jar로써 포함되어야 할 종속성. 이 종속성 구성은 전이성이 없습니다. 모드가 아닌 일반 종속성을 위한 것으로, Loom이 자동으로 모드 식별자와 이름, 버전으로 `fabric.mod.json`을 생성합니다

## Loom이 자동으로 적용하는 구성 {#configuration}

- `java`, `eclipse` Gradle 내장 플러그인 적용
- [Fabric](https://maven.fabricmc.net/), [Mojang](https://libraries.minecraft.net/), Maven Central을 리포지토리로 등록
- `genEclipseEuns` 작업이 `eclipse` 작업으로 종료되도록 구성
- `.idea` 폴더가 최상위 프로젝트에 존재하는 경우, 어셋을 최신으로 업데이트하고 실행 구성을 `.idea/runConfigurations`에 생성
- `net.fabricmc:fabric-mixin-compile-extensions`을 `annotationProcessor` 종속성 구성으로 추가
- Mixin 어노테이션 처리기를 사용하는 모든 비테스트 JavaCompile 작업 구성
- `dev`로 분류된 리맵 Jar를 출력하는 `remapJar` 작업 구성
- `sourcesJar` 작업이 존재하는 경우, 리맵된 소스 Jar를 출력하는 `remapSourcesJar` 작업 구성
- `remapJar`및 `remapSourcesJar` 작업을 `build` 작업의 종속성으로 설정
- `remapJar` 작업과 `remapSourcesJar` 작업이 실행되었을 때 출력을 `archives` 아티팩트에 추가하도록 구성
- `maven-publish`에서 제공하는 각 MavenPublication에 종속성 구성 Maven 스코프가 적용된 모드 종속성을 POM에 추가

모든 실행 구성은 `${projectDir}/run` 디렉토리에서 실행되며, `-Dfabric.development` VM 변수가 추가되어 실행됩니다. 실행 구성의 Main 클래스는 모드 종속성으로 추가된 Fabric 로더 JAR의 `fabric-installer.json`을 통해 자동으로 정의되지만, 다른 모드 종속성을 통해 정의될 수 있습니다. 이 파일을 찾을 수 없는 경우, Main 클래스는 `net.fabricmc.loader.launch.knot.KnotClient`와 `net.fabricmc.loader.launch.knot.KnotServer`로 정의됩니다.
