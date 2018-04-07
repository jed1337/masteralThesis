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

/**
 * The noise data here contains DDoS attacks that have been relabeled to "Normal"
 * as well as everything from  {@link driver.mode.noiseLevel.HalfNoise}
 */
public final class MultiNoise implements NoiseDataset{

   @Override
   public NoiseDatasetNames getNoiseDatasetName(){
      return NoiseDatasetNames.NOISE_2;
   }

   @Override
   public List<PreprocessFile> getPreprocessFiles() throws IOException {
      return Collections.unmodifiableList(Arrays.asList(
			new PreprocessNoiseNormal(),
			new PreprocessNoiseHttpFlood(),
			new PreprocessNoiseUDPFlood(),
			new PreprocessNoiseTCPFlood(),
			new PreprocessNoiseSlowRead(),
			new PreprocessNoiseSlowHeaders()
      ));
   }
}