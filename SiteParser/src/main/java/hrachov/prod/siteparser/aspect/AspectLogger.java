package hrachov.prod.siteparser.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogger {
    private static Logger logger = LoggerFactory.getLogger(AspectLogger.class.getName());

    @Before("execution(* hrachov.prod.siteparser..*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info(" >>> " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }
    @After("execution(* hrachov.prod.siteparser..*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info(" <<< " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

    }
}
