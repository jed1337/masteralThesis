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

public final class HybridIsAttack extends Mode{
   public HybridIsAttack(int totalInstancesCount, NoiseLevel nl)
         throws IOException {
      super(totalInstancesCount, nl, new HybridStageIsAttack());

      super.pfL.add(new PreprocessNormal());
      
      super.pfL.add(new PreprocessTCPFlood());
      super.pfL.add(new PreprocessUDPFlood());
      super.pfL.add(new PreprocessHTTPFlood());
      
      super.pfL.add(new PreprocessSlowBody());
      super.pfL.add(new PreprocessSlowHeaders());
      super.pfL.add(new PreprocessSlowRead());
      
      new HybridStageIsAttack().setPreprocessFileCount(super.pfL, totalInstancesCount);
      System.out.println("");
   }
   
   @Override
   public String getSystemType() {
      return "Hybrid isAttack";
   }
}