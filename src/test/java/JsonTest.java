import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by fornia on 6/29/17.
 */
public class JsonTest {
    Map jsonMap2test = new HashMap();
    Gson gson;
    Gson adapterGson;
    @Before
    public void prepare(){
        jsonMap2test.put("a",1);
        jsonMap2test.put("b",0);
        jsonMap2test.put("c",1.1);
        jsonMap2test.put("d",1.0);
        gson = new Gson();
        adapterGson = new GsonBuilder().registerTypeAdapter(
                new TypeToken<TreeMap<String, Object>>(){}.getType(),
                new JsonDeserializer<TreeMap<String, Object>>() {
                    @Override
                    public TreeMap<String, Object> deserialize(
                            JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {

                        TreeMap<String, Object> treeMap = new TreeMap<>();
                        JsonObject jsonObject = json.getAsJsonObject();
                        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                        for (Map.Entry<String, JsonElement> entry : entrySet) {
                            Object ot = entry.getValue();
                            if(ot instanceof JsonPrimitive){
                                String s = ((JsonPrimitive) ot).getAsString();
                                if(s.contains("."))/*{

                                }
                                double d = ((JsonPrimitive) ot).getAsDouble();
                                int i = (int) d;
                                if(d>i)*/{
                                    treeMap.put(entry.getKey(),((JsonPrimitive) ot).getAsDouble());
                                }else {
                                    treeMap.put(entry.getKey(),((JsonPrimitive) ot).getAsInt());
                                }
                                //treeMap.put(entry.getKey(), ((JsonPrimitive) ot).getAsString());
                            }else{
                                treeMap.put(entry.getKey(), ot);
                            }
                        }
                        return treeMap;
                    }
                }).create();
    }

    @Test
    public void testGson(){

        //double a = 1.0;
        //System.out.println((int)a);

        System.out.println(adapterGson.fromJson("1",int.class));
        String genJson = gson.toJson(jsonMap2test);
        System.out.println(genJson);
        Map genMap = gson.fromJson(genJson,Map.class);
        System.out.println(gson.toJson(genMap));
        Map adapterGenMap = adapterGson.fromJson(genJson,new TypeToken<TreeMap<String,Object>>(){}.getType());
        System.out.println(adapterGenMap);

        gson.fromJson("{\"x\":1,\"y\":2}",Points.class);
    }
    class Points{
        int x;
        int y;

        @Override
        public String toString() {
            return "x:"+x+",y:"+y;
            //return super.toString();
        }
    }
    public static class MapDeserializerDoubleAsIntFix implements JsonDeserializer<Map<String, Object>>{

        @Override  @SuppressWarnings("unchecked")
        public Map<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return (Map<String, Object>) read(json);
        }

        public Object read(JsonElement in) {

            if(in.isJsonArray()){
                List<Object> list = new ArrayList<Object>();
                JsonArray arr = in.getAsJsonArray();
                for (JsonElement anArr : arr) {
                    list.add(read(anArr));
                }
                return list;
            }else if(in.isJsonObject()){
                Map<String, Object> map = new LinkedTreeMap<String, Object>();
                JsonObject obj = in.getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entitySet = obj.entrySet();
                for(Map.Entry<String, JsonElement> entry: entitySet){
                    map.put(entry.getKey(), read(entry.getValue()));
                }
                return map;
            }else if( in.isJsonPrimitive()){
                JsonPrimitive prim = in.getAsJsonPrimitive();
                if(prim.isBoolean()){
                    return prim.getAsBoolean();
                }else if(prim.isString()){
                    return prim.getAsString();
                }else if(prim.isNumber()){
                    Number num = prim.getAsNumber();
                    // here you can handle double int/long values
                    // and return any type you want
                    // this solution will transform 3.0 float to long values
                    if(Math.ceil(num.doubleValue())  == num.longValue())
                        return num.longValue();
                    else{
                        return num.doubleValue();
                    }
                }
            }
            return null;
        }
    }

}
