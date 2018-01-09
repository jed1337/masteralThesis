package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackType;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessSlowHeaders extends PreprocessFile {
   public PreprocessSlowHeaders() throws IOException{
      super(
         GlobalFeatureExtraction.getInstance().getSlowHeadersPath(),
         GeneralAttackType.LOW_RATE,
         "slowHeaders"
      );
   }
}
