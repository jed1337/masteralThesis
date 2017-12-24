package preprocessFiles;

import constants.GeneralAttackType;
import constants.FileNameConstants;
import java.io.IOException;

public final class PreprocessNormal extends PreprocessFile{
   public PreprocessNormal() throws IOException {
      super(
         FileNameConstants.KDD_TRAIN,
         GeneralAttackType.NORMAL,
         "normal"
      );
   }
}