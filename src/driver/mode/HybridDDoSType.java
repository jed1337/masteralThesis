package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessHighrate;
import preprocessFiles.PreprocessLowrate;

public final class HybridDDoSType extends Mode{
   public HybridDDoSType(int totalCount, NoiseLevel nl) throws IOException {
      super(totalCount, nl);

//TODO make this part in all of the other subclasses not repeaet code      
      super.pfAL.add(new PreprocessHighrate());
      super.pfAL.add(new PreprocessLowrate());
      
      super.setPreprocessFileCount();
   }

   @Override
   public String getReplacement() {
      return "tcpFlood:highrate, udpFlood:highrate, httpFlood:highrate, slowBody:lowrate, slowHeaders:lowrate, slowRead:lowrate";
   }
}