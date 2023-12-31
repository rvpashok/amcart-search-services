package com.amcart.search.util;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class CommonUtil {
    public static String escapeMetaCharacters(String inputString){
        final String[] metaCharacters = {"+","-","$","{","}","[","]",
                "(",")",".","*","=","?","|","~",":","-","&","%","\\"
                ,"/","^","!"};
        for (int i = 0 ; i < metaCharacters.length ; i++){
            if(inputString.contains(metaCharacters[i])){
                inputString = inputString.replace(metaCharacters[i],"\\\\"+metaCharacters[i]);
            }
        }
        return inputString;
    }

    public static String replaceUnwantedCharacter(String inputString){
        String toRet = "";
        if(Objects.nonNull(inputString) && !inputString.isBlank()){
            toRet = inputString.replaceAll("[><]","");
        }
        return toRet;
    }
}
