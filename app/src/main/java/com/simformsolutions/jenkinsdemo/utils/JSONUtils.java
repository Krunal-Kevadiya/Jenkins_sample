package com.simformsolutions.jenkinsdemo.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.simformsolutions.jenkinsdemo.utils.Logger.makeLogTag;

public class JSONUtils
{
    private static final String TAG = makeLogTag(JSONUtils.class);

    public static Object toJSON(Object object) throws JSONException {
        if (object instanceof Map) {
            JSONObject json = new JSONObject();
            Map map = (Map) object;
            for (Object key : map.keySet()) {
                json.put(key.toString(), toJSON(map.get(key)));
            }
            return json;
        } else if (object instanceof Iterable) {
            JSONArray json = new JSONArray();
            for (Object value : ((Iterable)object)) {
                json.put(value);
            }
            return json;
        } else {
            return object;
        }
    }

    public static boolean isEmptyObject(JSONObject object) {
        return object.names() == null;
    }

    public static Map<String, Object> getMap(JSONObject object, String key, HashMap<String, Object> map) throws JSONException {
        return toMap(object.getJSONObject(key), map);
    }

    public static HashMap<String, Object> toMap(JSONObject object, HashMap<String, Object> map) throws JSONException {

        if( map == null )
            map = new HashMap();

        Iterator keys = object.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            map.put(key, fromJson(object.get(key), null));
        }
        return map;
    }

    public static List toList(JSONArray array) throws JSONException {
        ArrayList<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            list.add(fromJson(array.get(i), null));
        }
        return list;
    }

    private static Object fromJson(Object json, HashMap<String, Object> map) throws JSONException {
        if (json == JSONObject.NULL) {
            return null;
        } else if (json instanceof JSONObject) {
            return toMap((JSONObject) json, map);
        } else if (json instanceof JSONArray) {
            return toList((JSONArray) json);
        } else {
            return json;
        }
    }

    public static ArrayList<String> toStringArray(JSONArray jsonArr) {

        if (jsonArr == null || jsonArr.length() == 0) {
            return null;
        }

        ArrayList<String> stringArray = new ArrayList<String>();

        for (int i = 0, count = jsonArr.length(); i < count; i++) {
            try {
                String str = jsonArr.getString(i);
                stringArray.add(str);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return stringArray;
    }

    /**
     * Convert a given list of {@link String} into a {@link JSONArray}
     * **
     */
    public static JSONArray toJSONArray(ArrayList<String> stringArr) {
        JSONArray jsonArr = new JSONArray();

        for (int i = 0; i < stringArr.size(); i++) {
            String value = stringArr.get(i);
            jsonArr.put(value);
        }

        return jsonArr;
    }
}