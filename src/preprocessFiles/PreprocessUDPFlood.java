package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackType;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessUDPFlood extends PreprocessFile {
   public PreprocessUDPFlood() throws IOException {
      super(
         GlobalFeatureExtraction.getInstance().getUDPFloodPath(),
         GeneralAttackType.HIGH_RATE,
         "udpFlood"
      );
   }
}
