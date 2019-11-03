package net.inxas.rit.widget.font;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * CMAPです。
 * 指定の文字コードの文字をキーにして
 * グリフマップを作成します。
 * 
 * @author inxas
 * @since  2019/10/31
 */
public class TrueTypeCmapTable {
    private final int version;
    private final int platformID;
    private final int encodingID;
    private final int format;
    private final HashMap<Long, Long> cmap;
    private final HashMap<Long, HashMap<Long, Long>> varSelectorMap;

    TrueTypeCmapTable(int version, int platformID, int encodingID, int format) {
        this.version = version;
        this.platformID = platformID;
        this.encodingID = encodingID;
        this.format = format;
        cmap = new LinkedHashMap<>();
        varSelectorMap = new HashMap<>();
    }

    public void put(long code, long glyphID) {
        cmap.put(code, glyphID);
    }

    public long get(long code) {
        return cmap.getOrDefault(code, 0L);
    }

    public Set<Long> keySet() {
        return cmap.keySet();
    }

    public int getVersion() {
        return version;
    }

    public int getPlatformID() {
        return platformID;
    }

    public int getEncodingID() {
        return encodingID;
    }

    public int getFormat() {
        return format;
    }

    public void varAdd(long baseValue, long varSelector, long glyphID) {
        if (!varSelectorMap.containsKey(baseValue)) {
            varSelectorMap.put(baseValue, new HashMap<>());
        }
        varSelectorMap.get(baseValue).put(varSelector, glyphID);
    }

    public long getVar(long baseValue, long varSelector) {
        if (!varSelectorMap.containsKey(baseValue)) {
            return 0;
        }
        return varSelectorMap.get(baseValue).getOrDefault(varSelectorMap, 0L);
    }
}
