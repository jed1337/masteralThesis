package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
<<<<<<< HEAD
import globalParameters.GlobalFeatureExtraction;
=======
import globalClasses.GlobalFeatureExtraction;
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
import java.io.IOException;

public class PreprocessUDPFlood extends PreprocessFile {
   public PreprocessUDPFlood() throws IOException {
<<<<<<< HEAD
      super(
         GlobalFeatureExtraction.getInstance().getUDPFloodPath(),
=======
      super(GlobalFeatureExtraction.getInstance().getUDPFloodPath(),
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
         GeneralAttackTypeEnum.HIGH_RATE,
         SpecificAttackTypeEnum.UDP_FLOOD
      );
   }
}