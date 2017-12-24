package preprocessFiles;

import constants.GeneralAttackType;
import constants.FileNameConstants;
import java.io.IOException;

public final class PreprocessNeptune extends PreprocessFile{
   public PreprocessNeptune() throws IOException {
      super(
         FileNameConstants.KDD_TRAIN, 
         GeneralAttackType.HIGH_RATE, 
         "neptune"
      );
   }
}