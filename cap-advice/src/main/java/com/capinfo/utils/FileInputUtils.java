package com.capinfo.utils;

import com.google.gson.Gson;

public class FileInputUtils {


    public static void updateFile(String id, String filesDynCode, String fileUniqueCode) {
        //"cap_advice_deal"
        FileInputParams params = new FileInputParams();
        params.setBusiId(id);
        params.setFilesDynCode(filesDynCode);
        params.setFileUniqueCode(fileUniqueCode);
        Gson gson = new Gson();
        String str = gson.toJson(params);
        //POST /enclosure/fileInput/api/updateFile
        HttpUtils.sendPostJson("http://39.97.230.101:8001/enclosure/fileInput/api/updateFile", str);
    }

}
