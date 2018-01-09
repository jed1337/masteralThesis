package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public final class PreprocessNormal extends PreprocessFile{
   public PreprocessNormal() throws IOException {
      super(GlobalFeatureExtraction.getInstance().getKDDTrainPath(),
         GeneralAttackTypeEnum.NORMAL,
         SpecificAttackTypeEnum.NORMAL
      );
   }
}