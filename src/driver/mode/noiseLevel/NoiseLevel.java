package driver.mode.noiseLevel;

import generalInterfaces.GetPreprocessFiles;

public interface NoiseLevel extends GetPreprocessFiles{
   /**
    * Returns the percentage of noise data to put with the normal data
    * @return 
    */
   public abstract float getNoiseLevelFloat();
   
   /**
    * The identifier of the subclass
    * @return 
    */
   public abstract String getNoiseLevelString();
}