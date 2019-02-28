package game.components;

import com.almasb.fxgl.entity.component.Component;
import factories.EntityTypes;

/**
 * @author Daniel Bedrich
 */
public class TypeComponent extends Component {
    public final EntityTypes type;

    public TypeComponent(EntityTypes type) {
        this.type = type;
    }
}
