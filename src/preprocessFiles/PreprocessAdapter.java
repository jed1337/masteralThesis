package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
import java.io.IOException;

public class PreprocessAdapter extends PreprocessFile{
   public PreprocessAdapter
         (String fileName, GeneralAttackTypeEnum generalAttackType, SpecificAttackTypeEnum specificAttackType)
            throws IOException {
      super(fileName, generalAttackType, specificAttackType);
   }
}