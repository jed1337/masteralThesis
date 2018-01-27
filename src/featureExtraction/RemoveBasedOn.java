package featureExtraction;

import java.util.List;
import java.util.function.Consumer;
import preprocessFiles.preprocessAs.FormatAsArff;

public interface RemoveBasedOn {
   /**
    * Returns a String List (Ideally an unmodifiable one)
    * of the features to be removed <br>
    * The implementation differs depending on which class extending
    * FeatureExtraction implements this interface
    * @return 
    */
   public abstract List<String> getFeaturesToRemove();
   
   /**
    * Should only make use of the removeNonMatchingClasses function in FormatAsArff
    * @see preprocessFiles.preprocessAs.FormatAsArff#removeNonMatchingClasses(String, String...)
    * @return 
    */
   public abstract Consumer<FormatAsArff> removeNonMatchingClasses();
}
