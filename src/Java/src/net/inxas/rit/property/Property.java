package net.inxas.rit.property;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * プロパティファイルを編集、読み取り、作成します。
 * 
 * @author inxas
 * @since  2019/10/27
 */
public class Property {
    private boolean loaded;
    private final Path filePath;
    private final LinkedHashMap<String, String> property;

    public Property(Path filePath) {
        if (filePath == null) {
            throw new NullPointerException("filePath must not be null!");
        }
        this.filePath = filePath;
        property = new LinkedHashMap<>();
        loaded = false;
    }

    public String get(String key) {
        if (!loaded)
            load();
        return property.get(key);
    }

    public void put(String key, String value) {
        if (!loaded)
            load();
        property.put(key, value);
    }

    public Set<String> keySet() {
        return property.keySet();
    }

    public LinkedHashMap<String, String> getExpandedMap() {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();

        for (String key : keySet()) {
            String value = get(key);
            if (value.indexOf("%") != -1) {
                char[] chars = value.toCharArray();
                boolean replaceMode = false;
                StringBuilder builder = new StringBuilder(30);
                StringBuilder cache = new StringBuilder(20);
                for (int i = 0; i < chars.length; i++) {
                    if (chars[i] == '%') {
                        if (replaceMode) {
                            builder.append(result.get(cache.toString()));
                        } else {
                            builder.append(cache.toString());
                        }
                        replaceMode = !replaceMode;
                        cache.setLength(0);
                    } else {
                        cache.append(chars[i]);
                    }
                }
                builder.append(cache.toString());
                result.put(key, builder.toString());
            } else {
                result.put(key, value);
            }
        }

        return result;
    }

    public void putAll(String[] keyAndValue) {
        if (!loaded)
            load();
        for (int i = 0; i < keyAndValue.length; i += 2) {
            property.put(keyAndValue[i], keyAndValue[i + 1]);
        }
    }

    private void load() {
        if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] keyAndValue = line.split("=");
                    if (keyAndValue.length != 2)
                        continue;
                    property.put(keyAndValue[0].strip(), keyAndValue[1].strip());
                }
            } catch (Exception e) {
                return;
            }
        }
        loaded = true;
    }

    public void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            property.forEach((key, value) -> {
                try {
                    writer.write(key);
                    writer.append('=');
                    writer.write(value);
                    writer.newLine();
                } catch (IOException e) {}
            });
        } catch (IOException e) {
            return;
        }
    }

    public Path getPath() {
        return filePath;
    }
}
