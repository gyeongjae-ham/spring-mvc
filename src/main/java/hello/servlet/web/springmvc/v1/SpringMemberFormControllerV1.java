package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// @Controller 안에 @Component가 있어서 스프링이 자동으로 빈을 등록해준다
// 스프링 MVC에서 에노테이션 기반 컨트롤러로 인식한다(RequestMappingHandlerMapping에서 hadler 정보라고 인식한다)
@Controller
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form") // 요청 정보를 매핑한다, 해당 URL이 호출되면 메서드가 호출된다
    public ModelAndView process() {
        return new ModelAndView("new-form"); // 모델과 뷰 정보를 담아서 반환한다
    }
}
