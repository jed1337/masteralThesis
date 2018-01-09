package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.FileNameConstants;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public final class PreprocessNeptune extends PreprocessFile{
   public PreprocessNeptune() throws IOException {
      super(GlobalFeatureExtraction.getInstance().getKDDTrainPath(),
         GeneralAttackTypeEnum.HIGH_RATE, 
         "neptune"
      );
   }
}