package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackType;
import java.io.IOException;

public class PreprocessHTTPFlood extends PreprocessFile{
   public PreprocessHTTPFlood() throws IOException {
      super(
         FileNameConstants.CNIS_HTTP_FLOOD,
         GeneralAttackType.HIGH_RATE,
         "httpFlood"
      );
   }
}