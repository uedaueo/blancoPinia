package blanco.pinia.task;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import blanco.pinia.task.valueobject.BlancoPiniaProcessInput;

/**
 * Apache Antタスク [BlancoPinia]のクラス。
 *
 * Vueストア定義書からソースコードを自動生成する BlancoPiniaのAntTaskです。<br>
 * このクラスでは、Apache Antタスクで一般的に必要なチェックなどのコーディングを肩代わりします。
 * 実際の処理は パッケージ[blanco.pinia.task]にBlancoPiniaBatchProcessクラスを作成して記述してください。<br>
 * <br>
 * Antタスクへの組み込み例<br>
 * <pre>
 * &lt;taskdef name=&quot;blancopinia&quot; classname=&quot;blanco.pinia.task.BlancoPiniaTask">
 *     &lt;classpath&gt;
 *         &lt;fileset dir=&quot;lib&quot; includes=&quot;*.jar&quot; /&gt;
 *         &lt;fileset dir=&quot;lib.ant&quot; includes=&quot;*.jar&quot; /&gt;
 *     &lt;/classpath&gt;
 * &lt;/taskdef&gt;
 * </pre>
 */
public class BlancoPiniaTask extends Task {
    /**
     * Vueストア定義書からソースコードを自動生成する BlancoPiniaのAntTaskです。
     */
    protected BlancoPiniaProcessInput fInput = new BlancoPiniaProcessInput();

    /**
     * フィールド [metadir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldMetadirProcessed = false;

    /**
     * フィールド [targetdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTargetdirProcessed = false;

    /**
     * フィールド [tmpdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTmpdirProcessed = false;

    /**
     * フィールド [encoding] に値がセットされたかどうか。
     */
    protected boolean fIsFieldEncodingProcessed = false;

    /**
     * フィールド [tabs] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTabsProcessed = false;

    /**
     * フィールド [xmlrootelement] に値がセットされたかどうか。
     */
    protected boolean fIsFieldXmlrootelementProcessed = false;

    /**
     * フィールド [sheetType] に値がセットされたかどうか。
     */
    protected boolean fIsFieldSheetTypeProcessed = false;

    /**
     * フィールド [targetStyle] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTargetStyleProcessed = false;

    /**
     * フィールド [lineSeparator] に値がセットされたかどうか。
     */
    protected boolean fIsFieldLineSeparatorProcessed = false;

    /**
     * フィールド [searchTmpdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldSearchTmpdirProcessed = false;

    /**
     * フィールド [targetSubdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTargetSubdirProcessed = false;

    /**
     * フィールド [strictNullable] に値がセットされたかどうか。
     */
    protected boolean fIsFieldStrictNullableProcessed = false;

    /**
     * フィールド [supportedPiniaVersion] に値がセットされたかどうか。
     */
    protected boolean fIsFieldSupportedPiniaVersionProcessed = false;

    /**
     * verboseモードで動作させるかどうか。
     *
     * @param arg verboseモードで動作させるかどうか。
     */
    public void setVerbose(final boolean arg) {
        fInput.setVerbose(arg);
    }

    /**
     * verboseモードで動作させるかどうか。
     *
     * @return verboseモードで動作させるかどうか。
     */
    public boolean getVerbose() {
        return fInput.getVerbose();
    }

    /**
     * Antタスクの[metadir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 1<br>
     * メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setMetadir(final String arg) {
        fInput.setMetadir(arg);
        fIsFieldMetadirProcessed = true;
    }

    /**
     * Antタスクの[metadir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 1<br>
     * メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getMetadir() {
        return fInput.getMetadir();
    }

    /**
     * Antタスクの[targetdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 2<br>
     * 出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。<br>
     *
     * @param arg セットしたい値
     */
    public void setTargetdir(final String arg) {
        fInput.setTargetdir(arg);
        fIsFieldTargetdirProcessed = true;
    }

    /**
     * Antタスクの[targetdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 2<br>
     * 出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。<br>
     * デフォルト値[blanco]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTargetdir() {
        return fInput.getTargetdir();
    }

    /**
     * Antタスクの[tmpdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 3<br>
     * テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。<br>
     *
     * @param arg セットしたい値
     */
    public void setTmpdir(final String arg) {
        fInput.setTmpdir(arg);
        fIsFieldTmpdirProcessed = true;
    }

