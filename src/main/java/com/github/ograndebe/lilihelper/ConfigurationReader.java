package com.github.ograndebe.lilihelper;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConfigurationReader {

    private final List<ButtonConfiguration> buttonList;

    private Map<String, Integer> keyMap = new HashMap<>();

    public ConfigurationReader(File file) {

        keyMap.put("ctrl",KeyEvent.VK_CONTROL);
        keyMap.put("control",KeyEvent.VK_CONTROL);
        keyMap.put("alt",KeyEvent.VK_ALT);
        keyMap.put("shift",KeyEvent.VK_SHIFT);

        IntStream.range(0,9).forEach(i->keyMap.put(String.valueOf(i), KeyEvent.VK_0+i));
        IntStream.range(0,9).forEach(i->keyMap.put(String.valueOf(i), KeyEvent.VK_0+i));
        IntStream.range(0,11).forEach(i->keyMap.put(String.format("f%s",i+1), KeyEvent.VK_F1+i));
        IntStream.range(65,91).forEach(i->keyMap.put(String.format("%s",(char)i).toLowerCase(), i));

        try (Stream<String> stream = Files.lines(file.toPath())) {
            this.buttonList = stream.filter(l -> !l.startsWith("#"))
                    .map(this::convert)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ButtonConfiguration convert(String s) {
        String[] split = s.split("[;]");
        final String label = split[0];
        final String def = split[1];
        final ButtonType type = ButtonType.valueOf(split[2]);
        final Set<Integer> keys = convertKeys(def);
        return new ButtonConfiguration(label, keys, type);
    }

    private Set<Integer> convertKeys(String def) {
        final String[] split = def.split("[+]");
        final HashSet<Integer> keys = new HashSet<>(split.length);
        for (String conf: split) {
            final Integer keyCode = keyMap.get(conf.toLowerCase());
            if (keyCode == null) throw new RuntimeException("Nao eh possivel utilizar a tecla: "+conf);
            keys.add(keyCode);
        }
        return keys;
    }

    public List<ButtonConfiguration> getButtonList() {
        return buttonList;
    }
}
