package preprocessFiles;

import constants.GeneralAttackType;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public final class PreprocessNormal extends PreprocessFile{
   public PreprocessNormal() throws IOException {
      super(
         GlobalFeatureExtraction.getInstance().getKDDTrainPath(),
         GeneralAttackType.NORMAL,
         "normal"
      );
   }
}