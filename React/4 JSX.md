# 4. JSX

## JSX란?

- A syntax extension to JavaScript: 자바스크립트의 확장 문법
- react도 사실 공식 명칭은 react.js이다.
- JavaScript + XML/HTML
    - 기본적인 구조는 JavaScript의 구조이지만 실제로 사용하는 값을 보면 html tag값들을 사용하는 것

## JSX 코드

```jsx
const element = <h1> Hello, world! </h1>
```

- 이렇게 자바 스크립트의 equal = 연산자를 사용하는데 오른쪽 값이 일반적인 값이 아닌 HTML 코드를 사용

## JSX의 역할

- 내부적으로 xml html코드를 자바스크립트 코드로 변환하는 과정을 거치게 된다.
- 이때 xml, html을 자바스크립트로 변환하는 기능을 하는 것이 React의 createElement함수이다.

```jsx
React.createElement(
	type,
	[props],
	[...children]
)
```

- JSX를 사용한 코드
    
    ```jsx
    class Hello extends React.Component {
    	render(){
    		return <div> Hello {this.props.toWhat} </div>;
    	}
    }
    
    ReactDom.render(
    	<Hello toWhat="World" />
    	document.getElementById("root")
    );
    ```
    
- JSX를 사용하지 않은 코드
    
    ```jsx
    class Hello extends React.Component {
    	render(){
    		return React.createElement("<div>",null, `Hello ${this.props.toWhat}`);
    	}
    }
    
    ReactDom.render(
    	React.createElement(Hello, {toWhat: 'World'} , null),
    	document.getElementById("root")
    );
    ```
    
    - 이렇게 JSX를 사용한 것과 사용하지 않은 코드의 차이를 보면 XML, HTML 언어를 사용하면서 React.createElemnt()를 사용해 객체를 생성하는지 아니면 바로 JavaScript 내부에서 사용하는지 차이이다.

## React.createElement()

```jsx
const element = {
	type: "hi",
	props: {
		className: "greeting",
		children: "Hello, World!"		
	}
}
```

- 만약 React.createElement() 함수를 호출하게 되면 보이는 것과 같은 element 객체를 생성하게 된다.
- React에서는 이러한 객체들을 사용해서 돔을 생성하고 그렇게 최신 상태를 유지하게 된다.
    - type: div, span과 같은 유형을 나타내거나 다른 react 컴포넌트가 들어갈 수 있다.
    - props: react에 관련된 속성
    - children: 현재 element가 포함하고 있는 자식 element
- JSX를 사용하는 것이 필수는 아니지만 굳이 React.createElement를 사용해야 할 이유가 없다. JSX를 사용하는 장점들이 많기 때문에 편리함

## JSX의 장점

- 간결한 코드
    - 함수 호출을 직접적으로 하지 않기 때문에 parameter들을 넣지 않기 때문에 코드가 더욱 간결해진다.
- 가독성 향상
    - parameter를 사용하지 않기 때문에 코드의 의미가 훨씬 빠르게 와 닿는다.
    - 가독성이 좋다면 버그를 발견하기 쉽다.
- Injection Attacks 방어
    - 입력 창에 일반적인 문자가 아닌 소스 코드를 입력해서 해킹하는 것을 막을 수 있다.
        
        ```jsx
        const title = response.potentiallyMaliciousInput;
        
        const element = <h1>{title} </h1>;
        ```
        
        - 이런 식으로 React DOM은 렌더링 하기 이전에 임베딩 된 값을 모두 문자열로 변환하게 된다. 그렇기 때문에 명시적으로 선언되지 않은 값이 괄호 사이에 들어갈 수 없다.
        

## JSX 사용법

- 기본적으로 자바스크립트 문법을 확장 시킨 것 이기 때문에 모든 자바스크립트 문법을 지원한다.
- 만약 HTML, XML 코드를 사용하다가 중간에 자바스크립트 코드를 사용하고 싶을 때는 중괄호 {}를 사용해서 묶어주면 된다.
    - 변수나 함수 사용 모두 가능하다.
    
    ```jsx
    const name = "다찬";
    
    // 태그 내부에서 자바스크립트 변수 가져오기
    const element = <h1>안녕, {name}</h1>;
    
    //태그 내부에 자바스크립트 함수 실행
    const element = <h1>안녕, {function_a(name)}</h1>;
    
    // 태그의 속성에 값 넣기
    const element = <div class={user.input}></div>;
    ```
    

실습 예제

<img src="https://user-images.githubusercontent.com/111109411/216918327-59a62f95-4b13-4a90-b9a8-ac8398c74a58.png" width=40%>



- src 폴더 내부에 demo라는 폴더를 만들고 example_children.jsx와 example_parent.jsx파일을 생성한다.

**example_children.jsx**

```jsx
import React from "react";

function Book(props){
    return (
      <div>
        <h1>{`이 책의 이름은 ${props.name}입니다.`}</h1>
      </div>
    );
}

export default Book;
```

**example_parent.jsx**

```jsx
import React from "react";
import Book from "./Book";

function Library(props){
    return (
      <div>
        <Book name="처음 만난 파이썬" numOfPage={300}>
      </div>
    );
}

export default Library;
```

- Parent의 경우에는 children이라는 jsx function을 가져와 사용하게 된다. 이때 사용하는 것이 컴포넌트를 함수처럼 불러와 사용하는 것이다.
- props는 HTTP 통신에서 request의 값을 사용하듯이 props.keyname 처럼 사용하면 된다.

**index.js**

- 실제로 이것을 실행하기 위해서는 index.js파일에 추가 시켜줘야 한다.

```jsx
import Library from './demo/example_parent';

...

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
    <Library / >
  </React.StrictMode>
)
```
