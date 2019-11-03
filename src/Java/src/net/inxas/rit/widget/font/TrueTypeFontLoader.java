package net.inxas.rit.widget.font;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import net.inxas.rit.widget.font.TrueTypeFontStruct.EncodingRecord;
import net.inxas.rit.widget.font.TrueTypeFontStruct.HMetrics;
import net.inxas.rit.widget.font.TrueTypeFontStruct.NameRecord;
import net.inxas.rit.widget.geometry.GlyphPath;
import net.inxas.rit.widget.geometry.Point;

/**
 * .ttf形式のフォントファイルを読み込みます。
 * 
 * @author inxas
 * @since  2019/10/29
 */
public class TrueTypeFontLoader {
    private final TrueTypeFontStruct struct;
    private final byte[] bytes;
    private long offset;
    private int numTables;

    private static interface TableProcess {
        void process(long offset);
    }

    public TrueTypeFontLoader(byte[] allBytes) {
        this.bytes = allBytes;
        struct = new TrueTypeFontStruct();
        load();
    }

    public TrueTypeFontStruct getFontStruct() {
        return struct;
    }

    public GlyphPath getGlyph(char c) {
        TrueTypeCmapTable cmapTable = struct.encodingRecords[0].cmapTable;
        long glyphID = cmapTable.get(c);
        System.out.println("platform:" + cmapTable.getPlatformID());
        System.out.println("encode:" + cmapTable.getEncodingID());
        System.out.println("format:" + cmapTable.getFormat());
        long offset = struct.locaOffsets[(int) glyphID];
        return loadGlyf(offset);
    }

    private byte int8() {
        return bytes[(int) offset++];
    }

    private short int16() {
        return (short) (bytes[(int) offset++] << 8 | Byte.toUnsignedInt(int8()));
    }

    private int int24() {
        return bytes[(int) offset++] << 16 | Short.toUnsignedInt(int16());
    }

    private int int32() {
        return (int) (bytes[(int) offset++] << 24 | Integer.toUnsignedLong(int24()));
    }

    private long int64() {
        return (long) bytes[(int) offset++] << 56
                | Integer.toUnsignedLong(bytes[(int) offset++]) << 48
                | Integer.toUnsignedLong(bytes[(int) offset++]) << 40
                | Integer.toUnsignedLong(bytes[(int) offset++]) << 32
                | Integer.toUnsignedLong(int32());
    }

    private short uint8() {
        return (short) Byte.toUnsignedInt(int8());
    }

    private int uint16() {
        return Short.toUnsignedInt(int16());
    }

    private long uint24() {
        return Integer.toUnsignedLong(int24());
    }

    private long uint32() {
        return Integer.toUnsignedLong(int32());
    }

    private double fixed() {
        return int16() + ((double) int16()) / 0x10000;
    }

    private short fword() {
        return int16();
    }

    private int ufword() {
        return uint16();
    }

    private long longDateTime() {
        return int64();
    }

    private String tag() {
        return new String(cut(4));
    }

    private int offset16() {
        return uint16();
    }

    private long offset32() {
        return uint32();
    }

    private byte[] cut(int len) {
        byte[] result = Arrays.copyOfRange(bytes, (int) offset, (int) offset + len);
        offset += len;
        return result;
    }

    private int uint16(int offset) {
        return Byte.toUnsignedInt(bytes[offset++]) * 256 +
                Byte.toUnsignedInt(bytes[offset]);
    }

