package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import java.util.ArrayList;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessHighrate;
import preprocessFiles.PreprocessLowrate;

public class HybridIsAttack extends Mode{
   public HybridIsAttack(int totalCount, NoiseLevel nl) throws IOException {
      super(totalCount, nl);
   }
   
   @Override
   public ArrayList<PreprocessFile> getPreprocessFiles() throws IOException {
      super.pfAL.add(new PreprocessHighrate());
      super.pfAL.add(new PreprocessLowrate());
      
      super.setPreprocessFileCount();
      return super.pfAL;
   }

   @Override
   public String getReplacement() {
      return "tcpFlood:attack, udpFlood:attack, httpFlood:attack, slowBody:attack, slowHeaders:attack, slowRead:attack";
   }
}