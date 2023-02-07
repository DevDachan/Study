# 5. Elements Redering

## Element란?

- react 앱을 구성하는 요소로 가장 작은 블록 단위라고 생각하면 된다.
- 원래 대로라면 Element는 웹 사이트에 대한 모든 정보를 담고 있는 객체인 DOM에서 사용하는 용어이다.
- 우리가 크롬의 검사 탭을 들어갔을 때 HTML에 관한 값이 나오는 탭이 Elements임을 봐도 element들을 모아 놓은 것이다. 하지만 여기서 말하는 element는 DOM element이다.

![Untitled](5%20Elements%20Redering%20ec192d6499b248f5af9bb0b8291b143f/Untitled.png)

## DOM Elements VS React Elements

- 화면에 나타나는 내용을 기술하는 자바스크립트 객체. React가 초창기에는 이러한 객체를 Descriptor라고 불렀지만 결과적으로 웹 페이지에 나타나는 형태는 DOM Elements이기 때문에 Element라고 부르기로 결정했다.

![Untitled](5%20Elements%20Redering%20ec192d6499b248f5af9bb0b8291b143f/Untitled%201.png)

- 가장 쉽게 표현 하면 React Elements는 DOM Element를 가상으로 표현한 것이라고 생각하면 된다.
- 그렇기 때문에 DOM Elements는 React Element에 비해 많은 정보를 담고 있기 때문에 상대적으로 크고 무겁다.
- React Element는 화면에서 보이는 것들을 기술한다.
    
    ```jsx
    const element = <h1>Hello, world</h1>;
    ```
    
    - 우리가 이렇게 React Element를 선언해 사용하게 되면 React에서는 이것을 실제 화면에서 보게 되는 DOM Elements로 생성해준다.
    

## Element의 생김새

- React Elemnts는 자바스크립트 객체 형태로 존재한다.
- 이러한 객체는 한번 생성되면 바꿀 수 없는 불변성을 가지고 있다.
    
    ```jsx
    * React Element
    
    {
    	type: "button", 
    	props: {
    		className: "bg-green",
    		children: {
    			type: "b",
    			props: {
    				children: "Hello, elements!"
    			}
    		}
    	}
    }
    
    ## 이렇게 type에는 단순히 HTML 태그 뿐만 아니라 React Element가 들어가도 상관 없다
    {
    	type: Button, 
    	props: {
    		color: "green",
    		children: "Hello, element!"
    	}
    }
    
    * DOM Elements
    
    <button class="bg-freen">
    	<b>
    		Hello, element!
    	</b>
    </button>
    ```
    
    - **type**: HTML Tag or React Component
    - **props**: 간단하게 생각하면 element의 속성 (class, style과 같은 attributes의 상위 값)
    - **children**: 여러 개의 자식 element
- **Element 선언**
    
    ```jsx
    function Button(props){
    	return (
    		<button className={`bg-${props.color}`}>
    			<b>
    				{props.children}
    			</b>
    	)
    }
    
    function ConfirmDialog(props){
    	return(
    		<div>
    			<p><내용 확인후 확인버튼 누르기/p>
    			<Button color="green"> 확인 </Button>
    		</div>
    	)
    }
    ```
    
- **Element 구조**
    
    ```jsx
    {
    	type: 'div',
    	props: {
    		children [
    			{
    				type: 'p',
    				props: {
    					children: "내용을 확인했으면 확인 버튼" 
    			},{
    				type: Button,
    				props: {
    					color: 'green',
    					children: "확인" 
    			}
    		]
    	}
    }
    ```
    

## Element 특성

- immutable(불변성): 한번 생성된 Element는 생성 후에 children이나 attributes를 바꿀 수 없다.
    - 그렇다면 화면 갱신이 이루어지지 않는가? → Element 생성 후에 바꿀 수 없는 것
    - ex) 붕어빵이 기계에 들어가면 구워져 나오는데 붕어빵 내부 재료는 바꿀 수 없다. 그렇기 때문에 새로운 붕어빵을 보여주기 위해서는 새로운 붕어빵을 구워야 한다.
        
        ![Untitled](5%20Elements%20Redering%20ec192d6499b248f5af9bb0b8291b143f/Untitled%202.png)
        
    - 마찬가지로 화면에 새로운 내용을 보여주기 위해서는 기존의 Element를 수정하는 것이 아니라 새로운 Element를 만들어 기존 Element를 대체 해줘야 한다.
        
        ![Untitled](5%20Elements%20Redering%20ec192d6499b248f5af9bb0b8291b143f/Untitled%203.png)
        

## Element 렌더링하기

- React Element가 화면에 보여지기 위해서는 반드시 렌더링 과정을 거쳐야 한다.
- Root DOM Node: 모든 react element는 root DOM에 연결이 된다.
    
    ```jsx
    <div id="root"></div>
    ```
    
    ![Untitled](5%20Elements%20Redering%20ec192d6499b248f5af9bb0b8291b143f/Untitled%204.png)
    
- Root DOM이 element를 실제 DOM에 렌더링 하기 위해서는 아래와 같은 함수를 실행해줘야 한다.
    
    ```jsx
    const element = <h1>안녕!</h1>;
    ReactDOM.render(element, document.getElementById('root'));
    ```
    
    - ex) 매 초 시간이 바뀌는 tick함수: 매 초마다 tick함수를 실행시켜주면서 기존 Element를 교체해주는 작업을 하는 것
        
        ```jsx
        function tick(){
        	const element = (
        		<div>
        			<h1> 안녕! </h1>
        			<h2> 현재 시간: {new Date().toLocaleTimeString()}</h2>
        		</div>
        	);
        	ReactDOM.render(element, document.getElementById('root'));
        }
        
        setInterval(tick, 1000);
        ```
        

## 실습 예제

**Clock.jsx**

```jsx
import React from "react";

function Clock(){
	const element = (
		<div>
			<h1> 안녕! </h1>
			<h2> 현재 시간: {new Date().toLocaleTimeString()}</h2>
		</div>
	);
	ReactDOM.render(element, document.getElementById('root'));
}

export default Clock;
```

**index.js**

```jsx
import Clock from './demo2(clock)/Clock'

setInterval(() => {
  ReactDOM.render(
    <React.StringMode>
      <Clock />
    </React.StringMode>
    document.getElementById('root')
  );
},1000);
```