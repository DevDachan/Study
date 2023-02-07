# 7. State and Lifecycle

## State

- 일반적으로 상태라는 뜻을 가지는데 리액트에서는 Component의 상태를 나타낸다.
- 이때 말하는 상태는 컴포넌트의 데이터라는 의미에 가깝고 변경 가능한 데이터를 말한다.
- 이러한 state는 개발자가 정의한다.
- 꼭 렌더링이나 데이터 흐름에 사용되는 값만 state에 포함시켜야 한다.
- 복잡한 형태는 아니고 JavaScript 형식이다.

```jsx
class LikeButton extends React.Component{
	constructor(props){
		super(props);
		
		this.state = {
			liked: false
		};
	}
}
```

- 클래스 컴포넌트의 경우 state를 constructor(생성자)에서 정의가 된다.

```jsx
thisstate = {
	name: "inJe"
};

this.setState({
	name: "dachan"
});
```

- state는 직접 수정할 수 없고 수정을 해서는 안된다.
- state를 마음대로 수정하게 되면 프로그램이 정상적으로 실행되지 않을 가능성이 있다. 그렇기 때문에 꼭 setState를 사용해서 수정해야 한다.

## Lifecycle

- 리액트 컴포넌트는 생명주기가 정해져 있고 일정 시간이 지나면 지워진다.

![Untitled](7%20State%20and%20Lifecycle%20037e5262951749628ecd4a83562ecc1e/Untitled.png)

- 출생: constructor에서 컴포넌트가 생성이 된다.
- 인생: new props, setState(), forceUpdate() 등의 method를 통해서 컴포넌트의 업데이트가 이뤄진다.
- 사망: 상위 컴포넌트가 더 이상 표시되지 않게 됐을 때 사망하게 된다.
- 컴포넌트는 계속 존재하는 것이 아니라 시간의 흐름에 따라 생성되고 업데이트 되다가 사라진다.