package driver.mode;

import java.io.IOException;
import java.util.ArrayList;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessHighrate;
import preprocessFiles.PreprocessLowrate;
import preprocessFiles.PreprocessNoise;
import preprocessFiles.PreprocessNormal;

public class HybridIsAttack extends Mode{
   public HybridIsAttack(int totalCount) {
      super(totalCount);
   }
   
   @Override
   public ArrayList<PreprocessFile> getPreprocessFiles() throws IOException {
      ArrayList<PreprocessFile> pfAL = new ArrayList<>();
      pfAL.add(new PreprocessNoise(super.totalCount / 4));
      pfAL.add(new PreprocessNormal(super.totalCount / 4));
      
      pfAL.add(new PreprocessHighrate(super.totalCount / 4));
      pfAL.add(new PreprocessLowrate(super.totalCount / 4));
      return pfAL;
   }

   @Override
   public String getReplacement() {
      return "tcpFlood:attack, udpFlood:attack, httpFlood:attack, slowBody:attack, slowHeaders:attack, slowRead:attack";
   }
}