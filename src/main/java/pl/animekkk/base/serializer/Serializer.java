package pl.animekkk.base.serializer;

public interface Serializer {

    String serialize(Object object);

    <T> T deserialize(String object, Class<T> clazz);

}
