package com.bsep.admin.app.utils;

import com.bsep.admin.app.dto.SubjectDto;

import java.util.HashMap;

public class SubjectUtil {


    private static String[] splitSubject(String subjectToSplit){
        return subjectToSplit.split(",");
    }

    private static HashMap<String, String> convertToMap(String[] splitSubject){
        HashMap<String, String> map = new HashMap<>();
        for(String s: splitSubject){
            String[] subjectField = s.split("=");
            map.put(subjectField[0], subjectField[1]);
        }

        return map;
    }

    public static SubjectDto extractSubject(String subjectString){
        SubjectDto subject = new SubjectDto();
        String[] split = splitSubject(subjectString);
        HashMap<String, String> map = convertToMap(split);
        subject.setEmail(map.get("E"));
        subject.setUserId(map.get("UID"));
        subject.setCountry(map.get("C"));
        subject.setOrganizationUnit(map.get("OU"));
        subject.setOrganization(map.get("O"));
        subject.setGivenName(map.get("GIVENNAME"));
        subject.setSurname(map.get("SURNAME"));
        subject.setCommonName(map.get("CN"));
        return subject;
    }
}
