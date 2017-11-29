package setupFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class SetupNeptune extends SetupFile{
   public SetupNeptune(int instancesCount) throws IOException{
      this(instancesCount, FileNameConstants.KDD_TRAIN);
   }
   
   public SetupNeptune(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"neptune"});
   }
}