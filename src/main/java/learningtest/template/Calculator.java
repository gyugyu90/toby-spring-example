package learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filepath) throws IOException {
        LineCallback sumCallback = (line, value) -> value + Integer.valueOf(line);
        return lineReadTemplate(filepath, sumCallback, 0);
    }

    public Integer calcMultiply(String filepath) throws IOException {
        LineCallback multiplyCallback = (line, value) -> value * Integer.valueOf(line);
        return lineReadTemplate(filepath, multiplyCallback, 1);
    }

    public Integer lineReadTemplate(String filepath, LineCallback callback, int initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            Integer res = initVal;
            String line;
            while((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(br != null) {
                try { br.close(); } catch (IOException e) { e.printStackTrace(); }
            }
        }
    }

}
