package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessHTTPFlood extends PreprocessFile{
   public PreprocessHTTPFlood() throws IOException {
      super(GlobalFeatureExtraction.getInstance().getHTTPFloodPath(),
         GeneralAttackTypeEnum.HIGH_RATE,
         SpecificAttackTypeEnum.HTTP_FLOOD
      );
   }
}