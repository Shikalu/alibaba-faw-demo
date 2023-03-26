package com.faw.summer.core;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 鹿胜宝
 * @date 2023/03/26
 */
public class BeanFactory {
    /**
     * 储存对象
     */
    private static Map<String, Object> map = new HashMap<String, Object>();

    //任务一：读取解析xml，通过反射技术实例化对象并且存储待用（map集合）
    static {
        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> beanList = rootElement.selectNodes("//bean");
            for (int i = 0; i < beanList.size(); i++) {
                Element element = beanList.get(i);
                //解析属性
                String id = element.attributeValue("id");
                String clazz = element.attributeValue("class");
                //实例化
                Class<?> aClass = Class.forName(clazz);
                Object instance = aClass.newInstance();
                //存储对象
                map.put(id, instance);
            }

            //解析依赖注入
            List<Element> properties = rootElement.selectNodes("//property");
            for (int i = 0; i < properties.size(); i++) {
                Element element = properties.get(i);
                String name = element.attributeValue("name");
                String ref = element.attributeValue("ref");

                //找到要注入的bean
                Element parent = element.getParent();

                //取出实例
                String parentId = parent.attributeValue("id");
                Object parentObject = map.get(parentId);

                //反射注入，需要setter方法
                Method[] methods = parentObject.getClass().getMethods();
                for (int j = 0; j < methods.length; j++) {
                    Method method = methods[j];
                    if (method.getName().equalsIgnoreCase("set" + name)) {
                        method.invoke(parentObject, map.get(ref));
                    }
                }

                //内省注入，需要getter方法
//                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(name,parentObject.getClass());
//                Method writeMethod = propertyDescriptor.getWriteMethod();
//                writeMethod.invoke(parentObject,map.get(ref));

                //放回实例
                map.put(parentId, parentObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // 任务二：对外提供获取实例对象的接口
    public static Object getBean(String id) {
        return map.get(id);
    }
}
