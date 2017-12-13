package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public final class PreprocessHighrate extends PreprocessFile{
   public PreprocessHighrate() throws IOException {
      super(
         FileNameConstants.CNIS_HIGHRATE, 
         GeneralAttackType.HIGH_RATE, 
         new String[]{"tcpFlood", "udpFlood", "httpFlood"}
      );
   }
}