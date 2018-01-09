package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessHTTPFlood extends PreprocessFile{
   public PreprocessHTTPFlood() throws IOException {
      super(GlobalFeatureExtraction.getInstance().getHTTPFloodPath(),
         GeneralAttackTypeEnum.HIGH_RATE,
         "httpFlood"
      );
   }
}