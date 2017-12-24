package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackType;
import java.io.IOException;

public class PreprocessSlowBody extends PreprocessFile {
   public PreprocessSlowBody() throws IOException{
      super(
         FileNameConstants.CNIS_SLOW_BODY,
         GeneralAttackType.LOW_RATE,
         "slowBody"
      );
   }
}
