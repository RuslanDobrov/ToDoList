package todolist.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.stream.Stream;

@Component
@Aspect
public class AuthenticationAspects {
    private final AuthenticationUtils authenticationUtils;

    public AuthenticationAspects(AuthenticationUtils authenticationUtils) {
        this.authenticationUtils = authenticationUtils;
    }

    @Pointcut("execution(@todolist.annotations.Authenticational * *(..))")
    public void annotatedByAuthenticational() {}

    @Around("annotatedByAuthenticational()")
    public Object around(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = findRequest(joinPoint);
        if (request == null) {
            throw new RuntimeException("Method annotated by @Authentication must have HttpServletRequest argument");
        }
        Object target = joinPoint.getTarget();
        return authenticationUtils.performAfterAuthentication(request, (userId) -> {
            try {
                Field userIdField = target.getClass().getDeclaredField("userId");
                userIdField.setAccessible(true);
                userIdField.set(target, userId);
                ResponseEntity<Object> result = (ResponseEntity<Object>) joinPoint.proceed();
                userIdField.set(target, null);
                userIdField.setAccessible(false);
                return result;
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Class with method annotated by @Authenticational must have userId field", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }

    private HttpServletRequest findRequest(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = (HttpServletRequest) Stream.of(joinPoint.getArgs()).filter(arg -> HttpServletRequest.class.isAssignableFrom(arg.getClass())).findFirst().orElse(null);
        return request;
    }
}
