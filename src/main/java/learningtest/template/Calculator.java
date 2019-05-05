package learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filepath) throws IOException {

        BufferedReaderCallback sumCallback = br -> {
            Integer sum = 0;
            String line;
            while((line = br.readLine()) != null) {
                sum += Integer.valueOf(line);
            }
            return sum;
        };
        return fileReadTemplate(filepath, sumCallback);
    }

    public Integer calcMultiply(String filepath) throws IOException {
        BufferedReaderCallback multiplyCallback = br -> {
            Integer multiply = 1;
            String line;
            while((line = br.readLine()) != null) {
                multiply *= Integer.valueOf(line);
            }
            return multiply;
        };
        return fileReadTemplate(filepath, multiplyCallback);
    }

    private Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            // 콜백 오브젝트 호출 템플릿에서 만든 컨텍스트 정보인 BufferedReader를 전달해주고 콜백의 작업결과를 받아온다.
            return callback.doSomethingWithReader(br);
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
