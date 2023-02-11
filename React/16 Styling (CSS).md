# 16. Styling (CSS)

## CSS (Cascading Style Sheets)

- 스타일링을 위한 일종의 계단식 언어이다.
- 기본적으로 CSS는 Selector와 Style로 구성된다.

# Selector

- Element에 스타일이 적용되는 규칙
- 스타일을 어떤 Element에 적용할 지를 선택하게 해준다.

![Untitled](16%20Styling%20(CSS)%2074843491e1eb4be28db5d494a9b0ace9/Untitled.png)

## Selector의 유형

1. **Element Selector**

```jsx
h1{
	color: green;
}
```

1. **ID selector**
- 특정 ID를 가지는 selector의 style 적용

```jsx
#section {
	background-color: black;
}
```

1. **Class selector**
- 특정 Class 명을 가진 선택자에게만 적용을 시킨다.

```jsx
.section{
	font-size: 20px;
}
```

- Element Selector와 Class selector를 함께 사용 가능
    
    ```jsx
    h1.section{
    	font-size: 20px;
    }
    ```
    
    - 이렇게 설정하게 되면 h1 태그의 section 클래스 일 때만 적용이 된다.
1. **Universal selector**
- 모든 선택자에 적용을 시키기 위함

```jsx
* {
	font-size: 20px;
}
```

1. **Grouping selector**
- 여러가지 선택자를 하나의 그룹으로 묶어 사용하기 위함
- 구분자로는 ,를 사용한다.

```jsx
h1, h2, p{
	color: black;
}
```

1. **Element의 상태와 관련된 selector**
- :**hover** : 마우스 커서가 element 위에 올라왔을 때
- :**active** : 주로 <a> 태그(link)에 사용되는데, element가 클릭됐을 때를 의미
- :**focus** : 주로<input> 태그에서 사용되는데, element가 초점을 갖고 있을 경우를 의미
- :**checked** : radio button이나 checkbox 같은 유형의 <input> 태그가 체크되어 있는 경우를 의미
- **:first-child, :last-child** : 상위 element를 기준으로 각각 첫번째 child, 마지막 child일 경우를 의미

```jsx
button:hover{
	font-weight: bold;
}

a:active{
	color: red;
}

input:focus {
	color: #000000;
}

option:checked{
	background: #00ff00;
}

p:first-child{
	background: #ff0000;
}

p:last-child{
	background: #0000ff;
}
```

# 레이아웃과 관련된 속성

- 레이아웃이란 화면에 Element들을 어떻게 배치할 것 인가를 정해주는 것이다.

## display

- Element에서 가장 중요한 속성으로써 element를 어떻게 표시할 지에 관련한 방법을 말한다.
    
    ```jsx
    div{
    	display: none | block | inline | flex;
    }
    ```
    
    - **display: none;**
        - element를 화면에서 숨기기 위해 사용
        - <script> 태그의 display 속성 기본값은 none이다.
    - **display: block;**
        - 블록 단위로 element를 배치
        - <p>, <div>, <h1>~<h6> 태그의 diㅕsplay 속성 기본 값이 block이다.
    - **display: inline;**
        - element를 라인 안에 넣는 것
        - <span> 태그의 display 속성 기본 값이 inline이다.
        - 이 속성을 사용하면 모든 width와 height에 관련한 속성값이 사라진다.
    - **display: flex;**
        - element를 블록 레벨의 flex container로 표시
        - container이기 때문에 내부에 다른 element들을 포함한다.

## visivility

- 가시 성이라는 뜻을 가지고 있고 CSS에서는 Element를 화면에 보이거나 감출 때 사용한다.
    
    ```jsx
    div{
    	visibility: visible | hidden;
    }
    ```
    
    - visibility: visible;
        - element를 화면에 보이게 하는 것
    - visibility:: hidden;
        - 화면에서 안보이게 감추는 것
        - element를 안보이게만 하는 것이고 화면에서의 영역은 그대로 차지한다.

## Position

