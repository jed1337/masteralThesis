package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackTypeEnum;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessTCPFlood extends PreprocessFile {
   public PreprocessTCPFlood() throws IOException {
      super(GlobalFeatureExtraction.getInstance().getTCPFloodPath(),
         GeneralAttackTypeEnum.HIGH_RATE,
         "tcpFlood"
      );
   }
}
