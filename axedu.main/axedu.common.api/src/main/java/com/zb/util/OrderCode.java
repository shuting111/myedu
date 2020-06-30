package com.zb.util;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/5/7
 * @Version V1.0
 */
public class OrderCode {

    //订单编号前缀
    public static final String PREFIX = "DD";
    //订单编号后缀（核心部分）
    private static long code;

    // 生成订单编号
    public static synchronized String getOrderCode() {
        code++;
        String str = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        long m = Long.parseLong((str)) * 10000;
        m += code;
        return PREFIX + m;
    }

    public static void main(String[] args) {
        System.out.println(OrderCode.getOrderCode());
    }

}
