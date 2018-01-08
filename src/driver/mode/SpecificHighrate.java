package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;
import driver.categoricalType.CategoricalType;

public final class SpecificHighrate extends SpecificAttack{
<<<<<<< HEAD
   public SpecificHighrate() throws IOException {
      super.pfBS.add(new PreprocessTCPFlood());
      super.pfBS.add(new PreprocessUDPFlood());
      super.pfBS.add(new PreprocessHTTPFlood());
=======
   public SpecificHighrate(int totalInstancesCount, NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      super(totalInstancesCount, nl, categoricalType);
      
      super.pfL.add(new PreprocessTCPFlood());
      super.pfL.add(new PreprocessUDPFlood());
      super.pfL.add(new PreprocessHTTPFlood());

      categoricalType.setPreprocessFileCount(super.pfL, totalInstancesCount);
>>>>>>> parent of f698a90... Before trying NetMate featuers
   }
      
   @Override
   public String getSystemType() {
      return "Specific Highrate";
   }
}