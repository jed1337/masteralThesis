package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
<<<<<<< HEAD
import globalParameters.GlobalFeatureExtraction;
=======
import globalClasses.GlobalFeatureExtraction;
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
import java.io.IOException;

public final class PreprocessNeptune extends PreprocessFile{
   public PreprocessNeptune() throws IOException {
<<<<<<< HEAD
      super(
         GlobalFeatureExtraction.getInstance().getKDDTrainPath(),
=======
      super(GlobalFeatureExtraction.getInstance().getKDDTrainPath(),
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
         GeneralAttackTypeEnum.HIGH_RATE, 
         SpecificAttackTypeEnum.NEPTUNE
      );
   }
}