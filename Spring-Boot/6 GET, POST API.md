# 6. GET, POST API

## **GET API**

**@RequestMapping**

- value와 method로 정의하여 API를 개발하는 방식 (고전적이므로 사용하지 않음)

```

@RequestMapping(value="/hello", method= RequestMethod.GET)

```

**@GetMapping (without Param)**

- 별도의 파라미터 없이 GET API를 호출하는 경우 사용되는 방법

```

@GetMapping(value="/hello")

```

**@PathVariable**

- GET 형식의 요청에서 파라미터를 전달하기 위해 URL에 값을 담아 요청하는 방법
- 이 방식은 value에서 사용된 {변수}의 이름과 method의 매개변수와 일치시켜야 한다.

```

@GetMapping(value= “/hello/{variable}”)

public String getHello (@PathVariable String variable){

  return variable;

}

```

- 만약 @GetMapping에서 사용된 {변수}의 이름과 메소드의 매개변수가 다를 경우 아래와 같이 사용한다.

```

@GetMapping(value= “/hello/{variable2}”)

public String getHello2 (@PathVariable(variable2) String var){

  return var;

}

```

**@RequestParam**

- GET형식의 요청에서 쿼리 문자열을 전달하기 위해 사용되는 방법
- ?를 기준으로 우측에 {key}={value}의 형태로 전달되며, 복수 형태로 전달 시 &로 분류한다.

Ex) [http://localhost:8080/api/hello?name=dachan&email=devdachan&organization=devdachan](http://localhost:8080/api/hello?name=dachan&email=devdachan&organization=devdachan)

```

@GetMapping(value=”hello”)

public String getRequestParam(

  @RequestParam String name,

  @RequestParam String email,

  @RequestParam String organization

  ){

    return name + “ ” + email + “ ” + organization;

}

```

- 만약 어떤 값이 들어올지 모르는 상황에서는 Map구조를 사용해서 쿼리를 읽는다.

```

@GetMapping(value=”hello”)

public String getRequestParam(@RequestParam MAP<String, String> param){

  StringBuilder sb = new StringBuilder();

  param.entrySet().forEach(map -> {

    sb.append(map.getKey() +” : ”+ map.getValue() + “\n”);

  });

  return sb.toString();

}

```

**DTO(Data Transfer Object) 사용**

- GET형식의 요청에서 쿼리 문자열을 전달하기 위해 사용되는 방법
- key와 value가 정해져 있지만 받을 파라미터가 많을 경우 DTO객체를 사용한 방식

```

@GetMapping(value=”hello”)

public String getRequestParam(MemberDTO memberDTO){

  return memberDTO.toString();

}

public class MemberDTO{

  private String name;

  private String email;
  
  private String organization;

  …

}

```

**Code**

```

@RestController

@RequestMapping("/api/v1/get-api") // 이런식으로 class밖에 RequestMapping을 추가해줄 경우 전체 경로를 추가 가능하다.

// http://localhost:8080/api/v1/get-api

public class GetController {

// http://localhost:8080/api/v1/get-api/hello

  @RequestMapping(value = "/hello", method = RequestMethod.GET)

  public String getHello(){

    return "Hello World";

  }

```

<img src="https://user-images.githubusercontent.com/111109411/213903752-fba80253-48e4-454d-a88d-046175539ef6.png" width=60%>



```

@GetMapping(value="/variable1/{variable}")

public String getVariable(@PathVariable("variable") String var){

  return var;

}

```

<img src="https://user-images.githubusercontent.com/111109411/213903762-dd10dc3d-f16a-4f37-a864-61db3fc6b129.png" width=60%>


```

@GetMapping(value="hello3")

public String getRequestParam3(MemberDTO memberDTO){

  return memberDTO.toString();

}

```

<img src="https://user-images.githubusercontent.com/111109411/213903777-6723fad7-f560-428d-ba83-2ddf6cf5be89.png" width=60%>


```

@GetMapping(value="hello2")

public String getRequestParam2(@RequestParam Map<String, String> param){

  StringBuilder sb = new StringBuilder();

  param.entrySet().forEach(map -> {

    sb.append(map.getKey() +" : "+ map.getValue() + "\n");

  });

  return sb.toString();

}

```

## **POST API**

**POST API**

- 리소스를 추가하기 위해 사용되는 API (GET은 서버의 리소스를 가져올 때 사용됨)

@PostMapping: POST API를 제작하기 위해 사용되는 어노테이션 (@RequestMapping + POST Method)

- 일반적으로 추가하고자 하는 Resource를 http body에 추가하여 서버에 요청
- 그렇기 때문에 @RequestBody를 이용하여 body에 담겨있는 값을 받아야 함.

Ex)

@PostMapping(value=”/member”)

public String postMember(@RequestBody Map<String, Object> postData){

  StringBulder sb = new StringBuilder();

  postData.entrySet().forEach( map-> {

    sb.append(map.getKey()+ “: ” + map.getValue() + “\n”);

  });

  return sb.toString();

}

**DTO 사용**

- key와 value가 정해져 있지만 받아야 할 파라미터가 많을 경우 DTO객체를 사용한 방식

@PostMapping(value=”/member”)

public String postMemberDto(@RequestBody MemberDTO memberDTO){

  return memberDTO.toString();

}

**Code**

- 전체적으로 달라진 것은 없고 그냥 annotation을 @RequestBody를 써야 함

```

@PostMapping(value = "/member")

public String postMember(@RequestBody Map<String, Object> postData){

  StringBuilder sb = new StringBuilder();

  postData.entrySet().forEach( map -> {

    sb.append(map.getKey() + " : " + map.getValue() + "\n");

  });

  return sb.toString();

}

```

<img src="https://user-images.githubusercontent.com/111109411/213903785-0ea03cfc-12d0-427b-b691-10335443ea70.png" width=60%>

- POST로 보낼 경우에는 보이는 것처럼 BODY에 JSON형태로 추가해야 함
