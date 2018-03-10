package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
<<<<<<< HEAD
import globalParameters.GlobalFeatureExtraction;
=======
import globalClasses.GlobalFeatureExtraction;
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
import java.io.IOException;

public class PreprocessSlowHeaders extends PreprocessFile {
   public PreprocessSlowHeaders() throws IOException{
<<<<<<< HEAD
      super(
         GlobalFeatureExtraction.getInstance().getSlowHeadersPath(),
=======
      super(GlobalFeatureExtraction.getInstance().getSlowHeadersPath(),
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
         GeneralAttackTypeEnum.LOW_RATE,
         SpecificAttackTypeEnum.SLOW_HEADERS
      );
   }
}