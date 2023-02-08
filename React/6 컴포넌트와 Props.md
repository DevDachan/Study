# 6. 컴포넌트와 Props

## Component란?

- Class Component와 Function Component로 나뉘게 된다. 초기에는 Class를 많이 사용했지만 불편해서 Function을 개선해서 많이 사용하게 됐고 그것을 훅이라고 말한다.
- React라는 것이 하나의 사용자 정의 태그 기반 웹 서비스를 만드는 것이라 생각하면 되는데 그때 말하는 사용자 정의 태그를 컴포넌트라고 생각하면 된다.

## Function Compoent (함수 컴포넌트)

```jsx
function Welcome(props){
	return <h1> 안녕, {props.name}</h1>
}
```

- 위 코드와 같은 형식으로 함수 형태로 존재하며 return 값으로 react element를 반환해주기 때문에 react component라고 할 수 있다.
- 형식이 매우 간단함

## Class Component (클래스 컴포넌트)

```jsx
class Welcome extends React.Component{
	render(){
			return <h1> 안녕, {this.props.name} </h1>;
	}
}
```

- 함수 컴포넌트와 가장 큰 차이점은 React.Component를 상속 받아야 한다는 것이다.
    - 상속:  자바 객체 지향 프로그래밍에서 나오는 정의로 한 클래스의 변수들과 함수들을 상속 받아서 새로운 자식 클래스를 만드는 방법

## Component의 이름

- 컴포넌트 이름은 항상 대문자로 시작해야 한다.
    - 소문자로 시작하는 것을 DOM Tag로 인식하기 때문이다.
        
        ```jsx
        // HTML div 태그로 인식
        const element = <div />;
        
        // Welcome이라는 리액트 컴포넌트로 인식
        const element = <Welcome name="다찬" />
        ```
        

## Conpoenent 렌더링

- 렌더링을 위해서는 컴포넌트로부터 element를 생성해줘서 DOM에 추가해줘야 한다.
    
    ```jsx
    function Welcome(props){
    	return <h1> 안녕, {props.name}</h1>;
    }
    
    const root = ReactDOM.createRoot(document.getElementById('root'));
    const element = <Welcome name="다찬" />;
    ReactDOM.render(
    	element,
    	document.getElementById('root')
    );
    ```
    
- 위와 같이 ReactDOM의 render를 통해서 실제 DOM에 추가가 된다.

## Component 합성

- 합성이란 Component안에 또 다른 Component를 쓰는 것이다.
- 이러한 합성을 통해 복잡한 화면을 여러 개의 Component로 나눠서 구현이 가능해진다.
    
    ```jsx
    function Welcome(props){
    	return <h1> 안녕, {props.name}</h1>;
    }
    
    function App(props){
    	return (
    		<div>
    			<Welcome name="Mike" />
    			<Welcome name="Steve" />
    			<Welcome name="Jane" />
    		</div>
    	)
    }
    ReactDOM.render(
    	<App />,
    	document.getElementById('root')
    );
    ```
    
    - 이런 식으로 App이라는 함수 안에서 Welcome이라는 컴포넌트를 여러개 선언 함으로써 새로운 컴포넌트를 생성 가능
- 같은 컴포넌트이지만 각기 다른 props를 가지고 있다.
    

<img src="https://user-images.githubusercontent.com/111109411/217175978-f9563dc7-96e1-4955-b0c3-69d802872d9d.png" width=60%>


## Component 추출

- 합성과는 반대로 큰 컴포넌트에서 일부를 추출해서 새로운 컴포넌트를 만드는 것
- 이러한 추출을 잘 하면 재사용성이 증가한다.
- 개발속도가 향상된다.
    - Example
        
        ```jsx
        function Component(props){
        	return (
        		<div className="component">
        			<div className="user-info">
        				<img className="avatar"
        					src={props.author.avatarUrl}
        					alt={props.author.name}	
        				/>
        				<div className="user-info-name">
        					{props.author.name}
        				</div>
        			</div>
        	
        			<div className="comment-text">
        				{props.text}
        			</div>
        			<div className="comment-date">
        				{formatDate(props.date)}
        			</div>
        		</div>
        	);
        }
        ```
        
    1. **Avatar 추출하기**
    - 만약 Avatar라는 컴포넌트를 추출하게 된다면 아래와 같은 형태가 될 것이다.
        
        ```jsx
        function Avatar(props){
        	return (
        		<img className="avatar"
        					src={props.author.avatarUrl}
        					alt={props.author.name}	
        		/>
        	)
        }
        ```
        
    1. **UserInfo 추출하기**
    - 마찬가지로 user-info를 추출하면 아래와 같다. Avatar의 경우 이전에 컴포넌트로 추출 했기 때문에 컴포넌트로 사용이 된다.
    
    ```jsx
    function UserInfo(props){
    	return (
    					<div className="user-info">
    						<Avatar user={props.user}>
    						<div className="user-info-name">
    							{props.author.name}
    						</div>
    					</div>
    	);
    }
    ```
    
    1. **전체 추출이 완료된 코드**
    
    ```jsx
    function Component(props){
    	return (
    			<UserInfo user={props.author}>
    	
    			<div className="comment-text">
    				{props.text}
    			</div>
    			<div className="comment-date">
    				{formatDate(props.date)}
    			</div>
    		</div>
    	);
    }
    ```
    
<img src="https://user-images.githubusercontent.com/111109411/217176151-f654018c-ef4b-486f-8526-c85bfde3ef5c.png" width=60%>




## 실습 예제

**index.js**

```jsx
import CommentList from './demo3(comment)/CommentList'

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
      <CommentList />
  </React.StrictMode>,
  document.getElementById("root")
);
```

- React.stricMode란 애플리케이션 내의 잠재적인 문제를 알아내기 위한 도구이다. 단순히 개발 모드에서만 활성화되고 Production Build에는 영향을 끼치지 않는다.

**Comment.jsx**

```jsx
import React from "react";
const styles = {
    wrapper: {
        margin: 8,
        padding: 8,
        display: "flex",
        flexDirection: "row",
        border: "1px solid grey",
        borderRadius: 16,
    },
    imageContainer: {},
    image: {
        width: 50,
        height: 50,
        borderRadius: 25,
    },
    contentContainer: {
        marginLeft: 8,
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
    },
    nameText: {
        color: "black",
        fontSize: 16,
        fontWeight: "bold",
    },
    commentText: {
        color: "black",
        fontSize: 16,
    },
};

function Comment(props) {
    return (
        <div style={styles.wrapper}>
            <div style={styles.imageContainer}>
                <img
                    src="https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png"
                    style={styles.image}
                />
            </div>

            <div style={styles.contentContainer}>
                <span style={styles.nameText}>{props.name}</span>
                <span style={styles.commentText}>{props.comment}</span>
            </div>
        </div>
    );
}

export default Comment;
```

**CommentList.jsx**

```jsx
import React from "react";
import Comment from "./Comment";

const comments = [
    {
        name: "이인제",
        comment: "안녕하세요, 소플입니다.",
    },
    {
        name: "유재석",
        comment: "리액트 재미있어요~!",
    },
    {
        name: "강민경",
        comment: "저도 리액트 배워보고 싶어요!!",
    },
];

function CommentList(props) {
    return (
        <div>
            {comments.map((comment) => {
                return (
                    <Comment name={comment.name} comment={comment.comment} />
                );
            })}
        </div>
    );
}

export default CommentList;
```

- map함수에 대해서는 이후에 자세히 배울 예정

<img src="https://user-images.githubusercontent.com/111109411/217176235-926a5e95-dcef-4e34-922c-cd546b690536.png" width=60%>



