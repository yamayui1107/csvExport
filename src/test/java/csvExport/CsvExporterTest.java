package csvExport;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class CsvExporterTest {

    private static List<CsvExportable> csvExportableList = new ArrayList<>();
    private static TestCsvExportable testCsvExportable1 = new TestCsvExportable();
    private static TestCsvExportable testCsvExportable2 = new TestCsvExportable();
    private static TestCsvExportable testCsvExportable3 = new TestCsvExportable();
    private static String fileName = "test";

    @BeforeAll
    static void setup(){
        System.out.println("テスト開始");
        testCsvExportable1.setOneRecord(Arrays.asList("testCol1","testCol2","testCol3"));
        testCsvExportable2.setOneRecord(Arrays.asList("testCol4","testCol5","testCol6"));
        testCsvExportable3.setOneRecord(Arrays.asList("testCol7","testCol8","testCol9"));
        csvExportableList.add(testCsvExportable1);
        csvExportableList.add(testCsvExportable2);
        csvExportableList.add(testCsvExportable3);
    }
    @AfterAll
    static void end(){
        System.out.println("テスト終了");
    }

    @Test
    public void ファイルパスファイル名出力形式を指定してファイル出力を行う(){
        boolean expected = true;
        boolean actual = false;

        try {
            CsvExporter.export(".",fileName,csvExportableList);
        }catch (IOException e){
            e.printStackTrace();
            actual = false;
        }
        //カレントディレクトリの取得
        String current = Paths.get("").toAbsolutePath().toString() + "/" + fileName + ".csv";

        //ファイル読み込み
        File file = new File(current);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            while (line != null){
                List<String> list =  Arrays.asList(line.split(","));
                System.out.println(list);
                line = br.readLine();
            }

            //ファイル削除
            Files.delete(Paths.get(current));
            actual = true;
        }catch (IOException e){
            actual = false;
        }
        Assertions.assertEquals(expected,actual);
    }

    private static class TestCsvExportable implements CsvExportable{
        private List<String> headerList = Arrays.asList("test1","test2","test3");
        private List<String> oneRecord = Arrays.asList("testColumn1","testColumn2","testColumn3");

        private void setHeaderList(List<String> headerList){
            this.headerList = headerList;
        }

        private void setOneRecord(List<String> oneRecord) {
            this.oneRecord = oneRecord;
        }

        @Override
        public List<String> getHeaderList() {
            return headerList;
        }
        @Override
        public List<String> getOneRecord() {
            return oneRecord;
        }
    }

}