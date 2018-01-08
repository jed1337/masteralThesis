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
      
      super.pfBS.add(new PreprocessTCPFlood());
      super.pfBS.add(new PreprocessUDPFlood());
      super.pfBS.add(new PreprocessHTTPFlood());
      
      super.pfBS.add(new PreprocessSlowBody());
      super.pfBS.add(new PreprocessSlowHeaders());
      super.pfBS.add(new PreprocessSlowRead());
   }

   @Override
   public String getSystemType() {
      return "Hybrid DDoS Type";
   }
}