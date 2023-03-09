package com.ebanma.cloud.tmpTest;

import org.junit.Test;

/**
 * @author 鹿胜宝
 * @date 2023/03/07
 */
public class Tmp {
    @Test
    public void t1() {
        int i = 1;
        int i1 = (++i) + (++i);
        System.out.println(i1);
    }
}
