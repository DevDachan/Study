# 17. Mini Project (Blog)

## 전체적인 기능

- 글 목록 보기
- 글 보기
- 댓글 보기
- 글 작성
- 댓글 작성

## 전체 디자인

![Untitled](17%20Mini%20Project%20(Blog)%206df42bcb8a61455bb6cdda8e9b0ca7c8/Untitled.png)

## 프로젝트 생성하기

```jsx
$ npx create-react-app mini-blog
```

![Untitled](17%20Mini%20Project%20(Blog)%206df42bcb8a61455bb6cdda8e9b0ca7c8/Untitled%201.png)

## 프로젝트 실행하기

```jsx
npm start
```

![Untitled](17%20Mini%20Project%20(Blog)%206df42bcb8a61455bb6cdda8e9b0ca7c8/Untitled%202.png)

## 필요한 패키지

```jsx
$ npm install --save react-router-dom
$ npm install --save styled-components
```

- **react-router-dom:** 리액트 앱에서 라우팅(페이지 전환)을 위해 필요한 패키지
- **styled-components:** 앞에서 배운 내용처럼 styled-components 사용을 위해 필요한 패키지
- **—save**: 현재 리액트 앱에 의존성을 주입해주는 옵션
    - 의존성 주입이 되어 있다면 다른 사람이 해당 리액트 앱을 실행할 경우 npm start만 해주면 자동으로 의존성이 주입 된다.

## 각 기능에 필요한 Component

- 글 목록 보기
    - PostList, PostListItem
- 글 보기
    - Post
- 댓글 보기
    - CommentList, CommentListItem
- 글 작성
    - PostWrite
- 댓글 작성
    - CommentWrite

## 전체적인 프로젝트 폴더 구조

![Untitled](17%20Mini%20Project%20(Blog)%206df42bcb8a61455bb6cdda8e9b0ca7c8/Untitled%203.png)

- 폴더 구조에는 정해진 양식이 없지만 협업을 위해서는 보편적으로 많이 사용하는 방식으로 구성하는 것이 좋고 재 사용이 되는 컴포넌트는 모아 놓으면 좋다.

## UI 컴포넌트

- 사용자가 입력을 할 수 있게 해주는 컴포넌트
- 프로젝트 설계 시에는 Top-Down 방식으로 사용
- 프로젝트 구현 시에는 Bottom-Up 방식으로 작은 부분부터 구현

## Button 컴포넌트 만들기

- 기본적으로 HTML에 button 태그를 사용해도 되지만 굳이 컴포넌트를 만드는 이유는 버튼의 스타일을 바꾸고 버튼에 들어갈 내용을 props로 받아 쉽게 사용하게 하기 위함

### Button.jsx

```jsx
import React from "react";
import styled from "styled-components";

const StyledButton = styled.button`
  padding: 8px 16px;
  font-size: 16px;
  border-width: 1px;
  border-radius: 8px;
  cursor: pointer;
`;

function Button(props){
  const {title, onClick} = props;

  return <StyledButton onClick={onClick}> {title || "button"}</StyledButton>;

}

export default Button;
```

- 사용자 입력으로는 title과 onClick 함수를 입력 받는다.
- **cursor**: 해당 element 위에 위치하는 마우스 커서의 모양이 변경 가능하다.
    - **auto**: 자동
    - **default**: 기본 값
    - **pointer**: 손가락 모양
    - **wait**: 로딩

## TextInput 컴포넌트 만들기

- 입력을 받을 때 input 태그를 사용하지만 여러 줄의 입력을 받아야 하기 때문에 Textarea사용

### TextInput.jsx

```jsx
import React from "react";
import styled from "styled-components";

const StyledTextarea = styled.textarea`
  width: calc(100%-32px);
  ${(props) =>
    props.height &&
    `
      height: ${props.height}px;
    `
  }
  padding:16px;
  font-size: 16px;
  line-height: 20px;
