package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;

public class PreprocessHTTPFlood implements PreprocessFileBuilderStrategy{
   @Override
   public PreprocessFile.PreprocessFileBuilder getBuilder() {
      return new PreprocessFile.PreprocessFileBuilder(
         f->f.getHTTPFloodPath(),
         GeneralAttackTypeEnum.HIGH_RATE,
         SpecificAttackTypeEnum.HTTP_FLOOD
      );
   }
}