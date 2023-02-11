# 15. Context

## Context란?

- 기존에는 컴포넌트의 props를 통한 단방향 데이터를 전달했다. 이러한 방식은 구조가 복잡해질 경우 데이터 접근이 복잡해진다.

![Untitled](15%20Context%20c49705e78bb944f1815b826332dd7890/Untitled.png)

- 컴포넌트 트리를 통해 새로운 방식으로 데이터를 전달해서 쉽게 데이터에 접근이 가능하도록 함
    
    ![Untitled](15%20Context%20c49705e78bb944f1815b826332dd7890/Untitled%201.png)
    

## 언제 Context를 사용하는가?

- 여러 컴포넌트에서 자주 사용하는 정보
    - 로그인 여부, 로그인 정보, UI 테마, 현재 언어등
    - 아래와 같은 예시를 봤을 때 같은 데이터를 사용하지만 컴포넌트의 구조 때문에 계속해서 props를 넘겨줘야 한다.
        
        ```jsx
        function App(props){
        	return <Toolbar thene="dark">
        }
        
        function Toolbar(props){
        	return (
        		<div>
        			<ThemedButton theme={props.theme} />
        		</div>
        	);
        }
        
        function ThemeButton(props){
        	return <Button theme={props.theme} />
        }
        ```
        
    - Context 사용 예시
        
        ```jsx
        const ThemeContext = React.createContext('light');
        
        function App(props){
        	return (
        		// Provider를 사용해서 하위 컴포넌트들에게 현재 테마 데이터를 전달하게 된다.
        		// 이러한 Provider 값은 깊이에 상관없이 사용이 가능함
        		<ThemeContext.Provider value="dark">
        			<Toolbar />
        		</ThemeContext>
        	);
        }
        
        function Toolbar(props){
        	return (
        		<div>
        			<ThemedButton />
        		</div>
        	);
        }
        
        function ThemeButton(props){
        	return (
        		// 리액트는 기본적으로 가장 가까운 상위 테마 Provider를 찾아 해당 값을 사용한다.
        		// 여기에서는 상위 Provider가 있기 때문에 현재 테마의 값을 'dark'로 사용하고 해당 Provider가
            // 없을 경우에는 기본값'light'를 사용한다.
        		<ThemeContext.Consumer>
        		{ value => <Button theme={value} />}
        		</ThemeContext.Consumer>
        	);
        }
        ```
        
    

## Context 사용 고려할 점

- Context와 Component가 연동되면 재사용성이 떨어지기 때문에 많이 사용하면 좋지 않다.
- 때문에 여러 컴포넌트에서 사용하는 정보가 아니라면 Composition방식이 더 적합하다.
- 가장 하위에 존재하는 Avatar컴포넌트가 user와 avatarSize를 필요하기 때문에 여러 단계에 걸쳐 전달해주고 있다.

```jsx
// Page 컴포넌트는 PageLayout 컴포넌트를 렌더링
<Page user={user} avatarSize={avatarSize} />

// PageLayout컴포넌트는 NavigationBar 컴포넌트를 렌더링
<PageLayout user={user} avatarSize={avatarSize} />

// NavigationBar컴포넌트는 Link 컴포넌트를 렌더링
<NavigationBar user={user} avatarSize={avatarSize} />

// Link 컴포넌트는 Avatar 컴포넌트를 렌더링
<Link href={user.permalink}>
	<Avatar user={user} avatarSize={avatarSize} />
</Link>
```

- 이런 식으로 Element variable 형태로 우리가 원하는 컴포넌트를 저장하고 해당 변수를 넘겨주면 식이 더 간단해진다.

```jsx
function Page(props){
	const user = props.user;

	const userLink = (
		<Link href={user.permalink}>
			<Avatar user={user} size={props.avatarSize} />
		</Link>
	);
	
	return <PageLayout userLink={userLink} />;
}

<PageLayout userLink={...} />

<NavigationBar userLink={...} />
```

- 하위 컴포넌트를 여러 개의 변수로 나눠서 전달
    - 하나의 데이터에 다양한 레벨에 있는 중첩된 컴포넌트가 접근 할 경우는 아래와 같이 사용하면 안되고 Context를 사용해야 한다.

```jsx
function Page(props){
	const user = props.user;

	const toBar = (
		<NavigationBar>
			<Link href={user.permalink}>
				<Avatar user={user} size={props.avatarSize} />
			</Link>
		</Navigation>
	);
	
	const content = <Feed user={user} />

	return (
		<PageLayout 
			topbar={topBar}
			content={content}
	 />
	
	);
}
```

# Context API

## React.createContext()

