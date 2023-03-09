package com.ebanma.cloud.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务基类
 */
public abstract class BaseCartService {

    // 加入到购物车中的商品信息
    private static List<Sku> cartSkuList =
            new ArrayList<Sku>(){
                {
                    add(new Sku(654032, "无人机",
                            4999, 1,
                            4999, SkuCategoryEnum.ELECTRONICS));

                    add(new Sku(642934, "VR一体机",
                            2299, 1,
                            2299, SkuCategoryEnum.ELECTRONICS));

                    add(new Sku(645321, "纯色衬衫",
                            409, 3,
                            1227, SkuCategoryEnum.CLOTHING));

                    add(new Sku(654327, "牛仔裤",
                            528, 1,
                            528, SkuCategoryEnum.CLOTHING));

                    add(new Sku(675489, "跑步机",
                            2699, 1,
                            2699, SkuCategoryEnum.SPORTS));

                    add(new Sku(644564, "Java编程思想",
                            79, 1,
                            79, SkuCategoryEnum.BOOKS));

                    add(new Sku(678678, "Java核心技术",
                            149, 1,
                            149, SkuCategoryEnum.BOOKS));

                    add(new Sku(697894, "数据结构与算法",
                            78, 1,
                            78, SkuCategoryEnum.BOOKS));

                    add(new Sku(696968, "TensorFlow进阶指南",
                            85, 1,
                            85, SkuCategoryEnum.BOOKS));
                }
            };

    /**
     * 获取商品信息列表
     * @return
     */
    public static List<Sku> getCartSkuList() {
        return cartSkuList;
    }
}