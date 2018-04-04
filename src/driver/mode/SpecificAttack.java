package driver.mode;

import driver.categoricalType.SpecificAttackType;
import driver.mode.noiseLevel.NoNoise;
import java.io.IOException;

public abstract class SpecificAttack extends Mode{
   public SpecificAttack() throws IOException {
      super(NoNoise.getInstance(), new SpecificAttackType());
   }
}