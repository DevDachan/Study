# 12. 폼

## 폼(form)이란?

- 사용자로부터 입력을 받기 위해 사용
- 리액트의 폼과 HTML의 폼은 다르다.
    - 리액트는 컴포넌트 내부에서 state를 통해 데이터를 관리한다.
    - HTML은 Element 내부에 각각의 state가 존재한다.
    - **HTML form**
        
        ```jsx
        <form>
        	<label>
        		이름: 
        		<input type="text" name="name">
        	</label>
        	<button type="submit">제출</button>
        </form>
        ```
        
        - 위 코드는 리액트에서 잘 작동하지만 자바스크립트를 통해 사용자가 입력한 값에 접근하기는 불편한 구조이다.
    

## Controlled Components

- 사용자가 입력한 값에 접근하고 제어할 수 있도록 해주는 컴포넌트
- 리액트의 통제를 받는 컴포넌트 (Input Form Element)
    
<img src="https://user-images.githubusercontent.com/111109411/217731617-98dc4168-8f77-407c-80b1-ae76c2509213.png" width=60%>

    
    - HTML form으로 관리할 경우 각각의 state를 자체적으로 관리하게 되지만 Controlled Component를 사용하게 되면 모든 데이터를 하나의 state에서 관리가 가능하며 setState()로만 수정이 가능해진다.

## Input 태그

```jsx
function NameForm(props){
	const [value, setValue] = uesState('');

	const handleChange = (event) => {
		setValue(event.target.value);	
	}

	const handleSubmit = (event) => {
		alert('입력한 이름: '+ value);
		event.preventDefault();
	}

	return (
		<form onSubmit={handleSubmit}>
			<label>
				이름: 
				<input type="text" value={value} onChange={handleChange} />
			</label>
			<button type="submit">제출</button>
		</form>
	)
}
```

- input의 value는 state값이므로 항상 state값이 input에 표시된다.
- handleChange: onChange시 호출되는 함수로써 setValue를 사용함으로써 새롭게 변경된 값을 value에 저장하게 된다.
    - event: event객체를 나타낸다.
    - target: 현재 발생한 event의 target (input element)
    - value: 값 (input의 value)
- 이처럼 Controlled Component를 사용하면 사용자의 입력을 직접적으로 제어할 수 있음
    - 쉽게 말해 사용자가 특정 입력을 했을 경우 다른 값을 변경하는 것이 가능한 것이다.
- 모든 입력 값을 대문자로 변경

```jsx
const handleChange= () => {
	setValue(event.target.value.toUpperCase());
}
```

## Textarea 태그

- **HTML textarea:** 여러 줄에 걸쳐 긴 텍스트를 입력 받기 위한 HTML 태그
    
    ```jsx
    <textarea>
    	안녕하세요, 여기에 긴 텍스트가 들어갑니다.
    </textarea>
    ```
    
- **React textarea**: 이전 예시와는 다르게 state의 초기 값을 설정해줌으로써 처음부터 값이 출력 된다.
    
    ```jsx
    function RequestFrom(props){
    	const [value, setValue] = useState('요청사항을 입력하세요.');
    
    	const handleChange = (event) => {
    		setValue(event.target.value);
    	}
    	
    	const handleSubmit = (event) => {
    		alert("입력한 요청사항: " + value);
    		event.preventDefault();
    	}
    
    	return (
    		<form onSubmit={handleSubmit}>
    			<label>	
    				요청사항:
    				<textarea value={value} onChange={handleChange} />
    			</label>
    		</form>
    	)
    }
    ```
    

## Select 태그

- **HTML Select**: Drop-down 목록을 보여주기 위한 HTML 태그
    
    ```jsx
    <select>
    	<option value="apple">사과</option>
    	<option value="banana">바나나</option>
    	<option selected value="grape">포도</option>
    	<option value="watermelon">수박</option>
    </select>
    ```
    
- **React Select:** React에서는 selected를 하기 위해서 각각의 option에서 속성을 부여하는 것이 아니라 Select에서 선택을 하게 된다.
    
    ```jsx
    function FruitSelect(props){
    	const [value, setValue] = useState('grape');
    
    	const handleChange = (event) => {
    		setValue(event.target.value);
    	}
    	
    	const handleSubmit = (event) => {
    		alert("선택한 과일: " + value);
    		event.preventDefault();
    	}
    
    	return (
    		<form onSubmit={handleSubmit}>
    			<label>	
    				과일을 선택하세요
    				<select value={value} onChange={handleChange}>
    					<option value="apple">사과</option>
    					<option value="banana">바나나</option>
    					<option value="grape">포도</option>
    					<option value="watermelon">수박</option>
    				</select>
    			</label>
    		</form>
    	)
    }
    ```
    
    - 위 코드와 같이 설정을 하게 되면 만약 값이 선택이 됐을 때 state하나만 바꿔주면 되므로 편리하다.
- 여러 개의 옵션도 선택 가능
    
    ```jsx
    <select multiple={true} value={['B', 'C']}>
    ```
    

## Controlled Component정리

```jsx
// input 태그
<input type="text" value={value} onChange="handleChange" />

// textarea 태그
<textarea value={value} onChange={handleChange} />

// select 태그
<select value={value} onChange={handleChange} />
	<option value="apple">사과</option>
	<option value="banana">바나나</option>
	<option value="grape">포도</option>
	<option value="watermelon">수박</option>
</select>

```

## File Input 태그

- 디바이스의 저장 장치로부터 하나 또는 여러개의 파일을 선택할 수 있게 해주는 HTML 태그
- HTML file input 태그
    
    ```jsx
    <input type="file" />
    ```
    
- file input 태그는 기본적으로 읽기 전용이기 때문에 react에서는 uncontrolled Component가 된다. 즉 값이 React의 통제를 받지 않음
- 여러 개의 입력을 받기 위해서는 여러개의 state를 선언하여 각각의 입력에 대해 사용해야 한다.

```jsx
function Reservation(props){
	const [haveBreakfast, setHaveBreakfast] = uesState(true);
	const [numberOfGuest, setNumberOfGuest] = uesState(2);

	const handleSubmit = (event) => {
		alert('아침식사 여부: ${haveBreakfast}, 방문객 수: ${numberOfGuest} ';
		event.preventDefault();
	}

	return (
		<form onSubmit={handleSubmit}>
			<label>
				아침식사 여부: 
				<input type="checkBox" 
					checked={haveBreakfast} 
					onChange={(event) => {
							setHaveBreakfast(event.target.checked);
				}}/>
			</label>
			<button type="submit">제출</button>
			<br />
			<label>
				방문객 수: 
					<input type="checkBox" 
						checked={haveBreakfast} 
						onChange={(event) => {
								setNumberOfGuest(event.target.checked);
						}}/>
			</label>
			<button type="submit">제출</button>
		</form>
	)
}
```

## Input Null Value

- 만약 Element의 value에 Null값을 넣으면 input의 값을 수정할 수 있는 상태가 된다.
- 기본적으로 한번 생성이 되면 값을 변경하는 것이 되지 않는다.

```jsx
ReactDOM.render(<input value="hi" />, rootNode);

setTimeout(function(){
	ReactDOM.render(<input value={null} />, rootNode)
}, 1000)
```