    /**
     * Antタスクの[tmpdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 3<br>
     * テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。<br>
     * デフォルト値[tmp]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTmpdir() {
        return fInput.getTmpdir();
    }

    /**
     * Antタスクの[encoding]アトリビュートのセッターメソッド。
     *
     * 項目番号: 4<br>
     * 自動生成するソースファイルの文字エンコーディングを指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setEncoding(final String arg) {
        fInput.setEncoding(arg);
        fIsFieldEncodingProcessed = true;
    }

    /**
     * Antタスクの[encoding]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 4<br>
     * 自動生成するソースファイルの文字エンコーディングを指定します。<br>
     *
     * @return このフィールドの値
     */
    public String getEncoding() {
        return fInput.getEncoding();
    }

    /**
     * Antタスクの[tabs]アトリビュートのセッターメソッド。
     *
     * 項目番号: 5<br>
     * タブをwhite spaceいくつで置き換えるか、という値です。<br>
     *
     * @param arg セットしたい値
     */
    public void setTabs(final String arg) {
        try {
            fInput.setTabs(Integer.parseInt(arg));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Antタスクの[tabs]アトリビュートに与えられた値の数値解析に失敗しました。" + e.toString());
        }
        fIsFieldTabsProcessed = true;
    }

    /**
     * Antタスクの[tabs]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 5<br>
     * タブをwhite spaceいくつで置き換えるか、という値です。<br>
     * デフォルト値[4]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTabs() {
        return String.valueOf(fInput.getTabs());
    }

    /**
     * Antタスクの[xmlrootelement]アトリビュートのセッターメソッド。
     *
     * 項目番号: 6<br>
     * XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。<br>
     *
     * @param arg セットしたい値
     */
    public void setXmlrootelement(final boolean arg) {
        fInput.setXmlrootelement(arg);
        fIsFieldXmlrootelementProcessed = true;
    }

    /**
     * Antタスクの[xmlrootelement]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 6<br>
     * XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public boolean getXmlrootelement() {
        return fInput.getXmlrootelement();
    }

    /**
     * Antタスクの[sheetType]アトリビュートのセッターメソッド。
     *
     * 項目番号: 7<br>
     * meta定義書が期待しているプログラミング言語を指定します<br>
     *
     * @param arg セットしたい値
     */
    public void setSheetType(final String arg) {
        fInput.setSheetType(arg);
        fIsFieldSheetTypeProcessed = true;
    }

    /**
     * Antタスクの[sheetType]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 7<br>
     * meta定義書が期待しているプログラミング言語を指定します<br>
     * デフォルト値[java]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getSheetType() {
        return fInput.getSheetType();
    }

    /**
     * Antタスクの[targetStyle]アトリビュートのセッターメソッド。
     *
     * 項目番号: 8<br>
     * 出力先フォルダの書式を指定します。&lt;br&gt;\nblanco: [targetdir]/main&lt;br&gt;\nmaven: [targetdir]/main/java&lt;br&gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)<br>
     *
     * @param arg セットしたい値
     */
    public void setTargetStyle(final String arg) {
        fInput.setTargetStyle(arg);
        fIsFieldTargetStyleProcessed = true;
    }

    /**
     * Antタスクの[targetStyle]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 8<br>
     * 出力先フォルダの書式を指定します。&lt;br&gt;\nblanco: [targetdir]/main&lt;br&gt;\nmaven: [targetdir]/main/java&lt;br&gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)<br>
     * デフォルト値[blanco]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTargetStyle() {
        return fInput.getTargetStyle();
    }

    /**
     * Antタスクの[lineSeparator]アトリビュートのセッターメソッド。
     *
     * 項目番号: 9<br>
     * 行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。<br>
     *
     * @param arg セットしたい値
     */
    public void setLineSeparator(final String arg) {
        fInput.setLineSeparator(arg);
        fIsFieldLineSeparatorProcessed = true;
    }

    /**
     * Antタスクの[lineSeparator]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 9<br>
     * 行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。<br>
     * デフォルト値[LF]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getLineSeparator() {
        return fInput.getLineSeparator();
    }

    /**
     * Antタスクの[searchTmpdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 10<br>
     * import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。<br>
     *
     * @param arg セットしたい値
     */
    public void setSearchTmpdir(final String arg) {
        fInput.setSearchTmpdir(arg);
        fIsFieldSearchTmpdirProcessed = true;
    }

    /**
     * Antタスクの[searchTmpdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 10<br>
     * import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。<br>
     *
     * @return このフィールドの値
     */
    public String getSearchTmpdir() {
        return fInput.getSearchTmpdir();
    }