`;

function TextInput(props){
  const {height, value, onChange} = props;

  return <StyledTextarea height={height} value={value} onChange={onChange}></StyledTextarea>;

}

export default TextInput;
```

- 사용자에게 height, value, onChange를 입력 받고 만약 height이 입력 값으로 들어왔을 경우에만 높이 값을 설정하게 된다.

## List 컴포넌트 만들기

### PostListItem.jsx

```jsx
import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  width: calc(100% -32px);
  padding: 16px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  border: 1px solid grey;
  border-radius: 8px;
  cursor: pointer;
  background: white;
  :hover{
    background: lightgrey;
  }
`;

const TitleText = styled.p`
  font-size: 20px;
  font-weight: 500;
`;

function PostListItem(props){
  const {post , onClick} = props;

  return (
      <Wrapper onClick={onClick}>
        <TitleText>{post.title}</TitleText>
      </Wrapper>
  );
}
```

- PostListItem은 기본적으로 하나의 게시글을 나타내는 컴포넌트이므로 계층적으로 구조를 봤을 때는 가장 밑에 존재하는 자식 컴포넌트라고 생각하면 된다.

### PostList.jsx

```jsx
import React from "react";
import styled from "styled-components";
import PostListItem from "PostListItem";

const Wrapper = styled.div`
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: flex-start;

  & > *{
    :not(:last-child){
      margin-bottom: 16px;
    }
  }
`;

function PostList(props){
  const {posts , onClickItem} = props;

  return (
      <Wrapper>
          {posts.map((post, indx) => {
            return(
              <PostListItem
                key={post.id}
                post={post}
                onClick=(() => {
                  onClickItem(post);
                })

            );
          })}
      </Wrapper>
  );
}

export default PostList;
```

- 위 Style에서 사용된 >의 경우에는 그에 해당하는 직계만 허용하겠다는 의미이다.
- **&: Parent Selector**
- 때문에 위에서 & > *의 경우에는 Wrapper를 선택하고 모든 자식 컴포넌트에게 적용을 하겠다는 이야기이다.
- map함수는 이전에 배운 내용처럼 for문으로 각각의 array를 하나씩 읽어 오는 것이라고 생각하면 된다. (posts는 기본적으로 각각의 게시글 내용을 다 담고 있는 array이다.)
- **:not:** 부정의 의미로 해당 not이 가리키는 selector가 아니라면 해당 style을 적용하는 것
    - 위에 예시에서는 lastchild가 아니라면 style을 적용하는 것이다.

### CommentListItem.jsx

```jsx
import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  width: calc(100% -32px);
  padding: 16px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  border: 1px solid grey;
  border-radius: 8px;
  cursor: pointer;
  background: white;
  :hover{
    background: lightgrey;
  }
`;

const ContentText = styled.p`
  font-size: 14px;
`;

function CommnetListItem(props){
  const {comment} = props;

  return (
      <Wrapper onClick={onClick}>
        <ContentText>{comment.content}</ContentText>
      </Wrapper>
  );
}

export default CommnetListItem;
```

- commnet의 경우 단순히 댓글을 나타내는 것이므로 별도의 onClick이 필요 없다.
- comment가 댓글의 내용을 담고 있는 객체를 의미함

### CommentList.jsx

```jsx
import React from "react";
import styled from "styled-components";
import PostListItem from "PostListItem";

const Wrapper = styled.div`
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: flex-start;

  & > *{
    :not(:last-child){
      margin-bottom: 16px;
    }
  }
`;

function CommentList(props){
  const {comments} = props;

  return (
      <Wrapper>
          {
            comments.map((comment, index) => {
                return <CommentListItem key={comment.id} comment={comment} />;
            })}
      </Wrapper>
  );
}

export default CommentList;
```

## 가짜 데이터 만들기

- DB연동이 되어 있지 않기 때문에 가짜 데이터를 통해서 실습을 진행한다.
- 아래 json파일을 src 폴더 아래 저장하기

### data.json

