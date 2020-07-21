package com.github.ograndebe.lilihelper;

import java.util.Collections;
import java.util.Set;

public class ButtonConfiguration {

    public ButtonConfiguration(String label, Set<Integer> keys, ButtonType type) {
        this.label = label;
        this.keys = Collections.unmodifiableSet(keys);
        this.type = type;
    }

    private final String label;
    private final Set<Integer> keys;
    private final ButtonType type;


    public String getLabel() {
        return label;
    }

    public Set<Integer> getKeys() {
        return keys;
    }

    public ButtonType getType() {
        return type;
    }
}
