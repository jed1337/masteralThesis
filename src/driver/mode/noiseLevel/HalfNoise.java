package driver.mode.noiseLevel;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessFile;
import preprocessFiles.noise.PreprocessNoiseHttpFlood;
import preprocessFiles.noise.PreprocessNoiseNormal;
import preprocessFiles.noise.PreprocessNoiseSlowHeaders;
import preprocessFiles.noise.PreprocessNoiseSlowRead;
import preprocessFiles.noise.PreprocessNoiseTCPFlood;
import preprocessFiles.noise.PreprocessNoiseUDPFlood;

public final class HalfNoise implements NoiseLevel{
   @Override
   public List<PreprocessFile> getPreprocessFiles() throws IOException {
      return Collections.unmodifiableList(Arrays.asList(
			new PreprocessNoiseNormal(),
			new PreprocessNoiseHttpFlood(),
			new PreprocessNoiseUDPFlood(),
			new PreprocessNoiseTCPFlood(),
//			new PreprocessNoiseSlowBody(),
			new PreprocessNoiseSlowRead(),
			new PreprocessNoiseSlowHeaders()
      ));
   }

   /**
    * @return 0.5 since the amount of normal data and noise gets
    * evenly distributed by categoricalType.setPreprocessFileCount(). I.E.
    * 50% noise, 50% normal
    */
   @Override
   public float getNoiseLevelFloat(){
      return 0.50f;
   }

   @Override
   public String getNoiseLevelString() {
      return "Noise";
   }
}