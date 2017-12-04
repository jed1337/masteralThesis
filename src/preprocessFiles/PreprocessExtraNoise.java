package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class PreprocessExtraNoise extends PreprocessFile{
   public PreprocessExtraNoise(int instancesCount) throws IOException {
      this(instancesCount, FileNameConstants.CNIS_EXTRA_NOISE);
   }
   
   public PreprocessExtraNoise(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"normal"});
   }
}