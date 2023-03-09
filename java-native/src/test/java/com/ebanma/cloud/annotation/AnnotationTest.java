package com.ebanma.cloud.annotation;

import org.junit.Test;

import java.io.Console;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationTest {

//    Annotation in Method 'public java.lang.String com.ebanma.cloud.annotation.AnnotationTest.toString()' : @com.ebanma.cloud.annotation.MyAnnotation(title=toStringMethod, description=override toString method)
//    toStringMethod
//    Annotation in Method 'public static void com.ebanma.cloud.annotation.AnnotationTest.oldMethod()' : @java.lang.Deprecated()
//    Annotation in Method 'public static void com.ebanma.cloud.annotation.AnnotationTest.oldMethod()' : @com.ebanma.cloud.annotation.MyAnnotation(title=old static method, description=deprecated old static method)
//    old static method
//    Annotation in Method 'public static void com.ebanma.cloud.annotation.AnnotationTest.genericsTest() throws java.io.FileNotFoundException' : @com.ebanma.cloud.annotation.MyAnnotation(title=test method, description=suppress warning static method)
//    test method

    @Override
    @MyAnnotation(title = "toStringMethod", description = "override toString method")
    public String toString() {
        return "Override toString method";
    }

    @Deprecated
    @MyAnnotation(title = "old static method", description = "deprecated old static method")
    public static void oldMethod() {
        System.out.println("old method, don't use it.");
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    @MyAnnotation(title = "test method", description = "suppress warning static method")
    public static void genericsTest() throws FileNotFoundException {
        List<String> l = new ArrayList();
        l.add("abc");
        oldMethod();
    }


    @Test
    public void test1() {
        try {
            // 获取所有methods
            Method[] methods = AnnotationTest.class.getClassLoader()
                    .loadClass(("com.ebanma.cloud.annotation.AnnotationTest"))
                    .getMethods();

            // 遍历
            for (Method method : methods) {
                // 方法上是否有MyMethodAnnotation注解
                if (method.isAnnotationPresent(MyAnnotation.class)) {
                    try {
                        // 获取并遍历方法上的所有注解
                        for (Annotation anno : method.getDeclaredAnnotations()) {
                            System.out.println("Annotation in Method '"
                                    + method + "' : " + anno);
                        }

                        // 获取MyMethodAnnotation对象信息
                        MyAnnotation methodAnno = method
                                .getAnnotation(MyAnnotation.class);

                        System.out.println(methodAnno.title());

                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test2() {
        Console console = System.console();
        System.out.println(console);
    }
}