    private String getEncodingName(int platformID, int encodingID) {
        String result = null;
        switch (platformID) {
        case TrueTypeFontStruct.PLATFORM_UNICODE:
            switch (encodingID) {
            case TrueTypeFontStruct.PU_ENCO_UNICODE_1_0:
                break;
            case TrueTypeFontStruct.PU_ENCO_UNICODE_1_1:
                break;
            case TrueTypeFontStruct.PU_ENCO_ISO_IEC_10646:
                break;
            case TrueTypeFontStruct.PU_ENCO_UNICODE_2_0_BMP:
                break;
            case TrueTypeFontStruct.PU_ENCO_UNICODE_2_0_FULL:
                break;
            case TrueTypeFontStruct.PU_ENCO_UNICODE_VARIATION:
                break;
            case TrueTypeFontStruct.PU_ENCO_UNICODE_FULL:
                break;
            }
            break;
        case TrueTypeFontStruct.PLATFORM_MACINTOSH:
            switch (encodingID) {
            case TrueTypeFontStruct.PM_ENCO_ROMAN:
                break;
            case TrueTypeFontStruct.PM_ENCO_JAPANESE:
                break;
            case TrueTypeFontStruct.PM_ENCO_CHINESE_TRADITIONAL:
                break;
            case TrueTypeFontStruct.PM_ENCO_KOREAN:
                break;
            case TrueTypeFontStruct.PM_ENCO_ARABIC:
                break;
            case TrueTypeFontStruct.PM_ENCO_HEDREW:
                break;
            case TrueTypeFontStruct.PM_ENCO_GREEK:
                break;
            case TrueTypeFontStruct.PM_ENCO_RUSSIAN:
                break;
            case TrueTypeFontStruct.PM_ENCO_RSYMBOL:
                break;
            case TrueTypeFontStruct.PM_ENCO_DEVANAGARI:
                break;
            case TrueTypeFontStruct.PM_ENCO_GURMUKHI:
                break;
            case TrueTypeFontStruct.PM_ENCO_GUJARATI:
                break;
            case TrueTypeFontStruct.PM_ENCO_ORIYA:
                break;
            case TrueTypeFontStruct.PM_ENCO_BENGALI:
                break;
            case TrueTypeFontStruct.PM_ENCO_TAMIL:
                break;
            case TrueTypeFontStruct.PM_ENCO_TELUGU:
                break;
            case TrueTypeFontStruct.PM_ENCO_KANNADA:
                break;
            case TrueTypeFontStruct.PM_ENCO_MALAYALAM:
                break;
            case TrueTypeFontStruct.PM_ENCO_SINHALESE:
                break;
            case TrueTypeFontStruct.PM_ENCO_BURMESE:
                break;
            case TrueTypeFontStruct.PM_ENCO_KHMER:
                break;
            case TrueTypeFontStruct.PM_ENCO_THAI:
                break;
            case TrueTypeFontStruct.PM_ENCO_GEORGIAN:
                break;
            case TrueTypeFontStruct.PM_ENCO_ARMENIAN:
                break;
            case TrueTypeFontStruct.PM_ENCO_CHINESE_SIMPLIFIED:
                break;
            case TrueTypeFontStruct.PM_ENCO_TIBETAN:
                break;
            case TrueTypeFontStruct.PM_ENCO_MONGOLIAN:
                break;
            case TrueTypeFontStruct.PM_ENCO_GEEZ:
                break;
            case TrueTypeFontStruct.PM_ENCO_SLAVIC:
                break;
            case TrueTypeFontStruct.PM_ENCO_VIETNAMESE:
                break;
            case TrueTypeFontStruct.PM_ENCO_SINDHI:
                break;
            case TrueTypeFontStruct.PM_ENCO_UNINTERPRETED:
                break;
            }
            break;
        case TrueTypeFontStruct.PLATFORM_ISO:
            switch (encodingID) {
            case TrueTypeFontStruct.PI_ENCO_7BIT_ASCII:
                break;
            case TrueTypeFontStruct.PI_ENCO_ISO_10646:
                break;
            case TrueTypeFontStruct.PI_ENCO_ISO_8859_1:
                break;
            }
            break;
        case TrueTypeFontStruct.PLATFORM_WINDOWS:
            switch (encodingID) {
            case TrueTypeFontStruct.PW_ENCO_SYMBOL:
                break;
            case TrueTypeFontStruct.PW_ENCO_UNICODE_BMP:
                break;
            case TrueTypeFontStruct.PW_ENCO_SHIFT_JIS:
                break;
            case TrueTypeFontStruct.PW_ENCO_PRC:
                break;
            case TrueTypeFontStruct.PW_ENCO_BIG5:
                break;
            case TrueTypeFontStruct.PW_ENCO_WANSUNG:
                break;
            case TrueTypeFontStruct.PW_ENCO_JOHAB:
                break;
            case TrueTypeFontStruct.PW_ENCO_UNICODE_FULL:
                break;
            }
            break;
        case TrueTypeFontStruct.PLATFORM_CUSTOM:
            break;
        }
        return result;
    }

    private void load() {
        offset = 0;
        long sfntVersion = uint32();
        numTables = uint16();
        int searchRange = uint16();
        int entrySelector = uint16();
        int rangeShift = uint16();
        loadTables();
    }

