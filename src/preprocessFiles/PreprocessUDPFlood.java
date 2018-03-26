package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessUDPFlood extends PreprocessFile {
   public PreprocessUDPFlood() throws IOException {
      super(
         GlobalFeatureExtraction.getInstance().getUDPFloodPath(),
         GeneralAttackTypeEnum.HIGH_RATE,
         SpecificAttackTypeEnum.UDP_FLOOD
      );
   }
}