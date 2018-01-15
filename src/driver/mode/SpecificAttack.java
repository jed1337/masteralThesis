package driver.mode;

import java.io.IOException;
import driver.categoricalType.SpecificAttackType;
import driver.mode.noiseLevel.NoNoise;

public abstract class SpecificAttack extends Mode{
   public SpecificAttack() throws IOException {
      super(NoNoise.getInstance(), new SpecificAttackType());
   }
}