```jsx
[
    {
        "id": 1,
        "title": "리액트에서 리스트 렌더링하기",
        "content": "안녕하세요, 소플입니다.\n이번 글에서는 리액트에서 리스트를 렌더링하는 방법에 대해서 배워보겠습니다.\n리스트를 렌더링하기 위해서는 자바스크립트 배열에서 제공하는 map함수를 사용합니다.",
        "comments": [
            {
                "id": 11,
                "content": "실제로 개발하다보면 map함수를 진짜 많이 쓰는 것 같아요😄"
            },
            {
                "id": 12,
                "content": "적용해보니 코드가 정말 간결해지네요ㅎㅎ"
            },
            {
                "id": 13,
                "content": "key를 꼭 넣어줘야 하는군요~"
            },
            {
                "id": 14,
                "content": "생산성이 확 올라가는 느낌입니다ㅋㅋ"
            },
            {
                "id": 15,
                "content": "오늘도 좋은 글 감사합니다!👍"
            }
        ]
    },
    {
        "id": 2,
        "title": "리액트의 조건부 렌더링이란?",
        "content": "안녕하세요, 소플입니다.\n이번 글에서는 리액트의 조건부 렌더링에 대해서 배워보도록 하겠습니다.\n조건부 렌더링은 말 그대로 조건에 따라서 렌더링을 다르게 한다는 의미입니다.",
        "comments": [
            {
                "id": 21,
                "content": "이렇게 사용하는 방법이 있었군요!"
            },
            {
                "id": 22,
                "content": "좋은 글 감사합니다ㅎㅎ"
            },
            {
                "id": 23,
                "content": "항상 ?만 사용했었는데, 이제 &&도 사용해봐야 겠네요."
            },
            {
                "id": 24,
                "content": "쉬운 설명 감사드립니다😁"
            },
            {
                "id": 25,
                "content": "바로 코드에 적용해보겠습니다!!"
            }
        ]
    },
    {
        "id": 3,
        "title": "리액트 Hook에 대해서 배워볼까요?",
        "content": "안녕하세요, 소플입니다.\n이번 글에서는 리액트의 Hook에 대해서 배워보도록 하겠습니다.\nHook은 리액트의 함수 컴포넌트의 흐름에 끼어들어서 다양한 작업들을 처리하기 위해서 사용합니다.",
        "comments": [
            {
                "id": 31,
                "content": "뭔가 어려운 개념이었는데, 글을 읽고 조금 정리가 된 것 같습니다."
            },
            {
                "id": 32,
                "content": "Hook이 뭔가 했더니 이런거였군요. 알려주셔서 감사합니다ㅎㅎ"
            },
            {
                "id": 33,
                "content": "처음에 훅을 접했을 때 너무 어려웠는데 감사합니다!👍"
            },
            {
                "id": 34,
                "content": "앞으로는 잘 사용할 수 있을것 같아요"
            },
            {
                "id": 35,
                "content": "이름부터 너무 어려운 훅...🥲"
            }
        ]
    },
    {
        "id": 4,
        "title": "리액트 컴포넌트 개념 소개",
        "content": "이번 글에서는 리액트의 컴포넌트에 대해서 설명을 해보려고 합니다.\n리액트가 컴포넌트 기반이라는 것은 리액트를 조금만 공부해보신 분들도 다 알고 계실겁니다.\n그렇다면 컴포넌트는 도대체 뭘까요?",
        "comments": [
            {
                "id": 41,
                "content": "헷갈렸던 개념을 확실히 이해할 수 있어서 좋네요ㅋㅋ"
            },
            {
                "id": 42,
                "content": "컴포넌트에 대한 쉬운 설명 감사드려요👏"
            },
            {
                "id": 43,
                "content": "컴포넌트를 제대로 이해하지 않은 상태로 사용하기만 했는데 확실히 개념을 잡을 수 있어서 좋습니다!👍"
            },
            {
                "id": 44,
                "content": "리액트는 컴포넌트 기반이라서 재사용성도 높고 정말 좋은것 같아요"
            },
            {
                "id": 45,
                "content": "리액트 최고!!👍"
            }
        ]
    },
    {
        "id": 5,
        "title": "처음 만난 리액트 강의 소개",
        "content": "안녕하세요, 소플입니다.\n오늘은 제가 만든 리액트 강의를 소개해드리려고 합니다.\n강의 이름은 '처음 만난 리액트'입니다.\n강의 이름에서 이미 느끼셨을텐데, 리액트 초보자분들을 위한 강의입니다.",
        "comments": [
            {
                "id": 51,
                "content": "강의 너무 좋아요~!"
            },
            {
                "id": 52,
                "content": "초보자도 쉽게 이해할 수 있어서 좋습니다😃"
            },
            {
                "id": 53,
                "content": "실습도 따라하면서 하는데 좋아요"
            },
            {
                "id": 54,
                "content": "좋은 강의 감사드립니다👍👍"
            },
            {
                "id": 55,
                "content": "오 이런 강의가 있었군요~"
            }
        ]
    },
    {
        "id": 6,
        "title": "안녕하세요 소플입니다.",
        "content": "제 블로그에 오신 것을 환영합니다.\n앞으로 유익한 글들을 자주 올리도록 하겠습니다!",
        "comments": [
            {
                "id": 61,
                "content": "많이 올려주세요!👍"
            },
            {
                "id": 62,
                "content": "와 좋습니다ㅎㅎ"
            },
            {
                "id": 63,
                "content": "리액트 너무 어려워요ㅠㅠ😂"
            },
            {
                "id": 64,
                "content": "소플님 강의 잘 듣고 있습니다~!"
            },
            {
                "id": 65,
                "content": "꾸준히 블로그 활동 해주세요!!😀"
            }
        ]
    }
]
```

