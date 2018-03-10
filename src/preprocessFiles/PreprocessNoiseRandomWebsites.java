package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.FileNameConstants;
import constants.SpecificAttackTypeEnum;
import java.io.IOException;

public final class PreprocessNoiseRandomWebsites extends PreprocessFile{
   public PreprocessNoiseRandomWebsites() throws IOException {
      super(FileNameConstants.NOISE_RANDOM_WEBSITES, 
         GeneralAttackTypeEnum.NORMAL, 
         SpecificAttackTypeEnum.NORMAL
      );
   }
}