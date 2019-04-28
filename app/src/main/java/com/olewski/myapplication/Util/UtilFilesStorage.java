package com.olewski.myapplication.Util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UtilFilesStorage {


    /**
     * Create a dataTodo.json file if it not exist
     * @param context
     */
    public static void createFile(Context context, String fileName) {
        try {
            boolean isFileExist = false;
            for (String file : context.fileList()) {
                if (file.equals(fileName)) {
                    isFileExist = true;
                    break;
                }
            }
            if (!isFileExist) {
                FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param context
     * @return
     */
    public static FileInputStream openFileToRead(Context context, String fileName) {
        try {
            return context.openFileInput(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param context
     * @return
     */
    public static FileOutputStream openFileToWrite(Context context, String fileName) {
        try {
            return context.openFileOutput(fileName, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param file
     */
    public static void closeFileToRead(FileInputStream file) {
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param file
     */
    public static void closeFileToWrite(FileOutputStream file) {
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param context
     * @return
     */
    public static JSONArray readDataToJson(Context context, String fileName) {
        try {
            FileInputStream file = openFileToRead(context, fileName);
            StringBuilder data = new StringBuilder();

            if (file != null) {
                int bytes;
                while ((bytes = file.read()) != -1)
                    data.append(Character.toChars(bytes));

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

    /**
     *
     * @param context
     * @param jsonObject
     * @return
     */
    public static boolean writeDataInJson(Context context, JSONObject jsonObject, String fileName) {
        try {
            JSONArray jsonArray = readDataToJson(context, fileName);
            if (jsonArray != null && jsonObject != null) {
                jsonArray.put(jsonObject);

                FileOutputStream file = openFileToWrite(context, fileName);
                if (file != null) {

                    file.write(jsonArray.toString().getBytes(StandardCharsets.UTF_8));
                    closeFileToWrite(file);
                    return true;
                }
            } else if (jsonObject != null) {
                FileOutputStream file = openFileToWrite(context, fileName);

                if (file != null && fileName.equals("dataList.json")) {
                    JSONArray jsonArray1 = new JSONArray("[]");
                    JSONObject jsonDefaultList = new JSONObject();
                    jsonDefaultList.put("id", 0);
                    jsonDefaultList.put("name", "Default");
                    jsonArray1.put(jsonDefaultList);
                    jsonArray1.put(jsonObject);
                    file.write(jsonArray1.toString().getBytes(StandardCharsets.UTF_8));
                    return true;
                } else if (file != null) {
                    JSONArray jsonArray1 = new JSONArray("[]");
                    jsonArray1.put(jsonObject);
                    file.write(jsonArray1.toString().getBytes(StandardCharsets.UTF_8));
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     *
     * @param context
     * @param jsonObject
     * @return
     */
    public static boolean removeDataInJson(Context context, JSONObject jsonObject, String fileName) {
        try {
            JSONArray jsonArray = readDataToJson(context, fileName);
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

                FileOutputStream file = openFileToWrite(context, fileName);
                if (file != null) {
                    file.write(jsonArray.toString().getBytes("UTF-8"));
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
