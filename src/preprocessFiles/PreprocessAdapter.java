package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import java.io.IOException;

public class PreprocessAdapter extends PreprocessFile{
   public PreprocessAdapter(String fileName, GeneralAttackTypeEnum generalAttackType, String specificAttackType)
           throws IOException {
      super(fileName, generalAttackType, specificAttackType);
   }
}