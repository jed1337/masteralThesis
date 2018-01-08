package driver.mode;

import java.io.IOException;
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessSlowBody;
import preprocessFiles.PreprocessSlowHeaders;
import preprocessFiles.PreprocessSlowRead;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;
import driver.categoricalType.CategoricalType;
import driver.mode.noiseLevel.NoNoise;

public final class HybridDDoSType extends SystemType{
   public HybridDDoSType(CategoricalType categoricalType) throws IOException {
      super(NoNoise.getInstance(), categoricalType);
      
      super.pfL.add(new PreprocessTCPFlood());
      super.pfL.add(new PreprocessUDPFlood());
      super.pfL.add(new PreprocessHTTPFlood());
      
      super.pfL.add(new PreprocessSlowBody());
      super.pfL.add(new PreprocessSlowHeaders());
      super.pfL.add(new PreprocessSlowRead());
   }

   @Override
   public String getSystemType() {
      return "Hybrid DDoS Type";
   }
}