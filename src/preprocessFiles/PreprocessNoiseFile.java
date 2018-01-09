package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import java.io.IOException;

public abstract class PreprocessNoiseFile extends PreprocessFile{
   public PreprocessNoiseFile(String fileName)throws IOException {
      super(
         fileName, 
         GeneralAttackTypeEnum.NORMAL,
         "normal"
      );
   }
}