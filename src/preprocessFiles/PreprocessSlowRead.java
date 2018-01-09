package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessSlowRead extends PreprocessFile {
   public PreprocessSlowRead() throws IOException{
      super(GlobalFeatureExtraction.getInstance().getSlowReadPath(),
         GeneralAttackTypeEnum.LOW_RATE,
         SpecificAttackTypeEnum.SLOW_READ
      );
   }
}