package com.olewski.myapplication.Util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UtilFilesStorage {


    /**
     * Créer un fichier si il n'existe pas
     *
     * @param fileName Nom du fichier
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
     * Ouvre le fichier spécifier en paramètre si il existe
     *
     * @param fileName Nom du fichier
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
     * Ouvre le fichier spécifier en paramètre si il existe
     *
     * @param fileName Nom du fichier
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
     * Ferme le fichier
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
     * Ferme le fichier
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
     * Lie le fichier si il existe
     *
     * @param fileName Nom du fichier à lire
     * @param context
     * @return
     */
    public static JSONArray readDataToJson(Context context, String fileName) {
        try {
            //Ouverture du fichier
            FileInputStream file = openFileToRead(context, fileName);
            StringBuilder data = new StringBuilder();
            List<Byte> byteArray = new ArrayList<>();
            if (file != null) {
                int bytes;
                //Récupération de chaque byte du fichier
                while ((bytes = file.read()) != -1)
                    byteArray.add((byte) bytes);

                //Conversion de la liste de bytes en tableau de byte
                byte[] a = new byte[byteArray.size()];
                for (int i = 0; i < byteArray.size(); i++) {
                    a[i] = byteArray.get(i);
                }
                //Conversion du tableau de byte en utf-8
                data.append(new String(a, StandardCharsets.UTF_8));
                //Fermeture du fichier
                closeFileToRead(file);
            }
            if (!data.toString().equals("")) {
                System.out.println(data.toString());
                return new JSONArray(new JSONTokener(data.toString()));
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Si le fichier spécifié existe : ajout de l'objet à la position spécifié ou à la fin du tableau si ce n'est pas spécifié
     * Sinon initialise le fichier
     *
     * @param fileName     nom du fichier
     * @param indexInArray position dans le tableau de donnée
     * @param context
     * @param jsonObject   Objet à insérer
     * @return
     */
    public static boolean writeDataInJson(Context context, JSONObject jsonObject, Integer indexInArray, String fileName) {
        try {
            //Récupération du tableau de données
            JSONArray jsonArray = readDataToJson(context, fileName);
            if (jsonArray != null && jsonObject != null) {
                //Si le tableau existe et si l'object à insérer n'est pas null
                if (indexInArray != null) {
                    //Si un index est spécifié on insert l'élements à la position voulue
                    for (int i = jsonArray.length(); i > indexInArray; i--) {
                        jsonArray.put(i, jsonArray.get(i - 1));
                    }
                    jsonArray.put(indexInArray, jsonObject);
                } else {
                    //Sinon on l'insert à la fin du tableau
                    jsonArray.put(jsonObject);
                }
                //On ouvre le fichier
                FileOutputStream file = openFileToWrite(context, fileName);
                if (file != null) {
                    //Ecriture du fichier en UTF-8
                    file.write(jsonArray.toString().getBytes(StandardCharsets.UTF_8));
                    closeFileToWrite(file);
                    return true;
                }
            } else if (jsonObject != null) {
                //Si le tableau n'existe pas on ouvre le fichier
                FileOutputStream file = openFileToWrite(context, fileName);

                if (file != null && fileName.equals("dataList.json")) {
                    //Si le fichier dataList existe existe
                    JSONArray jsonArray1 = new JSONArray("[]");
                    //On créer un nouveau tableau et on insère l'objet et on ajoute la liste default qui possède un id 0
                    JSONObject jsonDefaultList = new JSONObject();
                    jsonDefaultList.put("id", 0);
                    jsonDefaultList.put("name", "Default");
                    jsonArray1.put(jsonDefaultList);
                    jsonArray1.put(jsonObject);
                    //Ecriture du fichier
                    file.write(jsonArray1.toString().getBytes(StandardCharsets.UTF_8));
                    return true;
                } else if (file != null) {
                    //Création du tableau vide
                    JSONArray jsonArray1 = new JSONArray("[]");
                    jsonArray1.put(jsonObject);
                    //Ecriture du fichier
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
     * Supprime l'objet du tableau de donnée si il existe
     *
     * @param fileName   Nom du fichier
     * @param context
     * @param jsonObject
     * @return
     */
    public static boolean removeDataInJson(Context context, JSONObject jsonObject, String fileName) {
        try {
            JSONArray jsonArray = readDataToJson(context, fileName);
            if (jsonArray != null && jsonObject != null) {
                int idFound = getIdOfObject(jsonArray, jsonObject, fileName);
                if (idFound != -1)
                    jsonArray.remove(idFound);

                FileOutputStream file = openFileToWrite(context, fileName);
                if (file != null) {
                    file.write(jsonArray.toString().getBytes(StandardCharsets.UTF_8));
                    closeFileToWrite(file);
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Modification des donnée
     *
     * @param context
     * @param jsonObject Nom de l'objet à modifier
     * @param fileName   Nom du fichier
     */

    public static void modifyDataInJson(Context context, JSONObject jsonObject, String fileName) {
        try {
            //Récupération du tableau de données
            JSONArray jsonArray = readDataToJson(context, fileName);
            if (jsonArray != null && jsonObject != null) {
                //Sauvegarde de la position de l'object dans le tableau
                int idFound = getIdOfObject(jsonArray, jsonObject, fileName);
                //Si l'object existe bien dans le tableau
                if (idFound != -1) {
                    //Récupération de l'object à modifier
                    JSONObject objectFound = jsonArray.getJSONObject(idFound);

                    //Suppression de l'ancien objet
                    removeDataInJson(context, objectFound, fileName);

                    //Inversion de la valeur de isDone
                    boolean isDone = jsonObject.getBoolean("isDone");
                    jsonObject.remove("isDone");
                    jsonObject.put("isDone", !isDone);

                    //Réécriture de l'objet dans le fichier
                    writeDataInJson(context, jsonObject, idFound, fileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère la position de l'objet dans le tableau de données
     *
     * @param jsonArray         tableau de données
     * @param jsonObjectToFound objet à trouver
     * @param fileName          nom du fichier
     * @return
     */
    private static int getIdOfObject(JSONArray jsonArray, JSONObject jsonObjectToFound, String fileName) {
        try {
            int idFound = -1;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectTrait = null;

                jsonObjectTrait = jsonArray.getJSONObject(i);

                if (jsonObjectTrait.has("id") && jsonObjectTrait.has("text") && jsonObjectTrait.has("isDone")
                        && jsonObjectToFound.has("id") && jsonObjectToFound.has("text") && jsonObjectToFound.has("isDone")) {
                    if (jsonObjectTrait.getDouble("id") == jsonObjectToFound.getDouble("id")
                            && jsonObjectTrait.getString("text").equals(jsonObjectToFound.get("text")) &&
                            jsonObjectTrait.getBoolean("isDone") == jsonObjectToFound.getBoolean("isDone")) {
                        return i;
                    }
                } else if (jsonObjectTrait.has("id") && jsonObjectTrait.has("name")
                        && jsonObjectToFound.has("id") && jsonObjectToFound.has("name")) {
                    if (jsonObjectTrait.getInt("id") == jsonObjectToFound.getInt("id")
                            && jsonObjectTrait.getString("name").equals(jsonObjectToFound.getString("name"))) {
                        return i;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

}
