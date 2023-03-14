package com.ebanma.cloud.tmpTest;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 鹿胜宝
 * @date 2023/03/12
 */
public class Tmp {

    @Test
    public void test() throws InterruptedException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
        Method capacity = map.getClass().getDeclaredMethod("capacity");
        capacity.setAccessible(true);
        Object o = capacity.invoke(map);
        System.out.println(o);
        List<String> list = new ArrayList<String>(){{
            add("1");
            add("2");
            add("3");
        }};
        for (String s : list) {
            if("2".equals(s)) {
                int i = list.indexOf(s);
                list.set(i,"5");
            }
        }
        System.out.println(list);
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(date.format(df));

        String dateString = "2022-11-23";
        DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString,df2);
        System.out.println(localDate);
    }
}
