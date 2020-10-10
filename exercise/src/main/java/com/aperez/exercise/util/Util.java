package com.aperez.exercise.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class Util {

    public static boolean validateStringPatter(String input, String stringPattern) {
        log.info("Validacion formato {} con {}", input, stringPattern);

        Pattern pattern = Pattern.compile(stringPattern);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }


}
