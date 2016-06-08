package com.lykkex.LykkeWallet.gui.utils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/**
 * Created by Nirmel on 6/3/2016.
 */
public class CustomJsonFloatDeserializer implements JsonDeserializer<Float> {
    public Float deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            return Float.parseFloat(json.getAsJsonPrimitive().getAsString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
