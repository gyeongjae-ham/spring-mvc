package hello.servlet.web.frontcontroller.v1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV1 {

    // 이 interface를 통해서 여러 컨트롤러를 생성
    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
