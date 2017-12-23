package preprocessFiles;

import constants.GeneralAttackType;
import java.io.IOException;

public class PreprocessAdapter extends PreprocessFile{
   public PreprocessAdapter(String fileName, Enum<GeneralAttackType> generalAttackType, String[] specificAttackTypes)
           throws IOException {
      super(fileName, generalAttackType, specificAttackTypes);
   }
}