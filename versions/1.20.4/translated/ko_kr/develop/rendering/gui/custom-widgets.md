---
title: 사용자 정의 위젯
description: 화면에 사용될 사용자 정의 위젯을 만드는 방법을 알아보세요.
authors:
  - IMB11

search: false
---

# 사용자 정의 위젯

위젯은 일반적으로 화면에 추가되는 렌더링 컴포넌트의 컨테이너로, 키 입력, 마우스 클릭 등의 여러 이벤트를 통해 플레이어와 상호작용할 수 있습니다.

## 위젯 만들기

`ClickableWidget`을 확장(Extend)하는 등 위젯 클래스를 만드는 방법은 여러 가지가 있습니다. `ClickableWidget` 클래스는 `Drawable`, `Element`, `Narratable`, `Selectable` 인터페이스를 확장해 높이, 너비, 위치, 이벤트 처리 등 여러 유용한 도구를 제공합니다.

- `Drawable` - 렌더링 - `addDrawableChild` 메소드를 통해 화면에 위젯을 등록하려면 필수적인 인터페이스입니다.
- `Element` - 이벤트 - 마우스 클릭, 키 입력 등과 같은 이벤트를 처리하려면 필수적입니다.
- `Narratable` - 접근성 - 스크린 리더 등 여러 접근성 도구가 사용자 정의 위젯에 접근하려면 필수적입니다.
- `Selectable` - 탭 선택 - 위젯이 <kbd>탭</kbd> 키를 통해 선택 가능하게 하려면 필수적입니다.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

## 화면에 위젯 추가하기

다른 위젯와 같이, 위젯을 화면에 추가하려면 `Screen` 클래스에서 제공되는 `addDrawableChild` 메소드를 사용해야 합니다. 이러한 작업은 `init` 메소드에서 수행되어야 합니다.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![화면에 표시되는 사용자 정의 위젯](/assets/develop/rendering/gui/custom-widget-example.png)

## 위젯 이벤트

`onMouseClicked`, `onMouseReleased`, `onKeyPressed` 등의 메소드를 덮어 쓰기(Override) 하여 마우스 클릭, 키 입력 등과 같은 이벤트를 처리할 수 있습니다.

예를 들어, `ClickableWidget` 클래스에서 제공되는 `isHovered()` 메소드를 통해 마우스가 호버되면 위젯 색이 변경되도록 해보겠습니다.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

![호버 이벤트 예시](/assets/develop/rendering/gui/custom-widget-events.webp)
