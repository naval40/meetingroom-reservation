# room-reservation
##### 제약조건에 맞게 회의실을 예약하고 볼수 있도록 한 어플리케이션 프로그램

## prerequisite
* IDE : Spring Tool Suite 
* build tool : gradle
* Framework : Spring Boot
* DB : JPA with H2
* Dependency

```	compile('org.springframework.boot:spring-boot-starter')
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-thymeleaf")
    compile("org.springframework.boot:spring-boot-devtools")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("com.h2database:h2")
    compile("org.apache.commons:commons-lang3")
    compile("org.projectlombok:lombok")
    
	testCompile('org.springframework.boot:spring-boot-starter-test')
 	testCompile('org.mockito:mockito-core:2.7.22')
```

## 프로젝트 구성

### 초기화
##### User와 Room정보는 spring boot 기동시 초기 세팅됨(InitStaticValues.java 참고)
### JPA 모델 정보 
##### Reservation : 예약 정보는 제목, 날짜, 시작/종료시간, 사용자, 회의실 정보를 가짐.
##### User/Room : 사용자/회의실 정보는 persistence 로 객채화 함.
 

## Run
you can run on IDE or type below in console at project root path

```
./gradlew build
java -jar build/libs/meetingroom-reservation-0.0.1-SNAPSHOT.jar
```
##### after then you can access to main page ( [http://localhost:8080](http://localhost:8080) )
##### there are two pages Add and List. 
##### you can access that page to click the button

## 개선사항
##### spring boot와 tymeleaf 템플릿을 처음 사용하다보니 시간이 좀더 걸렸음.
##### SPA로 구현하면 좀더 깔끔할것 같다.
##### list화면에서 스케쥴링 UI로 구현하는데 어려움이 있어서 중도에 해당 부분제거함.
##### User를 객채화한것은 인증로직도 추가하려했으나 시간상 생략.
