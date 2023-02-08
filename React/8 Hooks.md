# 8. Hooks

## Hooks이란?

- 함수 컴포넌트는 state를 별도로 정의해서 사용하지 않고 갈결하기 때문에 생명주기에 맞춰 어떤 코드가 실행되도록 할 수 없었다.
- Hooks을 사용하면 함수 컴포넌트가 클래스 컴포넌트처럼 사용할 수 있게 된다.
    
  
 <img src="https://user-images.githubusercontent.com/111109411/217471715-3426d684-a7d5-4ab1-a137-a56b0108dc13.png" width=60%>


    
- 원하는 시점에 원하는 함수를 실행하도록 해줌
    

<img src="https://user-images.githubusercontent.com/111109411/217471781-d186812d-e2ee-4717-8d2e-b31bb957c409.png" width=60%>


    

## useState() Hook

- state를 사용하기 위한 Hook
    - 원래 count를 증가시키더라도 재렌더링이 이루어지지 않기 때문에 숫자가 변하지 않는다.

```jsx
function Counter(props){
	var count = 0;

	return (
		<div>
			<p> 총 {count}번 클릭했습니다. </p>
			<button onClick={() => count++}>
				클릭
			</button>
		</div>
	)
}
```

**useState() 사용법**

```jsx
const [변수명, set함수명] = useState(초기값);
```

```jsx
import React, {useState} from "react";

function Counter(props){
	const [count, setCount] = uewState(0);

	return (
		<div>
			<p> 총 {count}번 클릭했습니다. </p>
			<button onClick={() => setCount(count +1)}>
				클릭
			</button>
		</div>
	)
}
```

- 클래스 컴포넌트와 함수 컴포넌트의 state 사용법의 차이 중 하나는 함수 컴포넌트에서 useState()를 사용하게 되면 변수 각각에 대해 set함수가 따로 존재하게 되는 것이다.

## useEffect() 사용법

- side effect를 수행하기 위한 Hook
    - side effect: 부작용이라는 의미로써 개발자가 의도치 않은 코드가 실행되면서 버그가 나타나는 것 → 효과, 영향에 해당하는 effect에 해당한다.
    - 이러한 side effect는 다른 컴포넌트에 영향을 미칠 수 있으며 렌더링 중에는 작업이 완료 될 수 없다.

```jsx
useEffect(이펙트 함수, 의존성 배열);
```

- 만약 Effect function이 mount, unmount 시에 한번 씩만 실행 되도록 하기 위해서는 의존성 배열에 빈 배열을 넣으면 된다.

```jsx
useEffect(이펙트 함수,[] )
```

- 만약 의존성 배열을 생략하게 되면 컴포넌트가 업데이트 될 때마다 호출이 된다.

```jsx
useEffect(이펙트 함수)
```

```jsx
useEffect(() ⇒ {
	document.title = "You clicked ${count} times"
});
```

- 위처럼 정의하게 되면 매번 렌더링 될 때마다 useEffect가 실행된다.

- useEffect 구조
    
    ```jsx
    useEffect(() => {
    // 컴포넌트가 마운트 된 이후
    // 의존성 배열에 있는 변수들 중 하나라도 값이 변경되었을 때 실행됨
    // 의존성 배열에 빈 배열을 넣으면 마운트와 언마운트시에 단 한번 씩만 실행된다.
    // 의존성 배열 생략 시 컴포넌트 업데이트 시마다 실행 됨
    ...
    
    	return () => {
    		// 컴포넌트가 마운트 해제되기 전에 실행 됨
    	}
    
    }, [의존성 변수1, 의존성 변수2,...]);
    ```
    

## useMemo()

- Memoized value를 return해주는 Hook
- Memoization: 최적화를 위해 사용되는 용어로 비용이 높은 호출에 대해 이전에 호출한 결과를 return해주는 것이다. 한마디로 쿠키나 캐시 같은 느낌
    
    ```jsx
    const memoizedValue = useMemo(
    	() => {
    		// 연산량이 높은 작업을 수행하여 결과를 반환
    		return computeExpensiveValue(의존성 변수1, 의존성 변수2);
    	},
    	[의존성 변수1, 의존성 변수2]
    );
    ```
    
