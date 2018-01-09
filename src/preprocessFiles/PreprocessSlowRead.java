package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackType;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessSlowRead extends PreprocessFile {
   public PreprocessSlowRead() throws IOException{
      super(
         GlobalFeatureExtraction.getInstance().getSlowReadPath(),
         GeneralAttackType.LOW_RATE,
         "slowRead"
      );
   }
}