    /**
     * Antタスクの[targetSubdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 11<br>
     * 配置・実装ディレクトリのサブディレクトリを指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setTargetSubdir(final String arg) {
        fInput.setTargetSubdir(arg);
        fIsFieldTargetSubdirProcessed = true;
    }

    /**
     * Antタスクの[targetSubdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 11<br>
     * 配置・実装ディレクトリのサブディレクトリを指定します。<br>
     * デフォルト値[store]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTargetSubdir() {
        return fInput.getTargetSubdir();
    }

    /**
     * Antタスクの[strictNullable]アトリビュートのセッターメソッド。
     *
     * 項目番号: 12<br>
     * Nullable な property に対して、? の付与をやめて | undefined | null の定義を行う。false の場合は ? が付与されて | undefined のみ付与される。<br>
     *
     * @param arg セットしたい値
     */
    public void setStrictNullable(final boolean arg) {
        fInput.setStrictNullable(arg);
        fIsFieldStrictNullableProcessed = true;
    }

    /**
     * Antタスクの[strictNullable]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 12<br>
     * Nullable な property に対して、? の付与をやめて | undefined | null の定義を行う。false の場合は ? が付与されて | undefined のみ付与される。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public boolean getStrictNullable() {
        return fInput.getStrictNullable();
    }

    /**
     * Antタスクの[supportedPiniaVersion]アトリビュートのセッターメソッド。
     *
     * 項目番号: 13<br>
     * Support する Pinia の最低メジャーバージョンを指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setSupportedPiniaVersion(final String arg) {
        try {
            fInput.setSupportedPiniaVersion(Integer.parseInt(arg));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Antタスクの[supportedPiniaVersion]アトリビュートに与えられた値の数値解析に失敗しました。" + e.toString());
        }
        fIsFieldSupportedPiniaVersionProcessed = true;
    }

    /**
     * Antタスクの[supportedPiniaVersion]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 13<br>
     * Support する Pinia の最低メジャーバージョンを指定します。<br>
     * デフォルト値[1]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getSupportedPiniaVersion() {
        return String.valueOf(fInput.getSupportedPiniaVersion());
    }

    /**
     * Antタスクのメイン処理。Apache Antから このメソッドが呼び出されます。
     *
     * @throws BuildException タスクとしての例外が発生した場合。
     */
    @Override
    public final void execute() throws BuildException {
        System.out.println("BlancoPiniaTask begin.");

        // 項目番号[1], アトリビュート[metadir]は必須入力です。入力チェックを行います。
        if (fIsFieldMetadirProcessed == false) {
            throw new BuildException("必須アトリビュート[metadir]が設定されていません。処理を中断します。");
        }

        if (getVerbose()) {
            System.out.println("- verbose:[true]");
            System.out.println("- metadir:[" + getMetadir() + "]");
            System.out.println("- targetdir:[" + getTargetdir() + "]");
            System.out.println("- tmpdir:[" + getTmpdir() + "]");
            System.out.println("- encoding:[" + getEncoding() + "]");
            System.out.println("- tabs:[" + getTabs() + "]");
            System.out.println("- xmlrootelement:[" + getXmlrootelement() + "]");
            System.out.println("- sheetType:[" + getSheetType() + "]");
            System.out.println("- targetStyle:[" + getTargetStyle() + "]");
            System.out.println("- lineSeparator:[" + getLineSeparator() + "]");
            System.out.println("- searchTmpdir:[" + getSearchTmpdir() + "]");
            System.out.println("- targetSubdir:[" + getTargetSubdir() + "]");
            System.out.println("- strictNullable:[" + getStrictNullable() + "]");
            System.out.println("- supportedPiniaVersion:[" + getSupportedPiniaVersion() + "]");
        }

        try {
            // 実際のAntタスクの主処理を実行します。
            // If you get a compile error at this point, You may be able to solve it by implementing a BlancoPiniaProcess interface and creating an BlancoPiniaProcessImpl class in package blanco.pinia.task.
            final BlancoPiniaProcess proc = new BlancoPiniaProcessImpl();
            if (proc.execute(fInput) != BlancoPiniaBatchProcess.END_SUCCESS) {
                throw new BuildException("The task has terminated abnormally.");
            }
        } catch (IllegalArgumentException e) {
            if (getVerbose()) {
                e.printStackTrace();
            }
            throw new BuildException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException("タスクを処理中に例外が発生しました。処理を中断します。" + e.toString());
        } catch (Error e) {
            e.printStackTrace();
            throw new BuildException("タスクを処理中にエラーが発生しました。処理を中断します。" + e.toString());
        }
    }
}
