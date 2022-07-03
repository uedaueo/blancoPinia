package blanco.pinia.valueobject;

import java.util.List;

/**
 * Vueストアのクラスをあらわすバリューオブジェクトクラス。このクラスの設定情報をもとにクラスが自動生成されます。
 */
public class BlancoPiniaClassStructure {
    /**
     * ストア名を指定します。必須項目です。
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * パッケージ名を指定します。必須項目です。
     *
     * フィールド: [package]。
     */
    private String fPackage;

    /**
     * 本番時にファイルを配置する際のベースディレクトリ。主にTypeScriptのimport文生成時に使用する事を想定しています。
     *
     * フィールド: [basedir]。
     */
    private String fBasedir;

    /**
     * 本番時に実装クラスを配置する際のベースディレクトリ。主にTypeScriptのimport文生成時に使用する事を想定しています。
     *
     * フィールド: [impledir]。
     */
    private String fImpledir;

    /**
     * クラスの説明です。
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * クラスの補助説明です。文字参照エンコード済みの値を格納してください。
     *
     * フィールド: [descriptionList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fDescriptionList = new java.util.ArrayList<java.lang.String>();

    /**
     * State 定義コードの先頭に書かれるコード群です。
     *
     * フィールド: [stateHeaderList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fStateHeaderList = new java.util.ArrayList<java.lang.String>();

    /**
     * Getters 定義コードの先頭に書かれるコード群です。
     *
     * フィールド: [gettersHeaderList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fGettersHeaderList = new java.util.ArrayList<java.lang.String>();

    /**
     * Actions 定義コードの先頭に書かれるコード群です。
     *
     * フィールド: [actionsHeaderList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fActionsHeaderList = new java.util.ArrayList<java.lang.String>();

    /**
     * Store 定義コードの先頭に書かれるコード群です。
     *
     * フィールド: [storeHeaderList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fStoreHeaderList = new java.util.ArrayList<java.lang.String>();

    /**
     * フィールド名の名前変形をおこなうかどうか。
     *
     * フィールド: [adjustFieldName]。
     * デフォルト: [true]。
     */
    private Boolean fAdjustFieldName = true;

    /**
     * デフォルト値の変形をおこなうかどうか。※なるべく変形を利用しないことを推奨したい。※プログラムAPIとして生成する際には、このフィールドを明示的に設定してください。
     *
     * フィールド: [adjustDefaultValue]。
     * デフォルト: [false]。
     */
    private Boolean fAdjustDefaultValue = false;

    /**
     * TypeScript 独自。blancoで一括生成されたクラスについて、import文を自動生成します。
     *
     * フィールド: [createImportList]。
     * デフォルト: [true]。
     */
    private Boolean fCreateImportList = true;

    /**
     * 継承するクラスを指定します。
     *
     * フィールド: [extends]。
     */
    private String fExtends;

    /**
     * Stateのリストを記憶します。
     *
     * フィールド: [stateList]。
     * デフォルト: [new java.util.ArrayList&lt;&gt;()]。
     */
    private List<BlancoPiniaStateStructure> fStateList = new java.util.ArrayList<>();

    /**
     * Gettersのリストを記憶します。
     *
     * フィールド: [gettersList]。
     * デフォルト: [new java.util.ArrayList&lt;&gt;()]。
     */
    private List<List<BlancoPiniaGettersStructure>> fGettersList = new java.util.ArrayList<>();

    /**
     * Actionsのリストを記憶します。
     *
     * フィールド: [actionsList]。
     * デフォルト: [new java.util.ArrayList&lt;&gt;()]。
     */
    private List<List<BlancoPiniaActionsStructure>> fActionsList = new java.util.ArrayList<>();

    /**
     * ファイル説明
     *
     * フィールド: [fileDescription]。
     */
    private String fFileDescription;

