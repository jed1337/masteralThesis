package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import driver.categoricalType.CategoricalType;

public abstract class SpecificAttack extends Mode{
   public SpecificAttack(int totalCount, NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      super(totalCount, nl, categoricalType);
   }
}