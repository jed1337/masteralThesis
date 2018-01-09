package preprocessFiles;

import constants.GeneralAttackType;
import constants.FileNameConstants;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public final class PreprocessNeptune extends PreprocessFile{
   public PreprocessNeptune() throws IOException {
      super(
         GlobalFeatureExtraction.getInstance().getKDDTrainPath(),
         GeneralAttackType.HIGH_RATE, 
         "neptune"
      );
   }
}