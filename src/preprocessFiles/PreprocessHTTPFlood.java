package preprocessFiles;

import constants.GeneralAttackType;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessHTTPFlood extends PreprocessFile{
   public PreprocessHTTPFlood() throws IOException {
      super(
         GlobalFeatureExtraction.getInstance().getHTTPFloodPath(),
         GeneralAttackType.HIGH_RATE,
         "httpFlood"
      );
   }
}