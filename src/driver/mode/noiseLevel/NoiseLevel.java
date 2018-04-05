package driver.mode.noiseLevel;

import constants.NoiseDatasetNames;
import generalInterfaces.GetPreprocessFiles;

public interface NoiseLevel extends GetPreprocessFiles{
   
   /**
    * The identifier of the noise dataset used
    * @return 
    */
   public abstract NoiseDatasetNames getNoiseDatasetName();
   
   /**
    * Returns the percentage of noise data to put with the normal data
    * @return
    */
   public abstract float normalToNoiseRatio();
}