package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessHighrate;
import preprocessFiles.PreprocessLowrate;
import preprocessFiles.PreprocessNormal;

public final class Single extends Mode{
   public Single(int totalCount, NoiseLevel nl) throws IOException {
      super(totalCount, nl);
      
      super.pfAL.add(new PreprocessHighrate());
      super.pfAL.add(new PreprocessLowrate());
      super.pfAL.add(new PreprocessNormal());
      
      super.setPreprocessFileCount();
   }

   @Override
   public String getReplacement() {
      return "";
//      return "tcpFlood:highrate, udpFlood:highrate, httpFlood:highrate, slowBody:lowrate, slowHeaders:lowrate, slowRead:lowrate";
   }
}