- Element를 어떻게 배치 할 것 인지를 정하는 Style이다.
    
    ```jsx
    div{
    	position: static | flexed | relative | absolute;
    }
    ```
    
    - **static**
        - 기본 값으로 element를 원래의 순서대로 위치 시킨다.
    - **fixed**
        - element를 브라우저 window에 상대적으로 위치 시킴
    - **relative**
        - element를 보통의 위치에 상대적으로 위치 시킴
    - **absolute**
        - element를 절대 위치에 위치 시킴
        - 이때 기준은 상위 element가 된다.

# 가로, 세로와 관련된 속성

```jsx
div{
	width: auto | value;
	height: auto | value;
	min-width: auto | value;
	max-width: auto | value;
	min-height: auto | value;
	max-height: auto | value;
}
```

- 보통 value로는 %나 pixel 단위를 사용한다.

## Flexbox

- 기존 CSS Layout 사용의 불편한 부분을 개선하기 위해 등장
- display에서 flex를 사용하게 되면 element가 flex container가 된다.
    
    ![Untitled](16%20Styling%20(CSS)%2074843491e1eb4be28db5d494a9b0ace9/Untitled%201.png)
    
    ```jsx
    div{
    	display: flex;
    	flex-direction: row | column | row-reverse | column-reverse;
    	align-items: stretch | flex-start | center | flex-end | baseline;
    	justify-content: flex-start | center | flex-end | space-between | space-around;
    }
    ```
    
- **flex-direction**
    - 아이템을 어떤 방향으로 배치할 것인가.
    - **row**: 기본 값이며 아이템을 행을 따라 가로 순서대로 왼쪽부터 배치
    - **column** : 아이템을 열을 따라 세로 순서대로 위쪽부터 배치
    - **row-reverse:** 아이템을 행의 역방향으로 오른쪽부터 배치
    - **column-reverse**: 아이템을 열의 역방향으로 아래쪽부터 배치
    
    ![Untitled](16%20Styling%20(CSS)%2074843491e1eb4be28db5d494a9b0ace9/Untitled%202.png)
    
    ![Untitled](16%20Styling%20(CSS)%2074843491e1eb4be28db5d494a9b0ace9/Untitled%203.png)
    

- **align-items**
    - 어떻게 item들을 정렬 할 건인지를 결정한다. (cross axis기준)
    - **stertch** : 기본 값으로써 아이템을 늘려서 컨테이너를 가득 채움
    - **flex-start** : cross axis의 시작 지점으로 아이템을 정렬
    - **center** : cross axis의 중앙으로 아이템을 정렬
    - **flex-end** : cross axis의 끝 지점으로 아이템을 정렬
    - **baseline** : 아이템을 baseline 기준으로 정렬
    
    ![Untitled](16%20Styling%20(CSS)%2074843491e1eb4be28db5d494a9b0ace9/Untitled%204.png)
    

- **justify-content**
    - 어떻게 아이템들을 나란히 맞출 것인지를 결정
    - **flex-start** : main axis의 시작 지점으로 아이템을 정렬
    - **center** : main axis의 중앙으로 아이템을 정렬
    - **flex-end** : main axis의 끝 지점으로 아이템을 정렬
    - **space-between** : main axis를 기준으로 첫 아이템은 시작 지점에 맞추고 마지막 아이템은 끝 지점에 맞추며 중간에 있는 아이템들 사이의 간격이 일정하게 되도록 맞춤
    - **space-around** : main axis를 기준으로 각 아이템의 주변 간격을 동일하게 맞춤
        
        ![Untitled](16%20Styling%20(CSS)%2074843491e1eb4be28db5d494a9b0ace9/Untitled%205.png)
        

# 글꼴과 관련된 속성

```jsx
#title{
	font-family: "사용할 글꼴 이름", <일반적인 글꼴 분류>;
	font-size: value;
	font-weight: normal | bold;
	font-style: normal | italic | oblique;
}
```

## font-family

