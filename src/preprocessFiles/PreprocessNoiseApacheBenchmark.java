package preprocessFiles;

import constants.GeneralAttackType;
import constants.FileNameConstants;
import java.io.IOException;

public final class PreprocessNoiseApacheBenchmark extends PreprocessFile{
   public PreprocessNoiseApacheBenchmark() throws IOException {
      super(
         FileNameConstants.NOISE_APACHE_BENCHMARK, 
         GeneralAttackType.HIGH_RATE,
         "normal"
      );
   }
}