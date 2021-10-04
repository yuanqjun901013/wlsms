package com.web.wlsms.controller.test;

import com.alibaba.fastjson.JSON;

public class MainTest {
    public static void main(String[] args) {
        String str = "1/2";
        String[] strArr= str.split("/");
        boolean isNumA = strArr[1].matches("[0-9]+");
        boolean isNumB = strArr[0].matches("[0-9]+");
        if(isNumA && isNumB) {
            int a = Integer.parseInt(strArr[1]), b = Integer.parseInt(strArr[0]);//a 是分母
            int gcd = gcd(a, b);
            System.out.println(b / gcd + "/" + a / gcd); // 输出了 5/6
        }else {
            System.out.println(JSON.toJSONString(strArr));
        }
    }
    public static int gcd(int x, int y){ // 这个是运用辗转相除法求 两个数的 最大公约数 看不懂可以百度 // 下
        if(y == 0)
            return x;
        else
            return gcd(y,x%y);
    }
}
