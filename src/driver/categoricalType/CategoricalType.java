package driver.categoricalType;

import constants.CategoricalTypeConstants;
import java.util.List;
import preprocessFiles.PreprocessFile;

public interface CategoricalType {
   public int getClassCount(List<PreprocessFile> pfL);
   public String getRelabel(List<PreprocessFile> pfL);
   public CategoricalTypeConstants getCategoricalType();
}