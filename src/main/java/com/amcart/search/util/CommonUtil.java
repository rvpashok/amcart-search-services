package com.amcart.search.util;

import lombok.experimental.UtilityClass;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

@UtilityClass
public class CommonUtil {
    public static String escapeMetaCharacters(String inputString) {
        final String[] metaCharacters = {"+", "-", "$", "{", "}", "[", "]",
                "(", ")", ".", "*", "=", "?", "|", "~", ":", "-", "&", "%", "\\"
                , "/", "^", "!"};
        for (int i = 0; i < metaCharacters.length; i++) {
            if (inputString.contains(metaCharacters[i])) {
                inputString = inputString.replace(metaCharacters[i], "\\\\" + metaCharacters[i]);
            }
        }
        return inputString;
    }

    public static String decodeUriString(String inputStr){
        String toRet = null;
        try {
            if(Objects.nonNull(inputStr) && !inputStr.isBlank()){
                toRet = URLDecoder.decode(inputStr, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return toRet;
    }

    public static String replaceUnwantedCharacter(String inputString) {
        String toRet = "";
        if (Objects.nonNull(inputString) && !inputString.isBlank()) {
            toRet = inputString.replaceAll("[><]", "");
        }
        return toRet;
    }
}
