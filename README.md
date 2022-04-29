## Spring-MVC

### Spring-MVC-1 and Spring-MVC-2

### Spring-MVC-1

- 웹 어플리케이션 이해
- 서블릿
- 서블릿, JSP, MVC 패턴
- MVC 프레임워크 만들기
- 스프링 MVC - 구조 이해
- 스프링 MVC - 기본 기능
- 스프링 MVC - 웹 페이지 만들기

#### request.getParameter()

- `GET` 메서드의 `query parameter` 형식도 지원하고, `POST HTML Form` 형식도 둘 다 지원한다

> *참고*<br>
> 1. `content-type`은 `HTTP` 메시지 바디의 데이터 형식을 지정한다<br>
> 2. `GET URL query parameter` 형식으로 클라이언트에서 서버로 데이터를 전달할 때는`HTTP` 메시지 바디를 사용하지 않기 때문에 `content-type`이 없다
> 3. `POST HTML Form` 형식으로 데이터를 전달하면 `HTTP` 메시지 바디에 해당 데이터를 포함해서 보내기 때문에 바디에 포함된 데이터가 어떤 형식인지 `content-type`을 꼭 지정해야 한다.
     이렇게 폼으로 데이터를 전송하는 형식을 `application/x-www-form-urlencoded`라 한다.