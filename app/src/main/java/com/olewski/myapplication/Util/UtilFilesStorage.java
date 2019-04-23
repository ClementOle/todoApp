package com.olewski.myapplication.Util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UtilFilesStorage {


    public static void createFile(Context context) {
        try {
            boolean isFileExist = false;
            for (String fileName : context.fileList()) {
                if (fileName.equals("dataTodo.json")) {
                    isFileExist = true;
                    break;
                }
            }
            if (!isFileExist) {
                FileOutputStream outputStream = context.openFileOutput("dataTodo.json", Context.MODE_PRIVATE);
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FileInputStream openFileToRead(Context context) {
        try {
            return context.openFileInput("dataTodo.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FileOutputStream openFileToWrite(Context context) {
        try {
            return context.openFileOutput("dataTodo.json", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void closeFileToRead(FileInputStream file) {
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeFileToWrite(FileOutputStream file) {
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONArray readDataToJson(Context context) {
        try {
            FileInputStream file = openFileToRead(context);
            StringBuilder data = new StringBuilder();
            if (file != null) {
                int bytes;
                while ((bytes = file.read()) != -1)
                    data.append((char) bytes);

                closeFileToRead(file);
            }
            if (!data.toString().equals("")) {
                return new JSONArray(new JSONTokener(data.toString()));
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean writeDataInJson(Context context, JSONObject jsonObject) {
        try {
            JSONArray jsonArray = readDataToJson(context);
            if (jsonArray != null && jsonObject != null) {
                jsonArray.put(jsonObject);

                FileOutputStream file = openFileToWrite(context);
                if (file != null) {
                    file.write(jsonArray.toString().getBytes());
                    closeFileToWrite(file);
                    return true;
                }
            } else if (jsonObject != null) {
                FileOutputStream file = openFileToWrite(context);
                if (file != null) {
                    JSONArray jsonArray1 = new JSONArray("[]");
                    jsonArray1.put(jsonObject);
                    file.write(jsonArray1.toString().getBytes());
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public static boolean removeDataInJson(Context context, JSONObject jsonObject) {
        try {
            JSONArray jsonArray = readDataToJson(context);
            if (jsonArray != null && jsonObject != null) {
                int idFound = -1;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    if(jsonObject1.has("id") && jsonObject1.has("text") && jsonObject1.has("isDone")
                        && jsonObject.has("id") && jsonObject.has("text") && jsonObject.has("isDone"))
                        if (jsonObject1.getInt("id") == jsonObject.getInt("id")
                        && jsonObject1.getString("text").equals(jsonObject.get("text")) &&
                        jsonObject1.getBoolean("isDone") == jsonObject.getBoolean("isDone")) {
                            idFound = i;
                            break;
                        }
                }
                if (idFound != -1) {
                    jsonArray.remove(idFound);
                }

                FileOutputStream file = openFileToWrite(context);
                if (file != null) {
                    file.write(jsonArray.toString().getBytes());
                    closeFileToWrite(file);
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
