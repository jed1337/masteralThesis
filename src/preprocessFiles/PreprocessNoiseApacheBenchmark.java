package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.FileNameConstants;
import constants.SpecificAttackTypeEnum;
import java.io.IOException;

public final class PreprocessNoiseApacheBenchmark implements PreprocessFileBuilderStrategy{
   public PreprocessNoiseApacheBenchmark() throws IOException {
      super(FileNameConstants.NOISE_APACHE_BENCHMARK, 
         GeneralAttackTypeEnum.HIGH_RATE,
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