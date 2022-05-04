package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// urlPatterns에 *을 사용해서 이 경로 하위로 들어오는 건 무조건 이 controller를 거치게끔 만든다
@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    // 매핑 정보 - 키(url), 값(컨트롤러) : 어떤 url이 들어오면 어떤 controller를 호출하라는 의미
    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    // 컨트롤러 별 uri를 키로 설정해서 map에 담아준다
    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // getRequestURI로 /front-controller/v1/members/~~ 부분을 받아온다
        String requestURI = request.getRequestURI();
        // 받아온 URI 정보로 맵에서 해당 컨트롤러의 인스턴스를 불러온다(다형성에 의해서 구현한 부모 인터페이스로 불러올 수 있다)
        ControllerV4 controller = controllerMap.get(requestURI);
        // 없을 경우 예외처리로 404처리 해주고
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>(); // 추가 - frontcontroller에서 model을 생성해서 해당 controller에 넘겨주면 거기서 model에 값을 넣어주고 view 논리 경로 반환

        String viewName = controller.process(paramMap, model);

        MyView view = viewResolver(viewName);
        // view render를 위해서 필요한 model의 정보를 같이 넘겨준다
        view.render(model, request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName))); // 파라미터의 값들을 키, 값의 형태로 반복문 돌면서 맵을 생성해서 반환해준다
        return paramMap;
    }
}
