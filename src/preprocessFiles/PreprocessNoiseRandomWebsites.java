package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.FileNameConstants;
import constants.SpecificAttackTypeEnum;
import java.io.IOException;

public final class PreprocessNoiseRandomWebsites implements PreprocessFileBuilderStrategy{
   public PreprocessNoiseRandomWebsites() throws IOException {
      super(FileNameConstants.NOISE_RANDOM_WEBSITES, 
         GeneralAttackTypeEnum.NORMAL, 
         "normal"
      );
   }

   @Override
   public PreprocessFile.PreprocessFileBuilder getBuilder() {
      return new PreprocessFile.PreprocessFileBuilder(
         f->f.getHTTPFloodPath(),
         GeneralAttackTypeEnum.HIGH_RATE,
         SpecificAttackTypeEnum.HTTP_FLOOD
      );
   }
}