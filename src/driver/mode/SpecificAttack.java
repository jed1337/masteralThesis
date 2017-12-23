package driver.mode;

import driver.categoricalType.CategoricalType;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;

public abstract class SpecificAttack extends Mode{
   public SpecificAttack(int totalCount, NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      super(totalCount, nl, categoricalType);
   }
   
//   @Override
//   public final String getRelabel() {
//      return "";
//   }
}