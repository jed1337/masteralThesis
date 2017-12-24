package driver.mode;

import driver.mode.noiseLevel.NoNoise;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessHighrate;
import preprocessFiles.PreprocessLowrate;
import driver.categoricalType.AttackType;

public final class HybridDDoSType extends Mode{
   
   public HybridDDoSType(int totalCount, NoiseLevel nl, AttackType categoricalType) throws IOException {
      super(totalCount, NoNoise.getInstance(), categoricalType);

//TODO make this part in all of the other subclasses not repeaet code      
      super.pfL.add(new PreprocessHighrate());
      super.pfL.add(new PreprocessLowrate());
      
      super.setPreprocessFileCount();
   }

//   @Override
//   public String getRelabel() {
//      return "tcpFlood:highrate, udpFlood:highrate, httpFlood:highrate, slowBody:lowrate, slowHeaders:lowrate, slowRead:lowrate";
//   }

   @Override
   public String getSystemType() {
      return "Hybrid DDoS Type";
   }
}