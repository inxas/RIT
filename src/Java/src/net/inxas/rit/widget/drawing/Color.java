package net.inxas.rit.widget.drawing;

/**
 * 色を表します。
 * 描画する環境によって感じる色が多少異なる可能性があります。
 * 主要な色は定数でそろえています。
 * 
 * 色情報をRGBAで管理します。
 * 各値は0から0xFF(255)でなければなりません。
 * もし、上下限を超える値に設定しようとしたらそれぞれ上下限値に修正されます。
 * また、各値は不変です。
 * 
 * @author inxas
 * @since  2019/10/25
 */
public class Color {
    public static final Color ALPHA = new Color(0x00, 0x00, 0x00, 0x00);
    public static final Color BLACK = new Color(0x00, 0x00, 0x00);
    public static final Color WHITE = new Color(0xFF, 0xFF, 0xFF);
    public static final Color RED = new Color(0xFF, 0x00, 0x00);
    public static final Color GREEN = new Color(0x00, 0xFF, 0x00);
    public static final Color BLUE = new Color(0x00, 0x00, 0xFF);

    /**
     * 二つの色の間の色を作成します。
     * 
     * @param  a 色a
     * @param  b 色b
     * @return   二つの色の間の色
     */
    public static Color mixing(Color a, Color b) {
        return new Color((a.R + b.R) / 0xFF, (a.G + b.G) / 0xFF, (a.B + b.B) / 0xFF, (a.A + b.A) / 0xFF);
    }

    /**
     * undercoatに対してtopcoatを塗り重ねた色を作成します。
     * undercoatのalpha値は考慮されません。
     * もしtopcoatの{@link #withAlpha()}がfalseを返した場合、
     * topcoatを返します。
     * また、undercoatがnullだった場合はtopcoatを、topcoatがnullだった場合undercoatを返します。
     * 
     * @param  undercoat 下地
     * @param  topcoat   上塗り
     * @return           undercoatに対してtopcoatを塗り重ねた色
     */
    public static Color coating(Color undercoat, Color topcoat) {
        if (undercoat == null)
            return topcoat;
        else if (topcoat == null)
            return undercoat;
        if (topcoat.withAlpha()) {
            int newR = undercoat.R + ((topcoat.R - undercoat.R) * topcoat.A / 0xFF);
            int newG = undercoat.G + ((topcoat.G - undercoat.G) * topcoat.A / 0xFF);
            int newB = undercoat.B + ((topcoat.B - undercoat.B) * topcoat.A / 0xFF);
            return new Color(newR, newG, newB);
        } else {
            return topcoat;
        }
    }

    /**
     * 指定の色の透明度を変化させた色を作成します。
     * 
     * @param  color 色
     * @param  alpha 透明度
     * @return       作成した色
     */
    public static Color changeAlpha(Color color, int alpha) {
        return new Color(color.R, color.G, color.B, alpha);
    }

    private final int R, G, B, A;

    /**
     * RGB値で色を作成します。
     * Alpha値は0xFF(255)が設定されます。
     * 
     * @param r 赤色成分
     * @param g 緑色成分
     * @param b 青色成分
     */
    public Color(int r, int g, int b) {
        this(r, g, b, 0xFF);
    }

    /**
     * RGBA値で色を作成します。
     * 
     * @param r 赤色成分
     * @param g 緑色成分
     * @param b 青色成分
     * @param a 透明度
     */
    public Color(int r, int g, int b, int a) {
        R = r < 0 ? 0 : r > 0xFF ? 0xFF : r;
        G = g < 0 ? 0 : g > 0xFF ? 0xFF : g;
        B = b < 0 ? 0 : b > 0xFF ? 0xFF : b;
        A = a < 0 ? 0 : a > 0xFF ? 0xFF : a;
    }

    /**
     * 赤色成分を返します。
     * 
     * @return 赤色成分
     */
    public int getRed() {
        return R;
    }

    /**
     * 緑色成分を返します。
     * 
     * @return 緑色成分
     */
    public int getGreen() {
        return G;
    }

    /**
     * 青色成分を返します。
     * 
     * @return 青色成分
     */
    public int getBlue() {
        return B;
    }

    /**
     * 透明度を返します。
     * 
     * @return 透明度
     */
    public int getAlpha() {
        return A;
    }

    /**
     * 透明度が存在するかを調べます。
     * 
     * @return もし透明度が存在するならばtrue、そうでなければfalse
     */
    public boolean withAlpha() {
        return A < 0xFF;
    }

    /**
     * objがthisと同じインスタンスであるか、
     * RGBA値がすべて等しい場合にtrueを返します。
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || !(obj instanceof Color))
            return false;
        Color color = (Color) obj;
        if (R == color.R && G == color.G && B == color.B && A == color.A)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "net.inxas.rit.widget.drawing.Color[R=" + R + ",G=" + G + ",B=" + B + ",A=" + A + "]";
    }
}