    private static class OffsetAndLength {
        long offset, length;
    }

    private void loadTables() {
        HashMap<String, OffsetAndLength> offsetTable = new LinkedHashMap<>();
        for (int i = 0; i < numTables; i++) {
            String tag = tag();
            long checkSum = uint32();
            long offset = offset32();
            long length = uint32();
            OffsetAndLength oal = new OffsetAndLength();
            oal.offset = offset;
            oal.length = length;
            offsetTable.put(tag, oal);
        }
        for (String tag : offsetTable.keySet()) {
            long offset = offsetTable.get(tag).offset;
            long length = offsetTable.get(tag).length;
            switch (tag) {
            // Required Tables
            case "cmap":
                loadCmap(offset);
                break;
            case "head":
                loadHead(offset);
                break;
            case "hhea":
                loadHhea(offset);
                break;
            case "hmtx":
                loadHmtx(offset, offsetTable.get("head").offset, offsetTable.get("maxp").offset);
                break;
            case "maxp":
                loadMaxp(offset);
                break;
            case "name":
                loadName(offset);
                break;
            case "OS/2":
                loadOS2(offset);
                break;
            case "post":
                loadPost(offset);
                break;

            // Tables Related to TrueType Outlines
            case "cvt ":
                loadCvt(offset, length);
                break;
            case "fpgm":
                loadFpgm(offset, length);
                break;
            case "glyf":
                struct.glyfOffset = offset;
                break;
            case "loca":
                loadLoca(offset, offsetTable.get("head").offset, offsetTable.get("maxp").offset);
                break;
            case "prep":
                break;
            case "gasp":
                break;

            // Tables Related to CFF Outlines
            case "CFF ":
                break;
            case "CFF2":
                break;
            case "VORG":
                break;

            // Table Related to SVG Outlines
            case "SVG ":
                break;

            // Tables Related to Bitmap Glyphs
            case "EBDT":
                break;
            case "EBLC":
                break;
            case "EBSC":
                break;
            case "CBDT":
                break;
            case "CBLC":
                break;
            case "sbix":
                break;

            // Advanced Typographic Tables
            case "BASE":
                break;
            case "GDEF":
                break;
            case "GPOS":
                break;
            case "GSUB":
                break;
            case "JSTF":
                break;
            case "MATH":
                break;

            // Tables used for OpenType Font Variations
            case "avar":
                break;
            case "cvar":
                break;
            case "fvar":
                break;
            case "gvar":
                break;
            case "HVAR":
                break;
            case "MVAR":
                break;
            case "STAT":
                break;
            case "VVAR":
                break;

            // Tables Related to Color Fonts
            case "COLR":
                break;
            case "CPAL":
                break;

            // Other OpenType Tables
            case "DSIG":
                break;
            case "hdmx":
                break;
            case "hern":
                break;
            case "LTSH":
                break;
            case "MERG":
                break;
            case "meta":
                break;
            case "PCLT":
                break;
            case "VDMX":
                break;
            case "vhea":
                break;
            case "vmtx":
                break;
            }
        }
    }

    private boolean loadedCmap = false;

    private void loadCmap(long cmapOffset) {
        if (loadedCmap)
            return;
        offset = cmapOffset;
        int version = uint16();
        int numTables = uint16();
        struct.encodingRecords = new EncodingRecord[numTables];
        for (int i = 0; i < numTables; i++) {
            int platformID = uint16();
            int encodingID = uint16();
            long formatOffset = offset32();
            final long oldOffset = offset;
            offset = cmapOffset + formatOffset;
            int format = uint16();
            TrueTypeCmapTable cmap = new TrueTypeCmapTable(version, platformID, encodingID, format);
            switch (format) {
            case 0:
                format0(cmap);
                break;
            case 2:
                format2(cmap);
                break;
            case 4:
                format4(cmap);
                break;
            case 6:
                format6(cmap);
                break;
            case 8:
                format8(cmap);
                break;
            case 10:
                format10(cmap);
                break;
            case 12:
                format12(cmap);
                break;
            case 13:
                format13(cmap);
                break;
            case 14:
                format14(cmap);
                break;
            }
            struct.encodingRecords[i] = new EncodingRecord(platformID, encodingID, cmap);
            offset = oldOffset;
        }
        loadedCmap = true;
    }

