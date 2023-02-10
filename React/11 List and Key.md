# 11. 리스트와 키

## List란?

- 목록이라는 뜻으로 같은 item을 순서대로 모아 놓은 것이다.
- Array가 리스트에 해당하고 Array란 JavaScript의 변수나 객체들을 하나의 변수로 묶어 놓은 것을 말한다.
    
    ```jsx
    const numbers = [1,2,3,4];
    
    <li> </li>
    ```
    
- 여기서 한가지 명시해야 할 점은 실제 HTML 태그와 React에서 사용되는 Element는 같은 형태를 띄고 있지만 각각의 속성을 넣는 방법이 약간씩 다르다.

## Key란?

- 각 객체나 item을 구분할 수 있는 고유한 값
- React에서는 item을 구분하기 위한 고유한 문자열
- key의 값은 같은 list에 있는 Elements 사이에서만 고유한 값이면 된다.


<img src="https://user-images.githubusercontent.com/111109411/217731518-d096f324-820e-4503-8f5d-a939972e495e.png" width=60%>

## map()

- 배열에 들어있는 특정 값에 처리를 한 후 return해주는 함수

```jsx
const doubled = numbers.map((number) => number *2);
```

- map()를 사용하여 배열에 Element를 렌더링 하는 예시

```jsx
const numbers = [1,2,3,4,5];
const listItems = numbers.map((number) => <li>{number}</li>);

ReactDOM.render(
	<ul>{listItems}</li>
	document.getElementById('root')
);
```

## 기본적인 List Component (<li>)

```jsx
function NumberList(props){
	const {numbers} = props;

	const listItems = numbers.map((number) => <li>{number}</li>);

	return ( <ul>{listItems}</ul> );
}

	const numbers = [1,2,3,4,5]

	ReactDOM.render(
		<NumberList numbers={numbers} />
		document.getElementById('root')
	)
```

- 위와 같이 코드를 작성 할 경우 오류가 발생한다.
    - 리스트 안에 있는 값들은 반드시 key값을 포함하고 있어야 하기 때문이다. 현재는 단순히 item들만 추가되어 있는 상태임

## List Key 설정 방법

- key로 값을 사용하는 경우 (만약 중복된 숫자가 있을 경우에는 고유한 값이 아니기에 경고)
    
    ```jsx
    const numbers = [1, 2, 3, 4, 5];
    const listItems = numbers.map((number) => 
    	<li key={number.toString()}>
    		{number}
    	</li>
    );
    ```
    
- key로 id를 사용하는 경우 (id의 의미는 고유하기 때문에 key값으로 적합함)
    
    ```jsx
    const todoItems = dotos.map((todo) => 
    	<li key={todo.id}>
    		{todo.text}
    	</li>	
    );
    ```
    
- key로 index를 사용하는 경우 (index의 경우에도 해당 배열 내에서 고유한 값이기 때문에 적합하다. 하지만 item의 순서가 바뀔 수 있는 경우에는 적합하지 않음)
    
    ```jsx
    const todoItems = todos.map((todo, index) => 
    	<li key={index}>
    		{todo.text}
    	</li>
    );
    ```
    
- 참고로 React에서는 만약 key값을 넣어주지 않을 경우에는 기본적으로 index를 key값으로 사용하게 된다.
- 결국 map()함수 안에 있는 Elements는 꼭 key가 필요하다.

## 실습 예제

**AttendanceBook.jsx**

```jsx
import React from "react";

const students = [
    {
        id: 1,
        name: "Inje",
    },
    {
        id: 2,
        name: "Steve",
    },
    {
        id: 3,
        name: "Bill",
    },
    {
        id: 4,
        name: "Jeff",
    },
];

function AttendanceBook(props) {
    return (
        <ul>
            {students.map((student, index) => {
                return <li key={student.id}>{student.name}</li>;
            })}
        </ul>
    );
}

export default AttendanceBook;
```

- 위 예제에서 id가 key값에 해당한다.
- key값은 실제 HTML에서는 나타나지 않음

**index.js**

```jsx
import AttendanceBook from './demo7(list_key)/AttendanceBook';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <AttendanceBook />
  </React.StrictMode>,
  document.getElementById('root')
);
```
