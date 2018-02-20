package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich parsedSandwich = new Sandwich();
        try {
            JSONObject sandwichJSON = new JSONObject(json);

            JSONObject name = sandwichJSON.getJSONObject("name");
            parsedSandwich.setMainName(name.getString("mainName"));
            JSONArray akaArray = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList();
            for(int i=0; i<akaArray.length(); i++){
                alsoKnownAs.add(akaArray.getString(i));
            }
            parsedSandwich.setAlsoKnownAs(alsoKnownAs);

            parsedSandwich.setPlaceOfOrigin(sandwichJSON.getString("placeOfOrigin"));
            parsedSandwich.setDescription(sandwichJSON.getString("description"));
            parsedSandwich.setImage(sandwichJSON.getString("image"));

            JSONArray ingredientsArray = sandwichJSON.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList();
            for(int i=0; i<ingredientsArray.length(); i++){
                ingredients.add(ingredientsArray.getString(i));
            }
            parsedSandwich.setIngredients(ingredients);
        } catch (JSONException e) {
            parsedSandwich = null;
            e.printStackTrace();
        }
        return parsedSandwich;
    }

}
