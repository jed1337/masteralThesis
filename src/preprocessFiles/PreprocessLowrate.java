package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class PreprocessLowrate extends PreprocessFile{
   public PreprocessLowrate() throws IOException {
      super(
         FileNameConstants.CNIS_LOWRATE, 
         AttackType.LOW_RATE, 
         new String[]{"slowBody", "slowHeaders", "slowRead"}
      );
   }
}