    private void format0(TrueTypeCmapTable cmap) {
        int length = uint16();
        int language = uint16();
        for (int i = 1; i <= 256; i++) {
            cmap.put(uint8(), i);
        }
    }

    // 非対応
    private void format2(TrueTypeCmapTable cmap) {
        int length = uint16();
        int language = uint16();
        int[] subHeaderKeys = new int[256];
        for (int i = 0; i < 256; i++) {
            subHeaderKeys[i] = uint16() / 8;
        }
    }

    private void format4(TrueTypeCmapTable cmap) {
        int length = uint16();
        int language = uint16();
        int setCountX2 = uint16();
        int searchRange = uint16();
        int entrySelector = uint16();
        int rangeShift = uint16();

        final int segCount = setCountX2 / 2;
        int[] endCode = new int[segCount];
        for (int i = 0; i < segCount; i++) {
            endCode[i] = uint16();
        }

        int reservedPad = uint16();

        int[] startCode = new int[segCount];
        for (int i = 0; i < segCount; i++) {
            startCode[i] = uint16();
        }

        int[] idDelta = new int[segCount];
        for (int i = 0; i < segCount; i++) {
            idDelta[i] = int16();
        }

        long rangeOffsetPosition = offset;
        int[] idRangeOffset = new int[segCount];
        for (int i = 0; i < segCount; i++) {
            idRangeOffset[i] = int16();
        }

        for (int i = 0; i < segCount; i++) {
            int start = startCode[i];
            int end = endCode[i];
            int delta = idDelta[i];
            int range = idRangeOffset[i];
            if (start != 0xFFFF && end != 0xFFFF) {
                for (int j = start; j <= end; j++) {
                    int glyphID;
                    if (range == 0) {
                        glyphID = j + delta;
                    } else {
                        glyphID = (int) (range / 2 + (j - start) + i + rangeOffsetPosition);
                    }
                    glyphID %= 65536;
                    cmap.put(j, glyphID);
                }
            }
        }
    }

    private void format6(TrueTypeCmapTable cmap) {
        int length = uint16();
        int language = uint16();
        int firstCode = uint16();
        int entryCount = uint16();
        for (int i = 1; i <= entryCount; i++) {
            cmap.put(firstCode + i - 1, i);
        }
    }

    // 非対応
    private void format8(TrueTypeCmapTable cmap) {
        uint16();
        long length = uint32();
        long language = uint32();
        int[] is32 = new int[8192];
        for (int i = 0; i < 8192; i++) {
            is32[i] = uint8();
        }
        long numGroups = uint32();
        for (long i = 0; i < numGroups; i++) {
            long startCharCode = uint32();
            long endCharCode = uint32();
            long startGlyphID = uint32();
        }
    }

    private void format10(TrueTypeCmapTable cmap) {
        uint16();
        long length = uint32();
        long language = uint32();
        long startCharCode = uint32();
        long numChars = uint32();
        for (int i = 1; i <= numChars; i++) {
            cmap.put(startCharCode + i - 1, i);
        }
    }

    private void format12(TrueTypeCmapTable cmap) {
        uint16();
        long length = uint32();
        long language = uint32();
        long numGroups = uint32();
        for (long i = 0; i < numGroups; i++) {
            long startCharCode = uint32();
            long endCharCode = uint32();
            long startGlyphID = uint32();
            for (long code = startCharCode; code <= endCharCode; code++) {
                cmap.put(code, startGlyphID + code - startCharCode);
            }
        }
    }

    private void format13(TrueTypeCmapTable cmap) {
        uint16();
        long length = uint32();
        long language = uint32();
        long numGroups = uint32();
        for (long i = 0; i < numGroups; i++) {
            long startCharCode = uint32();
            long endCharCode = uint32();
            long glyphID = uint32();
            for (long code = startCharCode; code <= endCharCode; code++) {
                cmap.put(code, glyphID);
            }
        }
    }

