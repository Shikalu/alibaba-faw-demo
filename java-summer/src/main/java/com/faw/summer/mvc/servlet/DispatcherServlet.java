package com.faw.summer.mvc.servlet;

import com.faw.summer.mvc.annotation.Autowired;
import com.faw.summer.mvc.annotation.Controller;
import com.faw.summer.mvc.annotation.RequestMapping;
import com.faw.summer.mvc.annotation.Service;
import com.faw.summer.mvc.model.Handler;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 鹿胜宝
 * @date 2023/03/29
 */
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 4043252848966228233L;

    private Properties properties = new Properties();

    //扫描到的类的全限定类名
    private List<String> classNames = new ArrayList<>();

    //Ioc容器
    private Map<String, Object> ioc = new HashMap<>();

    private List<Handler> handlers = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Handler handler = getHandler(req);
        if (handler == null) {
            resp.getWriter().write("404 not found");
            return;
        }

        Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
        Object[] objects = new Object[parameterTypes.length];

        //获取实际入参 ？name=1&type=2
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (Entry<String, String[]> entry : parameterMap.entrySet()) {
            String join = StringUtils.join(entry.getValue(), ",");

            if (!handler.getParaIndexMapping().containsKey(entry.getKey())) {
                continue;
            }
            Integer index = handler.getParaIndexMapping().get(entry.getKey());
            objects[index] = join;
        }
        //处理映射转发
        try {
            handler.getMethod().invoke(handler.getController(), objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Handler getHandler(HttpServletRequest req) {
        if (handlers.isEmpty()) {
            return null;
        }

        String requestURI = req.getRequestURI();
        for (Handler handler : handlers) {
            Matcher matcher = handler.getPattern().matcher(requestURI);
            if (!matcher.matches()) {
                continue;
            }
            return handler;
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 1. 前端控制器加载配置文件
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        doLoadConfig(contextConfigLocation);
        // 2. 包扫描，扫描相关注解
        doScan(properties.getProperty("scanPackage"));
        // 3. IOC容器进行相关Bean初始化
        doInstance();
        // 4. 依赖注入
        doAutowired();
        // 5. 通过相关组件建立url和method之前的映射关系
        initHandlerMapping();
        System.out.println("自定义MVC初始化完成！");

    }

    private void initHandlerMapping() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> aClass = entry.getValue().getClass();

            if (!aClass.isAnnotationPresent(Controller.class)) {
                continue;
            }

            String baseUrl = "";
            if (aClass.isAnnotationPresent(RequestMapping.class)) {
                baseUrl = aClass.getAnnotation(RequestMapping.class).value();
            }

            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }
                String methodUrl = method.getAnnotation(RequestMapping.class).value();
                String url = baseUrl + methodUrl;

                Handler handler = new Handler(entry.getValue(), method, Pattern.compile(url));

                //计算参数
                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];

                    if (parameter.getType() == HttpServletRequest.class ||
                            parameter.getType() == HttpServletResponse.class) {
                        String simpleName = parameter.getType().getSimpleName();
                        handler.getParaIndexMapping().put(simpleName, i);
                    } else {
                        handler.getParaIndexMapping().put(parameter.getName(), i);
                    }
                }
                handlers.add(handler);

            }
        }
    }

    private void doAutowired() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Entry<String, Object> entry : ioc.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();

            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowired.class)) {
                    continue;
                }
                //有@autowired注解的处理
                String beanName = field.getAnnotation(Autowired.class).value();
                if (StringUtils.isBlank(beanName)) {
                    beanName = field.getType().getName();
                }
                //依赖注入
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        try {
            for (String className : classNames) {
                Class<?> aClass = Class.forName(className);
                // @Controller 注解
                if (aClass.isAnnotationPresent(Controller.class)) {
                    String simpleName = aClass.getSimpleName();
                    ioc.put(lowerFirst(simpleName), aClass.newInstance());
                }
                // @Service 注解
                else if (aClass.isAnnotationPresent(Service.class)) {
                    //获取@Service注解的value值，以实现类名为id存一份。
                    String value = aClass.getAnnotation(Service.class).value();
                    if (!StringUtils.isBlank(value)) {
                        ioc.put(lowerFirst(value), aClass.newInstance());
                    } else {
                        String simpleName = aClass.getSimpleName();
                        ioc.put(lowerFirst(simpleName), aClass.newInstance());
                    }
                    //面向接口开发，以接口名为ID也存一份
                    Class<?>[] interfaces = aClass.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
                        ioc.put(anInterface.getName(), aClass.newInstance());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void doScan(String scanPackage) {
        //com.faw --->com/faw
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + scanPackage.replaceAll("\\.", "/");
        File filePath = new File(path);
        File[] files = filePath.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                //如果是目录结构，递归寻找
                doScan(scanPackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                //如果是class文件，存储全限定类名
                String className = scanPackage + "." + file.getName().replaceAll(".class", "");
                classNames.add(className);
            }
        }

    }

    private void doLoadConfig(String contextConfigLocation) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字符串首字母变为小写
     *
     * @param str str
     * @return {@link String }
     * @author 鹿胜宝
     */
    private String lowerFirst(String str) {
        if (!StringUtils.isBlank(str)) {
            char[] chars = str.toCharArray();
            if ('A' <= chars[0] && chars[0] <= 'Z') {
                chars[0] += 32;
            }
            return String.valueOf(chars);
        }
        return str;
    }
}
