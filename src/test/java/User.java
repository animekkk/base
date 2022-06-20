import lombok.Data;
import pl.animekkk.base.Savable;

@Data
public class User implements Savable {

    private final String name;
    private final int age;


    @Override
    public String getIdentificator() {
        return this.name;
    }
}