    private void format14(TrueTypeCmapTable cmap) {
        long format14Offset = offset;
        long length = uint32();
        long numVarSelectorRecords = uint32();
        for (long i = 0; i < numVarSelectorRecords; i++) {
            long varSelector = uint24();
            long defaultUVSOffset = offset32();
            long nonDefaultUVSOffset = offset32();
            final long oldOffset = offset;
            if (defaultUVSOffset != 0) {
                offset = format14Offset + defaultUVSOffset;
                long numUnicodeValueRanges = uint32();
                for (long j = 0; j < numUnicodeValueRanges; j++) {
                    long startUnicodeValue = uint24();
                    int additionalCount = uint8();
                    for (long k = 0; k <= additionalCount; k++) {
                        cmap.put(startUnicodeValue + k, cmap.getVar(startUnicodeValue + k, varSelector));
                    }
                }
            }
            if (nonDefaultUVSOffset != 0) {
                offset = format14Offset + nonDefaultUVSOffset;
                long numUVSMappings = uint32();
                for (long j = 0; j < numUVSMappings; j++) {
                    long baseValue = uint24();
                    int glyphID = uint16();
                    cmap.varAdd(baseValue, varSelector, glyphID);
                }
            }
            offset = oldOffset;
        }
    }

    private boolean loadedHead = false;

    private void loadHead(long headOffset) {
        if (loadedHead)
            return;
        offset = headOffset;
        struct.majorVersion = uint16();
        struct.minorVersion = uint16();
        struct.fontRevision = fixed();
        long checkSumAdjustment = uint32();
        long magicNumber = uint32();
        if (magicNumber != 0x5F0F3CF5) {
            throw new IncorrectFontFormatException("format is incorrect.");
        }
        int flags = uint16();
        struct.unitsPerEm = uint16();
        struct.createdTime = longDateTime();
        struct.modifiedTime = longDateTime();
        struct.xMin = int16();
        struct.yMin = int16();
        struct.xMax = int16();
        struct.yMax = int16();
        int macStyle = uint16();
        struct.isBold = (macStyle & (1 << 0)) != 0;
        struct.isItalic = (macStyle & (1 << 1)) != 0;
        struct.isUnderline = (macStyle & (1 << 2)) != 0;
        struct.isOutline = (macStyle & (1 << 3)) != 0;
        struct.isShadow = (macStyle & (1 << 4)) != 0;
        struct.isCondensed = (macStyle & (1 << 5)) != 0;
        struct.isExtended = (macStyle & (1 << 6)) != 0;
        struct.leastRecPPEM = uint16();
        struct.fontDirectionHint = int16();
        struct.indexToLocFormat = int16();
        struct.glyphDataFormat = int16();

        loadedHead = true;
    }

    private boolean loadedHhea = false;

    private void loadHhea(long hheaOffset) {
        if (loadedHhea)
            return;
        offset = hheaOffset;
        int majorVersion = uint16();
        int minorVersion = uint16();
        struct.ascent = fword();
        struct.descent = fword();
        struct.lineGap = fword();
        struct.advanceWidthMax = ufword();
        struct.minLeftSideBearing = fword();
        struct.minRightSideBearing = fword();
        struct.xMaxExtent = fword();
        struct.caretSlopeRise = int16();
        struct.caretSlopeRun = int16();
        struct.caretOffset = int16();
        int16();
        int16();
        int16();
        int16();
        struct.metricDataFormat = int16();
        struct.numberOfHMetrics = uint16();

        loadedHhea = true;
    }

    private boolean loadedHmtx = false;

    // 事前にloadHead,loadMaxpをやる
    private void loadHmtx(long hmtxOffset, long headOffset, long maxpOffset) {
        if (loadedHmtx)
            return;
        if (!loadedHead) {
            loadHead(headOffset);
        }
        if (!loadedMaxp) {
            loadMaxp(maxpOffset);
        }
        struct.longHorMetrics = new HMetrics[struct.numberOfHMetrics];
        for (int i = 0; i < struct.longHorMetrics.length; i++) {
            struct.longHorMetrics[i] = new HMetrics(uint16(), int16());
        }
        struct.leftSideBearings = new short[struct.numGlyphs - struct.numberOfHMetrics];
        for (int i = 0; i < struct.leftSideBearings.length; i++) {
            struct.leftSideBearings[i] = int16();
        }
        loadedHmtx = true;
    }

    private boolean loadedMaxp = false;

    private void loadMaxp(long maxpOffset) {
        if (loadedMaxp)
            return;
        offset = maxpOffset;
        int version = int32();
        struct.numGlyphs = uint16();
        if (version == 0x00010000) {
            struct.maxPoints = uint16();
            struct.maxContours = uint16();
            struct.maxCompositePoints = uint16();
            struct.maxCompositeContours = uint16();
            struct.maxZones = uint16();
            struct.maxTwilightPoints = uint16();
            struct.maxStorage = uint16();
            struct.maxFunctionDefs = uint16();
            struct.maxInstructionDefs = uint16();
            struct.maxStackElements = uint16();
            struct.maxSizeOfInstructions = uint16();
            struct.maxComponentElements = uint16();
            struct.maxComponentDepth = uint16();
        }
        loadedMaxp = true;
    }

