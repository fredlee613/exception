package hello.exception.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                return new ModelAndView();
                // argument 없이 return 할 경우, servlet container까지 정상적으로 진행됨. (에러 없는 것 처럼)
                // exception을 sendError로 바꿔쳐서 정상 흐름처럼 변경하는 것이 목적이다.
            }
        } catch (IOException e) {
            log.info("resolver ex", e);
        }
        return null;
        // 빈 ModelAndView : 뷰를 렌더링 하지 않고, 정상 흐름으로 서블릿이 리턴
        // ModelAndView 지정: 뷰를 렌더링
        // null: 다음 ExceptionResolver를 찾아서 실행. 만약 처리할 수 없는 ExceptionResolver가 없으면 예외 처리 X, 기본에 발생한 예외를 서블릿 밖으로 던진다.
    }
}
