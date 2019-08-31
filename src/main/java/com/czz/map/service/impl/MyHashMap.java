package com.czz.map.service.impl;

import com.czz.map.service.MyMap;

import java.util.ArrayList;
import java.util.List;


/**
 * @author chengzhzh@datangmobile.com
 * @create 2019-08-31 8:56
 */
public class MyHashMap<K,V> implements MyMap<K,V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 1<<4;//数组默认长度
    private static final float DEFAULT_LOAD_FACTYOR = 0.75f;//阈值比例
    private int defaultInitSize;
    private float defaultLoadFactory;

    private int entryUseSize;//map 中entry 的数量
    private Entry<K,V>[] table = null;

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY,DEFAULT_LOAD_FACTYOR);
    }

    public MyHashMap(int defaultInitialCapacity, float defaultLoadFactory) {
        if(defaultInitialCapacity<0){
            throw new IllegalArgumentException("Illegal initial capacity :"+defaultInitialCapacity);
        }
        if(defaultLoadFactory<=0 || Float.isNaN(defaultLoadFactory)){
            throw new IllegalArgumentException("Illegal load factory :"+defaultLoadFactory);
        }
        this.defaultInitSize = defaultInitialCapacity;
        this.defaultLoadFactory = defaultLoadFactory;

        table = new Entry[this.defaultInitSize];
    }

    class Entry<K,V> implements MyMap.Entry<K,V> {
        private K key;
        private V value;
        private Entry<K,V> next;
        public Entry() {
        }

        public Entry(K key, V value, Entry<K, V> next) {
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
    }

    @Override
    public V put(K k, V v) {
        V oldValue = null;
        if(entryUseSize>=defaultInitSize*defaultLoadFactory){
            resize(2*defaultInitSize);
        }
        // 得到 hash 值 计算出数组中的位置
        int index = hash(k) & (defaultInitSize-1);
        if(table[index] == null){
            table[index] = new Entry<>(k, v, null);
            ++entryUseSize;
        }else{
            // 遍历单链表
            Entry<K, V> entry = table[index];
            Entry<K, V> e = entry;
            while (e != null){
                    if (k==e.getKey() || k.equals(e.getKey())) {
                        oldValue = e.getValue();
                        e.value = v;
                        return oldValue;
                    }
                e = e.next;
            }
            table[index] = new Entry<>(k,v,entry);
        }
        return oldValue;
    }

    private int hash(K k){
        int hashCode = k.hashCode();
        hashCode ^= (hashCode>>>20) ^ (hashCode>>>12);
        return hashCode ^ (hashCode>>>7) ^ (hashCode>>>4);
    }

    private void resize(int i) {
        Entry[] newTable = new Entry[i];
        defaultInitSize = i;
        entryUseSize = 0;
        rehash(newTable);
    }

    private void rehash(Entry[] newTable) {

        //得到原来老的 entry 集合 注意遍历单链表
        List<Entry<K,V>> entryList = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                do{
                    entryList.add(entry);
                    entry = entry.next;
                }while (entry!=null);
            }
        }

        if(newTable.length>0){
            table = newTable;
        }

        for (Entry<K, V> entry : entryList) {
            put(entry.getKey(),entry.getValue());
        }
    }

    @Override
    public V get(K k) {
        int index = hash(k) & (defaultInitSize-1);
        if(table[index] == null){
            return null;
        }else{
            Entry<K,V> entry = table[index];
            do{
                if(k==entry.getKey() || k.equals(entry.getKey())){
                    return entry.getValue();
                }
                entry = entry.next;
            }while (entry!=null);
        }
        return null;
    }
}
