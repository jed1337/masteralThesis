package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.PreprocessFileName;
import constants.SpecificAttackTypeEnum;
import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessSlowRead extends PreprocessFile {
   public PreprocessSlowRead() throws IOException{
      super(
         GlobalFeatureExtraction.getInstance().getSlowReadPath(),
         GeneralAttackTypeEnum.LOW_RATE,
         SpecificAttackTypeEnum.SLOW_READ,
         PreprocessFileName.SLOW_READ
      );
   }
}