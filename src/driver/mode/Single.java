package driver.mode;

import java.io.IOException;
import java.util.ArrayList;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessHighrate;
import preprocessFiles.PreprocessLowrate;
import preprocessFiles.PreprocessNoise;
import preprocessFiles.PreprocessNormal;

public final class Single extends Mode{
   public Single(int totalCount) {
      super(totalCount);
   }
   
   @Override
   public ArrayList<PreprocessFile> getPreprocessFiles() throws IOException{
      ArrayList<PreprocessFile> pfAL = new ArrayList<>();
      pfAL.add(new PreprocessNoise(super.totalCount / 6));
      pfAL.add(new PreprocessNormal(super.totalCount / 6));

      pfAL.add(new PreprocessHighrate(super.totalCount / 3));
      pfAL.add(new PreprocessLowrate(super.totalCount / 3));
      return pfAL;
   }

   @Override
   public String getReplacement() {
      return "tcpFlood:highrate, udpFlood:highrate, httpFlood:highrate, slowBody:lowrate, slowHeaders:lowrate, slowRead:lowrate";
   }
}