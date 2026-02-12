---
title: Linux에 Java 설치하기
description: Linux에 Java를 설치하는 방법에 대한 단계별 가이드입니다.
authors:
  - IMB11
---

이 가이드는 Linux에 Java 21을 설치하는 과정을 안내해 줄 것입니다.

## 1. 이미 Java가 설치되었는지 확인하기 {#1-check-if-java-is-already-installed}

터미널을 열고 `java -version`을 입력한 뒤, <kbd>Enter</kbd>를 누릅니다.

!["java -version"이 입력된 터미널](/assets/players/installing-java/linux-java-version.png)

:::warning
Minecraft: Java Edition 1.21을 플레이 하려면, 적어도 Java 21 이상의 Java가 설치되어야 합니다. 만약 이 명령어가 Java 21보다 낮은 버전을 표시한다면, 기존의 Java 설치를 업데이트해야 합니다.
:::

## 2. Java 21 다운로드하고 설치하기 {#2-downloading-and-installing-java}

대부분의 Linux 배포판에서 사용 가능한 OpenJDK 21을 사용하는 것을 권장합니다.

### Arch Linux {#arch-linux}

:::info
Arch Linux에 Java를 설치하는 방법에 대한 자세한 내용은 [Arch Linux 위키](https://wiki.archlinux.org/title/Java)를 참조하세요.
:::

다음 명령어를 사용하여 공식 패키지 저장소에서 최신 JRE를 설치할 수 있습니다:

```sh
sudo pacman -S jre-openjdk
```

그래픽 UI 없이 서버를 실행하려면, 헤드리스(headless) 버전을 설치할 수도 있습니다:

```sh
sudo pacman -S jre-openjdk-headless
```

모드를 개발하고자 한다면, JRE 대신 JDK를 설치해야 합니다:

```sh
sudo pacman -S jdk-openjdk
```

### Debian/Ubuntu {#debian-ubuntu}

아래의 `apt` 명령어를 사용하여 Java 21을 설치할 수 있습니다:

```sh
sudo apt update
sudo apt install openjdk-21-jdk
```

### Fedora {#fedora}

아래의 `dnf` 명령어를 사용하여 Java 21을 설치할 수 있습니다:

```sh
sudo dnf install java-21-openjdk
```

그래픽 UI가 필요하지 않다면, 대신 헤드리스(headless) 버전을 설치할 수도 있습니다:

```sh
sudo dnf install java-21-openjdk-headless
```

모드를 개발하고자 한다면, JRE 대신 JDK를 설치해야 합니다:

```sh
sudo dnf install java-21-openjdk-devel
```

### 기타 Linux 배포판 {#other-linux-distributions}

문서에 없는 배포판을 사용하고 있다면, [Adoptium](https://adoptium.net/temurin/)에서 최신 JRE를 다운로드할 수 있습니다.

모드를 개발하고자 한다면 배포판의 다른 가이드를 참고해야 합니다.

## 3. Java 21이 올바르게 설치되었는지 확인하기 {#3-verify-that-java-is-installed}

설치 과정을 완료했다면, 터미널을 열고 `java -version`을 입력하여 Java 21이 올바르게 설치되었는지 확인할 수 있습니다.

명령어가 성공적으로 실행되었다면, 아래와 같이 Java 버전이 표시된 것을 확인할 수 있을 것입니다:

!["java -version"이 입력된 터미널](/assets/players/installing-java/linux-java-version.png)
