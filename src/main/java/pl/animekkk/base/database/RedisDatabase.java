package pl.animekkk.base.database;

import lombok.Data;
import pl.animekkk.base.Savable;
import pl.animekkk.base.serializer.GsonSerializer;
import pl.animekkk.base.serializer.Serializer;
import redis.clients.jedis.JedisPooled;

import java.util.HashSet;
import java.util.Set;

@Data
public class RedisDatabase implements Database {

    private final JedisPooled jedis;
    private Serializer serializer = new GsonSerializer();

    @Override
    public void save(String path, Savable... objects) {
        for(Savable object : objects) {
            jedis.hset(path, object.getIdentificator(), this.serializer.serialize(object));
        }
    }

    @Override
    public <T> Set<T> load(String path, Class<T> clazz) {
        Set<T> objects = new HashSet<>();
        for(String value : this.jedis.hgetAll(path).values()) {
            objects.add(serializer.deserialize(value, clazz));
        }
        return objects;
    }
}
