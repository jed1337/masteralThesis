package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public final class PreprocessNoiseApacheBenchmark extends PreprocessFile{
   public PreprocessNoiseApacheBenchmark() throws IOException {
      super(
         FileNameConstants.NOISE_APACHE_BENCHMARK, 
         GeneralAttackType.NORMAL, 
         new String[]{"normal"}
      );
   }
}