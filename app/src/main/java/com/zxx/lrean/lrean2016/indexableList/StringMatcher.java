package com.zxx.lrean.lrean2016.indexableList;

/**
 * Created by Administrator on 2016/4/26.
 */
public class StringMatcher {
    /**

     * @param value      item的文本
     * @param keyword   索引列表中的字符4
     * @return  keyword 没有在value中找到返回假；
     */
    public static boolean match(String value,String keyword){
        int i=0;//value的指针
        int j=0;//keyword的指针
        //value和keyword参数都不能为空
        if  (value==null||keyword==null){
            return false;
        }
        //value和keyword参数都不能为空67
        if (keyword.length()>value.length()){
            return false;
        }
        do {
            if (keyword.charAt(j)==value.charAt(i)){
                i++;
                j++;
            }else if (j>0){
                break;
            }else{
                i++;
            }

        }while (i<value.length()&&j<keyword.length());
        return (j==keyword.length())?true:false ;
    }
}
