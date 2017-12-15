package jupiterpa.ledstrip.service;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GPIOAOP
{
	
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired private CounterService counterService;
	
	@Around("execution(* jupiterpa.ledstrip.service.GPIO.*(..))")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        logger.info(TECHNICAL, " GPIO {}", Arrays.toString(joinPoint.getArgs()));
		counterService.increment("counter.led.update");

		try {
			return joinPoint.proceed();
		} catch (Throwable e) {
			logger.error(TECHNICAL, "  => " + e ); 
			e.printStackTrace();
			counterService.increment("counter.led.fail");
			throw e;
		}
	}
}