    /**
     * 外部定義された ActionsTree を使う場合は型名を指定します。import 文は「 Vueストア定義・Actions・ヘッダ情報」に記述します。
     *
     * フィールド: [importedActionsTree]。
     */
    private String fImportedActionsTree;

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [ストア名を指定します。必須項目です。]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [ストア名を指定します。必須項目です。]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [package] の値を設定します。
     *
     * フィールドの説明: [パッケージ名を指定します。必須項目です。]。
     *
     * @param argPackage フィールド[package]に設定する値。
     */
    public void setPackage(final String argPackage) {
        fPackage = argPackage;
    }

    /**
     * フィールド [package] の値を取得します。
     *
     * フィールドの説明: [パッケージ名を指定します。必須項目です。]。
     *
     * @return フィールド[package]から取得した値。
     */
    public String getPackage() {
        return fPackage;
    }

    /**
     * フィールド [basedir] の値を設定します。
     *
     * フィールドの説明: [本番時にファイルを配置する際のベースディレクトリ。主にTypeScriptのimport文生成時に使用する事を想定しています。]。
     *
     * @param argBasedir フィールド[basedir]に設定する値。
     */
    public void setBasedir(final String argBasedir) {
        fBasedir = argBasedir;
    }

    /**
     * フィールド [basedir] の値を取得します。
     *
     * フィールドの説明: [本番時にファイルを配置する際のベースディレクトリ。主にTypeScriptのimport文生成時に使用する事を想定しています。]。
     *
     * @return フィールド[basedir]から取得した値。
     */
    public String getBasedir() {
        return fBasedir;
    }

    /**
     * フィールド [impledir] の値を設定します。
     *
     * フィールドの説明: [本番時に実装クラスを配置する際のベースディレクトリ。主にTypeScriptのimport文生成時に使用する事を想定しています。]。
     *
     * @param argImpledir フィールド[impledir]に設定する値。
     */
    public void setImpledir(final String argImpledir) {
        fImpledir = argImpledir;
    }

