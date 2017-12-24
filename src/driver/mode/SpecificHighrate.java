package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;
import driver.categoricalType.CategoricalType;

public final class SpecificHighrate extends SpecificAttack{
   public SpecificHighrate(int totalInstancesCount, NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      super(totalInstancesCount, nl, categoricalType);
      
      super.pfL.add(new PreprocessTCPFlood());
      super.pfL.add(new PreprocessUDPFlood());
      super.pfL.add(new PreprocessHTTPFlood());

      categoricalType.setPreprocessFileCount(super.pfL, totalInstancesCount);
   }
      
   @Override
   public String getSystemType() {
      return "Specific Highrate";
   }
}