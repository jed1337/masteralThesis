package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessLowrate;

public final class SpecificLowrate extends SpecificAttack{
   public SpecificLowrate(int totalCount, NoiseLevel nl) throws IOException {
      super(totalCount, nl);
      
      super.pfAL.add(new PreprocessLowrate());

      super.setPreprocessFileCount();
   }
}