    private boolean loadedName = false;

    private void loadName(long nameOffset) {
        if (loadedName)
            return;
        this.offset = nameOffset;
        int format = uint16();
        int count = uint16();
        int storageOffset = offset16();
        struct.nameRecords = new NameRecord[count];
        for (int i = 0; i < count; i++) {
            int platformID = uint16();
            int encodingID = uint16();
            int languageID = uint16();
            int nameID = uint16();
            int strLength = uint16();
            int stringOffset = offset16();

            final long old = offset;
            offset = nameOffset + storageOffset + stringOffset;
            String data = new String(cut(strLength));
            offset = old;
            NameRecord nameRecord = new NameRecord(platformID, encodingID, languageID, nameID, data);
        }
        if (format == 1) {
            int langTagCount = uint16();
            for (int i = 0; i < langTagCount; i++) {
                int length = uint16();
                int stringOffset = offset16();
                final long old = offset;
                offset = nameOffset + storageOffset + stringOffset;
                String data = new String(cut(length), StandardCharsets.UTF_16BE);
                offset = old;
            }
        }
        loadedName = true;
    }

    private boolean loadedOS2 = false;

    private void loadOS2(long OS2Offset) {
        if (loadedOS2)
            return;
        offset = OS2Offset;
        struct.os2Version = uint16();
        struct.xAvgCharWidth = int16();
        struct.usWeightClass = uint16();
        struct.usWidthClass = uint16();
        struct.fsType = uint16();
        struct.ySubscriptXSize = int16();
        struct.ySubscriptYSize = int16();
        struct.ySubscriptXOffset = int16();
        struct.ySubscriptYOffset = int16();
        struct.ySuperscriptXSize = int16();
        struct.ySuperscriptYSize = int16();
        struct.ySuperscriptXOffset = int16();
        struct.ySuperscriptYOffset = int16();
        struct.yStrikeoutSize = int16();
        struct.yStrikeoutPosition = int16();
        struct.sFamilyClass = int16();
        struct.bFamilyType = uint8();
        struct.bSerifStyle = uint8();
        struct.bWeight = uint8();
        struct.bProportion = uint8();
        struct.bContrast = uint8();
        struct.bStrokeVariation = uint8();
        struct.bArmStyle = uint8();
        struct.bLetterform = uint8();
        struct.bMidline = uint8();
        struct.bXHeight = uint8();
        struct.ulUnicodeRange1 = uint32();
        struct.ulUnicodeRange2 = uint32();
        struct.ulUnicodeRange3 = uint32();
        struct.ulUnicodeRange4 = uint32();
        struct.achVendID = tag();
        struct.fsSelection = uint16();
        struct.usFirstCharIndex = uint16();
        struct.usLastCharIndex = uint16();
        struct.sTypoAscender = int16();
        struct.sTypoDescender = int16();
        struct.sTypoLineGap = int16();
        struct.usWinAscent = uint16();
        struct.usWinDescent = uint16();
        struct.ulCodePageRange1 = uint32();
        struct.ulCodePageRange2 = uint32();
        struct.sxHeight = int16();
        struct.sCapHeight = int16();
        struct.usDefaultChar = (char) uint16();
        struct.usBreakChar = (char) uint16();
        struct.usMaxContext = uint16();
        struct.usLowerOpticalPointSize = uint16();
        struct.usUpperOpticalPointSize = uint16();
        loadedOS2 = true;
    }

    private boolean loadedPost = false;

    private void loadPost(long postOffset) {
        if (loadedPost)
            return;
        offset = postOffset;
        int version = int32();
        struct.italicAngle = fixed();
        struct.underlinePosition = fword();
        struct.underlineThickness = fword();
        struct.isFixedPitch = uint32();
        struct.minMemType42 = uint32();
        struct.maxMemType42 = uint32();
        struct.minMemType1 = uint32();
        struct.maxMemType1 = uint32();
        loadedPost = true;
    }

    private boolean loadedCvt = false;

