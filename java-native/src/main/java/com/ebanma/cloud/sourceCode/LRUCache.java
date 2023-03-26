package com.ebanma.cloud.sourceCode;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 鹿胜宝
 * @date 2023/03/25
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private static final int MAX_ENTRIES = 3;

    //插入节点后的移除策略，默认false，不执行移除，此处重写方法要在超出时候移除
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > MAX_ENTRIES;
    }

    LRUCache() {
        //访问节点后移动到链表末尾，true
        super(MAX_ENTRIES, 0.75f, true);
    }
}
