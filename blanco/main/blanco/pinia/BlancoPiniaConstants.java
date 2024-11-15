package blanco.pinia;

/**
 * blancoPiniaが利用する定数クラス。
 */
public class BlancoPiniaConstants {
    /**
     * 項目番号:1<br>
     * プロダクト名。英字で指定します。
     */
    public static final String PRODUCT_NAME = "blancoPinia";

    /**
     * 項目番号:2<br>
     * プロダクト名の小文字版。英字で指定します。
     */
    public static final String PRODUCT_NAME_LOWER = "blancopinia";

    /**
     * 項目番号:3<br>
     * バージョン番号。
     */
    public static final String VERSION = "3.0.4";

    /**
     * 項目番号:4<br>
     * 処理の過程で利用されるサブディレクトリ。
     */
    public static final String TARGET_SUBDIRECTORY = "/pinia";

    /**
     * 項目番号:5<br>
     * targetdirに設定される文字列
     */
    public static final String TARGET_STYLE_BLANCO = "blanco";

    /**
     * 項目番号:6<br>
     * targetdirに設定される文字列
     */
    public static final String TARGET_STYLE_MAVEN = "maven";

    /**
     * 項目番号:7<br>
     * targetdirに設定される文字列
     */
    public static final String TARGET_STYLE_FREE = "free";

    /**
     * 項目番号:8<br>
     * 生成したソースコードを保管するディレクトリのsuffix
     */
    public static final String TARGET_DIR_SUFFIX_BLANCO = "main";

    /**
     * 項目番号:9<br>
     * 生成したソースコードを保管するディレクトリのsuffix
     */
    public static final String TARGET_DIR_SUFFIX_MAVEN = "main/typescript";

    /**
     * 項目番号:10<br>
     * valueobjectが格納されているサブディレクトリ
     */
    public static final String OBJECT_SUBDIRECTORY = "/valueobjectts";

    /**
     * 項目番号:11<br>
     * 実装クラス名のサフィックス
     */
    public static final String IMPLECLASS_SUFFIX = "Imple";

    /**
     * 項目番号:12<br>
     * Pinia の Major Version 1 を表す定数です。
     */
    public static final Integer PINIA_MAJOR_VERSION_1 = 1;

    /**
     * 項目番号:13<br>
     * Pinia の Major Version 2 を表す定数です。
     */
    public static final Integer PINIA_MAJOR_VERSION_2 = 2;
}
