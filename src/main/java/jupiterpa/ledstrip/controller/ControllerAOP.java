package jupiterpa.ledstrip.controller;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAOP
{
	
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Around("execution(* jupiterpa.ledstrip.controller.LEDStripController.*(..))")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
		
        MDC.put("endpoint", LEDStripController.PATH );
        
        logger.info(TECHNICAL, "HTTP {}", joinPoint.getSignature().getName());
        if (joinPoint.getArgs().length > 0) {
        	logger.info(TECHNICAL, "   - {}", Arrays.toString(joinPoint.getArgs()));
        }

		try {
			Object result = joinPoint.proceed();
			logger.info(TECHNICAL, "  -> {}", result ); 
			return result;
		} catch (Throwable e) {
			logger.error(TECHNICAL, "  => {}", e ); 
			e.printStackTrace();
			throw e;
		}
	}
}
