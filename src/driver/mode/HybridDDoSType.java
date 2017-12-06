package driver.mode;

import java.io.IOException;
import java.util.ArrayList;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessHighrate;
import preprocessFiles.PreprocessLowrate;

public class HybridDDoSType extends Mode{
   public HybridDDoSType(int totalCount) {
      super(totalCount);
   }
   
   @Override
   public ArrayList<PreprocessFile> getPreprocessFiles() throws IOException {
      ArrayList<PreprocessFile> pfAL = new ArrayList<>();
      pfAL.add(new PreprocessHighrate(super.totalCount / 4));
      pfAL.add(new PreprocessLowrate(super.totalCount / 4));
      return pfAL;
   }

   @Override
   public String getReplacement() {
      return "tcpFlood:highrate, udpFlood:highrate, httpFlood:highrate, slowBody:lowrate, slowHeaders:lowrate, slowRead:lowrate";
   }
}