- 어떤 글꼴을 사용할 지를 정하는 속성
- 글꼴의 이름에 공백이 들어갈 경우 “”로 묶어야 된다.
- **fallback system**: 대비 체계라는 뜻으로 지정한 글꼴을 찾지 못했을 경우를 대비해서 사용할 글꼴을 순서대로 지정해주는 것
    
    ```jsx
    #title1{
    	font-family: "Times New Roman", Times, serif;
    }
    #title2{
    	font-family: "Times New Roman", Times, serif;
    }
    #title3{
    	font-family: "Times New Roman", Times, serif;
    }
    ```
    
    - **일반적인 글꼴 분류 (Generic font family, 가장 마지막 위치)**
        - **serif**
            - 각 글자의 모서리에 작은 테두리를 갖고 있는 형태의 글꼴
        - **sans-serif**
            - 모서리에 테두리가 없이 깔끔한 선을 가진 글꼴
            - 컴퓨터 모니터에서는 Serif보다 가독성이 좋은
        - **monispace**
            - 모든 글자가 같은 가로 길이를 가지는 글꼴
        - **cursive**
            - 사람 손 글씨 모양의 글꼴
        - **fantasy**
            - 장식이 들어간 형태의 글꼴

## font-size

- 글꼴의 크기와 관련한 속성
- **px(pixel), em, rem ,vw(viewport width)**
- 브라우저의 기본 글꼴 크기 1em은 16 pixels와 동일하다.

```jsx
#title1{
	font-size: 16px;
}
#title2{
	font-size: 1em;
}
#title3{
	font-size: 10vw;
}
```

## font-weight

- 글꼴의 두깨와 관련된 속성
- 100~900, bold, normal

```jsx
#title1{
	font-weight: 100;
}
#title2{
	font-weight: bold;
}
```

## font-style

- 글꼴의 style을 지정하기 위한 속성
- **normal**
    - 일반적인 글자의 형태를 의미
- **italic**
    - 글자가 기울어진 형태로 나타남
    - 별도로 기울어진 형태의 글자들을 직접 디자인해서 만든 것
- **oblique**
    - 글자가 비스듬한 형태로 나타남
    - 그냥 글자를 기울인 것
    
    ```jsx
    #title1{
    	font-style: italic;
    }
    #title2{
    	font-style: normal;
    }
    ```
    

## 기타 많이 사용하는 속성

- **background-color**
    - **16진수 컬러 값:** #ff0000
    - **투명도를 가진 16진수 컬러 값**: #ff000055
    - **RGB 컬러 값**: rgb(255,0,0)
    - **RGBA 컬러 값**: rgba(255, 0, 0, 0.5)
    - **HSL 컬러 값:** hsl(120, 100%, 25%)
    - **HSLA 컬러 값**: hsla(120, 100%, 50%, 0.3)
    - **미리 정의된 생상 이름**: red
    - **currentcolor 키워드**: 현재 지정된 색상 값을 사용
    - transparent를 사용할 경우 배경이 투명해짐
        
        ```jsx
        div{
        	background-color: color | transparent;
        }
        ```
        
- **border**
    - 테두리를 위한 속성
    - border는 border-width,style,color를 한번에 입력 가능하도록 만든 속성이다.
    
    ```jsx
    div{
    	border: border-width border-style border-color
    }
    ```
    

## Styled-components

- CSS문법을 그대로 사용하면서 결과물을 스타일링 된 컴포넌트 형태로 만들어주는 오픈소스 라이브러리이다.
- 컴포넌트 개념을 사용하기 때문에 리액트에서 많이 사용되고 있다.
    
    ```jsx
    # npm을 사용하는 경우
    npm install --save styled-components
    
    # yarn을 사용하는 경우
    yarn add styled-components
    ```
    
    ```jsx
    import styled from 'styled-components';
    
    const Title = styled.h1`
    	font-size: 1.5em;
    	color: white;
    	text-align: center;
    `;
    
    return (
    	<Title>
    		안녕!
    	</Title>
    )
    ```
    

## Tagged template literal이란?

