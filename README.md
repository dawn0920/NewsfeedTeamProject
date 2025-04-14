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
### 유저 api 명세서
| 기능                   | Method | URL                | Request                                                                                                                                                                                                                                                      | Response                                                                                                                                                                                                                                                                                                                              | 응답 코드 |
 |------------------------|--------|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|
| 회원가입               | POST   | /users/signup      | body: {   "email": "test@example.com",   "password": "secure123",   "userRefId": "testUser01",   "name": "홍길동",   "birthday": "1990-01-01",   "phone": "010-1234-5678" }                                                                                  | {   "id": 1,   "email": "test@example.com",   "userRefId": "testUser01",   "name": "홍길동",   "birthday": "1990-01-01",   "phone": "010-1234-5678" }                                                                                                                                                                                 | 200 OK    |
| 로그인                 | POST   | /users/login       | body: {   "email": "test@example.com",   "password": "secure123" }                                                                                                                                                                                           | {   "grantType": "Bearer",   "accessToken": "eyJhbGciOiJIUzI1NiJ9...",   "refreshToken": "eyJhbGciOiJIUzI1NiJ9..." }                                                                                                                                                                                                                  | 200 OK    |
| 로그아웃               | POST   | /users/logout      |header  'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...'                                                                                                                                                                   | 로그아웃 완료                                                                                                                                                                                                                                                                                                                         | 200 OK    |
| 사용자 프로필 조회     | GET    | /{userId}          |                                                                                                                                                                                                                                                              | {   "id": 5,   "email": "test@example.com",   "userRefId": "testUser01",   "name": "홍길동",   "intro": "hi",   "birthday": "1990-01-01",   "follow": 0,   "following": 0,   "phone": "010-1234-5678",   "createdTime": "2025-04-13T15:26:37.531657",   "modifiedTime": "2025-04-13T15:26:37.531657",   "fileName": exmaplefile.jpg } | 200 OK    |
| 사용자 프로필 업데이트 | PUT    | /{userId}          | header : 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...'  body: {   "email": "test1@example.com",   "userRefId": "testUser11",   "name": "홍길길",   "intro": "hi1",   "profileImg":"profile1.jpg",   "birthday": "1990-11-11",   "phone": "010-1134-5678" } | {   "email": "test1@example.com",   "userRefId": "testUser11",   "name": "홍길길",   "intro": "hi1",   "profileImg":"profile1.jpg",   "birthday": "1990-11-11",   "phone": "010-1134-5678" }                                                                                                                                          | 200 OK    |
| 회원 탈퇴              | PATCH  | /{userId}          | header  'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...'  body: {   "password": "secure123",   "withdrawn": true }                                                                                                                                            | {   "id": 1,   "withdrawn": true }                                                                                                                                                                                                                                                                                                    | 200 OK    |
| 사용자의 게시글 조회   | GET    | /{userId}/posts    | header  'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...'                                                                                                                                                                                                      | 페이징 된 사용자의 글 작성 목록                                                                                                                                                                                                                                                                                                       | 200 OK    |
| 사용자의 댓글 조회     | GET    | /{userId}/comments | header  'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...'                                                                                                                                                                                                      | 페이징 된 사용자의 댓글 작성 목록                                                                                                                                                                                                                                                                                                     | 200 OK    |
| 팔로우/언팔로우 토글     | POST    | /{toUserId}/follow | header: `Authorization: Bearer <accessToken>` PathVariable: `toUserId: Long`                                                                                                                                                                                                       | { "fromUserId": 1, "toUserId": 2, "followed": true }                                                                                                                                                                                                                                                                                                     | 200 OK    |
| 팔로잉 리스트 조회     | GET    | /{userId}/following | PathVariable: `userId: Long`                                                                                                                                                                                                      | [ { "userId": 2, "name": "홍길동", "profileImg": "..." } ]                                                                                                                                                                                                                                                                                                     | 200 OK    |
| 팔로워 리스트 조회     | GET    | /{userId}/follower | PathVariable: `userId: Long`                                                                                                                                                                                                      | [ { "userId": 3, "name": "김영희", "profileImg": "..." } ]                                                                                                                                                                                                                                                                                                     | 200 OK    |




