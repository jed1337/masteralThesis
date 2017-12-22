package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessHighrate;
import preprocessFiles.PreprocessLowrate;
import preprocessFiles.PreprocessNormal;

public final class HybridIsAttack extends Mode{
   public HybridIsAttack(int totalCount, NoiseLevel nl) throws IOException {
      super(totalCount, nl);

      super.pfAL.add(new PreprocessHighrate());
      super.pfAL.add(new PreprocessLowrate());
      super.pfAL.add(new PreprocessNormal());
      
      super.setPreprocessFileCount();
   }
   
   @Override
   public String getReplacement() {
      return "tcpFlood:attack, udpFlood:attack, httpFlood:attack, slowBody:attack, slowHeaders:attack, slowRead:attack";
   }
   
   @Override
   public String getSystemType() {
      return "Hybrid isAttack";
   }
}