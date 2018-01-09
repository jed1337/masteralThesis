package preprocessFiles;

import constants.GeneralAttackType;
import constants.FileNameConstants;
import java.io.IOException;

public final class PreprocessNoiseRandomWebsites extends PreprocessFile{
   public PreprocessNoiseRandomWebsites() throws IOException {
      super(
         FileNameConstants.NOISE_RANDOM_WEBSITES, 
         GeneralAttackType.NORMAL, 
         "normal"
      );
   }
}