//package net.inxas.rit.widget.font;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * 
// * @author inxas
// * @since  2019/10/28
// */
//public class TrueTypeAnalyzer {
//    private final Path path;
//    private byte[] allBytes;
//    private long offset;
//    private List<HashMap<String, String>> nameTable;
//
//    public TrueTypeAnalyzer(Path path) throws IncorrectFontFormatException {
//        if (path == null) {
//            throw new NullPointerException("path must not be null!");
//        }
//        if (Files.notExists(path)) {
//            throw new IncorrectFontFormatException("File does not exist.");
//        }
//        if (Files.isDirectory(path)) {
//            throw new IncorrectFontFormatException("path must be a file.");
//        }
//        this.path = path;
//        load();
//    }
//
//    private static final byte[] TTC_FORMAT = { (byte) 't', (byte) 't', (byte) 'c', (byte) 'f' };
//    private static final byte[] TTC_VERSION_1 = { 0x00, 0x01, 0x00, 0x00 };
//    private static final byte[] TTC_VERSION_2 = { 0x00, 0x02, 0x00, 0x00 };
//    private static final byte[] DSIG = { (byte) 'D', (byte) 'S', (byte) 'I', (byte) 'G' };
//    private static final byte[] TTF_VERSION = { 0x00, 0x01, 0x00, 0x00 };
//
//    private static final int TTF_PLAT_UNICODE = 0;
//    private static final int TTF_PLAT_MACINTOSH = 1;
//    private static final int TTF_PLAT_ISO = 2;
//    private static final int TTF_PLAT_MICROSOFT = 3;
//    private static final int TTF_PLAT_CUSTOM = 4;
//
//    private static final int MS_ENCO_SYMBOL = 0;
//    private static final int MS_ENCO_UNICODE_BMP = 1;
//    private static final int MS_ENCO_SHIFT_JIS = 2;
//    private static final int MS_ENCO_PRC = 3;
//    private static final int MS_ENCO_BIG5 = 5;
//    private static final int MS_ENCO_JOHAB = 6;
//    private static final int MS_ENCO_UNICODE_FULL = 10;
//
//    private static final int NAMEID_COPYRIGHT_KEY = 0;
//    private static final int NAMEID_FONT_FAMILY_KEY = 1;
//    private static final int NAMEID_FONT_SUBFAMILY_KEY = 2;
//    private static final int NAMEID_FONT_IDENTIFIER_KEY = 3;
//    private static final int NAMEID_FULL_FONT_NAME_KEY = 4;
//    private static final int NAMEID_VERSION_KEY = 5;
//    private static final int NAMEID_POSTSCRIPT_KEY = 6;
//    private static final int NAMEID_TRADEMARK_KEY = 7;
//    private static final int NAMEID_MANUFACTURER_KEY = 8;
//    private static final int NAMEID_DESIGNER_KEY = 9;
//    private static final int NAMEID_EXPLANATION_KEY = 10;
//    private static final int NAMEID_URL_VENDOR_KEY = 11;
//    private static final int NAMEID_URL_DESIGNER_KEY = 12;
//    private static final int NAMEID_LICENSE_KEY = 13;
//    private static final int NAMEID_LICENSE_URL_KEY = 14;
//    private static final int NAMEID_LETTERPRESS_FAMILY_KEY = 16;
//    private static final int NAMEID_LETTERPRESS_SUBFAMILY_KEY = 17;
//    private static final int NAMEID_COMPATIBLE_FULL_FONT_NAME_KEY = 18;
//    private static final int NAMEID_SAMPLE_TEXT_KEY = 19;
//    private static final int NAMEID_POSTSCRIPT_CID_FINDFONT_KEY = 20;
//    private static final int NAMEID_WWS_FAMILY_KEY = 21;
//    private static final int NAMEID_WWS_SUBFAMILY_KEY = 22;
//    private static final int NAMEID_LIGHT_BACKGROUND_PALETTE_KEY = 23;
//    private static final int NAMEID_DARK_BACKGROUND_PALETTE_KEY = 24;
//    private static final int NAMEID_VARIATIONS_POSTSCRIPT_NAME_PREFIX_KEY = 25;
//
//    private void load() throws IncorrectFontFormatException {
//        try {
//            allBytes = Files.readAllBytes(path);
//        } catch (IOException e) {
//            throw new IncorrectFontFormatException("Font loading failed.", e);
//        }
//        offset = 0;
//        nameTable = new ArrayList<>();
//        byte[] format = cut(4);
//        if (Arrays.equals(format, TTC_FORMAT)) {
//            ttcLoad();
//        }
//    }
//
//    private byte[] cut(int len) {
//        byte[] result = Arrays.copyOfRange(allBytes, (int) offset, (int) offset + len);
//        offset += len;
//        return result;
//    }
//
//    private long cutLong() {
//        return byteArrayToLong(cut(4));
//    }
//
//    private long cutSLong64() {
//        return byteArrayToSignedLong(cut(8));
//    }
//
//    private int cutInt() {
//        return byteArrayToInt(cut(2));
//    }
//
//    private int cutSInt16() {
//        return byteArrayToSignedInt(cut(2));
//    }
//
//    private int cutInt24() {
//        return byteArrayToInt(cut(3));
//    }
//
//    private int cutSInt32() {
//        return byteArrayToSignedInt(cut(4));
//    }
//
//    private int cutInt8() {
//        return byteArrayToInt(cut(1));
//    }
//
//    private byte[] get(long offset, int len) {
//        long old = this.offset;
//        this.offset = offset;
//        byte[] result = cut(len);
//        this.offset = old;
//        return result;
//    }
//
//    /**
//     * バイト配列をlong整数に変換するためのユーティリティメソッドです。
//     * bytesの長さが4よりも大きいとオーバーフローを起こします。
//     * 例えば{0x00,0x02,0x1F,0x00}なら139008を返します。
//     * 
//     * @param  bytes bytes
//     * @return       result
//     */
//    @SuppressWarnings("static-method")
//    private long byteArrayToLong(byte[] bytes) {
//        long digit = 1;
//        long result = 0;
//        for (int i = bytes.length - 1; i >= 0; i--) {
//            result += Byte.toUnsignedLong(bytes[i]) * digit;
//            digit *= 256;
//        }
//        return result;
//    }
//
//    /**
//     * バイト配列をint整数に変換するためのユーティリティメソッドです。
//     * bytesの長さが2よりも大きいとオーバーフローを起こします。
//     * 
//     * @param  bytes bytes
//     * @return       result
//     */
//    @SuppressWarnings("static-method")
//    private int byteArrayToInt(byte[] bytes) {
//        int digit = 1;
//        int result = 0;
//        for (int i = bytes.length - 1; i >= 0; i--) {
//            result += Byte.toUnsignedInt(bytes[i]) * digit;
//            digit *= 256;
//        }
//        return result;
//    }
//
//    private long byteArrayToSignedLong(byte[] bytes) {
//        long digit = 1;
//        long result = 0;
//        for (int i = bytes.length - 1; i >= 0; i--) {
//            result += bytes[i] * digit;
//            digit *= 256;
//        }
//        return result;
//    }
//
//    @SuppressWarnings("static-method")
//    private int byteArrayToSignedInt(byte[] bytes) {
//        int digit = 1;
//        int result = 0;
//        for (int i = bytes.length - 1; i >= 0; i--) {
//            result += bytes[i] * digit;
//            digit *= 256;
//        }
//        return result;
//    }
//
//    /**
//     * {@link #load()}内で、ヘッダの最初の4byteを読み込んだ直後に
//     * TTCフォーマットだと判断されたら呼び出されます。
//     */
//    private void ttcLoad() {
//        byte[] version = cut(4);
//        int numFonts = (int) cutLong();
//        long[] startingOffsets = new long[numFonts];
//        for (int i = 0; i < numFonts; i++) {
//            startingOffsets[i] = cutLong();
//        }
//        long DSIGTableSize = 0;
//        long DSIGOffset = 0;
//        if (Arrays.equals(version, TTC_VERSION_2)) {
//            if (Arrays.equals(cut(4), DSIG)) {
//                DSIGTableSize = cutLong();
//                DSIGOffset = cutLong();
//            } else {
//                offset -= 4;
//            }
//        } else if (!Arrays.equals(version, TTC_VERSION_1)) {
//            throw new IncorrectFontFormatException("format is incorrect.");
//        }
//
//        for (long offset : startingOffsets) {
//            ttfLoad(offset);
//        }
//    }
//
//    /**
//     * ttfLoad
//     */
//    private void ttfLoad(long ttfOffset) {
//        this.offset = ttfOffset;
//        byte[] version = cut(4);
//        if (!Arrays.equals(version, TTF_VERSION)) {
//            throw new IncorrectFontFormatException("format is incorrect.");
//        }
//        int tableLength = cutInt();
//        int searchRange = cutInt();
//        int entrySelector = cutInt();
//        int rangeShift = cutInt();
//
//        HashMap<String, TTFOffsetTable> offsetTable = new HashMap<>(tableLength);
//        for (int i = 0; i < tableLength; i++) {
//            String name = new String(cut(4));
//            long checkSum = cutLong();
//            long offset = cutLong();
//            long length = cutLong();
//            TTFOffsetTable table = new TTFOffsetTable(checkSum, offset, length);
//            offsetTable.put(name, table);
//        }
//        // TTFCmapTable(offsetTable.get("cmap").offset);
//        for (TTFNameRecord record : TTFNameTable(offsetTable.get("name").offset)) {
//            System.out.println(record.data);
//        }
//    }
//
//    private void TTFCmapTable(long cmapOffset) {
//        this.offset = cmapOffset;
//        int version = cutInt();
//        int numTables = cutInt();
//        for (int i = 0; i < numTables; i++) {
//            int platformID = cutInt();
//            int encodingID = cutInt();
//            long recordOffset = cutLong();
//            TTFCmapRecord(platformID, encodingID, cmapOffset + recordOffset);
//        }
//    }
//
//    private void TTFCmapRecord(int platformID, int encodingID, long recordOffset) {
//        long oldOffset = offset;
//        this.offset = recordOffset;
//        int format = cutInt();
//        System.out.println(format);
//        switch (format) {
//        case 0:
//            format0(platformID, encodingID);
//            break;
//        case 2:
//            format2();
//            break;
//        case 4:
//            format4();
//            break;
//        case 6:
//            format6();
//            break;
//        case 8:
//            format8();
//            break;
//        case 10:
//            format10();
//            break;
//        case 12:
//            format12();
//            break;
//        case 13:
//            format13();
//            break;
//        case 14:
//            format14(platformID, encodingID);
//            break;
//        default:
//            throw new IncorrectFontFormatException("format is incorrect.");
//        }
//        offset = oldOffset;
//    }
//
//    private void format0(int platformID, int encodingID) {
//
//    }
//
//    private void format2() {
//        int len = cutInt();
//        int lang = cutInt();
//    }
//
//    private void format4() {
//
//    }
//
//    private void format6() {
//
//    }
//
//    private void format8() {
//
//    }
//
//    private void format10() {
//
//    }
//
//    private void format12() {
//
//    }
//
//    private void format13() {
//
//    }
//
//    private void format14(int platformID, int encodingID) {
//        long len = cutLong();
//        long numVarSelectorRecords = cutLong();
//        for (long i = 0; i < numVarSelectorRecords; i++) {
//            int varSelector = cutInt24();
//            long defaultUVSOffset = cutLong();
//            long nonDefaultUVSOffset = cutLong();
//
//            if (defaultUVSOffset != 0)
//                defaultUVSTable(defaultUVSOffset);
//            if (nonDefaultUVSOffset != 0)
//                nonDefaultUVSTable(nonDefaultUVSOffset);
//        }
//    }
//
//    private void defaultUVSTable(long offset) {
//        long oldOffset = this.offset;
//        this.offset = offset;
//
//        long numUnicodeValueRanges = cutLong();
//        for (long i = 0; i < numUnicodeValueRanges; i++) {
//            int startUnicodeValue = cutInt24();
//            int additionalCount = cutInt8();
//        }
//
//        this.offset = oldOffset;
//    }
//
//    private void nonDefaultUVSTable(long offset) {
//        long numUVSMappings = cutLong();
//        for (long i = 0; i < numUVSMappings; i++) {
//            int unicodeValue = cutInt24();
//            int glyphID = cutInt();
//        }
//    }
//
//    private List<TTFNameRecord> TTFNameTable(long nameOffset) {
//        this.offset = nameOffset;
//        int format = cutInt();
//        int count = cutInt();
//        int stringOffset = cutInt();
//        ArrayList<TTFNameRecord> records = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            int platformID = cutInt();
//            int encodingID = cutInt();
//            int languageID = cutInt();
//            int nameID = cutInt();
//            int len = cutInt();
//            int offset = cutInt();
//            byte[] storage = get(nameOffset + stringOffset + offset, len);
//            records.add(new TTFNameRecord(platformID, encodingID, languageID, nameID, new String(storage)));
//        }
//        if (format == 1) {
//
//        }
//        return records;
//    }
//
//    private void TTFHeadTable(long headOffset) {
//        offset = headOffset;
//        int majorVersion = cutInt();
//        int minorVersion = cutInt();
//        int fontRevision = cutSInt32();
//        long checkSumAdjustment = cutLong();
//        long magicNumber = cutLong();
//        int flags = cutInt();
//        int unitsPerEm = cutInt();
//        long created = cutSLong64();
//        long modified = cutSLong64();
//        int xMin = cutSInt16();
//        int yMin = cutSInt16();
//        int xMax = cutSInt16();
//        int yMax = cutSInt16();
//        int macStyle = cutInt();
//        int lowestRecPPEM = cutInt();
//        int fontDirectionHint = cutSInt16();
//        int indexToLocFormat = cutSInt16();
//        int glyphDataFormat = cutSInt16();
//    }
//
//    /**
//     * 
//     * @author inxas
//     * @since  2019/10/28
//     */
//    private static class TTFOffsetTable {
//        TTFOffsetTable(long checkSum, long offset, long length) {
//            this.checkSum = checkSum;
//            this.offset = offset;
//            this.length = length;
//        }
//
//        final long checkSum;
//        final long offset;
//        final long length;
//    }
//
//    private static class TTFNameRecord {
//        TTFNameRecord(int platformID, int encodingID, int languageID, int nameID, String data) {
//            this.platformID = platformID;
//            this.encodingID = encodingID;
//            this.languageID = languageID;
//            this.nameID = nameID;
//            this.data = data;
//        }
//
//        final int platformID;
//        final int encodingID;
//        final int languageID;
//        final int nameID;
//        final String data;
//    }
//}
