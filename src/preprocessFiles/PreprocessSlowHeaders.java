package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackType;
import java.io.IOException;

public class PreprocessSlowHeaders extends PreprocessFile {
   public PreprocessSlowHeaders() throws IOException{
      super(
         FileNameConstants.CNIS_SLOW_HEADERS,
         GeneralAttackType.LOW_RATE,
         "slowHeaders"
      );
   }
}
