package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV2 {

    // 이 interface를 통해서 여러 컨트롤러를 생성
    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
