import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BarcodeToSgtin {
    JSONArray prefixList;

    public BarcodeToSgtin() throws IOException {
        String gcpJsonString = Files.readString( Paths.get(Paths.get("").toAbsolutePath() + "/converter/src/main/resources/GCPPrefixList.json"));
        JSONObject gcpJson = new JSONObject(gcpJsonString);
        prefixList = gcpJson.getJSONObject("GCPPrefixFormatList").getJSONArray("entry");
        System.out.println(prefixList.get(0));
    }
}
