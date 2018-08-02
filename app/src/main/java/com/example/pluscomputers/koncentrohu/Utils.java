package com.example.pluscomputers.koncentrohu;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class Utils {

    private Utils() {
    }

    public static List<Photo> extractPhotos(JSONObject response) {

        List<Photo> listPhotos = new ArrayList<>();

        try {

            JSONObject rootObj = response.getJSONObject("photos");
            JSONArray photoArray = rootObj.getJSONArray("photo");


            for (int i = 0; i <= photoArray.length(); i++) {
                JSONObject photoObj = photoArray.getJSONObject(i);
                String id = photoObj.getString("id");
                String owner = photoObj.getString("owner");
                String secret = photoObj.getString("secret");
                String server = photoObj.getString("server");
                int farm = photoObj.getInt("farm");
                String title = photoObj.getString("title");

                String flickrImageUrl = "https://farm" + farm + ".staticflickr.com/" + server +
                        "/" + id + "_" + secret + ".jpg";

                Photo photo = new Photo(id, flickrImageUrl);

                listPhotos.add(photo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listPhotos;
    }

}

