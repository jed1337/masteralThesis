package featureExtraction;

import java.util.List;
import java.util.function.Consumer;
import preprocessFiles.preprocessAs.FormatAsArff;

interface RemoveBasedOn {
   public abstract List<String> getFeaturesToRemove();
   public abstract Consumer<FormatAsArff> removeNonMatchingClasses();
}
