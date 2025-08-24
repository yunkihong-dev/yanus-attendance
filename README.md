<div align="center">

# 🎯 Yanus
### 효율적인 출석 관리 시스템

<img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL">
<img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white" alt="Thymeleaf">

<br><br>

<a href="http://220.69.241.91/" target="_blank">
  <img src="https://img.shields.io/badge/🌐_LIVE_DEMO-FF6B6B?style=for-the-badge&logoColor=white" alt="Live Demo">
</a>

</div>

---

<table>
<tr>
<td width="50%" valign="top">

### ✨ 주요 기능

- 🚀 **출석 체크** - 간편한 출석 확인 및 기록
- 👥 **회원 관리** - 사용자 등록 및 관리  
- 📊 **세부 조회** - 출석 현황 상세 조회 및 통계
- 🗑️ **회원 삭제** - 회원 정보 삭제 기능
- 📈 **관리자 대시보드** - 전체 출석 현황 관리

</td>
<td width="50%" valign="top">

### 🛠️ 기술 스택

- **Backend** Spring Boot
- **Database** MySQL  
- **Template** Thymeleaf
- **Frontend** HTML, CSS, JavaScript
- **Security** Spring Security

</td>
</tr>
</table>

---


<details>
<summary>📋 환경 설정</summary>

```properties
# application.yml
# server
server:
  port: 80
  servlet:
    session:
      timeout: 64800 # 세션 타임아웃을 초 단위로 설정 (18  시간)

# jdbc
#로컬 테스트용
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/YANUS?useSSL=false&serverTimezone=Asia/Seoul
    username: root
    password: 12345678

  mvc:
    hiddenmethod:
      filter:
        enabled: true

  # jpa
  jpa:
    hibernate:
      ddl-auto: update

    # jpa format
    properties:
      hibernate:
        format_sql: true
    show-sql: true

  # log
  output:
    ansi:
      enabled: always
```

</details>

---

## 📈 업데이트 히스토리

<table>
<tr align="center">
<th>버전</th>
<th>날짜</th>
<th>주요 업데이트</th>
</tr>
<tr align="center">
<td><img src="https://img.shields.io/badge/v1.3.0-FF6B6B?style=flat-square"></td>
<td>2025.05.28</td>
<td>회원 삭제 기능 추가</td>
</tr>
<tr align="center">
<td><img src="https://img.shields.io/badge/v1.2.0-4ECDC4?style=flat-square"></td>
<td>2025.01.20</td>
<td>세부 조회 기능 추가</td>
</tr>
<tr align="center">
<td><img src="https://img.shields.io/badge/v1.1.0-45B7D1?style=flat-square"></td>
<td>2023.12.09</td>
<td>도메인 연결 완료</td>
</tr>
</table>

---

## 📱 사용 가이드

<table>
<tr>
<td width="50%">

### 👤 **일반 사용자**
1. **회원가입** - 사이트 접속 후 계정 생성
2. **출석 체크** - 로그인 후 출석 버튼 클릭
3. **통계 조회** - 개인 출석 이력 및 통계 확인

</td>
<td width="50%">

### 👑 **관리자**
1. **대시보드** - 전체 출석 현황 모니터링
2. **회원 관리** - 사용자 정보 수정/삭제
3. **통계 분석** - 기간별 출석 데이터 분석

</td>
</tr>
</table>

---

## 🔐 보안 & 🤝 기여

<table>
<tr>
<td width="50%">

### 🛡️ **보안 기능**
- Spring Security 인증/권한
- 비밀번호 암호화 (BCrypt)
- SQL 인젝션 방지

</td>
<td width="50%">

### 📝 **기여 방법**
1. Fork 저장소
2. `git checkout -b feature/new-feature`
3. `git commit -m "feat: add feature"`
4. Pull Request 생성

</td>
</tr>
</table>

---

<div align="center">

### 👨‍💻 개발자

**[yunkihong-dev](https://github.com/yunkihong-dev)** 

<img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white" alt="GitHub">

<br><br>

### 📞 문의 & 지원

<img src="https://img.shields.io/badge/Issues-FF6B6B?style=for-the-badge&logo=github&logoColor=white" alt="Issues"> **버그 리포트 & 기능 제안**

<br><br>

⭐ **프로젝트가 도움이 되셨다면 스타를 눌러주세요!** ⭐

</div>
