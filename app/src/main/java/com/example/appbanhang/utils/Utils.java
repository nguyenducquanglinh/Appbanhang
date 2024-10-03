package com.example.appbanhang.utils;

import com.example.appbanhang.Model.GioHang;
import com.example.appbanhang.Model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL="http://192.168.55.111/banhang/";
    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static User user_current = new User();
    public static String statusOrser(int status){
        String result = "";
        switch (status){
        case 0:
            result = "Đơn hàng đang được xử lí";
            break;
            case 1:
                result = "Đơn hàng được tiếp nhận";
                break;
            case 2:
                result = "Đơn hàng đã được giao cho đơn vị vận chuyển";
                break;
            case 3:
                result = "Giao hàng thành công";
                break;
            case 4:
                result = "Đơn hàng đã được hủy";
                break;
            default:
                result = "...";
        }

        return result;
    }
}