- 컴포넌트가 반복될 때마다 연상량이 높은 작업을 반복할 필요가 없으니 렌더링 속도가 빨라진다.
- useMemo()로 전달된 함수는 렌더링이 실행되는 동안 실행되기 때문에 렌더링 과정에서 실행되면 안되는 동작(side effect)등을 포함하면 안된다.
- 의존성 배열을 넣지 않을 경우 매 렌더링마다 함수가 실행 됨. 때문에 의존성 배열을 넣지 않는다면 별 의미가 없어진다.
- 만약 의존성 배열이 빈 배열일 경우 컴포넌트 마운트(생성) 시에만 호출됨
    
    ```jsx
    const memoizedValue = useMemo(
    	() => {
    		return computeExpensiveValue(a,b);
    	},
    	[]
    );
    ```
    

## useCallback()

- useMemo()와 유사하지만 값이 아닌 함수를 반환해준다.
- 컴포넌트가 렌더링 될 때마다 함수를 새로 정의하는 것이 아니라 의존성 배열의 값이 바뀐 경우에만 함수를 새로 정의해준다.
    
    ```jsx
    const memoizedCallback = useCallback(
    	() => {
    		doSomething(의존성 변수1, 의존성 변수2);
    	},
    	[의존성 변수1, 의존성 변수2]
    ); 
    ```
    
- 동일한 역할을 하는 두 줄의 코
    
    ```jsx
    useCallback(함수, 의존성 배열);
    
    useMemo(() => 함수, 의존성 배열);
    ```
    

## useRef()

- Reference를 사용하기 위한 Hook
- **Reference**: 특정 컴포넌트에 접근할 수 있는 객체
- useRef Hook은 쉽게 말해 변경 가능한 current라는 속성을 가진 상자라고 생각하면 된다.
- refObject.current = 현재 참조하고 있는 Element
    
    ```jsx
    const refContainer = useRef(초깃값);
    ```
    
    - ex) 버튼 클릭 시 input으로 focus해주는 부분
    
    ```jsx
    function TextInputWithFocusButton(props){
    	// 초기값이 null인 reference를 생성
    	const inputElem = useRef(null);
    
    	const onButtonClick = () => {
    		//current는 마운트된 input element를 가리킴
    		inputElem.current.focus();
    	}
    
    	return (
    		<>
    			<input ref={inputElem} type="text" />
    			<button onClick={onButtonClick}>
    				Focus the input
    			</button>
    		</>
    	);
    }
    ```
    
    - 쉽게 생각하면 reference의 느낌 그대로 참조의 역할을 해주는 상자를 만들어 주는 것이다. 그래서 위처럼 특정 reference를 참조하고 있는 것에 대한 동작을 지정해줄 수 있게 inputElem.current라는 속성이 존재.
    - HTML로만 생각해봤을 때는 특정 class 명을 생성해놓고 해당 class를 가지고 있는 모든 tag에 대해서 동작을 수행하도록 하는 것
- 아래와 같이 HTML에 포함 시키더라도 노드가 변경될 때마다 myRef current 속성에 DOM 노드를 저장한다.
    
    ```jsx
    <div ref={myRef}  />
    ```
    
- useRef() Hook을 사용하는 것과 current 속성을 추가하는 것에 차이는 useRef()는 매번 렌더링 될 때마다 같은 Reference 객체를 반환한다.
- 또한 useRef() Hook은 내부의 데이터가 변경되었을 때 별도로 알리지 않기 때문에 재 렌더링이 일어나지 않는다.

## useCallback과 useRef의 차이

- Callback ref: DOM 노드의 변화를 알기 위해서 사용하는 함수.
    - React는 기본적으로 ref가 다른 노드에 연결될 때 Callback을 호출하게 된다.
- useRef Hook을 사용하면 current ref속성이 변경되었을 때 따로 알려주지 않고 useCallbak Hook을 사용하면 자식 컴포넌트가 변경되었을 때 알림 받을 수 있고 이를 통해 다른 값들을 바꿔줄 수 있다.

