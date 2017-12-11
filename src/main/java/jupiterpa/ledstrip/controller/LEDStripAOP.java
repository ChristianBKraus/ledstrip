package jupiterpa.ledstrip.controller;

import java.util.Arrays;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LEDStripAOP implements MethodInterceptor {
	
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@AfterReturning("execution(* jupiterpa.ledstrip.controller.LEDStripController.*(..))")
	public void logServiceAccess(JoinPoint joinPoint) {
		System.out.println("Completed: " + joinPoint);
	}

	
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        MDC.put("endpoint", "Controller: " + LEDStripController.PATH );
        logger.info(TECHNICAL, "Name: " + methodInvocation.getMethod().getName());
        if (methodInvocation.getArguments().length > 0) {
        	logger.info(TECHNICAL, "Arguments: " + Arrays.toString(methodInvocation.getArguments()));
        }

		try {
			Object result = methodInvocation.proceed();

			logger.info(TECHNICAL, "Result: " + result ); 

			return result;

		} catch (IllegalArgumentException e) {
			logger.error(TECHNICAL, "Exception: " + e ); 
			throw e;
		}
	}
}
