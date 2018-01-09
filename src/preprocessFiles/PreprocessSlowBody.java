package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import globalClasses.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessSlowBody extends PreprocessFile {
   public PreprocessSlowBody() throws IOException{
      super(GlobalFeatureExtraction.getInstance().getSlowBodyPath(),
         GeneralAttackTypeEnum.LOW_RATE,
         "slowBody"
      );
   }
}
