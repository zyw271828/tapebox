package com.github.tapebox;

import java.io.File;

public class Helper {
    static boolean isFileExist(String filePath) {
        File f = new File(filePath);

        if (f.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static String getTx(String str) throws Exception {
        // TODO: unimplemented method
        return "";
    }

    static String getDuration(String str) throws Exception {
        // TODO: unimplemented method
        return "";
    }

    static String getTps(String str) throws Exception {
        // TODO: unimplemented method
        return "";
    }
}
