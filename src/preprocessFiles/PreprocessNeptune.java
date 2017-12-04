package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class PreprocessNeptune extends PreprocessFile{
   public PreprocessNeptune(int instancesCount) throws IOException{
      this(instancesCount, FileNameConstants.KDD_TRAIN);
   }
   
   public PreprocessNeptune(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"neptune"});
   }
}