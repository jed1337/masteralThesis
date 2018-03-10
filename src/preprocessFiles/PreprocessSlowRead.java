package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
<<<<<<< HEAD
import globalParameters.GlobalFeatureExtraction;
=======
import globalClasses.GlobalFeatureExtraction;
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
import java.io.IOException;

public class PreprocessSlowRead extends PreprocessFile {
   public PreprocessSlowRead() throws IOException{
<<<<<<< HEAD
      super(
         GlobalFeatureExtraction.getInstance().getSlowReadPath(),
=======
      super(GlobalFeatureExtraction.getInstance().getSlowReadPath(),
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
         GeneralAttackTypeEnum.LOW_RATE,
         SpecificAttackTypeEnum.SLOW_READ
      );
   }
}