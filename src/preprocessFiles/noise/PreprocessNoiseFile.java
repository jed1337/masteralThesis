package preprocessFiles.noise;

import constants.GeneralAttackTypeEnum;
import constants.PreprocessFileName;
import constants.SpecificAttackTypeEnum;
import java.io.IOException;
import preprocessFiles.PreprocessFile;

public abstract class PreprocessNoiseFile extends PreprocessFile{
   protected PreprocessNoiseFile(String fileName, PreprocessFileName pfName)throws IOException {
      super(
         fileName, 
         GeneralAttackTypeEnum.NORMAL,
         SpecificAttackTypeEnum.NORMAL,
         pfName
      );
   }
}