package com.web.wlsms.controller.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class DutyScheduling {
    public static void main(String[] args) {
        // 用户输入窗口
        String aa = null ;
        Calendar calendar = Calendar.getInstance();
        System.out.println("输入下列查询条件之一：(格式: 日期20xx-0x-0x、 姓名xxx、星期xxx) 开始查询！");
        Scanner sc=new Scanner(System.in);
        String[]student_on_duty={"尹理虎","潘琳","王帅","陈勇","王进才","袁其军","岳东","陈谦"};  //人员名单
        //定义各种数组
        String [] riqi=new String[8] ; //值班日期
        String [] paihaohou=new String[8] ; //排班后名单
//        String [] newriqi=new String[31];
        //  这个日期判断会导致日期集合里面出现NULL值，
        for(int i=0;i<riqi.length;i++) {
            calendar.add(Calendar.DATE, +1);
            Date ss=calendar.getTime();
            aa=new SimpleDateFormat( "yyyy-MM-dd EEEE").format(ss) ;
//            if( (!aa.contains("星期六"))) {
//                if((!aa.contains("星期日"))) {
                    riqi[i]=aa;
//                }
//            }
        }
        //去掉riqi数组里面的null值，并把新产生的数组赋值给 newriqi
//        newriqi = DeleteArrayNull.deleteArrayNull(riqi);

        //姓名和日期星期匹配
        for(int j=0;j< riqi.length; j++) {
            // 姓名和日期匹配后写入数组
            paihaohou[j] = (riqi[j]+"是："+student_on_duty[j]+"值日。");
            //    System.out.println(paihaohou[j]); //查看 paihaohou数组值得产生
        }
        //编写查询条件 1.姓名 2.星期  3.日期
        try {
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
        }
        finally {
            System.out.println("谢谢使用！");
        }
        sc.close();
    }
}

//
//         }
//           break;
//
//       //  按星期查找
//        case 1 : { System.out.println("输入星期：");
//            String query_week =sc.next();
//            for(int i=0;i< paihaohou.length;i++) {
//                if( paihaohou[i].contains(query_week  )) {
//                     System.out.println(paihaohou[i]);
//            }
//            }
//        };
//         break ;
//
//         //按日期查找
//        case 2:{   System.out.println("输入日期：");
//            String query_date =sc.next();
//            for(int i=0;i< paihaohou.length;i++) {
//                if( paihaohou[i].contains(query_date )) {
//                     System.out.println(paihaohou[i]);
//            }
//            }
//        };
//        break;
//    }