package driver.mode;

import driver.categoricalType.*;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessNormal;
import preprocessFiles.PreprocessSlowBody;
import preprocessFiles.PreprocessSlowHeaders;
import preprocessFiles.PreprocessSlowRead;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;

public final class Single extends SystemType{
   public Single(NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      super(nl, categoricalType);
      
      super.pfBS.add(new PreprocessNormal());
      
      super.pfBS.add(new PreprocessTCPFlood());
      super.pfBS.add(new PreprocessUDPFlood());
      super.pfBS.add(new PreprocessHTTPFlood());
      
      super.pfBS.add(new PreprocessSlowBody());
      super.pfBS.add(new PreprocessSlowHeaders());
      super.pfBS.add(new PreprocessSlowRead());
   }

   @Override
   public String getSystemType() {
      return "Single";
   }
}