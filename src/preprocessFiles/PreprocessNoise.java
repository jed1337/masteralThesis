package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class PreprocessNoise extends PreprocessFile{
   public PreprocessNoise(int instancesCount) throws IOException {
      this(instancesCount, FileNameConstants.CNIS_NOISE);
   }
   
   public PreprocessNoise(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"normal"});
   }
}