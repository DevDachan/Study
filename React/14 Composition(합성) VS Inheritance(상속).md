# 14. Composition(합성) VS Inheritance(상속)

## Composition(합성)이란?

- 여러 개의 컴포넌트를 합쳐서 새로운 컴포넌트를 생성하는 것
- composition에는 여러개의 방법이 존재한다.

### 1. Containment

- 하위 컴포넌트를 포함하는 형태의 Composition
- Sidebar나 Dialog같은 Box형태의 컴포넌트는 자신의 하위 컴포넌트를 미리 알 수 없다.
- children이라는 props를 사용함
    
    ```jsx
    function FancyBorder(props){
    	return(
    		<div className={'FancyBorder FancyBorder-' + props.color}>
    			{props.children}
    		</div>
    	);
    }
    ```
    
    - 해당 props의 하위 컴포넌트가 모두 children으로 들어오게 된다.
    - children은 개발자가 직접 넣는 것이 아니라 React에서 넣어주는 값이다.
    - 결과적으로 FancyBorder 컴포넌트는 자신의 하위 컴포넌트를 모두 포함하여 예쁜 테두리로 감싸주는 컴포넌트가 된다.
        
        ```jsx
        function WelcomDialog(props){
        	return (
        		<FancyBorder color="blue">
        			<h1 className="Dialog-title">
        				어서오세요
        			</h1>
        			<p className="Dialog-message">
        				우리 사이트에 방문하신 것을 환영합니다!
        			</p>
        		</FancyBorder>
        	)
        }
        ```
        
- 여러 개의 children 집합이 필요한 경우?
    - 별도로 props를 정해서 각각 원하는 컴포넌트를 넣어주면 된다.
    
    ```jsx
    function SplitPane(props){
    	return (
    		<div className="SplitPane">
    			<div className="SplitPane-left">
    				{props.left}
    			</div>
    			<div className="SplitPane-right">
    				{props.right}
    			</div>
    		</div>
    	);
    }
    function App(props){
    	return (
    		<SplitPane>
    			left={
    				<Contacts />
    			}
    			right={
    				<Chat />
    			}
    		/>
    	);
    }
    ```
    
    - 각각 화면의 왼쪽과 오른쪽에 배치하도록 사용하기 위해서 children의 집합을 분리시켜 사용하기
    

### 2. Specialization

- WelcomeDialog는 Dialog의 특별한 케이스다. Dialog는 수많은 형태가 있는데 그중 WelcomeDialog는 누군가를 반기기 위한 Dialog이다.
- 이처럼 범용적인 개념을 구별이 되게 구체화하는 것을 Specialization이라고 한다.
- 기존의 객체지향 언어에서는 상속을 사용하여 Specialization을 구현한다.
- 리액트에서는 합성을 사용하여 Specialization을 구현한다.
    
    ```jsx
    function Dialog(props){
    	return (
    		<FancyBorder color="blue">
    			<h1 className="Dialog-title">
    				{props.title}			
    			</h1>
    			<p className="Dialog-message">
    				{props.message}	
    			</p>
    		</FancyBorder>	
    	);
    }
    
    function WelcomeDialog(props){
    	return(
    		<Dialog
    			title="어서오세요"
    			message="우리 사이트에 방문하신 것을 환영합니다."
    		/>
    	);
    }
    ```
    
    - 위에 보이는 것처럼 Dialog라는 범용적인 컴포넌트를 만들고 이후 구체화된 컴포넌트인 WelcomeDialog를 만들게 된다.
    

## Containment와 Specialization을 같이 사용하기

```jsx
function Dialog(props){
	return (
		<FancyBorder color="blue">
			<h1 className="Dialog-title">
				{props.title}			
			</h1>
			<p className="Dialog-message">
				{props.message}	
			</p>
		{props.children}
		</FancyBorder>	
	);
}

function SignUpDialog(props){
	const [nickname, setNickname] = useState('');

	const handleChange = (event) => {
		setNickname(event.target.value);
	}

	return(
		<Dialog
			title="어서오세요"
			message="닉네임을 입력해 주세요."
		/>
		<input
			value={nickname}
			onChange={handleChange} />
		<button onClick={handleSignUp}>
			가입하기
		</button>
		</Dialog>
	);
}
```

- 이 예제를 보면 Containment(포함)을 확실하게 이해 할 수 있다.
    - Dialog에서 가장 하단에 props.children부분에 해당 컴포넌트를 호출하는 상위 컴포넌트의 children이 포함이 되는 것이다.

## Inheritance (상속)

- 객체지향 개념으로는 부모 클래스를 상속 받아 자식 클래스를 생성한다는 의미로 자식 클래스는 부모 클래스의 변수나 함수등의 속성을 모두 가지게 된다.
- 리액트의 개념으로는 다른 컴포넌트로부터 상속을 받아서 새로운 컴포넌트를 만드는것을 의미한다.
    - 하지만 리액트에서 상속을 사용하는 좋은 방법이 딱히 없기 때문에 위에서 설명하는 Composition을 사용하는 것이 좋은 방법이다.
    

## **결론!**

- 복잡한 컴포넌트를 쪼개서 여러 개의 컴포넌트로 만들고, 만든 컴포넌트들을 조합해서 새로운 컴포넌트를 만들자.

## 실습 예제

**Card.jsx**

```jsx
function Card(props) {
    const { title, backgroundColor, children } = props;

    return (
        <div
            style={{
                margin: 8,
                padding: 8,
                borderRadius: 8,
                boxShadow: "0px 0px 4px grey",
                backgroundColor: backgroundColor || "white",
            }}
        >
            {title && <h1 style={{fontWeight: "bold"}}>{title}</h1>}
            {children}
        </div>
    );
}

export default Card;
```

**ProfileCard.jsx**

```jsx
import Card from "./Card";

function ProfileCard(props) {
    return (
        <Card title="Inje Lee" backgroundColor="#4ea04e">
            <p>안녕하세요, 소플입니다.</p>
            <p>저는 리액트를 사용해서 개발하고 있습니다.</p>
        </Card>
    );
}

export default ProfileCard;
```

**index.jsx**

```jsx
import ProfileCard from './demo10(composition_inheritance)/ProfileCard';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <ProfileCard />
  </React.StrictMode>,
  document.getElementById('root')
);
```