package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.FileNameConstants;
import constants.SpecificAttackTypeEnum;
import java.io.IOException;

public final class PreprocessNoiseApacheBenchmark extends PreprocessFile{
   public PreprocessNoiseApacheBenchmark() throws IOException {
      super(FileNameConstants.NOISE_APACHE_BENCHMARK, 
         GeneralAttackTypeEnum.HIGH_RATE,
         SpecificAttackTypeEnum.NORMAL
      );
   }
}