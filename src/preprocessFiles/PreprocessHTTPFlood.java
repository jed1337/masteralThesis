package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
<<<<<<< HEAD
import globalParameters.GlobalFeatureExtraction;
=======
import globalClasses.GlobalFeatureExtraction;
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
import java.io.IOException;

public class PreprocessHTTPFlood extends PreprocessFile{
   public PreprocessHTTPFlood() throws IOException {
<<<<<<< HEAD
      super(
         GlobalFeatureExtraction.getInstance().getHTTPFloodPath(),
=======
      super(GlobalFeatureExtraction.getInstance().getHTTPFloodPath(),
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
         GeneralAttackTypeEnum.HIGH_RATE,
         SpecificAttackTypeEnum.HTTP_FLOOD
      );
   }
}