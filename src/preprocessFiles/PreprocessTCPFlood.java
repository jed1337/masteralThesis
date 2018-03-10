package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
<<<<<<< HEAD
import globalParameters.GlobalFeatureExtraction;
=======
import globalClasses.GlobalFeatureExtraction;
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
import java.io.IOException;

public class PreprocessTCPFlood extends PreprocessFile {
   public PreprocessTCPFlood() throws IOException {
<<<<<<< HEAD
      super(
         GlobalFeatureExtraction.getInstance().getTCPFloodPath(),
=======
      super(GlobalFeatureExtraction.getInstance().getTCPFloodPath(),
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
         GeneralAttackTypeEnum.HIGH_RATE,
         SpecificAttackTypeEnum.TCP_FLOOD
      );
   }
}