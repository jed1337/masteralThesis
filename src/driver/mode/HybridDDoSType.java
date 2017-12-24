package driver.mode;

import driver.mode.noiseLevel.NoNoise;
import java.io.IOException;
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessSlowBody;
import preprocessFiles.PreprocessSlowHeaders;
import preprocessFiles.PreprocessSlowRead;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;
import driver.categoricalType.CategoricalType;

public final class HybridDDoSType extends Mode{
   
   public HybridDDoSType(int totalInstancesCount, CategoricalType categoricalType) throws IOException {
      super(totalInstancesCount, NoNoise.getInstance(), categoricalType);

//TODO make this part in all of the other subclasses not repeaet code      
      super.pfL.add(new PreprocessTCPFlood());
      super.pfL.add(new PreprocessUDPFlood());
      super.pfL.add(new PreprocessHTTPFlood());
      
      super.pfL.add(new PreprocessSlowBody());
      super.pfL.add(new PreprocessSlowHeaders());
      super.pfL.add(new PreprocessSlowRead());
      
      categoricalType.setPreprocessFileCount(super.pfL, totalInstanceCount);
   }

   @Override
   public String getSystemType() {
      return "Hybrid DDoS Type";
   }
}