```jsx
function MesaureExample(proprs){
	const [height, setHeight] = useState(0);

	const measureRef = useCallback(node => {
		if(node !== null){
			setHeight(node.getBoundingClientRect().height);
		}
	}, []	);
	
	return (
		<>
			<h1 ref={measureRef}> 안녕! </h1>
			<h2> 위 헤더의 높이는 {Math.round(height)}px입니다. </h2>
		</>
	);
}
```

- h1의 높이 값을 매번 update해주고 있는데 useCallback함수의 의존성 배열을 빈 배열을 넣어 줌으로써 h1 태그가 마운트, 언 마운트 시에만 callback함수가 호출이 되고 재 렌더링 시에는 호출되지 않는다.

## Hook의 규칙

1.  **최상위 레벨 호출**
- Hook은 무조건 최상위 레벨에서만 호출해야 한다. 이때 말하는 최상위란 리액트 함수 컴포넌트의 최상위 레벨을 의미한다.
- 반복문이나 조건문, 또는 중첩된 함수 안에서 훅을 호출하면 안된다.
    - Hook은 컴포넌트가 렌더링될 때마다 매번 같은 순서로 호출 되어야 하기 때문
    - 그래야 리액트가 다수의 useState와 useEffect 호출에서 컴포넌트의 state를 올바르게 관리할 수 있다.

```jsx
function MyComponent(props){
	const [name, setName] = useState("Inje");

	if(name !== ''){
		useEffect(() => {
			...
		});
	}
	...
}
```

- 위 예시는 잘못된 Hook 사용법으로 조건문이 참인 경우에만 useEffect를 사용하도록 되어 있다. 이런 경우는 조건문에 결과에 따라서 Hook의 호출 순서가 바뀌기 때문에 잘못된 것

1.  **리액트 함수 내에서 호출**
- 리액트 함수 컴포넌트에서만 Hook을 호출해야 한다. 일반적인 자바스크립트에서는 호출하면 안됨.

## eslint-plugin-react-hooks