- Context를 생성해주는 함수
- 리액트에서 렌더링을 할 때 Context 객체를 구독하는 하위 컴포넌트가 나오면 현재 컨택스트의 값을 가장 가까이 있는 Provider에서 받아오게 된다.
- 만약 상위 레벨에 매칭되는 Provider가 없다면 기본 값이 사용된다.
- 기본값으로 undefined를 넣으면 기본 값이 사용되지 않는다.

```jsx
const MyContext = React.cerateContext(기본값);
```

## Context.Provider

- 제공자의 의미로 데이터를 제공해주는 컴포넌트의 의미를 가지게 된다.
- 기본적으로 value라는 porps이 있으며 여기에 입력되는 값이 하위 컴포넌트에게 제공 된다.
    
    ```jsx
    <MyContext.Provider value={/* some value */}
    ```
    
- 하나의 Provider에는 여러개의 Consuming 컴포넌트가 연결 될 수 있으며 여러 개의 Provider는 중첩되어 사용하는 것이 가능하다.
- **Consuming 컴포넌트**: Provider의 Context value를 사용하는 컴포넌트로써 Context값의 변화를 지켜보다가 값이 변경되면 재 렌더링 된다.
    - 상위 컴포넌트가 값이 변경 되었을 때 업데이트 대상이 아니더라도 하위 컴포넌트에서 Context를 사용하고 있다면 하위 컴포넌트는 업데이트가 일어난다.

## Provider value에서 주의해야 할 사항

- Provider 컴포넌트가 재 렌더링 될 때마다 모든 하위 consumer 컴포넌트가 재 렌더링 됨
    - 만약 불필요한 재 렌더링을 막기 위해서는 value의 값을 바뀔 수 있는 값이 아닌 State를 사용해서 값을 넣어줘야 한다.
        
        ```jsx
        function App(props){
        	const [value, setValue] = useState({ something: 'something' });
        
        	return (
        		<MyContext.Provider value={value}>
        			<Toolbar />
        		</MyContext.Provider>
        	);
        }
        ```
        
    

## Class.contextType

- Provider 하위에 있는 클래스 컴포넌트에서 컨텍스트에 데이터에 접근하기 위해서 사용하는 함수이다.
- 현재는 많이 사용되지 않기 때문에 그냥 있다고만 알면 된다.
    
    ```jsx
    class MyClass extends React.Component {
    	componentDidMount(){
    		let value = this.context;
    	}
    	componentDidUpdate(){
    		let value = this.context;
    	}
    	componentWillUnmount(){
    		let value = this.context;
    	}
    	render(){
    		let value = this.context;
    	}
    }
    MyClass.contextType = MyContext;
    ```
    
    - 이런 식으로 Class에 마지막에 contextType을 선언해줌으로써 MyContext라는 데이터에 접근이 가능해진다.
    - 이렇게 접근이 가능해지면 this.context를 통해서 가장 가까운 상위 provider에서 값을 가져오게 된다.

## Context.Consumer

- Context의 데이터를 구독하는 컴포넌트이다.
- 클래스 컴포넌트에서는 위에서 나온 것과 같이 Class.context를 사용하면 된다.

```jsx
<MyContext.Consumer>
	{value => 컨텍스트 값에 따라 컴포넌트 렌더링}
</MyContext.Consumer>
```

- Function as a Child: 컴포넌트의 자식으로는 함수가 올 수 있다.
    - 자식으로 들어간 함수가 현재 컨텍스트의 value를 받아서 리액트 노드로 리턴을 해준다.
    - 이때 함수로 전달되는 value는 Provider의 value props과 동일하다.

## Function as a Child

```jsx
// children이라는 prop을 직접 선언하는 방식
<Profile children={name => <p>이름: {name} </p>} />

// Profile 컴포넌트로 감싸서 children으로 만드는 방식
<Profile>{name => <p>이름: {name} </p>} </Profile>
```

- 그냥 이렇게 리액트의 컴포넌트에서 children과 같은 속성에 값을 넣는 것 대신 함수를 넣어서 사용하는 것을 말한다.

## Context.displayName

- Context 객체가 가지는 문자열 속성으로써 크롬에서는 이 displayName으로 개발자도구에 표시가 된다.

```jsx
const MyContext = React.createContext(/* some value */);
MyContext.displayName = 'MuDisplayName';

// 개발자 도구에 "MyDisplayName.Provider"로 표시됨
<MyContext.Provider>
// 개발자 도구에 "MyDisplayName.Consumer"로 표시됨
<MyContext.Consumer>

```

## 여러 개의 Context 사용하기

- 앞에서 클래스 컴포넌트의 경우에는 Class.context를 사용하면 한번에 하나의 컨텍스트만 사용이 가능하다고 했는데 그렇다면 여러개의 context를 사용하려면?
    - Context.Provider를 중첩해서 사용해야 한다.

