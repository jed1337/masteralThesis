package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class PreprocessNormal extends PreprocessFile{
   public PreprocessNormal(int instancesCount) throws IOException{
      this(instancesCount, FileNameConstants.KDD_TRAIN);
   }
   
   public PreprocessNormal(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"normal"});
   }
}