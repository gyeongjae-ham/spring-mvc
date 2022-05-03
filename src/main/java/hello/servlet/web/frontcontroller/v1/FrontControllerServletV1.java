package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// urlPatterns에 *을 사용해서 이 경로 하위로 들어오는 건 무조건 이 controller를 거치게끔 만든다
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    // 매핑 정보 - 키(url), 값(컨트롤러) : 어떤 url이 들어오면 어떤 controller를 호출하라는 의미
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    // 컨트롤러 별 uri를 키로 설정해서 map에 담아준다
    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        // getRequestURI로 /front-controller/v1/members/~~ 부분을 받아온다
        String requestURI = request.getRequestURI();
        // 받아온 URI 정보로 맵에서 해당 컨트롤러의 인스턴스를 불러온다(다형성에 의해서 구현한 부모 인터페이스로 불러올 수 있다)
        ControllerV1 controller = controllerMap.get(requestURI);
        // 없을 경우 예외처리로 404처리 해주고
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 있다면 해당 인스턴스의 process를 실행해준다(간단하게 JSP로 뷰를 만드는 부분일수도 있고, 아니면 service로 비지니스 로직을 수행하는 것일 수도 있다)
        controller.process(request, response);
    }
}
