package setupFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class SetupNormal extends SetupFile{
   public SetupNormal(int instancesCount) throws IOException{
      this(instancesCount, FileNameConstants.KDD_TRAIN);
   }
   
   public SetupNormal(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"normal"});
   }
}