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
    private final String path;
    private Serializer serializer = new GsonSerializer();

    @Override
    public void save(Savable... objects) {
        for(Savable object : objects) {
            jedis.hset(this.path, object.getIdentificator(), this.serializer.serialize(object));
        }
    }

    @Override
    public <T> Set<T> load(Class<T> clazz) {
        Set<T> objects = new HashSet<>();
        for(String value : this.jedis.hgetAll(this.path).values()) {
            objects.add(serializer.deserialize(value, clazz));
        }
        return objects;
    }

    @Override
    public void remove(String identificator) {
        this.jedis.hdel(this.path, identificator);
    }

}
