package directed_weighted_graph;

import com.google.gson.*;

import java.lang.reflect.Type;

public class InterfaceAdapter implements JsonSerializer, JsonDeserializer {

    private static final String CLASSNAME = "className";
    private static final String DATA = "DATA";

    private Class getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
    }

    @Override
    public JsonElement serialize(Object o, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, o.getClass().getName());
        jsonObject.add(DATA, jsonSerializationContext.serialize(o));
        return jsonObject;
    }

    @Override
    public Object deserialize(JsonElement jsonElement, Type type,
                              JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();
        Class klass = getObjectClass(className);
        return jsonDeserializationContext.deserialize(jsonObject.get(DATA), klass);
    }
}
