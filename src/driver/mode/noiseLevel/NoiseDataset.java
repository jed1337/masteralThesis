package driver.mode.noiseLevel;

import constants.NoiseDatasetNames;
import generalInterfaces.GetPreprocessFiles;

public interface NoiseDataset extends GetPreprocessFiles{
   
   /**
    * The identifier of the noise dataset used
    * @return 
    */
   public abstract NoiseDatasetNames getNoiseDatasetName();
}