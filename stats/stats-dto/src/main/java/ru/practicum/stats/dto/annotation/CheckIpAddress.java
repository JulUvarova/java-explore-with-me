package ru.practicum.stats.dto.annotation;

import javax.validation.Payload;
import java.lang.annotation.*;

@Documented // аннотация должна быть добавлена в javadoc поля/метода
@Target(ElementType.FIELD) // что мы можем пометить этой аннотацией
@Retention(RetentionPolicy.RUNTIME) // жизненный цикл аннотации - когда она будет присутствовать
public @interface CheckIpAddress {
    String message() default "ip указан неверно";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
