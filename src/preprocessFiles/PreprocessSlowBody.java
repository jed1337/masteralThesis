package preprocessFiles;

import constants.GeneralAttackType;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessSlowBody extends PreprocessFile {
   public PreprocessSlowBody() throws IOException{
      super(
         GlobalFeatureExtraction.getInstance().getSlowBodyPath(),
         GeneralAttackType.LOW_RATE,
         "slowBody"
      );
   }
}
