package com.ebanma.cloud.sourceCode;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author 鹿胜宝
 * @date 2023/03/24
 */
public class MyHashMap<K, V> extends AbstractMap<K, V>
        implements Map<K, V>, Cloneable, Serializable {

    //初始化长度
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    //HashMap最大长度
    static final int MAXIMUM_CAPACITY = 1 << 30;
    //默认的加载因子（扩容因子）
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    //当链表长度大于此值且容量大于64时
    static final int TREEIFY_THRESHOLD = 8;
    //转换链表的临界值，当元素小于此值时，会将红黑树结构转换为链表结构
    static final int UNTREEIFY_THRESHOLD = 6;
    //最小树容量
    static final int MIN_TREEIFY_CAPACITY = 64;

    transient MyHashMap.Node<K, V>[] table;

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    //bucket
    static class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;

        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                return Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue());
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }
    }

    private V get(Objects key) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    private Node<K, V> getNode(int hash, Objects key) {
        Node<K, V>[] tab;
        Node<K, V> first, e;
        int n;
        K k;
        //非空判断,tab[(n - 1) & hash]保证不会出现数组下表越界
        if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & hash]) != null) {
            //判断第一个元素是否是要查询的元素
            if (first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            //下一个节点非空判断
            if ((e = first.next) != null) {
                //如果第一个节点是树结构，则使用getTreeNode直接获取相应的数据 TODO
//                if(first instanceof TreeNode)
//                    return ((TreeNode<K,V>)first).getTreeNode(hash,key);
                //否则走链表 TODO
            }
        }
        return null;
    }

    //hash函数
    static int hash(Objects key) {
        int h;
        return key == null ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    // 树结构 TODO
    static final class TreeNode<K, V> implements Map.Entry<K, V> {

        TreeNode(int hash, K key, V val, Node<K, V> next) {

        }

        @Override
        public K getKey() {
            return null;
        }

        @Override
        public V getValue() {
            return null;
        }

        @Override
        public V setValue(V value) {
            return null;
        }
    }


}
