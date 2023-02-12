# 18. Router

## react-router-dom

- 리액트를 위한 라우팅 라이브러리이다.
- 라우팅: 사용자가 원하는 경로로 보내는 과정을 말한다. 쉽게 URL을 이용해서 내가 원하는 페이지로 사용자를 이동 시키는 것이다.

## 구성 예시

```jsx
<BrowserRouter>
  <Routes>
    <Route index element={<Main />}></Route>
    <Route path="/product/" element={<Product />}></Route>                
    <Route path="*" element={<NotFound />}></Route>
  </Routes>
</BrowserRouter>

or
(해당 프로젝트는 위 예제를 사용한다.)
<Switch>
   <Route path="/topics?id=1">HTML is ...</Route>
   <Route path="/topics?id=2">CSS is ...</Route>
   <Route path="/topics?id=3">JS is ...</Route>
</Switch>
```

- **BrowserRouter**
    - 웹 브라우저에서 React-router를 사용해서 라우팅을 할 수 있도록 해주는 컴포넌트
    - 브라우저는 기본적으로 history를 포함하고 있는데  여기에는 사용자의 방문 기록이 저장이 된다. 그렇게 사용자가 뒤로가기를 누르면 이 history를 이용해 이전 경로로 가게 되는 것이다.
    - RrowserRouter는 이 history를 제공해준다
- **Routes**
    - 실제로 라우팅 경로를 구성할 수 있게 해주는 컴포넌트
    - 여러 개의 Route 컴포넌트를 Children으로 가지게 된다.
- **Route**
    - Route는 Routes의 하위 컴포넌트로써 path와 element라는 prop을 가지고 있다.
    - **path**: 실제 경로를 의미함
    - **element**: 경로가 일치 했을 경우 어떤 리액트 Element를 렌더링 할 것 인지를 나타낸다.
    

## useNavigate()

- 페이지 이동을 위해서 제공하는 Hook

```jsx
function SampleNavigate(props){
	const nabigate = useNavigate();

	const moveToMain = () =>{
		navigate("/");
	}
	return (
		...
	);
}
```

- Navagator를 사용하기 위해서는 path를 parameter로 넣어주면 된다.

## useParams()

- react-router-dom에서 제공하는 Hooks 중 하나로써 prameter값을 URL을 통해 넘겨 넘겨 받은 페이지에서 사용할 수 있도록 도와준다.

**App.js (Route)**

```jsx
<Switch>
   <Route path="/topics?id=1">HTML is ...</Route>
   <Route path="/topics?id=2">CSS is ...</Route>
   <Route path="/topics?id=3">JS is ...</Route>
</Switch>

or

<BrowserRouter>
  <Routes>
    <Route path="/" element={<Main />}></Route>
    <Route path="/product/:productId" element={<Product />}></Route>                
    <Route path="*" element={<NotFound />}></Route>
  </Routes>
</BrowserRouter>

or

const navigate = useNavigate();

<Button
    title="댓글 작성하기"
    onClick={() => {
    navigate("/topics?id=1");
   }}
/>
```

- App.js에서 라우팅을 할 때 기본적으로 위와 같이 path를 정해서 라우팅을 하도록 한다. 이때 뒤에 나오는 id라는 값을 하위 컴포넌트에서 사용하기 위해서는 이 useParams() Hook을 사용해야 함

```jsx
const params = useParams();

console.log(params.id);
```