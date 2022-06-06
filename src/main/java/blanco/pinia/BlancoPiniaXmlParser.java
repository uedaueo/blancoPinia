/*
 * blanco Framework
 * Copyright (C) 2004-2008 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.pinia;

import blanco.cg.BlancoCgSupportedLang;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;
import blanco.pinia.message.BlancoPiniaMessage;
import blanco.pinia.resourcebundle.BlancoPiniaResourceBundle;
import blanco.pinia.valueobject.BlancoPiniaActionsStructure;
import blanco.pinia.valueobject.BlancoPiniaClassStructure;
import blanco.pinia.valueobject.BlancoPiniaGettersStructure;
import blanco.pinia.valueobject.BlancoPiniaStateStructure;
import blanco.xml.bind.BlancoXmlBindingUtil;
import blanco.xml.bind.BlancoXmlUnmarshaller;
import blanco.xml.bind.valueobject.BlancoXmlAttribute;
import blanco.xml.bind.valueobject.BlancoXmlDocument;
import blanco.xml.bind.valueobject.BlancoXmlElement;

import java.io.File;
import java.util.*;

/**
 * A class that parses (reads and writes) the intermediate XML file format of blancoValueObject.
 *
 * @author IGA Tosiki
 */
public class BlancoPiniaXmlParser {
    /**
     * A message.
     */
    private final BlancoPiniaMessage fMsg = new BlancoPiniaMessage();

    private boolean fVerbose = false;

    public void setVerbose(boolean argVerbose) {
        this.fVerbose = argVerbose;
    }

    public boolean isVerbose() {
        return fVerbose;
    }

    /**
     * A resource bundle object for blancoValueObject.
     */
    private final static BlancoPiniaResourceBundle fBundle = new BlancoPiniaResourceBundle();

    public static Map<String, Integer> mapCommons = new HashMap<String, Integer>() {
        {
            put(fBundle.getMeta2xmlElementCommon(), BlancoCgSupportedLang.PHP);
        } // Supports PHP type sheets only.

        {
            put(fBundle.getMeta2xmlElementCommonCs(), BlancoCgSupportedLang.CS);
        }

        {
            put(fBundle.getMeta2xmlElementCommonJs(), BlancoCgSupportedLang.JS);
        }

        {
            put(fBundle.getMeta2xmlElementCommonVb(), BlancoCgSupportedLang.VB);
        }

        {
            put(fBundle.getMeta2xmlElementCommonPhp(), BlancoCgSupportedLang.PHP);
        }

        {
            put(fBundle.getMeta2xmlElementCommonRuby(), BlancoCgSupportedLang.RUBY);
        }

        {
            put(fBundle.getMeta2xmlElementCommonPython(), BlancoCgSupportedLang.PYTHON);
        }

        {
            put(fBundle.getMeta2xmlElementCommonKt(), BlancoCgSupportedLang.KOTLIN);
        }

        {
            put(fBundle.getMeta2xmlElementCommonTs(), BlancoCgSupportedLang.TS);
        }
    };

    /**
     * Parses an XML document in an intermediate XML file to get an array of information.
     *
     * @param argMetaXmlSourceFile An intermediate XML file.
     * @return An array of information obtained as a result of parsing.
     */
    public BlancoPiniaClassStructure[] parse(
            final File argMetaXmlSourceFile) {
        final BlancoXmlDocument documentMeta = new BlancoXmlUnmarshaller()
                .unmarshal(argMetaXmlSourceFile);
        if (documentMeta == null) {
            return null;
        }

        return parse(documentMeta);

    }

