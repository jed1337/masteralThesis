package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackType;
import java.io.IOException;

public class PreprocessSlowRead extends PreprocessFile {
   public PreprocessSlowRead() throws IOException{
      super(
         FileNameConstants.CNIS_SLOW_READ,
         GeneralAttackType.LOW_RATE,
         "slowRead"
      );
   }
}
