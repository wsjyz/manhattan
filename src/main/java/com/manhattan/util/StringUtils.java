package com.manhattan.util;

/**
 * Created by dam on 14-6-23.
 */
public class StringUtils {

    /**
     *暂时不支持去掉连续逗号
     * @param input
     * @return
     */
    public static String deleteComma(String input){
        if(input != null){
            StringBuilder str = new StringBuilder(input);
            if(str.indexOf(",") == 0){
                str.delete(0,1);
            }
            if(str.lastIndexOf(",") == str.length() -1){
                str.delete(str.length() - 1,str.length());
            }
            return str.toString();
        }
        return "";
    }
    public static String formatInStr(String input){
        String[] arr = input.split(",");
        StringBuilder strb = new StringBuilder("");
        for(String str:arr){
            if(!str.equals("")){
                strb.append(str).append(",");
            }
        }
        return deleteComma(strb.toString());
    }
    public static void main(String[] args) {
        System.out.println(StringUtils.formatInStr("a,b,c"));
        System.out.println(StringUtils.formatInStr(",a,b,c"));
        System.out.println(StringUtils.formatInStr("a,b,c,"));
        System.out.println(StringUtils.formatInStr("a"));
    }
}
