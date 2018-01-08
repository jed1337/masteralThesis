package preprocessFiles;

import constants.FileNameConstants;
import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;
import java.io.IOException;

public class PreprocessUDPFlood implements PreprocessFileBuilderStrategy{
   @Override
   public PreprocessFile.PreprocessFileBuilder getBuilder() {
      return new PreprocessFile.PreprocessFileBuilder(
         f->f.getUDPFloodPath(),
         GeneralAttackTypeEnum.HIGH_RATE,
         SpecificAttackTypeEnum.UDP_FLOOD
      );
   }
}