    /**
     * Parses an XML document in an intermediate XML file to get an array of value object information.
     *
     * @param argXmlDocument XML document of an intermediate XML file.
     * @return An array of value object information obtained as a result of parsing.
     */
    public BlancoPiniaClassStructure[] parse(
            final BlancoXmlDocument argXmlDocument) {
        final List<BlancoPiniaClassStructure> listStructure = new ArrayList<BlancoPiniaClassStructure>();

        // Gets the root element.
        final BlancoXmlElement elementRoot = BlancoXmlBindingUtil
                .getDocumentElement(argXmlDocument);
        if (elementRoot == null) {
            // The process is aborted if there is no root element.
            return null;
        }

        // Gets a list of sheets (Excel sheets).
        final List<BlancoXmlElement> listSheet = BlancoXmlBindingUtil
                .getElementsByTagName(elementRoot, "sheet");

        final int sizeListSheet = listSheet.size();
        for (int index = 0; index < sizeListSheet; index++) {
            final BlancoXmlElement elementSheet = listSheet.get(index);

            /*
             * Supports sheets written for languages other than Java.
             */
            List<BlancoXmlElement> listCommon = null;
            int sheetLang = BlancoCgSupportedLang.JAVA;
            for (String common : mapCommons.keySet()) {
                listCommon = BlancoXmlBindingUtil
                        .getElementsByTagName(elementSheet,
                                common);
                if (listCommon.size() != 0) {
                    BlancoXmlAttribute attr = new BlancoXmlAttribute();
                    attr.setType("CDATA");
                    attr.setQName("style");
                    attr.setLocalName("style");

                    sheetLang = mapCommons.get(common);
                    attr.setValue(new BlancoCgSupportedLang().convertToString(sheetLang));

                    elementSheet.getAtts().add(attr);

                    /* tueda DEBUG */
                    if (this.isVerbose()) {
                        System.out.println("/* tueda */ style = " + BlancoXmlBindingUtil.getAttribute(elementSheet, "style"));
                    }

                    break;
                }
            }

            if (listCommon == null || listCommon.size() == 0) {
                // Skips if there is no common.
                continue;
            }

            // Processes only the first item.
            final BlancoXmlElement elementCommon = listCommon.get(0);
            final String name = BlancoXmlBindingUtil.getTextContent(
                    elementCommon, "name");
            if (BlancoStringUtil.null2Blank(name).trim().length() == 0) {
                continue;
            }

            if (sheetLang != BlancoCgSupportedLang.PHP) {
                System.out.println("### ERROR Just PHP SheetLang is permitted.");
                throw new IllegalArgumentException("### ERROR: Invalid SheetLang : " + sheetLang);
            }

            BlancoPiniaClassStructure objClassStructure = parseElementSheetPhp(elementSheet);
            ;

            if (objClassStructure != null) {
                // Saves the obtained information.
                listStructure.add(objClassStructure);
            }
        }

        final BlancoPiniaClassStructure[] result = new BlancoPiniaClassStructure[listStructure
                .size()];
        listStructure.toArray(result);
        return result;
    }

    /**
     * Parses the "sheet" XML element (PHP format) in the intermediate XML file to get the value object information.
     *
     * @param argElementSheet "sheet" XML element in the intermediate XML file.
     * @return Value object information obtained as a result of parsing. Null is returned if "name" is not found.
     */
    public BlancoPiniaClassStructure parseElementSheetPhp(
            final BlancoXmlElement argElementSheet
    ) {

        final BlancoPiniaClassStructure objClassStructure = new BlancoPiniaClassStructure();
        /*
         * import named objects into defineComponent file with file names as key.
         */
        final Map<String, List<String>> namedExportedHeaderList = new HashMap<>();

        // Gets the common information.
        final BlancoXmlElement elementCommon = BlancoXmlBindingUtil
                .getElement(argElementSheet, fBundle
                        .getMeta2xmlElementCommon());
        if (elementCommon == null) {
            // Skips the processing of this sheet if there is no common.
            return null;
        }

        final String name = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "name");
        if (BlancoStringUtil.null2Blank(name).trim().length() == 0) {
            // Skips if name is empty.
            return null;
        }

        if (this.isVerbose()) {
            System.out.println("BlancoVueComponent#parseElementPhp name = " + name);
        }

