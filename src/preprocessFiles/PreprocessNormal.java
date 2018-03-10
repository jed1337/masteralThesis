package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
<<<<<<< HEAD
import globalParameters.GlobalFeatureExtraction;
=======
import globalClasses.GlobalFeatureExtraction;
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
import java.io.IOException;

public final class PreprocessNormal extends PreprocessFile{
   public PreprocessNormal() throws IOException {
<<<<<<< HEAD
      super(
         GlobalFeatureExtraction.getInstance().getNormalPath(),
=======
      super(GlobalFeatureExtraction.getInstance().getKDDTrainPath(),
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
         GeneralAttackTypeEnum.NORMAL,
         SpecificAttackTypeEnum.NORMAL
      );
   }
}