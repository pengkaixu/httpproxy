package com.i.information.util;

import java.util.regex.Pattern;

/**
 * @Auther: peng
 * @Date: 2021/11/30 0030 17:10
 * @Description:
 */
public class Illegalchara {
    private static String utf8Ch="[\u4E00-\u9FFF]";
    private static String gbkCh="";

    static Pattern ch=Pattern.compile(utf8Ch);

    public static boolean regCh(String str){
        return ch.matcher(str).find();
    }

//    public static void main(String[] args) {
//        String[] str=new String[]{"怂", "耜", "耩", "耸", "肃", "肆", "臧", "所", "艘", "苏", "损", "捱", "荽", "菘", "搜", "摭", "摺", "撕", "蒜", "蒴", "蓑", "蕺", "斯", "晴", "虽", "蛳", "朔", "蜥", "松", "螋", "梭", "榫", "死", "讼", "诉", "毓", "诵", "毽", "谘", "谡", "氵", "汜", "泗", "账", "淞", "跤", "丝", "丿", "溯", "亻", "俗", "送","速","遂","邃","烁","僳","ㄇ","酥","酸","凇","燧","狲","卅","厮","厶","叟","司","琐","锁","唆","锟","唢","锶","锼","嗉","嗣","嗦","嗽","瘀","嘶","隋","随","隧","隳","隼","四","睢","垅","塑","硕","颂","碎","墼","祀","祟","饲","妁","私","姒","娑","稣","穗","髓","笋","孙","宋","算","箝","寺","尬","尴","岁","粜","粟","素","索","嵇","嵩","嵴","巳","鸶","鹏","绥","缩","羧","翊"};
//        for(String c:str){
//            if(!regCh(c)){
//                System.out.print(c);
//                System.out.print(" ");
//            }
//
//        }
//
//    }
}
