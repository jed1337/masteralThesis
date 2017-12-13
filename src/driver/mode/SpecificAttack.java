package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;

public abstract class SpecificAttack extends Mode{
   public SpecificAttack(int totalCount, NoiseLevel nl) throws IOException {
      super(totalCount, nl);
   }
   
   @Override
   public final String getReplacement() {
      return "";
   }
}