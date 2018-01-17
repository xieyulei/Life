package com.xyl.life.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {

    public static SpannableString firstLineIndent(String s,int length){

        String t = s.replaceAll("[\r]\n", "\n");
        SpannableString result=new SpannableString(t);
        char key = '\n';
        List<Integer> list=new ArrayList<>();
        for (Integer index = t.indexOf(key);
             index >= 0;
             index = t.indexOf(key, index + 1)) {
            list.add(index);
        }
        list.add(t.length());

        int startIndex = 0;
        for (int i = 0; i < list.size(); i++) {
            result.setSpan(new LeadingMarginSpan.Standard(length,0), startIndex, list.get(i), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            startIndex = list.get(i) + 1;
        }
        return result;
    }
}
