package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public final class PreprocessExtraNoise extends PreprocessFile{
   public PreprocessExtraNoise() throws IOException {
      super(
         FileNameConstants.CNIS_EXTRA_NOISE, 
         AttackType.NORMAL, 
         new String[]{"normal"}
      );
   }
}