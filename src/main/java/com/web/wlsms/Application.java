package com.web.wlsms;

import io.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@Slf4j
@SpringBootApplication(exclude = {SpringBootConfiguration.class})
@ComponentScan(basePackages = {"com.web.wlsms.*"})

public class Application {

    public static void main(String[] args){
//        try {
            SpringApplication.run(Application.class,args);
//            System.out.println("Server startup done.");
//        }catch (Exception e){
//            log.error("服务xxx-support启动报错", e);
//        }
    }


    /**
     * destroy-method="close"的作用是当数据库连接不使用的时候,就把该连接重新放到数据池中,方便下次使用调用.
     * @return
     */
//    @Bean(destroyMethod = "close")
//    views DataSource dataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl(env.getProperty("spring.datasource.url"));
//        dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名
//        dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码
//        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
//        dataSource.setInitialSize(2);//初始化时建立物理连接的个数
//        dataSource.setMaxActive(20);//最大连接池数量
//        dataSource.setMinIdle(0);//最小连接池数量
//        dataSource.setMaxWait(60000);//获取连接时最大等待时间，单位毫秒。
//        dataSource.setValidationQuery("SELECT 1");//用来检测连接是否有效的sql
//        dataSource.setTestOnBorrow(false);//申请连接时执行validationQuery检测连接是否有效
//        dataSource.setTestWhileIdle(true);//建议配置为true，不影响性能，并且保证安全性。
//        dataSource.setPoolPreparedStatements(false);//是否缓存preparedStatement，也就是PSCache
//        return dataSource;
//    }

//    @Autowired
//    private ProductService productService;



//    @RequestMapping("/home")
//    views String home()
//    {
//        List<Product> productList = productService.getProductList();
//        StringBuilder sb=new StringBuilder();
//        sb.append("<html>");
//        sb.append("<head>");
//        sb.append("<meta charset='UTF-8'><title>云商城在线商品查询</title>");
//        sb.append("<style> " +
//                "body{text-align:center;margin-left:auto;margin-right:auto; width:1200;}" +
//                "a{text-decoration:none;}"+
//                "</style>");
//        sb.append("</head>");
//        sb.append("<body>");
//        sb.append("<table border='1' style='border-width: 1px;" +
//                "font-family:verdana,arial,sans-serif;" +
//                "font-size:11px;color:#333333;" +
//                "border-color: #666666;" +
//                "border-collapse: collapse;'>");
//        sb.append("<tr>");
//        sb.append("<th width='100' align=center nowrap><font color='486d99'>商品编号</font></th>");
//        sb.append("<th width='300' align=center nowrap><font color='486d99'>商品名称</font></th>");
//        sb.append("<th width='100' align=center nowrap><font color='486d99'>商品类型</font></th>");
//        sb.append("<th width='600' align=center nowrap><font color='486d99'>商品简介</font></th>");
//        sb.append("<th width='100' align=center nowrap><font color='486d99'>操作</font></th>");
//        sb.append("</tr>");
//        for(Product product:productList){
//            sb.append("<tr>");
//            sb.append("<td width='100' style='word-break:break-all' align=center nowrap>"+product.getId()+"</td>");
//            sb.append("<td width='300' style='word-break:break-all' align=center nowrap>"+product.getName()+"</td>");
//            sb.append("<td width='100' style='word-break:break-all' align=center nowrap>"+product.getType()+"</td>");
//            sb.append("<td width='600' style='word-break:break-all' nowrap>"+product.getDescription()+"</td>");
//            sb.append("<td width='100' style='word-break:break-all' align=center nowrap><a href='javascript:void(0)' onclick='queryProductDetail("+product.getId()+")'>【查看详情】</a></td>");
//            sb.append("</tr>");
//        }
//        sb.append("<script type='text/javascript'>" +
//                "var trs=document.getElementsByTagName('tr');" +
//                "for(var i=0; i<trs.length;i++){" +
//                "if(i%2==0){" +
//                "trs[i].style.background='#ffffff';" +
//                "}else{"+
//                "trs[i].style.background='#D4D4D4';" +
//                "}" +
//                "}" +
//                "</script>");
//        sb.append("</table>");
//        sb.append("</body>");
//        sb.append("<script type='text/javascript'>" +
//                "function queryProductDetail(id){" +
//                "alert(id);" +
//                "}" +
//                "</script>");
//        sb.append("</html>");
//        return sb.toString();
//    }
//
//    @RequestMapping("/learn")
//    views String learn()
//    {
//        Product productInfo = productService.queryProductById(3000002203L);
//        List<Product> productList = new ArrayList<Product>();
//        productList.add(productInfo);
//        StringBuilder sb=new StringBuilder();
//        sb.append("<html>");
//        sb.append("<head>");
//        sb.append("<meta charset='UTF-8'><title>云商城在线商品查询</title>");
//        sb.append("<style> " +
//                "body{text-align:center;margin-left:auto;margin-right:auto; width:1200;}" +
//                "a{text-decoration:none;}"+
//                "</style>");
//        sb.append("</head>");
//        sb.append("<body>");
//        sb.append("<table border='1' style='border-width: 1px;" +
//                "font-family:verdana,arial,sans-serif;" +
//                "font-size:11px;color:#333333;" +
//                "border-color: #666666;" +
//                "border-collapse: collapse;'>");
//        sb.append("<tr>");
//        sb.append("<th width='100' align=center nowrap><font color='486d99'>商品编号</font></th>");
//        sb.append("<th width='300' align=center nowrap><font color='486d99'>商品名称</font></th>");
//        sb.append("<th width='100' align=center nowrap><font color='486d99'>商品类型</font></th>");
//        sb.append("<th width='600' align=center nowrap><font color='486d99'>商品简介</font></th>");
//        sb.append("<th width='100' align=center nowrap><font color='486d99'>操作</font></th>");
//        sb.append("</tr>");
//        for(Product product:productList){
//            sb.append("<tr>");
//            sb.append("<td width='100' style='word-break:break-all' align=center nowrap>"+product.getId()+"</td>");
//            sb.append("<td width='300' style='word-break:break-all' align=center nowrap>"+product.getName()+"</td>");
//            sb.append("<td width='100' style='word-break:break-all' align=center nowrap>"+product.getType()+"</td>");
//            sb.append("<td width='600' style='word-break:break-all' nowrap>"+product.getDescription()+"</td>");
//            sb.append("<td width='100' style='word-break:break-all' align=center nowrap><a href='javascript:void(0)' onclick='queryProductDetail("+product.getId()+")'>【查看详情】</a></td>");
//            sb.append("</tr>");
//        }
//        sb.append("<script type='text/javascript'>" +
//                "var trs=document.getElementsByTagName('tr');" +
//                "for(var i=0; i<trs.length;i++){" +
//                "if(i%2==0){" +
//                "trs[i].style.background='#ffffff';" +
//                "}else{"+
//                "trs[i].style.background='#D4D4D4';" +
//                "}" +
//                "}" +
//                "</script>");
//        sb.append("</table>");
//        sb.append("</body>");
//        sb.append("<script type='text/javascript'>" +
//                "function queryProductDetail(id){" +
//                "alert(id);" +
//                "}" +
//                "</script>");
//        sb.append("</html>");
//        return sb.toString();
//    }
//
//    @RequestMapping("/b2bCap")
//    views String b2bCap(HttpServletRequest request,
//                         HttpServletResponse response) throws RemoteException {
//        Object jsonString= new Object();
//        try {
////            String endpoint = "http://36.152.19.16/services/userLogin?wsdl";
//            String endpoint = "http://127.0.0.1:8086/services/userLogin?wsdl";
//            //调用接口的targetNamespace
//            String targetNamespace = "http://userLogin.webservice.impl.b2cap.suning.com";
//            //所调用接口的方法method
//            String method = "fyLoginDetail";
//            // 创建一个服务(service)调用(call)
//            Service service = new Service();
//            Call call = (Call) service.createCall();// 通过service创建call对象
//            // 设置service所在URL
//            call.setTargetEndpointAddress(new java.net.URL(endpoint));
//            call.setOperationName(new QName(targetNamespace, method));
//            call.setUseSOAPAction(true);
//            //变量最好只是用String类型，其他类型会报错
//            call.addParameter("userId", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//            call.addParameter("orgCode", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);//设置参数名 state  第二个参数表示String类型,第三个参数表示入参
//            call.addParameter("orgName", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//            call.addParameter("userNo", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//            call.addParameter("costcenter1", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//            call.addParameter("costcenter2", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//            call.addParameter("costcenter3", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//            call.addParameter("costcenter4", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
////            System.getProperties().setProperty("http.proxyHost", "10.27.199.6");
////            System.getProperties().setProperty("http.proxyPort", "3128");
//            jsonString = call.invoke(new Object[]{"941", "8700001", "复翼高校1", "00010001000", "","","",""});
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return jsonString.toString();
//    }
//
//    @RequestMapping("/sync")
//    views String sync(HttpServletRequest request,
//                         HttpServletResponse response) throws RemoteException {
//        Object jsonString= new Object();
//        try {
////            String endpoint = "http://36.152.19.16/services/userInfoSync?wsdl";
//            String endpoint = "http://127.0.0.1:8086/services/userInfoSync?wsdl";
//            //调用接口的targetNamespace
//            String targetNamespace = "http://userLogin.webservice.impl.b2cap.suning.com";
//            //所调用接口的方法method
//            String method = "userInfoSync";
//            // 创建一个服务(service)调用(call)
//            Service service = new Service();
//            Call call = (Call) service.createCall();// 通过service创建call对象
//            // 设置service所在URL
//            call.setTargetEndpointAddress(new java.net.URL(endpoint));
//            call.setOperationName(new QName(targetNamespace, method));
//            call.setUseSOAPAction(true);
//            //变量最好只是用String类型，其他类型会报错
//            call.addParameter("json", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
////            System.getProperties().setProperty("http.proxyHost", "10.27.199.6");
////            System.getProperties().setProperty("http.proxyPort", "3128");
//            jsonString = call.invoke(new Object[]{"{\"orgName\": \"复翼高校1\",\"orgCode\": \"8700001\",\"data\": [{\"userName\": \"李老师\",\"userNo\": \"00010001000\",\"mobileNum\": \"18512580001\",\"enable\": 1}]}"});
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return jsonString.toString();
//    }

}
