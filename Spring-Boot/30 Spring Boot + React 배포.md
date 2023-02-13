# 30. Spring Boot + React 배포

## 자바 설치

```bash
$ sudo apt update
$ sudo apt install openjdk-11-jdk
```

- 기본적으로 Spring Boot의 경우에는 자바 언어를 사용하고 있기 때문에 우리가 Spring boot에서 사용하는 자바 버전에 맞는 jdk를 설치해줘야 한다.

## 프로젝트 가져오기

- 프로젝트를 서버로 가져오는 방법은 2가지가 존재한다.

### 1. Git을 통해 가져오기

- 보통은 대부분의 프로젝트를 Git을 통해 관리하기 때문에 해당 방법이 가장 관리하기 쉬움
    - 프로젝트를 Local에서 Update 했을 때 서버 측에서는 단순히 pull만 해주면 되기 때문
- **Git 설치**
    
    ```bash
    $ sudo apt install git
    ```
    
- **Git 프로젝트 가져오기**
    
    ```bash
    $ mkdir git
    $ cd git 
    $ sudo git init
    ```
    
- git에서 ssh를 통해 프로젝트를 가져오기 위해서는 GitHub에 **ssh key를 등록** 시켜줘야 한다고 한다.
    
    ```bash
    $ ssh-keygen -t rsa
    $ vim /root/.ssh/id_rsa.pub
    ```
    
    - 위에서 생성된 키를 ```GitHub > 계정 > 설정 > SSH and OPG keys > New SSH key``` 에 등록 시키기


<img src="https://user-images.githubusercontent.com/111109411/218372486-707167b0-ca33-4f32-b3d4-5e578e1f6a33.png" width=60%>


- **Git Clone**
    
    ```bash
    $ sudo git remote add origin git@github.com:[계정이름]/[repository]
    $ sudo git fetch origin
    $ sudo git pull origin master 
    ```
    

### 2. 직접 sftp shell 접속 후 가져오기

- Sftp로 접속하기 위해서는 Powershell을 통해 아래와 같이 접속을 한다.
    - 접속 시에는 프로젝트가 위치한 폴더의 상위 폴더에서 접속해야 함
    
    ```bash
    $ sftp [username]@[서버 주소]
    ```
    
    - **sftp 명령어**
    
    | mkdir | 원격 시스템(서버 측)에서 디렉토리  생성 |
    | --- | --- |
    | cd | 원격 시스템(서버 측)에서 디렉토리 이동 |
    | lcd | 로컬 시스템(클라이언트 측)에서 폴더 이동 |
    | ls | 원격 시스템(서버 측)에서 현재 디렉토리 내용 나열 |
    | lls | 로컬 시스템(클라이언트 측)에서 디렉토리 내용 나열 |
    | get | 원격 시스템에서 로컬 시스템으로 파일을 복사 |
    | put | 로컬 시스템에서 원격 시스템으로 파일을 복사 |
    | delete | 원격 시스템 디렉토리에서 파일을 삭제 |
    - **put   (local → server)**
        
        
        | put [파일 명] | 기본 명령어 |
        | --- | --- |
        | put [파일 명] [New 파일 명] | 다른 이름으로 저장 |
        | put -r [파일 명] | 디렉토리 자체를 업로드 |
    - **get (server → local)**
        
        
        | get [파일 명] | 기본 명령어 |
        | --- | --- |
        | get [파일 명] [New 파일 명] | 다른 이름으로 저장 |
        | get -r [파일 명] | 디렉토리 자체를 업로드 |
    

## 프로젝트 빌드

### Gradle

```jsx
$ chmod +x gradlew
$ ./gradlew clean build -x [file_name] &
```

### Maven

**pom.xml (내용 추가)**

```jsx
<build>
 <plugins>
  <plugin>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-maven-plugin</artifactId>
  </plugin>
 </plugins>
</build>
```

```jsx
$ maven package

or

$ mvn install
```

- 위 명령어는 pom.xml 파일이 들어 있는 프로젝트 폴더에서 실행해야 한다.

## 프로젝트 시작

**jar을 통한 spring boot 앱 실행**

```jsx
$ cd build/libs/
$ nohup java -jar [file_name] 
```

**maven을 사용한 spring boot 앱 실행**

```jsx
mvn spring-boot:run
```

**gradle을 사용한 spring boot 앱 실행**

```jsx
gradle bootRun
```

- 프로젝트를 재 시작 할 때에는 기존 포트를 죽인 후 재 시작 해야 한다.
    
    ```jsx
    $ fuser -k -n tcp 8080
    ```
    

**자동 재시작 및 핫 스와핑**: 아래의 의존성을 추가해줌으로써 자동으로 재 시작이 가능하도록 함

```jsx
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <optional>true</optional>
</dependency>
```


## 출처

[Spring Boot - 서버 배포하기 1](https://velog.io/@yewo2nn16/SpringBoot-서버-배포)
