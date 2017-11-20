package com.lmtech.common;

import java.util.Map;

public class StringLongEntry extends EntryBase<String, Long> {
    public StringLongEntry(String key, Long value) {
        super(key, value);
    }

    public StringLongEntry(Map.Entry<? extends String, ? extends Long> entry) {
        super(entry);
    }
}
