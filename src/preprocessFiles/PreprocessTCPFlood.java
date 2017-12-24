package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackType;
import java.io.IOException;

public class PreprocessTCPFlood extends PreprocessFile {
   public PreprocessTCPFlood() throws IOException {
      super(
         FileNameConstants.CNIS_TCP_FLOOD,
         GeneralAttackType.HIGH_RATE,
         "tcpFlood"
      );
   }
}
