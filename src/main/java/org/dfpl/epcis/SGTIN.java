package org.dfpl.epcis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SGTIN {
    JSONArray entryList;
    String gtin;

    public SGTIN(String gtin) throws IOException {
        String gcpJsonString = Files.readString(Paths.get("D:/project/sgtin-converter/src/main/resources/GCPPrefixList.json"));
        JSONObject gcpJson = new JSONObject(gcpJsonString);
        entryList = gcpJson.getJSONObject("GCPPrefixFormatList").getJSONArray("entry");
        System.out.println("prefixList size = " + entryList.length());
        this.gtin = gtin;
    }

    public String formatSGTIN(String prefix, String indicator, String itemRef) {
        return String.format("urn:epc:id:sgtin:%s.%s%s.*", prefix, indicator, itemRef);
    }

    public String getSGTIN() {
        if (gtin.length() == 8) {
            return computeGTIN8(gtin);
        }
        String gtin14 = convertToGTIN14(gtin);
        Integer gcpLength = getGCPLength(gtin14);
        String prefix = gtin14.substring(1, gcpLength + 1);
        String indicator = gtin14.substring(0, 1);
        String itemRef = gtin14.substring(gcpLength + 1, 13);

        return formatSGTIN(prefix, indicator, itemRef);
    }

    public String computeGTIN8(String gtin) {
        String prefix = "0".repeat(5) + gtin.substring(0, 3);
        String indicator = "0";
        String itemRef = gtin.substring(3, 7);

        return formatSGTIN(prefix, indicator, itemRef);
    }

    public String convertToGTIN14(String gtin) {
        int padding = 14 - gtin.length();
        return "0".repeat(padding) + gtin;
    }

    public Integer getGCPLength(String gtin14) {
        for (int i = gtin14.length(); i > 0; i--) {
            String gtinSubString = gtin14.substring(1, i);
            for (Object val : entryList) {
                JSONObject entry = (JSONObject) val;
                if (entry.getString("prefix").equals(gtinSubString)) {
                    return entry.getInt("gcpLength");
                }
            }
        }
        return null;
    }
}
