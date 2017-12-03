package driver.systemConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import setupFiles.SetupFile;

public interface NoiseLevel {
   public abstract ArrayList<SetupFile> getNoiseLevel(final int count) throws IOException;
}