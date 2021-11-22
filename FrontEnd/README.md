# 커밋 컨벤션

## 공통 사항

#### 커밋 컨벤션 통일

## 🤔 목적

- [ ] `feat:` 새로운 기능 추가
- [ ] `fix:` 버그 픽스
- [ ] `docs:` 사이트 / 문서 업그레이드
- [ ] `refactor:` 리팩토링
- [ ] `style:` 코드 포맷팅
- [ ] `test:` 테스트 코드 실행
- [ ] `Merge:` 브랜치 병합
- [ ] `chore:` 기타 변경사항, 다른 목적

## 🔗 관련 이슈 링크 및 재현 방법 

### 📝 사전 조건 

<!-- 예) Galaxy S20 -->
<!-- 예) Andriod 12, Chrome 90 -->

### 📝 재현 절차

<!-- 예) 로그인 수행, 컨퍼런스 목록에서 아이템을 클릭, 로그아웃됨 -->
<!-- 예) UI의 경우, 스크린 샷 이미지를 첨부하면 더 좋을 것 -->

### 📝 기대 결과

<!-- 예) 아이템이 클릭 시, 해당 컨퍼런스 상세 화면으로 진입해야 함 -->


## 💡 배경과 해결책 



## ☑️ 병합 전 자가진단

⚠️ 아래 항목들을 한 번 점검해 주세요! ⚠️

### ☑️ 커밋 컨벤션

- [ ] 기본적으로 커밋 메시지는 \[JIRA 이슈 번호\] 제목 / 본문으로 구성한다.
- [ ] 제목은 50자를 넘기지 않고, 마침표를 붙이지 않는다.
- [ ] 영어로 작성 시, 과거시제를 사용하지 않고 명령어로 작성하며, 첫 글자는 대문자를 사용한다.
- [ ] 본문은 `어떻게`보다 `무엇을`과 `왜`를 설명한다. 
- [ ] 한줄에 최대 72자를 넘기지 않고 제목과 구분되기 위해 한칸 띄워 작성한다.
- [ ] 모든 커밋에 본문 내용을 작성할 필요는 없다 (선택사항)
- [ ] 모든 커밋에 꼬리말을 작성할 필요는 없다. (선택사항)
- [ ] 이슈 번호를 적는다, 여러개 적어도 상관없다. 



JIRA 연동 시 조금 수정사항 있을 수 있음

ex) [JIRA 이슈 번호] fix : JPA N+1 문제가 발생했기 때문에 User Entity의 Member Fetch Type 수정

#### 프로젝트 컨벤션

- PR을 통한 merge : [리뷰어가 모두 approve 해야 merge 가능하도록 제한](https://vsh123.github.io/github/github-PR-setting/)
- [Google checkStyle](https://checkstyle.sourceforge.io/google_style.html) 적용
  - https://toma0912.tistory.com/93

------

## Git-Flow 브랜치 전략

![깃플로우](https://user-images.githubusercontent.com/39195377/111874689-b1461200-89d9-11eb-96bb-5b30d9a315ec.PNG)

✅ master : 베포 단계에 출시될 수 있는 브랜치
✅ develop : 개발이 끝난 버전을 개발하는 브랜치
✅ feature : 기능을 개발하는 브랜치
✅ release : 베포 버전을 준비하는 브랜치
✅ hotfix : 베포 버전에서 발생한 버그를 수정 하는 브랜치
✅ issue/* : issue를 나누어서 개발에 적용하는 브랜치

develop / BE / FE 구분하기

**Reference** : [우아한 형제들 기술블로그 : Git-flow](https://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html)

기본 베이스 브랜치는

Develop -> Release -> Master

Feature의 경우 /FE/Feature/기능명 으로 Develop브랜치에서 뽑아서 각자 네이밍하여 사용

Release 브랜치에 문제가 발생하면 hotfix 브랜치 뽑아서 사용하기

## 서버 구조

- Redis Server 분할(추후 리팩토링)
  - FCM(Firebase 토큰 저장소)
  - Login Session 저장소
  - 캐시 데이터 저장소
- AWS S3 & Lambda
  - AWS 프리 티어(이미지 저장 기본 50GB, Lambda 월별 무료요청 100만)

## FrontEnd 디렉터리 구조 

![](./)

## FrontEnd 패키지 구조

