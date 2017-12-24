package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import driver.categoricalType.AttackType;

public abstract class SpecificAttack extends Mode{
   public SpecificAttack(int totalCount, NoiseLevel nl, AttackType categoricalType) throws IOException {
      super(totalCount, nl, categoricalType);
   }
   
//   @Override
//   public final String getRelabel() {
//      return "";
//   }
}