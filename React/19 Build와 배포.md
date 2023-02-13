# 19. Build와 배포

## Build(빌드)

- 코드와 애플리케이션이 사용하는 이미지, CSS 파일 등의 파일을 모두 모아서 패키징 하는 과정
- 코드가 식별이 불가능 하도록 OP{FUS}CATION이라고 불리는 난독화 과정을 하거나 필요 없는 공백이나 줄바꿈을 제거하여 Minification이라고 불리는 축소 과정도 포함이 된다.
- 최종적으로 만들어진 파일들은 Build 폴더에 생성된다.

```jsx
$ npm run build
```

## Serve 패키지

- 위에서 만들어진 React Build 파일을 가지고 웹 애플리케이션을 실행하기 위해  Static 파일들을 Serving 해주는 역할을 해주는 프로그램이다.

```jsx
$ npm install -g serve
```

- serve 패키지 설치 시에는 -g를 통해 global mode에 설치해야 함
- **-g:** global mode를 설정하면 해당 프로젝트 내 뿐만 아니라 현재 사용 중인 컴퓨터의 다른 경로에서 사용 가능

```jsx
$ serve -s build
```

- 위 명령어를 실행하면 serve 프로그램이 실행된다.

<img src="https://user-images.githubusercontent.com/111109411/218306231-ba3ee7ad-60b5-46a7-87e2-d059675a50aa.png" width=60%>


## 배포

- 빌드를 통해 생성된 정적인 파일들을 배포하려는 서버에 올리는 과정
- 이 강의에서는 설명하지 않는다.
