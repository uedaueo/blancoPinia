/*
 * blanco Framework
 * Copyright (C) 2004-2020 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.pinia;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import blanco.pinia.task.BlancoPiniaProcessImpl;
import blanco.pinia.task.valueobject.BlancoPiniaProcessInput;
import blanco.valueobjectts.task.BlancoValueObjectTsProcessImpl;
import blanco.valueobjectts.task.valueobject.BlancoValueObjectTsProcessInput;

/**
 * Generation test for TypeScript.
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoPiniaTest {

    @Test
    public void testBlancoPinia() {
        System.out.println(System.getProperty("user.dir"));
        /* First, creates a ValueObject. */
        BlancoValueObjectTsProcessInput input = new BlancoValueObjectTsProcessInput();
        input.setMetadir("meta/objects");
        input.setEncoding("UTF-8");
        input.setSheetType("php");
        input.setTmpdir("tmpObjects");
        input.setTargetdir("sample/blanco");
        input.setTargetStyle("maven");
        input.setTabs(2);
        input.setVerbose(true);
        input.setLineSeparator("LF");
        input.setStrictNullable(true);

        BlancoValueObjectTsProcessImpl imple = new BlancoValueObjectTsProcessImpl();
        try {
            imple.execute(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Next, creates a pinia stores. */
        BlancoPiniaProcessInput inputStore = new BlancoPiniaProcessInput();
        inputStore.setMetadir("meta/stores");
        inputStore.setEncoding("UTF-8");
        inputStore.setSheetType("php");
        inputStore.setTmpdir("tmpTest");
        inputStore.setSearchTmpdir("tmpObjects");
        inputStore.setTargetdir("sample/blanco/main/typescript");
        inputStore.setTargetStyle("free");
        inputStore.setTabs(2);
        inputStore.setVerbose(true);
        inputStore.setStrictNullable(true);
        inputStore.setSupportedPiniaVersion(2);

        BlancoPiniaProcessImpl imple2 = new BlancoPiniaProcessImpl();
        try {
            imple2.execute(inputStore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
