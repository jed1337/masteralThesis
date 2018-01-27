package featureExtraction;

import featureExtraction.interfaces.ARFFPaths;
import featureExtraction.interfaces.GetDatasetName;
import featureExtraction.interfaces.RemoveBasedOn;

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
}