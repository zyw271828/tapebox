package com.github.tapebox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TapeRunner {
    static String runTape(String filePath, String tx) throws Exception {
        String result = null;

        try {
            Process p = Runtime.getRuntime().exec(new String[] { "tape", "--config=" + filePath, "--number=" + tx });

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while ((result = stdInput.readLine()) != null) {
                stringBuilder.append(result);
                stringBuilder.append('\n');
            }
            result = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
