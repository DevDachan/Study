# 9. Lombok

### Lombok이란?

- 반복되는 method를 Annotation을 사용하여 자동으로 작성해주는 라이브러리
- 일반적으로 VO, DTO, Model, Entity등의 데이터 클래스에서 주로 사용됨
    - @Getter
    - @Setter
    - @NoArgConstructor
    - @AllArgConstructor
    - @Data
    - @ToString
    

### **Lombok 의존성**

```jsx
<dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
		<optional>true</optional>
</dependency>
```

### @Getter // @Setter

- 해당 클래스에 선언되어 있는 필드를 기반으로 getField, setField와 같은 식으로 자동으로 method 생성
- 자동으로 getter와 setter를 생성해주기 때문에 별도로 만들어줄 필요가 없다.
    
<img src="https://user-images.githubusercontent.com/111109411/214297696-76de6337-3d14-4b10-8844-21442c243871.png" width=20%>    



### @NoArgsConstructor // @AllArgsConstructor // @RequiredArgsConstructor

- No : 파라미터가 없는 빈 생성자를 생성
- All : 모든 필드 값을 파라미터로 갖는 생성자를 생성
- Required : 필드 값 중 final이나 @NotNull인 값을 갖는 생성자를 생성
    

<img src="https://user-images.githubusercontent.com/111109411/214297734-f7e49730-6059-4bbb-b8da-a0dbc6938aa9.png" width=20%>    


### @ToString

- toString 메소드를 자동으로 생성
- @ToString 어노테이션에 exclude속성을 사용하여 특정 필드를 toString에서 제외 시킬 수 있음

<img src="https://user-images.githubusercontent.com/111109411/214297766-667cd62d-b5ca-49e1-b41c-a70e8d0ffdbf.png" width=100%>    



### @EqualsAndHashCode

- equals, hashCode 메소드를 자동으로 생성
    - equals: 두객체의 내용이 같은지 동등성을 비교하는 연산자
    - hashCode: 두 객체가 같은 객체인지 동일성을 비교하는 연산자
- callSuper 속성을 통해 메소드 생성시 부모 클래스의 필드까지 고려할 지 여부 설정 가능
    - callSuper = true: 부모 클래스 필드 값들도 동일한지 체크
    

### @Data

- 해당 어노테이션을 사용하면 앞서 나온 기능들을 한번에 추가해줌
    - @Getter, @Setter, @RequiredArgsConstructor, @ToString, EqualsAndHashCode
    

## Code

<img src="https://user-images.githubusercontent.com/111109411/214297810-3a1040a4-4855-4b8a-b1a7-24f0512afa4b.png" width=60%>    


- Getter 어노테이션 추가한 이후 우클릭해서 Refactor에 들어가면 Delombok을 통해 해당 어노테이션이 어떻게 구성하고 있는지 확인이 가능하다.

```jsx
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
    private String name;
    private String email;
    private String organization;
}
```

- 위에 보이는 것 같이 원래 Getter와 Setter를 설정해줘야 하는 부분이 한줄의 어노테이션으로 처리가 가능해진다.
