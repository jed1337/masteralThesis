package driver.systemConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import preprocessFiles.PreprocessFile;

public interface NoiseLevel {
   public abstract ArrayList<PreprocessFile> getNoiseLevel(final int count) throws IOException;
}