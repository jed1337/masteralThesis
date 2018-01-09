package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackType;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessTCPFlood extends PreprocessFile {
   public PreprocessTCPFlood() throws IOException {
      super(
         GlobalFeatureExtraction.getInstance().getTCPFloodPath(),
         GeneralAttackType.HIGH_RATE,
         "tcpFlood"
      );
   }
}
