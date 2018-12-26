package com.yiyiglobal.dp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangzukun on 2017/5/19.
 */
public class TxtUtil {

    /**
     * Java读取txt文件的内容
     * @param filePath
     */
    public static List<String> readTxtFile(String filePath){
        List<String> list = new ArrayList<String>();

        try {
            String encoding="utf-8";
            File file=new File(filePath);

            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
//                    System.out.println(lineTxt);
                    list.add(lineTxt);
                    //正则表达式匹配，主要为了匹配视频数据

                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }

        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        return list;

    }

    public static String parse (String s) {
        Pattern pattern =Pattern.compile("a?c");//匹配的模式
        Matcher matcher=pattern.matcher(s);

        while(matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println("test");
        }

        return s;

    }



    public static void main(String argv[]){

        //targetUrl\"\:\"^(?)$\"
        String str ="abcadc";
        parse(str);
//        String filePath = "/Users/wangzukun/Desktop/data/live.txt";

//        readTxtFile(filePath);
    }

}
