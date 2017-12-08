package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public class PreprocessHighrate extends PreprocessFile{
   public PreprocessHighrate() throws IOException {
      super(
         FileNameConstants.CNIS_HIGHRATE, 
         AttackType.HIGH_RATE, 
         new String[]{"tcpFlood", "udpFlood", "httpFlood"}
      );
   }
}