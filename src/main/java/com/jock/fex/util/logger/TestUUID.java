/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jock.fex.util.logger;

import com.jock.fex.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author think
 */
public class TestUUID {
    
    public static void main(String args[]) throws Exception{
//        System.out.println(UUID.randomUUID());
//        String testStr = null;
//        switch(testStr){
//            case "1":
//                System.out.println("1");
//            default :
//                System.out.println("null");
//        }
        String demo = "123  4   5";
        String[] split = demo.split("\\t");
        for(String str : split){
            //System.out.println(str);
        }
        System.out.println(StringUtils.replaceTab(demo));
        //System.out.println("\t");
    }
    
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("/^\\[ \t]*$/ ");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    
    
    
}
