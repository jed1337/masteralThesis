package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class PreprocessHighrate extends PreprocessFile{
   public PreprocessHighrate(int instancesCount) throws IOException {
      this(instancesCount, FileNameConstants.CNIS_HIGHRATE);
   }

   public PreprocessHighrate(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"tcpFlood", "udpFlood", "httpFlood"});
   }
}