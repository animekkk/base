# anBase
Library, which is used for easily saving data into databases.
## How to save and load data?
#### Savable
Every object that you want to save must extends `Savable`.
It simply adds `getIdentificator` method for identifying them in
```java
@Data
public class User extends Savable {

    private final String name;
    private final int age;

    @Override
    public String getIdentificator() {
        return this.name;
    }
}
```

---
In example class below I use `save` function for saving `User` objects and `load` function for loading them.
```java
public class RedisDatabaseExample {

    private final JedisPooled jedisPooled = new JedisPooled();
    private final RedisDatabase redisDatabase = new RedisDatabase(jedisPooled);

    public void save(User... users) {
        this.redisDatabase.save("anbase.users", users);
    }

    public Set<User> load() {
        return this.redisDatabase.load("anbase.users", User.class);
    }

}
```

#### Databases
For now we support only `Redis`, but you can create your own database by using `Database` interface.
Here is the example of `RedisDatabase`:
```java
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
```
#### Serializers
Also for now we only support `GsonSerializer` which uses `Gson` to serialize/deserialize data, and again you can create your own serializer by using `Serializer` interface.
Here is the example of `GsonSerializer`:
```java
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
```
