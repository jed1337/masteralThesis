package driver.mode;

import driver.categoricalType.HybridStageIsAttack;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessNormal;
import preprocessFiles.PreprocessSlowBody;
import preprocessFiles.PreprocessSlowHeaders;
import preprocessFiles.PreprocessSlowRead;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;

public final class HybridIsAttack extends SystemType{
   public HybridIsAttack(NoiseLevel nl) throws IOException {
      super(nl, new HybridStageIsAttack());
      
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
      return "Hybrid isAttack";
   }
}