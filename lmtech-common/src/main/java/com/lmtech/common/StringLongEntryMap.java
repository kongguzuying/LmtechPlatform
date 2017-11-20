package com.lmtech.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringLongEntryMap {
    private Map<String, Long> datas = new HashMap<>();

    public StringLongEntryMap() {

    }

    public StringLongEntryMap(List<StringLongEntry> entrys) {
        for (StringLongEntry entry : entrys) {
            datas.put(entry.getKey(), entry.getValue());
        }
    }

    public Long getEntryValue(String key) {
        if (datas.containsKey(key)) {
            return datas.get(key);
        } else {
            return null;
        }
    }

    public Map<String, Long> getEntryMap() {
        return datas;
    }
}
