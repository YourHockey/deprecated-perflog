package ru.vakoom.yourhockey.perflog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class PerformanceLoggerAspect {

	@Pointcut("@within(annotation) || @annotation(annotation)")
	public void methodsAndPublicMethodsOfClassesWithMeasurePerformanceAnnotation(MeasurePerformance annotation) {}

	@Around(value = "methodsAndPublicMethodsOfClassesWithMeasurePerformanceAnnotation(annotation)", argNames = "joinPoint,annotation")
	public Object measurePerformance(ProceedingJoinPoint joinPoint, MeasurePerformance annotation) {
		final String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
		final String methodName = joinPoint.getSignature().getName();

		final var stopWatch = new StopWatch();
		Throwable exception = null;
		Object result;

		log.info("Start method {}.{}()", className, methodName);
		stopWatch.start();
		try {
			result = joinPoint.proceed();
		} catch (Throwable e) {
			exception = e;
			throw new RuntimeException(e);
		} finally {
			stopWatch.stop();
			if (exception != null) {
				log.error(
					"Exception catches inside PerformanceLoggerAspect ({}.{}()) with message {}. Duration {}ms",
					className, methodName, exception.getMessage(), stopWatch.getTotalTimeMillis()
				);
			} else {
				log.info("Call of {}.{}() was completed successfully", className, methodName);
				log.info("Duration of call: {}.{}() - {}ms", className, methodName, stopWatch.getTotalTimeMillis());
			}
		}
		return result;
	}
}
