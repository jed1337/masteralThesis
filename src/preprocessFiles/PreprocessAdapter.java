package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
import java.io.IOException;

public class PreprocessAdapter extends PreprocessFile{
<<<<<<< HEAD
   public PreprocessAdapter(String fileName, GeneralAttackTypeEnum generalAttackType, SpecificAttackTypeEnum specificAttackType)
           throws IOException {
=======
   public PreprocessAdapter
         (String fileName, GeneralAttackTypeEnum generalAttackType, SpecificAttackTypeEnum specificAttackType)
            throws IOException {
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
      super(fileName, generalAttackType, specificAttackType);
   }
}