package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackTypeEnum;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessUDPFlood extends PreprocessFile {
   public PreprocessUDPFlood() throws IOException {
      super(GlobalFeatureExtraction.getInstance().getUDPFloodPath(),
         GeneralAttackTypeEnum.HIGH_RATE,
         "udpFlood"
      );
   }
}
