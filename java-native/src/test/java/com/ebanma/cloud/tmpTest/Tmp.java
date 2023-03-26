package com.ebanma.cloud.tmpTest;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 鹿胜宝
 * @date 2023/03/12
 */
public class Tmp {

    @Test
    public void test() throws InterruptedException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        //测试map初始容量
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        Method capacity = map.getClass().getDeclaredMethod("capacity");
        capacity.setAccessible(true);
        Object o = capacity.invoke(map);
        System.out.println(o);

        //增强for修改数据测试
        List<String> list = new ArrayList<String>() {

            private static final long serialVersionUID = 2599778643680741569L;

            {
                add("1");
                add("2");
                add("3");
            }
        };
        for (String s : list) {
            if ("2".equals(s)) {
                int i = list.indexOf(s);
                list.set(i, "5");
            }
        }
        System.out.println(list);

        //LocalDateTime 线程安全格式化方式测试
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(date.format(df));

        String dateString = "2022-11-23";
        DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, df2);
        System.out.println(localDate);
    }

    static ThreadLocal<Person> tl = new ThreadLocal<Person>();

    static class Person {
        String name = "yangguo";
    }

    // ThreadLocal练习
    @Test
    public void test2() throws InterruptedException {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get());
        }, "A").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person());         //后面用完需要tl.remove();最好放到finally中
        }, "B").start();

        Thread.sleep(5000);
    }

    @Test
    public void test3() {
        HashMap<String, String> map = new HashMap<String,String>();
        map.put("a", "1");
        System.out.println(map.get("a"));
    }
}
