package com.chinaoly.frm.core.service;

public interface Checker<K, V> {

    public V check(K source, Object... args);

}