```jsx
const ThemeContext = REact.createContext('light');

const UserContext = REact.createContext({
	name: 'Guest',
});

class App extends React.Component{
	render(){
		const {signedInUser, theme} = this.props;

		return (
			<ThemeContext.Provider value={theme}>
				<UserContext.Provider vlaue={signedInUser}>
					<Layout />
				</UserContext>
			</ThemeContext.Provider>
		);
	}
}

function Layout(){
	return(
		<div>
			<Sidebar />
			<Context />
		</div>
	);
}

function Content(){
	return (
		<ThemeContext.Consumer>
			{theme => (
					<UserContext.Consumer>
							{user => (
								<ProfilePage user={user} theme={theme} />
							)}
					</UserContext.Consumer>
			)}
		</ThemeContext.Consumer>
	);
}
```

- ThemeContext와 UserContext 두개의 context가 존재하는데 App 컴포넌트에서 각 Context에 대해 두개의 Provider를 사용해 자식 컴포넌트인 Layout을 감싸고 있다.
- 실제 Context를 사용하는 Content 컴포넌트에서는 2개의 Consumer를 사용해 데이터를 전달 받고 있다.
- 하지만 2개 이상의 컨텍스트 값이 자주 함께 사용 될 경우 모든 값을 한번에 제공해주는 별도의 Render prop Component를 직접 만드는 것을 고려하는게 좋다.

## useContext()

- Context를 사용하면서 매번 Provider, Consumer를 통해 감싸주는 것보다 더 편하게 Hook을 사용하면 된다.
    
    ```jsx
    function MyComponent(props){
    	const value = useContext(MyContext);
    
    	return (
    		...
    	)
    }
    ```
    
    - React.createContext() 함수로 생성된 컨텍스트 객체를 인자로 받아 현재 컨텍스트의 값을 리턴한다.
- 이렇게 useContext를 사용하면 다른 방식과 동일하게 상위 Provider에서 값을 가져오게 되고 만약 컨텍스트 값이 변경되면 변경된 값과 함께 useContext를 사용하는 컴포넌트가 재 렌더링이 된다.
- useContext를 사용할 때에는 꼭 Parameter로 Context 객체를 넣어줘야 한다.
    
    ```jsx
    // 올바른 사용법
    useContext(MyContext);
    
    // 잘못된 사용법
    userContext(MyContext.Consumer);
    userContext(MyContext.Provider);
    ```
    

## 실습 예제

**DarkOrLight.jsx**

```jsx
import { useState, useCallback } from "react";
import ThemeContext from "./ThemeContext";
import MainContent from "./MainContent";

function DarkOrLight(props) {
    const [theme, setTheme] = useState("light");

    const toggleTheme = useCallback(() => {
        if (theme == "light") {
            setTheme("dark");
        } else if (theme == "dark") {
            setTheme("light");
        }
    }, [theme]);

    return (
        <ThemeContext.Provider value={{ theme, toggleTheme }}>
            <MainContent />
        </ThemeContext.Provider>
    );
}

export default DarkOrLight;
```

**MainContext.jsx**

```jsx
import { useContext } from "react";
import ThemeContext from "./ThemeContext";

function MainContent(props) {
    const { theme, toggleTheme } = useContext(ThemeContext);

    return (
        <div
            style={{
                width: "100vw",
                height: "100vh",
                padding: "1.5rem",
                backgroundColor: theme == "light" ? "white" : "black",
                color: theme == "light" ? "black" : "white",
            }}
        >
            <p>안녕하세요, 테마 변경이 가능한 웹사이트 입니다.</p>
            <button onClick={toggleTheme}>테마 변경</button>
        </div>
    );
}

export default MainContent;
```

- MainContent에서는 상위 컴포넌트에서 toggleTheme를 입력 받아 테마를 적용시키는 컴포넌트이다.
- DarkOrLight가 실제로 어떤 색상을 적용할 지를 정하는 컴포넌트이고 이 DarkOrLight에서 버튼을 통해 색을 변경시키는 함수 또한 Context로 함수를 전달받아 onClick에 넣을 수 있다.

**index.jsx**

```jsx
import DarkOrLight from './demo11(Context)/DarkOrLight';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <DarkOrLight />
  </React.StrictMode>,
  document.getElementById('root')
);
```

- 전체적인 구조를 봤을 때 이전에 NodeJS나 JSP에서 사용하던 것과는 조금 다른 구조임을 알 수 있다.
- 이전에는 내가 특정 컴포넌트를 만든다 가정 했을 때 실제로 화면에 표시되는 부분이 트리에서 가장 상위 부분에 위치하는 하향식 구조였다면 리액트는 반대로 상향식 구조를 가지고 있다.