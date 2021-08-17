package com.web.wlsms.controller.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class DutySchedulingNew {
    public static void main(String[] args) {
        // 用户输入窗口
        System.out.println("输入下列查询条件之一：(格式: 日期20xx-0x-0x、 月份20xx-0x) 开始查询！");
        Scanner sc=new Scanner(System.in);
        System.out.println("输入起始日期：");
        String buildDate =sc.next();
        try {
        String buildTime = buildDate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date tokenTime = formatter.parse(buildTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tokenTime);
//        calendar.add(Calendar.MONTH, 0);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String[] student_on_duty={"尹理虎","潘琳","王帅","陈勇","王进才","袁其军","岳东","陈谦"};  //人员名单
        //定义各种数组
        String [] riqi = new String[50000] ; //值班日期
        String [] paihaohou = new String[50000] ; //排班后名单
        //  这个日期判断会导致日期集合里面出现NULL值
        String aa = "";
        for(int i=0;i < riqi.length;i++) {
            Date ss=calendar.getTime();
            aa=new SimpleDateFormat( "yyyy-MM-dd EEEE").format(ss) ;
            riqi[i]=aa;
            calendar.add(Calendar.DATE, +1);
        }
        int ssr = 0;
        //姓名和日期星期匹配
        for(int j = 0; j < riqi.length; j++) {
        // 姓名和日期匹配后写入数组
        paihaohou[j] = (riqi[j] + ":" + student_on_duty[ssr]);
         ssr++;
        if(ssr > 7){
            ssr = 0;
        }
        }
        //编写查询条件 1.姓名 2.星期  3.日期
            System.out.println("输入查询条件：");
            String query_name =sc.next();
            for(int i=0;i< paihaohou.length;i++) {
                if( paihaohou[i].contains( query_name )) {
                    System.out.println(paihaohou[i]);
                }
            }
        }
        // 最好写个异常方法类，这 里直接拿来用
        catch(IndexOutOfBoundsException e) {
            System.out.println("超过查询范围了，请重新输入");
        }catch(NullPointerException e) {
            System.out.println("没有查到相关信息，请重新输入查询条件！");
        } catch (Exception e) {
           System.out.println("查询异常！");
      } finally {
            System.out.println("谢谢使用！");
        }
        sc.close();
    }
}