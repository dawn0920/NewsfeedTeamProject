# TeamIntroduction
내일배움캠프 뉴스피드 팀 프로젝트

🔗 https://github.com/dawn0920/NewsfeedTeamProject

## 📝 프로젝트 소개
### 개발 목적

### 개발 기간
2025.04.07 ~ 2025.04.11

### 개발자
- **백가희** : 팀장, 와이어 프레임 작성, 팔로우, 좋아요 api 개발
- **백종현** : erd 작성, user api 개발
- **진형국** : 인증·인가 개발, exeption 처리 개발
- **조유석** : api 명세서 작성, comment api 개발
- **정승원** : post api 개발

<img width="450" alt="Group 15" src="https://github.com/user-attachments/assets/963dd6a0-28f5-4a9f-9d50-8d877615cbef" />

## 🗃️ ERD 다이어그램
![Image](https://github.com/user-attachments/assets/6a61a2a0-cd09-45aa-acc4-7bb3d3ea2688)

## 📘 API 명세서


## ✨ 주요 기능
- 회원가입 및 로그인 (JWT 인증)
- 게시글 작성 / 조회 / 수정 / 삭제
- 댓글 작성 / 조회 / 수정 / 삭제
- 게시글 및 댓글 좋아요
- 사용자 팔로우 / 언팔로우
- 예외 처리 및 전역 에러 핸들링
- Redis를 통한 토큰 저장 및 로그아웃 처리

## 🛠️ 사용 stack
- Java 17
- Spring Boot 3.4.4
- Spring Data JPA
- Spring Security
- Spring Validation
- Spring Web (REST API)
- Thymeleaf + Thymeleaf Extras Spring Security
- MySQL
- Redis
- H2 Database (Test)
- JWT (JJWT)
- Lombok
- Jackson (for JSON)
- JUnit 5
- Gradle
- GitHub

## 📂 폴더 구조 
```
📦src  
 ┣ 📂main  
 ┃ ┣ 📂java  
 ┃ ┃ ┗ 📂org.example.newsfeedteamproject  
 ┃ ┃   ┣ 📂base_entity  
 ┃ ┃   ┃ ┗ 📜BaseEntity.java  
 ┃ ┃   ┣ 📂comment  
 ┃ ┃   ┃ ┣ 📂controller  
 ┃ ┃   ┃ ┣ 📂dto  
 ┃ ┃   ┃ ┣ 📂entity  
 ┃ ┃   ┃ ┣ 📂repository  
 ┃ ┃   ┃ ┗ 📂service  
 ┃ ┃   ┣ 📂commentLikes  
 ┃ ┃   ┃ ┣ 📂controller  
 ┃ ┃   ┃ ┣ 📂dto  
 ┃ ┃   ┃ ┣ 📂entity  
 ┃ ┃   ┃ ┣ 📂repository  
 ┃ ┃   ┃ ┗ 📂service  
 ┃ ┃   ┣ 📂global  
 ┃ ┃   ┃ ┣ 📂common  
 ┃ ┃   ┃ ┃ ┣ 📂config  
 ┃ ┃   ┃ ┃ ┗ 📂jwt  
 ┃ ┃   ┃ ┣ 📂error  
 ┃ ┃   ┃ ┃ ┣ 📜CustomErrorResponse.java  
 ┃ ┃   ┃ ┃ ┣ 📜CustomException.java  
 ┃ ┃   ┃ ┃ ┣ 📜CustomExceptionHandler.java  
 ┃ ┃   ┃ ┃ ┣ 📜ErrorCode.java  
 ┃ ┃   ┃ ┃ ┗ 📜ExceptionCode.java  
 ┃ ┃   ┃ ┗ 📜GlobalController.java  
 ┃ ┃   ┣ 📂post  
 ┃ ┃   ┃ ┣ 📂controller  
 ┃ ┃   ┃ ┣ 📂dto  
 ┃ ┃   ┃ ┣ 📂entity  
 ┃ ┃   ┃ ┣ 📂repository  
 ┃ ┃   ┃ ┗ 📂service  
 ┃ ┃   ┣ 📂postLikes  
 ┃ ┃   ┃ ┣ 📂controller  
 ┃ ┃   ┃ ┣ 📂dto  
 ┃ ┃   ┃ ┣ 📂entity  
 ┃ ┃   ┃ ┣ 📂repository  
 ┃ ┃   ┃ ┗ 📂service  
 ┃ ┃   ┣ 📂user  
 ┃ ┃   ┃ ┣ 📂controller  
 ┃ ┃   ┃ ┣ 📂dto  
 ┃ ┃   ┃ ┣ 📂entity  
 ┃ ┃   ┃ ┣ 📂repository  
 ┃ ┃   ┃ ┗ 📂service  
 ┃ ┣ 📂resources  
 ┃ ┃ ┣ 📜application.properties  
 ┃ ┃ ┣ 📜application.yml  
 ┃ ┃ ┗ 📜application-local.properties  
 ┗ 📂test
```

### 🔗 figma link
https://www.figma.com/design/VbZC0qKnBZRQB8rwo3qFxy/-%EB%82%B4%EB%B0%B0%EC%BA%A0--%EB%89%B4%EC%8A%A4%ED%94%BC%EB%93%9C-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8?node-id=0-1&t=yimAzH3oq8lMMpJC-1
![Image](https://github.com/user-attachments/assets/25e583fc-283a-4465-8377-70c9259fbe74)



























