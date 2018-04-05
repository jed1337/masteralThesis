package driver.mode.noiseLevel;

import constants.NoiseDatasetNames;
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
    * @return the NosieDatasetNames
    */
   @Override
   public NoiseDatasetNames getNoiseDatasetName(){
      return NoiseDatasetNames.NOISE_2;
   }

   @Override
   public float getNoiseToAttackcRatio() {
      return 0.83333f;
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