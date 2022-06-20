import pl.animekkk.base.database.RedisDatabase;
import pl.animekkk.base.serializer.GsonSerializer;
import redis.clients.jedis.JedisPooled;

import java.util.Set;
import java.util.UUID;

public class Test {

    public static void main(String[] args) {
        User user = new User("John", 18);
        gsonSerializerTest(user);
        redisDatabaseTest("anbase.test", new User(UUID.randomUUID().toString(), 15), new User(UUID.randomUUID().toString(), 17));
    }

    public static void gsonSerializerTest(User user) {
        System.out.println("--- GSON SERIALIZER TEST ---");
        GsonSerializer gsonSerializer = new GsonSerializer();
        String serialized = gsonSerializer.serialize(user);
        System.out.println(user);
        System.out.println(serialized);
        User deserialized = gsonSerializer.deserialize(serialized, User.class);
        System.out.println(deserialized);
        System.out.println("IS CORRECT? " + (user.equals(deserialized)));
        System.out.println("--- GSON SERIALIZER TEST ---");
    }

    public static void redisDatabaseTest(String path, User... users) {
        System.out.println("--- REDIS DATABASE TEST ---");
        System.out.println("PROVIED DATA:");
        for(User user : users) {
            System.out.println(user.getIdentificator());
        }
        JedisPooled jedis = new JedisPooled();
        RedisDatabase redisDatabase = new RedisDatabase(jedis);
        redisDatabase.save(path, users);
        Set<User> loadedUsers = redisDatabase.load(path, User.class);
        System.out.println("LOADED DATA:");
        loadedUsers.forEach(user -> System.out.println(user.getIdentificator()));
        System.out.println("--- REDIS DATABASE TEST ---");
    }

}
