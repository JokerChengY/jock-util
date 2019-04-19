/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jock.fex.util.logger;

/**
 *
 * @author think
 */
public class MonitorRequest {
    public static void main(String args[]){
        //System.out.println(HttpRequestUtil.sendPost("http://www.test.xiaodaiba.cn/user/loginVerify.htm", String.valueOf(Math.random())));
        //System.out.println(HttpRequestUtil.sendPost("http://hm.baidu.com/hm.gif","cc=1"));
        //System.out.println(HttpRequestUtil.sendGet("http://hm.baidu.com/hm.js?9abbe660ba1aa5af5fcc49b82252c996",""));
        int i=0;
        for(int j=0;j<10;j++){
        	String url = "http://localhost:8081/pja.gif?pat=11&php=22&pia=33&pad=1212312&pau=asdfasdf12";
            //System.out.println(HttpRequestUtil.sendGet("http://localhost:8080/pja.gif?pat="+i+++"&php="+i+++"&pia="+i+++"&pad="+i+++"&pau=asdfasdf",""));
            System.out.println(HttpRequestUtil.sendGet(url,""));
        }
        
    }
}