    /**
     * フィールド [impledir] の値を取得します。
     *
     * フィールドの説明: [本番時に実装クラスを配置する際のベースディレクトリ。主にTypeScriptのimport文生成時に使用する事を想定しています。]。
     *
     * @return フィールド[impledir]から取得した値。
     */
    public String getImpledir() {
        return fImpledir;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [クラスの説明です。]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [クラスの説明です。]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [descriptionList] の値を設定します。
     *
     * フィールドの説明: [クラスの補助説明です。文字参照エンコード済みの値を格納してください。]。
     *
     * @param argDescriptionList フィールド[descriptionList]に設定する値。
     */
    public void setDescriptionList(final List<String> argDescriptionList) {
        fDescriptionList = argDescriptionList;
    }

    /**
     * フィールド [descriptionList] の値を取得します。
     *
     * フィールドの説明: [クラスの補助説明です。文字参照エンコード済みの値を格納してください。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[descriptionList]から取得した値。
     */
    public List<String> getDescriptionList() {
        return fDescriptionList;
    }

    /**
     * フィールド [stateHeaderList] の値を設定します。
     *
     * フィールドの説明: [State 定義コードの先頭に書かれるコード群です。]。
     *
     * @param argStateHeaderList フィールド[stateHeaderList]に設定する値。
     */
    public void setStateHeaderList(final List<String> argStateHeaderList) {
        fStateHeaderList = argStateHeaderList;
    }

    /**
     * フィールド [stateHeaderList] の値を取得します。
     *
     * フィールドの説明: [State 定義コードの先頭に書かれるコード群です。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[stateHeaderList]から取得した値。
     */
    public List<String> getStateHeaderList() {
        return fStateHeaderList;
    }

    /**
     * フィールド [gettersHeaderList] の値を設定します。
     *
     * フィールドの説明: [Getters 定義コードの先頭に書かれるコード群です。]。
     *
     * @param argGettersHeaderList フィールド[gettersHeaderList]に設定する値。
     */
    public void setGettersHeaderList(final List<String> argGettersHeaderList) {
        fGettersHeaderList = argGettersHeaderList;
    }

    /**
     * フィールド [gettersHeaderList] の値を取得します。
     *
     * フィールドの説明: [Getters 定義コードの先頭に書かれるコード群です。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[gettersHeaderList]から取得した値。
     */
    public List<String> getGettersHeaderList() {
        return fGettersHeaderList;
    }

    /**
     * フィールド [actionsHeaderList] の値を設定します。
     *
     * フィールドの説明: [Actions 定義コードの先頭に書かれるコード群です。]。
     *
     * @param argActionsHeaderList フィールド[actionsHeaderList]に設定する値。
     */
    public void setActionsHeaderList(final List<String> argActionsHeaderList) {
        fActionsHeaderList = argActionsHeaderList;
    }

    /**
     * フィールド [actionsHeaderList] の値を取得します。
     *
     * フィールドの説明: [Actions 定義コードの先頭に書かれるコード群です。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[actionsHeaderList]から取得した値。
     */
    public List<String> getActionsHeaderList() {
        return fActionsHeaderList;
    }

    /**
     * フィールド [storeHeaderList] の値を設定します。
     *
     * フィールドの説明: [Store 定義コードの先頭に書かれるコード群です。]。
     *
     * @param argStoreHeaderList フィールド[storeHeaderList]に設定する値。
     */
    public void setStoreHeaderList(final List<String> argStoreHeaderList) {
        fStoreHeaderList = argStoreHeaderList;
    }

    /**
     * フィールド [storeHeaderList] の値を取得します。
     *
     * フィールドの説明: [Store 定義コードの先頭に書かれるコード群です。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[storeHeaderList]から取得した値。
     */
    public List<String> getStoreHeaderList() {
        return fStoreHeaderList;
    }

    /**
     * フィールド [adjustFieldName] の値を設定します。
     *
     * フィールドの説明: [フィールド名の名前変形をおこなうかどうか。]。
     *
     * @param argAdjustFieldName フィールド[adjustFieldName]に設定する値。
     */
    public void setAdjustFieldName(final Boolean argAdjustFieldName) {
        fAdjustFieldName = argAdjustFieldName;
    }

    /**
     * フィールド [adjustFieldName] の値を取得します。
     *
     * フィールドの説明: [フィールド名の名前変形をおこなうかどうか。]。
     * デフォルト: [true]。
     *
     * @return フィールド[adjustFieldName]から取得した値。
     */
    public Boolean getAdjustFieldName() {
        return fAdjustFieldName;
    }

    /**
     * フィールド [adjustDefaultValue] の値を設定します。
     *
     * フィールドの説明: [デフォルト値の変形をおこなうかどうか。※なるべく変形を利用しないことを推奨したい。※プログラムAPIとして生成する際には、このフィールドを明示的に設定してください。]。
     *
     * @param argAdjustDefaultValue フィールド[adjustDefaultValue]に設定する値。
     */
    public void setAdjustDefaultValue(final Boolean argAdjustDefaultValue) {
        fAdjustDefaultValue = argAdjustDefaultValue;
    }

    /**
     * フィールド [adjustDefaultValue] の値を取得します。
     *
     * フィールドの説明: [デフォルト値の変形をおこなうかどうか。※なるべく変形を利用しないことを推奨したい。※プログラムAPIとして生成する際には、このフィールドを明示的に設定してください。]。
     * デフォルト: [false]。
     *
     * @return フィールド[adjustDefaultValue]から取得した値。
     */
    public Boolean getAdjustDefaultValue() {
        return fAdjustDefaultValue;
    }

    /**
     * フィールド [createImportList] の値を設定します。
     *
     * フィールドの説明: [TypeScript 独自。blancoで一括生成されたクラスについて、import文を自動生成します。]。
     *
     * @param argCreateImportList フィールド[createImportList]に設定する値。
     */
    public void setCreateImportList(final Boolean argCreateImportList) {
        fCreateImportList = argCreateImportList;
    }

    /**
     * フィールド [createImportList] の値を取得します。
     *
     * フィールドの説明: [TypeScript 独自。blancoで一括生成されたクラスについて、import文を自動生成します。]。
     * デフォルト: [true]。
     *
     * @return フィールド[createImportList]から取得した値。
     */
    public Boolean getCreateImportList() {
        return fCreateImportList;
    }

    /**
     * フィールド [extends] の値を設定します。
     *
     * フィールドの説明: [継承するクラスを指定します。]。
     *
     * @param argExtends フィールド[extends]に設定する値。
     */
    public void setExtends(final String argExtends) {
        fExtends = argExtends;
    }

    /**
     * フィールド [extends] の値を取得します。
     *
     * フィールドの説明: [継承するクラスを指定します。]。
     *
     * @return フィールド[extends]から取得した値。
     */
    public String getExtends() {
        return fExtends;
    }

    /**
     * フィールド [stateList] の値を設定します。
     *
     * フィールドの説明: [Stateのリストを記憶します。]。
     *
     * @param argStateList フィールド[stateList]に設定する値。
     */
    public void setStateList(final List<BlancoPiniaStateStructure> argStateList) {
        fStateList = argStateList;
    }

    /**
     * フィールド [stateList] の値を取得します。
     *
     * フィールドの説明: [Stateのリストを記憶します。]。
     * デフォルト: [new java.util.ArrayList&lt;&gt;()]。
     *
     * @return フィールド[stateList]から取得した値。
     */
    public List<BlancoPiniaStateStructure> getStateList() {
        return fStateList;
    }

    /**
     * フィールド [gettersList] の値を設定します。
     *
     * フィールドの説明: [Gettersのリストを記憶します。]。
     *
     * @param argGettersList フィールド[gettersList]に設定する値。
     */
    public void setGettersList(final List<List<BlancoPiniaGettersStructure>> argGettersList) {
        fGettersList = argGettersList;
    }

    /**
     * フィールド [gettersList] の値を取得します。
     *
     * フィールドの説明: [Gettersのリストを記憶します。]。
     * デフォルト: [new java.util.ArrayList&lt;&gt;()]。
     *
     * @return フィールド[gettersList]から取得した値。
     */
    public List<List<BlancoPiniaGettersStructure>> getGettersList() {
        return fGettersList;
    }

    /**
     * フィールド [actionsList] の値を設定します。
     *
     * フィールドの説明: [Actionsのリストを記憶します。]。
     *
     * @param argActionsList フィールド[actionsList]に設定する値。
     */
    public void setActionsList(final List<List<BlancoPiniaActionsStructure>> argActionsList) {
        fActionsList = argActionsList;
    }

    /**
     * フィールド [actionsList] の値を取得します。
     *
     * フィールドの説明: [Actionsのリストを記憶します。]。
     * デフォルト: [new java.util.ArrayList&lt;&gt;()]。
     *
     * @return フィールド[actionsList]から取得した値。
     */
    public List<List<BlancoPiniaActionsStructure>> getActionsList() {
        return fActionsList;
    }

    /**
     * フィールド [fileDescription] の値を設定します。
     *
     * フィールドの説明: [ファイル説明]。
     *
     * @param argFileDescription フィールド[fileDescription]に設定する値。
     */
    public void setFileDescription(final String argFileDescription) {
        fFileDescription = argFileDescription;
    }

    /**
     * フィールド [fileDescription] の値を取得します。
     *
     * フィールドの説明: [ファイル説明]。
     *
     * @return フィールド[fileDescription]から取得した値。
     */
    public String getFileDescription() {
        return fFileDescription;
    }

    /**
     * フィールド [importedActionsTree] の値を設定します。
     *
     * フィールドの説明: [外部定義された ActionsTree を使う場合は型名を指定します。import 文は「 Vueストア定義・Actions・ヘッダ情報」に記述します。]。
     *
     * @param argImportedActionsTree フィールド[importedActionsTree]に設定する値。
     */
    public void setImportedActionsTree(final String argImportedActionsTree) {
        fImportedActionsTree = argImportedActionsTree;
    }

    /**
     * フィールド [importedActionsTree] の値を取得します。
     *
     * フィールドの説明: [外部定義された ActionsTree を使う場合は型名を指定します。import 文は「 Vueストア定義・Actions・ヘッダ情報」に記述します。]。
     *
     * @return フィールド[importedActionsTree]から取得した値。
     */
    public String getImportedActionsTree() {
        return fImportedActionsTree;
    }

    /**
     * Gets the string representation of this value object.
     *
     * <P>Precautions for use</P>
     * <UL>
     * <LI>Only the shallow range of the object will be subject to the stringification process.
     * <LI>Do not use this method if the object has a circular reference.
     * </UL>
     *
     * @return String representation of a value object.
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("blanco.pinia.valueobject.BlancoPiniaClassStructure[");
        buf.append("name=" + fName);
        buf.append(",package=" + fPackage);
        buf.append(",basedir=" + fBasedir);
        buf.append(",impledir=" + fImpledir);
        buf.append(",description=" + fDescription);
        buf.append(",descriptionList=" + fDescriptionList);
        buf.append(",stateHeaderList=" + fStateHeaderList);
        buf.append(",gettersHeaderList=" + fGettersHeaderList);
        buf.append(",actionsHeaderList=" + fActionsHeaderList);
        buf.append(",storeHeaderList=" + fStoreHeaderList);
        buf.append(",adjustFieldName=" + fAdjustFieldName);
        buf.append(",adjustDefaultValue=" + fAdjustDefaultValue);
        buf.append(",createImportList=" + fCreateImportList);
        buf.append(",extends=" + fExtends);
        buf.append(",stateList=" + fStateList);
        buf.append(",gettersList=" + fGettersList);
        buf.append(",actionsList=" + fActionsList);
        buf.append(",fileDescription=" + fFileDescription);
        buf.append(",importedActionsTree=" + fImportedActionsTree);
        buf.append("]");
        return buf.toString();
    }

    /**
     * Copies this value object to the specified target.
     *
     * <P>Cautions for use</P>
     * <UL>
     * <LI>Only the shallow range of the object will be subject to the copying process.
     * <LI>Do not use this method if the object has a circular reference.
     * </UL>
     *
     * @param target target value object.
     */
    public void copyTo(final BlancoPiniaClassStructure target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoPiniaClassStructure#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fPackage
        // Type: java.lang.String
        target.fPackage = this.fPackage;
        // Name: fBasedir
        // Type: java.lang.String
        target.fBasedir = this.fBasedir;
        // Name: fImpledir
        // Type: java.lang.String
        target.fImpledir = this.fImpledir;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fDescriptionList
        // Type: java.util.List
        // Field[fDescriptionList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fStateHeaderList
        // Type: java.util.List
        // Field[fStateHeaderList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fGettersHeaderList
        // Type: java.util.List
        // Field[fGettersHeaderList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fActionsHeaderList
        // Type: java.util.List
        // Field[fActionsHeaderList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fStoreHeaderList
        // Type: java.util.List
        // Field[fStoreHeaderList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fAdjustFieldName
        // Type: java.lang.Boolean
        target.fAdjustFieldName = this.fAdjustFieldName;
        // Name: fAdjustDefaultValue
        // Type: java.lang.Boolean
        target.fAdjustDefaultValue = this.fAdjustDefaultValue;
        // Name: fCreateImportList
        // Type: java.lang.Boolean
        target.fCreateImportList = this.fCreateImportList;
        // Name: fExtends
        // Type: java.lang.String
        target.fExtends = this.fExtends;
        // Name: fStateList
        // Type: java.util.List
        // Field[fStateList] is an unsupported type[java.util.Listblanco.pinia.valueobject.BlancoPiniaStateStructure].
        // Name: fGettersList
        // Type: java.util.List
        // Field[fGettersList] is an unsupported type[java.util.Listjava.util.List<blanco.pinia.valueobject.BlancoPiniaGettersStructure>].
        // Name: fActionsList
        // Type: java.util.List
        // Field[fActionsList] is an unsupported type[java.util.Listjava.util.List<blanco.pinia.valueobject.BlancoPiniaActionsStructure>].
        // Name: fFileDescription
        // Type: java.lang.String
        target.fFileDescription = this.fFileDescription;
        // Name: fImportedActionsTree
        // Type: java.lang.String
        target.fImportedActionsTree = this.fImportedActionsTree;
    }
}
