package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.PreprocessFileName;
import constants.SpecificAttackTypeEnum;
import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessSlowBody extends PreprocessFile {
   public PreprocessSlowBody() throws IOException{
      super(
         GlobalFeatureExtraction.getInstance().getSlowBodyPath(),
         GeneralAttackTypeEnum.LOW_RATE,
         SpecificAttackTypeEnum.SLOW_BODY,
         PreprocessFileName.SLOW_BODY
      );
   }
}