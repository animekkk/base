package pl.animekkk.base.database;

import pl.animekkk.base.Savable;

import java.util.Set;

public interface Database {

    void save(Savable... objects);

    <T> Set<T> load(Class<T> clazz);

    void remove(String identificator);

}
