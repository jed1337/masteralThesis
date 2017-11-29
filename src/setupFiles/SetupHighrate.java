package setupFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class SetupHighrate extends SetupFile{
   public SetupHighrate(int instancesCount) throws IOException {
      this(instancesCount, FileNameConstants.CNIS_HIGHRATE);
   }

   public SetupHighrate(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"tcpFlood", "udpFlood", "httpFlood"});
   }
}