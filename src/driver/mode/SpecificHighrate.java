package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessHighrate;

public final class SpecificHighrate extends SpecificAttack{
   public SpecificHighrate(int totalCount, NoiseLevel nl) throws IOException {
      super(totalCount, nl);
      
      super.pfAL.add(new PreprocessHighrate());

      super.setPreprocessFileCount();
   }
      
   @Override
   public String getSystemType() {
      return "Specific Highrate";
   }
}