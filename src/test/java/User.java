import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.animekkk.base.Savable;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Savable {

    private final String name;
    private final int age;

    @Override
    public String getIdentificator() {
        return this.name;
    }
}