## Page 컴포넌트 구현하기

- 페이지를 컴포넌트라고 부르는게 어색할 수 있지만 리액트에서는 어쨌든 컴포넌트로 페이지를 구성한다.

### MainPage.jsx

- MainPage.jsx에서는 위에서 구현했던 list와 ui의 여러 컴포넌트들을 불러오는 정도의 구현 밖에 없다.
- 여기서 **react-router-dom** 객체를 사용하여 페이지를 라우팅 해준다.
    - 사용 방법은 **useNavigate()**라는 함수를 사용해 navigator를 생성해주고 각각의 컴폰넌트를 클릭하는 onClick event에서 useNavigate() 함수에 내가 들어갈 url을 입력해주면 된다.
    - 아래 예시에서 useNavigate() 함수는 편의를 위해 navigate로 줄여서 사용한다.

```jsx
import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import PostList from "../list/PostList";
import Button from "../ui/Button";
import data from '../../data.json';

const Wrapper = styled.div`
    padding: 16px;
    width: calc(100% - 32px);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
`;

const Container = styled.div`
    width: 100%;
    max-width: 720px;
    & > * {
        :not(:last-child) {
            margin-bottom: 16px;
        }
    }
`;

function MainPage(props) {
    const navigate = useNavigate();

    return (
        <Wrapper>
            <Container>
                <Button
                    title="글 작성하기"
                    onClick={() => {
                        navigate("/post-write");
                    }}
                />

                <PostList
                    posts={data}
                    onClickItem={(item) => {
                        navigate(`/post/${item.id}`);
                    }}
                />
            </Container>
        </Wrapper>
    );
}

export default MainPage;
```

- Wrapper와 Container는 단순히 각각의 컴포넌트를 감싸고 있는 부분에 대한 CSS style을 적용해주는 컴포넌트이다.

### PostWritePage.jsx

- 실제 사용자가 Post를 쓰기 위한 페이지를 말한다.

```jsx
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import TextInput from "../ui/TextInput";
import Button from "../ui/Button";

const Wrapper = styled.div`
    padding: 16px;
    width: calc(100% - 32px);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
