package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class PreprocessNoise extends PreprocessFile{
   public PreprocessNoise() throws IOException {
      super(
         FileNameConstants.CNIS_NOISE, 
         AttackType.NORMAL, 
         new String[]{"normal"}
      );
   }
}