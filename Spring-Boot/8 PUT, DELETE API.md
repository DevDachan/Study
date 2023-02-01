# 8. PUT, DELETE API

## PUT PAI

- 해당 리소스가 존재하면 갱신하고, 리소스가 없을 경우에는 새로 생성해주는 API
    - 업데이트를 위한 메소드
    - 기본 방식은 POST API와 동일

## DELETE API

- 서버를 통해 리소스를 삭제 하기 위해 사용되는 API
- 일반적으로 @PathVariable을 통해 리소스 ID등을 받아 처리

## **ResponseEntity**

- Spring Framework에서 제공하는 클래스 중 HttpEntity라는 클래스를 상속 받아 사용하는 클래스
    - 사용자의 HttpRequest에 대한 응답 데이터를 포함
- 400, 200등 Error에 대한 내용을 조금 더 자세하게 정의해주는 클래스

### PUT API Code

```jsx
@PutMapping(value = "/member")
    public String postMember(@RequestBody Map<String, Object> putData){
        StringBuilder sb = new StringBuilder();

        putData.entrySet().forEach(map ->{
            sb.append(map.getKey() + " : " + map.getValue() + "\n");
        });
        return sb.toString();
		}
```

- 기본적으로 map으로 data를 받고 있기 때문에 일단 출력해주고 있다.

<img src="https://user-images.githubusercontent.com/111109411/214031098-40f2b421-0519-4b38-8637-b42f7e65589e.png" width=60%>

```jsx
@PutMapping(value = "member1")
    public String postMemberDto1(@RequestBody MemberDTO memberDTO){
        return memberDTO.toString();
    }
```

- DTO에 관한 값을 보내주니 해당 값을 Response body로 보내주게 된다. 이때 return하는 값은 toString의 형태로만 보내지기 때문에 단순한 가공만 된다.

<img src="https://user-images.githubusercontent.com/111109411/214031142-6ab19b4b-e5fb-4624-87f8-83c01f90b813.png" width=60%>


```jsx
@PutMapping(value = "member2")
    public MemberDTO postMemberDto2(@RequestBody MemberDTO memberDTO){
        return memberDTO;
    }
```

- 이때는 toString없이 memeberDTO의 Object를 그대로 넘겼기 때문에 Response하는 부분에는 JSON형태로 Data가 출력이 된다.
- 대부분은 data를 처리할 때 Client부분에서 JSON형태의 Data 형태가 처리하기 쉽기 때문에 자주 쓰이게 된다.

<img src="https://user-images.githubusercontent.com/111109411/214031177-1963d94b-91d4-40b6-b89f-c54b827d64b5.png" width=60%>

```jsx
@PutMapping(value = "member3")
    public ResponseEntity<MemberDTO> postMemberDto3(@RequestBody MemberDTO memberDTO){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberDTO);
    }
```

- 위에서 사용한 member2와 동일한 결과 값을 나타내지만 202 Response Header를 가지고 있는 것을 볼 수 있다.
- 지금은 200번과 동일하지만 HttpStatus의 내용을 변경하면서 출력이 가능해진다.
- body값은 단순히 memberDTO가 들어가 있기 때문에 member2와 같은 것


<img src="https://user-images.githubusercontent.com/111109411/214031226-b7a1b7be-dba1-4100-b512-d4475725b29f.png" width=60%>
<img src="https://user-images.githubusercontent.com/111109411/214031231-f590702f-0139-4875-bd6e-ec8f74b6d8d1.png" width=60%>


### DELETE API Code

```jsx
@DeleteMapping(value = "/{variable}")
    public String DeleteVariable(@PathVariable String variable){
        return variable;
    }
```

- Delete는 현재 단순히 return만 해주도록 구현이 돼있기 때문에 별다른 Action이 없다.

<img src="://user-images.githubusercontent.com/111109411/214031273-80650b6d-0a74-41b3-bd31-e6f6037174bf.png" width=60%>

