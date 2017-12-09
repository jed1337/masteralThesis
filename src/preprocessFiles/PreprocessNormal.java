package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public final class PreprocessNormal extends PreprocessFile{
   public PreprocessNormal() throws IOException {
      super(
         FileNameConstants.KDD_TRAIN, 
         AttackType.NORMAL, 
         new String[]{"normal"}
      );
   }
}