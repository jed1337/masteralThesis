package setupFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class SetupNoise extends SetupFile{
   public SetupNoise(int instancesCount) throws IOException {
      this(instancesCount, FileNameConstants.CNIS_NOISE);
   }
   
   public SetupNoise(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"normal"});
   }
}