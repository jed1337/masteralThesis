package setupFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class SetupLowrate extends SetupFile{
   public SetupLowrate(int instancesCount) throws IOException{
      this(instancesCount, FileNameConstants.CNIS_LOWRATE);
   }

   public SetupLowrate(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"slowBody", "slowHeaders", "slowRead"});
   }
}