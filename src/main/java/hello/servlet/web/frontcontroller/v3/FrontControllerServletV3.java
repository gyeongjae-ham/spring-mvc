package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// urlPatterns에 *을 사용해서 이 경로 하위로 들어오는 건 무조건 이 controller를 거치게끔 만든다
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    // 매핑 정보 - 키(url), 값(컨트롤러) : 어떤 url이 들어오면 어떤 controller를 호출하라는 의미
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    // 컨트롤러 별 uri를 키로 설정해서 map에 담아준다
    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // getRequestURI로 /front-controller/v1/members/~~ 부분을 받아온다
        String requestURI = request.getRequestURI();
        // 받아온 URI 정보로 맵에서 해당 컨트롤러의 인스턴스를 불러온다(다형성에 의해서 구현한 부모 인터페이스로 불러올 수 있다)
        ControllerV3 controller = controllerMap.get(requestURI);
        // 없을 경우 예외처리로 404처리 해주고
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // paramMap - createParamMap으로 들어온 request의 파라미터 정보를 다 뽑아서 맵을 생성해준다
        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap); // 불러온 controller 객체에 보내주면 ModelView(논리이름, 모델)의 인스턴스에 논리이름을 담아서 반환해준다
        // mv의 모델 안에 request parameter의 정보들과 논리이름이 담겨있는 상태

        // 위에서 new-form의 형태로 반환되어 온 논리이름을 가지고
        // /WEB-INF/views/new-form.jsp로 만들어 줌
        String viewName = mv.getViewName(); // 논리 이름 new-form만 나옴
        MyView view = viewResolver(viewName);

        // view render를 위해서 필요한 model의 정보를 같이 넘겨준다
        view.render(mv.getModel(), request, response);
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
