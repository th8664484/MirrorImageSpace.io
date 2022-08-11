package com.blog;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class test  {
    public static void main(String[] args) {


        String ss = "/blog/126541245648712";
        String reg = "\\D+(\\d+)$";
        Matcher matcher =  Pattern.compile(reg).matcher(ss);
        long historyHighestLevel = 1;
        if(matcher.find()){
            historyHighestLevel = Long.parseLong(matcher.group(1));
            System.out.println(historyHighestLevel);
        }
    }
}
