package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackType;
import java.io.IOException;

public class PreprocessUDPFlood extends PreprocessFile {
   public PreprocessUDPFlood() throws IOException {
      super(
         FileNameConstants.CNIS_UDP_FLOOD,
         GeneralAttackType.HIGH_RATE,
         "udpFlood"
      );
   }
}
