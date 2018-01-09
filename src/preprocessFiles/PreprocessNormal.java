package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public final class PreprocessNormal extends PreprocessFile{
   public PreprocessNormal() throws IOException {
      super(GlobalFeatureExtraction.getInstance().getKDDTrainPath(),
         GeneralAttackTypeEnum.NORMAL,
         "normal"
      );
   }
}