`;

const Container = styled.div`
    width: 100%;
    max-width: 720px;
    & > * {
        :not(:last-child) {
            margin-bottom: 16px;
        }
    }
`;

function PostWritePage(props) {
    const navigate = useNavigate();

    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    return (
        <Wrapper>
            <Container>
                <TextInput
                    height={20}
                    value={title}
                    onChange={(event) => {
                        setTitle(event.target.value);
                    }}
                />

                <TextInput
                    height={480}
                    value={content}
                    onChange={(event) => {
                        setContent(event.target.value);
                    }}
                />

                <Button
                    title="글 작성하기"
                    onClick={() => {
                        navigate("/");
                    }}
                />
            </Container>
        </Wrapper>
    );
}

export default PostWritePage;
```

- useState Hook을 사용해서 각각의 내용이 변경되었을 때 새로운 컴포넌트를 만들어 재 렌더링이 되도록 설정한다.
- **title state**
    - 글의 제목을 위한 state
- **content state**
    - 글의 내용을 위한 state
    

### PostViewPage.jsx

- 글을 볼 수 있게 해주는 컴포넌트로써 댓글 작성이 가능하도록 해야 한다.
- prop로 전달 받은 글의 id를 이용해 글의 제목, 내용, 댓글 값을 가져와 화면에 렌더링 해야 한다.

```jsx
import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import CommentList from "../list/CommentList";
import TextInput from "../ui/TextInput";
import Button from "../ui/Button";
import data from "../../data.json";

const Wrapper = styled.div`
    padding: 16px;
    width: calc(100% - 32px);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
`;

const Container = styled.div`
    width: 100%;
    max-width: 720px;
    & > * {
        :not(:last-child) {
            margin-bottom: 16px;
        }
    }
`;

const PostContainer = styled.div`
    padding: 8px 16px;
    border: 1px solid grey;
    border-radius: 8px;
`;

const TitleText = styled.p`
    font-size: 28px;
    font-weight: 500;
`;

const ContentText = styled.p`
    font-size: 20px;
    line-height: 32px;
    white-space: pre-wrap;
`;

const CommentLabel = styled.p`
    font-size: 16px;
    font-weight: 500;
`;

function PostViewPage(props) {
    const navigate = useNavigate();
    const { postId } = useParams();

    const post = data.find((item) => {
        return item.id == postId;
    });

    const [comment, setComment] = useState("");

    return (
        <Wrapper>
            <Container>
                <Button
                    title="뒤로 가기"
                    onClick={() => {
                        navigate("/");
                    }}
                />
                <PostContainer>
                    <TitleText>{post.title}</TitleText>
                    <ContentText>{post.content}</ContentText>
                </PostContainer>

                <CommentLabel>댓글</CommentLabel>
                <CommentList comments={post.comments} />

                <TextInput
                    height={40}
                    value={comment}
                    onChange={(event) => {
                        setComment(event.target.value);
                    }}
                />
                <Button
                    title="댓글 작성하기"
                    onClick={() => {
                        navigate("/");
                    }}
                />
            </Container>
        </Wrapper>
    );
}

export default PostViewPage;
```

- **postId**: useParam() Hook을 이용해 URL을 통해 넘겨준 파라미터 값을 불러올 수 있다. 자세한 라우팅 Hook 사용의 경우는 라우팅 Note 참고

## App.js

```jsx
import React from "react";
import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";
import styled from "styled-components";
// Pages
import MainPage from './component/page/MainPage';
import PostWritePage from './component/page/PostWritePage';
import PostViewPage from './component/page/PostViewPage';

const MainTitleText = styled.p`
    font-size: 24px;
    font-weight: bold;
    text-align: center;
`;

function App(props) {
    return (
        <BrowserRouter>
            <MainTitleText>미니 블로그</MainTitleText>
            <Routes>
                <Route index element={<MainPage />} />
                <Route path="post-write" element={<PostWritePage />} />
                <Route path="post/:postId" element={<PostViewPage />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
```