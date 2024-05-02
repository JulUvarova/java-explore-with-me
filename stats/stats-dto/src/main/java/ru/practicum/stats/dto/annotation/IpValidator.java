package ru.practicum.stats.dto.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpValidator implements ConstraintValidator<CheckIpAddress, String> {
    private static final String IPV4_PATTERN =
            "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";

    private static final Pattern IP_PATTERN = Pattern.compile(IPV4_PATTERN);

    @Override
    public void initialize(CheckIpAddress constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String ip, ConstraintValidatorContext cxt) {
        Matcher matcher = IP_PATTERN.matcher(ip);
        return matcher.matches();
    }
}
