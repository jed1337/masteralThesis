package preprocessFiles;

import constants.FileNameConstants;
import java.io.IOException;

public final class PreprocessNoiseApacheBenchmark extends PreprocessFile{
   /**
    * Returns httpFlood
    * @throws IOException 
    */
   public PreprocessNoiseApacheBenchmark() throws IOException {
      super(
         FileNameConstants.NOISE_APACHE_BENCHMARK, 
         GeneralAttackType.HIGH_RATE,
         new String[]{"httpFlood"}
      );
   }
}