- **tagged template literal**
    - **literal**: 소스 코드의 고정 된 값을 의미한다.
        
        ```jsx
        let number = 20;   // 20= literal
        ```
        
    - **template literal**: literal을 템플릿 형태로 사용하는 자바스크립트 문법
        - 흔히 역 따옴표라고 부르는``를 사용하여 문자열을 작성하고 그 안에 대체 가능한 Expression을 넣는 방법이다.
        - Untagged template literal과 Tagged template literal
        
        ```jsx
        // Untagged template literal
        // 단순 문자열
        `string text`
        
        //대체 가능한 expression이 들어있는 문자열
        `string text ${expression} string text`
        
        //Tagged template literal
        myFunction`string text ${expression} string text`;
        ```
        
        - Tagged template literal 예제
        
        ```jsx
        const name = '인제';
        const region = '서울';
        
        function myTagFunction(strings, nameExp, regionExp){
        	let str0 = strings[0];
        	let str1 = strings[1];
        	let str2 = strings[2];
        	
        	return `${str0}${nameExp}${str1}${regionExp}${str2}`;
        
        } 
        
        const output = myTagFunction`제 이름은 ${name}이고 사는 곳은 ${region}입니다`;
        
        console.log(output);
        ```
        
    

## styled-components의 props 사용하기

```jsx
const Button = styled.button`
	color: ${props => props.dark ? "white" : "dark"};
	background: ${props => props.dark ? "black" : "white"};
	border: 1px solid black;
`;

function Sample(props){
	return (
		<div>
			<Button> Normal </Button>
			<Button dark> Dark</Button>
		</div>
	)
}
```

- 위 예시처럼 Button 컴포넌트에 dark라는 prop을 넣어줌으로써 해당 값을 토대로 색상 값을 변경하는 것이 가능하다.

## styled-components의 스타일 확장

- styled-components를 사용하면 리액트 컴포넌트가 생성 되는데 그럼 그렇게 만들어진 컴포넌트를 기반으로 추가적인 스타일을 적용하고 싶다면 어떻게 해야 하는가?

```jsx
const Button = styled.button`
	color: ${props => props.dark ? "white" : "dark"};
	background: ${props => props.dark ? "black" : "white"};
`;

const RoundButton = styled(Button)`
	border-radius: 16px;
`;

```

- 위 처럼 ()를 가지고 다른 컴포넌트의 스타일을 확장해서 사용하는 것이 가능하다.
- style로 감싸줄 경우 해당 컴포넌트의 스타일 값만 가져옴

## 실습 예제

**Blocks.jsx**

```jsx
import styled from "styled-components";

const Wrapper = styled.div`
    padding: 1rem;
    display: flex;
    flex-direction: row;
    align-items: flex-start;
    justify-content: flex-start;
    background-color: lightgrey;
`;

const Block = styled.div`
    padding: ${(props) => props.padding};
    border: 1px solid black;
    border-radius: 1rem;
    background-color: ${(props) => props.backgroundColor};
    color: white;
    font-size: 2rem;
    font-weight: bold;
    text-align: center;
`;

const blockItems = [
    {
        label: "1",
        padding: "1rem",
        backgroundColor: "red",
    },
    {
        label: "2",
        padding: "3rem",
        backgroundColor: "green",
    },
    {
        label: "3",
        padding: "2rem",
        backgroundColor: "blue",
    },
];

function Blocks(props) {
    return (
        <Wrapper>
            {blockItems.map((blockItem) => {
                return (
                    <Block
                        padding={blockItem.padding}
                        backgroundColor={blockItem.backgroundColor}
                    >
                        {blockItem.label}
                    </Block>
                );
            })}
        </Wrapper>
    );
}

export default Blocks;
```

**index.jsx**

```jsx
import Blocks from './demo12(style)/Blocks';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <Blocks />
  </React.StrictMode>,
  document.getElementById('root')
);
```

- 해당 코드들 실행 한 이후 검사 - element에서 각각의 flex값들 변경해보기