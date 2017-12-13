package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public final class PreprocessLowrate extends PreprocessFile{
   public PreprocessLowrate() throws IOException {
      super(
         FileNameConstants.CNIS_LOWRATE, 
         GeneralAttackType.LOW_RATE, 
         new String[]{"slowBody", "slowHeaders", "slowRead"}
      );
   }
}