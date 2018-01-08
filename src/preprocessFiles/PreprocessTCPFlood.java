package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;

public class PreprocessTCPFlood implements PreprocessFileBuilderStrategy{
   @Override
   public PreprocessFile.PreprocessFileBuilder getBuilder() {
      return new PreprocessFile.PreprocessFileBuilder(
         f->f.getTCPFloodPath(),
         GeneralAttackTypeEnum.HIGH_RATE,
         SpecificAttackTypeEnum.TCP_FLOOD
      );
   }
}