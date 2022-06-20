package pl.animekkk.base.database;

import pl.animekkk.base.Savable;

import java.util.Set;

public interface Database {

    void save(String path, Savable... objects);

    <T> Set<T> load(String path, Class<T> clazz);

}
