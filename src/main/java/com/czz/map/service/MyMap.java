package com.czz.map.service;

/**
 * @author chengzhzh@datangmobile.com
 * @create 2019-08-31 8:53
 * E 元素
 * T 类型
 * K 键
 * V 值
 */
public interface MyMap<K,V> {
    public V put(K k,V v);
    public V get(K k);

    interface Entry<K,V>{
        public K getKey();
        public V getValue();
    }
}
