package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackTypeEnum;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessSlowRead extends PreprocessFile {
   public PreprocessSlowRead() throws IOException{
      super(GlobalFeatureExtraction.getInstance().getSlowReadPath(),
         GeneralAttackTypeEnum.LOW_RATE,
         "slowRead"
      );
   }
}
