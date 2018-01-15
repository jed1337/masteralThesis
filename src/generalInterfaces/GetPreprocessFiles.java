package generalInterfaces;

import java.io.IOException;
import java.util.List;
import preprocessFiles.PreprocessFile;

public interface GetPreprocessFiles {
   public abstract List<PreprocessFile> getPreprocessFiles() throws IOException;
}
