package store.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {

    private static final String FILE_PATH = "src/main/resources/";

    public static List<String> readFile(String crewType) {
        File file = new File(FILE_PATH + crewType);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // 첫 번째 줄(헤더) 읽기
            br.readLine(); // 첫 번째 줄을 무시함
            // 나머지 라인 읽기
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(); // 예외 발생 시 빈 리스트 반환
        }
    }

}