        /*
         * Vue storeVue store definition common.
         */
        this.parseCommon(elementCommon, objClassStructure);

        /*
         * Implement directory
         */
        String storeId = objClassStructure.getName();
        String storeName = BlancoNameAdjuster.toParameterName(storeId);
        String impleDir = objClassStructure.getImpledir() + "/" + BlancoPiniaUtil.targetSubdir + "/" + storeId;
        String stateFileName = storeId + "State";
        String stateName = storeName + "State";
        String gettersFileName = storeId + "Getters";
        String gettersName = storeName + "Getters";
        String actionsFileName = storeId + "Actions";
        String actionsName = storeName + "Actions";

        /*
         * Add imports to defineStore file.
         * No user defined list for store.
         */
        BlancoPiniaUtil.addImportHeaderList("defineStore", "pinia", namedExportedHeaderList);
        BlancoPiniaUtil.addImportHeaderList(stateName, "./" + stateFileName, namedExportedHeaderList);
        BlancoPiniaUtil.addImportHeaderList(gettersName, impleDir + "/" +gettersFileName, namedExportedHeaderList);
        BlancoPiniaUtil.addImportHeaderList(actionsName, impleDir + "/" + actionsFileName, namedExportedHeaderList);

        /*
         * Parse State file informations
         */
        namedExportedHeaderList.clear();
        final List<BlancoXmlElement> stateList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlElementListState());
        if (stateList != null && stateList.size() != 0) {
            final BlancoXmlElement elementListRoot = stateList.get(0);
            /* share function with emits */
            this.parseState(elementListRoot, namedExportedHeaderList, objClassStructure);
        }

        /*
         * Get State headers user defined.
         */
        List<BlancoXmlElement> stateHeaderElementList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        fBundle.getMeta2xmlElementHeaderState());
        List<String> stateHeaderList = this.parseHeaderList(stateHeaderElementList, namedExportedHeaderList, null);
        if (stateHeaderList != null && stateHeaderList.size() > 0) {
            objClassStructure.getStateHeaderList().addAll(stateHeaderList);
        }

        /*
         * Parse Getters file informations
         */
        namedExportedHeaderList.clear();
        final List<BlancoXmlElement> gettersList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlElementListGetters());
        for (BlancoXmlElement element : gettersList) {
            List<BlancoPiniaGettersStructure> list = this.parseGetters(element, namedExportedHeaderList, objClassStructure);
            if (list.size() != 0) {
                objClassStructure.getGettersList().add(list);
            }
        }

        if (objClassStructure.getGettersList().size() != 0) {
            /*
             * Add imports to defineGetters file.
             */
            BlancoPiniaUtil.addImportHeaderList("StateTree", "pinia", namedExportedHeaderList);
            BlancoPiniaUtil.addImportHeaderList(stateFileName, "./" + stateFileName, namedExportedHeaderList);

            // Get headers user defined.
            List<BlancoXmlElement> gettersHeaderElementList = BlancoXmlBindingUtil
                    .getElementsByTagName(argElementSheet,
                            fBundle.getMeta2xmlElementHeaderGetters());
            List<String> gettersHeaderList = this.parseHeaderList(gettersHeaderElementList, namedExportedHeaderList, null);
            objClassStructure.getGettersHeaderList().addAll(gettersHeaderList);
        }

        /*
         * Parse Actions file informations
         */
        namedExportedHeaderList.clear();
        final List<BlancoXmlElement> actionsList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlElementListActions());
        for (BlancoXmlElement element : actionsList) {
            List<BlancoPiniaActionsStructure> list = this.parseActions(element, namedExportedHeaderList, objClassStructure);
            if (list.size() != 0) {
                objClassStructure.getActionsList().add(list);
            }
        }

        if (objClassStructure.getActionsList().size() != 0) {
            /*
             * Add imports to defineActions file.
             */
            BlancoPiniaUtil.addImportHeaderList("UnwrapRef", "vue", namedExportedHeaderList);
            BlancoPiniaUtil.addImportHeaderList(stateFileName, "./" + stateFileName, namedExportedHeaderList);

            // Get headers user defined.
            List<BlancoXmlElement> actionsHeaderElementList = BlancoXmlBindingUtil
                    .getElementsByTagName(argElementSheet,
                            fBundle.getMeta2xmlElementHeaderActions());
            List<String> actionsHeaderList = this.parseHeaderList(actionsHeaderElementList, namedExportedHeaderList, null);
            objClassStructure.getActionsHeaderList().addAll(actionsHeaderList);
        }

        return objClassStructure;
    }

    /**
     * Parses the intermediate XML file to get "Vue store definition common".
     *
     * @param argElementCommon
     * @param argObjClassStructure
     */
    private void parseCommon(
            final BlancoXmlElement argElementCommon,
            final BlancoPiniaClassStructure argObjClassStructure
    ) {
        argObjClassStructure.setName(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "name"));
        argObjClassStructure.setPackage(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "package"));
        argObjClassStructure.setBasedir(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "basedir"));
        argObjClassStructure.setImpledir(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "impledir"));

        argObjClassStructure.setDescription(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "description"));
        if (BlancoStringUtil.null2Blank(argObjClassStructure.getDescription())
                .length() > 0) {
            final String[] lines = BlancoNameUtil.splitString(argObjClassStructure
                    .getDescription(), '\n');
            for (int index = 0; index < lines.length; index++) {
                if (index == 0) {
                    argObjClassStructure.setDescription(lines[index]);
                } else {
                    // For a multi-line description, it will be split and stored.
                    // From the second line, assumes that character reference encoding has been properly implemented.
                    argObjClassStructure.getDescriptionList().add(lines[index]);
                }
            }
        }

        argObjClassStructure.setAdjustFieldName("true".equals(BlancoXmlBindingUtil
                .getTextContent(argElementCommon, "adjustFieldName")));
        argObjClassStructure.setAdjustDefaultValue("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "adjustDefaultValue")));
        argObjClassStructure.setCreateImportList("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "createImportList")));
        argObjClassStructure
                .setStateList(new ArrayList<>());
        argObjClassStructure
                .setGettersList(new ArrayList<>());
        argObjClassStructure
                .setActionsList(new ArrayList<>());
    }

    private void parseState(
            final BlancoXmlElement argElementProperties,
            final Map<String, List<String>> argHeaderList,
            final BlancoPiniaClassStructure argObjClassStructure
    ) {
        if (isVerbose()) {
            System.out.println("### parseState ###");
        }
        final List<BlancoXmlElement> listChildNodes = BlancoXmlBindingUtil
                .getElementsByTagName(argElementProperties, "list");
        for (int index = 0; index < listChildNodes.size(); index++) {
            final BlancoXmlElement elementList = listChildNodes.get(index);
            final BlancoPiniaStateStructure stateStructure = new BlancoPiniaStateStructure();

            stateStructure.setNo(BlancoXmlBindingUtil.getTextContent(
                    elementList, "no"));
            stateStructure.setName(BlancoXmlBindingUtil.getTextContent(
                    elementList, "name"));
            if (stateStructure.getName() == null
                    || stateStructure.getName().trim().length() == 0) {
                continue;
            }

            /*
             * Gets the type. Changes the type name to TypeScript style.
             */
            String phpType = BlancoXmlBindingUtil.getTextContent(elementList, "type");
            String targetType = BlancoPiniaUtil.convertPhpType2Ts(phpType, argObjClassStructure, argHeaderList, false);
            stateStructure.setType(targetType);

            /* Supports Generic. */
            String phpGeneric = BlancoXmlBindingUtil.getTextContent(elementList, "generic");
            if (BlancoStringUtil.null2Blank(phpGeneric).length() != 0) {
                String targetGeneric = "";
                try {
                    targetGeneric = BlancoPiniaUtil.convertPhpType2Ts(phpGeneric, argObjClassStructure, argHeaderList, true);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(fMsg.getMbvoji06(
                            argObjClassStructure.getName(),
                            stateStructure.getName(),
                            phpGeneric,
                            phpGeneric
                    ));
                }
                stateStructure.setGeneric(targetGeneric);
            }

            // Supports required.
            if ("true".equalsIgnoreCase(BlancoXmlBindingUtil.getTextContent(
                    elementList, "required"))) {
                stateStructure.setRequired(true);
            }

            // Supports Nullable.
            stateStructure.setNullable("true".equals(BlancoXmlBindingUtil
                    .getTextContent(elementList, "nullable")));

            // Supports description.
            stateStructure.setDescription(BlancoXmlBindingUtil
                    .getTextContent(elementList, "description"));
            final String[] lines = BlancoNameUtil.splitString(
                    stateStructure.getDescription(), '\n');
            for (int indexLine = 0; indexLine < lines.length; indexLine++) {
                if (indexLine == 0) {
                    stateStructure.setDescription(lines[indexLine]);
                } else {
                    // For a multi-line description, it will be split and stored.
                    // From the second line, assumes that character reference encoding has been properly implemented.
                    stateStructure.getDescriptionList().add(
                            lines[indexLine]);
                }
            }

            stateStructure.setDefault(BlancoXmlBindingUtil.getTextContent(
                    elementList, "default"));

            argObjClassStructure.getStateList().add(stateStructure);
        }
    }


    private List<BlancoPiniaGettersStructure> parseGetters(
            final BlancoXmlElement argElementProperties,
            final Map<String, List<String>> argHeaderList,
            final BlancoPiniaClassStructure argObjClassStructure
    ) {
        if (isVerbose()) {
            System.out.println("### parseGetters ###");
        }
        final List<BlancoXmlElement> listChildNodes = BlancoXmlBindingUtil
                .getElementsByTagName(argElementProperties, "list");
        final List<BlancoPiniaGettersStructure> list = new ArrayList<>();
        for (int index = 0; index < listChildNodes.size(); index++) {
            final BlancoXmlElement elementList = listChildNodes.get(index);
            final BlancoPiniaGettersStructure gettersStructure = new BlancoPiniaGettersStructure();

            gettersStructure.setNo(BlancoXmlBindingUtil.getTextContent(
                    elementList, "no"));
            gettersStructure.setName(BlancoXmlBindingUtil.getTextContent(
                    elementList, "name"));
            if (gettersStructure.getName() == null
                    || gettersStructure.getName().trim().length() == 0) {
                continue;
            }

            /*
             * Gets the type. Changes the type name to TypeScript style.
             */
            String phpType = BlancoXmlBindingUtil.getTextContent(elementList, "type");
            String targetType = BlancoPiniaUtil.convertPhpType2Ts(phpType, argObjClassStructure, argHeaderList, false);
            gettersStructure.setType(targetType);

            /* Supports Generic. */
            String phpGeneric = BlancoXmlBindingUtil.getTextContent(elementList, "generic");
            if (BlancoStringUtil.null2Blank(phpGeneric).length() != 0) {
                String targetGeneric = "";
                try {
                    targetGeneric = BlancoPiniaUtil.convertPhpType2Ts(phpGeneric, argObjClassStructure, argHeaderList, true);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(fMsg.getMbvoji06(
                            argObjClassStructure.getName(),
                            gettersStructure.getName(),
                            phpGeneric,
                            phpGeneric
                    ));
                }
                gettersStructure.setGeneric(targetGeneric);
            }

            // Supports required.
            if ("true".equalsIgnoreCase(BlancoXmlBindingUtil.getTextContent(
                    elementList, "required"))) {
                gettersStructure.setRequired(true);
            }

            // Supports Nullable.
            gettersStructure.setNullable("true".equals(BlancoXmlBindingUtil
                    .getTextContent(elementList, "nullable")));

            // Supports description.
            gettersStructure.setDescription(BlancoXmlBindingUtil
                    .getTextContent(elementList, "description"));
            final String[] lines = BlancoNameUtil.splitString(
                    gettersStructure.getDescription(), '\n');
            for (int indexLine = 0; indexLine < lines.length; indexLine++) {
                if (indexLine == 0) {
                    gettersStructure.setDescription(lines[indexLine]);
                } else {
                    // For a multi-line description, it will be split and stored.
                    // From the second line, assumes that character reference encoding has been properly implemented.
                    gettersStructure.getDescriptionList().add(
                            lines[indexLine]);
                }
            }

            gettersStructure.setDefault(BlancoXmlBindingUtil.getTextContent(
                    elementList, "default"));

            list.add(gettersStructure);
        }
        return list;
    }

    private List<BlancoPiniaActionsStructure> parseActions(
            final BlancoXmlElement argElementProperties,
            final Map<String, List<String>> argHeaderList,
            final BlancoPiniaClassStructure argObjClassStructure
    ) {
        if (isVerbose()) {
            System.out.println("### parseActions ###");
        }
        final List<BlancoXmlElement> listChildNodes = BlancoXmlBindingUtil
                .getElementsByTagName(argElementProperties, "list");
        List<BlancoPiniaActionsStructure> list = new ArrayList<>();
        for (int index = 0; index < listChildNodes.size(); index++) {
            final BlancoXmlElement elementList = listChildNodes.get(index);
            final BlancoPiniaActionsStructure actionsStructure = new BlancoPiniaActionsStructure();

            actionsStructure.setNo(BlancoXmlBindingUtil.getTextContent(
                    elementList, "no"));
            actionsStructure.setName(BlancoXmlBindingUtil.getTextContent(
                    elementList, "name"));
            if (actionsStructure.getName() == null
                    || actionsStructure.getName().trim().length() == 0) {
                continue;
            }

            /*
             * Gets the type. Changes the type name to TypeScript style.
             */
            String phpType = BlancoXmlBindingUtil.getTextContent(elementList, "type");
            String targetType = BlancoPiniaUtil.convertPhpType2Ts(phpType, argObjClassStructure, argHeaderList, false);
            actionsStructure.setType(targetType);

            /* Supports Generic. */
            String phpGeneric = BlancoXmlBindingUtil.getTextContent(elementList, "generic");
            if (BlancoStringUtil.null2Blank(phpGeneric).length() != 0) {
                String targetGeneric = "";
                try {
                    targetGeneric = BlancoPiniaUtil.convertPhpType2Ts(phpGeneric, argObjClassStructure, argHeaderList, true);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(fMsg.getMbvoji06(
                            argObjClassStructure.getName(),
                            actionsStructure.getName(),
                            phpGeneric,
                            phpGeneric
                    ));
                }
                actionsStructure.setGeneric(targetGeneric);
            }

            // Supports required.
            if ("true".equalsIgnoreCase(BlancoXmlBindingUtil.getTextContent(
                    elementList, "required"))) {
                actionsStructure.setRequired(true);
            }

            // Supports Async.
            actionsStructure.setAsync("true".equals(BlancoXmlBindingUtil
                    .getTextContent(elementList, "async")));

            // Supports Nullable.
            actionsStructure.setNullable("true".equals(BlancoXmlBindingUtil
                    .getTextContent(elementList, "nullable")));

            // Supports description.
            actionsStructure.setDescription(BlancoXmlBindingUtil
                    .getTextContent(elementList, "description"));
            final String[] lines = BlancoNameUtil.splitString(
                    actionsStructure.getDescription(), '\n');
            for (int indexLine = 0; indexLine < lines.length; indexLine++) {
                if (indexLine == 0) {
                    actionsStructure.setDescription(lines[indexLine]);
                } else {
                    // For a multi-line description, it will be split and stored.
                    // From the second line, assumes that character reference encoding has been properly implemented.
                    actionsStructure.getDescriptionList().add(
                            lines[indexLine]);
                }
            }

            actionsStructure.setDefault(BlancoXmlBindingUtil.getTextContent(
                    elementList, "default"));

            list.add(actionsStructure);
        }
        return list;
    }

    /**
     * Create headerList with auto-generated imports and user defined imports.
     *
     * @param argHeaderElementList
     * @param argImportHeaderList
     * @return
     */
    private List<String> parseHeaderList(
            final List<BlancoXmlElement> argHeaderElementList,
            final Map<String, List<String>> argImportHeaderList,
            final Map<String, List<String>> argDefaultExportedHeaderList
    ) {
        if (this.isVerbose()) {
            System.out.println("BlancoVueComponent#parseHeaderList: Start parseHeaderList.");
        }

        List<String> headerList = new ArrayList<>();

        /*
         * Creates a list of header.
         * First, outputs what is written in the definition document as is.
         */
        if (argHeaderElementList != null && argHeaderElementList.size() > 0) {
            final BlancoXmlElement elementHeaderRoot = argHeaderElementList.get(0);
            final List<BlancoXmlElement> listHeaderChildNodes = BlancoXmlBindingUtil
                    .getElementsByTagName(elementHeaderRoot, "header");
            for (int index = 0; index < listHeaderChildNodes.size(); index++) {
                final BlancoXmlElement elementList = listHeaderChildNodes
                        .get(index);

                final String headerName = BlancoXmlBindingUtil
                        .getTextContent(elementList, "name");
                if (this.isVerbose()) {
                    System.out.println("BlancoVueComponent#parseHeaderList header = " + headerName);
                }
                if (headerName == null || headerName.trim().length() == 0) {
                    continue;
                }
                headerList.add(
                        BlancoXmlBindingUtil
                                .getTextContent(elementList, "name"));
            }
        }

        /*
         * Next, outputs the auto-generated one.
         * The current method requires the following assumptions.
         *  * One class definition per file
         *  * Represents directories with Java/Kotlin style package notation in the definition sheet
         * TODO: Should it be possible to define the directory where the files are located on the definition sheet?
         */
        this.parseGeneratedHeaderList(headerList, argImportHeaderList, false);

        /*
         * Next, outputs the default exported one.
         * The current method requires the following assumptions.
         *  * One class definition per file
         *  * Represents directories with Java/Kotlin style package notation in the definition sheet
         * TODO: Should it be possible to define the directory where the files are located on the definition sheet?
         */
        if (argDefaultExportedHeaderList != null && argDefaultExportedHeaderList.size() > 0) {
            this.parseGeneratedHeaderList(headerList, argDefaultExportedHeaderList, true);
        }

        return headerList;
    }

    private void parseGeneratedHeaderList(
            List<String> argHeaderList,
            final Map<String, List<String>> argImportHeaderList,
            boolean isDefaultExported
    ) {
        if (argImportHeaderList != null && argImportHeaderList.size() > 0) {
            Set<String> fromList = argImportHeaderList.keySet();
            for (String strFrom : fromList) {
                StringBuffer sb = new StringBuffer();
                if (isDefaultExported) {
                    sb.append("import ");
                } else {
                    sb.append("import { ");
                }
                List<String> classNameList = argImportHeaderList.get(strFrom);
                int count = 0;
                for (String className : classNameList) {
                    if (count > 0) {
                        sb.append(", ");
                    }
                    sb.append(className);
                    count++;
                }
                if (count > 0) {
                    if (isDefaultExported) {
                        sb.append(" from \"" + strFrom + "\"");
                    } else {
                        sb.append(" } from \"" + strFrom + "\"");
                    }
                    if (this.isVerbose()) {
                        System.out.println("BlancoRestGeneratorTsXmlParser#parseGeneratedHeaderList import = " + sb.toString());
                    }
                    argHeaderList.add(sb.toString());
                }
            }
        }
    }
}
