package csvExport;

import java.util.List;

public interface CsvExportable {
    List<String> getHeaderList();
    List<String> getOneRecord();
}
