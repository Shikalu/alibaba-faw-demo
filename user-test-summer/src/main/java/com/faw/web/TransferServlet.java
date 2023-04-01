package com.faw.web;

import com.faw.domain.Result;
import com.faw.service.TransferService;
import com.faw.summer.aop.ProxyFactory;
import com.faw.summer.core.BeanFactory;
import com.faw.util.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 鹿胜宝
 * @date 2023/03/26
 */
@WebServlet(name = "transferServlet",urlPatterns = "/transferServlet")
public class TransferServlet extends HttpServlet {

    // 从工厂获取委托对象（委托对象是增强了事务控制的功能）
    // 首先从BeanFactory获取到proxyFactory代理工厂的实例化对象
    private ProxyFactory proxyFactory = (ProxyFactory) BeanFactory.getBean("proxyFactory");
    private TransferService transferService = (TransferService) proxyFactory.getCglibProxy(BeanFactory.getBean("transferService"));


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        Integer money = Integer.parseInt(req.getParameter("money"));

        System.out.println("收到请求 fromCardNo：" + fromCardNo + ", toCardNo: " + toCardNo + ", money: " + money);

        Result result = new Result();

        try {
            transferService.transfer(fromCardNo, toCardNo, money);
            result.setStatus("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus("201");
            result.setMessage(e.toString());
        }

        resp.setContentType("application/json;utf-8");
        resp.getWriter().print(JsonUtils.object2Json(result));

    }
}
