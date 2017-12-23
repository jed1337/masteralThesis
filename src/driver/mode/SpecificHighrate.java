package driver.mode;

import driver.categoricalType.CategoricalType;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessHighrate;

public final class SpecificHighrate extends SpecificAttack{
   public SpecificHighrate(int totalCount, NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      super(totalCount, nl, categoricalType);
      
      super.pfL.add(new PreprocessHighrate());

      super.setPreprocessFileCount();
   }
      
   @Override
   public String getSystemType() {
      return "Specific Highrate";
   }
}