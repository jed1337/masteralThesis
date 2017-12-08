package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import java.util.ArrayList;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessHighrate;
import preprocessFiles.PreprocessLowrate;

public class HybridDDoSType extends Mode{
   public HybridDDoSType(int totalCount, NoiseLevel nl) throws IOException {
      super(totalCount, nl);
   }
   
   @Override
   public ArrayList<PreprocessFile> getPreprocessFiles() throws IOException {
      super.pfAL.add(new PreprocessHighrate());
      super.pfAL.add(new PreprocessLowrate());
      
      super.setPreprocessFileCount();
//      pfAL.add(new PreprocessHighrate(super.totalCount / 4));
//      pfAL.add(new PreprocessLowrate(super.totalCount / 4));
      return super.pfAL;
   }

   @Override
   public String getReplacement() {
      return "tcpFlood:highrate, udpFlood:highrate, httpFlood:highrate, slowBody:lowrate, slowHeaders:lowrate, slowRead:lowrate";
   }
}