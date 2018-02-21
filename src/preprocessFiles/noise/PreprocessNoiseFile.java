package preprocessFiles.noise;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
import java.io.IOException;
import preprocessFiles.PreprocessFile;

public abstract class PreprocessNoiseFile extends PreprocessFile{
   public PreprocessNoiseFile(String fileName)throws IOException {
      super(
         fileName, 
         GeneralAttackTypeEnum.NORMAL,
         SpecificAttackTypeEnum.NORMAL
      );
   }
}