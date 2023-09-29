<div align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&height=250&color=80ea6e&fontColor=363636&text=%EC%9D%8C%EC%95%85%20%ED%8C%8C%EC%9D%BC%20%EC%97%85%EB%A1%9C%EB%93%9C%20%EC%84%9C%EB%B2%84" alt="header"/>
</div>

<div align="center">
    Spring, Thymeleaf, MySQL을 이용한 음악 파일 업로드 서버
    <br>
    디스코드 음악 봇과 연동하여 업로드한 음악 파일을 디스코드 봇을 통해 재생 가능 [디스코드 음악 봇](https://github.com/bandall/Discord-Music-Bot)
    <br>
    스프링 강의에서 배운 내용들을 익히기 위해 만들어 보았다.
</div>

## 🛠️ 기술 스택 🛠️

<div align="center">
    <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
    <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
    <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white">
    <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
    <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
    <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">
    
</div>

<br>

## 🧰 개발 도구 🧰

<div align="center">
    <img src="https://img.shields.io/badge/intellijidea-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
    <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">    
    <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
</div>

<br>

## 💻 주요 기능 💻

### 로그인 기능
![로그인 화면](https://github.com/bandall/Simple-File-Uploader/assets/32717522/8d57fa3a-c2b7-423b-9c45-4b59e7d8ff0a)
스프링 시큐리티 Form 로그인 방식 사용. 계정 정보는 디스코드 봇을 통해 확인 가능 <br>
![로그인 실패](https://github.com/bandall/Simple-File-Uploader/assets/32717522/6246df7b-2050-4bd5-885c-b3146d7d8d24)
로그인 실패 시 나오면 화면. BindingResult 사용 <br>

### 메인 페이지
![메인화면](https://github.com/bandall/Simple-File-Uploader/assets/32717522/ba4cb66c-57cf-4a7a-8c07-5db88f6b075e)
로그인 성공 시 나오는 메인 페이지<br>
파일 목록 조회와 파일 업로드 중 하나를 고를 수 있다. <br>

### 업로드 페이지
![업로드 화면](https://github.com/bandall/Simple-File-Uploader/assets/32717522/f71dbf38-b657-4244-ad4e-d987bfa26633)
파일을 하나 선택하여 업로드 할 수 있다. 업로드 할 수 있는 파일의 파일의 최대 크기는 300MB이다. <br>

![업로드 화면2](https://github.com/bandall/Simple-File-Uploader/assets/32717522/a3641f0c-66e6-4182-85f6-f7919b4d88cf)

<br>

![업로드 화면3](https://github.com/bandall/Simple-File-Uploader/assets/32717522/e2f1284c-4628-4a6b-bd69-f238a17b017e)
업로드 시 jQuery를 통해 파일 업로드 상황을 보여준다. <br>

### 업로드 후 페이지
![업로드 성공](https://github.com/bandall/Simple-File-Uploader/assets/32717522/c98d112a-23f9-4042-8e9d-2f24799e63ed)
업로드에 성공하면 파일의 ID를 보여준다. 해당 파일 ID를 디스코드 음악 봇에 입력하면 로컬 음악을 재생할 수 있다. ex) `/local [your file id]` <br>

![업로드 후](https://github.com/bandall/Simple-File-Uploader/assets/32717522/1c83bb25-f314-47f9-9f50-ad380802d45c)
파일 목록에서도 업로드 파일 ID를 확인할 수 있다. 파일 삭제는 파일을 업로드한 계정만 가능하다. <br>

### 검색 기능
![검색 기능](https://github.com/bandall/Simple-File-Uploader/assets/32717522/839f4050-ebb2-4f1e-acdc-94941bc21bdc)
원본 파일 이름 검색 기능을 통해 업로드한 파일을 조회할 수 있다. <br>

### 오류 페이지
![오류 화면](https://github.com/bandall/Simple-File-Uploader/assets/32717522/f58f5193-dcab-4917-b571-22bc59296582)
오류가 발생할 경우 오류 페이지를 보여준다. 오류 페이지는 동적으로 오류 메시지를 담아 렌더링된다.

## ❗ ISSUE ❗
Node.js 프로젝트 진행 시에도 시놀로지 내부 Docker를 이용해서 배포했었는데, 그때는 응답 속도가 100ms 이하였다.
하지만 이번 Spring의 경우 응답 속도가 500 ~ 1,000ms 사이를 왔다 갔다 한다. <br>
처음에는 Thymeleaf 렌더링으로 인한 지연인 줄 알았으나, API 응답 시에도 지연시간이 비슷하였다.
시놀로지 같이 CPU 사양이 많이 낮을 경우에는 싱글 스레드, 논블로킹 모델을 사용하는 Node.js가 적합한 것 같다. <br>
앞으로 Spring 프로젝트를 진행할 일이 있으면 AWS를 이용하는 것이 더 좋을 것 같다.

