package xyz.ufactions.customstaff.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utility {

    private static final Gson gson = new GsonBuilder().create();

    public static String serialize(Object o) {
        return gson.toJson(o);
    }

    public static <T> T deserialize(String serializedData, Class<T> type) {
        return gson.fromJson(serializedData, type);
    }
}