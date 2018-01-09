package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackTypeEnum;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessSlowHeaders extends PreprocessFile {
   public PreprocessSlowHeaders() throws IOException{
      super(GlobalFeatureExtraction.getInstance().getSlowHeadersPath(),
         GeneralAttackTypeEnum.LOW_RATE,
         "slowHeaders"
      );
   }
}
