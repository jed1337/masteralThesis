package setupFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class SetupExtraNoise extends SetupFile{
   public SetupExtraNoise(int instancesCount) throws IOException {
      this(instancesCount, FileNameConstants.CNIS_EXTRA_NOISE);
   }
   
   public SetupExtraNoise(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"normal"});
   }
}