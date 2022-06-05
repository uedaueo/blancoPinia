/*
 * blanco Framework
 * Copyright (C) 2004-2008 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.pinia.task;

import blanco.cg.BlancoCgSupportedLang;
import blanco.pinia.*;
import blanco.pinia.message.BlancoPiniaMessage;
import blanco.pinia.task.valueobject.BlancoPiniaProcessInput;
import blanco.pinia.valueobject.BlancoPiniaClassStructure;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlancoPiniaProcessImpl implements BlancoPiniaProcess {

    /**
     * A message.
     */
    private final BlancoPiniaMessage fMsg = new BlancoPiniaMessage();

    /**
     * {@inheritDoc}
     */
    public int execute(final BlancoPiniaProcessInput input)
            throws IOException, IllegalArgumentException {
        System.out.println("- " + BlancoPiniaConstants.PRODUCT_NAME
                + " (" + BlancoPiniaConstants.VERSION + ")");
        try {
            final File fileMetadir = new File(input.getMetadir());
            if (fileMetadir.exists() == false) {
                throw new IllegalArgumentException(fMsg.getMbvoja01(input
                        .getMetadir()));
            }

            /*
             * Determines the newline code.
             */
            String LF = "\n";
            String CR = "\r";
            String CRLF = CR + LF;
            String lineSeparatorMark = input.getLineSeparator();
            String lineSeparator = "";
            if ("LF".equals(lineSeparatorMark)) {
                lineSeparator = LF;
            } else if ("CR".equals(lineSeparatorMark)) {
                lineSeparator = CR;
            } else if ("CRLF".equals(lineSeparatorMark)) {
                lineSeparator = CRLF;
            }
            if (lineSeparator.length() != 0) {
                System.setProperty("line.separator", lineSeparator);
                if (input.getVerbose()) {
                    System.out.println("lineSeparator try to change to " + lineSeparatorMark);
                    String newProp = System.getProperty("line.separator");
                    String newMark = "other";
                    if (LF.equals(newProp)) {
                        newMark = "LF";
                    } else if (CR.equals(newProp)) {
                        newMark = "CR";
                    } else if (CRLF.equals(newProp)) {
                        newMark = "CRLF";
                    }
                    System.out.println("New System Props = " + newMark);
                }
            }

            /*
             * Processes targetdir and targetStyle.
             * Sets the storage location for the generated code.
             * targetstyle = blanco:
             *  Creates a main directory under targetdir.
             * targetstyle = maven:
             *  Creates a main/java directory under targetdir.
             * targetstyle = free:
             *  Creates a directory using targetdir as is.
             *  However, if targetdir is empty, the default string (blanco) is used.
             * by tueda, 2019/08/30
             */
            String strTarget = input.getTargetdir();
            String style = input.getTargetStyle();
            // Always true when passing through here.
            boolean isTargetStyleAdvanced = true;
            if (style != null && BlancoPiniaConstants.TARGET_STYLE_MAVEN.equals(style)) {
                strTarget = strTarget + "/" + BlancoPiniaConstants.TARGET_DIR_SUFFIX_MAVEN;
            } else if (style == null ||
                    !BlancoPiniaConstants.TARGET_STYLE_FREE.equals(style)) {
                strTarget = strTarget + "/" + BlancoPiniaConstants.TARGET_DIR_SUFFIX_BLANCO;
            }
            /* If style is free, uses targetdir as is. */
            if (input.getVerbose()) {
                System.out.println("/* tueda */ TARGETDIR = " + strTarget);
            }

            // Creates a temporary directory.
            new File(input.getTmpdir()
                    + BlancoPiniaConstants.TARGET_SUBDIRECTORY).mkdirs();

            new BlancoPiniaMeta2Xml().processDirectory(fileMetadir, input
                    .getTmpdir()
                    + BlancoPiniaConstants.TARGET_SUBDIRECTORY);

            // Generates ValueObject from XML-ized meta file.
            // Scans the temporary folder first.
            final File[] fileMeta2 = new File(input.getTmpdir()
                    + BlancoPiniaConstants.TARGET_SUBDIRECTORY)
                    .listFiles();

        /*
         * First, searches all the sheets and makes a list of structures from the class names.
         * The reason is that in the PHP-style definitions, the package name is not specified when specifying a class.
         */
            BlancoPiniaUtil.isVerbose = input.getVerbose();
            BlancoPiniaUtil.processValueObjects(input);

            BlancoPiniaUtil.targetSubdir = input.getTargetSubdir();

            // Next, scans the directory specified as the meta directory.
            for (int index = 0; index < fileMeta2.length; index++) {
                if (fileMeta2[index].getName().endsWith(".xml") == false) {
                    continue;
                }

                final BlancoPiniaXml2TypeScriptClass xml2Class = new BlancoPiniaXml2TypeScriptClass();
                xml2Class.setEncoding(input.getEncoding());
                xml2Class.setVerbose(input.getVerbose());
                xml2Class.setTargetStyleAdvanced(isTargetStyleAdvanced);
                xml2Class.setXmlRootElement(input.getXmlrootelement());
                xml2Class.setSheetLang(new BlancoCgSupportedLang().convertToInt(input.getSheetType()));
                xml2Class.setTabs(input.getTabs());
                BlancoPiniaClassStructure[] structures = xml2Class.process(fileMeta2[index], new File(strTarget));
            }

            return BlancoPiniaBatchProcess.END_SUCCESS;
        } catch (TransformerException e) {
            throw new IOException("An exception has occurred during the XML conversion process: " + e.toString());
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean progress(final String argProgressMessage) {
        System.out.println(argProgressMessage);
        return false;
    }
}
