package driver.mode.noiseLevel;

import generalInterfaces.GetPreprocessFiles;

public interface NoiseLevel extends GetPreprocessFiles{
   public abstract float getNoiseLevelFloat();
   public abstract String getNoiseLevelString();
}