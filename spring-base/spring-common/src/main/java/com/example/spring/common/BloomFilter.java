package com.example.spring.common;

import java.util.BitSet;
import java.util.Objects;

/**
 * 布隆过滤 不在的一定不在，在的有可能不在
 * 
 * @author huss
 * @email 452327322@qq.com
 * @date 2019年6月20日下午4:00:15
 */
public class BloomFilter {

    /**
     * 使用加法hash算法，所以定义了一个5个元素的质数数组
     */
    private static final int[] PRIMES = new int[] {31, 33, 37, 39, 41};

    private Hash[] hashList = new Hash[PRIMES.length];
    /**
     * 创建一个长度为10亿的比特位
     */
    private BitSet bits = new BitSet(256 << 22);

    public static BloomFilter getInstance() {
        return BloomFilterSingletonEnum.INSTANCE.getInstance();
    }

    private BloomFilter() {

        super();
        for (int i = 0; i < PRIMES.length; i++) {

            hashList[i] = new Hash(PRIMES[i]);
        }
    }

    public void add(String value) {
        for (Hash hash : hashList) {
            bits.set(hash.hash(value), true);
        }
    }

    public boolean contains(String value) {
        if (Objects.isNull(value) || value.trim().isEmpty()) {
            return false;
        }
        boolean ret = true;
        for (Hash hash : hashList) {
            ret = ret && bits.get(hash.hash(value));
        }
        return ret;
    }

    private enum BloomFilterSingletonEnum {
        INSTANCE;

        private BloomFilter bloomFilter;

        BloomFilterSingletonEnum() {
            this.bloomFilter = new BloomFilter();
        }

        public BloomFilter getInstance() {
            return this.bloomFilter;

        }
    }

    public static class Hash {
        private int prime;

        public Hash(int prime) {
            super();
            this.prime = prime;
        }

        public int hash(String key) {
            int hash;
            hash = key.length();
            for (int i = 0, size = key.length(); i < size; i++) {
                hash += key.charAt(i);
            }
            return (hash % prime);
        }

    }

}
