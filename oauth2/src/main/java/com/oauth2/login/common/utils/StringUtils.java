package com.oauth2.login.common.utils;

import lombok.NoArgsConstructor;
import org.slf4j.helpers.MessageFormatter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class StringUtils {

    /**
     * 형식과 객체가 주어졌을 때 해당 형식에 맞춰 객채를 배치한다.
     * @param format
     * @param objects
     * @return
     */
    public static String format(String format, Object... objects) {
        return MessageFormatter.arrayFormat(format, objects).getMessage();
    }

    public static String encodeString(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }
}