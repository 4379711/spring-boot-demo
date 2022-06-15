package com.example.demo.utils;


import com.example.demo.exception.BaseException;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author YaLong
 */
public class ResponseUtil {

    public static void setExcelResponse(HttpServletResponse response,String fileName){
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        try {
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new BaseException("文件名不合法");
        }
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);


    }
}
