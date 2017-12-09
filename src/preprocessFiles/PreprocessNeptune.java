package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public final class PreprocessNeptune extends PreprocessFile{
   public PreprocessNeptune() throws IOException {
      super(
         FileNameConstants.KDD_TRAIN, 
         AttackType.HIGH_RATE, 
         new String[]{"neptune"}
      );
   }
}