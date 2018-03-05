package featureExtraction;

import featureExtraction.interfaces.ARFFPaths;
import featureExtraction.interfaces.GetDatasetName;
import featureExtraction.interfaces.RemoveBasedOn;
import java.util.function.Consumer;
import preprocessFiles.preprocessAs.FormatAsArff;

public abstract class FeatureExtraction implements ARFFPaths, RemoveBasedOn, GetDatasetName{
   public abstract String getName();
   
   /**
    * Actual implementation delegated to ExtractionDBPathDecorator
    * @see featureExtraction.ExtractionDBPathDecorator#getDatasetName()
    * @return 
    */
   @Override
   public String getDatasetName() {
      return "";
   }
   
   /**
    * Additional formatting e.g. String to nominal. <br>
    * Should not delete stuff
    * @return 
    */
   public abstract Consumer<FormatAsArff> additionalFormatting();
}