[eslint-plugin-react-hooks](https://www.npmjs.com/package/eslint-plugin-react-hooks)

- Hook의 규칙을 따르도록 강제해주는 플러그인이다.
- 의존성 배열이 잘못되어 있을 때에도 경고 표시를 알려주고 어떻게 변경해줘야 하는지 알려준다.

## Custom Hook 만들기

- 여러 컴포넌트에서 반복적으로 사용되는 로직을 Hook으로 만들어 재사용하기 위함

**UserStatus**

```jsx
import React, {useState, useEffect} from "react";

function UserStatus(props){
	const [isOnline, setIsOnline] = useState(null);
	
	useEffect(() => {
		function handleStatusChange(status) {
			setIsOnline(status.isOnline);
		}
		
		ServerAPI.unsubscribeUserStatus(props.user.id, handleStatusChange);
		return ()=> {
			ServerAPI.unsubscribeUserStatus(props.user.id, handleStatusChange);
		}
	});

	if(isOnline === null){
		return '대기중 ...';
	}
	return isOnline ? "온라인" : "오프라인"; 

}
```

**UserListItem**

```jsx
import React, {useState, useEffect} from "react";

function UserListItem(props){
	const [isOnline, setIsOnline] = useState(null);
	
	useEffect(() => {
		function handleStatusChange(status) {
			setIsOnline(status.isOnline);
		}
		
		ServerAPI.unsubscribeUserStatus(props.user.id, handleStatusChange);
		return ()=> {
			ServerAPI.unsubscribeUserStatus(props.user.id, handleStatusChange);
		}
	});

	return (
		<li style={{ color: isOnline ? 'green' : 'black'}}>
			{props.user.name}
		</li>
	)
}
```

- 위 두 코드를 비교해봤을 때 useState와 useEffect를 사용하는 부분이 같은 것을 볼 수 있다. 두 코드 모두 사용자의 id여부에 따라서 isOnline을 바꾸고 해당 값으로 string을 return하거나 색을 바꿔 return해주는 함수이다.

## Custom Hook 추출하기

- 2개의 자바스크립트 함수에서 로직을 공유하고 싶을 때에는 새로운 함수를 생성해야 한다.
- 이름이 use로 시작하고 내부에서 다른 Hook을 호출하는 하나의 자바스크립트 함수
- 다른 함수(Hook)와 동일하게 Custom Hook또한 최상위 레벨에서만 호출해야 한다.

```jsx
import React, {useState, useEffect} from "react";

function useUserStatus(userId){
	const [isOnline, setIsOnline] = useState(null);
	
	useEffect(() => {
		function handleStatusChange(status) {
			setIsOnline(status.isOnline);
		}
		
		ServerAPI.unsubscribeUserStatus(props.user.id, handleStatusChange);
		return ()=> {
			ServerAPI.unsubscribeUserStatus(props.user.id, handleStatusChange);
		}
	});
	
	return isOnline;
}
```

- 별로 특별한 것은 없고 반복되는 로직을 그대로 함수화 시키면 된다.

## Custom Hook 사용하기

```jsx
import React, {useState, useEffect} from "react";

function UserStatus(props){
	const isOnline = userUserStatus(props.user.id);

	if(isOnline === null){
		return '대기중 ...';
	}
	return isOnline ? "온라인" : "오프라인"; 

}

function UserListItem(props){
	const isOnline = userUserStatus(props.user.id);

	return (
		<li style={{ color: isOnline ? 'green' : 'black'}}>
			{props.user.name}
		</li>
	)
}
```

- custom Hook의 이름은 반드시 use로 시작해야 한다.
    - 만약 이름이 use로 시작하지 않는다면 특정 함수 내부에서 Hook을 호출하는지 알 수 없음
    - 같은 커스텀 훅을 사용하는 내부에 있는 모든 컴포넌트의 State와 Effect는 전부 분리되어 있다.
    - 그렇다면 어떻게 Custom Hook이 이러한 Hook들을 분리시키는 것일까?
        - 각 Custom Hook 호출에 대해서 분리된 state를 얻게 된다.

## 실습 예제

**Accommodate.jsx**

```jsx
import React, { useState, useEffect } from "react";
import useCounter from "./useCounter";

const MAX_CAPACITY = 10;

function Accommodate(props) {
    const [isFull, setIsFull] = useState(false);
    const [count, increaseCount, decreaseCount] = useCounter(0);

    useEffect(() => {
        console.log("======================");
        console.log("useEffect() is called.");
        console.log(`isFull: ${isFull}`);
    });

    useEffect(() => {
        setIsFull(count >= MAX_CAPACITY);
        console.log(`Current count value: ${count}`);
    }, [count]);

    return (
        <div style={{ padding: 16 }}>
            <p>{`총 ${count}명 수용했습니다.`}</p>

            <button onClick={increaseCount} disabled={isFull}>
                입장
            </button>
            <button onClick={decreaseCount}>퇴장</button>

            {isFull && <p style={{ color: "red" }}>정원이 가득찼습니다.</p>}
        </div>
    );
}

export default Accommodate;
```

- 일단 기본적으로 useCounter라는 Custom Hook을 사용해서 count, incerateCount, decreaseCount의 값을 불러온다.
- **useEffect**: 첫번째 useEffect의 경우 별도의 의존성 변수가 없기 때문에 재 렌더링 될 때마다 실행이 된다.
- **useEffect** : state의 경우 현재 방이 가득 찼는지를 확인하기 위해 존재. 만약 count가 MAX_CAPACITY보다 클 경우 true가 된다. 해당 값을 실시간 반영을 하기 위해서 의존성 변수를 count로 주고 count값이 변경될 때마다 setIsFull이 실행되도록 함
- disabled option에 값을 isFull을 넣어줌으로써 만약 현재 방이 가득 찼을 경우 disabled시킴

**useCounter.jsx**

```jsx
import React, { useState } from "react";

function useCounter(initialValue) {
    const [count, setCount] = useState(initialValue);

    const increaseCount = () => setCount((count) => count + 1);
    const decreaseCount = () => setCount((count) => Math.max(count - 1, 0));

    return [count, increaseCount, decreaseCount];
}

export default useCounter;
```

**index.jsx**

```jsx
import Accommodate from './demo4(Hook)/Accommodate';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <Accommodate />
  </React.StrictMode>,
  document.getElementById('root')
);
```
