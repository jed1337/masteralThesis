package driver.mode;

import driver.categoricalType.CategoricalType;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessLowrate;

public final class SpecificLowrate extends SpecificAttack{
   public SpecificLowrate(int totalCount, NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      super(totalCount, nl, categoricalType);
      
      super.pfL.add(new PreprocessLowrate());

      super.setPreprocessFileCount();
   }
      
   @Override
   public String getSystemType() {
      return "Specific Highrate";
   }
}