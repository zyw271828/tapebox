package com.github.tapebox;

import java.io.File;
import java.text.DecimalFormat;

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
        try {
            str = str.substring(0, str.length() - 1);
            String lastLine = str.substring(str.lastIndexOf("\n") + 1);
            String tx = lastLine.split(", ")[0];
            tx = tx.substring(tx.lastIndexOf(": ") + 2);

            return tx;
        } catch (Exception e) {
            throw new Exception("An error occurred while parsing the result.");
        }
    }

    static String getDuration(String str) throws Exception {
        try {
            str = str.substring(0, str.length() - 1);
            String lastLine = str.substring(str.lastIndexOf("\n") + 1);
            String duration = lastLine.split(", ")[1];
            duration = duration.substring(duration.lastIndexOf(": ") + 2);

            // Keep two decimal places
            duration = duration.substring(0, duration.length() - 1); // Remove 's'
            Double res = Double.parseDouble(duration);
            DecimalFormat df = new DecimalFormat("#.00");

            return df.format(res) + "s";
        } catch (Exception e) {
            throw new Exception("An error occurred while parsing the result.");
        }
    }

    static String getTps(String str) throws Exception {
        try {
            str = str.substring(0, str.length() - 1);
            String lastLine = str.substring(str.lastIndexOf("\n") + 1);
            String tps = lastLine.split(", ")[2];
            tps = tps.substring(tps.lastIndexOf(": ") + 2);

            // Keep two decimal places
            Double res = Double.parseDouble(tps);
            DecimalFormat df = new DecimalFormat("#.00");

            return df.format(res);
        } catch (Exception e) {
            throw new Exception("An error occurred while parsing the result.");
        }
    }
}
