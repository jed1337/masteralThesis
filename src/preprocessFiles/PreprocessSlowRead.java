package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;

public class PreprocessSlowRead implements PreprocessFileBuilderStrategy{
   @Override
   public PreprocessFile.PreprocessFileBuilder getBuilder() {
      return new PreprocessFile.PreprocessFileBuilder(
         f->f.getSlowReadPath(),
         GeneralAttackTypeEnum.LOW_RATE,
         SpecificAttackTypeEnum.SLOW_READ
      );
   }
}