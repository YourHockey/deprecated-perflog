package ru.vakoom.yourhockey.perflog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation class that enables to measure the public methods.
 * If this annotation is used on class (type) level, then all public methods in the class are measured.
 * If a public method is annotated, then the execution time of this method is measured.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MeasurePerformance {
	boolean isScrappingNeeded() default false;
}