### 게시글 api 명세서
| 기능      | Method | URL             | Request                                                                                                                              | Response                                                                                                                                                                                                                                                                          | 응답 코드  |
 |---------|--------|-----------------|--------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------|
| 게시글 만들기 | POST   | /posts          | body: { "contents": "test post5", "tag": "#test #newsfeed #team21", "img":"이미지 경로", "mention": "@testuser5" }                        | { "id": 5, "contents": "test post5", "img": "base64EncodedImageString", "tag": "#test #newsfeed #team21", "mention": "@testuser5", "postLike": 0, "commentNum": 0, "userId": 1, "createdAt": "2025-04-13T15:49:14.0126732", "updatedAt": "2025-04-13T15:49:14.0126732" }          | 200 OK |
| 게시글 조회  | GET    | /posts/{postId} |                                                                                                                                      | { "id": 5, "contents": "test post5", "img": "base64EncodedImageString", "tag": "#test #newsfeed #team21", "mention": "@testuser5", "postLike": 0, "commentNum": 0, "userId": 1, "createdAt": "2025-04-13T15:49:14.012673", "updatedAt": "2025-04-13T15:49:14.012673" }            | 200 OK |
| 게시글 수정  | PUT    |   /posts/{postId}              | { "contents": "update post test1", "tag": "#updateed #team21", "img": "updatedBase64EncodedImageString", "mention": "@updateduser" } | { "id": 5, "contents": "update post test1", "img": "updatedBase64EncodedImageString", "tag": "#updateed #team21", "mention": "@updateduser", "postLike": 0, "commentNum": 0, "userId": 1, "createdAt": "2025-04-13T15:49:14.012673", "updatedAt": "2025-04-13T15:49:14.012673" }  | 200 OK |
| 게시글 삭제  | DELETE | /posts/{postId}                 |                                                                                                                                      |                                                                                                                                                                                                                                                                                   |    204 No Content|

### 게시글 좋아요 api 명세서
| 기능             | Method | URL                                     | Request                                | Response                                | 응답 코드     |
 |----------------|--------|-----------------------------------------|----------------------------------------|-----------------------------------------|-----------|
| 게시글 좋아요 토글     | POST   | posts/{postId}/like                     | Authorization: Bearer {{access_token}} | { "success": true, "postLikeCount": 1 } | 200 OK    |
| 게시물 좋아요 수 조회   | GET    | Authorization: Bearer {{access_token}}  |                                        | { "countPostLike": 1 }                  | 200 OK    |


###  댓글 API 명세서

| 기능 | Method | URL                                                        | Request 예시 | Response 예시 | 응답코드 |
 |------|--------|------------------------------------------------------------|--------------|---------------|----------|
| 게시글에 댓글 추가 | POST | /posts/{postId}/comments                                   | `{ "contents": "hi Add test comment1 ^^" }` | `{ "id": 5, "userId": 1, "postId": 1, "contents": "hi Add comment5", "creatTime": "2025-04-13T15:55:10.2615046", "modifiedTime": "2025-04-13T15:55:10.2615046", "commentLikes": [], "likeCount": 0 }` | 201 Created |
| 게시글의 댓글들을 가져오기 | GET |/posts/{postId}/comments | - | `{ "content": [ { "id": 5, "userId": 1, "postId": 1, "contents": "hi Add comment5", "creatTime": "2025-04-13T15:55:10.261505", "modifiedTime": "2025-04-13T15:55:10.261505", "commentLikes": [], "likeCount": 0 } ], "pageable": { "pageNumber": 0, "pageSize": 10, "sort": { "empty": false, "unsorted": false, "sorted": true }, "offset": 0, "unpaged": false, "paged": true }, "first": true, "last": true, "size": 10, "number": 0, "sort": { "empty": false, "unsorted": false, "sorted": true }, "numberOfElements": 5, "empty": false }` | 200 OK |
 
---

###  댓글 좋아요 API 명세서

| 기능 | Method | URL | Request 예시 | Response 예시 | 응답코드 |
 |------|--------|-----|--------------|---------------|----------|
| 댓글 좋아요 토글 | POST | comments/{commentId}/like | - | `{ "success": true, "commentLikeCount": 1 }` | 200 OK |
| 댓글 좋아요 수 가져오기 | GET | comments/{commentId}/like | - | `{ "countCommentLike": 1 }` | 200 OK |

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



























