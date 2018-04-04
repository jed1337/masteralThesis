package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.PreprocessFileName;
import constants.SpecificAttackTypeEnum;
import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessTCPFlood extends PreprocessFile {
   public PreprocessTCPFlood() throws IOException {
      super(
         GlobalFeatureExtraction.getInstance().getTCPFloodPath(),
         GeneralAttackTypeEnum.HIGH_RATE,
         SpecificAttackTypeEnum.TCP_FLOOD,
         PreprocessFileName.TCP_FLOOD
      );
   }
}