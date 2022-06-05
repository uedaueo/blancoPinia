/*
 * blanco Framework
 * Copyright (C) 2004-2010 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.pinia;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoStringUtil;
import blanco.pinia.message.BlancoPiniaMessage;
import blanco.pinia.resourcebundle.BlancoPiniaResourceBundle;
import blanco.pinia.valueobject.BlancoPiniaActionsStructure;
import blanco.pinia.valueobject.BlancoPiniaClassStructure;
import blanco.pinia.valueobject.BlancoPiniaGettersStructure;
import blanco.pinia.valueobject.BlancoPiniaStateStructure;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This is a class to auto-generate TypeScript source code from intermediate XML files for value objects.
 *
 * This is one of the main classes of BlancoValueObjectTs.
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoPiniaXml2TypeScriptClass {
    /**
     * A message.
     */
    private final BlancoPiniaMessage fMsg = new BlancoPiniaMessage();

    /**
     * A resource bundle object for blancoValueObject.
     */
    private final BlancoPiniaResourceBundle fBundle = new BlancoPiniaResourceBundle();

    /**
     * A programming language expected for the input sheet.
     */
    private int fSheetLang = BlancoCgSupportedLang.PHP;

    public void setSheetLang(final int argSheetLang) {
        fSheetLang = argSheetLang;
    }

    /**
     * Style of the source code generation destination directory
     */
    private boolean fTargetStyleAdvanced = false;
    public void setTargetStyleAdvanced(boolean argTargetStyleAdvanced) {
        this.fTargetStyleAdvanced = argTargetStyleAdvanced;
    }
    public boolean isTargetStyleAdvanced() {
        return this.fTargetStyleAdvanced;
    }

    private boolean fVerbose = false;
    public void setVerbose(boolean argVerbose) {
        this.fVerbose = argVerbose;
    }
    public boolean isVerbose() {
        return this.fVerbose;
    }

    private int fTabs = 4;
    public int getTabs() {
        return fTabs;
    }
    public void setTabs(int fTabs) {
        this.fTabs = fTabs;
    }

    private String fListClass = "";
    public String getListClass() {
        return this.fListClass;
    }
    public void setListClass(String listClass) {
        this.fListClass = listClass;
    }

    /**
     * A factory for blancoCg to be used internally.
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * Source file information for blancoCg to be used internally.
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    /**
     * Class information for blancoCg to be used internally.
     */
    private BlancoCgClass fCgClass = null;

    /**
     * Interface information for blancoCg to be used internally.
     */
    private BlancoCgInterface fCgInterface = null;

    /**
     * Character encoding of auto-generated source files.
     */
    private String fEncoding = null;

    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    private boolean fIsXmlRootElement = false;

    public void setXmlRootElement(final boolean isXmlRootElement) {
        fIsXmlRootElement = isXmlRootElement;
    }

    /**
     * Auto-generates TypeScript source code from an intermediate XML file representing a value object.
     *
     * @param argMetaXmlSourceFile
     *            An XML file containing meta-information about the ValueObject.
     * @param argDirectoryTarget
     *            Source code generation destination directory.
     * @throws IOException
     *             If an I/O exception occurs.
     * @return
     */
    public BlancoPiniaClassStructure[] process(
            final File argMetaXmlSourceFile,
            final File argDirectoryTarget) throws IOException {
        BlancoPiniaXmlParser parser = new BlancoPiniaXmlParser();
        parser.setVerbose(this.isVerbose());
        final BlancoPiniaClassStructure[] structures = parser.parse(argMetaXmlSourceFile);

        for (int index = 0; index < structures.length; index++) {
            BlancoPiniaClassStructure classStructure = structures[index];

            if (classStructure.getStateList() == null || classStructure.getStateList().size() == 0) {
                throw new IllegalArgumentException(fBundle.getXml2sourceFileErr007(classStructure.getName()));
            }

            if (isVerbose()) {
                System.out.println("%%% GettersList size = " + (classStructure.getGettersList() != null ? classStructure.getGettersList().size() : "null"));
            }

            boolean useGetters = isGettersDefined(classStructure.getGettersList());
            boolean useActions = isActionsDefined(classStructure.getActionsList());

            /* Generate defineStore calling. */
            generateDefineStore(classStructure, argDirectoryTarget, useGetters, useActions);

            /* Generate props options */
            generateState(classStructure, argDirectoryTarget);

            /* Generate getters functions */
            if (useGetters) {
                generateDefineGetters(classStructure, argDirectoryTarget);
            }

            /* Generate actions functions */
            if (useActions) {
                generateDefineActions(classStructure, argDirectoryTarget);
            }
        }
        return structures;
    }

    /**
     * Generate options for defineStore
     *
     * @param argClassStructure
     *          Class Informtions.
     * @param argDirectoryTarget
     *          Output directory for TypeScript source code.
     * @param argUseGetters
     *          Use Getters functions or not
     * @param argUseActions
     *          Use Actions functions or not
     * @throws IOException
     *          if an I/O exception occurs.
     */
    public void generateDefineStore(
            final BlancoPiniaClassStructure argClassStructure,
            final File argDirectoryTarget,
            final Boolean argUseGetters,
            final Boolean argUseActions) throws IOException {
        /*
         * The output directory will be in the format specified by the targetStyle argument of
         the ant task.
         * For compatibility, the output directory will be blanco/main if it is not specified.
         * by tueda, 2019/08/30
         */
        String strTarget = argDirectoryTarget
                .getAbsolutePath(); // advanced
        if (!this.isTargetStyleAdvanced()) {
            strTarget += "/main"; // legacy
        }
        final File fileBlancoMain = new File(strTarget);

        /* tueda DEBUG */
        if (this.isVerbose()) {
            System.out.println("/* tueda */ generateDefineComponent argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
        }

        String storeName = argClassStructure.getName();
        String storeId = BlancoNameAdjuster.toParameterName(storeName);
        String useStoreConst = "use" + storeName;
        String stateFunc = storeId + "State";
        String gettersFunc = storeId + "Getters";
        String actionsFunc = storeId + "Actions";
        String baseDir = argClassStructure.getBasedir();
        String impleDir = argClassStructure.getImpledir();

        String packageName = argClassStructure.getPackage();
        if (packageName == null) {
            packageName = "";
        }
        String packageDir = packageName.replace(".", "/");

        baseDir += "/" + packageDir;
        impleDir += "/" + packageDir;

        // Gets an instance of the BlancoCgObjectFactory class.
        fCgFactory = BlancoCgObjectFactory.getInstance();

        fCgSourceFile = fCgFactory.createSourceFile(argClassStructure
                .getPackage(), argClassStructure.getFileDescription());
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());

        /* imports */
        fCgSourceFile.getHeaderList().add("import {defineStore} from \"pinia\"");
        fCgSourceFile.getHeaderList().add(
                "import { " + stateFunc + " } from \"" + baseDir + "/" + storeName + "State\""
        );
        if (argUseGetters) {
            fCgSourceFile.getHeaderList().add(
                    "import { " + gettersFunc + " } from \"" + impleDir + "/" + storeName + "Getters\""
            );
        }
        if (argUseActions) {
            fCgSourceFile.getHeaderList().add(
                    "import { " + actionsFunc + " } from \"" + impleDir + "/" + storeName + "Actions\""
            );
        }

        // Creates a class.
        fCgClass = fCgFactory.createClass(storeName, "");
        fCgSourceFile.getClassList().add(fCgClass);
        // Do not declare class for defineComponent calling.
        fCgClass.setNoClassDeclare(true);

        List<String> plainTextList = fCgClass.getPlainTextList();
        // defineComponent call
        // Sets the JavaDoc for the class.
        plainTextList.add("/**");
        plainTextList.add(" * " + argClassStructure.getDescription());
        for (String line : argClassStructure.getDescriptionList()) {
            plainTextList.add(" * " + line);
        }
        plainTextList.add(" */");
        plainTextList.add("export const " + useStoreConst + " = defineStore(\"" + storeId + "\", {");
        String optionsLine = "state: () => " + stateFunc;
        if (argUseGetters || argUseActions) {
            optionsLine += ",";
        }
        plainTextList.add(optionsLine);
        if (argUseGetters) {
            optionsLine = "getters: " + gettersFunc;
            if (argUseActions) {
                optionsLine += ",";
            }
            plainTextList.add(optionsLine);
        }
        if (argUseActions) {
            plainTextList.add("actions: " + actionsFunc);
        }
        plainTextList.add("});");

        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getTsSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * Generate state file.
     *
     * @param argClassStructure
     * @param argDirectoryTarget
     * @throws IOException
     */
    public void generateState(
            final BlancoPiniaClassStructure argClassStructure,
            final File argDirectoryTarget) throws IOException {
        /*
         * The output directory will be in the format specified by the targetStyle argument of
         the ant task.
         * For compatibility, the output directory will be blanco/main if it is not specified.
         * by tueda, 2019/08/30
         */
        String strTarget = argDirectoryTarget
                .getAbsolutePath(); // advanced
        if (!this.isTargetStyleAdvanced()) {
            strTarget += "/main"; // legacy
        }
        final File fileBlancoMain = new File(strTarget);

        /* tueda DEBUG */
        if (this.isVerbose()) {
            System.out.println("/* tueda */ generateState argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
        }

        String storeName = argClassStructure.getName();
        String storeId = BlancoNameAdjuster.toParameterName(storeName);
        String stateType = storeName + "State";
        String stateConst = storeId + "State";

        // Gets an instance of the BlancoCgObjectFactory class.
        fCgFactory = BlancoCgObjectFactory.getInstance();

        fCgSourceFile = fCgFactory.createSourceFile(argClassStructure
                .getPackage(), argClassStructure.getFileDescription());
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());

        // Create interface first.
        fCgInterface = fCgFactory.createInterface(stateType, fBundle.getXml2sourceFileStateDefine());
        fCgSourceFile.getInterfaceList().add(fCgInterface);
        fCgInterface.setAccess("export");

        for (BlancoPiniaStateStructure stateStructure : argClassStructure.getStateList()) {
            BlancoCgField fieldInf = fCgFactory.createField(
                    stateStructure.getName(),
                    stateStructure.getType(),
                    stateStructure.getDescription()
            );
            fCgInterface.getFieldList().add(fieldInf);

            for (String line : stateStructure.getDescriptionList()) {
                fieldInf.getLangDoc().getDescriptionList().add(line);
            }
            String generics = stateStructure.getGeneric();
            if (BlancoStringUtil.null2Blank(generics).length() != 0) {
                fieldInf.getType().setGenerics(generics);
            }
            fieldInf.setNotnull(!stateStructure.getNullable());
            fieldInf.setAccess("");
        }

        // Create non-named class.
        fCgClass = fCgFactory.createClass(stateType, null);
        fCgSourceFile.getClassList().add(fCgClass);
        // Do not declare class for defineComponent calling.
        fCgClass.setNoClassDeclare(true);

        List<String> plainTextList = fCgClass.getPlainTextList();
        plainTextList.add("");

        // state definition
        BlancoCgField stateField = fCgFactory.createField(stateConst, stateType, fBundle.getXml2sourceFileStateDefine());
        fCgClass.getFieldList().add(stateField);
        stateField.setAccess("export const");
        stateField.setNotnull(true);

        StringBuffer stateBuf = new StringBuffer();
        int loopCount = 0;
        stateBuf.append("{" + this.getLineSeparator());
        for (BlancoPiniaStateStructure stateStructure : argClassStructure.getStateList()) {
            boolean hasDefault = BlancoStringUtil.null2Blank(stateStructure.getDefault()).length() != 0;
            boolean isNullable = stateStructure.getNullable();
            if (hasDefault) {
                if (loopCount > 0) {
                    stateBuf.append("," + this.getLineSeparator());
                }
                stateBuf.append(this.getTabSpace() + stateStructure.getName() + (isNullable ? "?:" : ": "));
                stateBuf.append(stateStructure.getDefault());
            }
            loopCount++;
        }
        stateBuf.append(this.getLineSeparator() + "}");
        stateField.setDefault(stateBuf.toString());

        /* In TypeScript, sets the header instead of import. */
        for (int index = 0; index < argClassStructure.getStateHeaderList()
                .size(); index++) {
            final String header = argClassStructure.getStateHeaderList()
                    .get(index);
            fCgSourceFile.getHeaderList().add(header);
        }

        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getTsSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * generate getters definitions.
     *
     * @param argClassStructure
     * @param argDirectoryTarget
     * @throws IOException
     */
    public void generateDefineGetters(
            final BlancoPiniaClassStructure argClassStructure,
            final File argDirectoryTarget) throws IOException {
        /*
         * The output directory will be in the format specified by the targetStyle argument of
         the ant task.
         * For compatibility, the output directory will be blanco/main if it is not specified.
         * by tueda, 2019/08/30
         */
        String strTarget = argDirectoryTarget
                .getAbsolutePath(); // advanced
        if (!this.isTargetStyleAdvanced()) {
            strTarget += "/main"; // legacy
        }
        final File fileBlancoMain = new File(strTarget);

        /* tueda DEBUG */
        if (this.isVerbose()) {
            System.out.println("/* tueda */ generateDefineGetters argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
        }

        String storeName = argClassStructure.getName();
        String stateType = storeName + "State";
        String gettersDefineName = "Define" + storeName + "Getters";
        String gettersDefineFunc = BlancoNameAdjuster.toParameterName(gettersDefineName);
        String gettersTree = storeName + "GettersTree";

        // Gets an instance of the BlancoCgObjectFactory class.
        fCgFactory = BlancoCgObjectFactory.getInstance();

        fCgSourceFile = fCgFactory.createSourceFile(argClassStructure
                .getPackage(), argClassStructure.getFileDescription());
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());

        // Create class just for filename define
        fCgClass = fCgFactory.createClass(gettersDefineName, null);
        fCgSourceFile.getClassList().add(fCgClass);
        // Do not declare class for defineComponent calling.
        fCgClass.setNoClassDeclare(true);

        List<String> plainTextList = fCgClass.getPlainTextList();
        plainTextList.add("");

        // declare getters tree type
        plainTextList.add("/** " + fBundle.getXml2sourceFileGettersDefine() + " */");
        plainTextList.add("export declare type " + gettersTree + "<S extends StateTree> = {");
        for (List<BlancoPiniaGettersStructure> gettersStructureList : argClassStructure.getGettersList()) {
            defineGetters(plainTextList, gettersStructureList);
        }
        plainTextList.add("}");

        // Create defineGettersFunction.
        BlancoCgMethod defineFunction = fCgFactory.createMethod(gettersDefineFunc, argClassStructure.getDescription());
        fCgClass.getMethodList().add(defineFunction);
        for (String line : argClassStructure.getDescriptionList()) {
            defineFunction.getLangDoc().getDescriptionList().add(line);
        }
        defineFunction.setAccess("export function");

        BlancoCgParameter gettersParam = fCgFactory.createParameter("getters", gettersTree, fBundle.getXml2sourceFileGettersParam());
        defineFunction.getParameterList().add(gettersParam);
        gettersParam.getType().setGenerics(stateType);
        gettersParam.setNotnull(true);

        BlancoCgReturn gettersReturn = fCgFactory.createReturn(gettersTree, fBundle.getXml2sourceFileGettersReturn());
        defineFunction.setReturn(gettersReturn);
        defineFunction.setNotnull(true);
        gettersReturn.getType().setGenerics(stateType);

        List<String> lines = defineFunction.getLineList();
        lines.add("return getters;");

        /* In TypeScript, sets the header instead of import. */
        for (int index = 0; index < argClassStructure.getGettersHeaderList()
                .size(); index++) {
            final String header = argClassStructure.getGettersHeaderList()
                    .get(index);
            fCgSourceFile.getHeaderList().add(header);
        }

        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getTsSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }


    /**
     * generate actions definitions.
     *
     * @param argClassStructure
     * @param argDirectoryTarget
     * @throws IOException
     */
    public void generateDefineActions(
            final BlancoPiniaClassStructure argClassStructure,
            final File argDirectoryTarget) throws IOException {
        /*
         * The output directory will be in the format specified by the targetStyle argument of
         the ant task.
         * For compatibility, the output directory will be blanco/main if it is not specified.
         * by tueda, 2019/08/30
         */
        String strTarget = argDirectoryTarget
                .getAbsolutePath(); // advanced
        if (!this.isTargetStyleAdvanced()) {
            strTarget += "/main"; // legacy
        }
        final File fileBlancoMain = new File(strTarget);

        /* tueda DEBUG */
        if (this.isVerbose()) {
            System.out.println("/* tueda */ generateDefineActions argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
        }

        String storeName = argClassStructure.getName();
        String stateType = storeName + "State";
        String actionsDefineName = "Define" + storeName + "Actions";
        String actionsDefineFunc = BlancoNameAdjuster.toParameterName(actionsDefineName);
        String actionsTree = storeName + "ActionsTree";

        // Gets an instance of the BlancoCgObjectFactory class.
        fCgFactory = BlancoCgObjectFactory.getInstance();

        fCgSourceFile = fCgFactory.createSourceFile(argClassStructure
                .getPackage(), argClassStructure.getFileDescription());
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());

        // Create class just for filename define
        fCgClass = fCgFactory.createClass(actionsDefineName, null);
        fCgSourceFile.getClassList().add(fCgClass);
        // Do not declare class for defineComponent calling.
        fCgClass.setNoClassDeclare(true);

        List<String> plainTextList = fCgClass.getPlainTextList();
        plainTextList.add("");

        // declare getters tree type
        plainTextList.add("/** " + fBundle.getXml2sourceFileActionsDefine() + " */");
        plainTextList.add("export declare type " + actionsTree + " = {");
        for (List<BlancoPiniaActionsStructure> actionsStructureList : argClassStructure.getActionsList()) {
            createActionsLangDoc(plainTextList, actionsStructureList);
            defineActions(plainTextList, actionsStructureList);
        }
        plainTextList.add("}");

        // Create defineActionsFunction.
        BlancoCgMethod defineFunction = fCgFactory.createMethod(actionsDefineFunc, argClassStructure.getDescription());
        fCgClass.getMethodList().add(defineFunction);
        for (String line : argClassStructure.getDescriptionList()) {
            defineFunction.getLangDoc().getDescriptionList().add(line);
        }
        defineFunction.setAccess("export function");

        String actionsType = actionsTree + " & ThisType<" + actionsTree + " & UnwrapRef<" + stateType + ">>";
        BlancoCgParameter actionsParam = fCgFactory.createParameter("actions", actionsType, fBundle.getXml2sourceFileActionsParam());
        defineFunction.getParameterList().add(actionsParam);
        actionsParam.setNotnull(true);

        BlancoCgReturn actionsReturn = fCgFactory.createReturn(actionsType, fBundle.getXml2sourceFileActionsReturn());
        defineFunction.setReturn(actionsReturn);
        defineFunction.setNotnull(true);

        List<String> lines = defineFunction.getLineList();
        lines.add("return actions;");

        /* In TypeScript, sets the header instead of import. */
        for (int index = 0; index < argClassStructure.getActionsHeaderList()
                .size(); index++) {
            final String header = argClassStructure.getActionsHeaderList()
                    .get(index);
            fCgSourceFile.getHeaderList().add(header);
        }

        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getTsSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * Create a LangDoc of Getters Function
     *
     * @param argListText
     * @param gettersStructureList
     */
    private void createGettersLangDoc(List<String> argListText, List<BlancoPiniaGettersStructure> gettersStructureList) {
        int size = gettersStructureList.size();
        argListText.add("/**");
        BlancoPiniaGettersStructure firstStructure = gettersStructureList.get(0);
        argListText.add(" * " + firstStructure.getDescription());
        for (String line : firstStructure.getDescriptionList()) {
            argListText.add(" * " + line);
        }
        argListText.add(" * ");

        for (int i = 1; i < size; i++) {
            BlancoPiniaGettersStructure paramStructure = gettersStructureList.get(i);
            argListText.add(" * @param " + paramStructure.getName() + " " + paramStructure.getDescription());
        }
        String type = firstStructure.getType();
        String generics = firstStructure.getGeneric();
        if (BlancoStringUtil.null2Blank(generics).length() > 0) {
            type = type + "<" + generics + ">";
        }
        if (BlancoStringUtil.null2Blank(type).length() > 0) {
            argListText.add(" * @return " + type);
        }
        argListText.add(" */");
    }

    /**
     * Create a LangDoc of Actions Function
     *
     * @param argListText
     * @param actionsStructureList
     */
    private void createActionsLangDoc(List<String> argListText, List<BlancoPiniaActionsStructure> actionsStructureList) {
        int size = actionsStructureList.size();
        argListText.add("/**");
        BlancoPiniaActionsStructure firstStructure = actionsStructureList.get(0);
        argListText.add(" * " + firstStructure.getDescription());
        for (String line : firstStructure.getDescriptionList()) {
            argListText.add(" * " + line);
        }
        argListText.add(" * ");

        for (int i = 1; i < size; i++) {
            BlancoPiniaActionsStructure paramStructure = actionsStructureList.get(i);
            argListText.add(" * @param " + paramStructure.getName() + " " + paramStructure.getDescription());
        }
        String type = firstStructure.getType();
        String generics = firstStructure.getGeneric();
        if (BlancoStringUtil.null2Blank(generics).length() > 0) {
            type = type + "<" + generics + ">";
        }
        if (BlancoStringUtil.null2Blank(type).length() > 0) {
            argListText.add(" * @return " + type);
        }
        argListText.add(" */");
    }

    private void defineGetters(List<String> argListText, List<BlancoPiniaGettersStructure> gettersStructureList) {
        int size = gettersStructureList.size();
        BlancoPiniaGettersStructure firstStructure = gettersStructureList.get(0);
        argListText.add(firstStructure.getName() + "(state: S): {");
        createGettersLangDoc(argListText, gettersStructureList);
        argListText.add("(");
        for (int i = 1; i < size; i++) {
            BlancoPiniaGettersStructure gettersStructure = gettersStructureList.get(i);
            String type = BlancoPiniaUtil.getSimpleClassName(gettersStructure.getType());
            String generics = gettersStructure.getGeneric();
            if (BlancoStringUtil.null2Blank(generics).length() > 0) {
                type = type + "<" + BlancoPiniaUtil.getSimpleClassName(generics) + ">";
            }
            if (i > 1) {
                addCommaToListString(argListText);
            }
            argListText.add(gettersStructure.getName() + ": " + type);
        }
        String firstType = BlancoPiniaUtil.getSimpleClassName(firstStructure.getType());
        String firstGenerics = firstStructure.getGeneric();
        if (BlancoStringUtil.null2Blank(firstGenerics).length() > 0) {
            firstType = firstType + "<" + BlancoPiniaUtil.getSimpleClassName(firstGenerics) + ">";
        }
        argListText.add("): " + firstType);
        argListText.add("}");
    }

    private void defineActions(List<String> argListText, List<BlancoPiniaActionsStructure> actionsStructureList) {
        int size = actionsStructureList.size();
        BlancoPiniaActionsStructure firstStructure = actionsStructureList.get(0);
        argListText.add(firstStructure.getName() + "(");
        for (int i = 1; i < size; i++) {
            BlancoPiniaActionsStructure actionsStructure = actionsStructureList.get(i);
            String type = BlancoPiniaUtil.getSimpleClassName(actionsStructure.getType());
            String generics = actionsStructure.getGeneric();
            if (BlancoStringUtil.null2Blank(generics).length() > 0) {
                type = type + "<" + BlancoPiniaUtil.getSimpleClassName(generics) + ">";
            }
            if (i > 1) {
                addCommaToListString(argListText);
            }
            argListText.add(actionsStructure.getName() + ": " + type);
        }
        String firstType = BlancoPiniaUtil.getSimpleClassName(firstStructure.getType());
        String firstGenerics = firstStructure.getGeneric();
        if (BlancoStringUtil.null2Blank(firstGenerics).length() > 0) {
            firstType = firstType + "<" + BlancoPiniaUtil.getSimpleClassName(firstGenerics) + ">";
        }
        if (firstStructure.getAsync()) {
            firstType = "Promise<" + firstType + ">";
        }
        argListText.add("): " + firstType + ";");
    }

    private boolean isGettersDefined(List<List<BlancoPiniaGettersStructure>> gettersStructureLists) {
        boolean isDefined = false;
        for (List<BlancoPiniaGettersStructure> gettersStructureList : gettersStructureLists) {
            if (gettersStructureList != null && gettersStructureList.size() > 0) {
                BlancoPiniaGettersStructure gettersStructure = gettersStructureList.get(0);
                if (BlancoStringUtil.null2Blank(gettersStructure.getName()).length() > 0) {
                    isDefined = true;
                }
            }
        }
        return isDefined;
    }

    private boolean isActionsDefined(List<List<BlancoPiniaActionsStructure>> actionsStructureLists) {
        boolean isDefined = false;
        for (List<BlancoPiniaActionsStructure> actionsStructureList : actionsStructureLists) {
            if (actionsStructureList != null && actionsStructureList.size() > 0) {
                BlancoPiniaActionsStructure actionsStructure = actionsStructureList.get(0);
                if (BlancoStringUtil.null2Blank(actionsStructure.getName()).length() > 0) {
                    isDefined = true;
                }
            }
        }
        return isDefined;
    }

    private String getTabSpace() {
        StringBuffer spc = new StringBuffer();
        for (int i = this.getTabs(); i > 0; i--) {
            spc.append(" ");
        }
        return spc.toString();
    }

    private String getLineSeparator() {
        return System.getProperty("line.separator", "\n");
    }

    private void addCommaToListString(List<String> list) {
        if (list.size() > 0) {
            String lastline = list.remove(list.size() - 1);
            list.add(lastline + ",");
        }
    }
}