    private void loadCvt(long cvtOffset, long cvtLength) {
        if (loadedCvt)
            return;
        offset = cvtOffset;
        struct.cvtTable = new short[(int) (cvtLength / 16)];
        for (int i = 0; i < struct.cvtTable.length; i++) {
            struct.cvtTable[i] = fword();
        }

        loadedCvt = true;
    }

    private boolean loadedFpgm = false;

    private void loadFpgm(long fpgmOffset, long fpgmLength) {
        if (loadedFpgm)
            return;
        offset = fpgmOffset;
        struct.fpgmTable = new short[(int) (fpgmLength / 8)];
        for (int i = 0; i < struct.fpgmTable.length; i++) {
            struct.fpgmTable[i] = uint8();
        }
        loadedFpgm = true;
    }

    private boolean loadedLoca = false;

    // 事前にloadHead,loadMaxpをやる
    private void loadLoca(long locaOffset, long headOffset, long maxpOffset) {
        if (loadedLoca)
            return;
        if (!loadedHead) {
            loadHead(headOffset);
        }
        if (!loadedMaxp) {
            loadMaxp(maxpOffset);
        }
        offset = locaOffset;

        struct.locaOffsets = new long[struct.numGlyphs + 1];
        if (struct.indexToLocFormat == 0) {
            for (int i = 0; i < struct.locaOffsets.length; i++) {
                struct.locaOffsets[i] = offset16() * 2L;
            }
        } else {
            for (int i = 0; i < struct.locaOffsets.length; i++) {
                struct.locaOffsets[i] = offset32();
            }
        }
        loadedLoca = true;
    }

    private GlyphPath loadGlyf(long glyfOffset) {
        offset = struct.glyfOffset + glyfOffset;
        short numberOfContours = int16();
        short xMin = int16();
        short yMin = int16();
        short xMax = int16();
        short yMax = int16();
        GlyphPath result = new GlyphPath(xMin, yMin, xMax, yMax);
        if (numberOfContours >= 0) {
            int[] endPtsOfContours = new int[numberOfContours];
            for (int i = 0; i < numberOfContours; i++) {
                endPtsOfContours[i] = uint16();
            }
            int instructionLength = uint16();
            short[] instructions = new short[instructionLength];
            for (int i = 0; i < instructionLength; i++) {
                instructions[i] = uint8();
            }

            ArrayList<Short> flags = new ArrayList<>();
            for (int i = 0, len = endPtsOfContours[numberOfContours - 1] + 1; i < len; i++) {
                short flag = uint8();
                flags.add(flag);
                if ((flag & 0b1000) != 0) {
                    short sideCount = uint8();
                    for (int j = 0; j < sideCount; j++, i++) {
                        flags.add(flag);
                    }
                }
            }

            short[] xCoordinates = new short[flags.size()];
            for (int i = 0; i < xCoordinates.length; i++) {
                final short flag = flags.get(i);
                short data;
                if ((flag & 0b10) != 0) {
                    data = uint8();
                    if ((flag & 0b10000) == 0) {
                        data *= -1;
                    }
                } else {
                    if ((flag & 0b10000) != 0) {
                        data = 0;
                    } else {
                        data = int16();
                    }
                }
                xCoordinates[i] = data;
            }
            short[] yCoordinates = new short[flags.size()];
            for (int i = 0; i < yCoordinates.length; i++) {
                final short flag = flags.get(i);
                short data;
                if ((flag & 0b100) != 0) {
                    data = uint8();
                    if ((flag & 0b100000) == 0) {
                        data *= -1;
                    }
                } else {
                    if ((flag & 0b100000) != 0) {
                        data = 0;
                    } else {
                        data = int16();
                    }
                }
                yCoordinates[i] = data;
            }

            int lastPoint = 0;
            for (int i = 0; i < numberOfContours; i++) {
                int endOfPoint = endPtsOfContours[i];
                result.moveTo(new Point(xCoordinates[lastPoint], -yCoordinates[lastPoint]),
                        (flags.get(lastPoint) & 0b1) != 0);
                for (int j = lastPoint + 1; j <= endOfPoint; j++) {
                    result.lineTo(new Point(xCoordinates[j], -yCoordinates[j]), (flags.get(j) & 0b1) != 0);
                }
                lastPoint = endOfPoint + 1;
                result.normalizeTo();
            }
            System.out.println();
        } else {

        }
        return result;
    }
}
