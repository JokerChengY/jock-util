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
public class Singleton {
    
    static class SingletonHolder{
        static Singleton instance = new Singleton();
    }
    
    public static Singleton getInstance(){
        return SingletonHolder.instance;
    }
}
