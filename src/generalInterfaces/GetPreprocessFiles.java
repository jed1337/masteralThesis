package generalInterfaces;

import java.io.IOException;
import java.util.List;
import preprocessFiles.PreprocessFile;

/**
 * Is within generalInterfaces since lots of unrelated classes implement this
 */
public interface GetPreprocessFiles {
   /**
    * @return a list of PreprocessFiles
    * @throws IOException if the file within PreprocessFile can't be found
    * or for some other reason as stated by the implementing class
    */
   public abstract List<PreprocessFile> getPreprocessFiles() throws IOException;
}
