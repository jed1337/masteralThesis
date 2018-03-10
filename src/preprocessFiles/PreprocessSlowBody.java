package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
<<<<<<< HEAD
import globalParameters.GlobalFeatureExtraction;
=======
import globalClasses.GlobalFeatureExtraction;
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
import java.io.IOException;

public class PreprocessSlowBody extends PreprocessFile {
   public PreprocessSlowBody() throws IOException{
<<<<<<< HEAD
      super(
         GlobalFeatureExtraction.getInstance().getSlowBodyPath(),
=======
      super(GlobalFeatureExtraction.getInstance().getSlowBodyPath(),
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
         GeneralAttackTypeEnum.LOW_RATE,
         SpecificAttackTypeEnum.SLOW_BODY
      );
   }
}