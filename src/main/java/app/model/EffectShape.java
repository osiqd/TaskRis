/**
 * Class for applying effects to shapes
 */

package app.model;

import javafx.scene.effect.*;

import java.io.IOException;
import java.util.EnumMap;
import java.util.logging.*;

public class EffectShape {
    private static final Logger logger = Logger.getLogger(EffectShape.class.getName());
    private final EnumMap<EffectEnum, Effect> effectEnumMap = new EnumMap<>(EffectEnum.class);

    static {
        try {
            FileHandler fh = new FileHandler("logs/effect_shape.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public EffectShape() {
        logger.log(Level.INFO, "Creating EffectShape");

        effectEnumMap.put(EffectEnum.NONE, null);

        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setOffsetX(2.0f);
        innerShadow.setOffsetY(2.0f);
        effectEnumMap.put(EffectEnum.INNER_SHADOW, innerShadow);

        BoxBlur blur = new BoxBlur();
        blur.setWidth(5);
        blur.setHeight(5);
        blur.setIterations(3);
        effectEnumMap.put(EffectEnum.BLUR, blur);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4.0f);
        dropShadow.setOffsetY(4.0f);
        effectEnumMap.put(EffectEnum.DROP_SHADOW, dropShadow);

        logger.log(Level.INFO, "EffectShape initialized with " + effectEnumMap.size() + " effects");
    }

    public Effect getEffect(EffectEnum effectEnum) {
        Effect effect = effectEnumMap.get(effectEnum);
        logger.log(Level.FINE, "Getting effect: " + effectEnum);
        return effect;
    }
}