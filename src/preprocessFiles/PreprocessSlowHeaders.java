package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessSlowHeaders extends PreprocessFile {
   public PreprocessSlowHeaders() throws IOException{
      super(GlobalFeatureExtraction.getInstance().getSlowHeadersPath(),
         GeneralAttackTypeEnum.LOW_RATE,
         SpecificAttackTypeEnum.SLOW_HEADERS
      );
   }
}
