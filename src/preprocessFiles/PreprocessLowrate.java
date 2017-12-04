package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class PreprocessLowrate extends PreprocessFile{
   public PreprocessLowrate(int instancesCount) throws IOException{
      this(instancesCount, FileNameConstants.CNIS_LOWRATE);
   }

   public PreprocessLowrate(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"slowBody", "slowHeaders", "slowRead"});
   }
}