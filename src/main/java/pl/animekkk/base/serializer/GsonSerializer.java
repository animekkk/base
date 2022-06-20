package pl.animekkk.base.serializer;

import com.google.gson.Gson;
import lombok.Data;

@Data
public class GsonSerializer implements Serializer {

    private Gson gson = new Gson();

    @Override
    public String serialize(Object object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T deserialize(String object, Class<T> clazz) {
        return gson.fromJson(object, clazz);
    }

}
