package csvExport;

import java.io.*;
import java.util.List;

public class CsvExporter {

    private CsvExporter(){}

    public static void export(String outputFilePath, String fileName, List<? extends CsvExportable> csvExportableList)throws IOException{
        if(csvExportableList == null || csvExportableList.size() == 0) return;

        //ファイルを生成
        try (PrintWriter pw = createNewCsvFile(outputFilePath, fileName);){

            //ヘッダーを書き込み
            List<String> headerList = csvExportableList.get(0).getHeaderList();
            writeHeader(headerList, pw);

            //csvデータを書き込み
            writeCsvData(csvExportableList, pw);

        }catch (IOException e){
            throw e;
        }
    }

    private static void writeCsvData(List<? extends CsvExportable> csvExportableList, PrintWriter pw) {
        if(csvExportableList == null || csvExportableList.size() == 0) return;

        for (CsvExportable csvExportable : csvExportableList){
            StringBuilder sb = new StringBuilder();
            List<String> oneRecord = csvExportable.getOneRecord();
            if(oneRecord == null || oneRecord.size() == 0) return;
            for (int i = 0; i < oneRecord.size(); i++){
               sb.append(oneRecord.get(i));
               if(i != oneRecord.size() -1){
                   sb.append(",");
               }
            }
            pw.println(sb.toString());
        }
    }

    private static void writeHeader(List<String> headerList, PrintWriter pw) {
        if(headerList == null || headerList.size() == 0) return;

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < headerList.size(); i++){
            sb.append(headerList.get(i));
            if(i != headerList.size() -1){
                sb.append(",");
            }
        }
        pw.println(sb.toString());
    }

    private static PrintWriter createNewCsvFile(String outputFilePath, String fileName)throws IOException {
        File file = new File(outputFilePath);
        if(!file.exists()){
            file.mkdirs();
        }
        if (!fileName.substring(fileName.length()-4,fileName.length()).equals(".csv")){
            fileName = fileName + ".csv";
        }
        FileWriter fw = new FileWriter(file + "/" + fileName);
        return new PrintWriter(new BufferedWriter(fw));
    }
}
