package driver.mode;

import driver.categoricalType.BinaryIsAttack;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessHighrate;
import preprocessFiles.PreprocessLowrate;
import preprocessFiles.PreprocessNormal;

public final class HybridIsAttack extends Mode{
   public HybridIsAttack(int totalCount, NoiseLevel nl) throws IOException {
      super(totalCount, nl, new  BinaryIsAttack());

      super.pfL.add(new PreprocessHighrate());
      super.pfL.add(new PreprocessLowrate());
      super.pfL.add(new PreprocessNormal());
      
      super.setPreprocessFileCount();
   }
   
//   @Override
//   public String getRelabel() {
//      return "tcpFlood:attack, udpFlood:attack, httpFlood:attack, slowBody:attack, slowHeaders:attack, slowRead:attack";
//   }
   
   @Override
   public String getSystemType() {
      return "Hybrid isAttack";
   }
}