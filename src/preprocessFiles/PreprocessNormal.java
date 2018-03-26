package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public final class PreprocessNormal extends PreprocessFile{
   public PreprocessNormal() throws IOException {
      super(
         GlobalFeatureExtraction.getInstance().getNormalPath(),
         GeneralAttackTypeEnum.NORMAL,
         SpecificAttackTypeEnum.NORMAL
      );
   }
}