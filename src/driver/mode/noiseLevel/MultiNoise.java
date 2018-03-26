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

public final class MultiNoise implements NoiseLevel{
   /**
    * @return 0.83333 since the amount of normal data and noise gets
    * evenly distributed by categoricalType.setPreprocessFileCount(). I.E.
    * 1/7: normal, 1/7: normalNoise, 1/7: noiseHTTPFlood, etc
    */
   @Override
   public float getNoiseLevelFloat(){
      return 0.83333f;
   }

   @Override
   public String getNoiseLevelString() {
      return "Noise";
   }

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
}