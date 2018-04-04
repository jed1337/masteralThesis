package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.PreprocessFileName;
import constants.SpecificAttackTypeEnum;
import java.io.IOException;

public class PreprocessAdapter extends PreprocessFile{
   public PreprocessAdapter
      (String fileName, GeneralAttackTypeEnum generalAttackType, SpecificAttackTypeEnum specificAttackType, PreprocessFileName pfName)
      throws IOException {
      super(fileName, generalAttackType, specificAttackType, pfName);
   }
}