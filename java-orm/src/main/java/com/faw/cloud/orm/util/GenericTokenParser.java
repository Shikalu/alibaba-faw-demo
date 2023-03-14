package com.faw.cloud.orm.util;

/**
 * @author Clinton Begin
 */
public class GenericTokenParser {

    private final String openToken; //开始标记
    private final String closeToken; //结束标记
    private final TokenHandler handler; //标记处理器

    public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.handler = handler;
    }

    /**
     * 解析${}和#{}
     *
     * @param text
     * @return 该方法主要实现了配置文件、脚本等片段中占位符的解析、处理工作，并返回最终需要的数据。
     * 其中，解析工作由该方法完成，处理工作是由处理器handler的handleToken()方法来实现
     */
    public String parse(String text) {
        // 1.校验
        // 1.1.验证参数问题，如果是null，就返回空字符串。
        if (text == null || text.isEmpty()) {
            return "";
        }
        // 1.2.下面继续验证是否包含开始标签，如果不包含，默认不是占位符，直接原样返回即可，否则继续执行。
        int start = text.indexOf(openToken, 0);
        if (start == -1) {
            return text;
        }

        // 2.拆分带？的sql以及占位符表达式：offset记录整体解析到的位置
        // 把text转成字符数组src，并且定义初始偏移量offset=0、存储最终需要返回字符串的变量builder，
        // text变量中占位符对应的变量名expression（由于多个占位符的存在，每次循环后需要重置）。判断start是否大于-1(即text中是否存在openToken)，如果存在就执行下面代码
        char[] src = text.toCharArray();
        int offset = 0;
        final StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;

        while (start > -1) {
            // 开始标记前存在转义字符
            if (start > 0 && src[start - 1] == '\\') {   //短路与防止数组下标越界，判断转义符的时候本身就需要转义
                builder.append(src, offset, start - offset - 1).append(openToken);    //转义符不计数，所以-1
                offset = start + openToken.length();
            //开始标记前不存在转义字符
            } else {
                //重置expression变量，避免空指针或者老数据干扰。
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                //添加 offset 和 openToken 之间的内容，添加到 builder 中
                builder.append(src, offset, start - offset);
                //修改 offset
                offset = start + openToken.length();
                //从上面的开始标记开始找结束标记
                int end = text.indexOf(closeToken, offset);
                //存在结束标记时
                while (end > -1) {
                    //存在转义
                    if (end > offset && src[end - 1] == '\\') {
                        expression.append(src, offset, end - offset - 1).append(closeToken);
                        offset = end + closeToken.length();
                        // 继续，寻找开始的 closeToken 的位置，循环自增条件
                        end = text.indexOf(closeToken, offset);
                    //不存在转义字符，即需要作为参数进行处理
                    } else {
                        expression.append(src, offset, end - offset);
                        offset = end + closeToken.length();
                        break;
                    }
                }
                //上面循环结束存在两种情况，end==-1，找不到closeToken; end > -1,也就是作为参数处理break跳出循环的。
                //找不到closeToken
                if (end == -1) {
                    // close token was not found.
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                //表达式参数处理OK后跳出前面的循环
                } else {
                    //首先根据参数的key（即expression）进行参数处理，返回?作为占位符
                    builder.append(handler.handleToken(expression.toString()));
                    offset = end + closeToken.length();
                }
            }
            // 继续，寻找开始的 openToken 的位置，循环自增条件
            start = text.indexOf(openToken, offset);
        }
        // 拼接剩余的部分
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }
}