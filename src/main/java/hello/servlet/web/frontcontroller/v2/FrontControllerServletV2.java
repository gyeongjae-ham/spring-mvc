package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// urlPatterns에 *을 사용해서 이 경로 하위로 들어오는 건 무조건 이 controller를 거치게끔 만든다
@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    // 매핑 정보 - 키(url), 값(컨트롤러) : 어떤 url이 들어오면 어떤 controller를 호출하라는 의미
    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    // 컨트롤러 별 uri를 키로 설정해서 map에 담아준다
    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // getRequestURI로 /front-controller/v1/members/~~ 부분을 받아온다
        String requestURI = request.getRequestURI();
        // 받아온 URI 정보로 맵에서 해당 컨트롤러의 인스턴스를 불러온다(다형성에 의해서 구현한 부모 인터페이스로 불러올 수 있다)
        ControllerV2 controller = controllerMap.get(requestURI);
        // 없을 경우 예외처리로 404처리 해주고
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 이제는 myview가 반환되므로 반환된 view를 받고
        MyView view = controller.process(request, response);
        // 그 view 인스턴스의 render를 실행해준다
        view.render(request, response);
    }
}
