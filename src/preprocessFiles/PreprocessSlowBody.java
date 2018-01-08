package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;

public class PreprocessSlowBody implements PreprocessFileBuilderStrategy{
   @Override
   public PreprocessFile.PreprocessFileBuilder getBuilder() {
      return new PreprocessFile.PreprocessFileBuilder(
         f->f.getSlowBodyPath(),
         GeneralAttackTypeEnum.LOW_RATE,
         SpecificAttackTypeEnum.SLOW_BODY
      );
   }
}