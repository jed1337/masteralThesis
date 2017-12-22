package driver.mode.noiseLevel;

import java.io.IOException;
import java.util.List;
import preprocessFiles.PreprocessFile;

public interface NoiseLevel {
   public abstract List<PreprocessFile> getPreprocessedFiles() throws IOException;

   public abstract float getNoiseLevelFloat();
   public abstract String getNoiseLevelString();
}